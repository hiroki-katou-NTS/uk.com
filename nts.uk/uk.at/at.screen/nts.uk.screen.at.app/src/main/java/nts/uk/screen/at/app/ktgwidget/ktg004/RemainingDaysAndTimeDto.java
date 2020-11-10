package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author thanhpv
 * @name 残日数と残時間
 */
@AllArgsConstructor
@Getter
public class RemainingDaysAndTimeDto {

	//日数
	private double day;
	
	//時間
	private AttendanceTime time;
}
