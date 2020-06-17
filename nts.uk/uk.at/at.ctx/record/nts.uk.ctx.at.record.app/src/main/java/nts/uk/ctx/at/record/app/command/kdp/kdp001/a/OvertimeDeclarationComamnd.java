package nts.uk.ctx.at.record.app.command.kdp.kdp001.a;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;

/**
 * 
 * @author sonnlb
 *
 */
public class OvertimeDeclarationComamnd {
	@Getter
	private Integer overTime;

	/**
	 * 時間外深夜時間
	 */
	@Getter
	private Integer overLateNightTime;

	public OvertimeDeclaration toDomainValue() {
		return new OvertimeDeclaration(new AttendanceTime(overTime), new AttendanceTime(overLateNightTime));
	}
}
