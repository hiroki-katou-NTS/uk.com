package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.BonusPayTime;

/** 加給時間 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaisingSalaryTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = RAISING_SALARY)
	private CalcAttachTimeDto rasingSalaryTime;

	/** 法定外加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = ILLEGAL)
	private CalcAttachTimeDto outOfLegalRasingSalaryTime;

	/** 法定内加給時間: 計算付き時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = LEGAL)
	private CalcAttachTimeDto inLegalRasingSalaryTime;

	/** 加給NO: i時間項目NO */
	private int no;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case RAISING_SALARY:
		case (LEGAL):
		case (ILLEGAL):
			return new CalcAttachTimeDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (RAISING_SALARY):
			return Optional.ofNullable(rasingSalaryTime);
		case (LEGAL):
			return Optional.ofNullable(inLegalRasingSalaryTime);
		case (ILLEGAL):
			return Optional.ofNullable(outOfLegalRasingSalaryTime);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (RAISING_SALARY):
			rasingSalaryTime = (CalcAttachTimeDto) value;
			break;
		case (LEGAL):
			inLegalRasingSalaryTime = (CalcAttachTimeDto) value;
			break;
		case (ILLEGAL):
			outOfLegalRasingSalaryTime = (CalcAttachTimeDto) value;
		default:
		}
	}
	
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
						rasingSalaryTime == null|| rasingSalaryTime.getTime() == null ? AttendanceTime.ZERO : new AttendanceTime(rasingSalaryTime.getTime()), 
						inLegalRasingSalaryTime == null || rasingSalaryTime.getTime() == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) 
								: inLegalRasingSalaryTime.createTimeWithCalc(),
						outOfLegalRasingSalaryTime == null || outOfLegalRasingSalaryTime.getTime() == null ? TimeWithCalculation.sameTime(AttendanceTime.ZERO) 
								: outOfLegalRasingSalaryTime.createTimeWithCalc());
	}
}
