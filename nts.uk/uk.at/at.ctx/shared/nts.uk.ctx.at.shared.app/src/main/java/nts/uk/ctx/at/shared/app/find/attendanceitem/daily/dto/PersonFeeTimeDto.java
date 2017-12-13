package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 人件費時間 */
@Data
public class PersonFeeTimeDto {

	/** The no. */
	//NO
	@AttendanceItemLayout(layout="A")
	@AttendanceItemValue(itemId=7, type=ValueType.INTEGER)
	private int no;
	
	/** The person fee time. */
	//人件費時間
	@AttendanceItemLayout(layout="B")
	@AttendanceItemValue(itemId=8, type=ValueType.INTEGER)
	private Integer personFeeTime; 
}
