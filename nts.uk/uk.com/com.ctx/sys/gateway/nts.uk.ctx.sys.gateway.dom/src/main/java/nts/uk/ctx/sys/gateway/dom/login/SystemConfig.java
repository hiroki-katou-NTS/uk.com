/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.dom.login;

import lombok.Getter;

/**
 * The Class SystemConfig.
 */
@Getter
public class SystemConfig {

	//インストール形式
	/** The install form. */
	private InstallForm installForm;

	/**
	 * @param installForm
	 */
	public SystemConfig(SystemConfigGetMemento memento) {
		this.installForm = memento.getInstallForm();
	}

	public void saveToMemento (SystemConfigSetMemento memento)
	{
		memento.setInstallForm(this.installForm);
	}

}
