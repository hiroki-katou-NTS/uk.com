/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionSetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;

/**
 * The Class MailDestinationFunctionDto.
 */
@Data
public class MailDestinationFunctionDto implements MailDestinationFunctionSetMemento {

	/** The setting item. */
	// 設定項目
	public Integer settingItem;

	/** The send by function setting. */
	// 機能別送信設定
	public List<SendMailByFunctionSettingDto> sendByFunctionSetting;

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
		this.sendByFunctionSetting = sendByFunctionSetting.stream().map(item -> {
			SendMailByFunctionSettingDto dto = new SendMailByFunctionSettingDto();
			item.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

}
