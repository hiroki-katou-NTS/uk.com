package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.ootsuka;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;

/**
 * ドメインサービス：大塚専用処理(Static)
 * @author shuichi_ishida
 */
public class OotsukaStaticService {

	/**
	 * 大塚モードの休憩時間帯取得
	 * 休憩時間帯が出退勤を含めている場合、切り分け、 出退勤範囲外の時間帯を切り出す
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param timeLeaveDailyOpt 日別勤怠の出退勤
	 * @return 出退勤範囲外の休憩時間帯
	 */
	public static List<TimeSheetOfDeductionItem> getBreakTimeSheet(
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			Optional<TimeLeavingOfDailyAttd> timeLeaveDailyOpt) {

		// 大塚モードの確認
		if (!AppContexts.optionLicense().customize().ootsuka()) return new ArrayList<>();
		// 就業時間帯から休憩時間帯を取得する
		List<TimeSpanForCalc> breakTimeFromMaster = integrationOfWorkTime.getBreakTimeZone(todayWorkType);
		// 出勤、退勤時刻で時間帯を縮める
		if (!timeLeaveDailyOpt.isPresent()) return new ArrayList<>();
		TimeLeavingOfDailyAttd timeLeaveDaily = timeLeaveDailyOpt.get();
		List<DeductionTime> checked = new ArrayList<>();
		List<DeductionTime> checking = new ArrayList<>(breakTimeFromMaster.stream()
				.map(t -> new DeductionTime(t.getStart(), t.getEnd())).collect(Collectors.toList()));
		for (TimeLeavingWork timeLeave : timeLeaveDaily.getTimeLeavingWorks()) {
			// 出勤か退勤がなければ、重複確認せずに、全ての休憩時間帯を含める
			if (!timeLeave.getAttendanceStamp().isPresent() || !timeLeave.getLeaveStamp().isPresent()){
				checked = new ArrayList<>(checking);
				continue;
			}
			checked = new ArrayList<>();
			TimeSpanForCalc attdLeave = timeLeave.getTimespan();
			for (DeductionTime breakTime : checking) {
				TimeSpanForCalc breakSpan = new TimeSpanForCalc(breakTime.getStart(), breakTime.getEnd());
				// 出退勤に含まれない休憩時間帯の取得
				List<TimeSpanForCalc> notDup = breakSpan.getNotDuplicationWith(attdLeave);
				checked.addAll(notDup.stream()
						.filter(t -> t.lengthAsMinutes() > 0)
						.map(t -> new DeductionTime(t.getStart(), t.getEnd())).collect(Collectors.toList()));
			}
			checking = new ArrayList<>(checked);
		}
		// 「控除項目の時間帯」へ変換
		return checked.stream()
				.map(t -> TimeSheetOfDeductionItem.createFromDeductionTimeSheet(t))
				.collect(Collectors.toList());
	}
}
