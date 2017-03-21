package nts.uk.ctx.pr.core.app.command.itemmaster.itemattend;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteItemAttendCommandHandler extends CommandHandler<DeleteItemAttendCommand> {

	@Inject
	ItemAttendRespository itemAttendRespository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteItemAttendCommand> context) {
		val companyCode = AppContexts.user().companyCode();
		
		this.itemAttendRespository.delete(companyCode,context.getCommand().getItemCd());

	}

}
