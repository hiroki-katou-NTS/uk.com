package nts.uk.ctx.at.shared.dom.worktime.algorithm.difftimecorrection;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.CollectionAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.JoggingWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicateStateAtr;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.DuplicationStatusOfTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone.RangeOfDayTimeZoneService;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
//import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
//import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

//時差勤務の時刻補正
@Stateless
public class DiffTimeCorrectionServiceImpl implements DiffTimeCorrectionService {

	// private JoggingWorkTime difftime;
	//
	// private DiffTimeWorkSetting difftimeSetting;

	@Inject
	private RangeOfDayTimeZoneService rangeOfDayTimeZoneService;

	@Override
	public void correction(JoggingWorkTime difftime, DiffTimeWorkSetting difftimeSetting,
			PredetemineTimeSetting predTime) {
		// this.difftime = difftime;
		// this.difftimeSetting = difftimeSetting;
		// 時差勤務時間が変動可能な時間かチェックする
		// if (this.checkCanUpdateDiffTime(difftime, difftimeSetting)) {
		// 打刻反映範囲を補正
		this.updateStamp(difftime, difftimeSetting, predTime);
		// 所定時間帯を補正

		this.predtimeUpdate(difftime, predTime);
		// 休憩時間帯を補正

		this.restTimeUpdate(difftime, difftimeSetting);
		// 就業時間帯を補正
		this.workTimeUpdate(difftime, difftimeSetting, predTime);
		// }
	}

	// 時差勤務時間が変動可能な時間かチェックする
//	private boolean checkCanUpdateDiffTime(JoggingWorkTime difftime, DiffTimeWorkSetting difftimeSetting) {
//
//		AttendanceTime changeTime = null;
//		if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
//			changeTime = difftimeSetting.getChangeExtent().getAheadChange();
//		} else {
//			changeTime = difftimeSetting.getChangeExtent().getBehindChange();
//		}
//
//		// 変動可能時間を超えているかチェックする
//		if (changeTime.lessThan(difftime.getTime())) {
//			return false;
//		}
//		return true;
//	}

	// 打刻反映範囲を補正
	private List<StampReflectTimezone> updateStamp(JoggingWorkTime difftime, DiffTimeWorkSetting difftimeSetting,
			PredetemineTimeSetting predTime) {
		if (!difftimeSetting.getStampReflectTimezone().getStampReflectTimezone().isEmpty()) {
			boolean updateStartTime = difftimeSetting.getStampReflectTimezone().isUpdateStartTime();

			if (difftime.getAtr().equals(CollectionAtr.BEFORE))// 時間帯の昇順
			{
				if (updateStartTime) {
					difftimeSetting.getStampReflectTimezone().getStampReflectTimezone().stream()
							.sorted((a, b) -> a.getStartTime().compareTo(b.getStartTime())).forEach(item -> {
								// 時刻をズラす
								TimeSpanForCalc time = this.shiftTime(difftime, item.getStartTime(), item.getEndTime(),predTime);
								item.updateStartTime(time.getStart());
							});
				}

				// 補正した時間帯の時系列の最後尾の時間帯を取得する
				StampReflectTimezone lastItem = difftimeSetting.getStampReflectTimezone().getStampReflectTimezone()
						.get(difftimeSetting.getStampReflectTimezone().getStampReflectTimezone().size());
				// 1日の範囲の最終チェック
				TimeSpanForCalc lastItemCalc = this.oneDaycheck(difftime, null, null, lastItem.getStartTime(),
						lastItem.getEndTime(), predTime);
				// 取得した時間帯の終了時刻を1日の時間の終了時刻に更新する
				difftimeSetting.getStampReflectTimezone().getStampReflectTimezone()
						.get(difftimeSetting.getStampReflectTimezone().getStampReflectTimezone().size())
						.updateEndTime(lastItemCalc.getEnd());
			} else// 時間帯の降順
			{
				if (updateStartTime) {
					difftimeSetting.getStampReflectTimezone().getStampReflectTimezone().stream()
							.sorted((a, b) -> b.getStartTime().compareTo(a.getStartTime())).forEach(item -> {
								// 時刻をズラす
								TimeSpanForCalc time = this.shiftTime(difftime, item.getStartTime(), item.getEndTime(),predTime);
								item.updateEndTime(time.getEnd());
							});
				}
				// 補正した時間帯の時系列の先頭の時間帯を取得する
				StampReflectTimezone firstItem = difftimeSetting.getStampReflectTimezone().getStampReflectTimezone()
						.get(0);
				// 1日の範囲の最終チェック
				TimeSpanForCalc firstItemCalc = this.oneDaycheck(difftime, firstItem.getStartTime(),
						firstItem.getEndTime(), null, null, predTime);
				// 取得した時間帯の開始時刻を1日の時間の開始時刻に更新する
				difftimeSetting.getStampReflectTimezone().getStampReflectTimezone().get(0)
						.updateStartTime(firstItemCalc.getStart());
			}
		}
		return difftimeSetting.getStampReflectTimezone().getStampReflectTimezone();
	}

