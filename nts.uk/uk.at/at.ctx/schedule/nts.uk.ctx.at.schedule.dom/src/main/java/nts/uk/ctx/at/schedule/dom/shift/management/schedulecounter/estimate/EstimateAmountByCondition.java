package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import lombok.Value;
import nts.arc.error.BusinessException;
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
	private EstimatePrice  estimatePrice;

/**
 * [C-1] 作る
 * @param estimateAmountNo
 * @param estimatePrice
 * @return
 */
public static EstimateAmountByCondition create(EstimateAmountNo estimateAmountNo, int estimatePrice) {
	
	if(estimateAmountNo.v() > 5 || estimateAmountNo.v() < 1) {
		throw new BusinessException("Msg_1869");
	}
	
	return new EstimateAmountByCondition(estimateAmountNo, new EstimatePrice(estimatePrice));
}

}
