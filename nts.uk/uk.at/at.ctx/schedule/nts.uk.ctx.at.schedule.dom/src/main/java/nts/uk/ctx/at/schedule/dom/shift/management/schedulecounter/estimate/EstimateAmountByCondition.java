package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainObject;
/**
 * 要件別目安金額	
 * @author lan_lt
 *
 */
@Value
public class EstimateAmountByCondition implements DomainObject{
	/** 目安金額枠NO */
	private final EstimateAmountNo estimateAmountNo;
	
	/** 目安金額 */
	private final EstimateAmount  estimateAmount;

/**
 * [C-1] 作る
 * @param estimateAmountNo 目安金額枠NO
 * @param estimatePrice 目安金額
 * @return
 */
public static EstimateAmountByCondition create(EstimateAmountNo estimateAmountNo, EstimateAmount estimateAmount) {
	return new EstimateAmountByCondition(estimateAmountNo, estimateAmount);
}

}
