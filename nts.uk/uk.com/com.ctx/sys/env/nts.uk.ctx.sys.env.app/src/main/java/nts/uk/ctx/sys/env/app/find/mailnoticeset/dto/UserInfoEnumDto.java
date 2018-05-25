/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

@Getter
@Setter
public class UserInfoEnumDto {

	/** The setting use send mail. */
	//メール送信利用設定
	private List<EnumConstant> settingUseSendMail;
	
	/** The self edit setting. */
	//ユーザー情報本人編集
	private List<EnumConstant> selfEditSetting;

	/**
	 * Inits the.
	 *
	 * @param i18n the i 18 n
	 * @return the user info enum dto
	 */
	public static UserInfoEnumDto init(I18NResourcesForUK i18n) {
		UserInfoEnumDto dto = new UserInfoEnumDto();
		dto.setSettingUseSendMail(EnumAdaptor.convertToValueNameList(SettingUseSendMail.class, i18n));
		dto.setSelfEditSetting(EnumAdaptor.convertToValueNameList(SelfEditUserInfo.class, i18n));
		return dto;
	}

}
