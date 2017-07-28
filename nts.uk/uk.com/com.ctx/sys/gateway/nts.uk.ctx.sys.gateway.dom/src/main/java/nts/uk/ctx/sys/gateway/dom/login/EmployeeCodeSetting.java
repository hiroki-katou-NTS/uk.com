/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Getter;

/**
 * The Class EmployeeCodeSetting.
 */
@Getter
public class EmployeeCodeSetting {

	/** The company id. */
	private String companyId;

	/** The number digit. */
	private Integer numberDigit;

	/** The edit type. */
	private EmployCodeEditType editType;

	/**
	 * @param companyId
	 * @param numberDigit
	 * @param editType
	 */
	public EmployeeCodeSetting(EmployeeCodeSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.numberDigit = memento.getNumberDigit();
		this.editType = memento.getEditType();
	}

	public void saveToMemento(EmployeeCodeSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setNumberDigit(this.numberDigit);
		memento.setEditType(this.editType);
	}
}
