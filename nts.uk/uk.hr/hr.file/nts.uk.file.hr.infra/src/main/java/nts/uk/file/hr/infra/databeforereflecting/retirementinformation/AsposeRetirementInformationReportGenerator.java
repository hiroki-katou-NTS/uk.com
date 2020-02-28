package nts.uk.file.hr.infra.databeforereflecting.retirementinformation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewRecordInfo;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation.ResignmentDivision;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation.Status;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.EvaluationItem;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
import nts.uk.file.hr.app.databeforereflecting.retirementinformation.RetirementInformationGenerator;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.PlannedRetirementDto;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetiDateDto;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.RetirementCourseDto;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredEmployeesQuery;
import nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find.SearchRetiredResultDto;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeRetirementInformationReportGenerator extends AsposeCellsReportGenerator
		implements RetirementInformationGenerator {

	private static final Map<String, List<Integer>> COL_MAP = createMap();

	private static Map<String, List<Integer>> createMap() {
		Map<String, List<Integer>> result = new HashMap<String, List<Integer>>();
		result.put("flag", Arrays.asList(0));
		result.put("extendEmploymentFlg", Arrays.asList(1));
		result.put("registrationStatus", Arrays.asList(2));
		result.put("desiredWorkingCourseId", Arrays.asList(3, 4));
		result.put("employeeCode", Arrays.asList(5));
		result.put("employeeName", Arrays.asList(6));
		result.put("businessnameKana", Arrays.asList(7));
		result.put("birthday", Arrays.asList(8));
		result.put("ageDisplay", Arrays.asList(9));
		result.put("departmentName", Arrays.asList(10, 11));
		result.put("employmentName", Arrays.asList(12, 13));
		result.put("dateJoinComp", Arrays.asList(14));
		result.put("retirementDate", Arrays.asList(15));
		result.put("releaseDate", Arrays.asList(16));
		result.put("inputDate", Arrays.asList(17));
		result.put("hrEvaluation1", Arrays.asList(18));
		result.put("hrEvaluation2", Arrays.asList(19));
		result.put("hrEvaluation3", Arrays.asList(20));
		result.put("healthStatus1", Arrays.asList(21));
		result.put("healthStatus2", Arrays.asList(22));
		result.put("healthStatus3", Arrays.asList(23));
		result.put("stressStatus1", Arrays.asList(24));
		result.put("stressStatus2", Arrays.asList(25));
		result.put("stressStatus3", Arrays.asList(26));
		result.put("interviewRecordTxt", Arrays.asList(27, 28));

		return Collections.unmodifiableMap(result);
	}

	private static final String TEMPLATE_FILE = "report/定年退職者一覧_Template.xlsx";

	private static final String REPORT_FILE_NAME = "定年退職者一覧_";

	private static final String REPORT_FILE_EXTENSION = ".xlsx";

	private static final String FORMAT_DATE = "yyyy/MM/dd";

	private static final String TITLE = "定年退職者一覧";

	private static final int FIRST_ROW_FILL = 7;

	private static final int MAX_LINE = 26;

	private static final int RETENTION = 0;
	private static final int RETIREMENT = 1;
	private static final int STATUS = 2;
	private static final int DESIRED_CODE = 3;
	private static final int DESIRED_NAME = 4;
	private static final int EMPLOYEE_CD = 5;
	private static final int EMPLOYEE_NAME = 6;
	private static final int EMPLOYEE_NAME_KANA = 7;
	private static final int BIRTH_DAY = 8;
	private static final int AGE = 9;
	private static final int DEPARTMENT_CODE = 10;
	private static final int DEPARTMENT_NAME = 11;
	private static final int EMPLOYMENT_CODE = 12;
	private static final int EMPLOYMENT_NAME = 13;
	private static final int JOIN_DATE = 14;
	private static final int RETI_DATE = 15;
	private static final int RELEASE_DATE = 16;
	private static final int INPUT_DATE = 17;
	private static final int PER_EVAL_1 = 18;
	private static final int PER_EVAL_2 = 19;
	private static final int PER_EVAL_3 = 20;
	private static final int HEATH_STATUS_1 = 21;
	private static final int HEATH_STATUS_2 = 22;
	private static final int HEATH_STATUS_3 = 23;
	private static final int STRESS_CHECK_1 = 24;
	private static final int STRESS_CHECK_2 = 25;
	private static final int STRESS_CHECK_3 = 26;
	private static final int INTERVIEW_DATE = 27;
	private static final int INTERVIEWER = 28;

	@Override
	public void generate(FileGeneratorContext generatorContext, SearchRetiredResultDto dataSource,
			SearchRetiredEmployeesQuery query, String companyName, List<ReferEvaluationItem> referEvaluaItems,
			RetiDateDto retiDto) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			// set up page prepare print
			this.writeFileExcel(worksheets, dataSource, query, companyName, referEvaluaItems, retiDto);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(
					this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME + REPORT_FILE_EXTENSION)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void writeFileExcel(WorksheetCollection wsc, SearchRetiredResultDto dto, SearchRetiredEmployeesQuery query,
			String companyName, List<ReferEvaluationItem> referEvaluaItems, RetiDateDto retiDto) {
		try {

			List<PlannedRetirementDto> exportData = dto.getRetiredEmployees();
			List<InterviewRecordInfo> interviewRecords = dto.getInterView().listInterviewRecordInfo;
			int rowIndex = FIRST_ROW_FILL;
			Worksheet ws = wsc.get(0);
			int lineCopy = 3;
			this.settingTableHeader(ws);
			int page = 1;
			for (int i = 0; i < exportData.size(); i++) {

				PlannedRetirementDto entity = exportData.get(i);
				Optional<InterviewRecordInfo> interViewOpt = interviewRecords.stream()
						.filter(x -> x.getEmployeeID().equals(entity.getSId())).findFirst();
				if (i % 2 == 0) {
					ws.getCells().copyRows(ws.getCells(), rowIndex, rowIndex + lineCopy - 1, lineCopy);
				}
				if (i == exportData.size() - 1) {
					ws.getCells().deleteRows(rowIndex, exportData.size() % 2 == 0 ? 3 : 4);
				}
				// content
				ws.getCells().get(rowIndex, RETENTION).putValue(entity.getPendingFlag() == 0 ? "" : "保留");
				ws.getCells().get(rowIndex, RETIREMENT)
						.putValue(EnumAdaptor.valueOf(entity.getExtendEmploymentFlg(), ResignmentDivision.class).name);
				ws.getCells().get(rowIndex, STATUS).putValue(
						entity.getStatus() != null ? EnumAdaptor.valueOf(entity.getStatus(), Status.class).name : "");
				ws.getCells().get(rowIndex, DESIRED_CODE).putValue(entity.getDesiredWorkingCourseCd());
				ws.getCells().get(rowIndex, DESIRED_NAME).putValue(entity.getDesiredWorkingCourseName());
				ws.getCells().get(rowIndex, EMPLOYEE_CD).putValue(entity.getScd());
				ws.getCells().get(rowIndex, EMPLOYEE_NAME).putValue(entity.getBusinessName());
				ws.getCells().get(rowIndex, EMPLOYEE_NAME_KANA).putValue(entity.getBusinessnameKana());
				ws.getCells().get(rowIndex, BIRTH_DAY).putValue(convertToString(entity.getBirthday()));
				ws.getCells().get(rowIndex, AGE).putValue(entity.getRetirementAge() + "歳");
				ws.getCells().get(rowIndex, DEPARTMENT_CODE).putValue(entity.getDepartmentCode());
				ws.getCells().get(rowIndex, DEPARTMENT_NAME).putValue(entity.getDepartmentName());
				ws.getCells().get(rowIndex, EMPLOYMENT_CODE).putValue(entity.getEmploymentCode());
				ws.getCells().get(rowIndex, EMPLOYMENT_NAME).putValue(entity.getEmploymentName());
				ws.getCells().get(rowIndex, JOIN_DATE).putValue(convertToString(entity.getDateJoinComp()));
				ws.getCells().get(rowIndex, RETI_DATE).putValue(convertToString(entity.getRetirementDate()));
				ws.getCells().get(rowIndex, RELEASE_DATE).putValue(entity.getReleaseDate());
				ws.getCells().get(rowIndex, INPUT_DATE).putValue(convertToString(entity.getInputDate()));
				ws.getCells().get(rowIndex, PER_EVAL_1).putValue(entity.getHrEvaluation1());
				ws.getCells().get(rowIndex, PER_EVAL_2).putValue(entity.getHrEvaluation2());
				ws.getCells().get(rowIndex, PER_EVAL_3).putValue(entity.getHrEvaluation3());
				ws.getCells().get(rowIndex, HEATH_STATUS_1).putValue(entity.getHealthStatus1());
				ws.getCells().get(rowIndex, HEATH_STATUS_2).putValue(entity.getHealthStatus2());
				ws.getCells().get(rowIndex, HEATH_STATUS_3).putValue(entity.getHealthStatus3());
				ws.getCells().get(rowIndex, STRESS_CHECK_1).putValue(entity.getStressStatus1());
				ws.getCells().get(rowIndex, STRESS_CHECK_2).putValue(entity.getStressStatus2());
				ws.getCells().get(rowIndex, STRESS_CHECK_3).putValue(entity.getStressStatus3());
				ws.getCells().get(rowIndex, INTERVIEW_DATE).putValue(
						interViewOpt.isPresent() ? convertToString(interViewOpt.get().getInterviewDate()) : "");
				ws.getCells().get(rowIndex, INTERVIEWER)
						.putValue(interViewOpt.isPresent() ? interViewOpt.get().getBusinessName() : "");
				setBorder(wsc.get(0), rowIndex);

				// line break
				if (page == 1) {
					if (rowIndex % (MAX_LINE + FIRST_ROW_FILL) == 0) {
						HorizontalPageBreakCollection hPageBreaks = ws.getHorizontalPageBreaks();
						hPageBreaks.add(rowIndex);
						page++;
					}
				} else {
					if ((rowIndex - (MAX_LINE + FIRST_ROW_FILL)) % MAX_LINE == 0) {
						HorizontalPageBreakCollection hPageBreaks = ws.getHorizontalPageBreaks();
						hPageBreaks.add(rowIndex);
						page++;
					}
				}

				rowIndex++;
			}
			List<Integer> delCols = new ArrayList<Integer>();
			
			// remove evalua by setting
			delCols.addAll(getRetiDelCols(retiDto.getRetirementCourses()));
			// remove by Reti Setting ()
			delCols.addAll(getEvalueRemoveCols( referEvaluaItems));
			// remove by hidden column in UI
			delCols.addAll(getHidendRemoveCols( query.getHidedColumns()));
			
			deleteCol(ws, delCols);
			
			this.settingHeader(ws, query, companyName);
			this.settingTitle(ws);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Integer> getRetiDelCols(List<RetirementCourseDto> retirementCourses) {
		List<Integer> delCols = new ArrayList<Integer>();
		if (retirementCourses.isEmpty()) {
			delCols.addAll(COL_MAP.get("desiredWorkingCourseId"));
		}

		return delCols;
	}

	private List<Integer> getHidendRemoveCols(List<String> hidedColumns) {
		List<Integer> delColList = new ArrayList<Integer>();

		hidedColumns.forEach(x -> {
			if (COL_MAP.get(x) != null) {
				delColList.addAll(COL_MAP.get(x));
			}
		});

		return delColList;
	}

	private List<Integer> getEvalueRemoveCols(List<ReferEvaluationItem> referEvaluaItems) {

		List<Integer> delColList = new ArrayList<Integer>();

		int totalItemType = 3;

		for (int i = 0; i < totalItemType; i++) {
			int currentItemType = i;

			int delColNumber = getStartCol(EnumAdaptor.valueOf(i, EvaluationItem.class));
			Optional<ReferEvaluationItem> setting = referEvaluaItems.stream()
					.filter(x -> x.getEvaluationItem().value == currentItemType).findFirst();
			int totalColInGroup = 3;
			if ((setting.isPresent() && !setting.get().isUsageFlg()) || !setting.isPresent()) {
				for (int j = 0; j < totalColInGroup; j++) {
					delColList.add(delColNumber - j);
				}
			} else {
				for (int j = 0; j < totalColInGroup - setting.get().getDisplayNum(); j++) {
					delColList.add(delColNumber - j);
				}

			}

		}
		return delColList;
	}

	private void deleteCol(Worksheet ws, List<Integer> delCol) {
		int deletedCol = 0;
		List<Integer> sortedDelcol = delCol.stream().distinct().sorted().collect(Collectors.toList());
		for (int i = 0; i < sortedDelcol.size(); i++) {
			ws.getCells().deleteColumn(sortedDelcol.get(i) - deletedCol);
			deletedCol++;
		}
	}

	private int getStartCol(EvaluationItem evaluationItem) {
		int colNumber = 0;
		switch (evaluationItem) {

		case PERSONNEL_ASSESSMENT:
			colNumber = PER_EVAL_3;
			break;
		case HEALTH_CONDITION:
			colNumber = HEATH_STATUS_3;
			break;
		case STRESS_CHECK:
			colNumber = STRESS_CHECK_3;
			break;

		}
		return colNumber;
	}

	private void settingTableHeader(Worksheet ws) {
		int rowIndex = FIRST_ROW_FILL - 1;

		ws.getCells().get(rowIndex, RETENTION).putValue(TextResource.localize("JCM008_P3_1"));
		ws.getCells().get(rowIndex, RETIREMENT).putValue(TextResource.localize("JCM008_P3_2"));
		ws.getCells().get(rowIndex, STATUS).putValue(TextResource.localize("JCM008_P3_3"));
		ws.getCells().get(rowIndex, DESIRED_CODE).putValue(TextResource.localize("JCM008_P3_4"));
		ws.getCells().get(rowIndex, DESIRED_NAME).putValue(TextResource.localize("JCM008_P3_4_1"));
		ws.getCells().get(rowIndex, EMPLOYEE_CD).putValue(TextResource.localize("JCM008_P3_5"));
		ws.getCells().get(rowIndex, EMPLOYEE_NAME).putValue(TextResource.localize("JCM008_P3_6"));
		ws.getCells().get(rowIndex, EMPLOYEE_NAME_KANA).putValue(TextResource.localize("JCM008_P3_7"));
		ws.getCells().get(rowIndex, BIRTH_DAY).putValue(TextResource.localize("JCM008_P3_8"));
		ws.getCells().get(rowIndex, AGE).putValue(TextResource.localize("JCM008_P3_9"));
		ws.getCells().get(rowIndex, DEPARTMENT_CODE).putValue(TextResource.localize("JCM008_P3_10"));
		ws.getCells().get(rowIndex, DEPARTMENT_NAME).putValue(TextResource.localize("JCM008_P3_11"));
		ws.getCells().get(rowIndex, EMPLOYMENT_CODE).putValue(TextResource.localize("JCM008_P3_12"));
		ws.getCells().get(rowIndex, EMPLOYMENT_NAME).putValue(TextResource.localize("JCM008_P3_13"));
		ws.getCells().get(rowIndex, JOIN_DATE).putValue(TextResource.localize("JCM008_P3_14"));
		ws.getCells().get(rowIndex, RETI_DATE).putValue(TextResource.localize("JCM008_P3_15"));
		ws.getCells().get(rowIndex, RELEASE_DATE).putValue(TextResource.localize("JCM008_P3_16"));
		ws.getCells().get(rowIndex, INPUT_DATE).putValue(TextResource.localize("JCM008_P3_17"));
		ws.getCells().get(rowIndex, PER_EVAL_1).putValue(TextResource.localize("JCM008_P3_18"));
		ws.getCells().get(rowIndex, PER_EVAL_2).putValue(TextResource.localize("JCM008_P3_19"));
		ws.getCells().get(rowIndex, PER_EVAL_3).putValue(TextResource.localize("JCM008_P3_20"));
		ws.getCells().get(rowIndex, HEATH_STATUS_1).putValue(TextResource.localize("JCM008_P3_21"));
		ws.getCells().get(rowIndex, HEATH_STATUS_2).putValue(TextResource.localize("JCM008_P3_22"));
		ws.getCells().get(rowIndex, HEATH_STATUS_3).putValue(TextResource.localize("JCM008_P3_23"));
		ws.getCells().get(rowIndex, STRESS_CHECK_1).putValue(TextResource.localize("JCM008_P3_24"));
		ws.getCells().get(rowIndex, STRESS_CHECK_2).putValue(TextResource.localize("JCM008_P3_25"));
		ws.getCells().get(rowIndex, STRESS_CHECK_3).putValue(TextResource.localize("JCM008_P3_26"));
		ws.getCells().get(rowIndex, INTERVIEW_DATE).putValue(TextResource.localize("JCM008_P3_27"));
		ws.getCells().get(rowIndex, INTERVIEWER).putValue(TextResource.localize("JCM008_P3_28"));

	}

	private void settingTitle(Worksheet ws) {
		ws.getCells().get(1, 0).putValue(TextResource.localize("JCM008_P2_1"));
		ws.getCells().get(2, 0).putValue(TextResource.localize("JCM008_P2_4"));
		ws.getCells().get(3, 0).putValue(TextResource.localize("JCM008_P2_6"));
		ws.getCells().get(4, 0).putValue(TextResource.localize("JCM008_P2_8"));
	}

	private void setBorder(Worksheet worksheet, int rowIndex) {
		int totalColumn = 29;
		int columnStart = 0;
		for (int column = columnStart; column < totalColumn; column++) {
			Cell cell = worksheet.getCells().get(rowIndex, column);
			Style style = cell.getStyle();
			style.setPattern(BackgroundType.SOLID);
			style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
			style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
			style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
			style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
			cell.setStyle(style);
		}
	}

	private String convertToString(GeneralDate date) {
		if (date == null) {
			return null;
		}
		return date.toString(FORMAT_DATE);
	}

	private void settingHeader(Worksheet ws, SearchRetiredEmployeesQuery query, String companyName) {

		// Set print page
		PageSetup pageSetup = ws.getPageSetup();
		pageSetup.setPrintArea("A1:AC");
		pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
		pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + TITLE);
		// Set header date
		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
		pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");

		ws.getCells().get(1, 3).putValue(query.getStartDate() + " ～ " + query.getEndDate());
		ws.getCells().get(1, 5).putValue(query.isIncludingReflected() ? "※反映済みを含む" : "");
		ws.getCells().get(2, 3).putValue(query.getRetirementAge() != null ? query.getRetirementAge() + "歳" : "");
		ws.getCells().get(3, 3).putValue(query.isAllSelectDepartment() ? "全て" : query.getSelectDepartmentName());
		ws.getCells().get(4, 3).putValue(query.isAllSelectEmployment() ? "全て" : query.getSelectEmploymentName());
	}

}
