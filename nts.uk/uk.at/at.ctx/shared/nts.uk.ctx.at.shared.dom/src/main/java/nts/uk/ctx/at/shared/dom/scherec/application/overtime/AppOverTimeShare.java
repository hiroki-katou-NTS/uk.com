package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

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
 * @author thanhnx
 * 
 * 残業申請(反映用)
 * 
 */
@AllArgsConstructor
@Getter
@Setter
public class AppOverTimeShare extends ApplicationShare {

	// 申請時間
	private ApplicationTimeShare applicationTime;

	// 休憩時間帯
	private List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<>();

	// 勤務時間帯
	private List<TimeZoneWithWorkNo> workHoursOp = new ArrayList<>();

	// 勤務情報
	private Optional<WorkInformation> workInfoOp = Optional.empty();

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
}
