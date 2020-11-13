/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.securitypolicy.lockoutdata;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.acountlock.locked.LockOutDataRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.LoginLogRepository;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.OperationSection;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.loginlog.SuccessFailureClassification;

/**
 * The Class LockOutDataDeleteCommandHandler.
 */
@Stateless
public class LockOutDataDeleteCommandHandler  extends CommandHandler<LockOutDataDeleteCommand>  {
	
	/** The lock out data repository. */
	@Inject
	private LockOutDataRepository lockOutDataRepository;
	@Inject
	private LoginLogRepository loginLogRepo;

	/*
	 * CLI001_ロックアウト一覧.解除処理
	 */
	@Override
	protected void handle(CommandHandlerContext<LockOutDataDeleteCommand> context) {
		// Get new domain
		LockOutDataDeleteCommand command = context.getCommand();
		List<String> lstUserId = command.getLstUserId();
		//アルゴリズム「ロックアウト削除」を実行する ※1件目からListの最後まで、実行する。  
		this.lockOutDataRepository.remove(lstUserId);
		//hoatt 2019.05.22 #107778
		//EA修正履歴No.3464
		//アルゴリズム「ログインログ削除」を実行する ※1件目からListの最後まで、実行する。
		loginLogRepo.deleteLstLoginLog(lstUserId, SuccessFailureClassification.Failure.value, OperationSection.Login.value);
	}

}
