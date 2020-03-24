package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;
/**
 * 1日の出退勤
 * @author tutk
 *
 */

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;

public class AttendanceOneDay {

	@Getter
	private final GeneralDate date;
	@Getter
	private final Optional<TimeActualStamp> attendance1;
	@Getter
	private final Optional<TimeActualStamp> leavingStamp1;
	@Getter
	private final Optional<TimeActualStamp> attendance2;
	@Getter
	private final Optional<TimeActualStamp> leavingStamp2;

	public AttendanceOneDay(GeneralDate date, Optional<TimeActualStamp> attendance1,
			Optional<TimeActualStamp> leavingStamp1, Optional<TimeActualStamp> attendance2,
			Optional<TimeActualStamp> leavingStamp2) {
		super();
		this.date = date;
		this.attendance1 = attendance1;
		this.leavingStamp1 = leavingStamp1;
		this.attendance2 = attendance2;
		this.leavingStamp2 = leavingStamp2;
	}

}
