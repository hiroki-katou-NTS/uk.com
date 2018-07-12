package nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgeStandard {
	/** 年齢基準年区分 */
	private AgeBaseYear ageCriteriaCls;
	
	/** 年齢基準日 */
	private String ageBaseDate;
}
