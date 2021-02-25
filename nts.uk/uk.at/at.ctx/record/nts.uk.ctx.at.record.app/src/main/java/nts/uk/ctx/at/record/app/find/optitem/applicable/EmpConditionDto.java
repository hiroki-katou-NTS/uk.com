/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.applicable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmploymentCondition;

/**
 * The Class EmpConditionDto.
 */
@Getter
@Setter
public class EmpConditionDto implements EmpConditionSetMemento {

	/** The optional item no. */
	private Integer optionalItemNo;

	/** The emp conditions. */
	private List<EmploymentConditionDto> empConditions;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		// Not used.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * SetEmpConditions(java.util.List)
	 */
	@Override
	public void setEmpConditions(List<EmploymentCondition> empConditions) {
		this.empConditions = empConditions.stream()
				.map(item -> new EmploymentConditionDto(item.getEmpCd(), item.getEmpApplicableAtr().value))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optNo) {
		this.optionalItemNo = optNo.v();
	}
}
