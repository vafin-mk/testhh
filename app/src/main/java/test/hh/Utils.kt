package test.hh

import android.content.Context
import test.hh.server.Vacancy

fun prettySalaryText(context:Context, vacancy: Vacancy): String {
  if (vacancy.salary == null || (vacancy.salary.min == null && vacancy.salary.max == null)) return context.getString(R.string.no_salary)
  return buildString {
    if (vacancy.salary.min != null) append(context.getString(R.string.salary_from, vacancy.salary.min.toInt())).append(" ")
    if (vacancy.salary.max != null) append(context.getString(R.string.salary_to, vacancy.salary.max.toInt())).append(" ")
    append(vacancy.salary.currency).append(" ")
    if (vacancy.salary.gross == true) append(context.getString(R.string.salary_gross))
  }
}