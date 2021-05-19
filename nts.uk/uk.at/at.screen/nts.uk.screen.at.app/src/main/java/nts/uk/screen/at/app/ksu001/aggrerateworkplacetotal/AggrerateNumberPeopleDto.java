package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.dailyperformance.correction.dto.EmploymentDto;
/**
 * 
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class AggrerateNumberPeopleDto {

	public Map<GeneralDate, Map<EmploymentDto, BigDecimal>> employment;
	
	public Map<GeneralDate, Map<ClassificationDto, BigDecimal>> classification;

	public Map<GeneralDate, Map<JobTitleInfoDto, BigDecimal>> jobTitleInfo;
}
