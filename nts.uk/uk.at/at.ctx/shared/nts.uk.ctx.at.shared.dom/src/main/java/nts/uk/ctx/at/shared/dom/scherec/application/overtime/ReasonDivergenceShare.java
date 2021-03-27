package nts.uk.ctx.at.shared.dom.scherec.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;

/**
 * @author thanhnx
 *
 *         申請乖離理由
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReasonDivergenceShare {
	// 理由
	private DivergenceReasonShare reason;
	// 理由コード
	private DiverdenceReasonCode reasonCode;
	// 乖離時間NO
	private Integer diviationTime;

}
