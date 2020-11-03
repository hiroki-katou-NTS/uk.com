package nts.uk.ctx.at.shared.app.workrule.workinghours;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 時刻(日区分付き) : Dto
 * @author tutk
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeZoneDto {
	/**
	 * 開始時刻 
	 */
	private TimeOfDayDto startTime;
	
	/**
	 * 終了時刻
	 */
	private TimeOfDayDto endTime;

}
