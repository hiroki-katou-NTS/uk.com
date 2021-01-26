package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeErrorCancelMethod;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceTimeErrorCancelMethodDto {
	
	//乖離理由が入力された場合、エラーを解除する
	public Boolean reasonInputed;
	

	//乖離理由が選択された場合、エラーを解除する
	public Boolean reasonSelected;
	
	public static DivergenceTimeErrorCancelMethodDto fromDomain(DivergenceTimeErrorCancelMethod divergenceTimeErrorCancelMethod) {
		return new DivergenceTimeErrorCancelMethodDto(
				divergenceTimeErrorCancelMethod.isReasonInputed(),
				divergenceTimeErrorCancelMethod.isReasonSelected());
	}
}
