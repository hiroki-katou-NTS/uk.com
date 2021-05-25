package nts.uk.screen.at.app.ksu001.aggreratepersonaltotal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggreratePersonalDto {
	
	public Map<String, EstimatedSalaryDto> estimatedSalary = new HashMap<String, EstimatedSalaryDto>();
	
	public Map<String, Map<TotalTimesDto, BigDecimal>> timeCount = new HashMap<String, Map<TotalTimesDto, BigDecimal>>();
	
	public Map<String, Map<Integer, BigDecimal>> workHours = new HashMap<String, Map<Integer, BigDecimal>>();
	
}
