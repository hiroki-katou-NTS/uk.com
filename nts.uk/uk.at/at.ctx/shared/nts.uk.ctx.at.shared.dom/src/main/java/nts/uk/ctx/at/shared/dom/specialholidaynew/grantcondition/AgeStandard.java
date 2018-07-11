package nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgeStandard {
	/** 年齢基準年区分 */
	private AgeBaseYear ageCriteriaCls;
	
	/** 年齢基準日 */
	private String ageBaseDate;

	public static AgeStandard createFromJavaType(int ageCriteriaCls, String ageBaseDate) {
		return new AgeStandard(EnumAdaptor.valueOf(ageCriteriaCls, AgeBaseYear.class), ageBaseDate);
	}
}
