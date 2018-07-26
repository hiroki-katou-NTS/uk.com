package nts.uk.ctx.at.shared.dom.specialholiday.grantcondition;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.time.calendar.MonthDay;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgeStandard {
	/** 年齢基準年区分 */
	private AgeBaseYear ageCriteriaCls;
	
	/** 年齢基準日 */
	private MonthDay ageBaseDate;

	public static AgeStandard createFromJavaType(int ageCriteriaCls, MonthDay ageBaseDate) {
		return new AgeStandard(EnumAdaptor.valueOf(ageCriteriaCls, AgeBaseYear.class), ageBaseDate);
	}
}
