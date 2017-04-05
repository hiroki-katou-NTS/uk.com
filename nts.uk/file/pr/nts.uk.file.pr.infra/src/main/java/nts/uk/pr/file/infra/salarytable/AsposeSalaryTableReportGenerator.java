/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.salarytable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.ejb.Stateless;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.salarytable.SalaryTableReportGenerator;
import nts.uk.file.pr.app.export.salarytable.data.Denomination;
import nts.uk.file.pr.app.export.salarytable.data.DepartmentData;
import nts.uk.file.pr.app.export.salarytable.data.EmployeeData;
import nts.uk.file.pr.app.export.salarytable.data.SalaryTableDataSource;
import nts.uk.file.pr.app.export.salarytable.query.SalaryTableReportQuery;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeSalaryTableReportGenerator.
 */
@Stateless
public class AsposeSalaryTableReportGenerator extends AsposeCellsReportGenerator implements SalaryTableReportGenerator {

	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "SalaryTableReport.pdf";

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/SalaryTableTemplate.xlsx";

	/** The Constant FIRST_COLUMN_INDEX. */
	private static final int FIRST_COLUMN_INDEX = 0;

	/** The Constant LAST_COLUMN_INDEX. */
	private static final int LAST_COLUMN_INDEX = 12;

	/** The Constant COLUMN_INDEX. */
	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	/** The Constant FIRST_ROW_INDEX. */
	private static final int FIRST_ROW_INDEX = 9;
	
	/** The Constant BLANK_ROWS. */
	private static final int BLANK_ROWS = 2;
	
	/** The Constant ROWS_PER_PAGE. */
	private static final int ROWS_PER_PAGE = 37;

	/** The Constant SPACES. */
	private static final String SPACES = "    ";

	/** The Constant SPACES1. */
	private static final String SPACES1 = "                        ";
	
	/** The Constant LIGHT_BLUE_COLOR. */
	private static final Color LIGHT_BLUE_COLOR = Color.fromArgb(197, 241, 247);
	
	/** The Constant LIGHT_GREEN_COLOR. */
	private static final Color LIGHT_GREEN_COLOR = Color.fromArgb(199, 243, 145);
	
	/** The Constant NONE_BREAK_CODE. */
	private static final int NONE_BREAK_CODE = 1;
	
	/** The Constant EMPLOYEE_BREAK_CODE. */
	private static final int EMPLOYEE_BREAK_CODE = 2;
	
	/** The Constant DEPARTMENT_BREAK_CODE. */
	private static final int DEPARTMENT_BREAK_CODE = 3;
	
