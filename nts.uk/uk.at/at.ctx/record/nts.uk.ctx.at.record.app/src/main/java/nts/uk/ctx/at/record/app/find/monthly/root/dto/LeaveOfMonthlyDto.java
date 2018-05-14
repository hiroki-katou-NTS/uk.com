package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AggregateLeaveDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.AnyLeave;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;

@Data
/** 月別実績の休業 */
@NoArgsConstructor
@AllArgsConstructor
public class LeaveOfMonthlyDto {
	/** 固定休業日数: 集計休業日数 */
	@AttendanceItemLayout(jpPropertyName = "固定休業日数", layout = "A", listMaxLength = 5, listNoIndex = true, enumField = "leaveAtr")
	private List<AggregateLeaveDaysDto> fixLeaveDays;

	/** 任意休業日数: 任意休業 */
	 @AttendanceItemLayout(jpPropertyName = "任意休業日数", layout = "A", listMaxLength = 4, indexField = "anyLeaveNo")
	/** TODO: check list max length */
	/** 任意休業日数: 任意休業 */
	// @AttendanceItemLayout(jpPropertyName = "固定休業日数", layout = "A", listMaxLength
	// = ??, indexField = "anyLeaveNo")
	private List<AnyLeaveDto> anyLeaveDays;

	public static LeaveOfMonthlyDto from(LeaveOfMonthly domain) {
		LeaveOfMonthlyDto dto = new LeaveOfMonthlyDto();
		if (domain != null) {
			dto.setAnyLeaveDays(ConvertHelper.mapTo(domain.getAnyLeaveDays(), 
							c -> new AnyLeaveDto(c.getValue().getAnyLeaveNo(),
												c.getValue().getDays() == null ? null : c.getValue().getDays().v())));
			dto.setFixLeaveDays(ConvertHelper.mapTo(domain.getFixLeaveDays(), 
					c -> new AggregateLeaveDaysDto(c.getValue().getLeaveAtr() == null ? 0 : c.getValue().getLeaveAtr().value,
										c.getValue().getDays() == null ? null : c.getValue().getDays().v())));
		}
		return dto;
	}

	public LeaveOfMonthly toDomain() {
		return LeaveOfMonthly.of(
				ConvertHelper.mapTo(fixLeaveDays,
						c -> AggregateLeaveDays.of(ConvertHelper.getEnum(c.getLeaveAtr(), CloseAtr.class),
								c.getDays() == null ? null : new AttendanceDaysMonth(c.getDays()))),
				ConvertHelper.mapTo(anyLeaveDays, c -> AnyLeave.of(c.getAnyLeaveNo(),
						c.getDays() == null ? null : new AttendanceDaysMonth(c.getDays()))));
	}
}
