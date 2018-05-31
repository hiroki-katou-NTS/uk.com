/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.find.mailnoticeset.employee;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.env.dom.mailnoticeset.MailFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SendMailByFunctionSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class FunctionSettingFinder.
 */
@Stateless
public class FunctionSettingFinder {

	/** The mail function repository. */
	@Inject
	private MailFunctionRepository mailFunctionRepository;

	/** The mail destination function repository. */
	@Inject
	private MailDestinationFunctionRepository mailDestinationFunctionRepository;

	/**
	 * Find by user info item.
	 *
	 * @param userInfoItem
	 *            the user info item
	 * @return the list
	 */
	public List<FunctionSettingDto> findByUserInfoItem(UserInfoItem userInfoItem) {
		
		String companyId = AppContexts.user().companyId();
		List<FunctionSettingDto> listResult = this.mailFunctionRepository.findAll(true).stream()
				.map(item -> new FunctionSettingDto(item.getFunctionId().v(), item.getFunctionName().v(), false))
				.collect(Collectors.toList());	

		if (listResult.isEmpty()) {
			throw new BusinessException("Msg_1183");
		}
		MailDestinationFunction mailDestinationFunction = this.mailDestinationFunctionRepository.findByCidAndSettingItem(companyId, userInfoItem);
		
		// In case the mail destination function is not found
		if (mailDestinationFunction == null) {
			return listResult;
		}
				
		Map<Integer, SendMailByFunctionSetting> mapSendMailByFunctionSetting = mailDestinationFunction.getSendByFunctionSetting().stream()
				.collect(Collectors.toMap(item -> item.getFunctionId().v(), Function.identity()));
		listResult.forEach(dto -> {
			SendMailByFunctionSetting sendSetting = mapSendMailByFunctionSetting.get(dto.getFunctionId());
			if (sendSetting == null) {
				dto.setSendSetting(false);
				return;
			}
			
			if (NotUseAtr.USE == sendSetting.getSendSetting()) {
				dto.setSendSetting(true);
			} else {
				dto.setSendSetting(false);
			}			
		});
		return listResult;
	}
}
