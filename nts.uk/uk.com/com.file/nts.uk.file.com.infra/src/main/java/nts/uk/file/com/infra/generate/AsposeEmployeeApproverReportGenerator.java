package nts.uk.file.com.infra.generate;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import approve.employee.EmployeeApproverDataSource;
import approve.employee.EmployeeApproverRootOutputGenerator;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApproverAsApplicationInforOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverAsApplicationOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeOrderApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeEmployeeApproverReportGenerator extends AsposeCellsReportGenerator
		implements EmployeeApproverRootOutputGenerator {

	private static final String TEMPLATE_FILE = "report/申請者として承認ルートのEXCEL出力.xlsx";

	private static final String REPORT_FILE_NAME = "申請者として承認ルートのEXCEL出力.xlsx";

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

	@Override
	public void generate(FileGeneratorContext generatorContext, EmployeeApproverDataSource dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			// set up page prepare print
			this.printPage(worksheet);
			this.printWorkplace(worksheets, dataSource);

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * PRINT PAGE
	 * 
	 * @param worksheet
	 * @param lstWorkplace
	 */
	private void printPage(Worksheet worksheet) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:P");
	}

	/**
	 * Sets the title style.
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setTitleStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
		style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOTTED, Color.getGray());
		cell.setStyle(style);
	}

	/**
	 * PRINT ALL WORKPLACE
	 * 
	 * @param worksheets
	 * @param dataSource
	 */
	private void printWorkplace(WorksheetCollection worksheets, EmployeeApproverDataSource dataSource) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		Map<String, WpApproverAsAppOutput> lstWorkplace = dataSource.getWpApprover();
		if (lstWorkplace.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Workplace list is empty"));

		}

		int firstRow = 2;
		for (Map.Entry m : lstWorkplace.entrySet()) {
			WpApproverAsAppOutput workplace = (WpApproverAsAppOutput) m.getValue();
			firstRow = this.printEachWorkplace(worksheets, cells, firstRow, workplace);

		}

	}

	/**
	 * PRINT EACH WORKPLACE
	 * 
	 * @param worksheets
	 * @param cells
	 * @param firstRow
	 * @param workplace
	 * @return
	 */
	private int printEachWorkplace(WorksheetCollection worksheets, Cells cells, int firstRow,
			WpApproverAsAppOutput workplace) {

		WorkplaceImport wpInfor = workplace.getWpInfor();
		Map<String, EmployeeApproverAsApplicationOutput> employee = workplace.getMapEmpRootInfo();

		// set "【職場】"
		Cell workPlace = cells.get(firstRow, COLUMN_INDEX[1]);
		workPlace.setValue("【職場】");

		// set worplace Name, workplace Code
		Cell workPlaceCode = cells.get(firstRow, COLUMN_INDEX[2]);
		workPlaceCode.setValue(wpInfor.getWkpCode() + " " + wpInfor.getWkpName());

		// tăng rowIndex lên 1
		firstRow = firstRow + 1;

		for (Map.Entry m : employee.entrySet()) {
			EmployeeApproverAsApplicationOutput output = (EmployeeApproverAsApplicationOutput) m.getValue();
			System.out.println(output);
			
			// IN RA TỪ CỘT THỨ 3 TRỞ RA
			for (Map.Entry m1 : output.getMapAppTypeAsApprover().entrySet()) {


				// LIST CÁC FORM CỦA MỘT NGƯỜI
				List<ApproverAsApplicationInforOutput> appLst = (List<ApproverAsApplicationInforOutput>) m1.getValue();

				//TÍNH MAX ĐỂ MERGER CELL
				int max = this.findMax(appLst);
				
				
				// set employee code - CỘT 1
				if(max > 1) {
					cells.merge(firstRow, COLUMN_INDEX[1], max, 1, true);
				}
				Cell em_Code = cells.get(firstRow, COLUMN_INDEX[1]);
				em_Code.setValue(output.getEmployeeInfor().getEId());
				
				// set employee name - CỘT 2
				
				if(max > 1) {
					cells.merge(firstRow, COLUMN_INDEX[2], max, 1, true);
				}
				Cell em_Name = cells.get(firstRow, COLUMN_INDEX[2]);
				em_Name.setValue(output.getEmployeeInfor().getEName());

				// IN RA CỘT THỨ 3
				if(max > 1) {
					cells.merge(firstRow, COLUMN_INDEX[3], max, 1, true);
				}
				Cell em_Form = cells.get(firstRow, COLUMN_INDEX[3]);
				if (m1.getKey().toString().equals("99")) {
					em_Form.setValue("共通");
				} else {
					em_Form.setValue(
							EnumAdaptor.valueOf(Integer.valueOf(m1.getKey().toString()), ApplicationType.class));
				}

				// SET STYLE CHO CỘT THỨ 3
				setTitleStyle(em_Form);

				for (int i = 1; i < COLUMN_INDEX.length; i++) {
					Cell cell = cells.get(firstRow, COLUMN_INDEX[i]);
					setTitleStyle(cell);
				}

				// KIỂM TRA XEM ĐỘ DÀI CÁC PHRASE
				// NẾU BẰNG 0 THÌ IN RA CỘT THỨ 13
				// NGƯỢC LẠI THÌ KHÔNG IN HAY GÌ ĐÓ
				if (appLst.size() == 0) {
					Cell notice = cells.get(firstRow, COLUMN_INDEX[14]);
					notice.setValue("マスタなし");
					Color color = Color.getRed();
					Font font = notice.getStyle().getFont();
					font.setColor(color);
					setTitleStyle(notice);
					notice.setStyle( notice.getStyle());
					firstRow = firstRow + 1;
				} else {

					// TÌM RA SỐ DÒNG MAX ĐỂ MERGE CELL CHO CỘT 3, 4

					

					int j = 4;
					// IN RA CỘT THỨ 4 CỦA PHRASE I
					for (ApproverAsApplicationInforOutput app : appLst) {

						if (app.getPhaseNumber() == 1) {

							cells.merge(firstRow, 4, max, 1, true);
							Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[4]);
							appPhrase.setValue(app.getApprovalForm());
							// SET STYLE
							for (int i = 0; i < max; i++) {
								setTitleStyle(appPhrase);
							}

							// IN RA CỘT THỨ 5 CỦA PHRASE I
							List<EmployeeOrderApproverAsAppOutput> employeelst = app.getLstEmpInfo();
							int i = 0;
							for (EmployeeOrderApproverAsAppOutput eName : employeelst) {
								Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[5]);
								em_name.setValue(eName.getEmployeeName());
								setTitleStyle(em_name);
								i++;

							}
							if ((j + 2) < 14) {
								j = j + 2;
							}

						} else if (app.getPhaseNumber() == 2) {

							cells.merge(firstRow, 4, max, 1, true);
							Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[6]);
							appPhrase.setValue(app.getApprovalForm());
							// SET STYLE
							for (int i = 0; i < max; i++) {
								setTitleStyle(appPhrase);
							}

							// IN RA CỘT THỨ 5 CỦA PHRASE I
							List<EmployeeOrderApproverAsAppOutput> employeelst = app.getLstEmpInfo();
							int i = 0;
							for (EmployeeOrderApproverAsAppOutput eName : employeelst) {
								Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[7]);
								em_name.setValue(eName.getEmployeeName());
								setTitleStyle(em_name);
								i++;

							}
							if ((j + 2) < 14) {
								j = j + 2;
							}

						} else if (app.getPhaseNumber() == 3) {
							cells.merge(firstRow, 4, max, 1, true);
							Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[8]);
							appPhrase.setValue(app.getApprovalForm());
							// SET STYLE
							for (int i = 0; i < max; i++) {
								setTitleStyle(appPhrase);
							}

							// IN RA CỘT THỨ 5 CỦA PHRASE I
							List<EmployeeOrderApproverAsAppOutput> employeelst = app.getLstEmpInfo();
							int i = 0;
							for (EmployeeOrderApproverAsAppOutput eName : employeelst) {
								Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[9]);
								em_name.setValue(eName.getEmployeeName());
								setTitleStyle(em_name);
								i++;

							}
							if ((j + 2) < 14) {
								j = j + 2;
							}

						} else if (app.getPhaseNumber() == 4) {
							cells.merge(firstRow, 4, max, 1, true);
							Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[10]);
							appPhrase.setValue(app.getApprovalForm());
							// SET STYLE
							for (int i = 0; i < max; i++) {
								setTitleStyle(appPhrase);
							}

							// IN RA CỘT THỨ 5 CỦA PHRASE I
							List<EmployeeOrderApproverAsAppOutput> employeelst = app.getLstEmpInfo();
							int i = 0;
							for (EmployeeOrderApproverAsAppOutput eName : employeelst) {
								Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[11]);
								em_name.setValue(eName.getEmployeeName());
								setTitleStyle(em_name);
								i++;

							}
							if ((j + 2) < 14) {
								j = j + 2;
							}

						}else if (app.getPhaseNumber() == 5) {
							cells.merge(firstRow, 4, max, 1, true);
							Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[12]);
							appPhrase.setValue(app.getApprovalForm());
							// SET STYLE
							for (int i = 0; i < max; i++) {
								setTitleStyle(appPhrase);
							}

							// IN RA CỘT THỨ 5 CỦA PHRASE I
							List<EmployeeOrderApproverAsAppOutput> employeelst = app.getLstEmpInfo();
							int i = 0;
							for (EmployeeOrderApproverAsAppOutput eName : employeelst) {
								Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[13]);
								em_name.setValue(eName.getEmployeeName());
								setTitleStyle(em_name);
								i++;

							}
							if ((j + 2) < 14) {
								j = j + 2;
							}

						}

					}
					firstRow = firstRow + max;

				}

			}
		}

		return firstRow;

	}

	/*
	 * find number of person
	 */

	private int findMax(List<ApproverAsApplicationInforOutput> approval) {
		int max = 1;
		for (ApproverAsApplicationInforOutput appRoot : approval) {
			if (max < appRoot.getLstEmpInfo().size()) {
				max = appRoot.getLstEmpInfo().size();
			}
		}
		return max;
	}

}
