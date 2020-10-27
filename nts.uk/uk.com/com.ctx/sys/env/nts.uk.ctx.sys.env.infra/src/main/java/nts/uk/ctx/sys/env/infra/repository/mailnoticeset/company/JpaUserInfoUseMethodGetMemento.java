/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodGetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUseContactCom;

/**
 * The Class JpaUserInfoUseMethodGetMemento.
 */
public class JpaUserInfoUseMethodGetMemento implements UserInfoUseMethodGetMemento {

	/** The entity. */
	private SevmtUseContactCom entity;
	
	/**
	 * Instantiates a new jpa user info use method get memento.
	 *
	 * @param entity the entity
	 */
	public JpaUserInfoUseMethodGetMemento(SevmtUseContactCom entity) {
		this.entity = entity;
	}

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getSevmtUseContactComPK().getCid();
	}

	/**
	 * Gets the setting item.
	 *
	 * @return the setting item
	 */
	@Override
	public UserInfoItem getSettingItem() {
		return UserInfoItem.valueOf(this.entity.getSevmtUseContactComPK().getSettingItem());
	}

	/**
	 * Gets the self edit.
	 *
	 * @return the self edit
	 */
	@Override
	public SelfEditUserInfo getSelfEdit() {
		return SelfEditUserInfo.valueOf(this.entity.getSelfEdit());
	}

	/**
	 * Gets the setting use mail.
	 *
	 * @return the setting use mail
	 */
	@Override
	public Optional<SettingUseSendMail> getSettingUseMail() {
		if (this.entity.getUseMailSet() == null) {
			return Optional.ofNullable(null);
		}
		return Optional.of(SettingUseSendMail.valueOf(this.entity.getUseMailSet()));
	}

}
