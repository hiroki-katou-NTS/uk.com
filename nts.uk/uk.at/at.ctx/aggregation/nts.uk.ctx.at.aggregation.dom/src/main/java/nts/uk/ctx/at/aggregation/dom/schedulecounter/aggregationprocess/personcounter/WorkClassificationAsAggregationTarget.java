package nts.uk.ctx.at.aggregation.dom.schedulecounter.aggregationprocess.personcounter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 集計対象の勤務分類
 * @author lan_lt
 *
 */
@AllArgsConstructor
public enum WorkClassificationAsAggregationTarget {
	//出勤
	WORKING(0),
	//休日
	HOLIDAY(1);
	
	public final int value;
	
	/**
	 * 日数を取得する
	 * @param require Require
	 * @param workInfo 勤務情報
	 * @return
	 */
	public BigDecimal getNumberDay(Require require, WorkInformation workInfo) {
		Optional<WorkType> workType = require.getWorkType(workInfo.getWorkTypeCode());

		if (!workType.isPresent()) {
			return BigDecimal.ZERO;
		}

		val workContent = workType.get().getDailyWork().getHalfDayWorkTypeClassification();
		
		Function<WorkTypeClassification, Boolean> condition = (workTypeClass) -> {
			if (this == WORKING) {
				return workTypeClass.isContinuousWork() || workTypeClass.isWeekDayAttendance();
			} else {
				return workTypeClass.isHoliday() || workTypeClass.isPause();
			}
		};
		
		return workContent.getAsMap()
				.entrySet().stream()
				.map(c -> condition.apply(c.getValue())? BigDecimal.valueOf(0.5) : BigDecimal.ZERO)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	
	public static interface Require {
		/**
		 * 勤務種類を取得する
		 * @param workTypeCd 勤務種類コード
		 * @return
		 */
		Optional<WorkType> getWorkType(WorkTypeCode workTypeCd);
	}
}
