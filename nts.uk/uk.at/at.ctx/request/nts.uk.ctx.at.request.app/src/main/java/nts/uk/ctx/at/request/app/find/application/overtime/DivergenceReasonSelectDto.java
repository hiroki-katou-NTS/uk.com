package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonSelect;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceReasonSelectDto {
	
	// 乖離理由コード
	public String divergenceReasonCode;

	// 乖離理由
	public String reason;

	// 乖離理由の入力を必須とする
	public Integer reasonRequired;
	
	public static DivergenceReasonSelectDto fromDomain(DivergenceReasonSelect divergenceReasonSelect) {
		
		return new DivergenceReasonSelectDto(
				divergenceReasonSelect.getDivergenceReasonCode().v(),
				divergenceReasonSelect.getReason().v(),
				divergenceReasonSelect.getReasonRequired().value);
	}
}
