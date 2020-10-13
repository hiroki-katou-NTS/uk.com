package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem.PriceUnit;

/** 日別勤怠の応援作業時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OuenWorkTimeOfDailyAttendanceDto implements ItemConst {

	/** 応援勤務枠NO: 応援勤務枠NO */
	private int no;
	
	/** 勤務時間: 時間帯別勤怠の時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WORKING_TIME + TIME)
	private OuenAttendanceTimeEachTimeSheetDto workTime;
	
	/** 移動時間: 応援別勤務の移動時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = MOVE + TIME)
	private OuenMovementTimeEachTimeSheetDto moveTime;
	
	/** 金額: 勤怠日別金額 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = AMOUNT)
	@AttendanceItemValue(type = ValueType.AMOUNT_NUM)
	private int amount;
	
	/** 単価: 単価 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = PRICE_UNIT)
	@AttendanceItemValue(type = ValueType.AMOUNT_NUM)
	private int priceUnit;
	
	public OuenWorkTimeOfDailyAttendance toDomain() {
		return OuenWorkTimeOfDailyAttendance.create(
				new OuenFrameNo(this.no),
				this.workTime.toDomain(),
				this.moveTime.toDomain(),
				new AttendanceAmountDaily(this.amount),
				new PriceUnit(this.priceUnit));
	}
	
	public static OuenWorkTimeOfDailyAttendanceDto valueOf(OuenWorkTimeOfDailyAttendance domain) {
		return new OuenWorkTimeOfDailyAttendanceDto(
				domain.getWorkNo().v(),
				OuenAttendanceTimeEachTimeSheetDto.valueOf(domain.getWorkTime()),
				OuenMovementTimeEachTimeSheetDto.valueOf(domain.getMoveTime()),
				domain.getAmount().v(),
				domain.getPriceUnit().v());
	}
}
