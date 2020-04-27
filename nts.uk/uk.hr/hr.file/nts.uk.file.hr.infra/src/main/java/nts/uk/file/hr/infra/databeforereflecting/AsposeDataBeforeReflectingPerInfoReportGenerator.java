package nts.uk.file.hr.infra.databeforereflecting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
import nts.arc.time.GeneralDateTime;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementCategory;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementReasonCategory1;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementReasonCategory2;
import nts.uk.file.hr.app.databeforereflecting.DataBeforeReflectingPerInfoGenerator;
import nts.uk.screen.hr.app.databeforereflecting.find.DataBeforeReflectResultDto;
import nts.uk.screen.hr.app.databeforereflecting.find.RetiredEmployeeInfoResult;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeDataBeforeReflectingPerInfoReportGenerator extends AsposeCellsReportGenerator
		implements DataBeforeReflectingPerInfoGenerator {

	private static final String TEMPLATE_FILE = "report/退職者登録一覧_Template.xlsx";

	private static final String REPORT_FILE_NAME = "退職者登録一覧_";

	private static final String REPORT_FILE_EXTENSION = ".xlsx";

	private static final String FORMAT_DATE = "yyyy/MM/dd";

	private static final String TITLE = "退職者登録一覧";

	private static final int FIRST_ROW_FILL = 3;

	private static final int MAX_LINE = 30;

	private static final int REG_STATUS = 0;
	private static final int SCD = 1;
	private static final int SNAME = 2;
	private static final int SNAMEKANA = 3;
	private static final int DEPCD = 4;
	private static final int DEPNAME = 5;
	private static final int EMPCD = 6;
	private static final int EMPNAME = 7;
	private static final int RETIDATE = 8;
	private static final int RELDATE = 9;
	private static final int INPUTDATE = 10;
	private static final int RETICTG = 11;
	private static final int RETIRES_CTG_CD_1 = 12;
	private static final int RETIRES_CTG_NAME_1 = 13;
	private static final int RETIRES_CTG_CD_2 = 14;
	private static final int RETIRES_CTG_NAME_2 = 15;
	private static final int RETE_NOTE = 16;
	private static final int PRO_FOR_RETI = 17;
	private static final int NOTI_DATE = 18;
	private static final int NOTI_PAYMENT = 19;
	private static final int PRO_FOR_DISMIS = 20;
	private static final int REASON_DISMIS_1 = 21;
	private static final int REASON_DISMIS_1_DETAIL = 22;
	private static final int REASON_DISMIS_2 = 23;
	private static final int REASON_DISMIS_2_DETAIL = 24;
	private static final int REASON_DISMIS_3 = 25;
	private static final int REASON_DISMIS_3_DETAIL = 26;
	private static final int REASON_DISMIS_4 = 27;
	private static final int REASON_DISMIS_4_DETAIL = 28;
	private static final int REASON_DISMIS_5 = 29;
	private static final int REASON_DISMIS_5_DETAIL = 30;
	private static final int REASON_DISMIS_6 = 31;
	private static final int REASON_DISMIS_6_DETAIL = 32;
	private static final int NOTI = 33;
	private static final int INTERVIEW_DATE = 34;
	private static final int INTERVIEWER = 35;

	@Override
	public void generate(FileGeneratorContext generatorContext, DataBeforeReflectResultDto data, String companyName) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {
			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			// set up page prepare print
			this.writeFileExcel(worksheets, data, companyName);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(
					this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME + REPORT_FILE_EXTENSION)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void writeFileExcel(WorksheetCollection wsc, DataBeforeReflectResultDto data, String companyName) {
		try {

			List<RetiredEmployeeInfoResult> retiredEmployees = data.getRetiredEmployees();
			List<EmployeeInformationImport> employeeImports = data.getEmployeeImports();

			int rowIndex = FIRST_ROW_FILL;
			Worksheet ws = wsc.get(0);
			this.settingTableHeader(ws);
			int page = 1;
			for (int i = 0; i < retiredEmployees.size(); i++) {

				RetiredEmployeeInfoResult entity = retiredEmployees.get(i);
				Optional<EmployeeInformationImport> employeeImportOpt = employeeImports.stream()
						.filter(x -> x.getEmployeeId().equals(entity.getSId())).findFirst();

				fillDataToCell(ws, rowIndex, entity, employeeImportOpt);

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

			this.settingHeader(ws, companyName);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setBorder(Worksheet worksheet, int rowIndex) {
		int totalColumn = 36;
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

	private void fillDataToCell(Worksheet ws, int rowIndex, RetiredEmployeeInfoResult entity,
			Optional<EmployeeInformationImport> employeeImportOpt) {
		EmployeeInformationImport empInfo = EmployeeInformationImport.builder().build();

		if (employeeImportOpt.isPresent()) {
			empInfo = employeeImportOpt.get();
		}
		// content

		String status = "";
		switch (entity.getStatus()) {
		case "1":
			status = TextResource.localize("JCM007_A3_2");
			break;
		case "2":
			status = TextResource.localize("JCM007_A3_3");
			break;
		}

		ws.getCells().get(rowIndex, REG_STATUS).putValue(status);

		ws.getCells().get(rowIndex, SCD).putValue(entity.getScd());
		ws.getCells().get(rowIndex, SNAME).putValue(empInfo.getEmployeeName());
		ws.getCells().get(rowIndex, SNAMEKANA).putValue(empInfo.getBusinessNameKana());

		if (empInfo.getDepartment() != null) {
			ws.getCells().get(rowIndex, DEPCD).putValue(empInfo.getDepartment().getDepartmentCode());
			ws.getCells().get(rowIndex, DEPNAME).putValue(empInfo.getDepartment().getDepartmentName());
		}

		if (empInfo.getEmployment() != null) {
			ws.getCells().get(rowIndex, EMPCD).putValue(empInfo.getEmployment().getEmploymentCode());
			ws.getCells().get(rowIndex, EMPNAME).putValue(empInfo.getEmployment().getEmploymentName());
		}

		ws.getCells().get(rowIndex, RETIDATE).putValue(convertDate(entity.getRetirementDate()));

		ws.getCells().get(rowIndex, RELDATE).putValue(convertDate(entity.getReleaseDate()));
		ws.getCells().get(rowIndex, INPUTDATE).putValue(convertDate(entity.getInputDate()));

		ws.getCells().get(rowIndex, RETICTG)
				.putValue(EnumAdaptor.valueOf(entity.getRetirementCategory(), RetirementCategory.class).name);

		ws.getCells().get(rowIndex, RETIRES_CTG_CD_1)
				.putValue(EnumAdaptor.valueOf(entity.getRetirementReasonCtg1(), RetirementReasonCategory1.class).name);

		ws.getCells().get(rowIndex, RETIRES_CTG_NAME_1).putValue(entity.getRetirementReasonCtgName1());

		ws.getCells().get(rowIndex, RETIRES_CTG_CD_2)
				.putValue(EnumAdaptor.valueOf(entity.getRetirementReasonCtg2(), RetirementReasonCategory2.class).name);

		ws.getCells().get(rowIndex, RETIRES_CTG_NAME_2).putValue(entity.getRetirementReasonCtgName2());
		ws.getCells().get(rowIndex, RETE_NOTE).putValue(entity.getRetirementRemarks());
		ws.getCells().get(rowIndex, PRO_FOR_RETI).putValue(entity.getRetirementReasonVal());
		ws.getCells().get(rowIndex, NOTI_DATE).putValue(convertDate(entity.getDismissalNoticeDate()));
		ws.getCells().get(rowIndex, NOTI_PAYMENT).putValue(convertDate(entity.getDismissalNoticeDateAllow()));
		ws.getCells().get(rowIndex, PRO_FOR_DISMIS).putValue(entity.getReaAndProForDis());
		ws.getCells().get(rowIndex, REASON_DISMIS_1).putValue(entity.getNaturalUnaReasons_1() == 0 ? "" : "○");
		ws.getCells().get(rowIndex, REASON_DISMIS_1_DETAIL).putValue(entity.getNaturalUnaReasons_1Val());
		ws.getCells().get(rowIndex, REASON_DISMIS_2).putValue(entity.getNaturalUnaReasons_2() == 0 ? "" : "○");
		ws.getCells().get(rowIndex, REASON_DISMIS_2_DETAIL).putValue(entity.getNaturalUnaReasons_2Val());
		ws.getCells().get(rowIndex, REASON_DISMIS_3).putValue(entity.getNaturalUnaReasons_3() == 0 ? "" : "○");
		ws.getCells().get(rowIndex, REASON_DISMIS_3_DETAIL).putValue(entity.getNaturalUnaReasons_3Val());
		ws.getCells().get(rowIndex, REASON_DISMIS_4).putValue(entity.getNaturalUnaReasons_4() == 0 ? "" : "○");
		ws.getCells().get(rowIndex, REASON_DISMIS_4_DETAIL).putValue(entity.getNaturalUnaReasons_4Val());
		ws.getCells().get(rowIndex, REASON_DISMIS_5).putValue(entity.getNaturalUnaReasons_5() == 0 ? "" : "○");
		ws.getCells().get(rowIndex, REASON_DISMIS_5_DETAIL).putValue(entity.getNaturalUnaReasons_5Val());
		ws.getCells().get(rowIndex, REASON_DISMIS_6).putValue(entity.getNaturalUnaReasons_6() == 0 ? "" : "○");
		ws.getCells().get(rowIndex, REASON_DISMIS_6_DETAIL).putValue(entity.getNaturalUnaReasons_6Val());
		ws.getCells().get(rowIndex, NOTI)
				.putValue(Integer.valueOf(StringUtil.isNullOrEmpty(entity.getNotificationCategory(), true) ? "0"
						: entity.getNotificationCategory()) == 0 ? "" : TextResource.localize("JCM007_P4_34_1"));
		ws.getCells().get(rowIndex, INTERVIEW_DATE).putValue("");
		ws.getCells().get(rowIndex, INTERVIEWER).putValue("");

	}

	private String convertDate(GeneralDateTime date) {
		if (date == null) {
			return null;
		}
		return date.toString(FORMAT_DATE);
	}

	private String convertDate(GeneralDate date) {
		if (date == null) {
			return null;
		}
		return date.toString(FORMAT_DATE);
	}

	private void settingTableHeader(Worksheet ws) {
		int rowIndex = FIRST_ROW_FILL - 1;

		ws.getCells().get(rowIndex, REG_STATUS).putValue(TextResource.localize("JCM007_P3_1"));
		ws.getCells().get(rowIndex, SCD).putValue(TextResource.localize("JCM007_P3_2"));
		ws.getCells().get(rowIndex, SNAME).putValue(TextResource.localize("JCM007_P3_3"));
		ws.getCells().get(rowIndex, SNAMEKANA).putValue(TextResource.localize("JCM007_P3_4"));
		ws.getCells().get(rowIndex, DEPCD).putValue(TextResource.localize("JCM007_P3_5"));
		ws.getCells().get(rowIndex, DEPNAME).putValue(TextResource.localize("JCM007_P3_6"));
		ws.getCells().get(rowIndex, EMPCD).putValue(TextResource.localize("JCM007_P3_7"));
		ws.getCells().get(rowIndex, EMPNAME).putValue(TextResource.localize("JCM007_P3_8"));
		ws.getCells().get(rowIndex, RETIDATE).putValue(TextResource.localize("JCM007_P3_9"));
		ws.getCells().get(rowIndex, RELDATE).putValue(TextResource.localize("JCM007_P3_10"));
		ws.getCells().get(rowIndex, INPUTDATE).putValue(TextResource.localize("JCM007_P3_11"));
		ws.getCells().get(rowIndex, RETICTG).putValue(TextResource.localize("JCM007_P3_12"));
		ws.getCells().get(rowIndex, RETIRES_CTG_CD_1).putValue(TextResource.localize("JCM007_P3_13"));
		ws.getCells().get(rowIndex, RETIRES_CTG_NAME_1).putValue(TextResource.localize("JCM007_P3_14"));
		ws.getCells().get(rowIndex, RETIRES_CTG_CD_2).putValue(TextResource.localize("JCM007_P3_15"));
		ws.getCells().get(rowIndex, RETIRES_CTG_NAME_2).putValue(TextResource.localize("JCM007_P3_16"));
		ws.getCells().get(rowIndex, RETE_NOTE).putValue(TextResource.localize("JCM007_P3_17"));
		ws.getCells().get(rowIndex, PRO_FOR_RETI).putValue(TextResource.localize("JCM007_P3_18"));
		ws.getCells().get(rowIndex, NOTI_DATE).putValue(TextResource.localize("JCM007_P3_19"));
		ws.getCells().get(rowIndex, NOTI_PAYMENT).putValue(TextResource.localize("JCM007_P3_20"));
		ws.getCells().get(rowIndex, PRO_FOR_DISMIS).putValue(TextResource.localize("JCM007_P3_21"));
		ws.getCells().get(rowIndex, REASON_DISMIS_1).putValue(TextResource.localize("JCM007_P3_22"));
		ws.getCells().get(rowIndex, REASON_DISMIS_1_DETAIL).putValue(TextResource.localize("JCM007_P3_23"));
		ws.getCells().get(rowIndex, REASON_DISMIS_2).putValue(TextResource.localize("JCM007_P3_24"));
		ws.getCells().get(rowIndex, REASON_DISMIS_2_DETAIL).putValue(TextResource.localize("JCM007_P3_25"));
		ws.getCells().get(rowIndex, REASON_DISMIS_3).putValue(TextResource.localize("JCM007_P3_26"));
		ws.getCells().get(rowIndex, REASON_DISMIS_3_DETAIL).putValue(TextResource.localize("JCM007_P3_27"));
		ws.getCells().get(rowIndex, REASON_DISMIS_4).putValue(TextResource.localize("JCM007_P3_28"));
		ws.getCells().get(rowIndex, REASON_DISMIS_4_DETAIL).putValue(TextResource.localize("JCM007_P3_29"));
		ws.getCells().get(rowIndex, REASON_DISMIS_5).putValue(TextResource.localize("JCM007_P3_30"));
		ws.getCells().get(rowIndex, REASON_DISMIS_5_DETAIL).putValue(TextResource.localize("JCM007_P3_31"));
		ws.getCells().get(rowIndex, REASON_DISMIS_6).putValue(TextResource.localize("JCM007_P3_32"));
		ws.getCells().get(rowIndex, REASON_DISMIS_6_DETAIL).putValue(TextResource.localize("JCM007_P3_33"));
		ws.getCells().get(rowIndex, NOTI).putValue(TextResource.localize("JCM007_P3_34"));
		ws.getCells().get(rowIndex, INTERVIEW_DATE).putValue(TextResource.localize("JCM007_P3_35"));
		ws.getCells().get(rowIndex, INTERVIEWER).putValue(TextResource.localize("JCM007_P3_36"));

	}

	private void settingHeader(Worksheet ws, String companyName) {

		// Set print page
		PageSetup pageSetup = ws.getPageSetup();
		pageSetup.setPrintArea("A1:AC");
		pageSetup.setHeader(0, "&\"ＭＳ ゴシック\"&10 " + companyName);
		pageSetup.setHeader(1, "&\"ＭＳ ゴシック\"&16 " + TITLE);
		// Set header date
		DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/M/d  HH:mm:ss", Locale.JAPAN);
		pageSetup.setHeader(2, "&\"ＭＳ ゴシック\"&10 " + LocalDateTime.now().format(fullDateTimeFormatter) + "\npage&P ");
	}

}
