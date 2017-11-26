package test.hh.ui

import android.app.Application
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_vacancy.view.*
import test.hh.R
import test.hh.server.Vacancy

class VacanciesAdapter(private val context: Context, private val items: List<Vacancy>, private val onVacancySelected: (Vacancy) -> Unit): RecyclerView.Adapter<VacanciesAdapter.ViewHolder>() {

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val vacancy = items[position]
    holder.name.text = vacancy.name
    holder.employer.text = vacancy.employer?.name ?: ""
    holder.salary.text = prettySalaryText(vacancy)
  }

  private fun prettySalaryText(vacancy: Vacancy): String {
    if (vacancy.salary == null || (vacancy.salary.min == null && vacancy.salary.max == null)) return context.getString(R.string.no_salary)
    return buildString {
      if (vacancy.salary.min != null) append(context.getString(R.string.salary_from, vacancy.salary.min.toInt())).append(" ")
      if (vacancy.salary.max != null) append(context.getString(R.string.salary_to, vacancy.salary.max.toInt())).append(" ")
      append(vacancy.salary.currency).append(" ")
      if (vacancy.salary.gross == true) append(context.getString(R.string.salary_gross))
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false))
  override fun getItemCount(): Int = items.size

  inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name: AppCompatTextView = view.findViewById(R.id.name)
    val employer: AppCompatTextView = view.findViewById(R.id.employer)
    val salary: AppCompatTextView = view.findViewById(R.id.salary)
    init {
      view.setOnClickListener{onVacancySelected.invoke(items[adapterPosition])}
    }
  }
}