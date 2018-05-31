package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.OrderReferWorkType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReferWorkTypeCmd {
	/**勤怠項目ID*/
	private int attendanceItemID;
	/**順番*/
	private int order;
	
	public static OrderReferWorkType fromCommand(OrderReferWorkTypeCmd command) {
		return new OrderReferWorkType(
				command.getAttendanceItemID(),
				command.getOrder()
				);
				
	}
}
