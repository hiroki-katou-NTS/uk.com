package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.estimate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 *　会社の目安金額	 
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEstimateAmount implements DomainAggregate{	
	/**　目安金額詳細　*/
	private EstimateAmountDetail detail;
	
	/**
	 * [1] 変更する
	 * @param detail 目安金額詳細
	 */
	public void update(EstimateAmountDetail detail) {
		this.detail = new EstimateAmountDetail(detail.getYearEstimatePrice(), detail.getMonthEstimatePrice());
	}
	
}
