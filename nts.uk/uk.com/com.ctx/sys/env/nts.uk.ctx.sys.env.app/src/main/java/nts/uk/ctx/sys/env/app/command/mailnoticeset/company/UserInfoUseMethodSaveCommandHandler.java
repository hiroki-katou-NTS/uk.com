/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SelfEditUserInfo;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingUseSendMail;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethod;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.UserInfoUseMethodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UserInfoUseMethodSaveCommandHandler.
 */
@Stateless
public class UserInfoUseMethodSaveCommandHandler extends CommandHandler<UserInfoUseMethodSaveCommand> {

	/** The repo. */
	@Inject
	private UserInfoUseMethodRepository repo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UserInfoUseMethodSaveCommand> context) {
		UserInfoUseMethodSaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<UserInfoUseMethod> lstUserInfoUseMethod = repo.findByCompanyId(companyId);
		if (lstUserInfoUseMethod.isEmpty()) {
			// save
			this.repo.create(command.toDomain());
		} else {
			List<UserInfoUseMethod> lstUserInfoUseMethodAfterCorrect = this.correctData(lstUserInfoUseMethod,command.toDomain());
			this.repo.update(lstUserInfoUseMethodAfterCorrect);
		}
	}

	/**
	 * Correct data.
	 *
	 * @param oldList the old list
	 * @param newList the new list
	 * @return the list
	 */
	private List<UserInfoUseMethod> correctData(List<UserInfoUseMethod> oldList, List<UserInfoUseMethod> newList) {
		return newList.stream().map(newDomain -> {
			Optional<UserInfoUseMethod> optionalDom = oldList.stream()
					.filter(i -> i.getSettingItem().equals(newDomain.getSettingItem())).findFirst();
			if (newDomain.getSettingUseMail().isPresent() && optionalDom.isPresent()) {
				if (newDomain.getSettingUseMail().get().equals(SettingUseSendMail.NOT_USE)) {
					newDomain.corretSelfEdit(optionalDom.get().getSelfEdit());
				}
			}
			if (!optionalDom.isPresent() && newDomain.getSettingUseMail().get().equals(SettingUseSendMail.NOT_USE)) {
				newDomain.corretSelfEdit(SelfEditUserInfo.CAN_NOT_EDIT);
			}
			return newDomain;
		}).collect(Collectors.toList());
	}

}
