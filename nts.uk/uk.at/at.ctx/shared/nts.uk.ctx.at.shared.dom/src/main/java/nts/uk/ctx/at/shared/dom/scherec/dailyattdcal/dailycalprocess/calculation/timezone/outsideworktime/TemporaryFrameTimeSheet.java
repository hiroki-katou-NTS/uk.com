package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.ActualWorkTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ActualWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.SpecBonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.MidNightTimeSheetForCalcList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.someitems.BonusPayTimeSheetForCalc;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * 臨時枠時間帯(WORK)
 * @author shuichi_ishida
 */
@Getter
public class TemporaryFrameTimeSheet extends ActualWorkingTimeSheet {

	/** 勤務NO */
	private WorkNo workNo;
	
	/**
	 * Constractor
	 * @param timeSheet 時間帯
	 * @param rounding 時間丸め設定
	 * @param recorddeductionTimeSheets 計上用控除時間帯
	 * @param deductionTimeSheets 控除用控除時間帯
	 * @param bonusPayTimeSheet 加給時間帯
	 * @param specifiedBonusPayTimeSheet 特定日加給時間帯
	 * @param midNighttimeSheet 深夜時間帯
	 * @param workNo 勤務NO
	 */
	private TemporaryFrameTimeSheet(
			TimeSpanForDailyCalc timeSheet,
			TimeRoundingSetting rounding,
			List<TimeSheetOfDeductionItem> recorddeductionTimeSheets,
			List<TimeSheetOfDeductionItem> deductionTimeSheets,
			List<BonusPayTimeSheetForCalc> bonusPayTimeSheet,
			List<SpecBonusPayTimeSheetForCalc> specifiedBonusPayTimeSheet,
			MidNightTimeSheetForCalcList midNighttimeSheet,
			WorkNo workNo) {
		
		super(timeSheet, rounding, recorddeductionTimeSheets, deductionTimeSheets, bonusPayTimeSheet,
				specifiedBonusPayTimeSheet, midNighttimeSheet);
		this.workNo = workNo;
	}
	
	/**
	 * 作成する
	 * @param companySet 会社別設定管理
	 * @param personDailySet 社員設定管理
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(WORK)
	 * @param timeLeavingWork 出退勤
	 * @param deductionTimeSheet 控除時間帯
	 * @return 臨時枠時間帯(WORK)
	 */
	public static Optional<TemporaryFrameTimeSheet> create(
			ManagePerCompanySet companySet,
			ManagePerPersonDailySet personDailySet,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeLeavingWork timeLeavingWork,
			DeductionTimeSheet deductionTimeSheet) {
	
		// 臨時枠時間帯を作成
		if (!timeLeavingWork.getAttendanceTime().isPresent()) return Optional.empty();
		if (!timeLeavingWork.getLeaveTime().isPresent()) return Optional.empty();
		TemporaryFrameTimeSheet myclass = new TemporaryFrameTimeSheet(
				new TimeSpanForDailyCalc(
						timeLeavingWork.getAttendanceTime().get(),
						timeLeavingWork.getLeaveTime().get()),
				integrationOfWorkTime.getCommonSetting().getExtraordTimeSet().getTimeRoundingSet(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new MidNightTimeSheetForCalcList(new ArrayList<>()),
				timeLeavingWork.getWorkNo());
		// 控除時間帯の登録
		myclass.registDeductionListForTemporary(
				deductionTimeSheet, Optional.of(integrationOfWorkTime.getCommonSetting()));
		// 加給時間帯の作成
		myclass.createBonusPayTimeSheet(
				personDailySet.getBonusPaySetting(), integrationOfDaily.getSpecDateAttr(), deductionTimeSheet);
		// 深夜時間帯の作成
		myclass.createMidNightTimeSheet(companySet.getMidNightTimeSheet(),
				Optional.of(integrationOfWorkTime.getCommonSetting()), deductionTimeSheet);
		// 臨時枠時間帯を返す
		return Optional.of(myclass);
	}
	
	/**
	 * 控除時間帯の登録（臨時枠時間帯）
	 * @param deductTimeSheet 控除時間帯
	 * @param commonSet 就業時間帯の共通設定
	 */
	private void registDeductionListForTemporary(
			DeductionTimeSheet deductTimeSheet,
			Optional<WorkTimezoneCommonSet> commonSet){
		
		// 控除時間帯の登録
		this.registDeductionList(ActualWorkTimeSheetAtr.OverTimeWork, deductTimeSheet, commonSet);
		// 休憩を除いた控除項目リストを取得
		List<TimeSheetOfDeductionItem> deductList = this.deductionTimeSheet.stream()
				.filter(c -> !(c.getDeductionAtr().isBreak() &&
						c.getBreakAtr().isPresent() && c.getBreakAtr().get().isBreak()))
				.collect(Collectors.toList());
		List<TimeSheetOfDeductionItem> recordList = this.recordedTimeSheet.stream()
				.filter(c -> !(c.getDeductionAtr().isBreak() &&
						c.getBreakAtr().isPresent() && c.getBreakAtr().get().isBreak()))
				.collect(Collectors.toList());
		// 控除項目リストを入れ替える
		this.deductionTimeSheet = deductList;
		this.recordedTimeSheet = recordList;
	}
}
