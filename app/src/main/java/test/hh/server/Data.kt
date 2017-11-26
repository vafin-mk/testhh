package test.hh.server

import com.google.gson.annotations.SerializedName

data class SearchResult(
  @SerializedName("items") val vacancies: List<Vacancy>
)

data class Vacancy(
  @SerializedName("salary") val salary: Salary?,
  @SerializedName("name") val name: String?,
  @SerializedName("employer") val employer: Employer?
)

data class Salary(
  @SerializedName("from") val min: Double?,
  @SerializedName("to") val max: Double?,
  @SerializedName("gross") val gross: Boolean?,
  @SerializedName("currency") val currency: String
)

data class Employer(
  @SerializedName("name") val name: String?
)