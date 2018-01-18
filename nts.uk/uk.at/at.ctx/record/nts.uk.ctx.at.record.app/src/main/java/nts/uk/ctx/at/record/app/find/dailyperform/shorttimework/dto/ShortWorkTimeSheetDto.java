package nts.uk.ctx.at.record.app.find.dailyperform.shorttimework.dto;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

/** 短時間勤務時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortWorkTimeSheetDto {

	/** 短時間勤務枠NO: 短時間勤務枠NO */
//	@AttendanceItemLayout(layout = "A", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer shortWorkTimeFrameNo;

	/** 育児介護区分: 育児介護区分 */
	/** @see nts.uk.ctx.at.record.dom.shorttimework.enums.ChildCareAttribute */
//	@AttendanceItemLayout(layout = "B", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer childCareAttr;

	/** 開始: 時刻(日区分付き) */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "開始", needCheckIDWithIndex = true, needCheckIDWithMethod = "childCare", methodForEnumValues = "childCareEnum")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer startTime;

	/** 終了: 時刻(日区分付き) */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "終了", needCheckIDWithIndex = true, needCheckIDWithMethod = "childCare", methodForEnumValues = "childCareEnum")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer endTime;

	/** 控除時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "E", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer deductionTime;

	/** 時間: 勤怠時間 */
//	@AttendanceItemLayout(layout = "F", jpPropertyName = "")
//	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer shortTime;
	
	public String childCare(){
		if(childCareAttr == null){
			return "";
		}
		
		switch (childCareAttr - 1) {
		case 0:
			return "育児";
		case 1:
			return "介護";
		default:
			return "";
		}
	}
	
	public void childCare(String text){
		if(childCareAttr == null){
			if(text.contains("育児")){
				this.childCareAttr = 0;
			} else if (text.contains("介護")){
				this.childCareAttr = 1;
			}
		}
	}
	
	public List<String> childCareEnum(){
		return Arrays.asList("育児", "介護");
	}
}
