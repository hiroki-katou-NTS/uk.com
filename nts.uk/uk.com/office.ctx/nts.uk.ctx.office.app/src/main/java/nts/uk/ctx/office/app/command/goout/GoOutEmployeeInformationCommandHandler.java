package nts.uk.ctx.office.app.command.goout;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.外出.App.外出を新規登録する.外出を新規登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GoOutEmployeeInformationCommandHandler extends CommandHandler<GoOutEmployeeInformationCommand> {

	@Inject
	GoOutEmployeeInformationRepository goOutEmployeeInformationRepository;

	@Override
	protected void handle(CommandHandlerContext<GoOutEmployeeInformationCommand> context) {
		GoOutEmployeeInformationCommand command = context.getCommand();
		GoOutEmployeeInformation domain = GoOutEmployeeInformation.createFromMemento(command);
		Optional<GoOutEmployeeInformation> checkDomain = goOutEmployeeInformationRepository.getBySidAndDate(command.getSid(), command.getGoOutDate());
		if(checkDomain.isPresent()) {
			goOutEmployeeInformationRepository.update(domain);
		} else {
			goOutEmployeeInformationRepository.insert(domain);
		}
	}
}
