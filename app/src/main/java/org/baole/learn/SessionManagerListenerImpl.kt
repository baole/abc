package org.baole.learn

import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import com.google.android.gms.cast.framework.Session
import com.google.android.gms.cast.framework.SessionManagerListener

class SessionManagerListenerImpl(val activity: MainActivity) : SessionManagerListener<Session> {
    override fun onSessionStarted(p0: Session?, p1: String?) {
        activity.invalidateOptionsMenu()
    }

    override fun onSessionEnded(p0: Session?, p1: Int) {
    }

    override fun onSessionResumed(p0: Session?, p1: Boolean) {
        activity.invalidateOptionsMenu()
    }

    override fun onSessionResumeFailed(p0: Session?, p1: Int) {
    }

    override fun onSessionSuspended(p0: Session?, p1: Int) {
    }

    override fun onSessionStarting(p0: Session?) {
    }

    override fun onSessionResuming(p0: Session?, p1: String?) {
    }

    override fun onSessionEnding(p0: Session?) {
    }

    override fun onSessionStartFailed(p0: Session?, p1: Int) {
    }
}