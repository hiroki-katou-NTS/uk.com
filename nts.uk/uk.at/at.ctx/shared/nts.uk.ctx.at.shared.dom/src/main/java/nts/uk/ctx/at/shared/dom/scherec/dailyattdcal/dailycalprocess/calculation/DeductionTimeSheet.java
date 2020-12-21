package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.DeductionClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.ShortTimeSheetAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.TimeSheetOfDeductionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.deductiontime.WorkingBreakTimeAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LateTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.LeaveEarlyTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedRestCalculateMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.RestTimeOfficeWorkCalcMethod;
import nts.uk.ctx.at.shared.dom.worktime.common.TotalRoundingSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSettingDetail;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 控除時間帯
 * 
 * @author keisuke_hoshina
 *
 */
@RequiredArgsConstructor
@Getter
public class DeductionTimeSheet {
	// 控除用
	private final List<TimeSheetOfDeductionItem> forDeductionTimeZoneList;
	// 計上用
	private final List<TimeSheetOfDeductionItem> forRecordTimeZoneList;
	//休憩
	private final List<BreakTimeOfDailyAttd> breakTimeOfDailyList;
	//外出
	private final Optional<OutingTimeOfDailyAttd> dailyGoOutSheet;
	//短時間
	private final List<ShortWorkingTimeSheet> shortTimeSheets;
	
