package nts.uk.ctx.at.shared.dom.scherec.application.holidayworktime;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.overtime.ApplicationTimeShare;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
 * @author thanhnx
 * 
 * 休日出勤申請(反映用)
 * 
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
		this.setOpStampRequestMode(application.getOpStampRequestMode());
		this.setOpAppStartDate(application.getOpAppStartDate());
		this.setOpAppEndDate(application.getOpAppEndDate());
	}


	public AppHolidayWorkShare(ApplicationShare application, WorkInformation workInformation,
			ApplicationTimeShare applicationTime, boolean backHomeAtr, boolean goWorkAtr,
			List<TimeZoneWithWorkNo> breakTimeList, List<TimeZoneWithWorkNo> workingTimeList) {
		super(application);
		this.workInformation = workInformation;
		this.applicationTime = applicationTime;
		this.backHomeAtr = backHomeAtr ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.goWorkAtr = goWorkAtr ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.breakTimeList = breakTimeList;
		this.workingTimeList = workingTimeList;
	}

}
