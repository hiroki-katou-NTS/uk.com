/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFunc;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFuncPK;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaSendMailByFunctionSettingSetMemento.
 */
public class JpaSendMailByFunctionSettingSetMemento implements SendMailByFunctionSettingSetMemento {

	/** The entity. */
	private SevmtMailDestinFunc entity;

	/**
	 * Instantiates a new jpa send mail by function setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaSendMailByFunctionSettingSetMemento(SevmtMailDestinFunc entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento#setFunctionId(nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId)
	 */
	@Override
	public void setFunctionId(FunctionId functionId) {
		SevmtMailDestinFuncPK pk = this.entity.getSevmtMailDestinFuncPK();
		pk.setFunctionId(functionId.v());
		this.entity.setSevmtMailDestinFuncPK(pk);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingSetMemento#setSendSetting(nts.uk.shr.com.enumcommon.NotUseAtr)
	 */
	@Override
	public void setSendSetting(NotUseAtr sendSetting) {
		this.entity.setSendSet(sendSetting.value);
	}

}
