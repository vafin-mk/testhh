package test.hh

import android.app.Application
import com.google.gson.Gson
import test.hh.server.ServerConnector

class HHApp : Application() {

  lateinit var serverConnector : ServerConnector
  val gson = Gson()

  override fun onCreate() {
    super.onCreate()
    serverConnector = ServerConnector()
  }
}

