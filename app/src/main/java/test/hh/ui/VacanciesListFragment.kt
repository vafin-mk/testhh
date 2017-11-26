package test.hh.ui

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.hh.HHApp
import test.hh.R
import test.hh.server.SearchResult
import test.hh.server.Vacancy

class VacanciesListFragment : Fragment() {

  lateinit var btnSearch: ImageView
  lateinit var searchFieldLayout: TextInputLayout
  lateinit var searchFieldText: AppCompatEditText
  lateinit var vacanciesList: RecyclerView
  lateinit var swipeRefreshLayout: SwipeRefreshLayout

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_vacancies_list, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (view == null) throw IllegalStateException("view is null")

    btnSearch = view.findViewById(R.id.btn_search)
    btnSearch.setOnClickListener{ updateVacancies() }

    searchFieldLayout = view.findViewById(R.id.til_search_field)

    searchFieldText = view.findViewById(R.id.et_search_field)

    swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
    swipeRefreshLayout.setOnRefreshListener { updateVacancies() }

    vacanciesList = view.findViewById(R.id.list_vacancies)
    vacanciesList.addItemDecoration(DividerItemDecoration(vacanciesList.context, OrientationHelper.VERTICAL))
    vacanciesList.layoutManager = LinearLayoutManager(activity)
  }

  private fun updateVacancies() {
    swipeRefreshLayout.isRefreshing = true
    (activity.application as HHApp).serverConnector.api.vacancies(searchFieldText.text.toString())
      .enqueue(object:Callback<SearchResult>{
      override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
        swipeRefreshLayout.isRefreshing = false
        vacanciesList.adapter = VacanciesAdapter(activity.applicationContext, response.body()?.vacancies ?: emptyList()) {openVacancyDescription(it)}
      }

      override fun onFailure(call: Call<SearchResult>, t: Throwable) {
        Log.e("Error", t.toString())
        swipeRefreshLayout.isRefreshing = false
      }
    })
  }

  private fun openVacancyDescription(vacancy: Vacancy) {
    val fragment = VacancyDescriptionFragment()
    val args = Bundle()
    args.putString("vacancy", (activity.application as HHApp).gson.toJson(vacancy))
    fragment.arguments = args
    val fm = activity.supportFragmentManager
    fm.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
  }
}