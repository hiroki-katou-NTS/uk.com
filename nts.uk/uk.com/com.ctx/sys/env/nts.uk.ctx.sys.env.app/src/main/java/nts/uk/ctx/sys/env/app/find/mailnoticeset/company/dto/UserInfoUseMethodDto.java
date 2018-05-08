/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * The Class UserInfoUseMethodDto.
 */

/**
 * Instantiates a new user info use method dto.
 */
@Data
public class UserInfoUseMethodDto implements UserInfoUseMethodSetMemento {

	/** The company id. */
	private String companyId;

	/** The setting item. */
	private Integer settingItem;

	/** The self edit. */
	private Integer selfEdit;

	/** The setting use mail. */
	private Integer settingUseMail;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setSettingItem(nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem)
	 */
	@Override
	public void setSettingItem(UserInfoItem settingItem) {
		this.settingItem = settingItem.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setSelfEdit(nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo)
	 */
	@Override
	public void setSelfEdit(SelfEditUserInfo selfEdit) {
		this.selfEdit = selfEdit.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodSetMemento#setSettingUseMail(java.util.Optional)
	 */
	@Override
	public void setSettingUseMail(Optional<SettingUseSendMail> settingUseMail) {
		if (settingUseMail.isPresent()) {
			this.settingUseMail = settingUseMail.get().value;
		}
	}

}
