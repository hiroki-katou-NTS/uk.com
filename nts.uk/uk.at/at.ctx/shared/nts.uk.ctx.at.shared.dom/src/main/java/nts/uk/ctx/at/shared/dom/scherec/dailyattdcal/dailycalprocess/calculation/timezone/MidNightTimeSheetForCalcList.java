package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.TimeSpanForDailyCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.InOutMidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheetForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 計算用深夜時間帯一覧
 * @author daiki_ichioka
 *
 */
@Getter
@AllArgsConstructor
public class MidNightTimeSheetForCalcList implements Cloneable {

	/** 時間帯一覧 */
	List<MidNightTimeSheetForCalc> timeSheets = new ArrayList<>();

	/**
	 * 深夜時間帯一覧を作成する
	 * @param calcRange 計算範囲
	 * @param midNightTimeSheet 深夜時間帯
	 * @param deductTimeSheet 控除時間帯
	 * @param roundSetting 深夜時間丸め設定
	 * @return 計算用深夜時間帯一覧
	 */
	public static MidNightTimeSheetForCalcList create(
			TimeSpanForDailyCalc calcRange,
			MidNightTimeSheet midNightTimeSheet,
			DeductionTimeSheet deductTimeSheet,
			TimeRoundingSetting roundSetting) {
		
		//最大の計算範囲に対する深夜時間帯を取得する
		List<TimeSpanForDailyCalc> allMidNight = midNightTimeSheet.getAllMidNightTimeSheet().stream()
				.map(m -> TimeSpanForDailyCalc.of(m))
				.collect(Collectors.toList());
		
		return new MidNightTimeSheetForCalcList(
				allMidNight.stream()
						.map(m -> MidNightTimeSheetForCalc.create(calcRange, m, deductTimeSheet, roundSetting))
						.filter(m -> m.isPresent())
						.map(m -> m.get())
						.collect(Collectors.toList()));
	}
	
	/**
	 * 空で作成する
	 * @return 空の計算用深夜時間帯一覧
	 */
	public static MidNightTimeSheetForCalcList createEmpty() {
		return new MidNightTimeSheetForCalcList(new ArrayList<>());
	}

	/**
	 * 深夜時間帯一覧を作り直す
	 * @param baseTime 指定時刻
	 * @param isDateBefore 指定時刻より早い時間を切り出す
	 * @return 切り出した深夜時間帯
	 */
	public MidNightTimeSheetForCalcList recreateMidNightTimeSheetBeforeBase(TimeWithDayAttr baseTime, boolean isDateBefore){
		return new MidNightTimeSheetForCalcList(
			this.timeSheets.stream()
					.map(t -> t.recreateMidNightTimeSheetBeforeBase(baseTime, isDateBefore))
					.filter(t -> t.isPresent())
					.map(t -> t.get())
					.collect(Collectors.toList()));
	}

	/**
	 * 深夜時間を計算する
	 * アルゴリズム：深夜時間を計算
	 * @return 深夜時間
	 */
	public AttendanceTime calcTotalTime() {
		return new AttendanceTime(this.timeSheets.stream()
				.map(t -> t.calcTotalTime().valueAsMinutes())
				.collect(Collectors.summingInt(value -> value)));
	}

	/**
	 * 所定内と所定外に分けて深夜時間帯を取得する
	 * @param outSideStart 所定外開始時刻
	 * @return 所定内・外の深夜時間帯
	 */
	public InOutMidNightTimeSheet getWithinAndOutSide(TimeWithDayAttr outSideStart) {
		return new InOutMidNightTimeSheet(
				this.timeSheets.stream()
						.flatMap(t -> t.getWithinAndOutSide(outSideStart).getWithin().stream())
						.collect(Collectors.toList()),
				this.timeSheets.stream()
						.flatMap(t -> t.getWithinAndOutSide(outSideStart).getOutside().stream())
						.collect(Collectors.toList()));
	}
	
	/**
	 * 指定された時間帯と重複している深夜時間帯を取得
	 * @param timeSpan 時間帯
	 * @return 重複範囲の時間帯深夜時間帯一覧
	 */
	public MidNightTimeSheetForCalcList getDuplicateRangeTimeSheet(TimeSpanForDailyCalc timeSpan) {
		return new MidNightTimeSheetForCalcList(this.timeSheets.stream()
				.map(t -> t.getDuplicateRangeTimeSheet(timeSpan))
				.filter(t -> t.isPresent())
				.map(t -> t.get())
				.collect(Collectors.toList()));
	}
	
	public MidNightTimeSheetForCalcList clone() {
		MidNightTimeSheetForCalcList clone = new MidNightTimeSheetForCalcList(this.timeSheets);
		try {
			clone.timeSheets = this.timeSheets.stream().map(t -> t.clone()).collect(Collectors.toList());
		}
		catch (Exception e) {
			throw new RuntimeException("MidNightTimeSheetForCalcList clone error.");
		}
		return clone;
	}
}
