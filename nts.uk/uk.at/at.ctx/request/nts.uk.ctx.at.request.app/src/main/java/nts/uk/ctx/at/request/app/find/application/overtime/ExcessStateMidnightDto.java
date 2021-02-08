package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateMidnight;

@AllArgsConstructor
@NoArgsConstructor
public class ExcessStateMidnightDto {
	// 超過状態
	public Integer excessState;
	// 法定区分
	public Integer legalCfl;
	
	public static ExcessStateMidnightDto fromDomain(ExcessStateMidnight excessStateMidnight) {
		return new ExcessStateMidnightDto(
				excessStateMidnight.getExcessState().value,
				excessStateMidnight.getLegalCfl().ordinal());
	}
}
