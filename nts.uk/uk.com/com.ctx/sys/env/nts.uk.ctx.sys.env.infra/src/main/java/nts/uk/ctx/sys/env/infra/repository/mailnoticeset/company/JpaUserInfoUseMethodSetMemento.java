/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.repository.mailnoticeset.company;

import java.util.Optional;

import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company.SevmtUseContactCom;

/**
 * The Class JpaUserInfoUseMethodSetMemento.
 */
public class JpaUserInfoUseMethodSetMemento implements UserInfoUseMethodSetMemento {

	/** The entity. */
	private SevmtUseContactCom entity;

	/**
	 * Instantiates a new jpa user info use method set memento.
	 *
	 * @param entity the entity
	 */
	public JpaUserInfoUseMethodSetMemento(SevmtUseContactCom entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getSevmtUseContactComPK().setCid(companyId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setSettingItem(nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem)
	 */
	@Override
	public void setSettingItem(UserInfoItem settingItem) {
		this.entity.getSevmtUseContactComPK().setSettingItem(settingItem.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setSelfEdit(nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo)
	 */
	@Override
	public void setSelfEdit(SelfEditUserInfo selfEdit) {
		this.entity.setSelfEdit(selfEdit == null ? null : selfEdit.value);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setSettingUseMail(java.util.Optional)
	 */
	@Override
	public void setSettingUseMail(Optional<SettingUseSendMail> settingUseMail) {
		this.entity.setUseMailSet(settingUseMail.isPresent() ? settingUseMail.get().value : null);
	}

}
