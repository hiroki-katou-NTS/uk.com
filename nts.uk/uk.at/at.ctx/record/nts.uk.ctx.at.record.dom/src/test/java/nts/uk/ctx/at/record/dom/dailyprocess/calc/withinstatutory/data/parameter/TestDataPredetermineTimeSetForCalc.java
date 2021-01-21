package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.data.parameter;

import java.util.Map;
import java.util.function.Function;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktime.predset.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TestDataPredetermineTimeSetForCalc {

	public static Map<String, PredetermineTimeSetForCalc> build() {
		return CsvParameter.load(
				"/testdata/WithinWorkTimeSheetTest/parameter/PredetermineTimeSetForCalc.csv",
				buildPredetermineTimeSetForCalc,
				PredetermineTimeSetForCalc.class);
	}
	
	/**
	 * TimezoneUse
	 */
	static Function<TestDataCsvRecord, TimezoneUse> buildTimezoneUse = record -> {
		return new TimezoneUse(
				record.asInt("start", v -> new TimeWithDayAttr(v)),
				record.asInt("end", v -> new TimeWithDayAttr(v)),
				record.asEnum("useAtr", UseSetting.class),
				record.asInt("workNo"));
	};
	
	/**
	 * BreakDownTimeDay
	 */
	static Function<TestDataCsvRecord, BreakDownTimeDay> buildBreakDownTimeDay = record -> {
		return new BreakDownTimeDay(
				record.asInt("oneDay"),
				record.asInt("morning"),
				record.asInt("afternoon"));
	};
	
	/**
	 * PredetermineTime
	 */
	static Function<TestDataCsvRecord, PredetermineTime> buildPredetermineTime = record -> {
		return new PredetermineTime(
				record.child("addTime", buildBreakDownTimeDay).get(),
				record.child("predTime", buildBreakDownTimeDay).get());
	};
	
	/**
	 * PredetermineTimeSetForCalc
	 */
	static Function<TestDataCsvRecord, PredetermineTimeSetForCalc> buildPredetermineTimeSetForCalc = record -> {
		return new PredetermineTimeSetForCalc(
				record.list("timeSheets", 2, buildTimezoneUse),
				record.asInt("AMEndTime", v -> new TimeWithDayAttr(v)),
				record.asInt("PMStartTime", v -> new TimeWithDayAttr(v)),
				record.child("additionSet", buildPredetermineTime).get(),
				record.asInt("oneDayRange", v -> new AttendanceTime(v)),
				record.asInt("startOneDayTime", v -> new TimeWithDayAttr(v)));
	};
}
