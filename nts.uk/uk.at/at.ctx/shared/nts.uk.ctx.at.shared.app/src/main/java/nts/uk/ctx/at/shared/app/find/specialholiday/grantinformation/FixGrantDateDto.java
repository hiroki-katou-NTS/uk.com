package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FixGrantDateDto {
	
	/** 付与日数 */
	private Integer grantDays;
	
	/** 期限 */
	private GrantDeadlineDto grantPeriodic;

	/** 付与月日 */
	private MonthDayDto grantMonthDay;
	
	public static FixGrantDateDto fromDomain(FixGrantDate domain) {
		return new FixGrantDateDto(
				domain.getGrantDays().getGrantDays().v(),
				GrantDeadlineDto.fromDomain(domain.getGrantPeriodic()),
				domain.getGrantMonthDay().isPresent() ? new MonthDayDto(domain.getGrantMonthDay().get().getMonth(), domain.getGrantMonthDay().get().getDay()) : null);
	}
	
}
