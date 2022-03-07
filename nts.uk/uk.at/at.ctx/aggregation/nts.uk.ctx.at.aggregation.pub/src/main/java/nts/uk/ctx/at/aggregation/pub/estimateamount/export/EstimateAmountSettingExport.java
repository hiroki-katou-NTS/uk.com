package nts.uk.ctx.at.aggregation.pub.estimateamount.export;

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
public class EstimateAmountSettingExport {

	/**
	 * 年間目安金額
	 */
	private Optional<EstimateAmountDetailExport> annualAmountDetail;

	/**
	 * 月度目安金額
	 */
	private Optional<EstimateAmountDetailExport> monthlyAmountDetail;

}
