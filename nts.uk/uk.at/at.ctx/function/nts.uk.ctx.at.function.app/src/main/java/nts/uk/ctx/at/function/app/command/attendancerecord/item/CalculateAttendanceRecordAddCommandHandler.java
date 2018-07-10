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
 * The type CalculateAttendanceRecordAddCommandHandler.
 *
 * @author locph
 *
 */
@Stateless
public class CalculateAttendanceRecordAddCommandHandler extends CommandHandler<CalculateAttendanceRecordAddCommand> {

	/**
	 * The CalculateAttendanceRecordRepositoty.
	 */
	@Inject
	CalculateAttendanceRecordRepositoty calculateAttendanceRecordRepository;

	/** The add formular type. */
	private static final int ADD_FORMULAR_TYPE = 1;
	
	/** The sub formular type. */
	private static final int SUB_FORMULAR_TYPE = 2;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CalculateAttendanceRecordAddCommand> context) {
		CalculateAttendanceRecordAddCommand command = context.getCommand();
		// get list added, suctracted
		List<Integer> addedItems = command.getTimeItems().stream().filter(e -> e.getFormulaType() == ADD_FORMULAR_TYPE)
				.map(TimeItemDto::getTimeItemId).collect(Collectors.toList());
		List<Integer> subtractedItems = command.getTimeItems().stream().filter(e -> e.getFormulaType() == SUB_FORMULAR_TYPE)
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
