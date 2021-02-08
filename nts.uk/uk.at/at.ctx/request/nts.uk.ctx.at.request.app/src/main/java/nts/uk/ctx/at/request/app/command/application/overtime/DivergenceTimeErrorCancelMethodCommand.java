package nts.uk.ctx.at.request.app.command.application.overtime;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeErrorCancelMethod;

@AllArgsConstructor
@NoArgsConstructor
public class DivergenceTimeErrorCancelMethodCommand {
	//乖離理由が入力された場合、エラーを解除する
	public Boolean reasonInputed;
		

	//乖離理由が選択された場合、エラーを解除する
	public Boolean reasonSelected;
	
	public DivergenceTimeErrorCancelMethod toDomain() {
		return new DivergenceTimeErrorCancelMethod(
				BooleanUtils.toInteger(reasonInputed),
				BooleanUtils.toInteger(reasonSelected));
	}
}
