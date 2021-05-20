package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.find.budget.external.ExternalBudgetDto;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesDetailDto;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AggrerateWorkplaceDto {

	// Map<年月日, Map<職場計の人件費の集計単位, BigDecimal>>
	public Map<GeneralDate, Map<LaborCostAggregationUnitDto, BigDecimal>> laborCostAndTime;
	
	// Map<年月日, Map<回数集計, BigDecimal>>
	public Map<String, Map<TotalTimesDetailDto, BigDecimal>> timeCount;
	
	// Map<年月日, Map<(雇用マスタ or 分類マスタ or 職位情報), BigDecimal>>
	public AggrerateNumberPeopleDto aggrerateNumberPeople;
	
	// Map<年月日, Map<外部予算実績項目, 外部予算実績値>>
	public Map<GeneralDate, Map<ExternalBudgetDto, String>> externalBudget;
}
