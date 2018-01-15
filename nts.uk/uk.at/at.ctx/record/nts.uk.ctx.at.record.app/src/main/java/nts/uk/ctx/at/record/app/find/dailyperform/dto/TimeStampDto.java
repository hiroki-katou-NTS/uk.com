package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/** 勤怠打刻 */
@Data
@AllArgsConstructor
public class TimeStampDto {

	/** 打刻元情報: 打刻元情報 */
//	@AttendanceItemLayout(layout = "A")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer stampSourceInformation;

	/** 時刻情報: 時刻(日区分付き) */
//	@AttendanceItemLayout(layout = "B")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer timesOfDay;

	/** 丸め後の時刻: 時刻(日区分付き) */
//	@AttendanceItemLayout(layout = "C")
//	@AttendanceItemValue(itemId = -1, type = ValueType.INTEGER)
	private Integer afterRoundingTimesOfDay;

	/** 場所コード: 勤務場所コード */
//	@AttendanceItemLayout(layout = "D")
//	@AttendanceItemValue(itemId = -1)
	private String placeCode;
}
