package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyRecordWorkAdapter;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.ErrorAlarmConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.FuncEmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DailyPerformanceService {
	// 社員の日別実績エラー一覧
	@Inject
	private FuncEmployeeDailyPerErrorAdapter employeeDailyAdapter;

	// 勤務実績のエラーアラーム
	@Inject
	private ErrorAlarmWorkRecordAdapter errorAlarmWorkRecordAdapter;

	// 勤務実績のエラーアラームチェック
	@Inject
	private WorkRecordExtraConAdapter workRecordExtraConAdapter;
	
	// Get attendance item name 勤怠項目名
	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
	
	// get attendance value　勤怠項目価値
	@Inject
	private DailyRecordWorkAdapter dailyRecordWorkAdapter;
	
	public List<ValueExtractAlarm> aggregationProcess(DailyAlarmCondition dailyAlarmCondition, PeriodByAlarmCategory period,
			FuncEmployeeSearchDto employee, String companyID ) {
		List<ValueExtractAlarm> valueExtractAlarmList= new ArrayList<>();
		
		List<String> listCode = dailyAlarmCondition.getErrorAlarmCode();
		if(listCode ==null || listCode.isEmpty()) return null;
		
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
				Map<Integer, String> attendanceNameMap =  dailyAttendanceItemNameAdapter.getDailyAttendanceItemNameAsMapName(eDaily.getAttendanceItemList());
				List<ItemValue>  attendanceValueList = dailyRecordWorkAdapter.getByEmployeeList(employee.getId(), eDaily.getDate(), eDaily.getAttendanceItemList()).getItems();
				
				// アラーム値メッセージ caculate from 	attendance Name and attendance value 		
				String messageContent ="";
				
				ValueExtractAlarm data = new ValueExtractAlarm(employee.getWorkplaceId(), employee.getId(),
						eDaily.getDate(), EnumAdaptor.convertToValueName(AlarmCategory.DAILY).getLocalizedName(),
						errorAlarmWorkRecord.get(eDaily.getErrorAlarmWorkRecordCode()).getName(), messageContent ,
						workRecordExtraConMap.get(eDaily.getErrorAlarmWorkRecordCode()).getDisplayMessage());
				
				valueExtractAlarmList.add(data);
			}

		}
		
		return valueExtractAlarmList;
		
	}
	

}
