package nts.uk.ctx.at.shared.dom.worktime;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;

/**　<<Event>> 実績の出退勤が変更された　*/
@Builder
@Getter
public class TimeLeaveChangeEvent extends DomainEvent {

	/** 年月日: 年月日 */
	private String employeeId;

	/** 社員ID: 社員ID */
	private GeneralDate targetDate;

	/** 変更された出退勤: 出退勤 */
	private List<TimeLeavingWork> timeLeave;

}
