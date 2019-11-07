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
public class UpdatePersonInfoMatrixItemCommandHandler extends CommandHandler<UpdatePersonInfoMatrixItemCommand> {

	@Inject
		private PersonInfoMatrixItemRepo repo;
	
		@Override
		protected void handle(CommandHandlerContext<UpdatePersonInfoMatrixItemCommand> context) {
			UpdatePersonInfoMatrixItemCommand command = context.getCommand();
			repo.update(command.toDomain());
			
		}

}
