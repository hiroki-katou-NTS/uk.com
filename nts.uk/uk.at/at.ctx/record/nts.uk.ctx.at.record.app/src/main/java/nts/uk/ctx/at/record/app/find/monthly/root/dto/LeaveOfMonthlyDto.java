package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.leave.AggregateLeaveDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.leave.AnyLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;

@Data
/** 月別実績の休業 */
@NoArgsConstructor
@AllArgsConstructor
public class LeaveOfMonthlyDto implements ItemConst, AttendanceItemDataGate {
	/** 固定休業日数: 集計休業日数 */
	@AttendanceItemLayout(jpPropertyName = FIXED, layout = LAYOUT_A, listMaxLength = 5, listNoIndex = true, enumField = DEFAULT_ENUM_FIELD_NAME)
	private List<AggregateLeaveDaysDto> fixLeaveDays;

	/** 任意休業日数: 任意休業 */
	 @AttendanceItemLayout(jpPropertyName = OPTIONAL, layout = LAYOUT_B, listMaxLength = 4, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AnyLeaveDto> anyLeaveDays;

	public static LeaveOfMonthlyDto from(LeaveOfMonthly domain) {
		LeaveOfMonthlyDto dto = new LeaveOfMonthlyDto();
		if (domain != null) {
			dto.setAnyLeaveDays(ConvertHelper.mapTo(domain.getAnyLeaveDays(), 
							c -> new AnyLeaveDto(c.getValue().getAnyLeaveNo(),
												c.getValue().getDays() == null ? 0 : c.getValue().getDays().v())));
			dto.setFixLeaveDays(ConvertHelper.mapTo(domain.getFixLeaveDays(), 
					c -> new AggregateLeaveDaysDto(c.getValue().getLeaveAtr() == null ? 0 : c.getValue().getLeaveAtr().value,
										c.getValue().getDays() == null ? 0 : c.getValue().getDays().v())));
		}
		return dto;
	}

	public LeaveOfMonthly toDomain() {
		return LeaveOfMonthly.of(
				ConvertHelper.mapTo(fixLeaveDays,
						c -> AggregateLeaveDays.of(ConvertHelper.getEnum(c.getAttr(), CloseAtr.class),
								new AttendanceDaysMonth(c.getDays()))),
				ConvertHelper.mapTo(anyLeaveDays, c -> AnyLeave.of(c.getNo(),
						new AttendanceDaysMonth(c.getDays()))));
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		switch (path) {
		case FIXED:
			return new AggregateLeaveDaysDto();
		case OPTIONAL:
			return new AnyLeaveDto();
		default:
			return null;
		}
	}

	@Override
	public int size(String path) {
		switch (path) {
		case FIXED:
			return 5;
		case OPTIONAL:
			return 4;
		default:
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case FIXED:
			return PropType.ENUM_LIST;
		case OPTIONAL:
			return PropType.IDX_LIST;
		default:
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		switch (path) {
		case FIXED:
			return (List<T>) fixLeaveDays;
		case OPTIONAL:
			return (List<T>) anyLeaveDays;
		default:
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		switch (path) {
		case FIXED:
			fixLeaveDays = (List<AggregateLeaveDaysDto>) value; break;
		case OPTIONAL:
			anyLeaveDays = (List<AnyLeaveDto>) value; break;
		default:
		}
	}

	
}
