package nts.uk.ctx.at.function.dom.adapter.vacation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;


@Getter
@Setter
public class StatusHolidayImported {
	/**年月	 */
	private YearMonth ym;
	/**	発生日数 */
	private Double occurrenceDays;
	/**	発生時間 */
	private Integer occurrenceTimes;
	/**	使用日数 */
	private Double useDays;
	/**	使用時間 */
	private Integer useTimes;
	/**	未消化日数 */
	private Double unUsedDays;
	/**	未消化時間 */
	private Integer unUsedTimes;
	/**残数日数*/
	private Double remainDays;
	/**残数時間*/
	private Integer remainTimes;
	public StatusHolidayImported(YearMonth ym, Double occurrenceDays, Integer occurrenceTimes, Double useDays,
			Integer useTimes, Double unUsedDays, Integer unUsedTimes, Double remainDays, Integer remainTimes) {
		this.ym = ym;
		this.occurrenceDays = occurrenceDays;
		this.occurrenceTimes = occurrenceTimes;
		this.useDays = useDays;
		this.useTimes = useTimes;
		this.unUsedDays = unUsedDays;
		this.unUsedTimes = unUsedTimes;
		this.remainDays = remainDays;
		this.remainTimes = remainTimes;
	}
	
	
	
}
