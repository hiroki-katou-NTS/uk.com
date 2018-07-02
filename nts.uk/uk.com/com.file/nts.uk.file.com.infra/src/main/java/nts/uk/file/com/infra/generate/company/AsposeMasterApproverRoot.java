package nts.uk.file.com.infra.generate.company;

import java.util.List;
import java.util.Map;

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
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalForApplication;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.PersonApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WorkplaceApproverOutput;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputDataSource;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeMasterApproverRoot extends AsposeCellsReportGenerator implements MasterApproverRootOutputGenerator {
	private static final String TEMPLATE_FILE = "report/マスタリストのEXCEL出力.xlsx";

	private static final String REPORT_FILE_NAME = "マスタリストのEXCEL出力.xlsx";

	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };

	private static final int numberRowOfPage = 45;

	private static final int maxRowOfApp = 5;

	private static final int numberOfColumns = 13;
	@Override
	public void generate(FileGeneratorContext generatorContext, MasterApproverRootOutputDataSource dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();

			if (dataSource.isCheckCompany()) {//print sheet company
				if (dataSource.getMasterApproverRootOutput().getCompanyRootInfor() != null) {
					Worksheet worksheet = worksheets.get(0);
					// set up page prepare print
					this.printPage(worksheet, dataSource);
					this.printCompanyOfApproval(worksheets, dataSource);
				}
			} else {
				worksheets.get(0).setVisible(false);
			}

			if (dataSource.isCheckWorplace()) {//print sheet workplace
				if (!dataSource.getMasterApproverRootOutput().getWorplaceRootInfor().isEmpty()) {

					Worksheet workplaceSheet = worksheets.get(1);
					this.printWorkPlacePage(workplaceSheet, dataSource);
					this.printWorkplace(worksheets, dataSource);
				}
			} else {
				worksheets.get(1).setVisible(false);
			}

			if (dataSource.isCheckPerson()) {//print sheet person
				if (!dataSource.getMasterApproverRootOutput().getPersonRootInfor().isEmpty()) {
					Worksheet personSheet = worksheets.get(2);
					//header
					this.printPersonPage(personSheet, dataSource);
					//body
					this.printPerson(worksheets, dataSource);
				}
			} else {
				worksheets.get(2).setVisible(false);
			}

			designer.getDesigner().setWorkbook(workbook);
			designer.processDesigner();

			designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * PRINT PAGE COMPANY
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPage(Worksheet worksheet, MasterApproverRootOutputDataSource dataSource) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:N");
		pageSetup.setHeader(0, "【会社】 " + dataSource.getHeader().getNameCompany());
	}

	/**
	 * PRINT PAGE WORKPLACE
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printWorkPlacePage(Worksheet worksheet, MasterApproverRootOutputDataSource dataSource) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:N");
		pageSetup.setHeader(0, "【会社】 " + dataSource.getHeader().getNameCompany());
	}

	/**
	 * PRINT PAGE PERSON
	 * 
	 * @param worksheet
	 * @param lstDeparmentInf
	 */
	private void printPersonPage(Worksheet worksheet, MasterApproverRootOutputDataSource dataSource) {
		// Set print page
		PageSetup pageSetup = worksheet.getPageSetup();
		pageSetup.setFirstPageNumber(1);
		pageSetup.setPrintArea("A1:N");
		pageSetup.setHeader(0, "【会社】 " + dataSource.getHeader().getNameCompany());
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
		int max = 1;
		for (ApprovalRootMaster appRoot : approval.getLstApproval()) {
			int maxName = appRoot.getPersonName().size();
			if (max < maxName) {
				max = maxName;
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
			if (lstApproval.get(i).getLstApproval() == null
					|| CollectionUtil.isEmpty(lstApproval.get(i).getLstApproval())) {

				int numberOfPage = (firstRow + 1) / numberRowOfPage;

				int numberOfRowMerge = (numberRowOfPage * numberOfPage) - firstRow;
				if (numberOfRowMerge > 0) {
					// print App name
					Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
					nameApp.setValue(lstApproval.get(i).getAppTypeName());
					for (int index = 1; index < numberOfColumns; index++) {
						Cell oddCell = cells.get(firstRow, COLUMN_INDEX[index]);
						setTitleStyle(oddCell);
					}

					firstRow = firstRow + 1;
					HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
					hPageBreaks.add("N" + firstRow);
					VerticalPageBreakCollection vPageBreaks = worksheet.getVerticalPageBreaks();
					vPageBreaks.add("N" + firstRow);
				} else {

					// print App name
					Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
					nameApp.setValue(lstApproval.get(i).getAppTypeName());
					for (int index = 1; index < numberOfColumns; index++) {
						Cell oddCell = cells.get(firstRow, COLUMN_INDEX[index]);
						setTitleStyle(oddCell);
					}

					firstRow = firstRow + 1;

				}

			} else {

				int sizeOfApp = this.findMax(lstApproval.get(i));

				int numberOfPage = (firstRow + sizeOfApp) / numberRowOfPage;

				int numberOfRowMerge = (numberRowOfPage * numberOfPage) - firstRow;
				// print phase Name 1 mergered
				if (numberOfRowMerge > 0) {
					/**
					 * print App info
					 */

					// print App name
					cells.merge(firstRow, 1, numberOfRowMerge, 1, true);
					Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
					nameApp.setValue(lstApproval.get(i).getAppTypeName());

					// print start Date, end Date
					cells.merge(firstRow, 2, numberOfRowMerge, 1, true);
					Cell time = cells.get(firstRow, COLUMN_INDEX[2]);
					time.setValue(lstApproval.get(i).getStartDate() + "～" + lstApproval.get(i).getEndDate());

					// set style
					for (int k = 0; k < numberOfRowMerge; k++) {
						Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
						setTitleStyle(sName);

						Cell sTime = cells.get(firstRow + k, COLUMN_INDEX[2]);
						setTitleStyle(sTime);
					}

					int oldRow = firstRow;
					oldRow = oldRow + numberOfRowMerge + 1;
					if (sizeOfApp - numberOfRowMerge > 0) {
						// print App name
						cells.merge(oldRow, 1, sizeOfApp - numberOfRowMerge, 1, true);
						Cell nameAppMeger = cells.get(firstRow, COLUMN_INDEX[1]);
						nameAppMeger.setValue(lstApproval.get(i).getAppTypeName());

						// print start Date, end Date
						cells.merge(oldRow, 2, sizeOfApp - numberOfRowMerge, 1, true);
						Cell timeMerger = cells.get(firstRow, COLUMN_INDEX[2]);
						timeMerger.setValue(lstApproval.get(i).getStartDate() + "～" + lstApproval.get(i).getEndDate());

						// set style
						for (int k = 0; k < sizeOfApp - numberOfRowMerge; k++) {
							Cell sName = cells.get(oldRow + k, COLUMN_INDEX[1]);
							setTitleStyle(sName);

							Cell sTime = cells.get(oldRow + k, COLUMN_INDEX[2]);
							setTitleStyle(sTime);
						}

						// print each Phase info
						for (int j = 0; j < lstApproval.get(i).getLstApproval().size(); j++) {

							int numberOfPhase = lstApproval.get(i).getLstApproval().size();
							for (int x = 3; x <= 11; x = x + 2) {
								this.printPhaseOfCompany(worksheets, cells, x, firstRow, sizeOfApp, lstApproval, i, j,
										numberOfPhase);
								j = j + 1;
							}

						}
						firstRow = firstRow + lstApproval.get(i).getLstApproval().get(0).getPersonName().size();

					}

				} else {

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

				}

				// print each Phase info
				for (int j = 0; j < lstApproval.get(i).getLstApproval().size(); j++) {

					int numberOfPhase = lstApproval.get(i).getLstApproval().size();
					for (int x = 3; x <= 11; x = x + 2) {
						this.printPhaseOfCompany(worksheets, cells, x, firstRow, sizeOfApp, lstApproval, i, j,
								numberOfPhase);
						j = j + 1;
					}

				}
				firstRow = firstRow + lstApproval.get(i).getLstApproval().get(0).getPersonName().size();
			}

		}

	}

	private void printPhaseOfCompany(WorksheetCollection worksheets, Cells cells, int indexCol, int firstRow,
			int sizeOfApp, List<ApprovalForApplication> lstApproval, int i, int j, int numberOfPhase) {
		Worksheet worksheet = worksheets.get(0);
		if (j < numberOfPhase) {
			int oldRow = firstRow;
			int numberOfPage = (firstRow + sizeOfApp) / numberRowOfPage;

			int numberOfRowMerge = (numberRowOfPage * numberOfPage) - firstRow;
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
					personName.setValue(lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em));
					setTitleStyle(personName);
				}

				for (int index = 1; index < numberOfColumns; index++) {
					Cell oddCell = cells.get((firstRow + numberOfRowMerge), COLUMN_INDEX[index]);
					setTitleStyle(oddCell);
				}

				oldRow = oldRow + numberOfRowMerge + 1;

				// ngat trang
				if (j == 4) {
					HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
					hPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));
					VerticalPageBreakCollection vPageBreaks = worksheet.getVerticalPageBreaks();
					vPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));
				}

				// in ra sô dòng còn lại
				// in ra name của phase
				if (sizeOfApp - numberOfRowMerge > 0) {
					cells.merge(oldRow, indexCol, sizeOfApp - numberOfRowMerge, 1, true);
					Cell phaseName1 = cells.get(firstRow, COLUMN_INDEX[indexCol]);
					phaseName1.setValue(lstApproval.get(i).getLstApproval().get(j).getApprovalForm());

					for (int k = 0; k < sizeOfApp - numberOfRowMerge; k++) {
						Cell sPhaseName = cells.get(oldRow + k, COLUMN_INDEX[indexCol]);
						setTitleStyle(sPhaseName);
					}
				}

				// in ra các personName còn lại trong phase sau khi ngắt trang

				if (lstApproval.get(i).getLstApproval().get(j).getPersonName().size() < sizeOfApp) {

					for (int em = numberOfRowMerge; em < lstApproval.get(i).getLstApproval().get(j).getPersonName()
							.size(); em++) {
						// phase 1
						Cell personName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						personName.setValue(lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em));
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

					for (int em = numberOfRowMerge; em < sizeOfApp; em++) {
						// phase 1
						Cell personName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
						personName.setValue(lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em));
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
					personName.setValue(lstApproval.get(i).getLstApproval().get(j).getPersonName().get(em));
					// set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}
				if (lstApproval.get(i).getLstApproval().get(j).getPersonName().size() < maxRowOfApp) {
					for (int em = lstApproval.get(i).getLstApproval().get(j).getPersonName()
							.size(); em <= maxRowOfApp; em++) {
						// phase 1 set style
						Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
						setTitleStyle(sPName);
					}

				}

			}
			if (j == 4) {
				firstRow = firstRow + sizeOfApp;
			}
			j = j + 1;

		} else if (numberOfPhase <= j && j <= maxRowOfApp) {

			int numberOfPage = (firstRow + sizeOfApp) / numberRowOfPage;

			int numberOfRowMerge = (numberRowOfPage * numberOfPage) - firstRow;
			// print phase Name 1 mergered
			if (numberOfRowMerge > 0) {
				int oldRow = firstRow;

				// in ra nhung thang dc merger < numberRowOfPage*x
				cells.merge(firstRow, indexCol, numberOfRowMerge, 1, true);
				// set style
				for (int k = 0; k < numberOfRowMerge; k++) {
					Cell sPhaseName1 = cells.get(firstRow + k, COLUMN_INDEX[indexCol]);
					setTitleStyle(sPhaseName1);
				}

				for (int em = 0; em < numberOfRowMerge; em++) {

					// set style
					Cell sPName = cells.get(firstRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}

				oldRow = oldRow + numberOfRowMerge + 1;

				if (j == 4) {
					HorizontalPageBreakCollection hPageBreaks = worksheet.getHorizontalPageBreaks();
					hPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));
					VerticalPageBreakCollection vPageBreaks = worksheet.getVerticalPageBreaks();
					vPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));
				}

				// in ra sô dòng còn lại
				// in ra name của phase
				if (sizeOfApp - numberOfRowMerge > 0) {
					cells.merge(oldRow, indexCol, sizeOfApp - numberOfRowMerge, 1, true);
					for (int k = 0; k < sizeOfApp - numberOfRowMerge; k++) {
						Cell sPhaseName = cells.get(oldRow + k, COLUMN_INDEX[indexCol]);
						setTitleStyle(sPhaseName);
					}
				}

				for (int em = numberOfRowMerge; em < sizeOfApp; em++) {
					// set style
					Cell sPName = cells.get(oldRow + em, COLUMN_INDEX[indexCol + 1]);
					setTitleStyle(sPName);
				}
			} else {
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

				if (j == 4) {

					firstRow = firstRow + sizeOfApp;
				}
				j = j + 1;

			}

		} else {
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

			if (j == 4) {

				firstRow = firstRow + sizeOfApp;
			}
			j = j + 1;

		}
	}

	/**
	 * PRINT ALL PERSON
	 * 
	 * @param worksheets
	 * @param dataSource
	 */
	private void printPerson(WorksheetCollection worksheets, MasterApproverRootOutputDataSource dataSource) {
		Worksheet worksheet = worksheets.get(2);
		Cells cells = worksheet.getCells();
		Map<String, PersonApproverOutput> lstPerson = dataSource.getMasterApproverRootOutput().getPersonRootInfor();
		if (lstPerson.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Person list is empty"));

		}

		int firstRow = 3;
		for (Map.Entry m : lstPerson.entrySet()) {
			PersonApproverOutput person = (PersonApproverOutput) m.getValue();
			//print tung emp
			firstRow = this.printEachPerson(worksheets, cells, firstRow, person);

		}

	}

	/**
	 * PRINT EACH WORKPLACE
	 * print by person
	 * @param worksheets
	 * @param cells
	 * @param firstRow
	 * @param person
	 * @return
	 */
	private int printEachPerson(WorksheetCollection worksheets, Cells cells, int firstRow,
			PersonApproverOutput person) {

		EmployeeApproverOutput wpInfor = person.getEmployeeInfo();
		List<ApprovalForApplication> lstRootPs = person.getPsRootInfo();

		// set "【職場】"
		Cell workPlace = cells.get(firstRow, COLUMN_INDEX[1]);
		workPlace.setValue("【個人】");

		// set worplace Name, workplace Code
		Cell workPlaceCode = cells.get(firstRow, COLUMN_INDEX[2]);
		workPlaceCode.setValue(wpInfor.getEId() + " " + wpInfor.getEName());
		//TH row ten nv o cuoi trang
		int page = firstRow < 45 ? 1 : (firstRow-3)/42 + 1;
		int page_end = page * ca42 + 3;
		if(firstRow == page_end - 1){
			for(int nv = 1; nv <= 12; nv ++){
				Cell sName = cells.get(firstRow, COLUMN_INDEX[nv]);
				Style style = sName.getStyle();
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getGray());
				sName.setStyle(style);
			}
		}
		// tăng rowIndex lên 1
		firstRow = firstRow + 1;
		
		for (int i = 0; i < lstRootPs.size(); i++) {
			ApprovalForApplication app = lstRootPs.get(i);
			int sizeOfForm = this.findMax(app);
			//tinh index page
			int numberOfPage = (firstRow + sizeOfForm -3) / 42;
			//xd ngat trang ?
			int numberOfRowMerge = (42 * numberOfPage) - firstRow + 3;
			//TH khong ngat trang
			if (numberOfRowMerge <= 0 || sizeOfForm <= 1) {
				// in ra name app , time app
				cells.merge(firstRow, 1, sizeOfForm, 1, true);
				Cell appName = cells.get(firstRow, COLUMN_INDEX[1]);
				appName.setValue(app.getAppTypeName());

				cells.merge(firstRow, 2, sizeOfForm, 1, true);
				Cell timeName = cells.get(firstRow, COLUMN_INDEX[2]);
				timeName.setValue(app.getStartDate() + "~" + app.getEndDate());

				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
					setTitleStyle(sName);
					Cell sTimeName = cells.get(firstRow + k, COLUMN_INDEX[2]);
					setTitleStyle(sTimeName);
				}

				// in ra cac phase
				List<ApprovalRootMaster> lstPhase = lstRootPs.get(i).getLstApproval();
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfForm, lstPhase, false);
				firstRow = firstRow + sizeOfForm;
			} 
			//TH ngat trang
			else {//numberOfRowMerge >0
				//---set COLUMN 1
				//in trang cu
				cells.merge(firstRow, 1, numberOfRowMerge, 1, true);
				Cell a_name = cells.get(firstRow, COLUMN_INDEX[1]);
				a_name.setValue(app.getAppTypeName());
				//in trang moi
				cells.merge(firstRow + numberOfRowMerge, 1, (sizeOfForm - numberOfRowMerge), 1, true);
				Cell a_name2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[1]);
				a_name2.setValue(app.getAppTypeName());
				
				//---set COLUMN 2
				cells.merge(firstRow, 2, numberOfRowMerge, 1, true);
				Cell a_hist = cells.get(firstRow, COLUMN_INDEX[2]);
				a_hist.setValue(app.getStartDate() + "~" + app.getEndDate());
				//in trang moi
				cells.merge(firstRow + numberOfRowMerge, 2, (sizeOfForm - numberOfRowMerge), 1, true);
				Cell a_hist2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[2]);
				a_hist2.setValue(app.getStartDate() + "~" + app.getEndDate());
				
				// in ra cac phase
				List<ApprovalRootMaster> listPhase = lstRootPs.get(i).getLstApproval();
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfForm, listPhase, true);
				
				// nếu tổng số dòng trên một trang bị vượt qua > 42*x
				// thì sẽ ngắt trang
				HorizontalPageBreakCollection hPageBreaks = worksheets.get(1).getHorizontalPageBreaks();
				hPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));
				VerticalPageBreakCollection vPageBreaks = worksheets.get(1).getVerticalPageBreaks();
				vPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));

				// tăng chỉ số của row lên
				firstRow = firstRow + sizeOfForm;
			}
		}
		return firstRow;

	}

	/**
	 * PRINT ALL WORKPLACE
	 * 
	 * @param worksheets
	 * @param dataSource
	 */
	private void printWorkplace(WorksheetCollection worksheets, MasterApproverRootOutputDataSource dataSource) {
		Worksheet worksheet = worksheets.get(1);
		Cells cells = worksheet.getCells();
		Map<String, WorkplaceApproverOutput> lstWorkplace = dataSource.getMasterApproverRootOutput()
				.getWorplaceRootInfor();
		if (lstWorkplace.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Workplace list is empty"));
		}

		int firstRow = 3;
		for (Map.Entry m : lstWorkplace.entrySet()) {
			WorkplaceApproverOutput workplace = (WorkplaceApproverOutput) m.getValue();
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
			WorkplaceApproverOutput workplace) {

		WorkplaceImport wpInfor = workplace.getWpInfor();
		List<ApprovalForApplication> lstAppprove = workplace.getWpRootInfor();

		// set "【職場】"
		Cell workPlace = cells.get(firstRow, COLUMN_INDEX[1]);
		workPlace.setValue("【職場】");

		// set worplace Name, workplace Code
		Cell workPlaceCode = cells.get(firstRow, COLUMN_INDEX[2]);
		workPlaceCode.setValue(wpInfor.getWkpCode() + " " + wpInfor.getWkpName());

		// tăng rowIndex lên 1
		firstRow = firstRow + 1;

		for (int i = 0; i < lstAppprove.size(); i++) {
			ApprovalForApplication app = lstAppprove.get(i);
			int sizeOfForm = this.findMax(app);

			int numberOfPage = (firstRow + sizeOfForm) / numberRowOfPage;

			int numberOfRowMerge = (numberRowOfPage * numberOfPage) - firstRow;
			if (numberOfRowMerge <= 0) {

				// in ra name app , time app

				cells.merge(firstRow, 1, sizeOfForm, 1, true);
				Cell appName = cells.get(firstRow, COLUMN_INDEX[1]);
				appName.setValue(app.getAppTypeName());

				cells.merge(firstRow, 2, sizeOfForm, 1, true);
				Cell timeName = cells.get(firstRow, COLUMN_INDEX[2]);
				timeName.setValue(app.getStartDate() + "~" + app.getEndDate());

				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
					setTitleStyle(sName);
					Cell sTimeName = cells.get(firstRow + k, COLUMN_INDEX[2]);
					setTitleStyle(sTimeName);

				}

				// in ra cac phase

				List<ApprovalRootMaster> lstApp = lstAppprove.get(i).getLstApproval();
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfForm, lstApp, false);

				firstRow = firstRow + sizeOfForm;
			} else {

				// in ra name app , time app
				if (numberOfRowMerge > 1) {
					cells.merge(firstRow, 1, numberOfRowMerge, 1, true);
				}
				Cell appName = cells.get(firstRow, COLUMN_INDEX[1]);
				appName.setValue(app.getAppTypeName());

				if (numberOfRowMerge > 1) {
					cells.merge(firstRow, 2, numberOfRowMerge, 1, true);
				}
				Cell timeName = cells.get(firstRow, COLUMN_INDEX[2]);
				timeName.setValue(app.getStartDate() + "~" + app.getEndDate());

				for (int k = 0; k < numberOfRowMerge; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
					setTitleStyle(sName);
					Cell sTimeName = cells.get(firstRow + k, COLUMN_INDEX[2]);
					setTitleStyle(sTimeName);

				}

				// in ra cac phase

				List<ApprovalRootMaster> lstApp = lstAppprove.get(i).getLstApproval();
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfForm, lstApp, true);

				// ngắt trang
				HorizontalPageBreakCollection hPageBreaks = worksheets.get(1).getHorizontalPageBreaks();
				hPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));
				VerticalPageBreakCollection vPageBreaks = worksheets.get(1).getVerticalPageBreaks();
				vPageBreaks.add("N" + (firstRow + numberOfRowMerge + 1));

				// tăng chỉ số của row lên
				firstRow = firstRow + numberOfRowMerge;

				// in ra thành phần còn lại lstApp chưa đc in ra
				if (sizeOfForm - numberOfRowMerge > 0) {
					cells.merge(firstRow, 1, (sizeOfForm - numberOfRowMerge), 1, true);
					Cell appNameMergerd = cells.get(firstRow, COLUMN_INDEX[1]);
					appNameMergerd.setValue(app.getAppTypeName());

					cells.merge(firstRow, 2, (sizeOfForm - numberOfRowMerge), 1, true);
					Cell timeNameMerger = cells.get(firstRow, COLUMN_INDEX[2]);
					timeNameMerger.setValue(app.getStartDate() + "~" + app.getEndDate());

					for (int k = 0; k < (sizeOfForm - numberOfRowMerge); k++) {
						Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
						setTitleStyle(sName);
						Cell sTimeName = cells.get(firstRow + k, COLUMN_INDEX[2]);
						setTitleStyle(sTimeName);

					}

					// in ra số phase còn lai

					// luu gia tri numberOfRowMerge
					int oldRowMerge = numberOfRowMerge;
					for (int phase = 0; phase < lstApp.size(); phase++) {
						if (lstApp.get(phase).getPersonName().size() > numberOfRowMerge) {
							for (; oldRowMerge > 0; oldRowMerge--) {
								lstApp.get(phase).getPersonName().remove(0);
							}
						} else {
							lstApp.get(phase).getPersonName().clear();
						}
					}

					int numberOfNotMerger = (sizeOfForm - numberOfRowMerge);
					this.printPhaseOfApproval(worksheets, cells, firstRow, numberOfNotMerger, lstApp, false);
					firstRow = firstRow + 1;

				}

			}
		}
		return firstRow;

	}

	/**
	 * PRINT PHASE OF WORKPLACE
	 * 
	 * @param worksheets
	 * @param cells
	 * @param firstRow
	 * @param sizeOfForm
	 * @param lstApp
	 * @param isBreak
	 */
	private void printPhaseOfApproval(WorksheetCollection worksheets, Cells cells, int firstRow, int sizeOfForm,
			List<ApprovalRootMaster> lstPhase, boolean isBreak) {
		//tinh index page
		int numberOfPage = (firstRow + sizeOfForm -3) / 42;
		//xd ngat trang ?
		int numberOfRowMerge = (42 * numberOfPage) - firstRow + 3;
		if(!isBreak){//TH khong phan trang
			int colPhase = 3;
			for (int i = 1; i <= 5; i++) {//in tu phase 1-> 5
				//lay du lieu phase i
				ApprovalRootMaster phaseI = this.findPhasebyOrder(lstPhase, i);
				//xac dinh colume phase i
				colPhase = getColPhaseNumber(i);
				if(phaseI == null){//TH phase khong duoc setting
					if (sizeOfForm > 1) {//merge column 3,5,7,9,11 neu co
						cells.merge(firstRow, colPhase, sizeOfForm, 1, true);
						Cell formName = cells.get(firstRow, COLUMN_INDEX[colPhase]);
						setTitleStyle(formName);
					}
					continue;
				}
				//TH phase duoc setting
				//---set COLUMN 3 5 7 9 11
				if (sizeOfForm > 1) {
					cells.merge(firstRow, colPhase, sizeOfForm, 1, true);
				}
				Cell formName = cells.get(firstRow, COLUMN_INDEX[colPhase]);
				formName.setValue(phaseI.getApprovalForm());
				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[colPhase]);
					setTitleStyle(sName);
				}
				
				//---set COLUMN 4 6 8 10 12
				List<String> lstPerson = phaseI.getPersonName();
				if (lstPerson.size() < sizeOfForm) {//TH lstApprover < max row cua 1 root
					for (int j = 0; j < lstPerson.size(); j++) {//in lstApprover
						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);
					}
					for (int j = lstPerson.size(); j < sizeOfForm; j++) {//in nhung dong trong con lai
						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						setTitleStyle(perName);
					}
				} else if (lstPerson.size() == sizeOfForm) {//TH lstApprover = max row cua 1 root
					for (int j = 0; j < lstPerson.size(); j++) {
						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);
					}
				}
			}
			// fill border vao o trong con lai
			for (int curentCol = 0; curentCol < cells.getMaxColumn() - 1; curentCol++) {
				if (sizeOfForm > 1) {
					Cell value = cells.get(firstRow, COLUMN_INDEX[curentCol]);
					if (value == null) {
						cells.merge(firstRow, curentCol, sizeOfForm, 1, true);
					}
				}
				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[curentCol]);
					setTitleStyle(sName);
				}
				for (int j = 0; j < sizeOfForm; j++) {
					Cell perName = cells.get(firstRow + j, COLUMN_INDEX[curentCol + 1]);
					setTitleStyle(perName);
				}
			}
		}else{//TH phan trang
			int colPhase = 3;
			for (int i = 1; i <= 5; i++) {//in tu phase 1-> 5
				//lay du lieu phase i
				ApprovalRootMaster phaseI = this.findPhasebyOrder(lstPhase, i);
				//xac dinh colume phase i
				colPhase = getColPhaseNumber(i);
				if(phaseI == null){//TH phase khong duoc setting
					if (sizeOfForm > 1) {//merge column 3,5,7,9,11 neu co
						//trang cu
						cells.merge(firstRow, colPhase, numberOfRowMerge, 1, true);
						Cell appForm = cells.get(firstRow, COLUMN_INDEX[colPhase]);
						appForm.setValue("");
						//trang moi
						cells.merge(firstRow + numberOfRowMerge, colPhase, sizeOfForm - numberOfRowMerge, 1, true);
						Cell appForm2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[colPhase]);
						appForm2.setValue("");
					}
					continue;
				}
				//TH phase duoc setting
				//---set COLUMN 3 5 7 9 11 //TH phan trang (column ApprovalForm)
					//trang cu
					cells.merge(firstRow, colPhase, numberOfRowMerge, 1, true);
					Cell appForm = cells.get(firstRow, COLUMN_INDEX[colPhase]);
					appForm.setValue(phaseI.getApprovalForm());
					//trang moi
					cells.merge(firstRow + numberOfRowMerge, colPhase, sizeOfForm - numberOfRowMerge, 1, true);
					Cell appForm2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[colPhase]);
					appForm2.setValue(phaseI.getApprovalForm());
					
