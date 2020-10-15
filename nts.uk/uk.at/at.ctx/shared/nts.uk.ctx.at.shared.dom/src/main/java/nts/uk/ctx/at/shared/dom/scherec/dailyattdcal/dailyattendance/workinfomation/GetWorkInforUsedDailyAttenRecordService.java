package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.WorkInformation;

/**
 * 日別勤怠の実績で利用する勤務情報のリストを取得する
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠
 * @author HieuLt
 *
 */
public class GetWorkInforUsedDailyAttenRecordService {
	/**
	 * [1] 取得する	
	 * @param listWorkInfoOfDailyPerformance
	 * @return
	 */
	public static  List<WorkInformation>  getListWorkInfo(List<WorkInfoOfDailyAttendance> listWorkInfoOfDailyPerformance){
		List<WorkInformation> result = new ArrayList<>();
		List<WorkInformation> data = listWorkInfoOfDailyPerformance.stream().map(c ->c.getRecordInfo()).collect(Collectors.toList());
		//Distin Work Time Work Hour
		for(WorkInformation wi :data){
			Optional<WorkInformation> optWi = result.stream().filter(x-> x.getWorkTypeCode().v().equals(wi.getWorkTypeCode().v())
					&& x.getWorkTimeCodeNotNull().equals(wi.getWorkTimeCodeNotNull())).findFirst();
			if(!optWi.isPresent()){
				result.add(wi);
			}
		}
		return result;
	} 
}
