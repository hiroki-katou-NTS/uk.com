package nts.uk.screen.at.app.ksus02;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.kdl045.query.WorkAvailabilityOfOneDayDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDisplayInforOuput {
	private List<WorkAvailabilityOfOneDayDto> listWorkAvai = new ArrayList<>();
	
	private List<String> listDateHoliday  = new ArrayList<>();
}
