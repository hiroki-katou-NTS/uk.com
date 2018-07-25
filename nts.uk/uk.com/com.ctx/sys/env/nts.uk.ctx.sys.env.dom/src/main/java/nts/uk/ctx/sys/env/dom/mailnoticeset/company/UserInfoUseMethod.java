/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * Gets the setting use mail.
 *
 * @return the setting use mail
 */
@Getter
//ユーザー情報の使用方法
public class UserInfoUseMethod extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The setting item. */
	// 設定項目
	private UserInfoItem settingItem;

	/** The self edit. */
	// 本人編集
	private SelfEditUserInfo selfEdit;

	/** The setting use mail. */
	// メール利用設定
	private Optional<SettingUseSendMail> settingUseMail;

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(UserInfoUseMethodSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setSelfEdit(this.selfEdit);
		memento.setSettingItem(this.settingItem);
		memento.setSettingUseMail(this.settingUseMail);
	}

	/**
	 * Instantiates a new user info use method.
	 *
	 * @param memento the memento
	 */
	public UserInfoUseMethod(UserInfoUseMethodGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.selfEdit = memento.getSelfEdit();
		this.settingItem = memento.getSettingItem();
		this.settingUseMail = memento.getSettingUseMail();
	}
	
	public void corretSelfEdit(SelfEditUserInfo oldSelfEdit) {
		this.selfEdit = oldSelfEdit;
	}
}
