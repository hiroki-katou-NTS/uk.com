package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;

public class ReasonDivergenceCommand {
	// 理由
	public DivergenceReasonDto reason;
	// 理由コード
	public String reasonCode;
	// 乖離時間NO
	public Integer diviationTime;
	
	public ReasonDivergence toDomain() {
		return null;
	}
}
