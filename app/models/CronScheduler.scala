package models
 
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.quartz.impl.StdSchedulerFactory
import java.util.Random
import play.Logger

import controllers._

 object CronScheduler {
    val rand :Random = new Random()
    @throws(classOf[Exception])
    def ScheduleJob(jobName : String, SchedulerTimePattern : String, jobDataMap : JobDataMap) : String = {      
		  try {

			  val ScheduleTime : String = SchedulerTimePattern
			  val randomNum : Int = rand.nextInt()
			  val scheduler : Scheduler = new StdSchedulerFactory().getScheduler()
			  val jobKey : JobKey = JobKey.jobKey(jobName + randomNum, jobName + "_group" + randomNum)
			  if (scheduler.checkExists(jobKey))
				  scheduler.deleteJob(jobKey)

			  val job : JobDetail = JobBuilder.newJob(classOf[ScheduleJob]).usingJobData(jobDataMap).withIdentity(jobKey)
					.build()
			  val tk : TriggerKey = TriggerKey.triggerKey(jobName + randomNum, jobName + "_group" + randomNum)
			  val trigger : Trigger = TriggerBuilder.newTrigger().withIdentity(tk)
					.withSchedule(CronScheduleBuilder.cronSchedule(ScheduleTime)).build()

			  scheduler.start()
			  scheduler.scheduleJob(job, trigger)
			  return "Job Scheduled Successfully"
		  } catch {
		      case ex: SchedulerException =>{
           Logger.info(" Based on given time schedule, the given trigger will never fire.")
			     return " Based on given time schedule, the given trigger will never fire."
          }	
		      case ex: RuntimeException =>{
            Logger.info("Cron Expression Format is not Correct, Either Day or Week Should be ?.")
			      return "Cron Expression Format is not Correct, Either Day or Week Should be ?."
           }			     
		    }	
	  }	
 }
 