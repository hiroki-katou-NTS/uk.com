package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.OrderSorted;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class BusinessTypeSorted extends AggregateRoot {

	private String companyId;

	private int attendanceItemId;
	
	private OrderSorted order;

	public BusinessTypeSorted(String companyId, int attendanceItemId, OrderSorted order) {
		super();
		this.companyId = companyId;
		this.attendanceItemId = attendanceItemId;
		this.order = order;
	}
	
	public static BusinessTypeSorted createFromJavaType(String companyId, int attendanceItemId, int order){
		return new BusinessTypeSorted(companyId, attendanceItemId, new OrderSorted(order));
	}
}
