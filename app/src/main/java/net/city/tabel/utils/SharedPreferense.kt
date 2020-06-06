package net.city.tabel.utils

import android.content.Context

class SharedPreferense{


    companion object{

        private lateinit var context: Context

        fun setContext(contextF: Context){
            context=contextF
        }

        private val sharedPreferense by lazy { context.getSharedPreferences("TABEL",Context.MODE_PRIVATE) }

        fun getLogin():String?{
            return sharedPreferense.getString("LOGIN",null)
        }


        fun setLogin(login:String) {
            sharedPreferense.edit().putString("LOGIN",login).apply()

        }

        fun getPassword():String?{
            return sharedPreferense.getString("PASSWORD",null)
        }

        fun setPassword(password:String) {
            sharedPreferense.edit().putString("PASSWORD",password).apply()

        }

/*
        fun setWorkerObjects(ls:List<WorkerObject>){
         val editor= sharedPreferense.edit()
            val gson=Gson()
            val json=gson.toJson(ls)
            editor.putString("OBJECTLIST",json)
            editor.apply()
        }

        fun getWorkerObjects():List<WorkerObject>? {
            val gson=Gson()
            val json= sharedPreferense.getString("OBJECTLIST","")
            val type: Type = object : TypeToken<List<WorkerObject?>?>() {}.type

            val list:List<WorkerObject>? =gson.fromJson<List<WorkerObject>>(json,type)
            return list
        }
*/

        fun setTodayWorkers(ls:List<String>){

            sharedPreferense.edit().putStringSet("SAVEIDS",ls.toSet()).apply()

        }

        fun getTodayWorkers():List<String>?{

            val set=sharedPreferense.getStringSet("SAVEIDS",null)

            return set?.toList()
        }
    }

}