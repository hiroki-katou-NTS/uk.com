package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;

/**
 * 残業休出申請
 * 
 * @author thanhnx
 *
 */
@AllArgsConstructor
@Getter
@Setter
// 残業申請
public class AppOverTimeShare extends ApplicationShare {
	// 残業区分
	private OvertimeAppAtrShare overTimeClf;

	// 申請時間
	private ApplicationTimeShare applicationTime;

	// 休憩時間帯
	private List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();

	// 勤務時間帯
	private List<TimeZoneWithWorkNo> workHoursOp = new ArrayList<>();

	// 勤務情報
	private Optional<WorkInformation> workInfoOp = Optional.empty();

	// 時間外時間の詳細
	private Optional<AppOvertimeDetailShare> detailOverTimeOp = Optional.empty();

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
}
