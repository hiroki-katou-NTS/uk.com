package nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedCheckSDailyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtracSDailyItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ErrorAlarmScheduleDailyFinder {
	@Inject
	private FixedExtracSDailyItemsRepository scheduleFixItemDayRepository;
	
	/**
	 * Get 固定のチェック条件
	 * @return key, value
	 */
	public HashMap<Integer, String> getFixedCheckScheduleDailyItems() {
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		for (FixedCheckSDailyItems userType : FixedCheckSDailyItems.values()) {
			result.put(userType.value, userType.nameId);
		}
		
		return result;
	}
	
	/**
	 * Get スケジュール日次の固有抽出項目
	 * @return list of schedule fixed item day
	 */
	public List<ScheFixItemDayDto> getScheFixItemDay() {
		String contractCode = AppContexts.user().contractCode();
		String companyCode = AppContexts.user().companyCode();
		
		return scheduleFixItemDayRepository.getScheFixItemDay(contractCode, companyCode).stream()
				.map(eral -> ScheFixItemDayDto.fromDomain(eral))
				.collect(Collectors.toList());
	}
}
