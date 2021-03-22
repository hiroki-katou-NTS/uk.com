package nts.uk.screen.at.app.ksus01.a;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TimeLeavingOfDailyAttdDto {

	private List<TimeLeavingWorkDto> timeLeavingWorks;
	
	private int workTimes;
}
