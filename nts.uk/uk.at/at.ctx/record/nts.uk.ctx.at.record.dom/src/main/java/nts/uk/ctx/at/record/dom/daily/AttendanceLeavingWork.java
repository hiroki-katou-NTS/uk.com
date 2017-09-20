package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;

/**
 * 出退勤
 * @author keisuke_hoshina
 *
 */
@Value
public class AttendanceLeavingWork {
	private WorkStampWithActualStamp attendance;
	private WorkStampWithActualStamp leaveWork;
	private int workNo;
}
