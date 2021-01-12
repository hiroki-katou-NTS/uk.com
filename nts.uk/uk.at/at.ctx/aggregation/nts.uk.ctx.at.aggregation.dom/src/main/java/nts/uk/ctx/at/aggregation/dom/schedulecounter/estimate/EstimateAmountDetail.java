package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 目安金額詳細
 * @author lan_lt
 *
 */
@Value
public class EstimateAmountDetail implements DomainValue{
	/** 年間目安金額 */
	private final EstimateAmountList yearEstimatePrice;
	
	/** 月度目安金額*/
	private final EstimateAmountList monthEstimatePrice;

}		
