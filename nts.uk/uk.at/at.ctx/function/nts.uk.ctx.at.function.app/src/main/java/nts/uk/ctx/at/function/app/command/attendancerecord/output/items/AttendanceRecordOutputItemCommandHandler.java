package nts.uk.ctx.at.function.app.command.attendancerecord.output.items;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordOuputItemsRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttendanceRecordOutputItemCommandHandler extends CommandHandler<AttendanceRecordOutputItemCommand>{
	
	@Inject
	private AttendanceRecordOuputItemsRepository attOuputItemsRepo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordOutputItemCommand> context) {
		AttendanceRecordOutputItemCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		command.setCid(cId);
		
	}

}
