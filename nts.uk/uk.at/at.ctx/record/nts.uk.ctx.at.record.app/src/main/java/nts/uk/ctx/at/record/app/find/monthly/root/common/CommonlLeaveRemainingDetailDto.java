package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveRemainingDetail;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.ReserveLeaveRemainingDetail;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

@Data
/** 年休残明細 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonlLeaveRemainingDetailDto implements ItemConst {

	/** 付与日 */
	@AttendanceItemValue(type = ValueType.DATE)
	@AttendanceItemLayout(jpPropertyName = GRANT, layout = LAYOUT_A)
	private GeneralDate grantDate;

	/** 日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B)
	private double days;

	/** 時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_C)
	private int time;

	public static CommonlLeaveRemainingDetailDto from(AnnualLeaveRemainingDetail domain) {
		return domain == null ? null : new CommonlLeaveRemainingDetailDto(domain.getGrantDate(), domain.getDays().v(),
						domain.getTime().isPresent() ? domain.getTime().get().valueAsMinutes() : 0);
	}

	public AnnualLeaveRemainingDetail toDomain() {
		return AnnualLeaveRemainingDetail.of(grantDate, new AnnualLeaveRemainingDayNumber(days),
				Optional.of(new AnnualLeaveRemainingTime(time)));
	}
	
	public static CommonlLeaveRemainingDetailDto from(ReserveLeaveRemainingDetail domain) {
		return domain == null ? null : new CommonlLeaveRemainingDetailDto(domain.getGrantDate(), domain.getDays().v(), 0);
	}

	public ReserveLeaveRemainingDetail toReserveDomain() {
		return ReserveLeaveRemainingDetail.of(grantDate, new ReserveLeaveRemainingDayNumber(days));
	}
}