	/** The Constant HIERARCHY_BREAK_CODE. */
	private static final int HIERARCHY_BREAK_CODE = 4;
	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.screen.app.report.salarychart.SalaryChartReportGenerator#
	 * generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.ctx.pr.screen.app.report.salarychart.data.SalaryChartDataSource)
	 */
	@Override
	public void generate(FileGeneratorContext generatorContext, SalaryTableDataSource reportData,
			SalaryTableReportQuery query) {
		List<EmployeeData> empList = reportData.getEmployeeList();
		try {
			 AsposeCellsReportContext reportContext =
			 this.createContext(TEMPLATE_FILE);
			 Workbook workbook = reportContext.getWorkbook();

			// create worksheet and Formatting ...
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			Cells cells = worksheet.getCells();
			
			// Set header.
			DateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd hh:mm");
			worksheet.getPageSetup().setHeader(2,
					"&\"IPAPGothic\"&13 " + dateFormat.format(new Date()) + "\r\n&P ページ");
			reportContext.getDesigner().setDataSource("Header", reportData.getSalaryChartHeader());
			
			// Initial Variables
			int rowIndex = FIRST_ROW_INDEX;
			EmployeeData prevEmp = null;
			Stack<DepartmentData> depStack = new Stack<>();
			Map<Denomination, Long> tempDenomination = new HashMap<>();
			double tempAccumulate = 0;
			int members = 0;
			
			PrintProcess printProcess = new PrintProcess();
			printProcess.cells = cells;
			printProcess.query = query;
			printProcess.rowIndex = rowIndex;
			printProcess.depStack = depStack;
			printProcess.tempDenomination = tempDenomination;
			printProcess.tempAccumulate = tempAccumulate;
			printProcess.prevEmp = prevEmp;
			printProcess.members = members;
			
			// Fill Data
			this.printData(printProcess, empList);
			
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			reportContext.saveAsPdf(this.createNewFile(generatorContext, REPORT_FILE_NAME));
			
			// Save report as PDF file
			reportContext.getWorkbook().save("D:/SalaryTableReport.xlsx");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints the data.
	 *
	 * @param cells the cells
	 * @param empList the emp list
	 * @param query the query
	 * @param rowIndex the row index
	 * @param prevEmp the prev emp
	 * @param depStack the dep stack
	 */
	private void printData(PrintProcess printProcess, List<EmployeeData> empList) {
		// Temp Gross Vaviables
		Map<Denomination, Long> grossTotalDenomination = new HashMap<>();
		MutableDouble grossTotalAccumulate = new MutableDouble(0);
		MutableInt grossTotalEmp = new MutableInt(0);
		
		// Background Color of Row is White (or Green)
		MutableBoolean isGreenRow = new MutableBoolean(false);
		Iterator<EmployeeData> itr = empList.iterator();
		while (itr.hasNext()) {
			EmployeeData currentEmp = itr.next();
			printProcess.currentEmp = currentEmp;
			
			// Cumulate to Gross Total vars
			this.cumulateToAmount(grossTotalDenomination, grossTotalAccumulate, grossTotalEmp, currentEmp);
			
			if (printProcess.prevEmp == null) {
				// Print First Employee
				this.printFirstEmp(printProcess, isGreenRow);
				printProcess.prevEmp = currentEmp;
				continue;
			}
			String prevDepCode = printProcess.prevEmp.getDepartmentData().getDepCode();
			String currentDepCode = printProcess.currentEmp.getDepartmentData().getDepCode();
			
			if (prevDepCode.equals(currentDepCode)) {
				// Cumulate To Temp Variables and Print Employee
				this.printEmpSameDep(printProcess, isGreenRow);
				printProcess.prevEmp = currentEmp;
				continue;
			}
			if (!prevDepCode.equals(currentDepCode)) {
				// Push Temp DepartmentData To Stack and Reset Temp vars
				this.pushToStackAndReset(printProcess);
				// Cumulate to Temp vars
				this.cumulateToTemp(printProcess);

				// Print Sub Accumulated payment of Sub Department
				this.fillAccSubDep(printProcess);
				
				// Print Employee
				this.printEmpDifferentDep(printProcess, isGreenRow);
				printProcess.prevEmp = currentEmp;
			}
		}		
		// END BLOCK OF REPORT
		// Fill Total Of Department
		this.printTotalOfDep(printProcess);

		// Fill Accumulated Payment
		this.printAccAtTheEnd(printProcess);
		
		// Fill Gross Total
		this.printGrossTotal(printProcess, grossTotalEmp.getValue(), 
				grossTotalDenomination, grossTotalAccumulate.getValue());
	}
	
	
	/**
	 * Last cumulated deps.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param depStack the dep stack
	 * @param empDep the emp dep
	 */
	private void lastCumulatedDeps(PrintProcess printProcess){
		while (!printProcess.depStack.isEmpty()) {
			DepartmentData empDep = printProcess.prevEmp.getDepartmentData();
			String empDepPath = empDep.getDepPath();
			DepartmentData topInStack = printProcess.depStack.pop();
			// Cumulate printed Denomination into Stack
			if (!empDepPath.startsWith(topInStack.getDepPath())) {
				// print Last Accumulated DepartmentData
				this.printAccByHierarchy(printProcess, topInStack);
			}
			// if(topInStack.getDepPath().equals(empDepPath)){
			// //this.calculateDenomination(prevDepFromStack, topInStack);
			// this.printAccumulated(cells, rowIndex, topInStack);
			// }
			else {
				// Calculate Accumulation
				this.calculateDenomination(empDep, topInStack);
				this.printAccByHierarchy(printProcess, topInStack);
			}
			//empDep = topInStack;
			printProcess.prevEmp.setDepartmentData(topInStack);
		}
	}
	
	/**
	 * Prints the if same dep.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param currentEmp the current emp
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param members the members
	 * @param isGreen the is green
	 * @return the employee data
	 */
	private void printEmpSameDep(PrintProcess printProcess, MutableBoolean isGreen) {
		SalaryTableReportQuery query = printProcess.query;
		// Breaking Page Code
		int breakCode = query.getSelectedBreakPageCode();

		if (breakCode == EMPLOYEE_BREAK_CODE && query.getIsPrintDetailItem()) {
			this.breakPageByOption(printProcess);
			this.printDepInfo(printProcess);
		}
		// Cumulate To Temp Variables
		this.cumulateToTemp(printProcess);

		// print currentEmp
		this.printEmployee(printProcess, isGreen);
	}
	
	/**
	 * Prints the if different dep.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param prevEmp the prev emp
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param members the members
	 * @param depStack the dep stack
	 * @return the map
	 */
	private void pushToStackAndReset(PrintProcess printProcess){
		
		// Create border line for last employee in a department
		this.createEndRowRange(printProcess);
		
		// Print Total In Department if choosen
		this.printTotalOfDep(printProcess);
		
		// Push DepartmentData to Stack
		this.pushDepData(printProcess);
		
		// Reset Temp Variable and continue Cumulate
		printProcess.tempAccumulate = 0;
		printProcess.members = 0;
		printProcess.tempDenomination = new HashMap<>();
	}
	
	/**
	 * Prints the emp different dep.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param currentEmp the current emp
	 * @param isGreen the is green
	 * @return the employee data
	 */
	private void printEmpDifferentDep(PrintProcess printProcess, MutableBoolean isGreen){
		SalaryTableReportQuery query = printProcess.query;
		// Breaking Page Code
		int breakCode = query.getSelectedBreakPageCode();
		
		// Check for Breaking page By Employee
		if(query.getIsPrintDetailItem() && 
				((breakCode == EMPLOYEE_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE))){
			this.breakPageByOption(printProcess);
		}
		// Print current Department Info
		this.printDepInfo(printProcess);
		
		// Print current Employee
		isGreen.setValue(false);
		this.printEmployee(printProcess, isGreen);
	}
	
	/**
	 * Fill acc sub dep.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param currentEmp the current emp
	 * @param prevEmp the prev emp
	 * @param depStack the dep stack
	 */
	private void fillAccSubDep(PrintProcess printProcess){
		
		// Department Path of Current Employee and Previous employee
		String currentDepPath = printProcess.currentEmp.getDepartmentData().getDepPath();
		String prevDepPath = printProcess.prevEmp.getDepartmentData().getDepPath();
		
		// Check if Current Department is a sub Department of Previous Department
		boolean isSubDep = currentDepPath.startsWith(prevDepPath);
		if (!isSubDep) {
			// Print Sub Accumulated...
			this.printSubAccumulated(printProcess);
		}
	}

	/**
	 * Fill acc at the end.
	 *
	 * @param query the query
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param prevEmp the prev emp
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param depStack the dep stack
	 */
	private void printAccAtTheEnd(PrintProcess printProcess) {
		// Print Accumulated Hierarchy of Department
		if (printProcess.query.getIsPrintDepHierarchy()) {
			DepartmentData empDep = printProcess.prevEmp.getDepartmentData();
			empDep.setDenomination(printProcess.tempDenomination);
			empDep.setAccumulatedPayment(printProcess.tempAccumulate);
			this.printAccByHierarchy(printProcess, empDep);
			this.lastCumulatedDeps(printProcess);
		}
	}
	
	/**
	 * Cumulate to temp.
	 *
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param members the members
	 * @param currentEmp the current emp
	 */
	private void cumulateToTemp(PrintProcess printProcess){
		EmployeeData currentEmp = printProcess.currentEmp;
		
		// Cumulate to temple variables
		this.cumulateDenomination(printProcess);
		printProcess.tempAccumulate += currentEmp.getPaymentAmount();
		printProcess.members ++;
	}
	private void cumulateToAmount(Map<Denomination, Long> tempDenomination, 
			MutableDouble tempAccumulate, MutableInt members, EmployeeData currentEmp){
		// Cumulate to temple variables
		this.cumulateGrossDenomination(tempDenomination, currentEmp.getDenomination());
		tempAccumulate.add(currentEmp.getPaymentAmount());
		members.increment();
	}
	
	/**
	 * Fill first emp.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param currentEmp the current emp
	 * @param isGreen the is green
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param members the members
	 * @return the employee data
	 */
	private void printFirstEmp(PrintProcess printProcess, MutableBoolean isGreen){
		// Print Title Row and Department Info
		this.createTitle(printProcess);
		this.printDepInfo(printProcess);
		 // Cumulate To Temp Variables
		this.cumulateToTemp(printProcess);
		this.printEmployee(printProcess, isGreen);
	}
	
/**
 * Switch color.
 *
 * @param isGreen the is green
 * @return the boolean
 */
	private Boolean switchColor(Boolean isGreen) {
		if (isGreen) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * None break page.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void noneBreakPage(PrintProcess printProcess) {
		int currentRow = printProcess.rowIndex;
		
		if (currentRow % ROWS_PER_PAGE == 0) {
			this.createEndRowRange(printProcess);
			//currentRow += BLANK_ROWS;
			printProcess.rowIndex += BLANK_ROWS;
			this.createTitle(printProcess);
		}
	}
	
	/**
	 * Break page border line.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void breakPageBorderLine(PrintProcess printProcess) {
		int currentRow = printProcess.rowIndex;
		
		// Create Double border for last row 
		if (currentRow % ROWS_PER_PAGE == 0) {
			this.createEndRowRange(printProcess);
		}
		// Create Double border for first row in next page
		if(currentRow % ROWS_PER_PAGE == 1){
			this.createStartRowRange(printProcess);
		}
	}
	
	/**
	 * Break page by option.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void breakPageByOption(PrintProcess printProcess){
		int currentRow = printProcess.rowIndex;
		if(currentRow % ROWS_PER_PAGE == 0){
			this.createTitle(printProcess);
		} else {
			// Create End Row Range
			this.createEndRowRange(printProcess);		
			int nextPage = currentRow / ROWS_PER_PAGE +1;
			int blankRowsCurrentPage = nextPage * ROWS_PER_PAGE +1 - currentRow;
			printProcess.rowIndex += (blankRowsCurrentPage + BLANK_ROWS);
			// Create Title for Next Page
			this.createTitle(printProcess);
		}
	}

	/**
	 * Creates the end row range.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void createEndRowRange(PrintProcess printProcess){
		Cells cells = printProcess.cells;
		int currentRow = printProcess.rowIndex;
		Range endRange = cells.createRange(currentRow - 1, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
		endRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.DOUBLE, Color.getBlack());
	}
	
	/**
	 * Creates the start row range.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void createStartRowRange(PrintProcess printProcess){
		Cells cells = printProcess.cells;
		int currentRow = printProcess.rowIndex;
		Range startRange = cells.createRange(currentRow - 1, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
		startRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
	}
	
	/**
	 * Creates the row range.
	 *
	 * @param cells the cells
	 * @param firstRow the first row
	 */
	private void createLeftRightBorder(PrintProcess printProcess) {
		Cells cells = printProcess.cells;
		int currentRow = printProcess.rowIndex;
		
		// Create Left and Right Border to Cell
		Range range = cells.createRange(currentRow, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
		range.setOutlineBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
		range.setOutlineBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
	}
	
	/**
	 * Prints the sub accumulated.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param depStack the dep stack
	 * @param currentDepLevel the current dep level
	 */
	private void printSubAccumulated(PrintProcess printProcess){
		// Peek Department Data from Stack
		DepartmentData depToPrint = printProcess.depStack.peek();
		String depToPrintPath = depToPrint.getDepPath();
		int depToPrintLevel = depToPrint.getDepLevel();
		int currentDepLevel = printProcess.currentEmp.getDepartmentData().getDepLevel();
		
		do { // Print Accumulated payment Of Department
			depToPrint = printProcess.depStack.pop();
			this.printAccByHierarchy(printProcess, depToPrint);
			
			if (!printProcess.depStack.isEmpty()) {
				DepartmentData topInStack = printProcess.depStack.pop();
				if (depToPrintPath.startsWith(topInStack.getDepPath())) {
					this.calculateDenomination(depToPrint, topInStack);
					printProcess.depStack.push(topInStack);
				}
				// Assign 
				depToPrintPath = topInStack.getDepPath();
				depToPrintLevel = topInStack.getDepLevel();
			}
		} while (!printProcess.depStack.isEmpty() && !(currentDepLevel > depToPrintLevel));
	}

	/**
	 * Push dep data.
	 *
	 * @param tempDenomination the temp denomination
	 * @param accumulatePayment the accumulate payment
	 * @param depStack the dep stack
	 * @param prevEmp the prev emp
	 */
	private void pushDepData(PrintProcess printProcess){
		// Set Denomination for Temp DepartmentData
		DepartmentData prevDep = printProcess.prevEmp.getDepartmentData();
		DepartmentData tempDepData = DepartmentData.builder().build();
		tempDepData.setDenomination(printProcess.tempDenomination);
		tempDepData.setAccumulatedPayment(printProcess.tempAccumulate);
		tempDepData.setDepLevel(prevDep.getDepLevel());
		tempDepData.setDepCode(prevDep.getDepCode());
		tempDepData.setDepName(prevDep.getDepName());
		tempDepData.setDepPath(prevDep.getDepPath());
		
		// Push DepartmentData to Stack
		printProcess.depStack.push(tempDepData);
	}
	
	/**
	 * Prints the dep info.
	 *
	 * @param empdata the empdata
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 */
	private void printDepInfo(PrintProcess printProcess) {
		EmployeeData currentEmp = printProcess.currentEmp;
		Cells cells = printProcess.cells;
		int rowIndex = printProcess.rowIndex;
		DepartmentData depData = currentEmp.getDepartmentData();
		Cell cell = cells.get(rowIndex, 0);
		cell.setValue("部門  : " + SPACES + depData.getDepPath() + SPACES + depData.getDepName());

		// Set Style for cells
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell1 = cells.get(rowIndex, i);
			StyleModel stylemodel = new StyleModel();
			stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
			stylemodel.borderType = CellsBorderType.TopBottomOnly;
			this.setCellStyle1(cell1, stylemodel);
			createLeftRightBorder(printProcess);	
		}
		printProcess.rowIndex ++;
		// Breaking Page
		int breakCode = printProcess.query.getSelectedBreakPageCode();
		if (breakCode == NONE_BREAK_CODE) {
			this.noneBreakPage(printProcess);
		}
		if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageBorderLine(printProcess);
		}
	}
	
	/**
	 * Prints the total of dep.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param denomination the denomination
	 * @param amount the amount
	 * @param members the members
	 * @param query the query
	 */
	private void printTotalOfDep(PrintProcess printProcess) {
		SalaryTableReportQuery query = printProcess.query;
		Cells cells = printProcess.cells;
		int rowIndex = printProcess.rowIndex;
		int members = printProcess.members;
		if (query.getIsPrintTotalOfDepartment()) {
			int breakCode = query.getSelectedBreakPageCode();
			
			// Fill Data to cells
			cells.get(rowIndex, COLUMN_INDEX[0]).setValue("部門計" + SPACES1 + members + "人");
			for (Denomination d : Denomination.values()) {
				int columnIndex = d.value + 1;
				cells.get(rowIndex, columnIndex).setValue(printProcess.tempDenomination.get(d) + "枚");
			}
			cells.get(rowIndex, COLUMN_INDEX[11]).setValue(printProcess.tempAccumulate);
			
			// Style for cells
			for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
				Cell cell = cells.get(rowIndex, i);
				StyleModel stylemodel = new StyleModel();
				stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
				stylemodel.borderType = CellsBorderType.DoubleTopBorder;
				this.setCellStyle1(cell, stylemodel);
				createLeftRightBorder(printProcess);
			}
			printProcess.rowIndex ++;
			
			// Breaking Page
			if (breakCode == NONE_BREAK_CODE) {
				this.noneBreakPage(printProcess);
			}
			if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)) {
				this.breakPageBorderLine(printProcess);
			}
		}

	}
	
	/**
	 * Checks if is printed or not.
	 *
	 * @param query the query
	 * @param depToPrint the dep to print
	 * @param cells the cells
	 * @param rowIndex the row index
	 */
	private void printAccByHierarchy(PrintProcess printProcess, DepartmentData depToPrint) {
		SalaryTableReportQuery query = printProcess.query;
		// Breaking Page Code
		int breakCode = query.getSelectedBreakPageCode();
		// Hierarchy Breaking Page code
		int hierarchyBreakCode = query.getSelectedBreakPageHierarchyCode();
		List<Integer> selectedLevels = query.getSelectedLevels();
		int depToPrintLevel = depToPrint.getDepLevel();
		for (int i : selectedLevels) {
			if (depToPrintLevel == i) {
				this.printAccumulated(printProcess, depToPrint);
				// Check for Breaking page by Hierarchy
				if ((breakCode == HIERARCHY_BREAK_CODE) && (hierarchyBreakCode == depToPrintLevel)) {
					this.breakPageByOption(printProcess);
				}
				break;
			}
		}
	}

	/**
	 * Prints the gross total.
	 *
	 * @param query the query
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param totalmembers the totalmembers
	 * @param denomination the denomination
	 * @param amount the amount
	 */
	private void printGrossTotal(PrintProcess printProcess, int totalmembers, 
			Map<Denomination, Long> denomination, double amount) {
		Cells cells = printProcess.cells;
		int rowIndex = printProcess.rowIndex;
		if (printProcess.query.getIsCalculateTotal()) {
			// Fill Data to cells
			cells.get(rowIndex, COLUMN_INDEX[0]).setValue("総合計" + SPACES1 + totalmembers + "人");
			for (Denomination d : Denomination.values()) {
				int columnIndex = d.value + 1;
				cells.get(rowIndex, columnIndex).setValue(denomination.get(d) + "枚");
			}
			cells.get(rowIndex, COLUMN_INDEX[11]).setValue(amount);
			// Set Style for Cells

			// Style for cells
			for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
				Cell cell = cells.get(rowIndex, i);
				StyleModel stylemodel = new StyleModel();
				stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
				stylemodel.borderType = CellsBorderType.DoubleTopBorder;
				this.setCellStyle1(cell, stylemodel);
				createLeftRightBorder(printProcess);
			}
		}
	}

	/**
	 * Prints the accumulated.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param depData the dep data
	 * @param query the query
	 */
	private void printAccumulated(PrintProcess printProcess, DepartmentData depToPrint) {
		int breakCode = printProcess.query.getSelectedBreakPageCode();
		Cells cells = printProcess.cells;
		int rowIndex = printProcess.rowIndex;
		cells.get(rowIndex, COLUMN_INDEX[0]).setValue(depToPrint.getDepName() + SPACES + depToPrint.getDepPath());
		for (Denomination d : Denomination.values()) {
			int columnIndex = d.value + 1;
			cells.get(rowIndex, columnIndex).setValue(depToPrint.getDenomination().get(d) + "枚");
		}
		cells.get(rowIndex, COLUMN_INDEX[11]).setValue(depToPrint.getAccumulatedPayment());
		// Style for cells
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell = cells.get(rowIndex, i);
			StyleModel stylemodel = new StyleModel();
			stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
			stylemodel.borderType = CellsBorderType.DoubleTopBorder;
			this.setCellStyle1(cell, stylemodel);
			createLeftRightBorder(printProcess);
		}
		printProcess.rowIndex ++;
		// Breaking Page
		if (breakCode == NONE_BREAK_CODE) {
			this.noneBreakPage(printProcess);
		}	
		if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageBorderLine(printProcess);
		}
	}

	/**
	 * Cumulate denomination.
	 *
	 * @param denomination the denomination
	 * @param currentEmpDeno the current emp deno
	 */
	private void cumulateGrossDenomination(Map<Denomination, Long> denomination, Map<Denomination, Long> currentEmpDeno) {		
		for (Denomination d : Denomination.values()) {
			if(denomination.get(d) == null){
				denomination.put(d, 0l);
			}
			long quantity = denomination.get(d) + currentEmpDeno.get(d);
			denomination.replace(d, quantity);
		}		
	}
	private void cumulateDenomination(PrintProcess printProcess) {
		Map<Denomination, Long> denomination = printProcess.tempDenomination;
		for (Denomination d : Denomination.values()) {
			if(denomination.get(d) == null){
				printProcess.tempDenomination.put(d, 0l);
			}
			Map<Denomination, Long> currentEmpDeno = printProcess.currentEmp.getDenomination();
			long quantity = denomination.get(d) + currentEmpDeno.get(d);
			printProcess.tempDenomination.replace(d, quantity);
		}		
	}

	/**
	 * Calculate denomination.
	 *
	 * @param fromDep the from dep
	 * @param toDep the to dep
	 * @return the department data
	 */
	private DepartmentData calculateDenomination(DepartmentData fromDep, DepartmentData toDep) {
		for (Denomination d : Denomination.values()) {
			long quantity = fromDep.getDenomination().get(d) + toDep.getDenomination().get(d);
			toDep.getDenomination().replace(d, quantity);
		}
		if(fromDep.getAccumulatedPayment() == null){
			fromDep.setAccumulatedPayment(0.0);
		}
		double accumulateAmount = fromDep.getAccumulatedPayment() + toDep.getAccumulatedPayment();
		toDep.setAccumulatedPayment(accumulateAmount);
		return toDep;
	}

	/**
	 * Prints the employee.
	 *
	 * @param empdata the empdata
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param isGreen the is green
	 * @param query the query
	 * @return the employee data
	 */
	private void printEmployee(PrintProcess printProcess, 
			MutableBoolean isGreen) {
		Cells cells = printProcess.cells;
		int rowIndex = printProcess.rowIndex;
		EmployeeData currentEmp = printProcess.currentEmp;
		// Print Employee 
		boolean isPrintEmp = printProcess.query.getIsPrintDetailItem();
		if (isPrintEmp) {
			cells.get(rowIndex, COLUMN_INDEX[0])
					.setValue(currentEmp.getEmpCode() + SPACES + currentEmp.getEmpName());
			Map<Denomination, Long> deno = currentEmp.getDenomination();
			for (Denomination d : Denomination.values()) {
				int columnIndex = d.value + 1;
				cells.get(rowIndex, columnIndex).setValue(deno.get(d) + "枚");
			}
			cells.get(rowIndex, COLUMN_INDEX[11]).setValue(currentEmp.getPaymentAmount());

			// Style for cells
			for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
				Cell cell = cells.get(rowIndex, i);
				// this.setEmpStyle(cell);
				StyleModel stylemodel = new StyleModel();
				stylemodel.borderType = CellsBorderType.CommonBorder;
				stylemodel.backgroundColor = isGreen.getValue() ? LIGHT_GREEN_COLOR : null;
				this.setCellStyle1(cell, stylemodel);
				createLeftRightBorder(printProcess);
			}
			isGreen.setValue(switchColor(isGreen.getValue()));
			printProcess.rowIndex ++;
		}

		// Breaking Page
		int breakCode = printProcess.query.getSelectedBreakPageCode();
		if (breakCode == NONE_BREAK_CODE) {
			this.noneBreakPage(printProcess);
		}
		if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageBorderLine(printProcess);
		}

	}

	/**
	 * Creates the title.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 */
	private void createTitle(PrintProcess printProcess) {
		Cells cells = printProcess.cells;
		int rowIndex = printProcess.rowIndex;
		// Set Header for Employee column
		Cell empCell = cells.get(rowIndex, COLUMN_INDEX[0]);
		empCell.setValue("社員");
		Style style = empCell.getStyle();
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		empCell.setStyle(style);

		for (Denomination deno : Denomination.values()) {
			int columnIndex = deno.value + 1;
			Cell denoCell = cells.get(rowIndex, columnIndex);
			denoCell.setValue(deno.description);
		}
		// Set Header for Amount Column
		Cell amountCell = cells.get(rowIndex, COLUMN_INDEX[11]);
		amountCell.setValue("金額");

		// Set Style for Header Row
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell = cells.get(rowIndex, i);
			this.setCellStyle1(cell, StyleModel.titleStyle());
		}
		printProcess.rowIndex ++;
		
	}

	
	/**
	 * The Enum CellsBorderType.
	 */
	private enum CellsBorderType{
		
		/** The Top bottom only. */
		TopBottomOnly,
		
		/** The Double top border. */
		DoubleTopBorder,
		
		/** The Medium border. */
		MediumBorder,
		
		/** The Common border. */
		CommonBorder,
	}
	
	/**
	 * The Class StyleModel.
	 */
	public static class StyleModel{
		
		/** The background color. */
		public Color backgroundColor;
		
		/** The border type. */
		public CellsBorderType borderType;
		
		/** The text alignment. */
		public int textAlignment;
		
		/**
		 * Title style.
		 *
		 * @return the style model
		 */
		public static StyleModel titleStyle() {
			StyleModel style = new StyleModel();
			style.backgroundColor = LIGHT_BLUE_COLOR;
			style.borderType = CellsBorderType.MediumBorder;
			style.textAlignment = TextAlignmentType.CENTER;
			return style;
		}
	}
	
	/**
	 * Sets the cell style 1.
	 *
	 * @param cell the cell
	 * @param styleModel the style model
	 */
	private void setCellStyle1(Cell cell, StyleModel styleModel){
		Style style = cell.getStyle();
		if (styleModel.backgroundColor != null) {
			style.setForegroundColor(styleModel.backgroundColor);
			style.setPattern(BackgroundType.SOLID);
		} 
		if (styleModel.textAlignment != 0) {
			style.setHorizontalAlignment(styleModel.textAlignment);
		}
		switch (styleModel.borderType) {
			case MediumBorder:
				style.setBorder(BorderType.TOP_BORDER, CellBorderType.MEDIUM, Color.getBlack());
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.MEDIUM, Color.getBlack());
				style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
				style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
				break;
			case TopBottomOnly:
				style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
				break;
			case DoubleTopBorder:
				style.setBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.DOUBLE, Color.getBlack());
				style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOTTED, Color.getBlack());
				break;
			case CommonBorder:
				style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getBlack());
				style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.NONE, Color.getBlack());
				style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOTTED, Color.getBlack());
				break;
			default:
				break;
		}
		cell.setStyle(style);
	}
	

	class PrintProcess{
		int rowIndex;
		Cells cells;
		SalaryTableReportQuery query;
		Stack<DepartmentData> depStack;
		EmployeeData currentEmp;
		EmployeeData prevEmp;
		Map<Denomination, Long> tempDenomination;
		double tempAccumulate; 
		int members;
	}
}