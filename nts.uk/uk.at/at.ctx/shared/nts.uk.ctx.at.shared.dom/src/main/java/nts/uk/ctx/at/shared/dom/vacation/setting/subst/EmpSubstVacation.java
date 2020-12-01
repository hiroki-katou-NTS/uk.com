/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class EmpSubstVacation.
 */
// 雇用振休管理設定
@Getter
public class EmpSubstVacation extends DomainObject {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The emp contract type code. */
	// 雇用区分コード
	private String empContractTypeCode;
	
	private ManageDistinct manageDistinct;
	/**
	 * Instantiates a new emp subst vacation.
	 *
	 * @param companyId
	 *            the company id
	 * @param empContractTypeCode
	 *            the emp contract type code
	 * @param setting
	 *            the setting
	 */
	public EmpSubstVacation(String companyId, String empContractTypeCode,
			ManageDistinct manageDistinct) {
		super();
		this.companyId = companyId;
		this.empContractTypeCode = empContractTypeCode;
		this.manageDistinct = manageDistinct;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new emp subst vacation.
	 *
	 * @param memento
	 *            the memento
	 */
	public EmpSubstVacation(EmpSubstVacationGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.empContractTypeCode = memento.getEmpContractTypeCode();
		this.manageDistinct = memento.getManageDistinct();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmpSubstVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmpContractTypeCode(this.empContractTypeCode);
		memento.setManageDistinct(this.manageDistinct);
	}

}
