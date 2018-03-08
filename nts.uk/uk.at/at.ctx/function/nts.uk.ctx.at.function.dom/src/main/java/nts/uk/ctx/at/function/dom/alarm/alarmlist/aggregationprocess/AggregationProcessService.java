package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WkpConfigAtTimeAdapterDto;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSetting;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess.DailyAggregationProcessService;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.w4d4alarm.W4D4AlarmService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AggregationProcessService {
	
	@Inject
	private AlarmPatternSettingRepository alPatternSettingRepo;
	
	@Inject
	private ExtractAlarmForEmployeeService extractService;

	@Inject
	private SyWorkplaceAdapter workplaceAdapter;
		
	public List<AlarmExtraValueWkReDto> processAlarmListWorkRecord(List<FuncEmployeeSearchDto> listEmployee, String checkPatternCode, List<PeriodByAlarmCategory> periodByCategory) {
		List<AlarmExtraValueWkReDto> result = new ArrayList<>();

		String companyID = AppContexts.user().companyId();
		
		// パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> alarmPatternSetting = this.alPatternSettingRepo.findByAlarmPatternCode(companyID, checkPatternCode);		
		if(!alarmPatternSetting.isPresent())
			throw new RuntimeException("「アラームリストパターン設定 」が見つかりません！");
		
		
		List<ValueExtractAlarm> valueList = new ArrayList<>();
		// 従業員ごと に行う(for list employee)
		for (FuncEmployeeSearchDto employee : listEmployee) {
			valueList.addAll(extractService.process(alarmPatternSetting.get().getCheckConList(), periodByCategory, employee));
		}
		
		//Convert from ValueExtractAlarm to AlarmExtraValueWkReDto
		
		// get list workplaceId and hierarchyCode 
		List<WkpConfigAtTimeAdapterDto> workplaceTOhierarchy = workplaceAdapter.findByWkpIdsAtTime(companyID,
				GeneralDate.today(),
				listEmployee.stream().map(e -> e.getWorkplaceId()).distinct().collect(Collectors.toList()));
		
		return result;
	}

}
