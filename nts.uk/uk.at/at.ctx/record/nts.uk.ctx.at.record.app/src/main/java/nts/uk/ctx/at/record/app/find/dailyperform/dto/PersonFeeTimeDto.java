package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.Data;

/** 人件費時間 */
@Data
public class PersonFeeTimeDto {

	/** The no. */
	// NO
//	@AttendanceItemLayout(layout = "A")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer no;

	/** The person fee time. */
	// 人件費時間
//	@AttendanceItemLayout(layout = "B")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer personFeeTime;
}
