package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.gobacksche;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.ApplyTimeAtr;

/**
 * 直行直帰申請申請反映パラメータ
 * @author dudt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class GobackReflectParam {
	/**	社員ID */
	private String employeeId;
	/**	年月日 */
	private GeneralDate datePara;
	/**	 振出・休出時反映する区分*/
	private boolean outsetBreakReflectAtr; 
	/**	直行直帰申請 */
	private ApplicationGobackScheInfor appInfor;
	/**直行直帰申請 の　時刻の反映：　固定（開始）	 */
	private ApplyTimeAtr applyTimeAtr;
}
