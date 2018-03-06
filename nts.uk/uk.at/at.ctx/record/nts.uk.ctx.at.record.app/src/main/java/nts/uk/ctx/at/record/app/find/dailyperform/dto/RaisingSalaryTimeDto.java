package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDto {

	/** 加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "加給時間.時間")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer rasingSalaryTime;

	/** 法定外加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "法定外加給時間")
	private CalcAttachTimeDto outOfLegalRasingSalaryTime;

	/** 法定内加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "法定内加給時間")
	private CalcAttachTimeDto inLegalRasingSalaryTime;

	/** 加給NO: 加給時間項目NO */
	private Integer raisingSalaryNo;
	
	public static RaisingSalaryTimeDto toDto(BonusPayTime time){
		return time == null ? null : new RaisingSalaryTimeDto(
										time.getBonusPayTime() == null ? null : time.getBonusPayTime().valueAsMinutes(), 
										CalcAttachTimeDto.toTimeWithCal(time.getWithinBonusPay()),
										CalcAttachTimeDto.toTimeWithCal(time.getExcessBonusPayTime()),
										time.getBonusPayTimeItemNo());
	}
	
	public BonusPayTime toDomain(){
		return new BonusPayTime(raisingSalaryNo,
						rasingSalaryTime == null ? null : new AttendanceTime(rasingSalaryTime),
						inLegalRasingSalaryTime == null ? null : inLegalRasingSalaryTime.createTimeWithCalc(),
						outOfLegalRasingSalaryTime == null ? null : outOfLegalRasingSalaryTime.createTimeWithCalc() 
						);
	}
}
