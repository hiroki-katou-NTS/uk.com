package nts.uk.ctx.at.request.dom.application.stamp;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.AppReason;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanScheReason;
/**
 * 
 * @author Doan Duy Hung
 *
 */
/**
 * 
 * 打刻申請
 *
 */
@Value
@EqualsAndHashCode(callSuper = false)
public class ApplicationStamp extends Application {
	
	private StampRequestMode stampRequestMode;
	
	private List<ApplicationStampGoOutPermit> applicationStampGoOutPermits;
	
	private List<ApplicationStampWork> applicationStampWorks;
	
	private List<ApplicationStampCancel> applicationStampCancels;
	
	private ApplicationStampOnlineRecord applicationStampOnlineRecords;

	public ApplicationStamp(String companyID, String applicationID, PrePostAtr prePostAtr, GeneralDate inputDate,
			String enteredPersonSID, AppReason reversionReason, GeneralDate applicationDate,
			AppReason applicationReason, ApplicationType applicationType, String applicantSID,
			ReflectPlanScheReason reflectPlanScheReason, GeneralDate reflectPlanTime,
			ReflectPlanPerState reflectPlanState, ReflectPlanPerEnforce reflectPlanEnforce,
			ReflectPerScheReason reflectPerScheReason, GeneralDate reflectPerTime, ReflectPlanPerState reflectPerState,
			ReflectPlanPerEnforce reflectPerEnforce, GeneralDate startDate, GeneralDate endDate, 
			StampRequestMode stampRequestMode, List<ApplicationStampGoOutPermit> applicationStampGoOutPermits,
			List<ApplicationStampWork> applicationStampWorks, List<ApplicationStampCancel> applicationStampCancels,
			ApplicationStampOnlineRecord applicationStampOnlineRecords) {
		super(companyID, applicationID, prePostAtr, inputDate, enteredPersonSID, reversionReason, applicationDate,
				applicationReason, applicationType, applicantSID, reflectPlanScheReason, reflectPlanTime,
				reflectPlanState, reflectPlanEnforce, reflectPerScheReason, reflectPerTime, reflectPerState,
				reflectPerEnforce, startDate, endDate);
		this.stampRequestMode = stampRequestMode;
		this.applicationStampGoOutPermits = applicationStampGoOutPermits;
		this.applicationStampWorks = applicationStampWorks;
		this.applicationStampCancels = applicationStampCancels;
		this.applicationStampOnlineRecords = applicationStampOnlineRecords;
	}
	
	public static ApplicationStamp createFromJavaType(String companyID, PrePostAtr prePostAtr, 
			GeneralDate inputDate, String enteredPersonSID, GeneralDate appDate, String applicantSID, 
			StampRequestMode stampRequestMode, List<ApplicationStampGoOutPermit> applicationStampGoOutPermits,
			List<ApplicationStampWork> applicationStampWorks, List<ApplicationStampCancel> applicationStampCancels,
			ApplicationStampOnlineRecord applicationStampOnlineRecords){
		return new ApplicationStamp(
				companyID, 
				UUID.randomUUID().toString(), 
				prePostAtr, 
				inputDate, 
				enteredPersonSID, 
				new AppReason(""), 
				appDate, 
				new AppReason(""),  
				ApplicationType.STAMP_APPLICATION, 
				applicantSID, 
				ReflectPlanScheReason.NOTPROBLEM, 
				null, 
				ReflectPlanPerState.NOTREFLECTED, 
				ReflectPlanPerEnforce.NOTTODO, 
				ReflectPerScheReason.NOTPROBLEM, 
				null, 
				ReflectPlanPerState.NOTREFLECTED, 
				ReflectPlanPerEnforce.NOTTODO, 
				null,
				null,
				stampRequestMode, 
				applicationStampGoOutPermits, 
				applicationStampWorks, 
				applicationStampCancels, 
				applicationStampOnlineRecords);
	}
}
