package nts.uk.ctx.at.function.app.command.attendancerecord.export;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExport;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless

public class AttendanceRecordExportSaveCommandHandle extends CommandHandler<AttendanceRecordExportSaveCommand> {

	@Inject
	AttendanceRecordExportRepository attendanceRecExpRepo;

	@Override
	protected void handle(CommandHandlerContext<AttendanceRecordExportSaveCommand> context) {

//		// Get command
//		AttendanceRecordExportSaveCommand command = context.getCommand();
//
//		// Convert to domain
//		AttendanceRecordExport domain = new AttendanceRecordExport();
//
//		// Set data
//		domain.setColumnIndex(command.getColumnIndex());
//		domain.setExportAtr(ExportAtr.valueOf(command.getExportAtr()));
//		domain.setUseAtr(command.getUserAtr());
//
//		String companyId = AppContexts.user().companyId();
//		// Find AttendanceRecordExport
//		
//		List<AttendanceRecordExport> resultList = attendanceRecExpRepo.getAttendanceRecordExportByIndex(companyId, command.getExportCode(), command.getColumnIndex());
//		
//		// Update Entity
//		
//		resultList.forEach(item -> {
//			
//			
//		});
		
		
	}

}
