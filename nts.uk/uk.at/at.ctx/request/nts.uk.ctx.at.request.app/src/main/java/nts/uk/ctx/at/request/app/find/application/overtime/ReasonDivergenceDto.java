package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;

@AllArgsConstructor
@NoArgsConstructor
public class ReasonDivergenceDto {
	// 理由
	public String reason;
	// 理由コード
	public String reasonCode;
	// 乖離時間NO
	public Integer diviationTime;
	
	public static ReasonDivergenceDto fromDomain(ReasonDivergence reasonDivergence) {
		return new ReasonDivergenceDto(
				// can be null
				reasonDivergence.getReason() == null ? null : reasonDivergence.getReason().v(),
				reasonDivergence.getReasonCode() == null ? null : reasonDivergence.getReasonCode().v(),
				reasonDivergence.getDiviationTime().intValue());
	}
}
