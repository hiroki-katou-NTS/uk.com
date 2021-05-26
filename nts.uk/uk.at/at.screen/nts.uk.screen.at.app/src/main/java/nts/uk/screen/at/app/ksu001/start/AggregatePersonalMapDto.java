package nts.uk.screen.at.app.ksu001.start;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.ksu001.aggreratepersonaltotal.AggreratePersonalDto;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggregatePersonalMapDto {

	public List<EstimatedSalaryMapDto> estimatedSalary = Collections.emptyList();
	
	public List<TotalTimesMapDtoList> timeCount = Collections.emptyList();
	
	public List<WorkMapDtoList> workHours = Collections.emptyList();
	
	
	public static AggregatePersonalMapDto convertMap(AggreratePersonalDto dto) {
		return new AggregatePersonalMapDto(
				dto.convertEstimatedSalary(),
				dto.convertTimeCount(),
				dto.convertWorkhours()
				);
	}
}
