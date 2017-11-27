package test.hh.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import test.hh.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupScreens()
  }

  private fun setupScreens() {
    val fm = supportFragmentManager
    val transaction = fm.beginTransaction()
    transaction.add(R.id.container, VacanciesListFragment())
    val isTablet = resources.getBoolean(R.bool.is_tablet)
    if (isTablet) {
      transaction.add(R.id.second_container, VacancyDescriptionFragment())
    }
    transaction.commit()
  }
}