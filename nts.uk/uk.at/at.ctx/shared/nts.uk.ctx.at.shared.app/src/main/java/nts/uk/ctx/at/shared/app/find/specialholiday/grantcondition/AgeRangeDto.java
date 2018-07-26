package nts.uk.ctx.at.shared.app.find.specialholiday.grantcondition;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;

@Value
public class AgeRangeDto {
	/** 年齢下限 */
	private int ageLowerLimit;

	/** 年齢上限 */
	private int ageHigherLimit;

	public static AgeRangeDto fromDomain(AgeRange ageRange) {
		if(ageRange == null) {
			return null;
		}
		
		return new AgeRangeDto(
				ageRange.getAgeLowerLimit().v(),
				ageRange.getAgeHigherLimit().v()
		);
	}
}
