package nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 年齢範囲
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@Getter
@Data
public class AgeRange {
	/** 年齢下限 */
	private AgeLimit ageLowerLimit;
	
	/** 年齢上限 */
	private AgeLimit ageHigherLimit;

	public static AgeRange createFromJavaType(AgeLimit ageLowerLimit, AgeLimit ageHigherLimit) {
		return new AgeRange(ageLowerLimit, ageHigherLimit);
	}
}
