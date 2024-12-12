package lat.elmidraft.skillpath.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import lat.elmidraft.skillpath.utils.SharedPrefUtils

class SplashViewModel : ViewModel(){
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> = _isLogged

    fun checkLogin(ctx:Context) {
        viewModelScope.launch {
            delay(500)
            val token = SharedPrefUtils.getUser(ctx).token
            _isLogged.value = token!=""
        }
    }
}