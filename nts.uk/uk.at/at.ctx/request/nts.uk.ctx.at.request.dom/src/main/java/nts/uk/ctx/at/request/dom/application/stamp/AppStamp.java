package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.shr.com.time.TimeWithDayAttr;
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
@Getter
@AllArgsConstructor
@Builder
public class AppStamp extends AggregateRoot {
	
	private StampRequestMode stampRequestMode;
	
	@Setter
	private Application_New application_New;
	
	private List<AppStampGoOutPermit> appStampGoOutPermits;
	
	private List<AppStampWork> appStampWorks;
	
	private List<AppStampCancel> appStampCancels;
	
	private Optional<AppStampOnlineRecord> appStampOnlineRecord;
	
	public static AppStamp createGoOutPermitStamp(String companyID, GeneralDate appDate, String employeeID, AppReason appReason, List<AppStampGoOutPermit> appStampGoOutPermits){
		return new AppStamp(
				StampRequestMode.STAMP_GO_OUT_PERMIT, 
				Application_New.firstCreate(companyID, PrePostAtr.PREDICT, appDate, ApplicationType.STAMP_APPLICATION, employeeID, appReason), 
				appStampGoOutPermits, 
				Collections.emptyList(), 
				Collections.emptyList(), 
				Optional.empty());
	}
	
	public static AppStamp createWorkStamp(String companyID, GeneralDate appDate, String employeeID, AppReason appReason, List<AppStampWork> appStampWorks){
		return new AppStamp(
				StampRequestMode.STAMP_WORK, 
				Application_New.firstCreate(companyID, PrePostAtr.POSTERIOR, appDate, ApplicationType.STAMP_APPLICATION, employeeID, appReason), 
				Collections.emptyList(), 
				appStampWorks, 
				Collections.emptyList(), 
				Optional.empty());
	}
	
	public static AppStamp createCancelStamp(String companyID, GeneralDate appDate, String employeeID, AppReason appReason, List<AppStampCancel> appStampCancels){
		return new AppStamp(
				StampRequestMode.STAMP_CANCEL, 
				Application_New.firstCreate(companyID, PrePostAtr.POSTERIOR, appDate, ApplicationType.STAMP_APPLICATION, employeeID, appReason), 
				Collections.emptyList(), 
				Collections.emptyList(), 
				appStampCancels, 
				Optional.empty());
	}
	
	public static AppStamp createOnlineRecordStamp(String companyID, GeneralDate appDate, String employeeID, AppReason appReason, Optional<AppStampOnlineRecord> appStampOnlineRecord){
		return new AppStamp(
				StampRequestMode.STAMP_ONLINE_RECORD, 
				Application_New.firstCreate(companyID, PrePostAtr.POSTERIOR, appDate, ApplicationType.STAMP_APPLICATION, employeeID, appReason), 
				Collections.emptyList(), 
				Collections.emptyList(), 
				Collections.emptyList(), 
				appStampOnlineRecord);
	}
	
	public static AppStamp createOtherStamp(String companyID, GeneralDate appDate, String employeeID, AppReason appReason, List<AppStampWork> appStampWorks){
		return new AppStamp(
				StampRequestMode.OTHER, 
				Application_New.firstCreate(companyID, PrePostAtr.POSTERIOR, appDate, ApplicationType.STAMP_APPLICATION, employeeID, appReason), 
				Collections.emptyList(), 
				appStampWorks, 
				Collections.emptyList(), 
				Optional.empty());
	}
	