	/**
	 * 控除時間帯
	 * アルゴリズム：固定休憩用の時間帯作成
	 * @param workType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param oneDayRange 日別計算用時間帯
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @param predetermineTimeSetForCalc 所定時間設定(計算用クラス)
	 * @return 控除時間帯
	 */
	public static DeductionTimeSheet createTimeSheetForFixBreakTime(
			WorkType workType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeSpanForDailyCalc oneDayRange,
			TimeLeavingOfDailyAttd attendanceLeaveWork,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {
		// 計上用
		val record = createDedctionTimeSheet(
				DeductionAtr.Appropriate,
				workType,
				integrationOfWorkTime,
				integrationOfDaily,
				oneDayRange,
				attendanceLeaveWork,
				predetermineTimeSetForCalc);
		// 控除用
		val ded = createDedctionTimeSheet(
				DeductionAtr.Deduction,
				workType,
				integrationOfWorkTime,
				integrationOfDaily,
				oneDayRange,
				attendanceLeaveWork,
				predetermineTimeSetForCalc);
		
		return new DeductionTimeSheet(
				ded,
				record,
				integrationOfDaily.getBreakTime(),
				integrationOfDaily.getOutingTime(),
				integrationOfDaily.getShortTime().isPresent()
						? integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets()
						: Collections.emptyList());
	}


	/**
	 * 控除時間帯の作成
	 * アルゴリズム：流動休憩シフトから計算以外
	 * @param dedAtr 控除区分
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param oneDayOfRange 日別計算用時間帯
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @param predetermineTimeSetForCalc 所定時間設定(計算用クラス)
	 * @return 控除項目の時間帯(List)
	 */
	private static List<TimeSheetOfDeductionItem> createDedctionTimeSheet(
			DeductionAtr dedAtr,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeSpanForDailyCalc oneDayOfRange,
			TimeLeavingOfDailyAttd attendanceLeaveWork,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {

		/* 控除時間帯取得 控除時間帯リストへコピー */
		List<TimeSheetOfDeductionItem> useDedTimeSheet = collectDeductionTimes(
				dedAtr,
				todayWorkType,
				integrationOfWorkTime,
				integrationOfDaily,
				oneDayOfRange,
				attendanceLeaveWork);

		/* 重複部分補正処理 */
		useDedTimeSheet = new DeductionTimeSheetAdjustDuplicationTime(useDedTimeSheet).reCreate(
				integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeMethodSet(),
				integrationOfWorkTime.getRestClockManageAtr(),
				integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeDailyAtr());

		/* 控除でない外出削除 */
		List<TimeSheetOfDeductionItem> goOutDeletedList = new ArrayList<TimeSheetOfDeductionItem>();
		for (TimeSheetOfDeductionItem timesheet : useDedTimeSheet) {
			if (dedAtr.isAppropriate() || 
					(!(timesheet.getDeductionAtr().isGoOut() && timesheet.getGoOutReason().get().isPublicOrCmpensation()))) {
				goOutDeletedList.add(timesheet);
			}
		}

		/* 丸め処理(未完成)*/
//		if(integrationOfWorkTime.getCommonSetting().getGoOutSet().getTotalRoundingSet().getFrameStraddRoundingSet().isTotalAndRounding()) {
//			List<EmTimeZoneSet> fixWoSetting = integrationOfWorkTime.getEmTimeZoneSetList(todayWorkType);
//			
//			val mostFastOclock = fixWoSetting.stream().filter(tc -> tc.getEmploymentTimeFrameNo().v() == 1).findFirst();
//			val mostLateOclock = fixWoSetting.stream().filter(tc -> tc.getEmploymentTimeFrameNo().v() == fixWoSetting.size()).findFirst();
//			if(mostFastOclock.isPresent() && mostLateOclock.isPresent()) {
//				goOutDeletedList = rounding(
//						dedAtr,
//						goOutDeletedList,
//						integrationOfWorkTime.getCommonSetting().getGoOutSet().getTotalRoundingSet(),
//						mostFastOclock.get().getTimezone().getStart(),
//						mostLateOclock.get().getTimezone().getEnd(),
//						predetermineTimeSetForCalc.getOneDayTimeSpan());
//			}
//		}
		return goOutDeletedList;
	}


	/**
	 * 控除時間に該当する項目を集め控除時間帯を作成する
	 * アルゴリズム：控除時間帯の取得
	 * @param dedAtr 控除区分
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param oneDayOfRange 日別計算用時間帯
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @return 控除項目の時間帯(List)
	 */
	public static List<TimeSheetOfDeductionItem> collectDeductionTimes(
			DeductionAtr dedAtr,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeSpanForDailyCalc oneDayOfRange,
			TimeLeavingOfDailyAttd attendanceLeaveWork) {
		
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>();
		/* 休憩時間帯取得 */
		sheetList.addAll(getBreakTimeSheet(
				integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision(),
				integrationOfWorkTime.getFixedRestCalculateMethod(),
				integrationOfWorkTime.getFlowWorkRestSettingDetail(),
				integrationOfDaily.getBreakTime(),
				integrationOfDaily.getOutingTime()));
		
		/* 外出時間帯取得 */
		if(integrationOfDaily.getOutingTime().isPresent()) {
			sheetList.addAll(integrationOfDaily.getOutingTime().get().removeUnuseItemBaseOnAtr(
					dedAtr,
					integrationOfWorkTime.getWorkTimeSetting().getWorkTimeDivision().getWorkTimeMethodSet(),
					integrationOfWorkTime.getFlowWorkRestTimezone(todayWorkType),
					integrationOfWorkTime.getFlowWorkRestSettingDetail()));
		}
		/* 短時間勤務時間帯を取得 */
		//育児
		if(integrationOfDaily.getShortTime().isPresent()) {
			List<ShortWorkingTimeSheet> chilCare = integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets().stream()
					.filter(tc -> tc.getChildCareAttr().isChildCare())
					.collect(Collectors.toList());
			List<TimeSheetOfDeductionItem> list = getShortTime(attendanceLeaveWork,chilCare,integrationOfWorkTime.getCommonSetting().getShortTimeWorkSet().isChildCareWorkUse());
			for(TimeSheetOfDeductionItem timeSheetOfDeductionItem:list) {
				sheetList.add(timeSheetOfDeductionItem);
			}
		}
		//介護
		if(integrationOfDaily.getShortTime().isPresent()) {
			val care = integrationOfDaily.getShortTime().get().getShortWorkingTimeSheets().stream().filter(tc -> tc.getChildCareAttr().isCare()).collect(Collectors.toList());
			sheetList.addAll(getShortTime(attendanceLeaveWork,care,integrationOfWorkTime.getCommonSetting().getShortTimeWorkSet().isNursTimezoneWorkUse()));
		}
		/* ソート処理 */
		sheetList = sheetList.stream()
				.sorted((first, second) -> first.getTimeSheet().getTimeSpan().getStart().compareTo(second.getTimeSheet().getTimeSpan().getStart()))
				.collect(Collectors.toList());
		/* 計算範囲による絞り込み */
		List<TimeSheetOfDeductionItem> reNewSheetList = refineCalcRange(
				sheetList,
				oneDayOfRange,
				integrationOfWorkTime.getCommonRestSetting().getCalculateMethod(),
				attendanceLeaveWork);
		return reNewSheetList;
	}

	/**
	 * 計算範囲による絞り込みを行うためのループ
	 * 
	 * @param dedTimeSheets
	 *            控除項目の時間帯
	 * @param oneDayRange
	 *            1日の範囲
	 * @return 控除項目の時間帯リスト
	 */
	public static List<TimeSheetOfDeductionItem> refineCalcRange(List<TimeSheetOfDeductionItem> dedTimeSheets,
			TimeSpanForDailyCalc oneDayRange, RestTimeOfficeWorkCalcMethod calcMethod,
			TimeLeavingOfDailyAttd attendanceLeaveWork) {
		List<TimeSheetOfDeductionItem> sheetList = new ArrayList<TimeSheetOfDeductionItem>();
		for (TimeSheetOfDeductionItem timeSheet : dedTimeSheets) {
			switch (timeSheet.getDeductionAtr()) {
			case CHILD_CARE:
			case GO_OUT:
				Optional<TimeSpanForDailyCalc> duplicateGoOutSheet = oneDayRange.getDuplicatedWith(timeSheet.getTimeSheet());
				if (duplicateGoOutSheet.isPresent()) {
					/* ここで入れる控除、加給、特定日、深夜は duplicateGoOutSheetと同じ範囲に絞り込む */
					sheetList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(duplicateGoOutSheet.get(),
							timeSheet.getRounding(), timeSheet.getRecordedTimeSheet(), timeSheet.getDeductionTimeSheet(),
							timeSheet.getWorkingBreakAtr(),
							timeSheet.getGoOutReason(), timeSheet.getBreakAtr(), 
							timeSheet.getShortTimeSheetAtr(),timeSheet.getDeductionAtr(),timeSheet.getChildCareAtr()));

				}
				break;
				
			case BREAK:
				List<TimeSpanForDailyCalc> duplicateBreakSheet = timeSheet.getBreakCalcRange(
						attendanceLeaveWork.getTimeLeavingWorks(), calcMethod,
						oneDayRange.getDuplicatedWith(timeSheet.getTimeSheet()));
				if (!duplicateBreakSheet.isEmpty()) {
					duplicateBreakSheet.forEach(tc -> {
						/* ここで入れる控除、加給、特定日、深夜は duplicateGoOutSheetと同じ範囲に絞り込む */
						sheetList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(
								tc,
								timeSheet.getRounding(), timeSheet.getRecordedTimeSheet(), timeSheet.getDeductionTimeSheet(),
								timeSheet.getWorkingBreakAtr(),timeSheet.getGoOutReason(), timeSheet.getBreakAtr(),
								timeSheet.getShortTimeSheetAtr(),timeSheet.getDeductionAtr(),timeSheet.getChildCareAtr()));
					});
				}
				break;
			default:
				throw new RuntimeException("unknown deductionAtr:" + timeSheet.getDeductionAtr());
			}
		}
		return sheetList;
	}

