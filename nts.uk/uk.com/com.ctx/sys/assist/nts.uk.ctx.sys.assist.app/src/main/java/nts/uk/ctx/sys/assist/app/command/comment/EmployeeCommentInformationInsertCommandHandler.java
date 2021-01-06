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
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.コメント.App.コメントを新規登録する.コメントを新規登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeeCommentInformationInsertCommandHandler extends CommandHandler<EmployeeCommentInformationCommand> {

	@Inject
	EmployeeCommentInformationRepository employeeCommentInformationRepository;

	@Override
	protected void handle(CommandHandlerContext<EmployeeCommentInformationCommand> context) {
		EmployeeCommentInformationCommand command = context.getCommand();
		EmployeeCommentInformation domain = EmployeeCommentInformation.createFromMemento(command);
		employeeCommentInformationRepository.insert(domain);
	}

}
