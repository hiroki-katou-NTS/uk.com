package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.OrderSorted;

/**
 * 勤務種別で参照する場合の並び順（スマホ版）- 日別
 * 
 * @author anhdt
 *
 */
@Getter
public class BusinessTypeSortedMobile extends AggregateRoot {

	private String companyId;

	private int attendanceItemId;

	private OrderSorted order;

		public BusinessTypeSortedMobile(String companyId, int attendanceItemId, OrderSorted order) {
			super();
			this.companyId = companyId;
			this.attendanceItemId = attendanceItemId;
			this.order = order;
		}

		public static BusinessTypeSortedMobile createFromJavaType(String companyId, int attendanceItemId, int order) {
			return new BusinessTypeSortedMobile(companyId, attendanceItemId, new OrderSorted(order));
		}

}
