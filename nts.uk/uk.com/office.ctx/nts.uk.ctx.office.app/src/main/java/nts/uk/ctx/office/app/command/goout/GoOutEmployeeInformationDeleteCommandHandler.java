package nts.uk.ctx.office.app.command.goout;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.外出.App.外出を削除する.外出を削除する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GoOutEmployeeInformationDeleteCommandHandler extends CommandHandler<GoOutEmployeeInformationCommand> {

	@Inject
	GoOutEmployeeInformationRepository goOutEmployeeInformationRepository;

	@Override
	protected void handle(CommandHandlerContext<GoOutEmployeeInformationCommand> context) {
		GoOutEmployeeInformationCommand command = context.getCommand();
		GoOutEmployeeInformation domain = GoOutEmployeeInformation.createFromMemento(command);
		goOutEmployeeInformationRepository.delete(domain);
	}

}
