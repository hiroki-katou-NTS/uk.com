package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**年数条件*/
@AllArgsConstructor
@Getter
public class YearRequirement {

	private YearType yearType;
	
	private Integer_0_99 yearMinimumNumber;
	
	private Integer_0_99 yearStandardNumber;
	
	public static YearRequirement createFromJavaType(int yearType, int yearMinimumNumber, int yearStandardNumber) {
		return new YearRequirement(
				EnumAdaptor.valueOf(yearType, YearType.class), 
				new Integer_0_99(yearMinimumNumber), 
				new Integer_0_99(yearStandardNumber)
				);
	}
	
}
