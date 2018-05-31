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
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevstMailDestinFunc;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import nts.uk.shr.com.enumcommon.NotUseAtr;
=======
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030

/**
 * The Class JpaMailDestinationFunctionGetMemento.
 */
public class JpaMailDestinationFunctionGetMemento implements MailDestinationFunctionGetMemento {

	/** The lst entity. */
	private List<SevstMailDestinFunc> lstEntity;

	/**
	 * Instantiates a new jpa mail destination function get memento.
	 *
<<<<<<< HEAD
	 * @param lstEntity the lst entity
=======
<<<<<<< HEAD
	 * @param lstEntity
	 *            the lst entity
=======
	 * @param lstEntity the lst entity
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030
	 */
	public JpaMailDestinationFunctionGetMemento(List<SevstMailDestinFunc> lstEntity) {
		this.lstEntity = lstEntity;
	}

<<<<<<< HEAD
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionGetMemento#getSettingItem()
=======
<<<<<<< HEAD
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionGetMemento#getSettingItem()
=======
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionGetMemento#getSettingItem()
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030
	 */
	@Override
	public UserInfoItem getSettingItem() {
		return UserInfoItem.valueOf(this.lstEntity.get(0).getSevstMailDestinFuncPK().getSettingItem());
	}

<<<<<<< HEAD
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionGetMemento#getSendByFunctionSetting()
=======
<<<<<<< HEAD
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionGetMemento#getSendByFunctionSetting()
=======
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionGetMemento#getSendByFunctionSetting()
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030
	 */
	@Override
	public List<SendMailByFunctionSetting> getSendByFunctionSetting() {
		return this.lstEntity.stream().map(item -> {
			return new SendMailByFunctionSetting(new JpaSendMailByFunctionSettingGetMemento(item));
		}).collect(Collectors.toList());
	}

<<<<<<< HEAD
=======
<<<<<<< HEAD
	@Override
	public List<SendMailByFunctionSetting> getSendByFunctionSetting(NotUseAtr use) {
		return this.lstEntity.stream().map(item -> {
			return new SendMailByFunctionSetting(new JpaSendMailByFunctionSettingGetMemento(item));
		}).collect(Collectors.toList()).stream().filter(item -> item.getSendSetting().equals(use))
				.collect(Collectors.toList());
	}

=======
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030
}
