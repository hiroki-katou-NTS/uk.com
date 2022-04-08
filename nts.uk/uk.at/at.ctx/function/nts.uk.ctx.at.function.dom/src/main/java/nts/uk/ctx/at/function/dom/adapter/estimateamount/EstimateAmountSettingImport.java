package nts.uk.ctx.at.function.dom.adapter.estimateamount;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         目安金額設定
 */
@AllArgsConstructor
@Getter
public class EstimateAmountSettingImport {

	/**
	 * 年間目安金額
	 */
	private Optional<EstimateAmountDetailImport> annualAmountDetail;

	/**
	 * 月度目安金額
	 */
	private Optional<EstimateAmountDetailImport> monthlyAmountDetail;

}
