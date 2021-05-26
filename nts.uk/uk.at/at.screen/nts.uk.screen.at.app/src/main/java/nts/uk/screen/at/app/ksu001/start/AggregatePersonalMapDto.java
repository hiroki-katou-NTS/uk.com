package nts.uk.screen.at.app.ksu001.start;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggregatePersonalMapDto {

	public List<EstimatedSalaryMapDto> estimatedSalary = Collections.emptyList();
	
	public List<TotalTimesMapDtoList> timeCount = Collections.emptyList();
	
	public List<WorkMapDtoList> workHours = Collections.emptyList();
}
