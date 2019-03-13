package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgreMaxTimeOfMonthlyImport {
	/** 36協定時間 */
	private AttendanceTimeMonth agreementTime;
	/** 上限時間 */
	private LimitOneMonth maxTime;
	/** 状態 */
	private AgreMaxTimeStatusOfMonthly status;
}
