package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author thanhpv
 * @name 残日数と残時間
 */
@NoArgsConstructor
@Getter
@Setter
public class RemainingDaysAndTimeDto {

	//日数
	private double day = 0.0;
	
	//時間
	private String time = "00:00";

	public RemainingDaysAndTimeDto(double day, AttendanceTime time) {
		super();
		this.day = day;
		this.time = time.toString();
	}
	
}
