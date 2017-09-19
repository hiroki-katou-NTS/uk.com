package nts.uk.file.com.infra.generate.company;

import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.HorizontalPageBreakCollection;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.VerticalPageBreakCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalForApplication;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootMaster;
import nts.uk.file.com.app.EmployeeUnregisterOutputDataSoure;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputDataSource;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeMasterApproverRoot extends AsposeCellsReportGenerator implements MasterApproverRootOutputGenerator {
	private static final String TEMPLATE_FILE = "report/マスタリストのEXCEL出力.xlsx";

	private static final String REPORT_FILE_NAME = "マスタリストのEXCEL出力.xlsx";

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

	@Override
	public void generate(FileGeneratorContext generatorContext, MasterApproverRootOutputDataSource dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			// set up page prepare print
			this.printPage(worksheet);
			this.printCompanyOfApproval(worksheets, dataSource);

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
	 * @param lstDeparmentInf
	 */
	private void printPage(Worksheet worksheet) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:N");
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

	/*
	 * find number of person
	 */

	private int findMax(ApprovalForApplication approval) {
		int max = 0;
		for (ApprovalRootMaster appRoot : approval.getLstApproval()) {
			if (max < appRoot.getPersonName().size()) {
				max = appRoot.getPersonName().size();
			}

		}
		return max;
	}

	private void printCompanyOfApproval(WorksheetCollection worksheets, MasterApproverRootOutputDataSource dataSource) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		List<ApprovalForApplication> lstApproval = dataSource.getMasterApproverRootOutput().getCompanyRootInfor()
				.getLstComs();
		int firstRow = 2;
		for (int i = 0; i < lstApproval.size(); i++) {
			if (lstApproval.get(i).getLstApproval() == null) {

				int numberOfPage = (firstRow + 1) / 52;

				int numberOfRowMerge = (52 * numberOfPage) - firstRow;
				if (numberOfRowMerge > 0) {
					// print App name
					Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
					nameApp.setValue(lstApproval.get(i).getAppTypeName());
					for (int index = 1; index < 13; index++) {
						Cell oddCell = cells.get(firstRow, COLUMN_INDEX[index]);
						setTitleStyle(oddCell);
					}

					firstRow = firstRow + 1;
					HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
					hPageBreaks.add("N" + firstRow);
					VerticalPageBreakCollection vPageBreaks = worksheet.getVerticalPageBreaks();
					vPageBreaks.add("N" + firstRow);

					firstRow = firstRow + 1;
				} else {

					// print App name
					Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
					nameApp.setValue(lstApproval.get(i).getAppTypeName());
					for (int index = 1; index < 13; index++) {
						Cell oddCell = cells.get(firstRow, COLUMN_INDEX[index]);
						setTitleStyle(oddCell);
					}

					firstRow = firstRow + 1;

				}

			} else {

				int sizeOfApp = this.findMax(lstApproval.get(i));

				/**
				 * print App info
				 */

				// print App name
				cells.merge(firstRow, 1, sizeOfApp, 1, true);
				Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
				nameApp.setValue(lstApproval.get(i).getAppTypeName());

				// print start Date, end Date
				cells.merge(firstRow, 2, sizeOfApp, 1, true);
				Cell time = cells.get(firstRow, COLUMN_INDEX[2]);
				time.setValue(lstApproval.get(i).getStartDate() + "～" + lstApproval.get(i).getEndDate());

				// set style
				for (int k = 0; k < sizeOfApp; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
					setTitleStyle(sName);

					Cell sTime = cells.get(firstRow + k, COLUMN_INDEX[2]);
					setTitleStyle(sTime);
				}

				// print each Phase info
				for (int j = 0; j < lstApproval.get(i).getLstApproval().size(); j++) {

					int numberOfPhase = lstApproval.get(i).getLstApproval().size();
					for (int x = 3; x <= 11; x = x + 2) {
						this.printPhase(worksheets, cells, x, firstRow, sizeOfApp, lstApproval, i, j, numberOfPhase);
						j = j + 1;
						// this.printPhase1(cells, x, firstRow, sizeOfApp, lstApproval, i, j,
						// numberOfPhase);
					}

				}
				firstRow = firstRow + lstApproval.get(i).getLstApproval().get(0).getPersonName().size();
			}

		}

	}

	private void printPhase(WorksheetCollection worksheets, Cells cells, int indexCol, int firstRow, int sizeOfApp,
			List<ApprovalForApplication> lstApproval, int i, int j, int numberOfPhase) {
		Worksheet worksheet = worksheets.get(0);
		if (j < numberOfPhase) {
			int oldRow = firstRow;
			int numberOfPage = (firstRow + sizeOfApp) / 52;

			int numberOfRowMerge = (52 * numberOfPage) - firstRow;
			// print phase Name 1 mergered
			if (numberOfRowMerge > 0) {

				// in ra phase 1
				cells.merge(firstRow, indexCol, numberOfRowMerge, 1, true);
				Cell phaseName = cells.get(firstRow, COLUMN_INDEX[indexCol]);
				phaseName.setValue(lstApproval.get(i).getLstApproval().get(j).getApprovalForm());
				// set style
				for (int k = 0; k < numberOfRowMerge; k++) {
					Cell sPhaseName = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
					setTitleStyle(sPhaseName);
				}

				for (int em = 0; em < numberOfRowMerge; em++) {
					// phase 1
					Cell personName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					personName.setValue(
							lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em).substring(33, 35));
					// set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}

				oldRow = oldRow + numberOfRowMerge + 1;
				// in ra sô dòng còn lại
				// in ra name của phase
				cells.merge(oldRow, indexCol, sizeOfApp - numberOfRowMerge, 1, true);
				Cell phaseName1 = cells.get(firstRow, COLUMN_INDEX[indexCol]);
				phaseName1.setValue(lstApproval.get(i).getLstApproval().get(j).getApprovalForm());

				for (int k = 0; k < sizeOfApp - numberOfRowMerge; k++) {
					Cell sPhaseName = cells.get(oldRow + k, COLUMN_INDEX[indexCol]);
					setTitleStyle(sPhaseName);
				}

				// in ra các personName còn lại trong phase sau khi ngắt trang

				if (lstApproval.get(i).getLstApproval().get(j).getPersonName().size() < sizeOfApp) {

					for (int em = numberOfRowMerge; em < lstApproval.get(i).getLstApproval().get(j).getPersonName()
							.size(); em++) {
						// phase 1
						Cell personName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						personName.setValue(
								lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em).substring(33, 35));
						// set style
						Cell sPName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						setTitleStyle(sPName);
					}

					for (int em = lstApproval.get(i).getLstApproval().get(j).getPersonName()
							.size(); em < sizeOfApp; em++) {
						// set style
						Cell sPName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						setTitleStyle(sPName);
					}

				} else {
					for (int em = numberOfRowMerge; em < sizeOfApp - numberOfRowMerge; em++) {
						// phase 1
						Cell personName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						personName.setValue(
								lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em).substring(33, 35));
						// set style
						Cell sPName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						setTitleStyle(sPName);
					}
				}
				j = j + 1;

			} else {

				cells.merge(firstRow, indexCol, sizeOfApp, 1, true);
				Cell phaseName = cells.get(firstRow, COLUMN_INDEX[indexCol]);
				phaseName.setValue(lstApproval.get(i).getLstApproval().get(j).getApprovalForm());
				// set style
				for (int k = 0; k < sizeOfApp; k++) {
					Cell sPhaseName = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
					setTitleStyle(sPhaseName);
				}

				for (int em = 0; em < lstApproval.get(i).getLstApproval().get(j).getPersonName().size(); em++) {
					// phase 1
					Cell personName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					personName.setValue(
							lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em).substring(33, 35));
					// set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}
				if (lstApproval.get(i).getLstApproval().get(j).getPersonName().size() < 5) {
					for (int em = lstApproval.get(i).getLstApproval().get(j).getPersonName().size(); em <= 5; em++) {
						// phase 1 set style
						Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
						setTitleStyle(sPName);
					}

				}

			}
			if(j == 4) {
				firstRow = firstRow + sizeOfApp;
			}
			j = j + 1;
			
		} else if (numberOfPhase <= j && j <= 5) {

			cells.merge(firstRow, indexCol, sizeOfApp, 1, true);
			// set style
			for (int k = 0; k < sizeOfApp; k++) {
				Cell sPhaseName1 = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
				setTitleStyle(sPhaseName1);
			}

			for (int em = 0; em < sizeOfApp; em++) {

				// set style
				Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
				setTitleStyle(sPName);
			}
			
			if(j == 4) {
				firstRow = firstRow + sizeOfApp;
			}
			j = j + 1;

		}
	}

	private void printPhase1(Cells cells, int indexCol, int firstRow, int sizeOfApp,
			List<ApprovalForApplication> lstApproval, int i, int j, int numberOfPhase) {
		if (indexCol == 3) {

			// print phase Name 1
			cells.merge(firstRow, indexCol, sizeOfApp, 1, true);
			Cell phaseName = cells.get(firstRow, COLUMN_INDEX[indexCol]);
			phaseName.setValue(lstApproval.get(i).getLstApproval().get(j).getApprovalForm());

			// set style
			for (int k = 0; k < sizeOfApp; k++) {
				Cell sPhaseName = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
				setTitleStyle(sPhaseName);
			}

			for (int em = 0; em < lstApproval.get(i).getLstApproval().get(j).getPersonName().size(); em++) {
				// phase 1
				Cell personName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
				personName
						.setValue(lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em).substring(33, 35));
				// set style
				Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
				setTitleStyle(sPName);
			}
			if (lstApproval.get(i).getLstApproval().get(j).getPersonName().size() < numberOfPhase) {
				for (int em = lstApproval.get(i).getLstApproval().get(j).getPersonName()
						.size(); em < numberOfPhase; em++) {
					// phase 1 set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}

			}
			j = j + 1;

		} else {

			// print phase Name 5
			if (j < numberOfPhase) {
				cells.merge(firstRow, indexCol, sizeOfApp, 1, true);
				Cell phaseName5 = cells.get(firstRow, COLUMN_INDEX[indexCol]);
				phaseName5.setValue(lstApproval.get(i).getLstApproval().get(j).getApprovalForm());

				// set style
				for (int k = 0; k < sizeOfApp; k++) {
					Cell sPhaseName1 = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
					setTitleStyle(sPhaseName1);
				}

				for (int em = 0; em < lstApproval.get(i).getLstApproval().get(j).getPersonName().size(); em++) {
					// phase 5
					Cell personName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					personName.setValue(
							lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em).substring(33, 35));

					// set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}
				if (lstApproval.get(i).getLstApproval().get(j).getPersonName().size() < numberOfPhase) {
					for (int em = lstApproval.get(i).getLstApproval().get(j).getPersonName()
							.size(); em < numberOfPhase; em++) {
						// phase 1 set style
						Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
						setTitleStyle(sPName);
					}

				}
				j = j + 1;
			} else if (numberOfPhase <= j && j <= 5) {

				cells.merge(firstRow, indexCol, sizeOfApp, 1, true);
				// set style
				for (int k = 0; k < sizeOfApp; k++) {
					Cell sPhaseName1 = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
					setTitleStyle(sPhaseName1);
				}

				for (int em = 0; em < sizeOfApp; em++) {

					// set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}
				j = j + 1;

			}
		}
	}

}
