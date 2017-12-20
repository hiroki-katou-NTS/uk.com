package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectPerScheReason;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerEnforce;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.ReflectPlanScheReason;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
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
public class AppStamp extends Application {
	
	private StampRequestMode stampRequestMode;
	
	private List<AppStampGoOutPermit> appStampGoOutPermits;
	
	private List<AppStampWork> appStampWorks;
	
	private List<AppStampCancel> appStampCancels;
	
	private AppStampOnlineRecord appStampOnlineRecords;

	public AppStamp(String CompanyID, String applicationID, PrePostAtr prePostAtr, GeneralDateTime inputDate,
			String enteredPersonSID, AppReason reversionReason, GeneralDate applicationDate, 
			AppReason applicationReason, ApplicationType applicationType, String applicantSID,
			ReflectPlanScheReason reflectPlanScheReason, GeneralDate reflectPlanTime,
			ReflectPlanPerState reflectPlanState, ReflectPlanPerEnforce reflectPlanEnforce,
			ReflectPerScheReason reflectPerScheReason, GeneralDate reflectPerTime, ReflectPlanPerState reflectPerState,
			ReflectPlanPerEnforce reflectPerEnforce, GeneralDate startDate, GeneralDate endDate,
			List<AppApprovalPhase> listPhase, StampRequestMode stampRequestMode,
			List<AppStampGoOutPermit> appStampGoOutPermits, List<AppStampWork> appStampWorks,
			List<AppStampCancel> appStampCancels, AppStampOnlineRecord appStampOnlineRecords) {
		super(CompanyID, applicationID, prePostAtr, inputDate, enteredPersonSID, reversionReason, applicationDate,
				applicationReason, applicationType, applicantSID, reflectPlanScheReason, reflectPlanTime,
				reflectPlanState, reflectPlanEnforce, reflectPerScheReason, reflectPerTime, reflectPerState,
				reflectPerEnforce, startDate, endDate, listPhase);
		this.stampRequestMode = stampRequestMode;
		this.appStampGoOutPermits = appStampGoOutPermits;
		this.appStampWorks = appStampWorks;
		this.appStampCancels = appStampCancels;
		this.appStampOnlineRecords = appStampOnlineRecords;
	}
	
	public static AppStamp createFromJavaType(String companyID, PrePostAtr prePostAtr, 
			GeneralDateTime inputDate, String enteredPersonSID, GeneralDate appDate, String applicantSID, 
			StampRequestMode stampRequestMode, List<AppStampGoOutPermit> appStampGoOutPermits,
			List<AppStampWork> appStampWorks, List<AppStampCancel> appStampCancels,
			AppStampOnlineRecord appStampOnlineRecords){
		return new AppStamp(
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
				appDate,
				appDate,
				null,
				stampRequestMode, 
				appStampGoOutPermits, 
				appStampWorks, 
				appStampCancels, 
				appStampOnlineRecords);
	}
	
	public void customValidate(){
		switch(this.stampRequestMode){
			case STAMP_GO_OUT_PERMIT: {
				for(AppStampGoOutPermit appStampGoOutPermit : this.appStampGoOutPermits){
					/*打刻申請詳細.打刻分類＝外出のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampGoOutPermit.getStampAtr().equals(AppStampAtr.GO_OUT)&&
							(appStampGoOutPermit.getStartTime() == null ||
							Strings.isEmpty(appStampGoOutPermit.getStartLocation()) || 
							appStampGoOutPermit.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
	
					/*打刻申請詳細.打刻分類＝育児 または 介護のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 終了時刻*/
					if((appStampGoOutPermit.getStampAtr().equals(AppStampAtr.CHILDCARE))&&
							(appStampGoOutPermit.getStartTime() == null ||
							appStampGoOutPermit.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					if((appStampGoOutPermit.getStampAtr().equals(AppStampAtr.CARE))&&
							(appStampGoOutPermit.getStartTime() == null ||
							appStampGoOutPermit.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					
					// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
					if(appStampGoOutPermit.getStartTime().greaterThanOrEqualTo(appStampGoOutPermit.getEndTime())){
						throw new BusinessException("Msg_307");
					}
				}
				break;
			}
			
			case STAMP_ADDITIONAL: {
				for(AppStampWork appStampWork : this.appStampWorks) {
					/*打刻申請詳細.打刻分類＝出勤／退勤 または 臨時のとき、すべての出退勤申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.ATTENDANCE)&&
							(appStampWork.getStartTime() == null ||
							Strings.isEmpty(appStampWork.getStartLocation()) || 
							appStampWork.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					
					// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
					if(appStampWork.getStartTime().greaterThanOrEqualTo(appStampWork.getEndTime())){
						throw new BusinessException("Msg_307");
					}
				}
				break;
			}
			
			case STAMP_CANCEL: {
				// すべての打刻取消申請が実績取消＝するしない区分.しない (#Msg_321#)
				Boolean allFalse = this.appStampCancels.stream().allMatch(item -> item.getCancelAtr()==0);
				if(allFalse){
					throw new BusinessException("Msg_321");
				}
				break;
			}
			
			case STAMP_ONLINE_RECORD: {
				// do nothing
				break;
			}
			
			case OTHER: {
				for(AppStampWork appStampWork : this.appStampWorks) {
					/*打刻申請詳細.打刻分類＝出勤／退勤 または 臨時のとき、すべての出退勤申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.ATTENDANCE)&&
							(appStampWork.getStartTime() == null ||
							Strings.isEmpty(appStampWork.getStartLocation()) || 
							appStampWork.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					
					/*打刻申請詳細.打刻分類＝外出のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.GO_OUT)&&
							(appStampWork.getStartTime() == null ||
							Strings.isEmpty(appStampWork.getStartLocation()) || 
							appStampWork.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
	
					/*打刻申請詳細.打刻分類＝育児 または 介護のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 終了時刻*/
					if((appStampWork.getStampAtr().equals(AppStampAtr.CHILDCARE))&&
							(appStampWork.getStartTime() == null ||
									appStampWork.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					if((appStampWork.getStampAtr().equals(AppStampAtr.CARE))&&
							(appStampWork.getStartTime() == null ||
									appStampWork.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					
					/*打刻申請詳細.打刻分類＝応援のとき、すべての出退勤申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 終了時刻
					- 応援カード*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.SUPPORT)&&
							(appStampWork.getStartTime() == null ||
							Strings.isBlank(appStampWork.getSupportCard()) || 
							appStampWork.getEndTime() == null )){
								throw new BusinessException("Msg_308");
					}
					
					// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
					if(appStampWork.getStartTime().greaterThanOrEqualTo(appStampWork.getEndTime())){
						throw new BusinessException("Msg_307");
					}
				}
				break;
			}
			
			default: {
				// do nothing
				break;
			}
		
		}
	}
}
