package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
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

		List<CommonlLeaveRemainingDetailDto> detailDtoList = new ArrayList<CommonlLeaveRemainingDetailDto>();
		domain.getDetails().forEach(c->{
			detailDtoList.add(CommonlLeaveRemainingDetailDto.from(c));
		});
		return domain == null ? null : new RsvLeaveRemainingNumberDto(
				domain.getTotalRemainingDays().v(), detailDtoList);
	}

	public ReserveLeaveRemainingNumber toReserveDomain() {
		return ReserveLeaveRemainingNumber.of(
				new ReserveLeaveRemainingDayNumber(totalRemainingDays),
				ConvertHelper.mapTo(details, c -> c == null ? null : c.toReserveDomain()));
	}
}
