package nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.ErAlApplicationAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErAlApplicationAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.ErrorAlarmWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationAdapter;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyRecordWorkAdapter;
import nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto.MessageWRExtraConAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm.FuncEmployeeDailyPerErrorImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
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
	
	//エラー発生時に呼び出す申請一覧
	@Inject 
	private ErAlApplicationAdapter erAlApplicationAdapter;
	
	// 書いた申請
	@Inject
	private ApplicationAdapter applicationAdapter;
	
	public List<ValueExtractAlarm> aggregationProcess(DailyAlarmCondition dailyAlarmCondition, PeriodByAlarmCategory period,
			EmployeeSearchDto employee, String companyID ) {
		List<ValueExtractAlarm> valueExtractAlarmList= new ArrayList<>();
		
		List<String> listCode = dailyAlarmCondition.getErrorAlarmCode();
		if(listCode ==null || listCode.isEmpty()) return null;
		
		// 勤務実績のエラーアラーム
		List<ErrorAlarmWorkRecordAdapterDto> errorAlarmWorkRecord = errorAlarmWorkRecordAdapter
				.getListErAlByListCode(companyID, listCode);
		Map<String, ErrorAlarmWorkRecordAdapterDto> errorAlarmMap = errorAlarmWorkRecord.parallelStream()
				.collect(Collectors.toMap(ErrorAlarmWorkRecordAdapterDto::getCode, x -> x));
		
		// 社員の日別実績エラー一覧
		List<FuncEmployeeDailyPerErrorImport> employeeDailyList = employeeDailyAdapter.getByErrorAlarm(employee.getId(),
				new DatePeriod(period.getStartDate(), period.getEndDate()), listCode);

		if(dailyAlarmCondition.isAddApplication()) {
			for(FuncEmployeeDailyPerErrorImport eDaily:  employeeDailyList) {
				ErrorAlarmWorkRecordAdapterDto errorAlarm = errorAlarmMap.get(eDaily.getErrorAlarmWorkRecordCode());
				if(errorAlarm == null) {
					employeeDailyList.removeIf( e->e.getErrorAlarmWorkRecordCode().equals(eDaily.getErrorAlarmWorkRecordCode()));
					listCode.removeIf( x ->x.equals(eDaily.getErrorAlarmWorkRecordCode()));
					continue;
				} 
				if(errorAlarm.getUseAtr()>0) {
					Optional<ErAlApplicationAdapterDto> erAlApplicationOpt = erAlApplicationAdapter
							.getAllErAlAppByEralCode(companyID, eDaily.getErrorAlarmWorkRecordCode());
					if (!erAlApplicationOpt.isPresent())
						throw new RuntimeException("Domain エラー発生時に呼び出す申請一覧 not found!");
					
					List<Integer> listAppType = erAlApplicationOpt.get().getAppType();
					List<Integer> listAppTypeWrited = applicationAdapter
							.getApplicationBySID(Arrays.asList(employee.getId()),
									eDaily.getDate(),
									eDaily.getDate())
							.stream().map(x -> x.getAppType()).collect(Collectors.toList());
					
					if(intersectTwoListAppType(listAppType, listAppTypeWrited )) {
						employeeDailyList.remove(eDaily);
					}
				}
				
			}
			
		}
		
		// Remove error code from errorAlarmWorkRecord if error  code not in  employeeDailyList
		List<String> errorCodeList = employeeDailyList.stream().map(x ->x.getErrorAlarmWorkRecordCode()).collect(Collectors.toList());
		errorAlarmWorkRecord.removeIf( x ->!errorCodeList.contains(x.getCode()));
		
		//勤務実績のエラーアラームチェック
		List<String> errorAlarmCheckIDs =  errorAlarmWorkRecord.stream().map( x->x.getErrorAlarmCheckID()).collect(Collectors.toList());
		List<MessageWRExtraConAdapterDto> messageList = workRecordExtraConAdapter.getMessageWRExtraConByListID(errorAlarmCheckIDs);
		
		Map<String, MessageWRExtraConAdapterDto> errAlarmCheckIDToMessage = messageList.stream()
				.collect(Collectors.toMap(MessageWRExtraConAdapterDto::getDisplayMessage, x -> x));


		for(FuncEmployeeDailyPerErrorImport eDaily: employeeDailyList) {
			// Attendance name and value
			Map<Integer, String> attendanceNameMap = dailyAttendanceItemNameAdapter
					.getDailyAttendanceItemNameAsMapName(eDaily.getAttendanceItemList());
			List<ItemValue> attendanceValueList = dailyRecordWorkAdapter
					.getByEmployeeList(employee.getId(), eDaily.getDate(), eDaily.getAttendanceItemList()).getItems();
			
			// アラーム値メッセージ caculate from 	attendance Name and attendance value 		
			String alarmContent ="";
			ValueExtractAlarm data = new ValueExtractAlarm(employee.getWorkplaceId(), employee.getId(),
					eDaily.getDate().toString(), EnumAdaptor.convertToValueName(AlarmCategory.DAILY).getLocalizedName(),
					errorAlarmMap.get(eDaily.getErrorAlarmWorkRecordCode()).getName(), alarmContent,
					errAlarmCheckIDToMessage
							.get(errorAlarmMap.get(eDaily.getErrorAlarmWorkRecordCode()).getErrorAlarmCheckID())
							.getDisplayMessage());
						
			valueExtractAlarmList.add(data);
		}

		
		return valueExtractAlarmList;
		
	}
	
	private boolean intersectTwoListAppType(List<Integer> listAppType1, List<Integer> listAppType2) {
		List<Integer> intersect = listAppType1.stream().filter(listAppType2::contains).collect(Collectors.toList());
		return !intersect.isEmpty();
	}
	

}
