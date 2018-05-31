package nts.uk.ctx.at.function.app.command.attendancerecord.item;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateItemAttributes;
import nts.uk.ctx.at.function.dom.attendancerecord.item.ItemName;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author locph
 */
@Stateless
public class CalculateAttendanceRecordAddCommandHandler extends CommandHandler<CalculateAttendanceRecordAddCommand> {

	/** The calculate attendance record repository. */
	@Inject
	CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepository;

	/**
	 * Handle add CalculateAttendanceRecord Item
	 * @param context
	 */
	@Override
	protected void handle(CommandHandlerContext<CalculateAttendanceRecordAddCommand> context) {
		CalculateAttendanceRecordAddCommand command = context.getCommand();
		// get list added, suctracted
		List<Integer> addedItems = command.getTimeItems().stream().filter(e -> e.getFormulaType() == 1)
				.map(TimeItemDto::getTimeItemId).collect(Collectors.toList());
		List<Integer> subtractedItems = command.getTimeItems().stream().filter(e -> e.getFormulaType() == 2)
				.map(TimeItemDto::getTimeItemId).collect(Collectors.toList());
		// convert to domain
		CalculateAttendanceRecord calculateAttendanceRecord = new CalculateAttendanceRecord(
				CalculateItemAttributes.valueOf(command.getAttribute()), new ItemName(command.getName()), addedItems,
				subtractedItems);
		// update
		this.calculateAttendanceRecordRepository.updateCalculateAttendanceRecord(AppContexts.user().companyId(),
				new ExportSettingCode((long) command.getExportSettingCode()), command.getColumnIndex(),
				command.getPosition(), command.getExportAtr(),command.isUseAtr(), calculateAttendanceRecord);
	}

}
