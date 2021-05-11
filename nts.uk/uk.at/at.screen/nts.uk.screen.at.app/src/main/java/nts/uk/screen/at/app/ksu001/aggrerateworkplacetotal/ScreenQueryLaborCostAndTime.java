package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkplaborcostandtime.WkpLaborCostAndTimeFinder;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.CountLaborCostTimeService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.workplacecounter.LaborCostAggregationUnit;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 人件費・時間を集計する
 * @author hoangnd
 *
 */
@Stateless
public class ScreenQueryLaborCostAndTime {
	
	@Inject
	private WkpLaborCostAndTimeFinder wkpLaborCostAndTimeFinder;
	
	@Injectable
	private CountLaborCostTimeService.Require require;
	
	public Map<GeneralDate, Map<LaborCostAggregationUnitDto, BigDecimal>> aggrerate(
			TargetOrgIdenInfor targetOrg,
			List<IntegrationOfDaily> aggrerateintegrationOfDaily,
			DatePeriod datePeriod
			) {
		Map<GeneralDate, Map<LaborCostAggregationUnitDto, BigDecimal>> output = null;
		// 1: 取得する(会社ID)
		
		Optional<WorkplaceCounterLaborCostAndTime> wkpLaborCostAndTime = Optional.empty();
		
		// 2: 集計する(Require, 対象組織識別情報, 期間, Map<人件費・時間の集計単位, 人件費・時間>, List<日別勤怠(Work)>)
		if (wkpLaborCostAndTime.isPresent()) {
			Map<GeneralDate, Map<LaborCostAggregationUnit, BigDecimal>> tallies = CountLaborCostTimeService.aggregate(
					require,
					targetOrg,
					datePeriod,
					wkpLaborCostAndTime.get().getLaborCostAndTimeList(),
					aggrerateintegrationOfDaily);
			
			output = tallies.entrySet()
				   .stream()
				   .collect(Collectors.toMap(
						   e -> ((GeneralDate)e.getKey()),
						   e -> ((Map<LaborCostAggregationUnit, BigDecimal>)e.getValue())
						   			.entrySet()
						   			.stream()
						   			.collect(Collectors.toMap(
						   					z -> LaborCostAggregationUnitDto.fromDomain((LaborCostAggregationUnit)z.getKey()),
						   					z -> z.getValue()))
						   
						   ));
		}
		
		return output;
	}
	

}
