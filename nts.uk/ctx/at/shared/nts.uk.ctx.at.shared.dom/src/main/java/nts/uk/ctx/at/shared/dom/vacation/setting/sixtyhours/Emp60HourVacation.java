/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class EmpSubstVacation.
 */
// 雇用別60H超休管理設定
@Getter
public class Emp60HourVacation extends DomainObject {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The emp contract type code. */
	// 雇用区分コード
	private String empContractTypeCode;

	/** The setting. */
	// 設定
	private SixtyHourVacationSetting setting;

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
	public Emp60HourVacation(String companyId, String empContractTypeCode,
			SixtyHourVacationSetting setting) {
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
	public Emp60HourVacation(Emp60HourVacationGetMemento memento) {
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
	public void saveToMemento(Emp60HourVacationSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setEmpContractTypeCode(this.empContractTypeCode);
		memento.setSetting(this.setting);
	}

}
