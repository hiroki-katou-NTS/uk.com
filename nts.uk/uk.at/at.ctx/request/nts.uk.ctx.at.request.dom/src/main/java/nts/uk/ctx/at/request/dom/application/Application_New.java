package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
/**
 * 申請
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
@Builder
public class Application_New extends DomainObject {
	@Setter
	private Long version;
	
	private String companyID; 
	
	// 申請ID
	private String appID;
	@Setter
	// 事前事後区分
	private PrePostAtr_Old prePostAtr;
	
	// 入力日
	private GeneralDateTime inputDate;
	
	// 入力者
	@Setter
	private String enteredPersonID;
	
	// 差戻し理由
	@Setter
	private AppReason reversionReason;
	
	// 申請日
	@Setter
	private GeneralDate appDate;
	
	// 申請理由
	@Setter
	private AppReason appReason;
	
	// 申請種類
	private ApplicationType_Old appType;
	
	// 申請者
	@Setter
	private String employeeID;
	
	// 申請終了日
	@Setter
	private Optional<GeneralDate> startDate;
	
	// 申請開始日
	@Setter
	private Optional<GeneralDate> endDate;
	
	// 反映情報
	private ReflectionInformation_New reflectionInformation;
	
	public static Application_New firstCreate(String companyID, PrePostAtr_Old prePostAtr, GeneralDate appDate, ApplicationType_Old appType, String employeeID, AppReason appReason){
		return Application_New.builder()
				.version(0L)
				.companyID(companyID)
				.appID(IdentifierUtil.randomUniqueId())
				.prePostAtr(prePostAtr)
				.inputDate(GeneralDateTime.now())
				.enteredPersonID(employeeID)
				.reversionReason(new AppReason(""))
				.appDate(appDate)
				.appReason(appReason)
				.appType(appType)
				.employeeID(employeeID)
				.startDate(Optional.ofNullable(appDate))
				.endDate(Optional.ofNullable(appDate))
				.reflectionInformation(ReflectionInformation_New.firstCreate())
				.build();
	}
	
	public boolean isAppOverTime(){
		return this.appType  == ApplicationType_Old.OVER_TIME_APPLICATION;
	}
	public boolean isAppGoBack(){
		return this.appType  == ApplicationType_Old.GO_RETURN_DIRECTLY_APPLICATION;
	}
	public boolean isAppHdWork(){
		return this.appType  == ApplicationType_Old.BREAK_TIME_APPLICATION;
	}
	public boolean isAppWkChange(){
		return this.appType  == ApplicationType_Old.WORK_CHANGE_APPLICATION;
	}
	public boolean isAppAbsence(){
		return this.appType  == ApplicationType_Old.ABSENCE_APPLICATION;
	}
	public boolean isAppCompltLeave(){
		return this.appType  == ApplicationType_Old.COMPLEMENT_LEAVE_APPLICATION;
	}
	public boolean isAppBusinessTrip(){
		return this.appType  == ApplicationType_Old.BUSINESS_TRIP_APPLICATION;
	}
	public boolean isAppStemApp(){
		return this.appType  == ApplicationType_Old.STAMP_APPLICATION;
	}
	public boolean isAnnualHolidayApp(){
		return this.appType  == ApplicationType_Old.ANNUAL_HOLIDAY_APPLICATION;
	}
	public boolean isAppEarlyLeaveCancel(){
		return this.appType  == ApplicationType_Old.EARLY_LEAVE_CANCEL_APPLICATION;
	}
	public boolean isAppStempNrApplication(){
		return this.appType  == ApplicationType_Old.STAMP_NR_APPLICATION;
	}
	public boolean isAppLongBusinessTrip(){
		return this.appType  == ApplicationType_Old.LONG_BUSINESS_TRIP_APPLICATION;
	}
	public boolean isAppBusinessTripAppHelper(){
		return this.appType  == ApplicationType_Old.BUSINESS_TRIP_APPLICATION_OFFICE_HELPER;
	}
	public boolean isApp_36(){
		return this.appType  == ApplicationType_Old.APPLICATION_36;
	}
}
