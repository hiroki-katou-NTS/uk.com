/**
 * 
 */
package nts.uk.ctx.pereg.app.command.person.setting.matrix.personinfomatrixitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem.PersonInfoMatrixItemRepo;

/**
 * @author hieult
 *
 */
@Transactional
@Stateless
public class CreatePersonInfoMatrixItemCommandHandler extends CommandHandler<CreatePersonInfoMatrixItemCommand> {

	
	@Inject
	private PersonInfoMatrixItemRepo repo;
	
		@Override
		protected void handle(CommandHandlerContext<CreatePersonInfoMatrixItemCommand> context) {
			CreatePersonInfoMatrixItemCommand command = context.getCommand();
			repo.insert(command.toDomain());
			
		}

}
