package test.hh

import android.app.Application
import test.hh.server.ServerConnector

class HHApp : Application() {

  lateinit var serverConnector : ServerConnector

  override fun onCreate() {
    super.onCreate()
    serverConnector = ServerConnector()
  }
}

