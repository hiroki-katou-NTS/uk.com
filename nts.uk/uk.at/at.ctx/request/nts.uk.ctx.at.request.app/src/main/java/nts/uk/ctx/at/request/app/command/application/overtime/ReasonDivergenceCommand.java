package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;

public class ReasonDivergenceCommand {
	// 理由
	public String reason;
	// 理由コード
	public String reasonCode;
	// 乖離時間NO
	public Integer diviationTime;
	
	public ReasonDivergence toDomain() {
		return new ReasonDivergence(
				new DivergenceReason(reason),
				new DiverdenceReasonCode(reasonCode),
				diviationTime);
	}
}
