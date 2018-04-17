package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.OrderReferWorkType;

@Getter
@Setter
@NoArgsConstructor
public class OrderReferWorkTypeDto {

	/**勤怠項目ID*/
	private int attendanceItemID;
	/**順番*/
	private int order;
	
	public OrderReferWorkTypeDto(int attendanceItemID, int order) {
		super();
		this.attendanceItemID = attendanceItemID;
		this.order = order;
	}
	
	public static OrderReferWorkTypeDto fromDomain(OrderReferWorkType domain) {
		return new OrderReferWorkTypeDto(
				domain.getAttendanceItemID(),
				domain.getOrder()
				);
	}
}
