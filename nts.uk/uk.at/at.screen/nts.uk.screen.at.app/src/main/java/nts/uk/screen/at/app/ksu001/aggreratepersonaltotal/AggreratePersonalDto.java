package nts.uk.screen.at.app.ksu001.aggreratepersonaltotal;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggreratePersonalDto {
	
	public Map<String, EstimatedSalaryDto> estimatedSalary;
	
	public Map<String, Map<Integer, BigDecimal>> timeCount;
	
}
