package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 勤務種別で参照する場合の月次項目の並び順
 * @author tutk
 *
 */
@Getter
public class BusinessTypeSortedMon extends AggregateRoot{
	/**会社ID*/
	private String companyID;
	/**並び順*/
	List<OrderReferWorkType> listOrderReferWorkType;
	public BusinessTypeSortedMon(String companyID, List<OrderReferWorkType> listOrderReferWorkType) {
		super();
		this.companyID = companyID;
		this.listOrderReferWorkType = listOrderReferWorkType;
	}

}
