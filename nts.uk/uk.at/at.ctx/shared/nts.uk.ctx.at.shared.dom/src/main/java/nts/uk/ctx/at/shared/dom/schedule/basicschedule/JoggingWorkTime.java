package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoggingWorkTime {
	// 勤怠時間
	AttendanceTime time;

	// 補正区分
	CollectionAtr atr;
}
