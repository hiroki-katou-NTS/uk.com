package nts.uk.screen.at.app.command.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@AllArgsConstructor
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
