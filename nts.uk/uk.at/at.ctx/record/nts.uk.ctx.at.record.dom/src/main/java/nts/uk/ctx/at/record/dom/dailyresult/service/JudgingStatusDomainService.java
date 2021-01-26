package nts.uk.ctx.at.record.dom.dailyresult.service;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.実績データから在席状態を判断する.実績データから在席状態を判断する
 */
public class JudgingStatusDomainService {
	
	public static AttendanceAccordActualData JudgingStatus(Require rq, String sid) {
		//TODO
		return AttendanceAccordActualData.builder().build();
	}

	public interface Require {

		/**
		 * [R-1]日別実績の勤務情報を取得する
		 * 
		 * @param sid      社員ID
		 * @param baseDate 年月日
		 */
		public Optional<WorkInfoOfDailyPerformance> getDailyActualWorkInfo(String sid, GeneralDate baseDate);

		/**
		 * [R-2] 日別実績の出退勤を取得する
		 * 
		 * @param sid      社員ID
		 * @param baseDate 年月日
		 */
		public Optional<TimeLeavingOfDailyPerformance> getDailyAttendanceAndDeparture(String sid, GeneralDate baseDate);

		/**
		 * [R-3] 日別勤務予定を取得する
		 * 
		 * @param sid      社員ID
		 * @param baseDate 年月日
		 */
		public Optional<String> getDailyWorkSchedule(String sid, GeneralDate baseDate);

		/**
		 * [R-3] 日別勤務予定を取得する
		 * 
		 * @param loginCid ログイン会社ID
		 * @param code     コード
		 */
		public Optional<WorkType> getWorkType(String loginCid, String code);
	}
}
