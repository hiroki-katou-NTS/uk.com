package nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeRange;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgeRangeDto {

	/** 年齢下限 */
	private int ageLowerLimit;

	/** 年齢上限 */
	private int ageHigherLimit;

	public static AgeRangeDto fromDomain(AgeRange ageRange) {
		return new AgeRangeDto(ageRange.getAgeLowerLimit().v(), ageRange.getAgeHigherLimit().v());
	}

}
