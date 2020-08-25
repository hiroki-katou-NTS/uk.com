package nts.uk.ctx.at.function.app.command.attendancerecord.output.items;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.ouput.items.AttendanceRecordOuputItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttendanceRecordOutputItemAddCommandHandler extends CommandHandler<AttendanceRecordOutputItemAddCommand>{
	
	@Inject
	private AttendanceRecordOuputItemsRepository attOuputItemsRepo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordOutputItemAddCommand> context) {
		AttendanceRecordOutputItemAddCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		command.setCid(cId);
		
	}

}
