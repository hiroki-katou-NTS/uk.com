package nts.uk.ctx.at.shared.dom.remainmana.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
/**
 * 残数算出対象期間
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class ClosureRemainPeriodOutputData {
	/** 締め */
	private Closure closure;
	/**	残数算出対象期間:  開始年月*/
	private YearMonth startMonth;
	/**	残数算出対象期間:  終了年月	 */
	private YearMonth endMonth;

}
