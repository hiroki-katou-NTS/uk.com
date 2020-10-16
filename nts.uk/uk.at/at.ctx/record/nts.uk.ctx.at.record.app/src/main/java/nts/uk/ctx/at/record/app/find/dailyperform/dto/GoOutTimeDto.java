package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;

/** 外出時間帯 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoOutTimeDto implements ItemConst, AttendanceItemDataGate {

	/** 戻り: 勤怠打刻(実打刻付き) */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = BACK)
	private WithActualTimeStampDto comeBack;

	/** 外出: 勤怠打刻(実打刻付き) */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = GO_OUT)
	private WithActualTimeStampDto outing;

	/** 外出時間: 勤怠時間 */
	private Integer outingTime;
	
	private Integer outingTimeCalc;

	/** 外出枠NO: 外出枠NO */
	private int no;

	/** 外出理由: 外出理由 */
	private int outingReason;
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case BACK:
		case (GO_OUT):
			return new WithActualTimeStampDto();
		default:
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		switch (path) {
		case (BACK):
			return Optional.ofNullable(comeBack);
		case (GO_OUT):
			return Optional.ofNullable(outing);
		default:
		}
		return AttendanceItemDataGate.super.get(path);
	}
	
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		switch (path) {
		case (BACK):
			comeBack = (WithActualTimeStampDto) value;
			break;
		case (GO_OUT):
			outing = (WithActualTimeStampDto) value;
			break;
		default:
		}
	}
	
	public static GoOutTimeDto toDto(OutingTimeSheet domain){
		return domain == null ? null : new GoOutTimeDto(
										WithActualTimeStampDto.toWithActualTimeStamp(domain.getComeBack().orElse(null)), 
										WithActualTimeStampDto.toWithActualTimeStamp(domain.getGoOut().orElse(null)),
										domain.getOutingTime() == null ? null : domain.getOutingTime().valueAsMinutes(), 
										domain.getOutingTimeCalculation() == null ? null : domain.getOutingTimeCalculation().valueAsMinutes(), 
										domain.getOutingFrameNo().v(), 
										domain.getReasonForGoOut().value);
	}
	
	@Override
	public GoOutTimeDto clone() {
		return new GoOutTimeDto(comeBack == null ? null : comeBack.clone(),
								outing == null ? null : outing.clone(),
								outingTime, outingTimeCalc, no, outingReason);
	}
	
	public OutingTimeSheet toDomain(){
		return new OutingTimeSheet(new OutingFrameNo(no), createTimeActual(outing), 
				outingTimeCalc == null ? AttendanceTime.ZERO : new AttendanceTime(outingTimeCalc),
				outingTime == null ? AttendanceTime.ZERO : new AttendanceTime(outingTime), 
				reason(), createTimeActual(comeBack));
	}
	
	private Optional<TimeActualStamp> createTimeActual(WithActualTimeStampDto c) {
		return c == null ? Optional.empty() : Optional.of(c.toDomain());
	}
	
	public GoingOutReason reason() {
		switch (outingReason) {
		case 0:
			return GoingOutReason.PRIVATE;
		case 1:
			return GoingOutReason.PUBLIC;
		case 2:
			return GoingOutReason.COMPENSATION;
		case 3:
		default:
			return GoingOutReason.UNION;
		}
	}
}
