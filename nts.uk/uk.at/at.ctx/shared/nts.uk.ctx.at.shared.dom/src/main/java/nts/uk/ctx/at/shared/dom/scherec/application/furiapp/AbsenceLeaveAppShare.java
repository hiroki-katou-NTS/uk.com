package nts.uk.ctx.at.shared.dom.scherec.application.furiapp;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @name 振休申請
 * @author ThanhNX
 */
@Getter
public class AbsenceLeaveAppShare extends ApplicationForHolidaysShare {

	/** 勤務時間帯 */
	private List<TimeZoneWithWorkNo> workingHours;

	/** 勤務情報 */
	private WorkInformation workInformation;

	/** するしない区分 */
	private NotUseAtr workChangeUse;

	/** 変更元の振休日 */
	private Optional<GeneralDate> changeSourceHoliday;

	public AbsenceLeaveAppShare(List<TimeZoneWithWorkNo> workingHours, WorkInformation workInformation,
			NotUseAtr workChangeUse, Optional<GeneralDate> changeSourceHoliday,
			TypeApplicationHolidaysShare typeApplicationHolidays, ApplicationShare application) {
		super(typeApplicationHolidays, application);
		this.workingHours = workingHours;
		this.workInformation = workInformation;
		this.workChangeUse = workChangeUse;
		this.changeSourceHoliday = changeSourceHoliday;
	}

	public Optional<TimeZoneWithWorkNo> getWorkTime(WorkNo workNo) {
		return this.workingHours.stream().filter(c -> c.getWorkNo().v() == workNo.v()).findFirst();
	}

}
