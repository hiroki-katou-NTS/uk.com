package nts.uk.ctx.sys.assist.app.command.comment;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.comment.EmployeeCommentInformation;
import nts.uk.ctx.sys.assist.dom.comment.EmployeeCommentInformationRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.コメント.App.コメントを変更登録する.コメントを変更登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeeCommentInformationUpdateCommandHandler extends CommandHandler<EmployeeCommentInformationCommand> {

	@Inject
	EmployeeCommentInformationRepository employeeCommentInformationRepository;

	@Override
	protected void handle(CommandHandlerContext<EmployeeCommentInformationCommand> context) {
		EmployeeCommentInformationCommand command = context.getCommand();
		EmployeeCommentInformation domain = EmployeeCommentInformation.createFromMemento(command);
		employeeCommentInformationRepository.update(domain);
	}

}
