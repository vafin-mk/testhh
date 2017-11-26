package test.hh.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import test.hh.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupMainFragment()
  }

  private fun setupMainFragment() {
    val fm = supportFragmentManager
    fm.beginTransaction().add(R.id.container, VacanciesListFragment()).commit()
  }
}