package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

@Data
/** 積休残数 */
@NoArgsConstructor
@AllArgsConstructor
public class RsvLeaveRemainingNumberDto implements ItemConst {

	/** 合計残日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double totalRemainingDays;

	/** 明細 */
	// @AttendanceItemLayout(jpPropertyName = "明細", layout = "C", listMaxLength = ??)
	private List<CommonlLeaveRemainingDetailDto> details;

	public static RsvLeaveRemainingNumberDto from(ReserveLeaveRemainingNumber domain) {
		// ooooo要修正！！
//		return domain == null ? null : new RsvLeaveRemainingNumberDto(
//				domain.getTotalRemainingDays().v(),
//				ConvertHelper.mapTo(domain.getDetails(), c -> CommonlLeaveRemainingDetailDto.from(c)));
		return null;
	}
	
	public ReserveLeaveRemainingNumber toReserveDomain() {
		// ooooo要修正！！
//		return ReserveLeaveRemainingNumber.of(
//				new ReserveLeaveRemainingDayNumber(totalRemainingDays), 
//				ConvertHelper.mapTo(details, c -> c == null ? null : c.toReserveDomain()));
		return null;
	}
}
