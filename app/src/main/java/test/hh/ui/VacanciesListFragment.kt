package test.hh.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.gson.reflect.TypeToken
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.hh.HHApp
import test.hh.R
import test.hh.VacancySelectedEvent
import test.hh.server.SearchResult
import test.hh.server.Vacancy

//todo fancy empty view when no vacancies for search
class VacanciesListFragment : Fragment() {
  private val PREFS = "PREFS"

  lateinit var btnSearch: ImageView
  lateinit var searchFieldLayout: TextInputLayout
  lateinit var searchFieldText: AppCompatEditText
  lateinit var vacanciesList: RecyclerView
  lateinit var swipeRefreshLayout: SwipeRefreshLayout

  lateinit var prefs: SharedPreferences

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_vacancies_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btnSearch = view.findViewById(R.id.btn_search)
    btnSearch.setOnClickListener{ updateVacancies() }

    searchFieldLayout = view.findViewById(R.id.til_search_field)

    searchFieldText = view.findViewById(R.id.et_search_field)

    swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
    swipeRefreshLayout.setOnRefreshListener { updateVacancies() }

    vacanciesList = view.findViewById(R.id.list_vacancies)
    vacanciesList.addItemDecoration(DividerItemDecoration(vacanciesList.context, OrientationHelper.VERTICAL))
    vacanciesList.layoutManager = LinearLayoutManager(activity)

    prefs = activity!!.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    val vacancies = (activity!!.application as HHApp).gson.fromJson<List<Vacancy>>(prefs.getString("vacancies", ""), object:TypeToken<List<Vacancy>>(){}.type) ?: emptyList()
    setVacancies(vacancies)
  }

  private fun updateVacancies() {
    swipeRefreshLayout.isRefreshing = true
    (activity!!.application as HHApp).serverConnector.api.vacancies(searchFieldText.text.toString())
      .enqueue(object:Callback<SearchResult>{
      override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
        swipeRefreshLayout.isRefreshing = false
        val vacancies = response.body()?.vacancies ?: emptyList()
        setVacancies(vacancies)
        if (vacancies.isNotEmpty()) prefs.edit().putString("vacancies", (activity!!.application as HHApp).gson.toJson(vacancies)).apply()
      }

      override fun onFailure(call: Call<SearchResult>, t: Throwable) {
        Log.e("Error", t.toString())//todo some nice dialog
        swipeRefreshLayout.isRefreshing = false
      }
    })
  }

  private fun setVacancies(vacancies: List<Vacancy>) {
    vacanciesList.adapter = VacanciesAdapter(activity!!.applicationContext, vacancies) {openVacancyDescription(it)}
  }

  private fun openVacancyDescription(vacancy: Vacancy) {
    val isTablet = resources.getBoolean(R.bool.is_tablet)
    if (isTablet) {
      EventBus.getDefault().post(VacancySelectedEvent(vacancy))
    } else {
      val fragment = VacancyDescriptionFragment()
      val args = Bundle()
      args.putString("vacancy", (activity!!.application as HHApp).gson.toJson(vacancy))
      fragment.arguments = args
      val fm = activity!!.supportFragmentManager
      fm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
    }
  }
}