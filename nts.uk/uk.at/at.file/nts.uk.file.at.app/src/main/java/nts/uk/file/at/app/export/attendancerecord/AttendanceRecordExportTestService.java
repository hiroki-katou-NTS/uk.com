package nts.uk.file.at.app.export.attendancerecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportColumnData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportDailyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklyData;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportWeeklySumaryData;

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
		AttendanceRecordReportData data = this.createTestData();
		AttendanceRecordReportDatasource datasource = new AttendanceRecordReportDatasource(data);

		this.generator.generate(context.getGeneratorContext(), datasource);

	}

	/**
	 * Creates the test data.
	 *
	 * @return the attendance record report data
	 */
	private AttendanceRecordReportData createTestData() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		// create test report headers and data
		List<AttendanceRecordReportColumnData> monthlyHeader = new ArrayList<>();
		List<AttendanceRecordReportColumnData> employeeMonthlyData = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			monthlyHeader.add(new AttendanceRecordReportColumnData("monthHU", "monthHD"));
			employeeMonthlyData.add(new AttendanceRecordReportColumnData(i + "_MDataU_" + i, i + "_MDataL_" + i));
		}
		List<AttendanceRecordReportColumnData> dailyHeader = new ArrayList<>();
		List<AttendanceRecordReportColumnData> dailyDatas = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			dailyHeader.add(new AttendanceRecordReportColumnData("dailyHU", "dailyHD"));
			dailyDatas.add(new AttendanceRecordReportColumnData("_DDataU_" + i, "_DDataL_" + i));
		}

		// create test weekly sumary test data
		List<AttendanceRecordReportColumnData> weeklySumData = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			weeklySumData.add(new AttendanceRecordReportColumnData("_WSDataU_" + i, "_WSDataL_" + i));
		}

		// create report header data
		AttendanceRecordReportData data = new AttendanceRecordReportData();
		data.setCompanyName("日通システム　株式会社");
		data.setReportName("出勤簿");
		data.setExportDateTime(format.format(new Date()));
		data.setMonthlyHeader(monthlyHeader);
		data.setDailyHeader(dailyHeader);
		data.setSealColName(Arrays.asList(new String[] { "seal 1", "seal 2", "seal 3", "seal 4" }));

		// Create employee data
		List<AttendanceRecordReportEmployeeData> reportEmployeeDatas = new ArrayList<>();

		// Employee 1
		List<AttendanceRecordReportWeeklyData> employeeWeekDatas;
		List<AttendanceRecordReportDailyData> reportDailyDatas;

		AttendanceRecordReportEmployeeData employeeData = new AttendanceRecordReportEmployeeData();
		employeeData.setInvidual("invidual 1");
		employeeData.setWorkplace("workplace 1");
		employeeData.setEmployment("employment 1");
		employeeData.setTitle("title 1");
		employeeData.setWorkType("workType 1");
		employeeData.setYearMonth("2011/02");

		// set monthly data
		employeeData.setEmployeeMonthlyData(employeeMonthlyData);

		// create employee weekly data
		employeeWeekDatas = new ArrayList<>();

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("1", "日", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("2", "月", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("3", "火", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("4", "水", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("5", "木", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("6", "金", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("7", "土", dailyDatas, false));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("1-7", false, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("8", "日", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("9", "月", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("10", "火", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("11", "水", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("12", "木", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("13", "金", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("14", "土", dailyDatas, false));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("8-14", false, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("15", "日", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("16", "月", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("17", "火", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("18", "水", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("19", "木", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("20", "金", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("21", "土", dailyDatas, true));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("15-21", true, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("22", "日", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("23", "月", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("24", "火", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("25", "水", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("26", "木", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("27", "金", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("28", "土", dailyDatas, true));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("22-28", true, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("29", "日", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("30", "月", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("31", "火", dailyDatas, true));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("29-31", true, weeklySumData)));

		// set weekly data
		employeeData.setWeeklyDatas(employeeWeekDatas);
		
		reportEmployeeDatas.add(employeeData);
		
		/*=================================== EMPLOYEE 2 ====================================*/
		employeeData = new AttendanceRecordReportEmployeeData();
		employeeData.setInvidual("invidual 2");
		employeeData.setWorkplace("workplace 2");
		employeeData.setEmployment("employment 2");
		employeeData.setTitle("title 2");
		employeeData.setWorkType("workType 2");
		employeeData.setYearMonth("2013/09");

		// set monthly data
		employeeData.setEmployeeMonthlyData(employeeMonthlyData);

		// create employee weekly data
		employeeWeekDatas = new ArrayList<>();

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("26", "木", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("27", "金", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("28", "土", dailyDatas, false));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("26-28", false, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("29", "日", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("30", "月", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("1", "火", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("2", "水", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("3", "木", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("4", "金", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("5", "土", dailyDatas, false));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("29-5", false, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("6", "日", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("7", "月", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("8", "火", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("9", "水", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("10", "木", dailyDatas, false));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("11", "金", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("12", "土", dailyDatas, true));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("6-12", true, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("13", "日", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("14", "月", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("15", "火", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("16", "水", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("17", "木", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("18", "金", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("19", "土", dailyDatas, true));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("13-19", true, weeklySumData)));

		reportDailyDatas = new ArrayList<>();
		reportDailyDatas.add(new AttendanceRecordReportDailyData("20", "日", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("21", "月", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("22", "火", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("23", "水", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("24", "木", dailyDatas, true));
		reportDailyDatas.add(new AttendanceRecordReportDailyData("25", "金", dailyDatas, true));
		employeeWeekDatas.add(new AttendanceRecordReportWeeklyData(reportDailyDatas,
				new AttendanceRecordReportWeeklySumaryData("20-25", true, weeklySumData)));

		// set weekly data
		employeeData.setWeeklyDatas(employeeWeekDatas);
		
		reportEmployeeDatas.add(employeeData);
		reportEmployeeDatas.add(reportEmployeeDatas.get(1));

		
		// Set whole report data
		Map<String, List<AttendanceRecordReportEmployeeData>> reportData = new LinkedHashMap<>();
		reportData.put("201805", reportEmployeeDatas);
		reportData.put("201806", reportData.get("201805"));

		// set report data
		data.setReportData(reportData);

		return data;
	}

}
