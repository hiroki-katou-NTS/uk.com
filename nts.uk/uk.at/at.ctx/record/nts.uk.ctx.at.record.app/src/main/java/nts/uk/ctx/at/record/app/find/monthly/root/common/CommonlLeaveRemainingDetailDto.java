package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveRemainingDetail;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave.ReserveLeaveRemainingDetail;

@Data
/** 年休残明細 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonlLeaveRemainingDetailDto implements ItemConst, AttendanceItemDataGate {

	/** 付与日 */
	@AttendanceItemValue(type = ValueType.DATE)
	@AttendanceItemLayout(jpPropertyName = GRANT, layout = LAYOUT_A)
	private GeneralDate grantDate;

	/** 日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B)
	private double days;

	/** 時間 */
	@AttendanceItemValue(type = ValueType.TIME)
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
	
	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case GRANT:
			return Optional.of(ItemValue.builder().value(grantDate).valueType(ValueType.DATE));
		case DAYS:
			return Optional.of(ItemValue.builder().value(days).valueType(ValueType.DAYS));
		case TIME:
			return Optional.of(ItemValue.builder().value(time).valueType(ValueType.TIME));
		default:
			return Optional.empty();
		}
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case GRANT:
			this.grantDate = value.valueOrDefault(null);
			break;
		case DAYS:
			this.days = value.valueOrDefault(0);
			break;
		case TIME:
			this.time = value.valueOrDefault(0);
			break;
		default:
			break;
		}
	}
	
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case GRANT:
		case DAYS:
		case TIME:
			return PropType.VALUE;
		default:
			return PropType.OBJECT;
		}
	}
}