	// 時刻をズラす
	@Override
	public TimeSpanForCalc shiftTime(JoggingWorkTime difftime, TimeWithDayAttr start, TimeWithDayAttr end,PredetemineTimeSetting predTime) {

		TimeWithDayAttr shiftStart = null;
		TimeWithDayAttr shiftEnd = null;
		// パラメータ取得
		if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
			// 時間帯を手前に補正
			// 開始時刻をパラメータ「ズラす時間」だけズラす
			shiftStart = this.preShift(start);
			// 終了時刻をパラメータ「ズラす時間」だけズラす
			shiftEnd = this.preShift(end);
		} else {
			// 時間帯を後ろに補正
			// 開始時刻をパラメータ「ズラす時間」だけズラす
			shiftStart = this.afterShift(start);
			// 終了時刻をパラメータ「ズラす時間」だけズラす
			shiftEnd = this.afterShift(end);
		}
		// 補正後の時間帯が1日の範囲におさまっているか確認
		// １日の範囲を取得
		TimeWithDayAttr startRange = predTime.getStartDateClock();
		TimeWithDayAttr endRange = new TimeWithDayAttr(
				predTime.getStartDateClock().valueAsMinutes() + predTime.getRangeTimeDay().valueAsMinutes());
		//
		DuplicationStatusOfTimeZone statusAtr = this.statusAtr(new TimeSpanForCalc(shiftStart, shiftEnd),
				new TimeSpanForCalc(startRange, endRange));

		// 1日の範囲に収まるように補正
		this.oneDayRangeObtain(statusAtr, new TimeSpanForCalc(shiftStart, shiftEnd),
				new TimeSpanForCalc(startRange, endRange));

