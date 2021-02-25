package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessStateDetail;


@AllArgsConstructor
@NoArgsConstructor
public class ExcessStateDetailDto {
	// frameNo
	public Integer frame;
	// type
	public Integer type;
	// 超過状態
	public Integer excessState;
	
	public static ExcessStateDetailDto fromDomain(ExcessStateDetail excessStateDetail) {
		return new ExcessStateDetailDto(
				excessStateDetail.getFrame().v(),
				excessStateDetail.getType().value,
				excessStateDetail.getExcessState().value);
	};

	
}
