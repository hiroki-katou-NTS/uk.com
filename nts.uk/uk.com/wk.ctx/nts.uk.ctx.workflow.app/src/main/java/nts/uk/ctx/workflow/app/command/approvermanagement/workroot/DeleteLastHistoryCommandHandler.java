package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.App.最終履歴の削除をする.最終履歴の削除をする
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteLastHistoryCommandHandler extends CommandHandler<DeleteLastHistoryCommand> {

	@Inject
	private UpdateWorkAppApprovalRByHistCommandHandler updateWorkAppApprovalRByHistCommandHandler;

	@Override
	protected void handle(CommandHandlerContext<DeleteLastHistoryCommand> context) {
		DeleteLastHistoryCommand command = context.getCommand();
		UpdateWorkAppApprovalRByHistCommand delCommand = new UpdateWorkAppApprovalRByHistCommand();
		delCommand.setCheck(2);
		delCommand.setEmployeeId(command.getSid());
		delCommand.setStartDate(command.getStartDate().toString("yyyy/mm/dd"));
		delCommand.setEndDate("9999/12/31");
		delCommand.setSysAtr(0);
		delCommand.setLstUpdate(command.getApprovalIds().stream().map(data -> new UpdateHistoryDto(data, null, null, 0))
				.collect(Collectors.toList()));
		delCommand.setEditOrDelete(0);
		// 03.履歴の削除を実行する(まとめて設定モード)
		this.updateWorkAppApprovalRByHistCommandHandler.handle(delCommand);
	}
}
