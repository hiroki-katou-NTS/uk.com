package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErrorAlarmConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DailyAggregationProcessService {

	@Inject
	private AlarmCheckConditionByCategoryRepository alCheckConByCategoryRepo;

	// 社員の日別実績エラー一覧
	@Inject
	private FuncEmployeeDailyPerErrorAdapter employeeDailyAdapter;

	// 勤務実績のエラーアラーム
	@Inject
	private ErrorAlarmWorkRecordAdapter errorAlarmWorkRecordAdapter;

	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;

	// 勤務実績のエラーアラームチェック
	@Inject
	private WorkRecordExtraConAdapter workRecordExtraConAdapter;

	public void dailyAggregationProcess(String checkConditionCode, PeriodByAlarmCategory period,
			FuncEmployeeSearchDto employee) {
		String companyID = AppContexts.user().companyId();
		// ドメインモデル「カテゴリ別アラームチェック条件」を取得する
		Optional<AlarmCheckConditionByCategory> alCheckConByCategory = alCheckConByCategoryRepo.find(companyID,
				AlarmCategory.DAILY.value, checkConditionCode);

		// カテゴリアラームチェック条件．抽出条件を元に日次データをチェックする
		DailyAlarmCondition dailyAlarmCondition = (DailyAlarmCondition) alCheckConByCategory.get()
				.getExtractionCondition();

		// tab2: 日別実績のエラーアラーム
		List<String> listCode = dailyAlarmCondition.getErrorAlarmCode();

		// 勤務実績のエラーアラーム
		Map<String, ErrorAlarmWorkRecordAdapterDto> errorAlarmWorkRecord = errorAlarmWorkRecordAdapter
				.getListErAlByListCode(companyID, listCode).stream()
				.collect(Collectors.toMap(ErrorAlarmWorkRecordAdapterDto::getCode, x -> x));

		// 社員の日別実績エラー一覧
		List<FuncEmployeeDailyPerErrorImport> employeeDailyList = employeeDailyAdapter.getByErrorAlarm(employee.getId(),
				new DatePeriod(period.getStartDate(), period.getEndDate()), listCode);

		//勤務実績のエラーアラームチェック
		List<ErrorAlarmConAdapterDto> workRecordExtraConList = workRecordExtraConAdapter
				.getAllWorkRecordExtraConByListID(listCode).stream().map(x -> x.getErrorAlarmCondition())
				.collect(Collectors.toList());
		Map<String, ErrorAlarmConAdapterDto> workRecordExtraConMap = workRecordExtraConList.stream()
				.collect(Collectors.toMap(ErrorAlarmConAdapterDto::getErrorAlarmCheckID, x -> x));

		if (dailyAlarmCondition.isAddApplication()) {

		} else {
			
			
			
			
			for (FuncEmployeeDailyPerErrorImport eDaily : employeeDailyList) {
				
				
				ValueExtractAlarm data = new ValueExtractAlarm(employee.getWorkplaceId(), employee.getId(),
						eDaily.getDate(), EnumAdaptor.convertToValueName(AlarmCategory.DAILY).getLocalizedName(),
						errorAlarmWorkRecord.get(eDaily.getErrorAlarmWorkRecordCode()).getCode(), "",
						workRecordExtraConMap.get(eDaily.getErrorAlarmWorkRecordCode()).getDisplayMessage());
			}

		}

	}
}
