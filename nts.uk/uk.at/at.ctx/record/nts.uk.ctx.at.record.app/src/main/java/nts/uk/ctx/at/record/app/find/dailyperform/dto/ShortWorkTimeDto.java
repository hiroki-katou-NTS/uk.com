package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.item.ValueType;

/** 日別実績の短時間勤務時間 */
@Data
public class ShortWorkTimeDto {

	/** 合計控除時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "合計控除時間", needCheckIDWithMethod = "childCareAttr", methodForEnumValues = "childCareEnum")
	private TotalDeductionTimeDto totalDeductionTime;

	/** 合計時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "合計時間", needCheckIDWithMethod = "childCareAttr", methodForEnumValues = "childCareEnum")
	private TotalDeductionTimeDto totalTime;

	/** 勤務回数 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "回数", needCheckIDWithMethod = "childCareAttr", methodForEnumValues = "childCareEnum")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer times;

	/** 育児介護区分 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "育児介護区分")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int childCareAndFamilyCareAtr;
	
	public String childCareAttr(){
		switch (this.childCareAndFamilyCareAtr) {
		case 0:
			return "育児";

		case 1:
			return "介護";
		default:
			return "";
		}
	}
	
	public void childCare(String text){
		if(text.contains("育児")){
			this.childCareAndFamilyCareAtr = 0;
		} else if (text.contains("介護")){
			this.childCareAndFamilyCareAtr = 1;
		}
	}
	
	public List<String> childCareEnum(){
		return Arrays.asList("育児", "介護");
	}
}