	/**
	 * 全控除項目の時間帯の合計を算出する
	 * 
	 * @return 控除時間
	 */
	public AttendanceTime calcDeductionAllTimeSheet(DeductionAtr dedAtr, TimeSpanForDailyCalc workTimeSpan) {
		List<TimeSheetOfDeductionItem> duplicatitedworkTime = getCalcRange(workTimeSpan);
		AttendanceTime sumTime = new AttendanceTime(0);

		/* stream.collect.summingInt() */
		for (TimeSheetOfDeductionItem dedItem : duplicatitedworkTime) {
			sumTime = sumTime.addMinutes(dedItem.calcTotalTime().valueAsMinutes());
		}
		return sumTime;
	}

	/**
	 * 休憩時間帯の合計時間を算出する
	 * 
	 * @param deductionTimeSheetList
	 * @return
	 */
	public AttendanceTime calcDeductionTotalTime(List<TimeSheetOfDeductionItem> deductionItemTimeSheetList,DeductionAtr atr) {
		AttendanceTime totalTime = new AttendanceTime(0);
		for (TimeSheetOfDeductionItem deductionItemTimeSheet : deductionItemTimeSheetList) {
			totalTime.addMinutes(deductionItemTimeSheet.calcTotalTime().valueAsMinutes());
		}
		return totalTime;
	}

