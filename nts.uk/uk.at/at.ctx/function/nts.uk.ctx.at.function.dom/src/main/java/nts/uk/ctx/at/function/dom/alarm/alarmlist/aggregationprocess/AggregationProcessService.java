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
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AggregationProcessService {
	
	@Inject
	private AlarmPatternSettingRepository alPatternSettingRepo;
	
	@Inject
	private ExtractAlarmForEmployeeService extractService;

	@Inject
	private SyWorkplaceAdapter workplaceAdapter;
		
	public List<AlarmExtraValueWkReDto> processAlarmListWorkRecord(List<EmployeeSearchDto> listEmployee, String checkPatternCode, List<PeriodByAlarmCategory> periodByCategory) {
		List<AlarmExtraValueWkReDto> result = new ArrayList<>();

		String companyID = AppContexts.user().companyId();
		
		// パラメータ．パターンコードをもとにドメインモデル「アラームリストパターン設定」を取得する
		Optional<AlarmPatternSetting> alarmPatternSetting = this.alPatternSettingRepo.findByAlarmPatternCode(companyID, checkPatternCode);		
		if(!alarmPatternSetting.isPresent())
			throw new RuntimeException("「アラームリストパターン設定 」が見つかりません！");
		
		
		List<ValueExtractAlarm> valueList = new ArrayList<>();
		// 従業員ごと に行う(for list employee)
		for (EmployeeSearchDto employee : listEmployee) {
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
