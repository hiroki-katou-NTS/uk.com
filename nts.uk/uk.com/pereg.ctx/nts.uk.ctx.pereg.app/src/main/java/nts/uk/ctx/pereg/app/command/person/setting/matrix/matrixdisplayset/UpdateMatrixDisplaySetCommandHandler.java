/**
 * 
 */
package nts.uk.ctx.pereg.app.command.person.setting.matrix.matrixdisplayset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.setting.matrix.matrixdisplayset.MatrixDisplaySettingRepo;

/**
 * @author hieult
 *
 */
@Stateless
@Transactional

public class UpdateMatrixDisplaySetCommandHandler extends CommandHandler<UpdateMatrixDisplaySetCommand> {

	@Inject
	private MatrixDisplaySettingRepo repo;
	
		@Override
		protected void handle(CommandHandlerContext<UpdateMatrixDisplaySetCommand> context) {
			// TODO Auto-generated method stub
			UpdateMatrixDisplaySetCommand command = context.getCommand();
			repo.update(command.toDomain());
	}

}
