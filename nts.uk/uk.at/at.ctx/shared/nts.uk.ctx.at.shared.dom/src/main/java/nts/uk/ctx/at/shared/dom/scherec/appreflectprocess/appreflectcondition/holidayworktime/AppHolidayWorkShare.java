package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.holidayworktime;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.AppOvertimeDetailShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime.ApplicationTimeShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
 * 休日出勤申請
 * @author thanhnx
 */
@NoArgsConstructor
@Getter
@Setter
public class AppHolidayWorkShare extends ApplicationShare{
	
	/**
	 * 勤務情報
	 */
	private WorkInformation workInformation;
	
	/**
	 * 申請時間
	 */
	private ApplicationTimeShare applicationTime;
	
	/**
	 * 直帰区分
	 */
	private NotUseAtr backHomeAtr;
	
	/**
	 * 直行区分
	 */
	private NotUseAtr goWorkAtr;
	
	/**
	 * 休憩時間帯
	 */
	private List<TimeZoneWithWorkNo> breakTimeList;
	
	/**
	 * 勤務時間帯
	 */
	private List<TimeZoneWithWorkNo> workingTimeList;
	
	/**
	 * 時間外時間の詳細
	 */
	private Optional<AppOvertimeDetailShare> appOvertimeDetail = Optional.empty();
	
	public AppHolidayWorkShare(ApplicationShare application) {
		super(application);
	}
	
	
	public void setApplication(ApplicationShare application) {
		this.setVersion(application.getVersion());
		this.setAppID(application.getAppID());
		this.setPrePostAtr(application.getPrePostAtr());
		this.setEmployeeID(application.getEmployeeID());
		this.setAppType(application.getAppType());
		this.setAppDate(application.getAppDate());
		this.setEnteredPersonID(application.getEnteredPersonID());
		this.setInputDate(application.getInputDate());
		this.setReflectionStatus(application.getReflectionStatus());
		this.setOpStampRequestMode(application.getOpStampRequestMode());
		this.setOpReversionReason(application.getOpReversionReason());
		this.setOpAppStartDate(application.getOpAppStartDate());
		this.setOpAppEndDate(application.getOpAppEndDate());
		this.setOpAppReason(application.getOpAppReason());
		this.setOpAppStandardReasonCD(application.getOpAppStandardReasonCD());
	}


	public AppHolidayWorkShare(ApplicationShare application, WorkInformation workInformation,
			ApplicationTimeShare applicationTime, boolean backHomeAtr, boolean goWorkAtr,
			List<TimeZoneWithWorkNo> breakTimeList, List<TimeZoneWithWorkNo> workingTimeList,
			Optional<AppOvertimeDetailShare> appOvertimeDetail) {
		super(application);
		this.workInformation = workInformation;
		this.applicationTime = applicationTime;
		this.backHomeAtr = backHomeAtr ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.goWorkAtr = goWorkAtr ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.breakTimeList = breakTimeList;
		this.workingTimeList = workingTimeList;
		this.appOvertimeDetail = appOvertimeDetail;
	}

}
