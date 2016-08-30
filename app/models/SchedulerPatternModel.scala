package models

import play.api.libs.json._

object SchedulerPatternModel {
  
  val data: JsValue =Json.arr(
    Json.obj(
        "name" -> "second",        
        "specialValue" -> Json.arr("*"),
        "range" -> Json.arr(0,59)
        ),
    Json.obj(
        "name" -> "minute",        
        "specialValue" -> Json.arr("*"),
        "range" -> Json.arr(0,59)
        ),
         Json.obj(
        "name" -> "hour",       
        "specialValue" -> Json.arr("*"),
        "range" -> Json.arr(0,23)
        ),
         Json.obj(
        "name" -> "day",       
        "specialValue" -> Json.arr("*","?"),
        "range" -> Json.arr(1,31)
        ),
         Json.obj(
        "name" -> "month",        
        "specialValue" -> Json.arr("*"),
        "range" -> Json.arr(1,12)
        ),
         Json.obj(
        "name" -> "week",        
        "specialValue" -> Json.arr("?","*","MON","TUE","WED","THU","FRI","SAT","SUN"),
        "range" -> Json.arr(0,0)
        ),
         Json.obj(
        "name" -> "year",       
        "specialValue" -> Json.arr("NO","*"),
        "range" -> Json.arr(2016,2099)
        )        
  )
}