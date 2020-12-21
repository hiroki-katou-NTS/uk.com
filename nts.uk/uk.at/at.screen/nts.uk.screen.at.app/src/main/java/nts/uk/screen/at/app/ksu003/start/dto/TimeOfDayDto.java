package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 時刻(日区分付き)
 * @author phongtq
 *
 */
@Value
@AllArgsConstructor
public class TimeOfDayDto {
	//日区分   : DayDivision
	public int dayDivision;
	//時刻
	public int time;
}
