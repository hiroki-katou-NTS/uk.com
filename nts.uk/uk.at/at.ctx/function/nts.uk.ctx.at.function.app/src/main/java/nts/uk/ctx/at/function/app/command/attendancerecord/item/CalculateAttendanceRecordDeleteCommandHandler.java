package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author tuannt-nws
 *
 */
@Stateless
public class CalculateAttendanceRecordDeleteCommandHandler
		extends CommandHandler<CalculateAttendanceRecordDeleteCommand> {

	/** The calculate attendance record repository. */
	@Inject
	CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepository;

	@Override
	protected void handle(CommandHandlerContext<CalculateAttendanceRecordDeleteCommand> context) {
		CalculateAttendanceRecordDeleteCommand command = context.getCommand();
		// get list added, suctracted
		List<Integer> addedItems = command.getTimeItems().stream().filter(e -> e.getFormulaType() == 1)
						.map(e -> e.getTimeItemId()).collect(Collectors.toList());
		List<Integer> subtractedItems = command.getTimeItems().stream().filter(e -> e.getFormulaType() == 2)
						.map(e -> e.getTimeItemId()).collect(Collectors.toList());
		//convert to domain
		CalculateAttendanceRecord calculateAttendanceRecord = new CalculateAttendanceRecord(CalculateItemAttributes.valueOf(command.getAttribute()), new ItemName(command.getName()), addedItems, subtractedItems);
		//delete
		this.calculateAttendanceRecordRepository.deleteCalculateAttendanceRecord(
																			AppContexts.user().companyId(),
																			new ExportSettingCode(Long.valueOf(command.getExportSettingCode())),
																			command.getColumnIndex(),
																			command.getPosition(),
																			command.getExportAtr(),
																			calculateAttendanceRecord);
	
	}

}
