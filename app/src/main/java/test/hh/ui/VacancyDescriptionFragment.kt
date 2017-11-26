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
import test.hh.HHApp
import test.hh.R
import test.hh.prettySalaryText
import test.hh.server.Vacancy

class VacancyDescriptionFragment: Fragment() {

  lateinit var vacancy: Vacancy
  lateinit var name: AppCompatTextView
  lateinit var employer: AppCompatTextView
  lateinit var salary: AppCompatTextView
  lateinit var requirement: AppCompatTextView
  lateinit var responsibility: AppCompatTextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    vacancy = (activity.application as HHApp).gson.fromJson(arguments.getString("vacancy"), Vacancy::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_vacancy_description, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (view == null) throw IllegalStateException("view is null")

    name = view.findViewById(R.id.name)
    name.text = vacancy.name ?: ""

    employer = view.findViewById(R.id.employer)
    employer.text = vacancy.employer?.name ?: ""

    salary = view.findViewById(R.id.salary)
    salary.text = prettySalaryText(context, vacancy)

    requirement = view.findViewById(R.id.requirement)
    requirement.text = vacancy.info?.requirement ?: ""

    responsibility = view.findViewById(R.id.responsibility)
    responsibility.text = vacancy.info?.responsibility ?: ""
  }
}