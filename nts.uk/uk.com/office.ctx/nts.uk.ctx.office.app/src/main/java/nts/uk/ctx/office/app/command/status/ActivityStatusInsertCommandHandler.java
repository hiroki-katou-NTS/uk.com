package nts.uk.ctx.office.app.command.status;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.ctx.office.dom.status.ActivityStatusRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.ステータス.App.在席のステータスを新規登録する.在席のステータスを新規登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ActivityStatusInsertCommandHandler extends CommandHandler<ActivityStatusCommand> {

	@Inject
	ActivityStatusRepository activityStatusRepository;

	@Override
	protected void handle(CommandHandlerContext<ActivityStatusCommand> context) {
		ActivityStatusCommand command = context.getCommand();
		ActivityStatus domain = ActivityStatus.createFromMemento(command);
		activityStatusRepository.insert(domain);
	}

}
