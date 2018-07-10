package nts.uk.ctx.at.function.dom.adapter.periodofspecialleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Setter
@Getter
public class SpecialHolidayImported {
	/** 年月 */
	private YearMonth ym;
	/** 使用日数 */
	private Double useDays;
	/** 使用時間 */
	private Integer useTimes;
	/** 残数日数 */
	private Double remainDays;
	/** 残数時間 */
	private Integer remainTimes;

	public SpecialHolidayImported(YearMonth ym, Double useDays, Integer useTimes, Double remainDays,
			Integer remainTimes) {
		this.ym = ym;
		this.useDays = useDays;
		this.useTimes = useTimes;
		this.remainDays = remainDays;
		this.remainTimes = remainTimes;
	}

}
