package nts.uk.screen.at.app.ksus01.b;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksus01.a.WorkScheduleDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InforInitialInput {
	
	private List<WorkScheduleDto> listWorkSchedule;
	
	private int baseYM;
	
	private int closingDay;
}
