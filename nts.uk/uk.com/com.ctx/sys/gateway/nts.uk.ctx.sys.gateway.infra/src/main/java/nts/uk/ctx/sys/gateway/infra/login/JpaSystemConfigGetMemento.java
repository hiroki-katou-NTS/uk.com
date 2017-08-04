/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.infra.login;

import nts.uk.ctx.sys.gateway.dom.login.InstallForm;
import nts.uk.ctx.sys.gateway.dom.login.SystemConfigGetMemento;
import nts.uk.ctx.sys.gateway.entity.login.SgwstSystemConfig;

public class JpaSystemConfigGetMemento implements SystemConfigGetMemento {

	private SgwstSystemConfig entity;

	public JpaSystemConfigGetMemento(SgwstSystemConfig entity) {
		this.entity = entity;
	}

	@Override
	public InstallForm getInstallForm() {
		return InstallForm.valueOf(new Integer(this.entity.getInstallForm()));
	}

}
