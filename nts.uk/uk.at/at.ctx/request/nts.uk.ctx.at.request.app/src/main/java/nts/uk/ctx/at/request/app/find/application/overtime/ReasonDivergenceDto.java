package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.DivergenceReasonDto;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;

@AllArgsConstructor
@NoArgsConstructor
public class ReasonDivergenceDto {
	// 理由
	public DivergenceReasonDto reason;
	// 理由コード
	public String reasonCode;
	// 乖離時間NO
	public Integer diviationTime;
	
	public static ReasonDivergenceDto fromDomain(ReasonDivergence reasonDivergence) {
		return new ReasonDivergenceDto(
				DivergenceReasonDto.fromDomain(reasonDivergence.getReason()),
				reasonDivergence.getReasonCode().v(),
				reasonDivergence.getDiviationTime().intValue());
	}
}
