package nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayshipment.ApplicationForHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;

/**
 * 振出申請
 * @author ThanhPV
 */
@Getter
public class RecruitmentApp extends ApplicationForHolidays {
	
	/** 勤務情報 */
	private WorkInformation workInformation;
	
	/** 勤務時間帯 */
	private List<TimeZoneWithWorkNo> workingHours;

	public RecruitmentApp(WorkInformation workInformation, List<TimeZoneWithWorkNo> workingHours, TypeApplicationHolidays typeApplicationHolidays, Application application) {
		super(typeApplicationHolidays, application);
		this.workInformation = workInformation;
		this.workingHours = workingHours;
	}
	
	public Optional<TimeZoneWithWorkNo> getWorkTime(WorkNo workNo) {
		return this.workingHours.stream().filter(c->c.getWorkNo().v() == workNo.v()).findFirst();
	}
	
}
