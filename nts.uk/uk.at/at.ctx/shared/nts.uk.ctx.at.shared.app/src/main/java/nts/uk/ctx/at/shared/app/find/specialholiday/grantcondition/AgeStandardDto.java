package nts.uk.ctx.at.shared.app.find.specialholiday.grantcondition;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeStandard;
import nts.uk.shr.com.time.calendar.MonthDay;

@Value
public class AgeStandardDto {
	/** 年齢基準年区分 */
	private int ageCriteriaCls;
	
	/** 年齢基準日 */
	private MonthDay ageBaseDate;

	public static AgeStandardDto fromDomain(AgeStandard ageStandard) {
		if(ageStandard == null) {
			return null;
		}
		
		return new AgeStandardDto(
				ageStandard.getAgeCriteriaCls().value,
				ageStandard.getAgeBaseDate()
		);
	}
}
