package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CopyPersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService.Require;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.App.複数社員に承認者を複写する.複数社員に承認者を複写する
 */
@Stateless
@TransactionAttribute
public class CopyApproversCommandHandler extends CommandHandler<CopyApproversCommand> {

	@Inject
	private Require require;

	@Override
	protected void handle(CommandHandlerContext<CopyApproversCommand> context) {
		String cid = AppContexts.user().companyId();
		CopyApproversCommand command = context.getCommand();
		// 1. 複写する(Require, 社員ID, 社員ID, 年月日)
		List<AtomTask> atomTasks = command.getTargetSids().stream()
				.map(targetSid -> CopyPersonalApprovalRootDomainService.copy(require, cid, command.getSourceSid(), targetSid, command.getBaseDate()))
				.collect(Collectors.toList());
		// 2. persist
		this.transaction.allInOneTransaction(atomTasks);
	}
}
