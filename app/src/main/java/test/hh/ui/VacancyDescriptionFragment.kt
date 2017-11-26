package test.hh.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import test.hh.HHApp
import test.hh.R
import test.hh.server.Vacancy

class VacancyDescriptionFragment: Fragment() {

  lateinit var vacancy: Vacancy

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    vacancy = (activity.application as HHApp).gson.fromJson(arguments.getString("vacancy"), Vacancy::class.java)
    Log.e("TEST", vacancy.toString())
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater?.inflate(R.layout.fragment_vacancy_description, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (view == null) throw IllegalStateException("view is null")

  }
}