/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionGetMemento;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
<<<<<<< HEAD
=======
<<<<<<< HEAD
import nts.uk.shr.com.enumcommon.NotUseAtr;
=======
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030

/**
 * The Class MailDestinationFunctionDto.
 */
@Value
public class MailDestinationFunctionDto implements MailDestinationFunctionGetMemento {

	/** The setting item. */
	public Integer settingItem;

	/** The send by function setting. */
	public List<SendMailByFunctionSettingDto> sendByFunctionSetting;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionGetMemento#getSendByFunctionSetting()
	 */
	@Override
	public List<SendMailByFunctionSetting> getSendByFunctionSetting() {
		return this.sendByFunctionSetting.stream()
				.map(SendMailByFunctionSetting::new)
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.env.dom.mailnoticeset.company.
	 * MailDestinationFunctionGetMemento#getSettingItem()
	 */
	@Override
	public UserInfoItem getSettingItem() {
		return UserInfoItem.valueOf(this.settingItem);
	}

<<<<<<< HEAD
=======
<<<<<<< HEAD
	@Override
	public List<SendMailByFunctionSetting> getSendByFunctionSetting(NotUseAtr use) {
		return this.sendByFunctionSetting.stream()
				.filter(item->item.getSendSetting().equals(use))
				.map(SendMailByFunctionSetting::new)
				.collect(Collectors.toList());
	}

=======
>>>>>>> delivery/release_user
>>>>>>> pj/at/dev/Team_D/KDL030
}
