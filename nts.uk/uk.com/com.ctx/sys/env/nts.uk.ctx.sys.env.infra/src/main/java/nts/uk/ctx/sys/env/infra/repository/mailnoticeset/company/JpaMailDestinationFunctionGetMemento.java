/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionGetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtMailDestinFunc;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaMailDestinationFunctionGetMemento.
 */
public class JpaMailDestinationFunctionGetMemento implements MailDestinationFunctionGetMemento {

	/** The lst entity. */
	private List<SevmtMailDestinFunc> lstEntity;

	/**
	 * Instantiates a new jpa mail destination function get memento.
	 *
	 * @param lstEntity
	 *            the lst entity
	 */
	public JpaMailDestinationFunctionGetMemento(List<SevmtMailDestinFunc> lstEntity) {
		this.lstEntity = lstEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionGetMemento#getSettingItem()
	 */
	@Override
	public UserInfoItem getSettingItem() {
		return UserInfoItem.valueOf(this.lstEntity.get(0).getSevmtMailDestinFuncPK().getSettingItem());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionGetMemento#getSendByFunctionSetting()
	 */
	@Override
	public List<SendMailByFunctionSetting> getSendByFunctionSetting() {
		return this.lstEntity.stream().map(item -> {
			return new SendMailByFunctionSetting(new JpaSendMailByFunctionSettingGetMemento(item));
		}).collect(Collectors.toList());
	}

	@Override
	public List<SendMailByFunctionSetting> getSendByFunctionSetting(NotUseAtr use) {
		return this.lstEntity.stream().map(item -> {
			return new SendMailByFunctionSetting(new JpaSendMailByFunctionSettingGetMemento(item));
		}).collect(Collectors.toList()).stream().filter(item -> item.getSendSetting().equals(use))
				.collect(Collectors.toList());
	}

}
