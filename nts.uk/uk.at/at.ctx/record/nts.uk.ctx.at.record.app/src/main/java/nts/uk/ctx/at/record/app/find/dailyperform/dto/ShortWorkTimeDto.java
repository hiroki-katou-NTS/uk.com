package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

/** 日別実績の短時間勤務時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
	private int childCareAttr;
	
	public String childCareAttr(){
		switch (this.childCareAttr) {
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
			this.childCareAttr = 0;
		} else if (text.contains("介護")){
			this.childCareAttr = 1;
		}
	}
	
	public List<String> childCareEnum(){
		return Arrays.asList("育児", "介護");
	}
	
	public static ShortWorkTimeDto toDto(ShortWorkTimeOfDaily domain){
		return domain == null ? null : new ShortWorkTimeDto(
											TotalDeductionTimeDto.getDeductionTime(domain.getTotalDeductionTime()), 
											TotalDeductionTimeDto.getDeductionTime(domain.getTotalTime()), 
											domain.getWorkTimes() == null ? null : domain.getWorkTimes().v(), 
											domain.getChildCareAttribute().value);
	}
	
}
