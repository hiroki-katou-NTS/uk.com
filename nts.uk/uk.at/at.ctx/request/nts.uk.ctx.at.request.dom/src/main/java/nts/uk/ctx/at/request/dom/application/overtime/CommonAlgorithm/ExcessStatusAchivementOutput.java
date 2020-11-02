package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ExcessState;
import nts.uk.ctx.at.request.dom.application.overtime.OutDateApplication;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcessStatusAchivementOutput {
	/// 実績状態
	private ExcessState excessState;
	//申請時間の超過状態
	private OutDateApplication outDateApplication;
}
