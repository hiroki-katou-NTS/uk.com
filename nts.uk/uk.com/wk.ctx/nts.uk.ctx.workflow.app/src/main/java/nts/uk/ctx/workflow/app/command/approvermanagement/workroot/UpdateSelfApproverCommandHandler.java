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
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateSelfApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService.Require;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.App.自分の承認者の修正更新をする.自分の承認者の修正更新をする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateSelfApproverCommandHandler extends CommandHandler<UpdateSelfApproverCommand> {

	@Inject
	private RequireService requireService;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateSelfApproverCommand> context) {
		String cid = AppContexts.user().companyId();
		Require require = requireService.createRequire();
		UpdateSelfApproverCommand command = context.getCommand();

		// 1. 変更登録する(Require, 会社ID, 社員ID, 期間, 承認者設定パラメータ)
		List<AtomTask> atomTasks = UpdateSelfApprovalRootDomainService.register(require, cid, command.getSid(),
				command.getParams().stream().map(ApprovalSettingParamCommand::toDomain).collect(Collectors.toList()),
				command.getPeriod());
		// 2. <call>
		this.transaction.allInOneTransaction(atomTasks);
	}
}
