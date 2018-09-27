package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpecialLeaveGrantNextDateOutput {
	/**	年月日 */
	private GeneralDate ymd;
	/**	エラーフラグ */
	private boolean errorFlg;
	/**	付与日数 */
	private double grantDays;
	
}
