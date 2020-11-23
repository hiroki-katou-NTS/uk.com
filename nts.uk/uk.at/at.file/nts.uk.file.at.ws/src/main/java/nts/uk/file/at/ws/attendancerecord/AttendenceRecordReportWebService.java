package nts.uk.file.at.ws.attendancerecord;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordExportService;

import nts.uk.file.at.app.export.attendancerecord.AttendanceRecordRequest;

@Path("at/function/attendancerecord/report")
@Produces("application/json")
public class AttendenceRecordReportWebService extends WebService {

	@Inject
	private AttendanceRecordExportService exportService;

	@POST
	@Path("export")
	public ExportServiceResult generate(AttendanceRecordRequest query) {

		return this.exportService.start(query);
	}
}
