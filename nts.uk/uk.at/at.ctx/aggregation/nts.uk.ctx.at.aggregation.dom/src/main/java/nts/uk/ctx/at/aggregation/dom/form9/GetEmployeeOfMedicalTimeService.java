package nts.uk.ctx.at.aggregation.dom.form9;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.DailyAttendanceGettingService;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
/**
 * 社員の出力医療時間を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.社員の出力医療時間を取得する
 * @author lan_lt
 *
 */
public class GetEmployeeOfMedicalTimeService {
	
	/**
	 * 取得する
	 * @param require
	 * @param empIds 社員IDリスト
	 * @param period 期間
	 * @param acquireTarget 取得対象
	 * @return
	 */
	public static Map<EmployeeIdAndYmd, EmployeeOfMedicalTime> get(Require require, List<EmployeeId> empIds
			, DatePeriod period
			, ScheRecGettingAtr acquireTarget){
		
		Map<ScheRecGettingAtr, List<IntegrationOfDaily>> dailyMap = DailyAttendanceGettingService.get(require, empIds, period, acquireTarget);
		
		List<IntegrationOfDaily> scheduleList = acquireTarget.isNeedSchedule()? dailyMap.get(ScheRecGettingAtr.ONLY_SCHEDULE)
				: Collections.emptyList();
		List<IntegrationOfDaily> recordList = acquireTarget.isNeedRecord()? dailyMap.get(ScheRecGettingAtr.ONLY_RECORD)
				: Collections.emptyList();
		
		Map<EmployeeIdAndYmd, EmployeeOfMedicalTime> empOfscheduleMedicalTimes = scheduleList.stream()
				.collect(Collectors.toMap(
						schedule ->  new EmployeeIdAndYmd( schedule.getEmployeeId(), schedule.getYmd() )
					,	schedule ->  EmployeeOfMedicalTime.create(schedule, ScheRecAtr.SCHEDULE)) );
		
		Map<EmployeeIdAndYmd, EmployeeOfMedicalTime> empOfRecordMedicalTimes = recordList.stream()
				.collect(Collectors.toMap(
						record ->  new EmployeeIdAndYmd( record.getEmployeeId(), record.getYmd() )
					,	record ->  EmployeeOfMedicalTime.create( record, ScheRecAtr.RECORD)) );
		
		Map<EmployeeIdAndYmd, EmployeeOfMedicalTime> empOfMedicalTimes = new HashMap<>();
		empOfMedicalTimes.putAll(empOfscheduleMedicalTimes);
		empOfMedicalTimes.putAll(empOfRecordMedicalTimes);
		
		return empOfMedicalTimes;
		
	}

	public static interface Require extends DailyAttendanceGettingService.Require{
		
	}
	
}
