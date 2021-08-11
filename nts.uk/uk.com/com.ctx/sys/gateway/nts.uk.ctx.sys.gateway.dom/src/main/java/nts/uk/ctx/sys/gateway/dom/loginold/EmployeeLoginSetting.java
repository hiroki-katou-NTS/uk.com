/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.loginold;

import lombok.Getter;

/**
 * The Class EmployeeLoginSetting.
 */

@Getter
//社員ログイン設定
public class EmployeeLoginSetting {

	//契約コード
	/** The contract code. */
	private ContractCode contractCode;

	//形式2許可区分
	/** The form 2 permit atr. */
	private ManageDistinct form2PermitAtr;

	//形式3許可区分
	/** The form 3 permit atr. */
	private ManageDistinct form3PermitAtr;

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(EmployeeLoginSettingSetMemento memento) {
		memento.setContractCode(this.contractCode);
		memento.setForm2PermitAtr(this.form2PermitAtr);
		memento.setForm3PermitAtr(this.form3PermitAtr);
	}

	/**
	 * Instantiates a new employee login setting.
	 *
	 * @param memento the memento
	 */
	public EmployeeLoginSetting(EmployeeLoginSettingGetMemento memento) {
		this.contractCode = memento.getContractCode();
		this.form2PermitAtr = memento.getForm2PermitAtr();
		this.form3PermitAtr = memento.getForm3PermitAtr();
	}
}
