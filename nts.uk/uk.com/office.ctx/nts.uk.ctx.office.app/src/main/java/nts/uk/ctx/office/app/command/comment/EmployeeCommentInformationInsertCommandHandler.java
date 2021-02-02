package nts.uk.ctx.office.app.command.comment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformation;
import nts.uk.ctx.office.dom.comment.EmployeeCommentInformationRepository;

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
		Optional<EmployeeCommentInformation> employeeCommentInformation = employeeCommentInformationRepository.getBySidAndDate(domain.getSid(), domain.getDate());
		if (employeeCommentInformation.isPresent()) {
			employeeCommentInformationRepository.update(domain);
			return;
		}
		employeeCommentInformationRepository.insert(domain);
	}

}
