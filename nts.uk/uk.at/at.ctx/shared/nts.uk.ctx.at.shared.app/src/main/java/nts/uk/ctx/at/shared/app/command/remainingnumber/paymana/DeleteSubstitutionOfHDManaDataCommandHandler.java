package nts.uk.ctx.at.shared.app.command.remainingnumber.paymana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;

@Stateless
public class DeleteSubstitutionOfHDManaDataCommandHandler
		extends CommandHandler<DeleteSubstitutionOfHDManaDataCommand> {

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;


	@Override
	protected void handle(CommandHandlerContext<DeleteSubstitutionOfHDManaDataCommand> context) {
		DeleteSubstitutionOfHDManaDataCommand command = context.getCommand();
		substitutionOfHDManaDataRepository.delete(command.getSubOfHDID());

	}

}
