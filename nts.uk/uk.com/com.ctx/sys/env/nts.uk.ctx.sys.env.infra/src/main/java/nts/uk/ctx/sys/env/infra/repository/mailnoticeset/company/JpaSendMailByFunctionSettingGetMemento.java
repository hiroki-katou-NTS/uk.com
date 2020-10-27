/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingGetMemento;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFunc;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaSendMailByFunctionSettingGetMemento.
 */
public class JpaSendMailByFunctionSettingGetMemento implements SendMailByFunctionSettingGetMemento {

	/** The entity. */
	private SevmtMailDestinFunc entity;

	/**
	 * Instantiates a new jpa send mail by function setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSendMailByFunctionSettingGetMemento(SevmtMailDestinFunc entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingGetMemento#getFunctionId()
	 */
	@Override
	public FunctionId getFunctionId() {
		return new FunctionId(this.entity.getSevmtMailDestinFuncPK().getFunctionId());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSettingGetMemento#getSendSetting()
	 */
	@Override
	public NotUseAtr getSendSetting() {
		return NotUseAtr.valueOf(this.entity.getSendSet());
	}

}
