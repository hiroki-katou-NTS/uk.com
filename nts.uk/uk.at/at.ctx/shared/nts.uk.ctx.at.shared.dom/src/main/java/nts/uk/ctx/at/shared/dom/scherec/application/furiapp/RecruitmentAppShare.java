package nts.uk.ctx.at.shared.dom.scherec.application.furiapp;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * 振出申請
 * @author ThanhNX
 */
@Getter
public class RecruitmentAppShare extends ApplicationForHolidaysShare {
	
	/** 勤務情報 */
	private WorkInformation workInformation;
	
	/** 勤務時間帯 */
	private List<TimeZoneWithWorkNo> workingHours;

	public RecruitmentAppShare(WorkInformation workInformation, List<TimeZoneWithWorkNo> workingHours, TypeApplicationHolidaysShare typeApplicationHolidays, ApplicationShare application) {
		super(typeApplicationHolidays, application);
		this.workInformation = workInformation;
		this.workingHours = workingHours;
	}
	
	public Optional<TimeZoneWithWorkNo> getWorkTime(WorkNo workNo) {
		return this.workingHours.stream().filter(c->c.getWorkNo().v() == workNo.v()).findFirst();
	}
	
	/** ドメインモデル「振出申請」の事前条件をチェックする */
	public void validateApp() {
		for (TimeZoneWithWorkNo timeZoneWithWorkNo : this.workingHours) {
			timeZoneWithWorkNo.validate();
		}
	}
	
}
