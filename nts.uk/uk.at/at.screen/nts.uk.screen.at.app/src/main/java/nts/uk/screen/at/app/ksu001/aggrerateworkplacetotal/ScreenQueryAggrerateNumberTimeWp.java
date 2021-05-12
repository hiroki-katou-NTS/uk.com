package nts.uk.screen.at.app.ksu001.aggrerateworkplacetotal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.assertj.core.util.Arrays;

import mockit.Injectable;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.screen.at.app.kml002.screenG.CountInfoDto;
import nts.uk.screen.at.app.kml002.screenG.CountInfoProcessor;
import nts.uk.screen.at.app.kml002.screenG.NumberOfTimeTotalDto;
import nts.uk.screen.at.app.kml002.screenG.RequestPrams;
import nts.uk.shr.com.context.AppContexts;

/**
 * 回数集計を集計する
 * @author hoangnd
 *
 */
@Stateless
public class ScreenQueryAggrerateNumberTimeWp {
	
	@Inject
	private CountInfoProcessor countInfoProcessor;
	
	@Inject
	private TotalTimesRepository totalTimeRepository;
	
	@Injectable
	TotalTimesCounterService.Require require;
	
	public Map<EmployeeId, Map<TotalTimes, BigDecimal>> aggrerate(
			List<IntegrationOfDaily> aggrerateintegrationOfDaily
			) {
		Map<EmployeeId, Map<TotalTimes, BigDecimal>> output = new HashMap<EmployeeId, Map<TotalTimes, BigDecimal>>();
		//1: 取得する(回数集計種類)
		Optional<CountInfoDto> countInfoOp = Optional.ofNullable(countInfoProcessor.getInfo(new RequestPrams(0)));
		
		// 2: Optional<回数集計選択>.isPresent
		if (countInfoOp.isPresent()) {
			

			// 2.1: 社員別に集計する(Require, List<回数集計NO>, List<日別勤怠(Work)>)
			Map<EmployeeId, Map<Integer, BigDecimal>> countTotalTime = TotalTimesCounterService.countingNumberOfTotalTimeByEmployee(
					require,
					Arrays.asList(new Integer[] {countInfoOp.get().getCountNumberOfTimeDtos().get(0).getNumber()})  // 集計対象の回数集計 = 1で取得した「回数集計選択」．選択した項目リスト
						  .stream()
						  .map(x -> (Integer)x)
						  .collect(Collectors.toList()), 
					aggrerateintegrationOfDaily // 日別勤怠リスト = Input．List<日別勤怠(Work)>
					);
			
			// 2.2: 
			final List<TotalTimes> totalTimes = totalTimeRepository.getTotalTimesDetailByListNo(AppContexts.user().companyId(),
					countInfoOp.get()
					   .getNumberOfTimeTotalDtos()
					   .stream()
					   .map(NumberOfTimeTotalDto::getNumber)
					   .collect(Collectors.toList())
					);
			
			countTotalTime.entrySet()
				.stream()
				.forEach(e -> {
					Map<TotalTimes, BigDecimal> value = e.getValue().entrySet().stream().collect(Collectors.toMap(
							x -> totalTimes.stream().filter(a -> a.getTotalCountNo() == (Integer) x.getKey()).findFirst().orElse(null),
							x -> (BigDecimal)x.getValue()));
					output.put(((EmployeeId)e.getKey()), value);
				});
			
		}
		
		
		return output;
	}
}
