package nts.uk.screen.at.app.ksu001.aggreratedinformation;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.ksu001.aggrerateschedule.AggrerateScheduleDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggreratedInformationDto {

	public Map<GeneralDate, Map<String, String>> externalBudget;
	
	public AggrerateScheduleDto aggrerateSchedule;
	
	
}
