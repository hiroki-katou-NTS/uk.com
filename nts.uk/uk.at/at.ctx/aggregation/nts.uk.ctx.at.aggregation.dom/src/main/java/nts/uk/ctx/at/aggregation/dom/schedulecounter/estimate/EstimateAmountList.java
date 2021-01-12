package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 目安金額リスト
 * @author lan_lt
 *
 */
@Value
public class EstimateAmountList implements DomainValue{
	/**　目安金額リスト　*/
	private final List<EstimateAmountByCondition> estimatePrices;
	
	/**
	 * 作る
	 * @param estimateAmount 目安金額リスト
	 * @return
	 */
	public static EstimateAmountList create (List<EstimateAmountByCondition> estimateAmount) {
		if(estimateAmount.size() > 5) {
			throw new BusinessException("Msg_1869");
		}
		
		val estimateDistincts = estimateAmount.stream().map(c -> c.getEstimateAmountNo().v())
													   .distinct().collect(Collectors.toList());
		if(estimateAmount.size() > estimateDistincts.size()) {
			throw new BusinessException("Msg_1870");
		}
		
		if(!estimateAmount.isEmpty() && estimateAmount.get(0).getEstimateAmountNo().v() != 1 ) {
			throw new BusinessException("Msg_1871");
		}
		
		estimateAmount.stream().reduce((pre, next) -> {

			if (pre.getEstimateAmountNo().v() != (next.getEstimateAmountNo().v() - 1)) {
				throw new BusinessException("Msg_1871");
			}

			if (pre.getEstimateAmount().v() >= next.getEstimateAmount().v()) {
				throw new BusinessException("Msg_147");
			}

			return next;
		});
		
		return new EstimateAmountList(estimateAmount);
	}

}
