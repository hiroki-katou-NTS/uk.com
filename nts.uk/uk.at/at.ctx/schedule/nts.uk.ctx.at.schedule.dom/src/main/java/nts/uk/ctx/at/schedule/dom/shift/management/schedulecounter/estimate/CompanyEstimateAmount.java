package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 *　会社の目安金額	 
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class CompanyEstimateAmount implements DomainAggregate{	
	/**　目安金額詳細　*/
	private EstimatePriceDetail detail;
	
	/**
	 * [1] 変更する
	 * @param detail 目安金額詳細
	 */
	public void update(EstimatePriceDetail detail) {
		this.detail = new EstimatePriceDetail(detail.getYearEstimatePrice(), detail.getMonthEstimatePrice());
	}
	
}
