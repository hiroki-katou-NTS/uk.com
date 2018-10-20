package nts.uk.file.com.infra.generate;

import java.util.ArrayList;
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

import approve.employee.EmployeeApproverDataSource;
import approve.employee.EmployeeApproverRootOutputGenerator;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.adapter.workplace.WorkplaceImport;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.ApproverAsAppInfor;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpApproverAsApp;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmpOrderApproverAsApp;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeApproverOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.registerapproval.AppTypes;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;
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
	 * Sets the title style for the mergerd row .
	 *
	 * @param cell
	 *            the new title style
	 */
	private void setTitleStyleMerge(Cell cell) {
		Style style = cell.getStyle();
		style.setPattern(BackgroundType.SOLID);
		style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getGray());
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
		Map<String, WpApproverAsAppOutput> lstWkpAppr = dataSource.getWpApprover();
		if (lstWkpAppr.size() == 0) {
			throw new BusinessException("Msg_7");
		}
		int firstRow = 1;
		List<WorkplaceImport> lstWpInfor = dataSource.getLstWpInfor();
		for (WorkplaceImport wp : lstWpInfor) {
			WpApproverAsAppOutput workplace = lstWkpAppr.get(wp.getWkpId());
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
		Map<String, EmpApproverAsApp> employee = workplace.getMapEmpRootInfo();
		List<EmployeeApproverOutput> lstEmployeeInfo = workplace.getLstEmployeeInfo();
		// set "【職場】"
		Cell workPlace = cells.get(firstRow, COLUMN_INDEX[1]);
		workPlace.setValue("【職場】");

		// set worplace Name, workplace Code
		Cell workPlaceCode = cells.get(firstRow, COLUMN_INDEX[2]);
		workPlaceCode.setValue(wpInfor.getWkpCode() + " " + wpInfor.getWkpName());

		// tăng rowIndex lên 1 theo template
		firstRow = firstRow + 1;
		// in ra các employee trong cùng một workplace
		for (EmployeeApproverOutput empInfo : lstEmployeeInfo) {
			EmpApproverAsApp output = employee.get(empInfo.getEmpId());
			int maxRow = 0;
			int totalMerger = 0;
			Map<AppTypes, List<ApproverAsAppInfor>> mapAppType = output.getMapAppType();
			List<AppTypes> lstAppTypes = output.getLstAppTypes();
			for (AppTypes appTypes : lstAppTypes) {//in theo list app type da sap xep
				// LIST CÁC FORM CỦA MỘT NGƯỜI
				List<ApproverAsAppInfor> appLst = mapAppType.get(appTypes);
				// TÍNH MAX ĐỂ MERGER CELL
				if (appLst != null || !CollectionUtil.isEmpty(appLst)) {
					maxRow = this.findMax(appLst);
				}
				totalMerger = totalMerger + maxRow;
			}
			int pages = (firstRow + totalMerger -1) / 51;
			firstRow = this.printEmployee(worksheets, totalMerger, cells, firstRow, output, false, pages, lstAppTypes);
		}
		return firstRow;
	}

	/*
	 * find number of person
	 */

	private int findMax(List<ApproverAsAppInfor> approval) {
		int max = 1;
		for (ApproverAsAppInfor appRoot : approval) {
			if (appRoot.getLstEmpInfo() != null && !CollectionUtil.isEmpty(appRoot.getLstEmpInfo())) {
				if (max < appRoot.getLstEmpInfo().size()) {
					max = appRoot.getLstEmpInfo().size();
				}
			}
		}
		return max;
	}

	/**
	 * print 1 employee (list App)
	 * @param worksheets
	 * @param totalMerger
	 * @param cells
	 * @param firstRow
	 * @param output
	 * @param isBreak
	 * @param pages
	 * @return
	 */
	private int printEmployee(WorksheetCollection worksheets, int totalMerger, Cells cells, int firstRow,
			EmpApproverAsApp output, boolean isBreak, int pages, List<AppTypes> lstAppTypes) {

		// bắt đầu của employee
		pages = (firstRow + totalMerger -1) / 51;
		int rowMergered = (51 * pages) - firstRow + 1;
		if (rowMergered > 0) {//TH sang co ngat trang
			int pagesOld = (firstRow - 1) / 51 + 1;
			//TH phan trang tren 1 lan: 
			if(pages - pagesOld >= 1 && rowMergered >= 51){
				List<Integer> lstRowMered = new ArrayList<>();
				int rowNew = firstRow;
				for(int p = pagesOld; p <= pages; p++){//phan trang
					HorizontalPageBreakCollection hPageBreaks = worksheets.get(0).getHorizontalPageBreaks();
					hPageBreaks.add("P" + (51 * p + 2));
					VerticalPageBreakCollection vPageBreaks = worksheets.get(0).getVerticalPageBreaks();
					vPageBreaks.add("P" + (51 * p + 2));
					int rowMergered1 = (51 * p) - rowNew + 1;
					// set employee code - CỘT 1
					if (rowMergered1 > 1) {
						cells.merge(rowNew, 1, rowMergered1, 1, true);
					}
					Cell em_Code = cells.get(rowNew, COLUMN_INDEX[1]);
					em_Code.setValue(output.getEmployeeInfor().getEmpCD());
					
					// set employee name - CỘT 2
					if (rowMergered1 > 1) {
						cells.merge(rowNew, 2, rowMergered1, 1, true);
					}

					Cell em_Name = cells.get(rowNew, COLUMN_INDEX[2]);
					em_Name.setValue(output.getEmployeeInfor().getEName());
					
					rowNew = rowNew + rowMergered1;
					lstRowMered.add(rowNew);
				}
				for (int i = 0; i < totalMerger; i++) {
					Cell style_Code = cells.get(firstRow + i, COLUMN_INDEX[1]);
					Cell style_Name = cells.get(firstRow + i, COLUMN_INDEX[2]);
					//them RIGHT_BORDER
					if (i < (totalMerger -1)) {
							setTitleStyleMerge(style_Code);
							setTitleStyleMerge(style_Name);
					}
					
					//them BOTTOM_BORDER (TH ket thuc trang ma chua het nv)
					if ((i == (rowMergered - 1)) || (i == (totalMerger -1)) || lstRowMered.contains(firstRow + i + 1))
					{
						setTitleStyle(style_Code);
						setTitleStyle(style_Name);
					}
					//merge row(column 1 & 2) + set value (column 1 & 2) : TH sang trang moi ma chua het nv cu
					if(i == rowMergered || lstRowMered.contains(i)){
						cells.merge(firstRow + i, 1, (totalMerger - i), 1, true);
						Cell em_Code1 = cells.get(firstRow + i, COLUMN_INDEX[1]);
						cells.merge(firstRow + i, 2, (totalMerger - i), 1, true);
						Cell em_Name1 = cells.get(firstRow + i, COLUMN_INDEX[2]);
						em_Code1.setValue(output.getEmployeeInfor().getEmpCD());
						em_Name1.setValue(output.getEmployeeInfor().getEName());
					}
				}
				//TODO
				// IN RA TỪ CỘT THỨ 3 TRỞ RA
				for (AppTypes appTypes : lstAppTypes) {//in theo list app type da sap xep
					// LIST CÁC FORM CỦA MỘT NGƯỜI
					List<ApproverAsAppInfor> appLst = output.getMapAppType().get(appTypes);
					// TÍNH MAX ĐỂ MERGER CELL
					int max = this.findMax(appLst);
					int p = (firstRow -1) / 51 + 1;
					firstRow = this.printColumns3(cells, max, appTypes, firstRow, appLst, max > (p * 51 + 1 - firstRow) ? (p * 51 + 1 - firstRow) : 0);
				}
			}else{//TH phan trang 1 lan
				HorizontalPageBreakCollection hPageBreaks = worksheets.get(0).getHorizontalPageBreaks();
				hPageBreaks.add("P" + (51 * pages + 2));
				VerticalPageBreakCollection vPageBreaks = worksheets.get(0).getVerticalPageBreaks();
				vPageBreaks.add("P" + (51 * pages + 2));
				// set employee code - CỘT 1
				if (rowMergered > 1) {
					//TH: sang trang
					cells.merge(firstRow, 1, rowMergered, 1, true);
				}
	
				Cell em_Code = cells.get(firstRow, COLUMN_INDEX[1]);
				em_Code.setValue(output.getEmployeeInfor().getEmpCD());
	
				// set employee name - CỘT 2
				if (rowMergered > 1) {
					cells.merge(firstRow, 2, rowMergered, 1, true);
				}
	
				Cell em_Name = cells.get(firstRow, COLUMN_INDEX[2]);
				em_Name.setValue(output.getEmployeeInfor().getEName());
	
				for (int i = 0; i < totalMerger; i++) {
					Cell style_Code = cells.get(firstRow + i, COLUMN_INDEX[1]);
					Cell style_Name = cells.get(firstRow + i, COLUMN_INDEX[2]);
					//them RIGHT_BORDER
					if (i < (totalMerger -1)) {
							setTitleStyleMerge(style_Code);
							setTitleStyleMerge(style_Name);
					}
					//them BOTTOM_BORDER (TH ket thuc trang ma chua het nv)
					if ((i == (rowMergered - 1)) || (i == (totalMerger -1)))
					{
						setTitleStyle(style_Code);
						setTitleStyle(style_Name);
					}
					//merge row(column 1 & 2) + set value (column 1 & 2) : TH sang trang moi ma chua het nv cu
					if(i == rowMergered){
						cells.merge(firstRow + i, 1, (totalMerger - rowMergered), 1, true);
						Cell em_Code1 = cells.get(firstRow + i, COLUMN_INDEX[1]);
						cells.merge(firstRow + i, 2, (totalMerger - rowMergered), 1, true);
						Cell em_Name1 = cells.get(firstRow + i, COLUMN_INDEX[2]);
						em_Code1.setValue(output.getEmployeeInfor().getEmpCD());
						em_Name1.setValue(output.getEmployeeInfor().getEName());
					}
				}
	
				// IN RA TỪ CỘT THỨ 3 TRỞ RA
				for (AppTypes appTypes : lstAppTypes) {//in theo list app type da sap xep
					// LIST CÁC FORM CỦA MỘT NGƯỜI
					List<ApproverAsAppInfor> appLst = output.getMapAppType().get(appTypes);
					// TÍNH MAX ĐỂ MERGER CELL
					int max = this.findMax(appLst);
					int p = (firstRow -1) / 51 + 1;
					firstRow = this.printColumns3(cells, max, appTypes, firstRow, appLst, max > (p * 51 + 1 - firstRow) ? (p * 51 + 1 - firstRow) : 0);
				}
			}
		} 
		else {//TH khong ngat trang

			// set employee code - CỘT 1
			if (totalMerger > 1) {
				cells.merge(firstRow, 1, totalMerger, 1, true);
			}

			Cell em_Code = cells.get(firstRow, COLUMN_INDEX[1]);
			em_Code.setValue(output.getEmployeeInfor().getEmpCD());

			// set employee name - CỘT 2
			if (totalMerger > 1) {
				cells.merge(firstRow, 2, totalMerger, 1, true);
			}

			Cell em_Name = cells.get(firstRow, COLUMN_INDEX[2]);
			em_Name.setValue(output.getEmployeeInfor().getEName());

			for (int i = 0; i < totalMerger; i++) {
				Cell style_Code = cells.get(firstRow + i, COLUMN_INDEX[1]);
				Cell style_Name = cells.get(firstRow + i, COLUMN_INDEX[2]);
				if (i == (totalMerger - 1)) {
					setTitleStyle(style_Code);
					setTitleStyle(style_Name);
				} else {
					setTitleStyleMerge(style_Code);
					setTitleStyleMerge(style_Name);
				}
			}

			// IN RA TỪ CỘT THỨ 3 TRỞ RA
			for (AppTypes appTypes : lstAppTypes) {//in theo list app type da sap xep
				// LIST CÁC FORM CỦA MỘT NGƯỜI
				List<ApproverAsAppInfor> appLst = output.getMapAppType().get(appTypes);
				// TÍNH MAX ĐỂ MERGER CELL
				int max = this.findMax(appLst);
				firstRow = this.printColumns3(cells, max, appTypes, firstRow, appLst,  0);
			}
		}
		return firstRow;
	}

	/**
	 * in column 3 -> 14 theo tung don
	 * @param cells
	 * @param max
	 * @param m1
	 * @param firstRow
	 * @param appLst
	 * @param rowMergered
	 * @return
	 */
	private int printColumns3(Cells cells, int max, AppTypes typeApp, int firstRow,
			List<ApproverAsAppInfor> appLst, int rowMergered) {
		if (rowMergered > 0) {//TH BREAK PAGE
			// IN RA CỘT THỨ 3
			if (rowMergered > 1) {
				cells.merge(firstRow, 3, rowMergered, 1, true);
			}

			Cell em_Form = cells.get(firstRow, COLUMN_INDEX[3]);
			String appName = "";
			if (typeApp.getEmpRoot() == EmploymentRootAtr.COMMON.value){
				appName = "共通";
			} else if(typeApp.getEmpRoot() == EmploymentRootAtr.APPLICATION.value){
				appName = EnumAdaptor.valueOf(typeApp.getCode(), ApplicationType.class).nameId;
			}
			else if(typeApp.getEmpRoot() == EmploymentRootAtr.CONFIRMATION.value){
				appName = EnumAdaptor.valueOf(typeApp.getCode(), ConfirmationRootType.class).nameId;
			}
			em_Form.setValue(appName);
			// SET STYLE CHO CỘT THỨ 3
			for (int i = 0; i < max; i++) {
				Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[3]);
				//them RIGHT_BORDER
				if (i < max) {
						setTitleStyleMerge(style_Form);
				}
				//them BOTTOM_BORDER (TH ket thuc trang ma chua het app)
				if ((i == (rowMergered - 1)))
				{
					setTitleStyle(style_Form);
				}
				//merge row(column 3) + set value (column 3) : TH sang trang moi ma chua het a cu
				if(i == rowMergered){
					cells.merge(firstRow + i, 3, (max - rowMergered), 1, true);
					Cell app_name = cells.get(firstRow + i, COLUMN_INDEX[3]);
					app_name.setValue(appName);
				}
			}

			// TÌM RA SỐ DÒNG MAX ĐỂ MERGE CELL CHO CỘT 4
			int j = 4;

			// IN RA CỘT THỨ 4 CỦA PHRASE I
			for (int k = 1; k <= 5; k++) {//in 5 phase
				//find phase k
				ApproverAsAppInfor app = this.findPhaseByOder(appLst, k);
				//print phase k
				this.printEachPhrase(cells, firstRow, max, app, j, rowMergered);
				//tang column index 2 don vi (chuyen sang vi tri phase ke tiep)
				if ((j + 2) < 14) {
					j = j + 2;
				}
			}

			//column 14
			String text14 = this.stateColumn14(typeApp.getErr());
			if (max > 1) {
				cells.merge(firstRow, 14, max, 1);
			}
			Cell notice = cells.get(firstRow, COLUMN_INDEX[14]);
			notice.setValue(text14);
			for (int i = 0; i < max; i++) {
				Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[14]);
				if (i == (max - 1)) {
					setTitleStyle(style_Form);
				} else {
					setTitleStyleMerge(style_Form);
				}
			}
			firstRow = firstRow + max;
		} 
		else {//TH khong ngat trang

			// IN RA CỘT THỨ 3
			if (max > 1) {
				cells.merge(firstRow, COLUMN_INDEX[3], max, 1, true);
			}
			Cell em_Form = cells.get(firstRow, COLUMN_INDEX[3]);
			String appName1 = "";
			if (typeApp.getEmpRoot() == EmploymentRootAtr.COMMON.value){
				appName1 = "共通";
			} else if(typeApp.getEmpRoot() == EmploymentRootAtr.APPLICATION.value){
				appName1 = EnumAdaptor.valueOf(typeApp.getCode(), ApplicationType.class).nameId;
			}
			else if(typeApp.getEmpRoot() == EmploymentRootAtr.CONFIRMATION.value){
				appName1 = EnumAdaptor.valueOf(typeApp.getCode(), ConfirmationRootType.class).nameId;
			}
			
			em_Form.setValue(appName1);
			// SET STYLE CHO CỘT THỨ 3
			for (int i = 0; i < max; i++) {
				Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[3]);
				if (i == (max - 1)) {
					setTitleStyle(style_Form);
				} else {
					setTitleStyleMerge(style_Form);
				}
			}
				// TÌM RA SỐ DÒNG MAX ĐỂ MERGE CELL CHO CỘT 3, 4

				int j = 4;

				// đoạn này xử lý có bao nhiêu phrase để set style
				if (appLst.size() < 5) {
					// IN RA CỘT THỨ 4 CỦA PHRASE I
					for (int k = 1; k<= 5; k++) {
						ApproverAsAppInfor app = this.findPhaseByOder(appLst, k);
							this.printEachPhrase(cells, firstRow, max, app, j, 0);
							if ((j + 2) < 14) {
								j = j + 2;
							}


					}

					if (max > 1) {
						cells.merge(firstRow, 14, max, 1);
					}
					String text14 = this.stateColumn14(typeApp.getErr());
					Cell notice = cells.get(firstRow, COLUMN_INDEX[14]);
					notice.setValue(text14);
					for (int i = 0; i < max; i++) {
						Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[14]);
						if (i == (max - 1)) {
							setTitleStyle(style_Form);
						} else {
							setTitleStyleMerge(style_Form);
						}
					}
					firstRow = firstRow + max;
				} else {
					// IN RA CỘT THỨ 4 CỦA PHRASE I
					for (int k = 1; k<= 5; k++) {
						ApproverAsAppInfor app = this.findPhaseByOder(appLst, k);

							this.printEachPhrase(cells, firstRow, max, app, j, 0);
							if ((j + 2) < 14) {
								j = j + 2;
							}

					}
					//column 14
					if (max > 1) {
						cells.merge(firstRow, 14, max, 1);
					}
					Cell notice = cells.get(firstRow, COLUMN_INDEX[14]);
					notice.setValue("");
					for (int i = 0; i < max; i++) {
						Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[14]);
						if (i == (max - 1)) {
							setTitleStyle(style_Form);
						} else {
							setTitleStyleMerge(style_Form);
						}
					}
					firstRow = firstRow + max;

				}

		}
		return firstRow;
	}

	private String stateColumn14(ErrorFlag err){
		if(err == null){
			return "マスタなし";
		}
		if(err.equals(ErrorFlag.NO_APPROVER)){
			return "承認者なし";
		}
		if(err.equals(ErrorFlag.NO_ERROR)){
			return "";
		}
		if(err.equals(ErrorFlag.NO_CONFIRM_PERSON)){
			return "確定者なし ";
		}
		if(err.equals(ErrorFlag.APPROVER_UP_10)){
			return "承認者10人以上";
		}
		return "マスタなし";
	}
	private ApproverAsAppInfor findPhaseByOder(List<ApproverAsAppInfor> lstApp, int order){
		for (ApproverAsAppInfor app : lstApp) {
			if(app.getPhaseNumber() == order){
				return app;
			}
		}
		return new ApproverAsAppInfor(0, "", new ArrayList<>());
	}
	
	//in tung phase
	private void printEachPhrase(Cells cells, int firstRow, int max, ApproverAsAppInfor app, int j, int rowMergered) {
		if (app != null) {
			//Check phan trang
			if(rowMergered > 0 && max > 1){//TH phan trang (column ApprovalForm)
				//trang cu
				if (rowMergered > 1) {
					cells.merge(firstRow, j, rowMergered, 1, true);
				}
				Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[j]);
				if(app.getLstEmpInfo() != null && app.getLstEmpInfo().size() > 0){
					appPhrase.setValue(app.getApprovalForm());
				}else{
					appPhrase.setValue("");
				}
				// SET STYLE CHO CỘT THỨ ApprovalForm
				for (int i = 0; i < max; i++) {
					Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[j]);
					//them RIGHT_BORDER
					if (i < max) {
							setTitleStyleMerge(style_Form);
					}
					//them BOTTOM_BORDER (TH ket thuc trang ma chua het app)
					if ((i == (rowMergered - 1)))
					{
						setTitleStyle(style_Form);
					}
					//merge row(column ApprovalForm) + set value (column ApprovalForm) : TH sang trang moi ma chua het a cu
					if(i == rowMergered){
						cells.merge(firstRow + i, j, (max - rowMergered), 1, true);
						Cell app_name = cells.get(firstRow + i, COLUMN_INDEX[j]);
						app_name.setValue(app.getApprovalForm());
					}
				}
			}else{//TH khong phan trang
				if (max > 1) {
					cells.merge(firstRow, j, max, 1, true);
				}
				Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[j]);
				if(app.getLstEmpInfo() != null && app.getLstEmpInfo().size() > 0){
					appPhrase.setValue(app.getApprovalForm());
				}else{
					appPhrase.setValue("");
				}
				
				// SET STYLE
				for (int i = 0; i < max; i++) {
					Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[j]);
					if (i == (max - 1)) {
						setTitleStyle(style_Form);
					} else {
						setTitleStyleMerge(style_Form);
					}
				}
			}

			// IN RA CỘT THỨ 5 CỦA PHRASE I
			if (app.getLstEmpInfo() != null && app.getLstEmpInfo().size() > 0) {
				//TH: list approver = 0
				List<EmpOrderApproverAsApp> employeelst = app.getLstEmpInfo();
				int i = 0;
				if(employeelst.size() == 0){
					for (i = 0; i < max; i++) {
						Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[j + 1]);
						em_name.setValue("");
						setTitleStyle(em_name);;

					}
				}else{
					for (EmpOrderApproverAsApp eName : employeelst) {
						Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[j + 1]);
						em_name.setValue(eName.getEmployeeName());
						setTitleStyle(em_name);
						i++;

					}
					if(employeelst.size() < max){
						for (i = employeelst.size(); i < max; i++) {
							Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[j + 1]);
							em_name.setValue("");
							setTitleStyle(em_name);;

						}
					}
				}
				
			} else {
				if (max > 1) {
					for (int i = 0; i < max; i++) {
						Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[j + 1]);
						em_name.setValue("");
						setTitleStyle(em_name);

					}

				} else {
					Cell em_name = cells.get(firstRow, COLUMN_INDEX[j + 1]);
					em_name.setValue("");
					setTitleStyle(em_name);

				}

			}

		} else {

			if (max > 1) {
				cells.merge(firstRow, j, max, 1, true);
			}
			Cell appPhrase = cells.get(firstRow, COLUMN_INDEX[j]);
			appPhrase.setValue("");
			// SET STYLE
			for (int i = 0; i < max; i++) {
				Cell style_Form = cells.get(firstRow + i, COLUMN_INDEX[j]);
				if (i == (max - 1)) {
					setTitleStyle(style_Form);
				} else {
					setTitleStyleMerge(style_Form);
				}
			}
			// IN RA CỘT THỨ 5 CỦA PHRASE I{
			if (max > 1) {
				for (int i = 0; i < max; i++) {
					Cell em_name = cells.get(firstRow + i, COLUMN_INDEX[j + 1]);
					em_name.setValue("");
					setTitleStyle(em_name);

				}

			} else {
				Cell em_name = cells.get(firstRow, COLUMN_INDEX[j + 1]);
				em_name.setValue("");
				setTitleStyle(em_name);

			}

		}

	}

}
