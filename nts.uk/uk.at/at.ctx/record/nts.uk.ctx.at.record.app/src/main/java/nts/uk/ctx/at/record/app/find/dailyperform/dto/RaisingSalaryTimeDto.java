package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.bonuspaytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDto implements ItemConst {

	/** 加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = RAISING_SALARY)
	private CalcAttachTimeDto rasingSalaryTime;

	/** 法定外加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ILLEGAL)
	private CalcAttachTimeDto outOfLegalRasingSalaryTime;

	/** 法定内加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = LEGAL)
	private CalcAttachTimeDto inLegalRasingSalaryTime;

	/** 加給NO: 加給時間項目NO */
	private Integer no;
	
	@Override
	public RaisingSalaryTimeDto clone(){
		return new RaisingSalaryTimeDto(rasingSalaryTime == null ? null : rasingSalaryTime.clone(), 
								outOfLegalRasingSalaryTime == null ? null : outOfLegalRasingSalaryTime.clone(),
								inLegalRasingSalaryTime == null ? null : inLegalRasingSalaryTime.clone(),
								no);
	}
	
	public static RaisingSalaryTimeDto toDto(BonusPayTime time){
		return time == null ? null : new RaisingSalaryTimeDto(
										time.getBonusPayTime() == null ? null : new CalcAttachTimeDto(0, time.getBonusPayTime().valueAsMinutes()), 
										CalcAttachTimeDto.toTimeWithCal(time.getExcessBonusPayTime()),
										CalcAttachTimeDto.toTimeWithCal(time.getWithinBonusPay()),
										time.getBonusPayTimeItemNo());
	}
	
	public BonusPayTime toDomain(){
		return new BonusPayTime(no,
						rasingSalaryTime == null ? AttendanceTime.ZERO : new AttendanceTime(rasingSalaryTime.getTime()), 
						inLegalRasingSalaryTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) 
								: inLegalRasingSalaryTime.createTimeWithCalc(),
						outOfLegalRasingSalaryTime == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) 
								: outOfLegalRasingSalaryTime.createTimeWithCalc());
	}
}
