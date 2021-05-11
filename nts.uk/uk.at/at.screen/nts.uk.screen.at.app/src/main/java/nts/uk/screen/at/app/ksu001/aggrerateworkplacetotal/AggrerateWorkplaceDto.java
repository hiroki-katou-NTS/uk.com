package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesDetailDto;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

@AllArgsConstructor
@NoArgsConstructor
public class AggrerateWorkplaceDto {

	public Map<GeneralDate, Map<LaborCostAggregationUnitDto, BigDecimal>> laborCostAndTime;
	
	public Map<EmployeeId, Map<TotalTimesDetailDto, BigDecimal>> timeCount;
	
	public AggrerateNumberPeopleDto aggrerateNumberPeople;
	
	public Map<GeneralDate, Map<ExternalBudgetDto, Integer>> externalBudget;
}
