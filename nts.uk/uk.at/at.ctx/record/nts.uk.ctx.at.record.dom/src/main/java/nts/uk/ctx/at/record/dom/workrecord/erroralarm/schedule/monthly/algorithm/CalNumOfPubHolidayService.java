package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly.algorithm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト.スケジュール日次・月次・年間.スケジュール月次のアラームリストのチェック条件.アルゴリズム.使用しない.特定属性の項目の予定を作成する.対比のチェック条件をチェック.所定公休日数比をチェック.公休使用数を計算する
 *
 */
@Stateless
public class CalNumOfPubHolidayService {
	
	/**
	 * 公休使用数を計算する
	 * 
	 * @param cid 会社ID
	 * @param ym 年月
	 * @param workSchedules List＜勤務予定＞
	 * @param integrationOfdailys List＜日別実績＞
	 * @param currentClosingPeriod 現在の締め期間
	 * @param workTypes List＜勤務種類＞
	 * 
	 * @return number of day holiday used (公休使用日数)
	 */
	public Double getNumOfPubHolidayUsed(
			String cid, YearMonth ym, 
			List<WorkScheduleWorkInforImport> workSchedules, 
			List<IntegrationOfDaily> integrationOfdailys,
			GetYearProcessAndPeriodDto currentClosingPeriod,
			List<WorkType> workTypes) {
		Double numberOfPublicHoliday = 0.0;
		
		// Input．年月　＜　Input．現在の締め期間．処理年月
		if (ym.lessThan(currentClosingPeriod.getProcessingYm())) {
			// 期間内の公休残数を集計する
			// TODO RQ718 not implement QA#113101
			
			return 0.0;
		}
		
		// 以外の場合
		GeneralDate sysDate = GeneralDate.today();
		
		// Input．年月の開始日から終了日までループ
		DatePeriod dPeriod = new DatePeriod(ym.firstGeneralDate(), ym.lastGeneralDate());
		List<GeneralDate> listDate = dPeriod.datesBetween();
		for(int day = 0; day < listDate.size(); day++) {
			GeneralDate exDate = listDate.get(day);
			
			// 勤務予定　＝　Input．List＜勤務予定＞から年月日　＝　ループ中の年月日を探す
			Optional<WorkScheduleWorkInforImport> workScheduleOpt = workSchedules.stream()
					.filter(x -> x.getYmd().equals(exDate))
					.findFirst();
			
			// 日別実績　＝　Input．List＜日別実績＞から年月日　＝　ループ中の年月日を探す
			Optional<IntegrationOfDaily> integrationOfdailyOpt = integrationOfdailys.stream()
					.filter(x -> x.getYmd().equals(exDate))
					.findFirst();
			
			// 「勤務予定」且つ「日別実績」がEmptyの場合
			if (!workScheduleOpt.isPresent() && !integrationOfdailyOpt.isPresent()) {
				continue;
			}
			
			String workTypeCode;
			
			// システム日付＜＝ループ中年月日
			if (sysDate.beforeOrEquals(exDate) && workScheduleOpt.isPresent()) {
				// 勤務集類コードをセット
				// 勤務集類コード　＝　探した勤務予定．勤務情報．勤務情報．勤務種類コード
				workTypeCode = workScheduleOpt.get().getWorkType();
			} else {
				// 勤務種類コードをセット
				if (integrationOfdailyOpt.isPresent()) {
					IntegrationOfDaily integrationOfDaily = integrationOfdailyOpt.get();
					workTypeCode = integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode().v();
				} else {
					workTypeCode = workScheduleOpt.get().getWorkType();
				}
			}
			
			if (workTypeCode == null) {
				continue;
			}
			
			// 勤務種類を探す
			Optional<WorkType> workTypeOpt = workTypes.stream()
					.filter(x -> x.getWorkTypeCode().v().equals(workTypeCode))
					.findFirst();
			if (!workTypeOpt.isPresent()) {
				continue;
			}
			
			// 公休消化するか判断
			// Output: 公休区分
			boolean hasUsePublicHoliday = workTypeOpt.get().getDailyWork().isHolidayWork();
			if (!hasUsePublicHoliday) {
				continue;
			}
			
			// 公休使用日数　+＝　1
			numberOfPublicHoliday += 1;
		}
		
		return numberOfPublicHoliday;
	}
}
