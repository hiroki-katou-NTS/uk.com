package nts.uk.ctx.at.record.dom.dailyprocess.calc.valueobject;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 就業時間
 * 就業時間（法定内用）の計算の戻り値として利用するクラス
 * @author ken_takasu
 *
 */
@Getter
public class WorkHour {
	//就業時間
	private AttendanceTime workTime;
	//休暇加算時間
	private AttendanceTime vacationAddTime;
	
	public WorkHour(AttendanceTime workTime,AttendanceTime vacationAddTime) {
		this.workTime = workTime;
		this.vacationAddTime = vacationAddTime;
	}
	
}
