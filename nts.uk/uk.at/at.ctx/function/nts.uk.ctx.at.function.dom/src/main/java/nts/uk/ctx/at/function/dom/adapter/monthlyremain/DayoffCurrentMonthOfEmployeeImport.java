package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
/**
 * 
 * @author Hoi1102
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DayoffCurrentMonthOfEmployeeImport {
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
