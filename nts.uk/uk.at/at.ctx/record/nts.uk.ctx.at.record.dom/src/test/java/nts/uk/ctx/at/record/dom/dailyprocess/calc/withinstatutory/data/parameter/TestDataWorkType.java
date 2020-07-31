package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.data.parameter;

import java.util.Map;
import java.util.function.Function;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.CsvParameter;
import nts.uk.ctx.at.shared.dom.common.time.TestDataCsvRecord;
import nts.uk.ctx.at.shared.dom.worktype.CalculateMethod;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * WorkType(勤務種類)のテストデータ
 */
public class TestDataWorkType {

	public static Map<String, WorkType> build() {
		return CsvParameter.load(
				"/testdata/WithinWorkTimeSheetTest/parameter/WorkType.csv",
				buildWorkType,
				WorkType.class);
	}

	/**
	 * DailyWork
	 */
	static Function<TestDataCsvRecord, DailyWork> buildDailyWork = record -> {
		return new DailyWork(
				record.asEnum("workTypeUnit", WorkTypeUnit.class),
				record.asEnum("oneDay", WorkTypeClassification.class),
				record.asEnum("morning", WorkTypeClassification.class),
				record.asEnum("afternoon", WorkTypeClassification.class));
	};
	
	/**
	 * WorkType
	 */
	static Function<TestDataCsvRecord, WorkType> buildWorkType = record -> {
		val dailyWork = record.child("dailyWork", buildDailyWork).get();
		val calculateMethod = record.asEnum("calculateMethod", CalculateMethod.class);
		return new WorkType(null, null, null, null, null, null, dailyWork, null, calculateMethod);
	};
}
