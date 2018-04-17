package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import lombok.Getter;

/**
 * 勤務種別で参照する場合の順番
 * @author tutk
 *
 */
@Getter
public class OrderReferWorkType {

	/**勤怠項目ID*/
	private int attendanceItemID;
	/**順番*/
	private int order;
	public OrderReferWorkType(int attendanceItemID, int order) {
		super();
		this.attendanceItemID = attendanceItemID;
		this.order = order;
	}
	
}
