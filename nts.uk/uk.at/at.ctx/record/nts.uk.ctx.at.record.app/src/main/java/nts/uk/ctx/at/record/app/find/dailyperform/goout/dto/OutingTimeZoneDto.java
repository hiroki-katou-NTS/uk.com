package nts.uk.ctx.at.record.app.find.dailyperform.goout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.common.WithActualTimeStampDto;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutingTimeZoneDto implements ItemConst{

	private Integer no;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = GO_OUT)
	private WithActualTimeStampDto outing;

	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = BACK)
	private WithActualTimeStampDto comeBack;

	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = REASON)
	@AttendanceItemValue(type = ValueType.ATTR)
	private int reason;
	
	private int outTimeCalc;
	
	private int outTIme;

	@Override
	protected OutingTimeZoneDto clone() {
		return new OutingTimeZoneDto(no, outing == null ? null : outing.clone(), 
				comeBack == null ? null : comeBack.clone(), reason, outTimeCalc, outTIme);
	}
	
	public GoingOutReason reason() {
		switch (this.reason) {
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
