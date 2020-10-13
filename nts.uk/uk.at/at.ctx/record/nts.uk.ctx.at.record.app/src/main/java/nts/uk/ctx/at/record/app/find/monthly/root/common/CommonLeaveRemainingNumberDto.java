package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingDetail;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingNumber;

@Data
/** 年休残数 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonLeaveRemainingNumberDto implements ItemConst {

	/** 合計残日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_A)
	private double totalRemainingDays;

	/** 合計残時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_B)
	private int totalRemainingTime;

	/** 明細 */
	// @AttendanceItemLayout(jpPropertyName = "明細", layout = "C", listMaxLength = ??)
	private List<CommonlLeaveRemainingDetailDto> details;

	public static CommonLeaveRemainingNumberDto from(AnnualLeaveRemainingNumber domain) {
		return domain == null ? null : new CommonLeaveRemainingNumberDto(
				domain.getTotalRemainingDays().v(), 
				domain.getTotalRemainingTime().isPresent() ? domain.getTotalRemainingTime().get().valueAsMinutes() : 0, 
				ConvertHelper.mapTo(domain.getDetails(), c -> CommonlLeaveRemainingDetailDto.from(c)));
	}
	
	public AnnualLeaveRemainingNumber toDomain() {
		return AnnualLeaveRemainingNumber.of(
				new AnnualLeaveRemainingDayNumber(totalRemainingDays), 
				Optional.of(new RemainingMinutes(totalRemainingTime)), 
				ConvertHelper.mapTo(details, c -> c == null ? new AnnualLeaveRemainingDetail(GeneralDate.today()) : c.toDomain()));
	}
	public static CommonLeaveRemainingNumberDto from(ReserveLeaveRemainingNumber domain) {
		return domain == null ? null : new CommonLeaveRemainingNumberDto(
				domain.getTotalRemainingDays().v(), 0, 
				ConvertHelper.mapTo(domain.getDetails(), c -> CommonlLeaveRemainingDetailDto.from(c)));
	}
	
	public ReserveLeaveRemainingNumber toReserveDomain() {
		return ReserveLeaveRemainingNumber.of(
				new ReserveLeaveRemainingDayNumber(totalRemainingDays), 
				ConvertHelper.mapTo(details, c -> c == null ? null : c.toReserveDomain()));
	}
}
