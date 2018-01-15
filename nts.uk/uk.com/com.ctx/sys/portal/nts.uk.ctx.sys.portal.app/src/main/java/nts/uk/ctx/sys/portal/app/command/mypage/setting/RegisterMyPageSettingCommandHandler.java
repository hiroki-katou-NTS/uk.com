/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.mypage.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSetting;
import nts.uk.ctx.sys.portal.dom.mypage.setting.MyPageSettingRepository;

/**
 * The Class RegisterMyPageSettingCommandHandler.
 */
@Stateless
public class RegisterMyPageSettingCommandHandler extends CommandHandler<RegisterMyPageSettingCommand> {

	/** The my page setting repository. */
	@Inject
	private MyPageSettingRepository myPageSettingRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterMyPageSettingCommand> context) {
		RegisterMyPageSettingCommand command = context.getCommand();
		// to Domain
		MyPageSetting mps = command.toDomain();
		// add
//		myPageSettingRepository.add(mps);
	}

}