	public void customValidate(DisplayAtr stampPlaceDisp){
		switch(this.stampRequestMode){
			case STAMP_GO_OUT_PERMIT: {
				for(AppStampGoOutPermit appStampGoOutPermit : this.appStampGoOutPermits){
					/*打刻申請詳細.打刻分類＝外出のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampGoOutPermit.getStampAtr().equals(AppStampAtr.GO_OUT)&&
							(!appStampGoOutPermit.getStartTime().isPresent() ||
							(stampPlaceDisp==DisplayAtr.DISPLAY && !appStampGoOutPermit.getStartLocation().isPresent()) || 
							!appStampGoOutPermit.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
	
					/*打刻申請詳細.打刻分類＝育児 または 介護のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 終了時刻*/
					if((appStampGoOutPermit.getStampAtr().equals(AppStampAtr.CHILDCARE))&&
							(!appStampGoOutPermit.getStartTime().isPresent()||
							!appStampGoOutPermit.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					if((appStampGoOutPermit.getStampAtr().equals(AppStampAtr.CARE))&&
							(!appStampGoOutPermit.getStartTime().isPresent() ||
							!appStampGoOutPermit.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					
					// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
//					if(appStampGoOutPermit.getStartTime().get().greaterThanOrEqualTo(appStampGoOutPermit.getEndTime().get())){
//						throw new BusinessException("Msg_307");
//					}
				}
				checkListTimeForAppStampGoOutPermit(this.appStampGoOutPermits);
				break;
			}
			
			case STAMP_WORK: {
				for(AppStampWork appStampWork : this.appStampWorks) {
					/*打刻申請詳細.打刻分類＝出勤／退勤 または 臨時のとき、すべての出退勤申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.ATTENDANCE)&&
							(!appStampWork.getStartTime().isPresent() ||
							(stampPlaceDisp==DisplayAtr.DISPLAY && !appStampWork.getStartLocation().isPresent()) || 
							!appStampWork.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					
					// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
//					if(appStampWork.getStartTime().get().greaterThanOrEqualTo(appStampWork.getEndTime().get())){
//						throw new BusinessException("Msg_307");
//					}
				}
				checkListTime(this.appStampWorks);
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
							(!appStampWork.getStartTime().isPresent() ||
							(stampPlaceDisp==DisplayAtr.DISPLAY && !appStampWork.getStartLocation().isPresent()) || 
							!appStampWork.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					
					/*打刻申請詳細.打刻分類＝外出のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 開始場所
					- 終了時刻*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.GO_OUT)&&
							(!appStampWork.getStartTime().isPresent() ||
							(stampPlaceDisp==DisplayAtr.DISPLAY && !appStampWork.getStartLocation().isPresent()) || 
							!appStampWork.getEndTime().isPresent())){
								throw new BusinessException("Msg_308");
					}
	
					/*打刻申請詳細.打刻分類＝育児 または 介護のとき、すべての外出許可申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 終了時刻*/
					if((appStampWork.getStampAtr().equals(AppStampAtr.CHILDCARE))&&
							(!appStampWork.getStartTime().isPresent() ||
									!appStampWork.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					if((appStampWork.getStampAtr().equals(AppStampAtr.CARE))&&
							(!appStampWork.getStartTime().isPresent() ||
									!appStampWork.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					
					/*打刻申請詳細.打刻分類＝応援のとき、すべての出退勤申請が以下のいずれも設定されていない (#Msg_308#)
					- 開始時刻
					- 終了時刻
					- 応援カード*/
					if(appStampWork.getStampAtr().equals(AppStampAtr.SUPPORT)&&
							(!appStampWork.getStartTime().isPresent() ||
							!appStampWork.getSupportCard().isPresent() || 
							!appStampWork.getEndTime().isPresent() )){
								throw new BusinessException("Msg_308");
					}
					
					// 開始時刻と終了時刻がともに設定されているとき、開始時刻≧終了時刻 (#Msg_307#)
//					if(appStampWork.getStartTime().get().greaterThanOrEqualTo(appStampWork.getEndTime().get())){
//						throw new BusinessException("Msg_307");
//					}
				}
				checkListTime(this.appStampWorks);
				break;
			}
			
			default: {
				// do nothing
				break;
			}
		
		}
	}
	private void checkListTime(List<AppStampWork> appStampWork){
		int count = appStampWork.size() - 1;
			for(int i = 0; i < appStampWork.size(); i++){
				this.checkTime(appStampWork.get(i).getStartTime().get(), appStampWork.get(i).getEndTime().get());
				if(i != count){
					TimeWithDayAttr startTime = appStampWork.get(i+1).getStartTime().get();
					this.checkTime(appStampWork.get(i).getEndTime().get(), startTime);
				}
			}
	}
	private void checkListTimeForAppStampGoOutPermit(List<AppStampGoOutPermit> appStampWork){
		int count = appStampWork.size() - 1;
			for(int i = 0; i < appStampWork.size(); i++){
				this.checkTime(appStampWork.get(i).getStartTime().get(), appStampWork.get(i).getEndTime().get());
				if(i != count){
					TimeWithDayAttr startTime = appStampWork.get(i+1).getStartTime().get();
					this.checkTime(appStampWork.get(i).getEndTime().get(), startTime);
				}
			}
	}
	private void checkTime(TimeWithDayAttr startTime, TimeWithDayAttr endTime){
		if(startTime.greaterThanOrEqualTo(endTime)){
			throw new BusinessException("Msg_307");
		}
	}
}
