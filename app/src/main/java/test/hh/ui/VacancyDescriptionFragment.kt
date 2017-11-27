package test.hh.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import test.hh.HHApp
import test.hh.R
import test.hh.VacancySelectedEvent
import test.hh.prettySalaryText
import test.hh.server.Vacancy

class VacancyDescriptionFragment: Fragment() {

  private var vacancy: Vacancy? = null
  private lateinit var name: AppCompatTextView
  private lateinit var employer: AppCompatTextView
  private lateinit var salary: AppCompatTextView
  private lateinit var requirement: AppCompatTextView
  private lateinit var responsibility: AppCompatTextView
  private lateinit var noVacancySelectedView: View
  private lateinit var vacancyLayout: ViewGroup

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments?.getString("vacancy") != null) {
      vacancy = (activity?.application as HHApp).gson.fromJson(arguments?.getString("vacancy"), Vacancy::class.java)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_vacancy_description, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    name = view.findViewById(R.id.name)
    employer = view.findViewById(R.id.employer)
    salary = view.findViewById(R.id.salary)
    requirement = view.findViewById(R.id.requirement)
    responsibility = view.findViewById(R.id.responsibility)
    noVacancySelectedView = view.findViewById(R.id.no_vacancy_selected)
    vacancyLayout = view.findViewById(R.id.vacancy_layout)
    updateVacancy(vacancy)
  }

  private fun updateVacancy(vacancy: Vacancy?) {
    if (vacancy == null) {
      noVacancySelectedView.visibility = View.VISIBLE
      vacancyLayout.visibility = View.GONE
      return
    }
    noVacancySelectedView.visibility = View.GONE
    vacancyLayout.visibility = View.VISIBLE

    name.text = vacancy.name ?: ""
    employer.text = vacancy.employer?.name ?: ""
    salary.text = prettySalaryText(context!!, vacancy)
    requirement.text = vacancy.info?.requirement ?: ""
    responsibility.text = vacancy.info?.responsibility ?: ""
  }

  @Subscribe(threadMode = ThreadMode.MAIN) fun onMessageEvent(event: VacancySelectedEvent) = updateVacancy(event.vacancy)

  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }
}