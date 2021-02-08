package nts.uk.ctx.at.aggregation.dom.schedulecounter.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 *　会社の目安金額	 
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class EstimateAmountForCompany implements DomainAggregate{	
	/**　目安金額詳細　*/
	private EstimateAmountDetail detail;
	
	/**
	 * 変更する
	 * @param detail 目安金額詳細
	 */
	public void update(EstimateAmountDetail detail) {
		this.detail = detail;
	}
	
}
