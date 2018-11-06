/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.subst;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

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

	/** The setting. */
	// 振休取得・使用方法
	private SubstVacationSetting setting;

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
			SubstVacationSetting setting) {
		super();
		this.companyId = companyId;
		this.empContractTypeCode = empContractTypeCode;
		this.setting = setting;
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
		this.setting = memento.getSetting();
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
		memento.setSetting(this.setting);
	}

}
