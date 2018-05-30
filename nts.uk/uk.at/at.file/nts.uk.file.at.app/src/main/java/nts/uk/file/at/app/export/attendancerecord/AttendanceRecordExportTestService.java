package nts.uk.file.at.app.export.attendancerecord;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportColumnData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;

/**
 * The Class AttendanceRecordExportTestService.
 */
@Stateless
public class AttendanceRecordExportTestService extends ExportService<AttendanceRecordRequest> {

	/** The generator. */
	@Inject
	private AttendanceRecordReportGenerator generator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.
	 * export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {
		AttendanceRecordReportData data = new AttendanceRecordReportData();
		
		List<AttendanceRecordReportColumnData> monthlyHeader = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			monthlyHeader.add(new AttendanceRecordReportColumnData("monthHU", "monthHD"));
		}
		List<AttendanceRecordReportColumnData> dailyHeader = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			dailyHeader.add(new AttendanceRecordReportColumnData("dailyHU", "dailyHD"));
		}
		
		data.setMonthlyHeader(monthlyHeader);
		data.setDailyHeader(dailyHeader);
		AttendanceRecordReportDatasource datasource = new AttendanceRecordReportDatasource(data);

		this.generator.generate(context.getGeneratorContext(), datasource);

	}

}
