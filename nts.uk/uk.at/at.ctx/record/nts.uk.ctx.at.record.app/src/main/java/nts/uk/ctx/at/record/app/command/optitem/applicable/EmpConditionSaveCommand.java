/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.applicable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmpConditionGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.applicable.EmploymentCondition;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmpConditionSaveCommand.
 */
@Getter
@Setter
public class EmpConditionSaveCommand implements EmpConditionGetMemento {

	/** The optional item no. */
	private Integer optionalItemNo;

	/** The emp conditions. */
	private List<EmploymentConditionDto> empConditions;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.optionalItemNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getEmploymentConditions()
	 */
	@Override
	public List<EmploymentCondition> getEmploymentConditions() {
		return this.empConditions.stream()
				.map(item -> new EmploymentCondition(item.getEmpCd(), item.getEmpApplicableAtr()))
				.collect(Collectors.toList());
	}

}
