package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/** 加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDto {

	/** 加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "加給時間")
	private CalcAttachTimeDto rasingSalaryTime;

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
										CalcAttachTimeDto.toTimeWithCal(time.getBonusPay()), 
										CalcAttachTimeDto.toTimeWithCal(time.getBonusPayTime()),
										CalcAttachTimeDto.toTimeWithCal(time.getSpecifiedbonusPayTime()),
										time.getBonusPayTimeItemNo());
	}
	
	public BonusPayTime toDomain(){
		return new BonusPayTime(raisingSalaryNo, 
						outOfLegalRasingSalaryTime == null ? null : outOfLegalRasingSalaryTime.createTimeWithCalc(), 
						rasingSalaryTime == null ? null : rasingSalaryTime.createTimeWithCalc(), 
						inLegalRasingSalaryTime == null ? null : inLegalRasingSalaryTime.createTimeWithCalc());
	}
}
