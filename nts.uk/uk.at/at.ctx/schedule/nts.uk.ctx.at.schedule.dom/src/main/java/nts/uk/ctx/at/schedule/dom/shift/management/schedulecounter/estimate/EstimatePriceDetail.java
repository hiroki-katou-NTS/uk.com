package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * 目安金額詳細
 * @author lan_lt
 *
 */
@Value
public class EstimatePriceDetail implements DomainValue{
	/** 年間目安金額 */
	private EstimatePrices yearEstimatePrice;
	
	/** 月度目安金額*/
	private EstimatePrices monthEstimatePrice;

}		
