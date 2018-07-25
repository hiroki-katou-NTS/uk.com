package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgeRangeDto {

	/** 年齢下限 */
	private Integer ageLowerLimit;

	/** 年齢上限 */
	private Integer ageHigherLimit;

	public static AgeRangeDto fromDomain(AgeRange ageRange) {
		if (ageRange != null) {
			return new AgeRangeDto(ageRange.getAgeLowerLimit().v(), ageRange.getAgeHigherLimit().v());
		}
		return null;
	}

}