		return new TimeSpanForCalc(shiftStart, shiftEnd);
	}

	// 1日の範囲に収まるように補正
	private void oneDayRangeObtain(DuplicationStatusOfTimeZone statusAtr, TimeSpanForCalc fixTime,
			TimeSpanForCalc base) {
		// TODO 社員の日別実績エラー一覧 に関して 対象外
	}

	// 時間帯が範囲に含まれてるか判断
	@Override
	public DuplicationStatusOfTimeZone statusAtr(TimeSpanForCalc base, TimeSpanForCalc compare) {
		// 時間帯が範囲に含まれてるか判断
		DuplicateStateAtr duplicateState = this.rangeOfDayTimeZoneService.checkPeriodDuplication(compare, base);
		DuplicationStatusOfTimeZone returnDupStatus = null;
		switch (duplicateState) {
		case SAME_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.SAME_WORK_TIME;
			break;
		case START_OF_COMPARISON_PERIOD_BETWEEN_REFERENCE_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.BEYOND_THE_START;
			break;
		case END_OF_COMPARISON_PERIOD_WITHIN_REFERENCE_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.BEYOND_THE_END;
			break;
		// 456
		case REFERENCE_PERIOD_FULLY_INCLUDED:
			returnDupStatus = DuplicationStatusOfTimeZone.INCLUSION_OUTSIDE;
			break;
		case REFERENCE_PERIOD_WAS_INCLUDED_START_SAME:
			returnDupStatus = DuplicationStatusOfTimeZone.INCLUSION_OUTSIDE;
			break;
		case REFERENCE_PERIOD_WAS_INCLUDED_END_SAME:
			returnDupStatus = DuplicationStatusOfTimeZone.INCLUSION_OUTSIDE;
			break;
		// 123
		case REFERENCE_PERIOD_COMPLETELY_EMBRACES:
			returnDupStatus = DuplicationStatusOfTimeZone.INCLUSION_INSIDE;
			break;
		case REFERENCE_PERIOD_INCLUDE_START_SAME:
			returnDupStatus = DuplicationStatusOfTimeZone.INCLUSION_INSIDE;
			break;
		case REFERENCE_PERIOD_INCLUDED_END_SAME:
			returnDupStatus = DuplicationStatusOfTimeZone.INCLUSION_INSIDE;
			break;
		// 78910
		case CONTINUOUS_AFTER_REFERENCE_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.NON_OVERLAPPING;
			break;
		case CONTINUOUS_BEFORE_REFERENCE_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.NON_OVERLAPPING;
			break;
		case REFERENCE_PERIOD_BEFORE_COMPARISON_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.NON_OVERLAPPING;
			break;
		case REFERENCE_PERIOD_AFTER_COMPARISON_PERIOD:
			returnDupStatus = DuplicationStatusOfTimeZone.NON_OVERLAPPING;
			break;
		default:
			break;
		}
		return returnDupStatus;
	}

	private TimeWithDayAttr afterShift(TimeWithDayAttr time) {
		// 翌々日23:59 の場合 < 時刻
		if (time.greaterThan(new TimeWithDayAttr(23 * 60 + 59))) {
			return new TimeWithDayAttr(23 * 60 + 59);
		}
		return time;
	}

	private TimeWithDayAttr preShift(TimeWithDayAttr time) {
		// 時刻 < 前日 12:00 の場合
		if (time.lessThan(new TimeWithDayAttr(-12 * 60))) {
			return new TimeWithDayAttr(-12 * 60);
		}
		return time;
	}

	@Override
	public TimeSpanForCalc oneDaycheck(JoggingWorkTime difftime, TimeWithDayAttr startFirst, TimeWithDayAttr endFirst,
			TimeWithDayAttr startLast, TimeWithDayAttr endLast, PredetemineTimeSetting predTime) {
		// 補正区分をチェックする
		if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
			// 取得した時間帯の終了時刻が1日の時間の終了時刻と一致しているかチェックする
			if (!endLast.equals(predTime.getRangeTimeDay())) {
				// 取得した時間帯の終了時刻を1日の時間の終了時刻に更新する
				endLast = new TimeWithDayAttr(
						predTime.getStartDateClock().valueAsMinutes() + predTime.getRangeTimeDay().valueAsMinutes());
				//
			}
			return new TimeSpanForCalc(startLast, endLast);
		} else {
			// 取得した時間帯の開始時刻が1日の時間の開始時刻と一致しているかチェックする
			if (!startFirst.equals(predTime.getRangeTimeDay())) {
				// 取得した時間帯の開始時刻を1日の時間の開始時刻に更新する
				startFirst = new TimeWithDayAttr(predTime.getStartDateClock().valueAsMinutes());
			}
			return new TimeSpanForCalc(startFirst, endFirst);
		}
	}

	// 所定時間帯を補正
	private void predtimeUpdate(JoggingWorkTime difftime, PredetemineTimeSetting predTime) {
		predTime.getPrescribedTimezoneSetting().getLstTimezone().stream().forEach(item -> {
			TimeSpanForCalc timeCalc = this.shiftTime(difftime, item.getStart(), item.getEnd(),predTime);
			item.updateStartTime(timeCalc.getStart());
			item.updateEndTime(timeCalc.getEnd());
		});

		// 補正区分をチェックする
		if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
			// 午前終了時刻を時差勤務時間分マイナスした時間に更新する
			predTime.getPrescribedTimezoneSetting().updateMorningEndTime(
					new TimeWithDayAttr(predTime.getPrescribedTimezoneSetting().getMorningEndTime().valueAsMinutes()
							- difftime.getTime().valueAsMinutes()));
			// 午後開始時刻を時差勤務時間分マイナスした時間に更新する
			predTime.getPrescribedTimezoneSetting().updateAfternoonStartTime(
					new TimeWithDayAttr(predTime.getPrescribedTimezoneSetting().getAfternoonStartTime().valueAsMinutes()
							- difftime.getTime().valueAsMinutes()));
		} else {
			// 午前終了時刻を時差勤務時間分プラスした時間に更新する
			predTime.getPrescribedTimezoneSetting().updateMorningEndTime(
					new TimeWithDayAttr(predTime.getPrescribedTimezoneSetting().getMorningEndTime().valueAsMinutes()
							+ difftime.getTime().valueAsMinutes()));
			// 午後開始時刻を時差勤務時間分プラスした時間に更新する
			predTime.getPrescribedTimezoneSetting().updateAfternoonStartTime(
					new TimeWithDayAttr(predTime.getPrescribedTimezoneSetting().getAfternoonStartTime().valueAsMinutes()
							+ difftime.getTime().valueAsMinutes()));
		}
	}

	// 就業時間帯を補正
	@Override
	public void workTimeUpdate(JoggingWorkTime difftime, DiffTimeWorkSetting difftimeSetting,
			PredetemineTimeSetting predTime) {
		// 就業時間帯を取得
		difftimeSetting.getHalfDayWorkTimezones().stream().forEach(halfDay -> {
			DiffTimeHalfDayWorkTimezone backupHalfDay = halfDay;
			// after change list
			List<TimeSpanForCalc> timeCalcForWork = new ArrayList<>();
			List<TimeSpanForCalc> timeCalcForOT = new ArrayList<>();
			// 時間帯の昇順
			if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
				halfDay.getWorkTimezone().getEmploymentTimezones().stream()
						.sorted((a, b) -> a.getTimezone().getStart().compareTo(b.getTimezone().getStart()));
				halfDay.getWorkTimezone().getOTTimezones().stream()
						.sorted((a, b) -> a.getTimezone().getStart().compareTo(b.getTimezone().getStart()));
			} else {// 時間帯の降順
				halfDay.getWorkTimezone().getEmploymentTimezones().stream()
						.sorted((a, b) -> b.getTimezone().getStart().compareTo(a.getTimezone().getStart()));
				halfDay.getWorkTimezone().getOTTimezones().stream()
						.sorted((a, b) -> b.getTimezone().getStart().compareTo(a.getTimezone().getStart()));
			}
			// List<DiffTimeOTTimezoneSet> otTime =
			// 残業時間帯の場合
			halfDay.getWorkTimezone().getOTTimezones().stream().forEach(item -> {
				if (item.isUpdateStartTime())//
				{
					// 時刻をズラす
					timeCalcForOT
							.add(this.shiftTime(difftime, item.getTimezone().getStart(), item.getTimezone().getEnd(),predTime));
					// ズラした後の重複チェック
					this.checkDuplycateAfterChange();
				} else {
					// TODO 前回のループで処理した時間帯との間に空白の時間帯が生じていないかチェックする
					this.checkHasChange(difftime, backupHalfDay, halfDay);
				}
			});

			// 就業時間帯の場合
			halfDay.getWorkTimezone().getEmploymentTimezones().stream().forEach(item -> {
				// 時刻をズラす
				timeCalcForWork
						.add(this.shiftTime(difftime, item.getTimezone().getStart(), item.getTimezone().getEnd(),predTime));
				// ズラした後の重複チェック
				this.checkDuplycateAfterChange();
			});

			// TODO ダミー時間帯の場合
//			List<TimeZone> lstDummy = this.generateListDummy(halfDay.getWorkTimezone().getEmploymentTimezones(),
//					halfDay.getWorkTimezone().getOTTimezones(), predTime);

			// 1日の範囲の最終チェック
			if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
				EmTimeZoneSet lastItem = halfDay.getWorkTimezone().getEmploymentTimezones()
						.get(halfDay.getWorkTimezone().getEmploymentTimezones().size());
				TimeSpanForCalc time = this.oneDaycheck(difftime, null, null, lastItem.getTimezone().getStart(),
						lastItem.getTimezone().getEnd(), predTime);
				halfDay.getWorkTimezone().getEmploymentTimezones()
						.get(halfDay.getWorkTimezone().getEmploymentTimezones().size()).getTimezone()
						.updateEnd(time.getEnd());
			} else {
				EmTimeZoneSet firstItem = halfDay.getWorkTimezone().getEmploymentTimezones().get(0);
				TimeSpanForCalc time = this.oneDaycheck(difftime, firstItem.getTimezone().getStart(),
						firstItem.getTimezone().getEnd(), null, null, predTime);
				halfDay.getWorkTimezone().getEmploymentTimezones().get(0).getTimezone().updateStart(time.getStart());
			}
		});
		// 残業時間帯を取得
	}

	// TODO QA
	private void checkHasChange(JoggingWorkTime difftime, DiffTimeHalfDayWorkTimezone backupHalfDay,
			DiffTimeHalfDayWorkTimezone halfDay) {
		// 前回のループ処理で開始時刻によって時刻を変動させたかチェックする
		halfDay.getWorkTimezone().getOTTimezones().stream()
				.sorted((a, b) -> a.getTimezone().getStart().compareTo(b.getTimezone().getStart()));
		backupHalfDay.getWorkTimezone().getOTTimezones().stream()
				.sorted((a, b) -> a.getTimezone().getStart().compareTo(b.getTimezone().getStart()));
		for (int i = 0; i < halfDay.getWorkTimezone().getOTTimezones().size(); i++) {
			if (!halfDay.getWorkTimezone().getOTTimezones().get(i)
					.equals(backupHalfDay.getWorkTimezone().getOTTimezones().get(i))) {
				if (difftime.getAtr().equals(CollectionAtr.BEFORE)) {
					halfDay.getWorkTimezone().getOTTimezones().get(i).getTimezone().updateEnd(
							backupHalfDay.getWorkTimezone().getOTTimezones().get(i).getTimezone().getStart());
				} else {

				}
			}
		}
	}

	// TODO ズラした後の重複チェック (社員の日別実績エラー一覧に関して)
	private void checkDuplycateAfterChange() {
		// TODO

	}

	// 就業時間帯を時系列順に1つのリストにまとめる