	// /**
	// * 引数の時間帯に重複する休憩時間帯の合計時間（分）を返す
	// * @param baseTimeSheet
	// * @return
	// */
	// public int sumBreakTimeIn(TimeSpanForDailyCalc baseTimeSheet) {
	// return this.breakTimeSheet.sumBreakTimeIn(baseTimeSheet);
	// }
	//
	/**
	 * 算出された休憩時間の合計を取得する
	 * 
	 * @param dedAtr
	 * @return
	 */
	public DeductionTotalTime getTotalBreakTime(DeductionAtr dedAtr) {
		DeductionTotalTime dedTotalTime;
		switch (dedAtr) {
		case Appropriate:
			dedTotalTime = getDeductionTotalTime(forDeductionTimeZoneList.stream()
					.filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()),dedAtr);
			break;
		case Deduction:
			dedTotalTime = getDeductionTotalTime(forRecordTimeZoneList.stream()
					.filter(tc -> tc.getDeductionAtr().isBreak()).collect(Collectors.toList()),dedAtr);
			break;
		default:
			throw new RuntimeException("unknown DeductionAtr" + dedAtr);
		}
		return dedTotalTime;
	}

	/**
	 * 算出された外出時間の合計を取得する
	 * 
	 * @param dedAtr
	 * @return
	 */
	public List<DeductionTotalTime> getTotalGoOutTime(DeductionAtr dedAtr) {
		List<DeductionTotalTime> dedTotalTimeList = new ArrayList<>();
		switch (dedAtr) {
		case Appropriate:
			dedTotalTimeList.add(
					getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
																		   .filter(tc -> tc.getGoOutReason().get().isPrivate()).collect(Collectors.toList()),dedAtr));
			dedTotalTimeList.add(
					getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isCompensation()).collect(Collectors.toList()),dedAtr));
			dedTotalTimeList.add(
					getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isPublic()).collect(Collectors.toList()),dedAtr));
			dedTotalTimeList.add(
					getDeductionTotalTime(forDeductionTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isUnion()).collect(Collectors.toList()),dedAtr));
			break;
		case Deduction:
			dedTotalTimeList.add(
					getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isPrivate()).collect(Collectors.toList()),dedAtr));
			dedTotalTimeList.add(
					getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isCompensation()).collect(Collectors.toList()),dedAtr));
			dedTotalTimeList.add(
					getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isPublic()).collect(Collectors.toList()),dedAtr));
			dedTotalTimeList.add(
					getDeductionTotalTime(forRecordTimeZoneList.stream().filter(tc -> tc.getDeductionAtr().isGoOut())
							.filter(tc -> tc.getGoOutReason().get().isUnion()).collect(Collectors.toList()),dedAtr));
			break;
		default:
			throw new RuntimeException("unknown DeductionAtr" + dedAtr);
		}
		return dedTotalTimeList;
	}

	/**
	 * 法定内・外、相殺時間から合計時間算出
	 * 
	 * @param deductionTimeSheetList
	 * @return
	 */
	public DeductionTotalTime getDeductionTotalTime(List<TimeSheetOfDeductionItem> deductionTimeSheetList,DeductionAtr atr) {
		AttendanceTime statutoryTotalTime = calcDeductionTotalTime(deductionTimeSheetList.stream()
				// .filter(tc -> tc) 一時的に
				.collect(Collectors.toList()),atr);
		AttendanceTime excessOfStatutoryTotalTime = calcDeductionTotalTime(deductionTimeSheetList.stream()
				// .filter(tc -> tc.getWithinStatutoryAtr().isExcessOfStatutory()) 一時的に
				.collect(Collectors.toList()),atr);
		return DeductionTotalTime.of(
				TimeWithCalculation
						.sameTime(statutoryTotalTime.addMinutes(excessOfStatutoryTotalTime.valueAsMinutes())),
				TimeWithCalculation.sameTime(statutoryTotalTime),
				TimeWithCalculation.sameTime(excessOfStatutoryTotalTime));
	}

	/**
	 * 計算を行う範囲に存在する控除時間帯の抽出
	 * 
	 * @param workTimeSpan
	 *            計算範囲
	 * @return 計算範囲内に存在する控除時間帯
	 */
	public List<TimeSheetOfDeductionItem> getCalcRange(TimeSpanForDailyCalc workTimeSpan) {
		return forDeductionTimeZoneList.stream().filter(tc -> workTimeSpan.getTimeSpan().contains(tc.getTimeSheet().getTimeSpan()))
				.collect(Collectors.toList());
	}

	/**
	 * 法定内区分を法定外へ変更する
	 * 
	 * @return 法定内区分変更後の控除時間帯
	 */
	public List<TimeSheetOfDeductionItem> replaceStatutoryAtrToExcess() {
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for (TimeSheetOfDeductionItem deductionTimeSheet : forDeductionTimeZoneList) {
			returnList.add(deductionTimeSheet.createWithExcessAtr());
		}
		return returnList;
	}

	/**
	 * 流動休憩開始までの間にある外出分、休憩をずらす
	 */
	public void includeUntilFluidBreakTimeStart() {

	}

	/**
	 * 休憩時間帯を作成する
	 * 
	 * @return 休憩時間帯
	 */

	public static List<TimeSheetOfDeductionItem> getBreakTimeSheet(WorkTimeDivision workTimeDivision,
			Optional<FixedRestCalculateMethod> calcRest, Optional<FlowWorkRestSettingDetail> flowDetail,
			List<BreakTimeOfDailyAttd> breakTimeOfDailyList, Optional<OutingTimeOfDailyAttd> goOutTimeSheetList) {
		List<TimeSheetOfDeductionItem> timeSheets = new ArrayList<>();
		/* 流動orフレックスかどうか判定 */
		if (!workTimeDivision.isfluidorFlex()) {
			/* 固定休憩時間帯作成 */
			timeSheets.addAll(getFixedBreakTimeSheet(calcRest, breakTimeOfDailyList));
		} else {
			/* 流動休憩時間帯作成 */
			timeSheets.addAll(getFluidBreakTimeSheet(flowDetail, true, breakTimeOfDailyList, goOutTimeSheetList));
		}

		// List<TimeSheetOfDeductionItem> dedTimeSheet = new
		// ArrayList<TimeSheetOfDeductionItem>();
		//
		// for(Optional<BreakTimeOfDailyPerformance> OptionalTimeSheet : timeSheets) {
		// if(OptionalTimeSheet.isPresent()) {
		// for(BreakTimeSheet timeSheet : OptionalTimeSheet.get().getBreakTimeSheets())
		// dedTimeSheet.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new
		// TimeZoneRounding(timeSheet.getStartTime().getTimeWithDay(),timeSheet.getEndTime().getTimeWithDay(),null)
		// , new
		// TimeSpanForDailyCalc(timeSheet.getStartTime().getTimeWithDay(),timeSheet.getEndTime().getTimeWithDay())
		// , Collections.emptyList()
		// , Collections.emptyList()
		// , Collections.emptyList()
		// , Optional.empty()
		// , Finally.empty()
		// , Finally.of(BreakClassification.BREAK)
		// , DeductionClassification.BREAK));
		// }
		// }
		return timeSheets;
	}

	/**
	 * 固定勤務 時に就業時間帯orスケマスタから設定を取得する
	 * 
	 * @param restCalc 固定給系の計算方法
	 * @return 休 時間帯
	 */
	public static List<TimeSheetOfDeductionItem> getFixedBreakTimeSheet(Optional<FixedRestCalculateMethod> calcRest,
			List<BreakTimeOfDailyAttd> breakTimeOfDailyList) {
		// 就業時間帯を参照
		if (calcRest.get().isReferToMaster()) {
			return breakTimeOfDailyList.stream().filter(tc -> tc.getBreakType().isReferWorkTime())
					.findFirst().map(c ->  c.changeAllTimeSheetToDeductionItem())
					.orElse(new ArrayList<>());
		}
		// スケを参照
		else {
			return breakTimeOfDailyList.stream().filter(tc -> tc.getBreakType().isReferSchedule())
					.findFirst().map(c ->  c.changeAllTimeSheetToDeductionItem())
					.orElse(new ArrayList<>());
		}
	}

	/**
	 * 流動勤務 休 設定取得
	 * 
	 * @param calcMethod
	 *            流動休 の計算方
	 * @param isFixedBreakTime
	 *            流動固定休 を使用する区分
	 * @param noStampSet
	 *            休 未打刻時 休設定
	 * @return 休 時間帯
	 */
	public static List<TimeSheetOfDeductionItem> getFluidBreakTimeSheet(Optional<FlowWorkRestSettingDetail> flowDetail,
			boolean isFixedBreakTime, List<BreakTimeOfDailyAttd> breakTimeOfDailyList,
			Optional<OutingTimeOfDailyAttd> goOutTimeSheetList) {
		if (isFixedBreakTime) {
			switch (flowDetail.get().getFlowFixedRestSetting().getCalculateMethod()) {
			// 予定を参照する
			case REFER_SCHEDULE:
				// if(予定から参照するかどうか)
				// 参照する
				return getReferenceTimeSheet(BreakType.REFER_SCHEDULE, breakTimeOfDailyList);
			// しない
			// return getReferenceTimeSheetFromWorkTime();
			// マスタを参照
			case REFER_MASTER:
				return getReferenceTimeSheet(BreakType.REFER_WORK_TIME, breakTimeOfDailyList);
			// 参照せずに打刻をする
			case STAMP_WHITOUT_REFER:
				if(goOutTimeSheetList.isPresent()) {
					return goOutTimeSheetList.get().changeAllTimeSheetToDeductionItem();
				}
				else {
					return Collections.emptyList();
				}
			default:
				throw new RuntimeException(
						"unKnown calcMethod" + flowDetail.get().getFlowFixedRestSetting().getCalculateMethod());
			}
		}
		return Collections.emptyList();
	}

	/**
	 * 流動固定休憩の計算方法が指定された休憩種類の日別計算用休憩時間帯クラスを取得する
	 * 
	 * @return 日別勤怠の休憩時間帯クラス
	 */
	public static List<TimeSheetOfDeductionItem> getReferenceTimeSheet(BreakType breakType, List<BreakTimeOfDailyAttd> breakTimeOfDailyList) {
		//予定もしくは実績の「日別勤怠の休憩時間帯」
		Optional<BreakTimeOfDailyAttd> sheduleOrRecord = breakTimeOfDailyList.stream()
				.filter(tc -> tc.getBreakType().equals(breakType))
				.findFirst();
		
		if(!sheduleOrRecord.isPresent())
			return Collections.emptyList();
		
		return sheduleOrRecord.get().changeAllTimeSheetToDeductionItem();
	}


	/**
	 * 控除時間帯の仮確定(流動用)
	 * アルゴリズム：流動休憩用の時間帯作成
	 * @param dedAtr 控除区分
	 * @param todayWorkType 勤務種類
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param integrationOfDaily 日別実績(Work)
	 * @param oneDayOfRange 日別計算用時間帯
	 * @param attendanceLeaveWork 日別実績の出退勤
	 * @param predetermineTimeSetForCalc 所定時間設定(計算用クラス)
	 * @return 控除項目の時間帯(List)
	 */
	public static List<TimeSheetOfDeductionItem> provisionalDecisionOfDeductionTimeSheet(
			DeductionAtr dedAtr,
			WorkType todayWorkType,
			IntegrationOfWorkTime integrationOfWorkTime,
			IntegrationOfDaily integrationOfDaily,
			TimeSpanForDailyCalc oneDayOfRange,
			TimeLeavingOfDailyAttd attendanceLeaveWork,
			PredetermineTimeSetForCalc predetermineTimeSetForCalc) {

		// 固定休憩か流動休憩か確認する
		if (integrationOfWorkTime.getFlowWorkRestTimezone(todayWorkType).get().isFixRestTime()) {// 固定休憩の場合
			return createDedctionTimeSheet(
					dedAtr,
					todayWorkType,
					integrationOfWorkTime,
					integrationOfDaily,
					oneDayOfRange,
					attendanceLeaveWork,
					predetermineTimeSetForCalc);
		} 
		else {// 流動休憩の場合
			// switch(fluidWorkSetting.getRestSetting().getFluidWorkBreakSettingDetail().getFluidBreakTimeSet().getCalcMethod())
			// {
			// //マスタを参照する
			// case ReferToMaster:
			//
			// //マスタと打刻を併用する
			// case ConbineMasterWithStamp:
			//
			// //参照せずに打刻する
			// //case StampWithoutReference:
			//
			// }
		}
		return Collections.emptyList();
	}
	
	//

	// /**
	// * 控除時間帯の作成 流動勤務で固定休憩の場合にシフトから計算する場合の処理の事
	// */
	// public void createLateTimeSheetForFluid(
	// WithinWorkTimeFrame withinWorkTimeFrame,
	// FluidWorkSetting fluidWorkSetting,
	// CalculationRangeOfOneDay oneDayRange) {
	//
	// //計算範囲の取得
	// AttendanceTime calcRange=
	// oneDayRange.getPredetermineTimeSetForCalc().getOneDayRange();
	// //控除時間帯の取得 ・・・保科君が作成済みの処理を呼ぶ
	// List<TimeSheetOfDeductionItem> deductionTimeSheet =
	// this.collectDeductionTimes(
	// dailyGoOutSheet,
	// oneDayRange,
	// CommonSet,
	// attendanceLeaveWork,
	// fixedCalc,
	// workTimeDivision,
	// noStampSet,
	// fluidSet,
	// acqAtr);
	// //控除時間帯同士の重複部分を補正
	// deductionTimeSheet = new
	// DeductionTimeSheetAdjustDuplicationTime(deductionTimeSheet)
	// .reCreate(WorkTimeMethodSet setMethod,RestClockManageAtr
	// clockManage,WorkTimeDailyAtr workTimeDailyAtr);
	// //控除合計時間クラスを作成 不要な可能性あり
	// //→合計時間を保持しておくためにこのインスタンスは必要(2017.11.27 by hoshina)
	// DeductionTotalTimeForFluidCalc deductionTotalTime = new
	// DeductionTotalTimeForFluidCalc(new AttendanceTime(0),new AttendanceTime(0));
	// //流動休憩時間帯を取得する
	// List<FluRestTimeSetting> fluRestTimeSheetList =
	// fluidWorkSetting.getWeekdayWorkTime().getRestTime().getFluidRestTime().getFluidRestTimes();
	// //外出取得開始時刻を作成する
	// AttendanceTime getGoOutStartClock = new
	// AttendanceTime(withinWorkTimeFrame.getCalcrange().getStart().valueAsMinutes());
	// //一時的に作成(最後に控除時間帯へ追加する休憩時間帯リスト)
	// List<TimeSheetOfDeductionItem> restTimeSheetListForAddToDeductionList = new
	// ArrayList<>();
	//
	// //流動休憩時間帯分ループ
	// for(int nowLoop = 0 ; nowLoop <fluRestTimeSheetList.size(); nowLoop++) {
	//
	// //流動休憩時間帯の作成（引数にgetGoOutStartClockを渡す
	// TimeSheetOfDeductionItem restTimeSheet =
	// deductionTotalTime.createDeductionFluidRestTime(
	// fluRestTimeSheetList.get(nowLoop),
	// getGoOutStartClock,
	// nowLoop,
	// fluidWorkSetting,
	// deductionTimeSheet
	// /*外出と流動休憩の関係設定*/);
	// //作成した時間帯を休憩時間帯リストに格納
	// restTimeSheetListForAddToDeductionList.add(restTimeSheet);
	// getGoOutStartClock = new
	// AttendanceTime(restTimeSheet.calcrange.getStart().valueAsMinutes());
	// }
	// //退勤時刻までの外出の処理
	// deductionTotalTime.collectDeductionTotalTime(list, getGoOutStartClock,
	// fluidWorkSetting, roopNo);
	// //控除時間帯をソート
	//
	// }

	/**
	 * 控除時間中の時間休暇相殺時間の計算
	 * 
	 * @return
	 */
	public DeductionOffSetTime calcTotalDeductionOffSetTime(LateTimeOfDaily lateTimeOfDaily,
			LateTimeSheet lateTimeSheet, LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily,
			LeaveEarlyTimeSheet leaveEarlyTimeSheet) {

		// 遅刻相殺時間の計算
		// DeductionOffSetTime lateDeductionSffSetTime =
		// lateTimeSheet.calcDeductionOffSetTime(lateTimeOfDaily.getTimePaidUseTime(),DeductionAtr.Appropriate);
		DeductionOffSetTime lateDeductionSffSetTime = new DeductionOffSetTime(new AttendanceTime(0),
				new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0));
		// 早退相殺時間の計算

		// 外出相殺時間の計算

		// 3つの相殺時間を合算する
		DeductionOffSetTime timeVacationOffSetTime = lateDeductionSffSetTime/* + 早退相殺時間 + 外出相殺時間 */;// 合算するメソッドは別途考慮

		return timeVacationOffSetTime;
	}

	/**
	 * 受け取った計算範囲へ控除時間帯を補正＆絞り込む
	 * 
	 * @param timeSpan
	 *            計算範囲
	 * @param atr
	 *            控除区分
	 * @return 控除項目の時間帯リスト(控除区分に従ってＬｉｓｔ取得)
	 */
	public List<TimeSheetOfDeductionItem> getDupliRangeTimeSheet(TimeSpanForDailyCalc timeSpan, DeductionAtr atr) {
		List<TimeSheetOfDeductionItem> dedList = getDedListWithAtr(atr);
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for (TimeSheetOfDeductionItem timeSheet : dedList) {
			val dupCalcRange = timeSheet.getTimeSheet().getDuplicatedWith(timeSpan);
			if (dupCalcRange.isPresent()) {
				TimeSheetOfDeductionItem divideStartTime = timeSheet.reCreateOwn(dupCalcRange.get().getTimeSpan().getStart(), false);
				TimeSheetOfDeductionItem correctAfterTimeSheet = divideStartTime.reCreateOwn(dupCalcRange.get().getTimeSpan().getEnd(), true);
				returnList.add(correctAfterTimeSheet);
			}
		}
		return returnList;
	}
	
	//public void 
	/**
	 * 控除区分に従って控除リスト取得
	 * 
	 * @param atr
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> getDedListWithAtr(DeductionAtr atr) {
		if (atr.isDeduction()) {
			return this.forDeductionTimeZoneList;
		} else {
			return this.forRecordTimeZoneList;
		}
	}
	
	/**
	 * 
	 * @param attendanceLeaveWork
	 * @param shortTimeSheet
	 * @param workTimeShortTimeSet 就業時間帯の短時間勤務設定
	 * @return
	 */
	public static List<TimeSheetOfDeductionItem> getShortTime(TimeLeavingOfDailyAttd attendanceLeaveWork, List<ShortWorkingTimeSheet> shortTimeSheet,boolean isCalcShortTime){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		for(ShortWorkingTimeSheet sts:shortTimeSheet) {
			if(sts.getStartTime() == null || sts.getEndTime() == null) continue;

			if(isCalcShortTime) {
				//出退勤と重複している部分削除
				val notDupSpanList = attendanceLeaveWork.getNotDuplicateSpan(new TimeSpanForDailyCalc(sts.getStartTime(),sts.getEndTime()));
				for(TimeSpanForDailyCalc notDupRange : notDupSpanList)
					returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(notDupRange, 
																							  new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
																							  new ArrayList<>(),
																							  new ArrayList<>(),
																							  WorkingBreakTimeAtr.NOTWORKING,
																							  Finally.empty(),
																							  Finally.empty(),
																							  decisionShortTimeAtr(attendanceLeaveWork.getTimeLeavingWorks(), sts),
																							  DeductionClassification.CHILD_CARE,
																							  Optional.of(getChildCareAtr(sts.getChildCareAttr()))
																							  ));
			}
			else {
				returnList.add(TimeSheetOfDeductionItem.createTimeSheetOfDeductionItemAsFixed(new TimeSpanForDailyCalc(sts.getStartTime(), sts.getEndTime()), 
																							  new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_DOWN),
																							  new ArrayList<>(),
																							  new ArrayList<>(),
																							  WorkingBreakTimeAtr.NOTWORKING,
																							  Finally.empty(),
																							  Finally.empty(),
																							  decisionShortTimeAtr(attendanceLeaveWork.getTimeLeavingWorks(), sts),
																							  DeductionClassification.CHILD_CARE,
																							  Optional.of(getChildCareAtr(sts.getChildCareAttr()))
																							  ));
			}
		}
		return returnList;
	}
	
	private static ChildCareAtr getChildCareAtr(ChildCareAttribute childCareAttr) {
		if(childCareAttr==ChildCareAttribute.CHILD_CARE) {
			return ChildCareAtr.CHILD_CARE;
		}
		return ChildCareAtr.CARE;
	}
	
	/**
	 * 短時間勤務区分の判断
	 * @param timeLeaves　出退勤
	 * @param shortTimeSheet 短時間勤務時間帯
	 * @return
	 */
	public static Optional<ShortTimeSheetAtr> decisionShortTimeAtr(List<TimeLeavingWork> timeLeaves, ShortWorkingTimeSheet shortTimeSheet) {
		Optional<ShortTimeSheetAtr> returnEnum = Optional.empty(); 
		//if(shortTimeSheet.getStartTime() == null || shortTimeSheet.getEndTime() == null) return returnEnum; 
		for(TimeLeavingWork tlw : timeLeaves) {
			TimeSpanForDailyCalc shortTimeSpan = new TimeSpanForDailyCalc(shortTimeSheet.getStartTime(), shortTimeSheet.getEndTime());
			//短時間.終了 <= 出勤
			//短時間.開始 <= 出勤 <= 短時間.終了
			if(tlw.getTimespan().getStart().greaterThan(shortTimeSheet.getEndTime())
			 ||shortTimeSpan.contains(tlw.getTimespan().getStart())) {
				returnEnum = Optional.of(ShortTimeSheetAtr.BEFORE_ATTENDANCE);
			}
			//退勤 <= 短時間.開始
			//短時間.開始 <= 退勤 <= 短時間.終了
			else if(shortTimeSheet.getStartTime().greaterThan(tlw.getTimespan().getEnd())
				  ||shortTimeSpan.contains(tlw.getTimespan().getEnd())) {
				returnEnum = Optional.of(ShortTimeSheetAtr.AFTER_LEAVING);
			}
		}
		return returnEnum;
	}

	/**
	 * 丸め処理
	 * @return
	 */
	public static List<TimeSheetOfDeductionItem> rounding(DeductionAtr dedAtr,List<TimeSheetOfDeductionItem> timeSheetOfDeductionItemList,TotalRoundingSet goOutSet,
														 TimeWithDayAttr mostFastWithinFrameOclock,TimeWithDayAttr mostLateWithinFrameOclock,
														 TimeSpanForDailyCalc oneDayRange){
		//if(goOutSet.ge){
//		if(false) {
//			val correctList = perRounding(timeSheetOfDeductionItemList,mostFastWithinFrameOclock,mostLateWithinFrameOclock,oneDayRange);
//			return correctList;
//		}
		return timeSheetOfDeductionItemList;
	}
	
	/**
	 * 計上時間の丸め設定.丸め方法 = 個別丸め　の分岐後処理は
	 * ここで行う。
	 * @param timeSheetOfDeductionItemList
	 * @param mostFastWithinFrameOclock　一番前の就業時間の開始時刻
	 * @param mostLateWithinFrameOclock 一番後ろの就業時間の終了時刻
	 * @return
	 */
	public static List<TimeSheetOfDeductionItem> perRounding(List<TimeSheetOfDeductionItem> timeSheetOfDeductionItemList,TimeWithDayAttr mostFastWithinFrameOclock,TimeWithDayAttr mostLateWithinFrameOclock,
															 TimeSpanForDailyCalc oneDayRange){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		//早出と就業時間帯.開始を跨いでる物の分割
		List<TimeSheetOfDeductionItem> containedMostFast = timeSheetOfDeductionItemList.stream().filter(tc -> tc.getTimeSheet().getTimeSpan().contains(mostFastWithinFrameOclock)).collect(Collectors.toList());
		//↑のリストに対して独自丸め実行
		returnList.addAll(ownRounding(containedMostFast, mostFastWithinFrameOclock));
		
		//就業時間帯.終了と残業を跨いでる物の分割
		List<TimeSheetOfDeductionItem> containedMostLate = timeSheetOfDeductionItemList.stream().filter(tc -> tc.getTimeSheet().getTimeSpan().contains(mostLateWithinFrameOclock)).collect(Collectors.toList());
		//↑のリストに対して独自丸め実行
		returnList.addAll(ownRounding(containedMostLate, mostLateWithinFrameOclock));
		
		List<TimeSheetOfDeductionItem> recreateList = new ArrayList<>();
		for(TimeSheetOfDeductionItem item : timeSheetOfDeductionItemList) {
			if(!containedMostFast.contains(item) && !containedMostLate.contains(item) ) {
				recreateList.add(item);
			}
		}
		recreateList.addAll(returnList);
		recreateList = recreateList.stream().sorted((first,second) -> first.getTimeSheet().getTimeSpan().getStart().compareTo(second.getTimeSheet().getTimeSpan().getEnd())).collect(Collectors.toList());
		
		
		//休憩・外出時間帯の抜き出し & 各控除時間帯を丸め処理
		//recreateList.stream().filter(tc -> tc.getDeductionAtr().isGoOut()).;
		
		//各控除時間帯を丸目後時刻補正
		List<TimeSheetOfDeductionItem> correctedTimeList = correctTimeAfterCorrect(returnList,oneDayRange,mostFastWithinFrameOclock,mostLateWithinFrameOclock);
		//外出・休憩丸め設定をクリア
		correctedTimeList.forEach(tc -> {
			if(tc.getDeductionAtr().isBreak() || tc.getDeductionAtr().isGoOut())
				tc.getRounding().setDefaultDataRoundingDown();;
		});
		return correctedTimeList;
	}
	
	/**
	 * 対象の時間帯を基準点の前後時間帯へと切り離す 
	 * @param containedList　対象時間帯
	 * @param baseOclock　基準時間
	 * @return　前後時間帯
	 */
	private static List<TimeSheetOfDeductionItem> ownRounding(List<TimeSheetOfDeductionItem> containedList,TimeWithDayAttr baseOclock){
		List<TimeSheetOfDeductionItem> returnList = new ArrayList<>();
		containedList.forEach(tc ->{
			//残業と就業時間帯を跨いでいる物の分割(開始～基準点)
			val devidedTimeSheet1 = divideBaseTime(tc,baseOclock,true);
			//残業と就業時間帯を跨いでいる物の分割(基準点～終了)
			val devidedTimeSheet2 = divideBaseTime(tc,baseOclock,false);
			
			returnList.add(devidedTimeSheet1);
			returnList.add(devidedTimeSheet2);
		});
		return returnList;
	}
	
	/**
	 * 基準点を基に、分割
	 * @param baseTimeSheet　分割する時間帯
	 * @param baseTime　基準時間
	 * @param isBefore　基準時間より前に変換する
	 * @return　変換後時間帯
	 */
	private static TimeSheetOfDeductionItem divideBaseTime(TimeSheetOfDeductionItem baseTimeSheet,TimeWithDayAttr baseTime,boolean isBefore) {
		//isBeforeを基に、開始～基準　or 基準～終了　の時間帯へ変換
		val devideTimeSheet = baseTimeSheet.reCreateOwn(baseTime, isBefore);
		//丸め(↑の開始からの距離を丸める)
		val roundingValue1 = devideTimeSheet.getRounding().round(devideTimeSheet.getTimeSheet().getTimeSpan().lengthAsMinutes());
		//丸め後に再作成(開始 + ↑の丸め後の値を基準点とし、開始～開始＋丸め後値で再作成する)
		return devideTimeSheet.reCreateOwn(devideTimeSheet.getTimeSheet().getTimeSpan().getStart().forwardByMinutes(roundingValue1), isBefore);
	}

	
	
	/**
	 * 丸め後の重複部分を補正
	 * @param returnList
	 * @param oneDayRange
	 * @return
	 */
	private static List<TimeSheetOfDeductionItem> correctTimeAfterCorrect(List<TimeSheetOfDeductionItem> returnList,TimeSpanForDailyCalc oneDayRange,TimeWithDayAttr startOclock, TimeWithDayAttr endOclock) {
		//returnListの末尾1個手前までループ
		for(int beforeNo = 0 ; beforeNo < returnList.size() - 1 ; beforeNo++) {
			TimeSpanForDailyCalc maxCorrectRange = getMaxCorrectRange(returnList.get(beforeNo),oneDayRange,startOclock,endOclock);
			TimeWithDayAttr baseTime = maxCorrectRange.getTimeSpan().getStart();
			if(returnList.get(beforeNo).getTimeSheet().getTimeSpan().contains(baseTime)) {
				
				//最大範囲を超えている分手前にずらす & 控除時間帯リストを更新
				returnList = moveBack(returnList,beforeNo,maxCorrectRange);
			}
			val nextTimeSheet = returnList.get(beforeNo + 1);
			//重複の判断処理
			if(returnList.get(beforeNo).getTimeSheet().getDuplicatedWith(nextTimeSheet.getTimeSheet()).isPresent()) {
				//重複している分次の時間帯を後ろにずらす
				returnList.set(beforeNo + 1, nextTimeSheet.replaceTimeSpan(Optional.of(returnList.get(beforeNo).getTimeSheet().reviseToAvoidDuplicatingWith(nextTimeSheet.getTimeSheet()))));
			}
		}
		return returnList;
	}

	/**
	 * 手前にずらす処理
	 * @param returnList　処理対象となる控除項目時間帯リスト
	 * @param listNo 処理対象のリスト番号
	 * @param maxCorrectRange 最大補正時間
	 * @return　補正後時間
	 */
	private static List<TimeSheetOfDeductionItem> moveBack(List<TimeSheetOfDeductionItem> returnList, int listNo,TimeSpanForDailyCalc maxCorrectRange) {
		TimeSheetOfDeductionItem targetTimeSheet = returnList.get(listNo);
		Optional<TimeSpanForDailyCalc> dupTimeSpan = targetTimeSheet.getTimeSheet().getDuplicatedWith(maxCorrectRange);
		//手前にずらす
		targetTimeSheet = targetTimeSheet.replaceTimeSpan(Optional.of(targetTimeSheet.getTimeSheet().shiftBack(dupTimeSpan.orElse(new TimeSpanForDailyCalc(new TimeWithDayAttr(0), new TimeWithDayAttr(0))).getTimeSpan().lengthAsMinutes()))); 
		returnList.set(listNo, targetTimeSheet);
		//手前件数分ループ
		for(int nowListNo = listNo - 1 ; 0 <= nowListNo ; nowListNo--) {
			//手前の時間帯取得
			val duplicateRange = targetTimeSheet.getTimeSheet().getTimeSpan().getDuplicatedWith(returnList.get(nowListNo).getTimeSheet().getTimeSpan());
			//一様Optionalチェック(EAには無し)
			if(duplicateRange.isPresent()) {
				//手前にずらす
				TimeSpanForDailyCalc afterMoveTimeSpan = returnList.get(nowListNo).getTimeSheet().shiftBack(duplicateRange.get().lengthAsMinutes());
				//↑でずらした結果、最大範囲を含んでいるか
				if(afterMoveTimeSpan.getTimeSpan().contains(maxCorrectRange.getTimeSpan())) {
					//時間帯補正
					afterMoveTimeSpan = new TimeSpanForDailyCalc(maxCorrectRange.getTimeSpan().getStart(), afterMoveTimeSpan.getTimeSpan().getEnd());
				}
				//時間帯のセット
				returnList.set(nowListNo, returnList.get(nowListNo).replaceTimeSpan(Optional.of(afterMoveTimeSpan)));
			}
		}
		
		return returnList;
	}

	/**
	 * 最大補正範囲を取得
	 * @param oneDayRange 1日の範囲
	 * @return 最大補正範囲
	 */
	private static TimeSpanForDailyCalc getMaxCorrectRange(TimeSheetOfDeductionItem deductionTimeSheet ,TimeSpanForDailyCalc oneDayRange,TimeWithDayAttr startOclock, TimeWithDayAttr endOclock) {
		//就業時間内.開始 > 控除時間帯.開始
		if(startOclock.greaterThan(deductionTimeSheet.getTimeSheet().getTimeSpan().getStart())) {
			return new TimeSpanForDailyCalc(oneDayRange.getTimeSpan().getStart(), startOclock);
		}
		//>就業時間内.終了 > 控除時間帯.開始
		if(endOclock.greaterThan(deductionTimeSheet.getTimeSheet().getTimeSpan().getStart())){
			return new TimeSpanForDailyCalc(oneDayRange.getTimeSpan().getStart(),startOclock);
		}
		else {
			return new TimeSpanForDailyCalc(endOclock, oneDayRange.getTimeSpan().getEnd());
		}
	}

		
}
