package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請乖離理由
public class ReasonDivergence {
	// 理由
	private DivergenceReason reason;
	// 理由コード
	private DiverdenceReasonCode reasonCode;
	// 乖離時間NO
	private Integer diviationTime;
	
	public Boolean isNullProp() {
		if (this.reason == null && this.reasonCode == null && this.diviationTime == null) return true;
		return false;
	}
}
