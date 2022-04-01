package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.SetOperationModeDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.自分の承認者の運用設定.App.運用モードを変更する.運用モードを変更する
 * @author NWS-DungDV
 *
 */
@Stateless
public class ChangeOperationModeCommandHandler extends CommandHandler<ChangeOperationModeCommand> {

	@Inject
	private RequireService requireService;
	
	@Override
	protected void handle(CommandHandlerContext<ChangeOperationModeCommand> context) {
		ChangeOperationModeCommand command = context.getCommand();
		val require = requireService.createRequire();

		List<AtomTask> atomTasks = SetOperationModeDomainService.update(require,
				AppContexts.user().companyId(),
				command.getOperationMode(),
				Optional.ofNullable(command.getItemNameInformation()));
		
		transaction.execute(AtomTask.bundle(atomTasks));
	}

}
