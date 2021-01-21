package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の割増時間 */
public class PremiumTimeOfMonthlyDto implements ItemConst {

	/** 割増時間: 集計割増時間 */
	@AttendanceItemLayout(jpPropertyName = PREMIUM, layout = LAYOUT_A, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AggregatePremiumTimeDto> premiumTimes;

	public static PremiumTimeOfMonthlyDto from(PremiumTimeOfMonthly domain) {
		PremiumTimeOfMonthlyDto dto = new PremiumTimeOfMonthlyDto();
		if(domain != null) {
			dto.setPremiumTimes(ConvertHelper.mapTo(domain.getPremiumTime(), c -> AggregatePremiumTimeDto.from(c.getValue())));
		}
		return dto;
	}
	
	public PremiumTimeOfMonthly toDomain(){
		return PremiumTimeOfMonthly.of(ConvertHelper.mapTo(premiumTimes, c -> c.toDomain()));
	}
}
