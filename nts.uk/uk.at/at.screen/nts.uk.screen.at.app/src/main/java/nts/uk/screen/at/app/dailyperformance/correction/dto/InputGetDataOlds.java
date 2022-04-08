package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InputGetDataOlds {
	
	List<String> employeeIds;
	
	DateRange dateRange;
	
	Integer displayFormat ;
	
	List<DPErrorDto> dPErrorDto;
	
}
