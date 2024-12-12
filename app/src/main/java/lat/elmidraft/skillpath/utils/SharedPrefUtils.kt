package lat.elmidraft.skillpath.utils

import android.content.Context

class SharedPrefUtils {
    data class User(
        val token: String,
        val email: String,
        val username: String
    )
    companion object{
        public fun saveUser(token:String, email:String, username:String, ctx:Context){
            val sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.putString("token", token)
            editor.putString("username",username)
            editor.putString("email",email)
            editor.apply()
        }
        public fun removeUserToken(ctx:Context){
            val sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("token")
            editor.remove("username")
            editor.remove("email")
            editor.apply()
        }
        public fun getUser(ctx:Context):User{
            val sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getString("token", "")?:"",
                sharedPreferences.getString("username", "")?:"",
                sharedPreferences.getString("email", "")?:"")
        }
    }
}