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

	private static final int numberRowOfPage = 52;

	private static final int maxRowOfApp = 5;

	private static final int numberOfColumns = 13;

	@Override
	public void generate(FileGeneratorContext generatorContext, MasterApproverRootOutputDataSource dataSource) {
		try (val reportContext = this.createContext(TEMPLATE_FILE)) {

			val designer = this.createContext(TEMPLATE_FILE);
			Workbook workbook = designer.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();

			if (dataSource.isCheckCompany()) {
				if (dataSource.getMasterApproverRootOutput().getCompanyRootInfor() != null) {
					Worksheet worksheet = worksheets.get(0);
					// set up page prepare print
					this.printPage(worksheet,dataSource);
					this.printCompanyOfApproval(worksheets, dataSource);
				}
			} else {
				worksheets.get(0).setVisible(false);
			}

			if (dataSource.isCheckWorplace()) {
				if (!dataSource.getMasterApproverRootOutput().getWorplaceRootInfor().isEmpty()) {

					Worksheet workplaceSheet = worksheets.get(1);
					this.printWorkPlacePage(workplaceSheet, dataSource);
					this.printWorkplace(worksheets, dataSource);
				}
			} else {
				worksheets.get(1).setVisible(false);
			}

			if (dataSource.isCheckPerson()) {
				if (!dataSource.getMasterApproverRootOutput().getPersonRootInfor().isEmpty()) {
					Worksheet personSheet = worksheets.get(2);
					this.printPersonPage(personSheet, dataSource);
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
			firstRow = this.printEachPerson(worksheets, cells, firstRow, person);

		}

	}

	/**
	 * PRINT EACH WORKPLACE
	 * 
	 * @param worksheets
	 * @param cells
	 * @param firstRow
	 * @param person
	 * @return
	 */
	private int printEachPerson(WorksheetCollection worksheets, Cells cells, int firstRow,
			PersonApproverOutput person) {

		EmployeeApproverOutput wpInfor = person.getEmployeeInfo();
		List<ApprovalForApplication> lstAppprove = person.getPsRootInfo();

		// set "【職場】"
		Cell workPlace = cells.get(firstRow, COLUMN_INDEX[1]);
		workPlace.setValue("【個人】");

		// set worplace Name, workplace Code
		Cell workPlaceCode = cells.get(firstRow, COLUMN_INDEX[2]);
		workPlaceCode.setValue(wpInfor.getEId() + " " + wpInfor.getEName());

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

				cells.merge(firstRow, 1, numberOfRowMerge, 1, true);
				Cell appName = cells.get(firstRow, COLUMN_INDEX[1]);
				appName.setValue(app.getAppTypeName());

				cells.merge(firstRow, 2, numberOfRowMerge, 1, true);
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
				this.printPhaseOfApproval(worksheets, cells, firstRow, numberOfRowMerge, lstApp, true);

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
				this.printPhaseOfApproval(worksheets, cells, firstRow, numberOfRowMerge, lstApp, true);

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
			List<ApprovalRootMaster> lstApp, boolean isBreak) {
		int numberOfPhase = lstApp.size();
		if (numberOfPhase < maxRowOfApp) {
			int colPhase = 3;
			for (int i = 0; i < numberOfPhase; i++) {
				ApprovalRootMaster appRootMaster = lstApp.get(i);
				// phase 1
				// phase Name
				if (sizeOfForm > 1) {
					cells.merge(firstRow, colPhase, sizeOfForm, 1, true);
				}
				Cell phaseName = cells.get(firstRow, COLUMN_INDEX[colPhase]);
				phaseName.setValue(appRootMaster.getApprovalForm());

				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[colPhase]);
					setTitleStyle(sName);
				}

				// content of phase
				List<String> lstPerson = appRootMaster.getPersonName();
				if (lstPerson.size() < maxRowOfApp) {
					for (int j = 0; j < lstPerson.size(); j++) {

						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);

					}

					for (int j = lstPerson.size(); j < sizeOfForm; j++) {

						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						setTitleStyle(perName);

					}

				} else if (lstPerson.size() == sizeOfForm) {
					for (int j = 0; j < lstPerson.size(); j++) {

						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);

					}

				}
				colPhase = colPhase + 2;
				if (i == (numberOfPhase - 1)) {
					for (int x = numberOfPhase; x < maxRowOfApp; x++) {
						if (sizeOfForm > 1) {
							cells.merge(firstRow, colPhase, sizeOfForm, 1, true);
						}
						for (int k = 0; k < sizeOfForm; k++) {
							Cell sName = cells.get(firstRow + k, COLUMN_INDEX[colPhase]);
							setTitleStyle(sName);
						}

						for (int j = 0; j < sizeOfForm; j++) {

							Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
							setTitleStyle(perName);

						}
						colPhase = colPhase + 2;

					}

				}
			}

		} else if (numberOfPhase >= maxRowOfApp) {
			int colPhase = 3;
			for (int i = 0; i < numberOfPhase; i++) {
				ApprovalRootMaster appRootMaster = lstApp.get(i);

				// phase 1
				// phase Name
				if (sizeOfForm > 1) {
					cells.merge(firstRow, colPhase, sizeOfForm, 1, true);
				}
				Cell phaseName = cells.get(firstRow, COLUMN_INDEX[colPhase]);
				phaseName.setValue(appRootMaster.getApprovalForm());

				for (int k = 0; k < sizeOfForm; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[colPhase]);
					setTitleStyle(sName);
				}

				// content of phase
				List<String> lstPerson = appRootMaster.getPersonName();
				if (lstPerson.size() < sizeOfForm) {
					for (int j = 0; j < lstPerson.size(); j++) {

						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);

					}

					for (int j = lstPerson.size(); j < sizeOfForm; j++) {

						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						setTitleStyle(perName);

					}

				} else if (lstPerson.size() >= sizeOfForm) {
					for (int j = 0; j < sizeOfForm; j++) {

						Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
						perName.setValue(lstPerson.get(j));
						setTitleStyle(perName);

					}
					if (isBreak = false) {
						if (lstPerson.size() <= maxRowOfApp) {
							for (int j = sizeOfForm; j < lstPerson.size(); j++) {

								Cell perName = cells.get(firstRow + j, COLUMN_INDEX[colPhase + 1]);
								perName.setValue(lstPerson.get(j));
								setTitleStyle(perName);

							}
						}
					}

				}
				colPhase = colPhase + 2;
			}
		}

	}

}
