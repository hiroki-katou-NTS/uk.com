package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsenceleaveCurrentMonthOfEmployee {
	/**	社員ID */
	private String sId;
	/**	年月 */
	private YearMonth ym;
	/**	発生日数 */
	private Double occurredDay;
	/**	使用日数 */
	private Double usedDays;
	/**	残日数 */
	private Double remainingDays;
	/**	繰越日数 */
	private Double carryforwardDays;
	/**	未消化日数 */
	private Double unUsedDays;
}
