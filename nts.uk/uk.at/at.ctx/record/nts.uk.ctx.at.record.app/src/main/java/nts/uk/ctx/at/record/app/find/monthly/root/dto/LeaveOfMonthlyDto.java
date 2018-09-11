package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AggregateLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AnyLeave;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;

@Data
/** 月別実績の休業 */
@NoArgsConstructor
@AllArgsConstructor
public class LeaveOfMonthlyDto implements ItemConst {
	/** 固定休業日数: 集計休業日数 */
	@AttendanceItemLayout(jpPropertyName = FIXED, layout = LAYOUT_A, listMaxLength = 5, listNoIndex = true, enumField = DEFAULT_ENUM_FIELD_NAME)
	private List<AggregateLeaveDaysDto> fixLeaveDays;

	/** 任意休業日数: 任意休業 */
	 @AttendanceItemLayout(jpPropertyName = OPTIONAL, layout = LAYOUT_B, listMaxLength = 4, indexField = DEFAULT_INDEX_FIELD_NAME)
	/** 任意休業日数: 任意休業 */
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
}
