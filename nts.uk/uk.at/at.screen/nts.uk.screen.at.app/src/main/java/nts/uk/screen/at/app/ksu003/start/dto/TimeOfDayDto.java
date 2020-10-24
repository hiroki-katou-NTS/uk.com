package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;

/**
 * 時刻(日区分付き)
 * @author phongtq
 *
 */
@Value
public class TimeOfDayDto {
	//日区分   : DayDivision
	private int dayDivision;
	//時刻
	private int time;
}
