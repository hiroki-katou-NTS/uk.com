package nts.uk.screen.at.app.kdl045.query;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.workrule.workinghours.TimeZoneDto;

/**
 * 時間休暇 : dto 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeVacationDto {
	private List<TimeZoneDto> timeZone = new ArrayList<>();
	
	private List<DailyAttdTimeVacationDto> usageTime = new ArrayList<>();

	public TimeVacationDto(List<TimeZoneDto> timeZone, List<DailyAttdTimeVacationDto> usageTime) {
		super();
		this.timeZone = timeZone;
		this.usageTime = usageTime;
	}
}
