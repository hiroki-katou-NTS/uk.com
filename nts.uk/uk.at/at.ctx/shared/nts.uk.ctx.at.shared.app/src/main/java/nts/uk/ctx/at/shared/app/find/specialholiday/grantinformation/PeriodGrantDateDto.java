package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.PeriodGrantDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PeriodGrantDateDto {

	/** 期間.start */
	private String start;
	
	/** 期間 .end*/
	private String end; 
	
	/** 付与日数 */
	private Integer grantDays;
	
	public static PeriodGrantDateDto fromDomain(PeriodGrantDate domain) {
		return new PeriodGrantDateDto(
				domain.getPeriod().start().toString(),
				domain.getPeriod().end().toString(),
				domain.getGrantDays().getGrantDays().v());
	}
}
