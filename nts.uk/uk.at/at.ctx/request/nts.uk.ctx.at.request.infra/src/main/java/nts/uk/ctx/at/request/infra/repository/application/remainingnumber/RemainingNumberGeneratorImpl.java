package nts.uk.ctx.at.request.infra.repository.application.remainingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.NumberOfWorkTypeUsedImport;
import nts.uk.ctx.at.request.dom.application.remainingnumer.ExcelInforCommand;
import nts.uk.ctx.at.request.dom.application.remainingnumer.PlannedVacationListCommand;
import nts.uk.ctx.at.request.dom.application.remainingnumer.RemainingNumberGenerator;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class RemainingNumberGeneratorImpl extends AsposeCellsReportGenerator implements RemainingNumberGenerator {

	private static final String FILE_TEMPLATE = "remainingNumberTemplate.xlsx";

	@Override
	public void generate(FileGeneratorContext generatorContext, List<ExcelInforCommand> dataSource) {
		// TODO Auto-generated method stub
		try (val reportContext = this.createContext(FILE_TEMPLATE)) {
			List<String> wkCodeTemp = new ArrayList<>();
			List<String> htbPlanneds = new ArrayList<>();
			val designer = this.createContext(FILE_TEMPLATE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			
			// get auto index
			if (!dataSource.isEmpty()) {
				for (int i = 0; i < dataSource.get(0).getPlannedVacationListCommand().size(); i++) {
					final String workTypeCode = dataSource.get(0).getPlannedVacationListCommand().get(i).getWorkTypeCode();

					for (int j = 0; j < dataSource.size(); j++) {
						Optional<NumberOfWorkTypeUsedImport> optNumWTUse = dataSource.get(j).getNumberOfWorkTypeUsedImport()
								.stream().filter((item) -> item.getWorkTypeCode().equals(workTypeCode)).findFirst();
						if (optNumWTUse.isPresent()) {
							wkCodeTemp.add(workTypeCode);
							break;
						}
					}
				}
			}
			htbPlanneds = wkCodeTemp.stream().distinct().collect(Collectors.toList());

			printTemplate(worksheet, dataSource, htbPlanneds);

			printDataSource(worksheet, dataSource, htbPlanneds);

			worksheet.autoFitColumns();
			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();
			LoginUserContext loginUserContext = AppContexts.user();
			String fileName = "KDM002_" + loginUserContext.employeeCode() + ".xlsx";

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(fileName)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void printTemplate(Worksheet worksheet, List<ExcelInforCommand> dataSource, List<String> htbPlanneds)
			throws Exception {

		Cells cells = worksheet.getCells();
		//KDM002_35
		cells.get(0, 0).setValue(TextResource.localize("KDM002_35"));
		setBackgroundHeader(cells.get(0, 0));
		setBorderStyle(cells.get(0, 0));
		cells.get(0, 1).setValue(TextResource.localize("KDM002_11"));
		setBackgroundHeader(cells.get(0, 1));
		setBorderStyle(cells.get(0, 1));
		cells.get(0, 2).setValue(TextResource.localize("KDM002_12"));
		setBackgroundHeader(cells.get(0, 2));
		setBorderStyle(cells.get(0, 2));
		cells.get(0, 3).setValue(TextResource.localize("KDM002_13"));
		setBackgroundHeader(cells.get(0, 3));
		setBorderStyle(cells.get(0, 3));
		cells.get(0, 4).setValue(TextResource.localize("KDM002_14"));
		setBackgroundHeader(cells.get(0, 4));
		setBorderStyle(cells.get(0, 4));
		cells.get(0, 5).setValue(TextResource.localize("KDM002_15"));
		setBackgroundHeader(cells.get(0, 5));
		setBorderStyle(cells.get(0, 5));
		cells.get(0, 6).setValue(TextResource.localize("KDM002_16"));
		setBackgroundHeader(cells.get(0, 6));
		setBorderStyle(cells.get(0, 6));
		cells.get(0, 7).setValue(TextResource.localize("KDM002_9"));
		setBackgroundHeader(cells.get(0, 7));
		setBorderStyle(cells.get(0, 7));
		
		// auto header
		int index = 0;
		for (String wtCode : htbPlanneds) {
			Optional<PlannedVacationListCommand> opPlanVa = dataSource.get(0).getPlannedVacationListCommand().stream()
					.filter(x -> x.getWorkTypeCode().equals(wtCode)).findFirst();
			if (opPlanVa.isPresent()) {
				cells.get(0, 7 + index).setValue(opPlanVa.get().getWorkTypeName());
				setBackgroundHeader(cells.get(0, 7 + index));
				setBorderStyle(cells.get(0, 7 + index));
				cells.get(0, 8 + index).setValue(opPlanVa.get().getWorkTypeName() + TextResource.localize("KDM002_34"));
				setBackgroundHeader(cells.get(0, 8 + index));
				setBorderStyle(cells.get(0, 8 + index));
				index += 2;
			}
		}

	}

	private void printDataSource(Worksheet worksheet, List<ExcelInforCommand> dataSource, List<String> htbPlanneds)
			throws Exception {
		int firstRow = 1;
		for (ExcelInforCommand excelInforCommand : dataSource) {
			firstRow = fillDataToExcel(worksheet, firstRow, excelInforCommand, htbPlanneds);
		}
	}

	private int fillDataToExcel(Worksheet worksheet, int firstRow, ExcelInforCommand excelInforCommand,
			List<String> htbPlanneds) {
		Cells cells = worksheet.getCells();
		cells.get(firstRow, 0).setValue(excelInforCommand.getEmployeeCode());
		setBorderStyle(cells.get(firstRow, 0));
		cells.get(firstRow, 1).setValue(excelInforCommand.getName());
		setBorderStyle(cells.get(firstRow, 1));
		cells.get(firstRow, 2).setValue(excelInforCommand.getDateStart());
		setBorderStyle(cells.get(firstRow, 2));
		cells.get(firstRow, 3).setValue(excelInforCommand.getDateEnd());
		setBorderStyle(cells.get(firstRow, 3));
		cells.get(firstRow, 4).setValue(excelInforCommand.getDateOffYear());
		setBorderStyle(cells.get(firstRow, 4));
		cells.get(firstRow, 5).setValue(excelInforCommand.getDateTargetRemaining());
		setBorderStyle(cells.get(firstRow, 5));
		cells.get(firstRow, 6)
				.setValue(excelInforCommand.getDateAnnualRetirement() + TextResource.localize("KDM002_33"));
		setBorderStyle(cells.get(firstRow, 6));
		cells.get(firstRow, 7).setValue(excelInforCommand.getDateAnnualRest() + TextResource.localize("KDM002_33"));
		setBorderStyle(cells.get(firstRow, 7));
		int i = 0;
		for (String wtCode : htbPlanneds) {
			Optional<NumberOfWorkTypeUsedImport> opNumber = excelInforCommand.getNumberOfWorkTypeUsedImport().stream()
					.filter(x -> x.getWorkTypeCode().equals(wtCode)).findFirst();
			if (opNumber.isPresent()) {
				cells.get(firstRow, 7 + i)
						.setValue(opNumber.get().getAttendanceDaysMonth() + TextResource.localize("KDM002_33"));
				setBorderStyle(cells.get(firstRow, 7 + i));
				i++;
			}
			Optional<PlannedVacationListCommand> opPlanVa = excelInforCommand.getPlannedVacationListCommand().stream()
					.filter(x -> x.getWorkTypeCode().equals(wtCode)).findFirst();
			if (opPlanVa.isPresent()) {
				cells.get(firstRow, 7 + i)
						.setValue(opPlanVa.get().getMaxNumberDays() + TextResource.localize("KDM002_33"));
				setBorderStyle(cells.get(firstRow, 7 + i));
				i++;
			}
		}
		firstRow += 1;
		return firstRow;
	}

	private void setBorderStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
		cell.setStyle(style);
	}

	private void setBackgroundHeader(Cell cell) {
		Style style = cell.getStyle();
		style.setForegroundColor(Color.getGainsboro());
		cell.setStyle(style);
	}
}
