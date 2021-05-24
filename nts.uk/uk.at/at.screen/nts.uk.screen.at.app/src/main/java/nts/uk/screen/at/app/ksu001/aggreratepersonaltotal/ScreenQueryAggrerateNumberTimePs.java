package nts.uk.screen.at.app.ksu001.aggreratepersonaltotal;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.TotalTimesCounterService;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.PersonalCounterCategory;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
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
public class ScreenQueryAggrerateNumberTimePs {

	@Inject
	private CountInfoProcessor countInfoProcessor;
	
	@Inject
	private TotalTimesRepository totalTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
//	@Inject
//	TotalTimesCounterService.Require require;
	
	public Map<EmployeeId, Map<TotalTimes, BigDecimal>> aggrerate(
			PersonalCounterCategory personalCounter,
			List<IntegrationOfDaily> aggrerateintegrationOfDaily
			) {
		Map<EmployeeId, Map<TotalTimes, BigDecimal>> output = new HashMap<EmployeeId, Map<TotalTimes, BigDecimal>>();
		//1: 取得する(回数集計種類)
		RequestPrams param;
		if (personalCounter == PersonalCounterCategory.TIMES_COUNTING_1) {
			param = new RequestPrams(1);
		} else if (personalCounter == PersonalCounterCategory.TIMES_COUNTING_2) {
			param = new RequestPrams(2);
		} else {
			param = new RequestPrams(3);
		}
		Optional<CountInfoDto> countInfoOp = Optional.ofNullable(countInfoProcessor.getInfo(param));
		
		Require require = new Require(totalTimeRepository, workTypeRepository); // todo
		// 2: Optional<回数集計選択>.isPresent
		if (countInfoOp.isPresent()) {
			

			// 2.1: 社員別に集計する(Require, List<回数集計NO>, List<日別勤怠(Work)>)
			Map<EmployeeId, Map<Integer, BigDecimal>> countTotalTime = TotalTimesCounterService.countingNumberOfTotalTimeByEmployee(
					require,
					CollectionUtil.isEmpty(countInfoOp.get().getCountNumberOfTimeDtos()) ? Collections.emptyList() : 
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
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Require implements TotalTimesCounterService.Require {
		
		@Inject
		private TotalTimesRepository totalTimesRepository;
		
		@Inject
		private WorkTypeRepository workTypeRepository;
		
		@Override
		public DailyRecordToAttendanceItemConverter createDailyConverter() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkType> workType(String companyId, String workTypeCd) {
			return workTypeRepository.findByPK(companyId, workTypeCd);
		}

		@Override
		public List<TotalTimes> getTotalTimesList(List<Integer> totalTimeNos) {
			return totalTimesRepository.getTotalTimesDetailByListNo(AppContexts.user().companyId(), totalTimeNos);
		}
	}
}
