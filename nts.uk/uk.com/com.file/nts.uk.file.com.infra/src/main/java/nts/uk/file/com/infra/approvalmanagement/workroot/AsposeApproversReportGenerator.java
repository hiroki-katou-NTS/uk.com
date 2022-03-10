package nts.uk.file.com.infra.approvalmanagement.workroot;

import java.io.OutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.bs.employee.dom.workplace.master.service.WorkplaceInforParam;
import nts.uk.ctx.bs.employee.pub.employee.ResultRequest600Export;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;
import nts.uk.file.com.app.approvalmanagement.workroot.ApproversExportDataSource;
import nts.uk.file.com.app.approvalmanagement.workroot.ApproversExportGenerator;
import nts.uk.query.pub.employee.EmployeeInformationExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalPhaseExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApprovalSettingInformationExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.ApproverExport;
import nts.uk.query.pub.workflow.workroot.approvalmanagement.EmploymentAppHistoryItemExport;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeApproversReportGenerator extends AsposeCellsReportGenerator implements ApproversExportGenerator {

	private static final String TEMPLATE_FILE = "report/CMM030.xlsx";
	// First data row
	private static final int DATA_ROW_START = 3;
	// A3_1 職場名
	private static final int COL_WORKPLACE_NAME = 0;
	// A3_2 コード
	private static final int COL_EMP_CODE = 1;
	// A3_3 名称
	private static final int COL_EMP_NAME = 2;
	// A3_4 職位
	private static final int COL_JOB_TITLE = 3;
	// A3_5 雇用
	private static final int COL_EMPLOYMENT = 4;
	// A3_6 申請種類
	private static final int COL_APP_TYPE = 5;
	// A3_7 履歴
	private static final int COL_PERIOD = 6;
	// A3_8, A3_9, A3_10, A3_11, A3_12 承認者
	private static final int COL_APPROVER_START = 7;
	private static final int APPROVER_COUNT = 5;
	private static final int TOTAL_COL = COL_APPROVER_START + APPROVER_COUNT;

	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	public void generate(FileGeneratorContext generatorContext, ApproversExportDataSource dataSource) {
		try (AsposeCellsReportContext reportContext = this.createContext(TEMPLATE_FILE)) {
			WorksheetCollection collection = reportContext.getWorkbook().getWorksheets();
			Worksheet sheet = collection.get(0);
			String extension = ".xlsx";
			String reportName = TextResource.localize("CMM030_170");
			sheet.setName(reportName);
			this.printHeader(sheet, reportContext, dataSource);
			this.printData(sheet, reportContext, dataSource);
			sheet.setActiveCell("A1");
			OutputStream outputStream = this.createNewFile(generatorContext,
					this.getReportName(reportName + extension));
			reportContext.processDesigner();
			reportContext.saveAsExcel(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printHeader(Worksheet sheet, AsposeCellsReportContext reportContext,
			ApproversExportDataSource dataSource) {
		String companyName = dataSource.getCompany().map(data -> data.getCompanyName().v()).orElse(null);
		String titleName = TextResource.localize("CMM030_170");
		String baseDate = dataSource.getBaseDate().toString(DATE_FORMAT);
		reportContext.setDataSource("titleName", titleName);
		reportContext.setDataSource("companyName", companyName);
		reportContext.setDataSource("baseDate", baseDate);
		sheet.getPageSetup().setHeader(0, "&\"ＭＳ ゴシック\"&9\0" + companyName);
		sheet.getPageSetup().setHeader(1, "&\"ＭＳ ゴシック,Bold\"&16\0" + titleName);
	}

	private void printData(Worksheet sheet, AsposeCellsReportContext reportContext,
			ApproversExportDataSource dataSource) {
		Cells cells = sheet.getCells();
		AtomicInteger currentRow = new AtomicInteger(DATA_ROW_START);

		List<String> sids = dataSource.getSettingInfos().stream()
				.map(data -> data.getPersonApprovalRoot().getEmployeeId()).collect(Collectors.toList());
		List<EmployeeInformationExport> employeeInfos = dataSource.getEmployeeInfos().stream()
				.filter(data -> sids.contains(data.getEmployeeId())).collect(Collectors.toList());
		List<String> workplaceIds = employeeInfos.stream().map(data -> data.getWorkplace().getWorkplaceId())
				.collect(Collectors.toList());
		List<WorkplaceInforParam> workplaces = dataSource.getWorkplaces().stream()
				.filter(data -> workplaceIds.contains(data.getWorkplaceId()))
				.sorted(Comparator.comparing(WorkplaceInforParam::getWorkplaceCode)).collect(Collectors.toList());

		workplaces.forEach(workplace -> {
			List<EmployeeInformationExport> employeesByWkp = employeeInfos.stream()
					.filter(data -> data.getWorkplace().getWorkplaceId().equals(workplace.getWorkplaceId()))
					.sorted(Comparator.comparing(EmployeeInformationExport::getEmployeeCode))
					.collect(Collectors.toList());
			this.printWorkplace(cells, reportContext, currentRow, dataSource, workplace, employeesByWkp);
		});
	}

	/**
	 * Print an workplace (A3_1~A3_12)
	 * 
	 * @param cells
	 * @param currentRow
	 * @param dataSource
	 * @param workplace
	 * @param employees
	 */
	private void printWorkplace(Cells cells, AsposeCellsReportContext reportContext, AtomicInteger currentRow,
			ApproversExportDataSource dataSource, WorkplaceInforParam workplace,
			List<EmployeeInformationExport> employees) {
		List<ApprovalSettingInformationExport> approvalSettingInformations = dataSource.getSettingInfos();
		// Print workplace name (A3_1)
		this.printCell(cells.get(currentRow.get(), COL_WORKPLACE_NAME), workplace.getWorkplaceName());

		// Map data
		Map<EmployeeInformationExport, List<ApprovalSettingInformationExport>> dataMap = employees.stream()
				.collect(Collectors.toMap(Function.identity(), emp -> approvalSettingInformations.stream()
						.filter(data -> data.getPersonApprovalRoot().getEmployeeId().equals(emp.getEmployeeId()))
						.sorted(Comparator.comparing(ApprovalSettingInformationExport::getEmploymentRootAtr)
								.thenComparing(ApprovalSettingInformationExport::getApplicationType,
										Comparator.nullsFirst(Comparator.naturalOrder()))
								.thenComparing(ApprovalSettingInformationExport::getConfirmationRootType,
										Comparator.nullsFirst(Comparator.naturalOrder())))
						.collect(Collectors.toList()), (e1, e2) -> e1, TreeMap::new));
		// Print employees (A3_2~A3_12)
		dataMap.entrySet().forEach(entry -> this.printEmployee(cells, reportContext, currentRow, dataSource,
				entry.getKey(), entry.getValue()));

		// Set bottom border for end of workplace
		this.setBottomBorder(cells, reportContext, currentRow.get() - 1, true);
	}

	/**
	 * Print an employee (A3_2~A3_12)
	 * 
	 * @param cells
	 * @param currentRow
	 * @param dataSource
	 * @param employeeInfo
	 * @param approvalSettingInformations
	 */
	private void printEmployee(Cells cells, AsposeCellsReportContext reportContext, AtomicInteger currentRow,
			ApproversExportDataSource dataSource, EmployeeInformationExport employeeInfo,
			List<ApprovalSettingInformationExport> approvalSettingInformations) {
		// Print employee info (A3_2~A3_5)
		this.printCell(cells.get(currentRow.get(), COL_EMP_CODE), employeeInfo.getEmployeeCode());
		this.printCell(cells.get(currentRow.get(), COL_EMP_NAME), employeeInfo.getBusinessName());
		this.printCell(cells.get(currentRow.get(), COL_JOB_TITLE), employeeInfo.getPosition().getPositionName());
		this.printCell(cells.get(currentRow.get(), COL_EMPLOYMENT), employeeInfo.getEmployment().getEmploymentName());

		// Print approval info (A3_6~A3_12)
		Iterator<ApprovalSettingInformationExport> iterator = approvalSettingInformations.iterator();
		while (iterator.hasNext()) {
			ApprovalSettingInformationExport settingInfo = iterator.next();
			boolean isValid = dataSource.getSubordinateEmployeeIds()
					.contains(settingInfo.getPersonApprovalRoot().getEmployeeId());
			this.printApprovalInfo(cells, currentRow, dataSource, settingInfo, isValid);
			if (!iterator.hasNext()) {
				this.setBottomBorder(cells, reportContext, currentRow.get(), false);
			}
			currentRow.getAndIncrement();
		}
	}

	/**
	 * Print a row of approval information (A3_6~A3_12)
	 * 
	 * @param cells
	 * @param currentRow
	 * @param dataSource
	 * @param settingInfo
	 * @param isValid     「配下社員IDリスト.社員ID」に合致する「承認ルート．社員ID」
	 */
	private void printApprovalInfo(Cells cells, AtomicInteger currentRow, ApproversExportDataSource dataSource,
			ApprovalSettingInformationExport settingInfo, boolean isValid) {
		// Set style
		Range templateRange = cells.createRange(DATA_ROW_START, 0, 1, TOTAL_COL);
		Style style = templateRange.get(0, 0).getStyle();
		Range range = cells.createRange(currentRow.get(), 0, 1, TOTAL_COL);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getBlack());
		range.setStyle(style);
		// Get data to print
		EmploymentRootAtr employmentRootAtr = EnumAdaptor.valueOf(settingInfo.getEmploymentRootAtr(),
				EmploymentRootAtr.class);
		String appType = "";
		switch (employmentRootAtr) {
		case COMMON:
			appType = TextResource.localize("CMM030_171");
			break;
		case APPLICATION:
			appType = EnumAdaptor.valueOf(settingInfo.getApplicationType(), ApplicationType.class).nameId;
			break;
		case CONFIRMATION:
			appType = EnumAdaptor.valueOf(settingInfo.getConfirmationRootType(), ConfirmationRootType.class).nameId;
			break;
		default:
			break;
		}
		Optional<EmploymentAppHistoryItemExport> optHistItem = settingInfo.getPersonApprovalRoot().getApprRoot()
				.getHistoryItems().stream().findFirst();
		List<ApprovalPhaseExport> approvalPhases = settingInfo.getApprovalPhases().stream()
				.sorted(Comparator.comparing(ApprovalPhaseExport::getPhaseOrder).reversed())
				.collect(Collectors.toList());
		boolean isValidOpeMode = settingInfo.getPersonApprovalRoot()
				.getOperationMode() == OperationMode.SUPERIORS_EMPLOYEE.value;
		AtomicBoolean isFirstApprover = new AtomicBoolean(true);
		AtomicInteger col = new AtomicInteger(COL_APPROVER_START);

		// Print approval info
		if (isValid) {
			this.printCell(cells.get(currentRow.get(), COL_APP_TYPE), appType);
			if (optHistItem.isPresent()) {
				this.printCell(cells.get(currentRow.get(), COL_PERIOD),
						TextResource.localize("CMM030_160",
								optHistItem.get().getDatePeriod().start().toString(DATE_FORMAT),
								optHistItem.get().getDatePeriod().end().toString(DATE_FORMAT)));
			}
			approvalPhases.forEach(phase -> {
				Cell cell = cells.get(currentRow.get(), col.getAndIncrement());
				if (isFirstApprover.getAndSet(false) && !isValidOpeMode) {
					this.printCell(cell, TextResource.localize("CMM030_161"));
				} else if (isValidOpeMode) {
					Optional<String> optApproverId = phase.getApprover().stream().map(ApproverExport::getEmployeeId)
							.filter(Objects::nonNull).findFirst();
					String empName = dataSource.getEmployees().stream()
							.filter(data -> optApproverId.map(x -> x.equals(data.getSid())).orElse(false)).findFirst()
							.map(ResultRequest600Export::getEmployeeName).orElse("");
					this.printCell(cell, empName);
				}
			});
		} else {
			this.printCell(cells.get(currentRow.get(), col.get()), TextResource.localize("CMM030_162"));
		}
	}

	private void printCell(Cell cell, String value) {
		cell.setValue(value);
		Style style = cell.getStyle();
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		style.setVerticalAlignment(TextAlignmentType.CENTER);
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.getFont().setName("ＭＳ ゴシック");
		style.getFont().setSize(9);
		cell.setStyle(style);
	}

	private void setBottomBorder(Cells cells, AsposeCellsReportContext reportContext, int row, boolean isEndWorkplace) {
		int firstIndex = isEndWorkplace ? COL_WORKPLACE_NAME : COL_EMP_CODE;
		int number = isEndWorkplace ? TOTAL_COL : TOTAL_COL - 1;
		Range range = cells.createRange(row, firstIndex, 1, number);
		Style style = range.get(0, 0).getStyle();
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		range.setStyle(style);
	}
}
