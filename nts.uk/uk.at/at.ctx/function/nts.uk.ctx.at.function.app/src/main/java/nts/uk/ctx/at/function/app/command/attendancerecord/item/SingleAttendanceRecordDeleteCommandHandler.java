package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleItemAttributes;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author tuannt-nws
 *
 */
@Stateless
public class SingleAttendanceRecordDeleteCommandHandler extends CommandHandler<SingleAttendanceRecordDeleteCommand>{

	/** The single attendance record repository. */
	@Inject
	SingleAttendanceRecordRepository singleAttendanceRecordRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SingleAttendanceRecordDeleteCommand> context) {
		SingleAttendanceRecordDeleteCommand command = context.getCommand();
		
		SingleAttendanceRecord singleAttendanceRecord = new SingleAttendanceRecord(SingleItemAttributes.valueOf(command.getAttribute()), new ItemName(command.getName()), command.getTimeItemId());
		
		this.singleAttendanceRecordRepository.deleteSingleAttendanceRecord(
																	AppContexts.user().companyId(),
																	new ExportSettingCode(Long.valueOf(command.getExportSettingCode())),
																	command.getColumnIndex(),
																	command.getPosition(),
																	command.getExportAtr(),
																	singleAttendanceRecord);
		
	}

}
