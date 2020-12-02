package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 目安金額
 * @author lan_lt
 *
 */
@Value
public class EstimatePrices implements DomainValue{
	/**　目安金額リスト　*/
	private List<EstimateAmountByCondition> estimatePrices;
	
	public static EstimatePrices create (List<EstimateAmountByCondition> estimateAmount) {
		if(estimateAmount.size() > 5) {
			throw new BusinessException("Msg_1869");
		}
		
		val estimateDistincts = estimateAmount.stream().map(c -> c.getEstimateAmountNo().v())
													   .distinct().collect(Collectors.toList());
		if(estimateAmount.size() > estimateDistincts.size()) {
			throw new BusinessException("Msg_1870");
		}
		
		List<EstimateAmountByCondition> esAmountSorted = estimateAmount.stream()
				.filter(c -> c.getEstimatePrice().v() != 0)
				.sorted(Comparator.comparing(EstimateAmountByCondition::getEstimateAmountNo))
				.collect(Collectors.toList());
		
		for(int i = 0; i < esAmountSorted.size() - 1; i++) {
			 val estimatePrice =   estimateAmount.get(i);
			 val estimatePriceNext =   estimateAmount.get(i + 1);
			 if(estimatePrice.getEstimateAmountNo().v() >= estimatePriceNext.getEstimateAmountNo().v()) {
					throw new BusinessException("Msg_1871");
			 }
			 
			 if(estimatePrice.getEstimatePrice().v() >= estimatePriceNext.getEstimatePrice().v()) {
					throw new BusinessException("Msg_147");
			 } 
		}
		
		return new EstimatePrices(esAmountSorted);
	}

}
