/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.List;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionSetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFunc;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFuncPK;

/**
 * The Class JpaMailDestinationFunctionSetMemento.
 */
public class JpaMailDestinationFunctionSetMemento implements MailDestinationFunctionSetMemento {

	/** The entities. */
	private List<SevstMailDestinFunc> entities;
	
	/** The setting item. */
	private int settingItem;
	
	/** The company id. */
	private String companyId;

	/**
	 * Instantiates a new jpa mail destination function set memento.
	 *
	 * @param entities the entities
	 * @param companyId the company id
	 */
	public JpaMailDestinationFunctionSetMemento(List<SevstMailDestinFunc> entities,String companyId) {
		this.entities = entities;
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionSetMemento#setSettingItem(nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem)
	 */
	@Override
	public void setSettingItem(UserInfoItem settingItem) {
		this.settingItem = settingItem.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionSetMemento#setSendByFunctionSetting(java.util.List)
	 */
	@Override
	public void setSendByFunctionSetting(List<SendMailByFunctionSetting> sendByFunctionSetting) {
		sendByFunctionSetting.stream().forEach(dom -> {
			SevstMailDestinFuncPK pk = new SevstMailDestinFuncPK(this.companyId, this.settingItem,
					dom.getFunctionId().v());
			SevstMailDestinFunc entity = new SevstMailDestinFunc(pk);
			dom.saveToMemento(new JpaSendMailByFunctionSettingSetMemento(entity));
			this.entities.add(entity);
		});
	}

}
