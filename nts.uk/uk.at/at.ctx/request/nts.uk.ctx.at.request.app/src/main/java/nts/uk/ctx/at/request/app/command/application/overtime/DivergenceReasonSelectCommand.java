package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceInputRequired;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReason;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonCode;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonSelect;

public class DivergenceReasonSelectCommand {
	// 乖離理由コード
	public String divergenceReasonCode;

	// 乖離理由
	public String reason;

	// 乖離理由の入力を必須とする
	public Integer reasonRequired;
	
	public DivergenceReasonSelect toDomain() {
		
		return new DivergenceReasonSelect(
				new DivergenceReasonCode(divergenceReasonCode),
				new DivergenceReason(reason),
				EnumAdaptor.valueOf(reasonRequired, DivergenceInputRequired.class));
	}
}
