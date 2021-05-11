package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
/**
 * 
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class AggrerateNumberPeopleDto {

	public Map<GeneralDate, Map<Employment, BigDecimal>> employment;
	
	public Map<GeneralDate, Map<Classification, BigDecimal>> classification;

	public Map<GeneralDate, Map<JobTitleInfo, BigDecimal>> jobTitleInfo;
}
