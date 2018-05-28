/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.company.dto.MailDestinationFunctionDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.MailFunctionDto;
import nts.uk.ctx.sys.env.app.find.mailnoticeset.dto.SettingDataDto;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailFunctionAndDestinationFinder.
 */
@Stateless
public class MailFunctionAndDestinationFinder {

	/** The mail function repository. */
	@Inject
	private MailFunctionRepository mailFunctionRepository;

	/** The mail destination function repository. */
	@Inject
	private MailDestinationFunctionRepository mailDestinationFunctionRepository;

	/**
	 * Gets the data.
	 *
	 * @param userInfoItem
	 *            the user info item
	 * @return the data
	 */
	public SettingDataDto getData(UserInfoItem userInfoItem) {
		String companyId = this.getCid();
		// ・メール送信設定可否区分＝True
		List<MailFunction> lstMailFunc = this.mailFunctionRepository.findAll(true);
		if (lstMailFunc.isEmpty()) {
			throw new BusinessException("Msg_1144");
		} else {

			List<MailFunctionDto> lstMailFunctionDto = lstMailFunc.stream().map(item -> {
				MailFunctionDto dto = new MailFunctionDto();
				item.saveToMemento(dto);
				return dto;
			}).collect(Collectors.toList());

			// ドメインモデル「メール送信先機能」を取得する
			MailDestinationFunction mailDes = this.mailDestinationFunctionRepository.findByCidAndSettingItem(companyId,
					userInfoItem);
			MailDestinationFunctionDto dto = new MailDestinationFunctionDto();
			if (mailDes == null) {
				dto = null;
			} else {
				mailDes.saveToMemento(dto);
			}
			return new SettingDataDto(lstMailFunctionDto, dto);
		}
	}

	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	private String getCid() {
		return AppContexts.user().companyId();
	}
}
