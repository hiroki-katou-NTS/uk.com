package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_0_99;

/** 年数条件 */
@Getter
public class YearRequirement {

	private YearType yearType;

	private Integer_0_99 yearMinimumNumber;

	private Integer_0_99 yearStandardNumber;

	public static YearRequirement createFromJavaType(int yearType, int yearMinimumNumber, int yearStandardNumber) {
		return new YearRequirement(EnumAdaptor.valueOf(yearType, YearType.class), new Integer_0_99(yearMinimumNumber),
				new Integer_0_99(yearStandardNumber));
	}

	public YearRequirement(YearType yearType, Integer_0_99 yearMinimumNumber, Integer_0_99 yearStandardNumber) {
		super();
		this.yearType = yearType;
		this.yearMinimumNumber = yearMinimumNumber;
		this.yearStandardNumber = yearStandardNumber;
		validate();
	}
	
	private void validate() {
		if(this.yearMinimumNumber.v()>this.yearStandardNumber.v()) {
			throw new BusinessException("MsgJ_51");
		}
	}

}
