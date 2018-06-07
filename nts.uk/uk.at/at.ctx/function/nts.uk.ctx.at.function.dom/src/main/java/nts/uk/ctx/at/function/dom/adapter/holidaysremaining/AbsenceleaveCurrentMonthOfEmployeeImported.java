package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;


@Getter
@Setter
@AllArgsConstructor
public class AbsenceleaveCurrentMonthOfEmployeeImported {
	/**	年月 */
	private YearMonth ym;
	
	/**	発生日数 */
	private Double occurredDay;
	
	/**	使用日数 */
	private Double usedDays;
	
	/**	未消化日数 */
	private Double unUsedDays;
	
	/**	残日数 = 残数日数 */
	private Double remainingDays;
}
