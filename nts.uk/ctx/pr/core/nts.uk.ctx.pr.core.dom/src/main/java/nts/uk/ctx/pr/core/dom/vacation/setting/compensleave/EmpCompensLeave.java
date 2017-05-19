/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.compensleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmpCompensLeave.
 */
@Getter
public class EmpCompensLeave extends DomainObject {

	/** The company id. */
	private String companyId;

	/** The emp contract type code. */
	private String empContractTypeCode;

	/** The setting. */
	private CompensatoryLeaveSetting setting;

	/**
	 * Instantiates a new emp compens leave.
	 *
	 * @param companyId
	 *            the company id
	 * @param empContractTypeCode
	 *            the emp contract type code
	 * @param setting
	 *            the setting
	 */
	public EmpCompensLeave(String companyId, String empContractTypeCode,
			CompensatoryLeaveSetting setting) {
		super();
		this.companyId = companyId;
		this.empContractTypeCode = empContractTypeCode;
		this.setting = setting;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new emp compens leave.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpCompensLeave(EmpCompensLeaveGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.empContractTypeCode = memento.getEmpContractTypeCode();
		this.setting = memento.getSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpCompensLeaveSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmpContractTypeCode(this.empContractTypeCode);
		memento.setSetting(this.setting);
	}

}
