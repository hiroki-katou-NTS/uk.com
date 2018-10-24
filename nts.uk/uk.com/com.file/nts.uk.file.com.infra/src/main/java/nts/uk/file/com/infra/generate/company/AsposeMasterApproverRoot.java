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
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalForApplication;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApprovalRootMaster;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterEmployeeOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterWkpOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.PersonApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WorkplaceApproverOutput;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputDataSource;
import nts.uk.file.com.app.company.approval.MasterApproverRootOutputGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
/**
 * print CMM018 - M
 * @author hoatt
 *
 */
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
			//print sheet COMPANY
			if (dataSource.isCheckCompany()) {
				if (dataSource.getMasterApproverRootOutput().getComRootInfor() != null) {
					Worksheet worksheet = worksheets.get(0);
					// set up page prepare print
					this.printPage(worksheet, dataSource);
					this.printCompanyOfApproval(worksheets, dataSource);
				}
			} else {
				worksheets.get(0).setVisible(false);
			}
			//print sheet WORKPLACE
			if (dataSource.isCheckWorplace()) {
				if (!dataSource.getMasterApproverRootOutput().getWkpRootOutput().getWorplaceRootInfor().isEmpty()) {

					Worksheet workplaceSheet = worksheets.get(1);
					this.printPage(workplaceSheet, dataSource);
					this.printWorkplace(worksheets, dataSource.getMasterApproverRootOutput().getWkpRootOutput());
				}
			} else {
				worksheets.get(1).setVisible(false);
			}
			//print sheet PERSON
			if (dataSource.isCheckPerson()) {
				if (!dataSource.getMasterApproverRootOutput().getEmpRootOutput().getPersonRootInfor().isEmpty()) {
					Worksheet personSheet = worksheets.get(2);
					//header
					this.printPage(personSheet, dataSource);
					//body
					this.printPerson(worksheets, dataSource.getMasterApproverRootOutput().getEmpRootOutput());
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
	 * PRINT PAGE HEADER
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
	/**
	 * PRINT COMPANY
	 * @param worksheets
	 * @param dataSource
	 */
	private void printCompanyOfApproval(WorksheetCollection worksheets, MasterApproverRootOutputDataSource dataSource) {
		Worksheet worksheet = worksheets.get(0);
		Cells cells = worksheet.getCells();
		List<ApprovalForApplication> lstRoot = dataSource.getMasterApproverRootOutput().getComRootInfor().getLstComs();
		int firstRow = 3;
		for (int i = 0; i < lstRoot.size(); i++) {//in tung root
			//find max row of 1 root
			int sizeOfApp = this.findMax(lstRoot.get(i));
			int numberOfPage = (firstRow + sizeOfApp - 3) / 42;
			int numberOfRowMerge = (42 * numberOfPage) - firstRow + 3;
			if (numberOfRowMerge > 0 && sizeOfApp > 1) {//TH phan trang
				ApprovalForApplication app = lstRoot.get(i);
				//---set COLUMN 1
				//in trang cu
				cells.merge(firstRow, 1, numberOfRowMerge, 1, true);
				Cell a_name = cells.get(firstRow, COLUMN_INDEX[1]);
				a_name.setValue(app.getAppTypeName());
				//in trang moi
				if(sizeOfApp > numberOfRowMerge){
					cells.merge(firstRow + numberOfRowMerge, 1, (sizeOfApp - numberOfRowMerge), 1, true);
					Cell a_name2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[1]);
					a_name2.setValue(app.getAppTypeName());
				}
				
				//---set COLUMN 2
				cells.merge(firstRow, 2, numberOfRowMerge, 1, true);
				Cell a_hist = cells.get(firstRow, COLUMN_INDEX[2]);
				a_hist.setValue(app.getStartDate() + "~" + app.getEndDate());
				//in trang moi
				if(sizeOfApp > numberOfRowMerge){
					cells.merge(firstRow + numberOfRowMerge, 2, (sizeOfApp - numberOfRowMerge), 1, true);
					Cell a_hist2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[2]);
					a_hist2.setValue(app.getStartDate() + "~" + app.getEndDate());
				}
				
				//in tung phase
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfApp, lstRoot.get(i).getLstApproval(), true);
				// nếu tổng số dòng trên một trang bị vượt qua > 42*x
				// thì sẽ ngắt trang
				HorizontalPageBreakCollection hPageBreaks = worksheets.get(0).getHorizontalPageBreaks();
				hPageBreaks.add("P" + (42 * numberOfPage + 4));
				VerticalPageBreakCollection vPageBreaks = worksheets.get(0).getVerticalPageBreaks();
				vPageBreaks.add("P" + (42 * numberOfPage + 4));

				// tăng chỉ số của row lên
				firstRow = firstRow + sizeOfApp;

			} else {//TH khong phan trang
					//---set COLUMN 1
				cells.merge(firstRow, 1, sizeOfApp, 1, true);
				Cell nameApp = cells.get(firstRow, COLUMN_INDEX[1]);
				nameApp.setValue(lstRoot.get(i).getAppTypeName());

				//---set COLUMN 2
				cells.merge(firstRow, 2, sizeOfApp, 1, true);
				Cell time = cells.get(firstRow, COLUMN_INDEX[2]);
				time.setValue(lstRoot.get(i).getStartDate() + "～" + lstRoot.get(i).getEndDate());

				// set style
				for (int k = 0; k < sizeOfApp; k++) {
					Cell sName = cells.get(firstRow + k, COLUMN_INDEX[1]);
					setTitleStyle(sName);

					Cell sTime = cells.get(firstRow + k, COLUMN_INDEX[2]);
					setTitleStyle(sTime);
				}
				//---in tung phase 
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfApp, lstRoot.get(i).getLstApproval(), false);
				firstRow = firstRow + sizeOfApp;
			}
		}
	}
	/**
	 * PRINT ALL PERSON
	 * 
	 * @param worksheets
	 * @param dataSource
	 */
	private void printPerson(WorksheetCollection worksheets, MasterEmployeeOutput dataPer) {
		Worksheet worksheet = worksheets.get(2);
		Cells cells = worksheet.getCells();
		List<EmployeeApproverOutput> lstEmployeeInfo = dataPer.getLstEmployeeInfo();
		Map<String, PersonApproverOutput> lstPerson = dataPer.getPersonRootInfor();
		if (lstPerson.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Person list is empty"));
		}
		int firstRow = 3;
		for (EmployeeApproverOutput emp : lstEmployeeInfo) {
			PersonApproverOutput person = lstPerson.get(emp.getEmpId());
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
		workPlaceCode.setValue(wpInfor.getEmpCD() + " " + wpInfor.getEName());
		//TH row ten nv o cuoi trang
		int page = firstRow < 45 ? 1 : (firstRow-3)/42 + 1;
		int page_end = page * 42 + 3;
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
				if(sizeOfForm > numberOfRowMerge){
					cells.merge(firstRow + numberOfRowMerge, 1, (sizeOfForm - numberOfRowMerge), 1, true);
					Cell a_name2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[1]);
					a_name2.setValue(app.getAppTypeName());
				}
				//---set COLUMN 2
				cells.merge(firstRow, 2, numberOfRowMerge, 1, true);
				Cell a_hist = cells.get(firstRow, COLUMN_INDEX[2]);
				a_hist.setValue(app.getStartDate() + "~" + app.getEndDate());
				//in trang moi
				if(sizeOfForm > numberOfRowMerge){
					cells.merge(firstRow + numberOfRowMerge, 2, (sizeOfForm - numberOfRowMerge), 1, true);
					Cell a_hist2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[2]);
					a_hist2.setValue(app.getStartDate() + "~" + app.getEndDate());
				}
				
				// in ra cac phase
				List<ApprovalRootMaster> listPhase = lstRootPs.get(i).getLstApproval();
				this.printPhaseOfApproval(worksheets, cells, firstRow, sizeOfForm, listPhase, true);
				
				// nếu tổng số dòng trên một trang bị vượt qua > 42*x
				// thì sẽ ngắt trang
				HorizontalPageBreakCollection hPageBreaks = worksheets.get(2).getHorizontalPageBreaks();
				hPageBreaks.add("P" + (42 * page + 4));
				VerticalPageBreakCollection vPageBreaks = worksheets.get(2).getVerticalPageBreaks();
				vPageBreaks.add("P" + (42 * page + 4));
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
	private void printWorkplace(WorksheetCollection worksheets, MasterWkpOutput dataWkp) {
		Worksheet worksheet = worksheets.get(1);
		Cells cells = worksheet.getCells();
		List<WorkplaceImport> lstWpInfor = dataWkp.getLstWpInfor();
		Map<String, WorkplaceApproverOutput> lstWorkplace = dataWkp.getWorplaceRootInfor();
		if (lstWorkplace.size() == 0) {
			throw new BusinessException(new RawErrorMessage("Workplace list is empty"));
		}
		int firstRow = 3;
		//for by list work place sorted
		for (WorkplaceImport wkp : lstWpInfor) {
			WorkplaceApproverOutput workplace = lstWorkplace.get(wkp.getWkpId());
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

			int numberOfPage = (firstRow + sizeOfForm - 3) / 42;

			int numberOfRowMerge = (42 * numberOfPage) - firstRow + 3;
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
				hPageBreaks.add("P" + (42 * numberOfPage + 4));
				VerticalPageBreakCollection vPageBreaks = worksheets.get(1).getVerticalPageBreaks();
				vPageBreaks.add("P" + (42 * numberOfPage + 4));

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
						if(sizeOfForm > numberOfRowMerge){
							cells.merge(firstRow + numberOfRowMerge, colPhase, sizeOfForm - numberOfRowMerge, 1, true);
							Cell appForm2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[colPhase]);
							appForm2.setValue("");
						}
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
				if(sizeOfForm > numberOfRowMerge){
					cells.merge(firstRow + numberOfRowMerge, colPhase, sizeOfForm - numberOfRowMerge, 1, true);
					Cell appForm2 = cells.get(firstRow + numberOfRowMerge, COLUMN_INDEX[colPhase]);
					appForm2.setValue(phaseI.getApprovalForm());
				}
				
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
