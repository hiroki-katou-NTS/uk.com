package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayoffCurrentMonthOfEmployee {
	/**	社員ID */
	private String sId;
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
	/**	残日数 */
	private Double remainingDays;
	/**	残時間 */
	private Integer remainingTimes;
	/**	繰越日数 */
	private Double carryForWardDays;
	/**	繰越時間 */
	private Integer carryForWordTimes;
	/**	未消化日数 */
	private Double unUsedDays;
	/**	未消化時間 */
	private Integer unUsedTimes;
}
