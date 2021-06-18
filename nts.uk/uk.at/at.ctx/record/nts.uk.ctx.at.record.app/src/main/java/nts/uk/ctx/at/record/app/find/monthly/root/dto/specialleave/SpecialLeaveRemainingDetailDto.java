package nts.uk.ctx.at.record.app.find.monthly.root.dto.specialleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;

/**
 * 特休残明細
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialLeaveRemainingDetailDto implements ItemConst, AttendanceItemDataGate {

	/** 付与日 */
	private GeneralDate grantDate;

	/** 日数 */
	private double days;
	/** 時間 */
	private Integer time;

}