//	private List<TimeZone> generateListDummy(List<EmTimeZoneSet> employmentTimezones,
//			List<DiffTimeOTTimezoneSet> otTime, PredetemineTimeSetting predTime) {
//		List<TimeZone> lstDummy = new ArrayList<>();
//		List<TimeZone> listMerge = new ArrayList<>();
//
//		employmentTimezones.stream().forEach(item -> {
//			listMerge.add(new TimeZone(item.getTimezone().getStart(), item.getTimezone().getEnd()));
//		});
//		otTime.stream().forEach(item -> {
//			listMerge.add(new TimeZone(item.getTimezone().getStart(), item.getTimezone().getEnd()));
//		});
//
//		listMerge.stream().sorted((a, b) -> a.getStart().compareTo(b.getStart()));
//		if (!listMerge.isEmpty()) {
//			for (int i = 0; i < listMerge.size(); i++) {
//				if (i == 0) {
//					if (listMerge.get(0).getStart().greaterThan(predTime.getStartDateClock())) {
//						lstDummy.add(new TimeZone(predTime.getStartDateClock(), listMerge.get(0).getStart()));
//					}
//				} else {
//					if (listMerge.get(i - 1).getEnd().lessThan(listMerge.get(i).getStart())) {
//						lstDummy.add(new TimeZone(listMerge.get(i - 1).getEnd(), listMerge.get(i).getStart()));
//					}
//				}
//			}
//		}
//		return lstDummy;
//	}

	// 休憩時間帯を補正
	private void restTimeUpdate(JoggingWorkTime difftime, DiffTimeWorkSetting difftimeSetting) {
		// not implement because update...
		// List<RestTimeForFix> lstRestTimeForFix =
		// difftimeSetting.getDayoffWorkTimezone().getRestTimezone().getRestTimezones().stream().map(item->{
		// TimeSpanForCalc timezone = new
		// TimeSpanForCalc(item.getStart(),item.getEnd());
		// return new
		// RestTimeForFix(timezone,StatutoryAtr.DeformationCriterion);
		// }).collect(Collectors.toList());
		//
		// RestTimeForFix restTimeForFix = new
		// RestTimeForFix(difftimeSetting.getDayoffWorkTimezone().getRestTimezone().getRestTimezones().);
	}
}