//				}
				
				//---set COLUMN 4 6 8 10 12
				List<String> lstPerson = phaseI.getPersonName();
				if(lstPerson.size() <= numberOfRowMerge){//chi in trang cu
					for (int x = 0; x < lstPerson.size(); x++) {
						Cell perName = cells.get(firstRow + x, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(x));
						setTitleStyle(perName);
					}
				}else{//in ca 2 trang
					//in trang cu
					for (int j = 0; j < numberOfRowMerge; j++) {//in lstApprover
						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);
					}
					for (int j = numberOfRowMerge; j < sizeOfForm; j++) {//in nhung dong trong con lai
						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						if(j > lstPerson.size()){
							perName.setValue("");
						}else{
							perName.setValue(lstPerson.get(j));
						}
						setTitleStyle(perName);
					}
				}
			}
			// fill border vao o trong con lai
			for (int curentCol = 0; curentCol < cells.getMaxColumn() - 1; curentCol++) {
				if (sizeOfForm > 1) {
					Cell value = cells.get(firstRow, COLUMN_INDEX[curentCol]);
					if (value == null) {
						cells.merge(firstRow, curentCol, sizeOfForm, 1, true);
					}
				}
				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[curentCol]);
					setTitleStyle(sName);
				}
				for (int j = 0; j < sizeOfForm; j++) {
					Cell perName = cells.get(firstRow + j, COLUMN_INDEX[curentCol + 1]);
					setTitleStyle(perName);
				}
			}
			
			
			
			
		}
	}

	private ApprovalRootMaster findPhasebyOrder(List<ApprovalRootMaster> lstPhase, int order){
		for (ApprovalRootMaster phase : lstPhase) {
			if(phase.getPhaseNumber() == order){
				return phase;
			}
		}
		return null;
	}
	private int getColPhaseNumber(int phaseNumber) {
		int colNumber = 0;
		switch (phaseNumber) {
		case 1:
			colNumber = 3;
			break;
		case 2:
			colNumber = 5;
			break;
		case 3:
			colNumber = 7;
			break;
		case 4:
			colNumber = 9;
			break;
		case 5:
			colNumber = 11;
			break;
		}
		return colNumber;
	}
}
