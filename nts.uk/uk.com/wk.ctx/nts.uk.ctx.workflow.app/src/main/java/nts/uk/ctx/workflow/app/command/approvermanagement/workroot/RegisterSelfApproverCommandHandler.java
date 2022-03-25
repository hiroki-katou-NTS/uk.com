package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateSelfApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService.Require;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.App.自分の承認者を新規登録する.自分の承認者を新規登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterSelfApproverCommandHandler extends CommandHandler<RegisterSelfApproverCommand> {

	@Inject
	private RequireService requireService;

	@Override
	protected void handle(CommandHandlerContext<RegisterSelfApproverCommand> context) {
		String cid = AppContexts.user().companyId();
		RegisterSelfApproverCommand command = context.getCommand();

		// 1. 新規登録する(Require, 会社ID, 社員ID, 年月日, 承認者設定パラメータ)
		Require require = this.requireService.createRequire();
		List<AtomTask> atomTasks = CreateSelfApprovalRootDomainService.register(require, cid, command.getSid(),
				command.getBaseDate(),
				command.getParams().stream().map(ApprovalSettingParamCommand::toDomain).collect(Collectors.toList()));
		// 2. <call>
		this.transaction.allInOneTransaction(atomTasks);
	}
}
