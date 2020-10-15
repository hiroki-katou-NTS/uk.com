package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout.GoOutOfMonthly;

@Data
/** 月別実績の外出 */
@NoArgsConstructor
@AllArgsConstructor
public class GoOutOfMonthlyDto implements ItemConst {

	/** 育児外出: 育児外出 */
	@AttendanceItemLayout(jpPropertyName = E_CHILD_CARE, layout = LAYOUT_A, listMaxLength = 2, listNoIndex = true, enumField = DEFAULT_ENUM_FIELD_NAME)
	private List<GoOutForChildCareDto> goOutForChildCares;

	/** 外出: 集計外出 */
	@AttendanceItemLayout(jpPropertyName = GO_OUT, layout = LAYOUT_B, listMaxLength = 4, listNoIndex = true, enumField = DEFAULT_ENUM_FIELD_NAME)
	private List<AggregateGoOutDto> goOuts;
	
	public GoOutOfMonthly toDomain(){
		return GoOutOfMonthly.of(ConvertHelper.mapTo(goOuts, c -> c.toDomain()), 
								ConvertHelper.mapTo(goOutForChildCares, c -> c.toDomain()));
	}
	
	public static GoOutOfMonthlyDto from(GoOutOfMonthly domain) {
		GoOutOfMonthlyDto dto = new GoOutOfMonthlyDto();
		if(domain != null) {
			dto.setGoOutForChildCares(ConvertHelper.mapTo(domain.getGoOutForChildCares(), c -> GoOutForChildCareDto.from(c.getValue())));
			dto.setGoOuts(ConvertHelper.mapTo(domain.getGoOuts(), c -> AggregateGoOutDto.from(c.getValue())));
		}
		return dto;
	}
}
