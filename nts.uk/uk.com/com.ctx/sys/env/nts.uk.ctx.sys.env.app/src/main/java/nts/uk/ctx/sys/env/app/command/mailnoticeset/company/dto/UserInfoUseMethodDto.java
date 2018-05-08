/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto;

import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodGetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.com.context.AppContexts;
/**
 * The Class UserInfoUseMethodDto.
 */
@Value
public class UserInfoUseMethodDto implements UserInfoUseMethodGetMemento {

	/** The setting item. */
	// 設定項目
	private Integer settingItem;

	/** The self edit. */
	// 本人編集
	private Integer selfEdit;

	/** The setting use mail. */
	// メール利用設定
	private Integer settingUseMail;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodGetMemento#
	 * getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodGetMemento#
	 * getSettingItem()
	 */
	@Override
	public UserInfoItem getSettingItem() {
		return UserInfoItem.valueOf(this.settingItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodGetMemento#
	 * getSelfEdit()
	 */
	@Override
	public SelfEditUserInfo getSelfEdit() {
		return SelfEditUserInfo.valueOf(this.selfEdit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodGetMemento#
	 * getSettingUseMail()
	 */
	@Override
	public Optional<SettingUseSendMail> getSettingUseMail() {
		return Optional
				.ofNullable(this.settingUseMail == null ? null : SettingUseSendMail.valueOf(this.settingUseMail));
	}

}
