package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;
/**
 * 1日の出退勤
 * @author tutk
 *
 */

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;

public class AttendanceOneDay {

	@Getter
	private final GeneralDate date;
	@Getter
	private final TimeActualStamp attendance1;
	@Getter
	private final TimeActualStamp leavingStamp1;
	@Getter
	private final TimeActualStamp attendance2;
	@Getter
	private final TimeActualStamp leavingStamp2;
	
	public AttendanceOneDay(GeneralDate date, TimeActualStamp attendance1, TimeActualStamp leavingStamp1,
			TimeActualStamp attendance2, TimeActualStamp leavingStamp2) {
		super();
		this.date = date;
		this.attendance1 = attendance1;
		this.leavingStamp1 = leavingStamp1;
		this.attendance2 = attendance2;
		this.leavingStamp2 = leavingStamp2;
	}
	
	

}
