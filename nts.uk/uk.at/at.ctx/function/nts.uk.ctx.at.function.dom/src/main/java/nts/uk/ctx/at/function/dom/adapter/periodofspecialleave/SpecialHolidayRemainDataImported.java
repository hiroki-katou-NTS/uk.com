package nts.uk.ctx.at.function.dom.adapter.periodofspecialleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Setter
@Getter
@AllArgsConstructor
public class SpecialHolidayRemainDataImported {
	/**	 年月*/
	private YearMonth ym;
	/**	 使用日数*/
	private Double useDays;
	/**	 使用時間*/
	private Integer useTimes;
	/** 残数日数*/
	private Double remainDays;
	/** 残数時間*/
	private Integer remainTimes;
}
