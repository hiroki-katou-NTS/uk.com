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
			MutableInt rowIndex = new MutableInt(FIRST_ROW_INDEX);
			EmployeeData prevEmp = null;
			Stack<DepartmentData> depStack = new Stack<>();
			
			// Fill Data
			this.printData(cells, empList, query, rowIndex, prevEmp, depStack);
			
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
	private void printData(Cells cells, List<EmployeeData> empList, SalaryTableReportQuery query,
			MutableInt rowIndex, EmployeeData prevEmp, Stack<DepartmentData> depStack) {
		// Temp Accumulation Variables
		Map<Denomination, Long> tempDenomination = new HashMap<>();
		MutableDouble tempAccumulate = new MutableDouble(0);
		MutableInt members = new MutableInt(0);
		
		// Temp Gross Vaviables
		Map<Denomination, Long> grossTotalDenomination = new HashMap<>();
		MutableDouble grossTotalAccumulate = new MutableDouble(0);
		MutableInt grossTotalEmp = new MutableInt(0);
		
		// Background Color of Row is White (or Green)
		MutableBoolean isGreenRow = new MutableBoolean(false);
		Iterator<EmployeeData> itr = empList.iterator();
		while (itr.hasNext()) {
			EmployeeData currentEmp = itr.next();
			// Cumulate to Gross Total vars
			this.cumulateToTemp(grossTotalDenomination, grossTotalAccumulate, grossTotalEmp, currentEmp);
			
			if (prevEmp == null) {
				// Print First Employee
				prevEmp = this.printFirstEmp(cells, rowIndex, query, currentEmp, 
						isGreenRow, tempDenomination, tempAccumulate, members);
				continue;
			}
			String prevDepCode = prevEmp.getDepartmentData().getDepCode();
			String currentDepCode = currentEmp.getDepartmentData().getDepCode();
			
			
			if (prevDepCode == currentDepCode) {
				// Cumulate To Temp Variables and Print Employee
				prevEmp = this.printEmpSameDep(cells, rowIndex, query, currentEmp, 
						tempDenomination, tempAccumulate, members, isGreenRow);
				continue;
			}
			if (prevDepCode != currentDepCode) {
				// Push Temp DepartmentData To Stack and Reset Temp vars
				tempDenomination = this.pushToStackAndReset(cells, rowIndex, query, prevEmp, 
						tempDenomination, tempAccumulate, members, depStack);
				// Cumulate to Temp vars
				this.cumulateToTemp(tempDenomination, tempAccumulate, members, currentEmp);

				// Print Sub Accumulated payment of Sub Department
				this.fillAccSubDep(cells, rowIndex, query, currentEmp, prevEmp, depStack);
				
				// Print Employee
				prevEmp = this.printEmpDifferentDep(cells, rowIndex, query, currentEmp, isGreenRow);
			}
		}		
		// END BLOCK OF REPORT
		// Fill Total Of Department
		this.printTotalOfDep(cells, rowIndex, tempDenomination, tempAccumulate.getValue(), 
				members.intValue(), query);

		// Fill Accumulated Payment
		this.printAccAtTheEnd(query, cells, rowIndex, prevEmp, tempDenomination, tempAccumulate, depStack);
		
		// Fill Gross Total
		this.printGrossTotal(query, cells, rowIndex, grossTotalEmp.getValue(), 
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
	private void lastCumulatedDeps(Cells cells, MutableInt rowIndex, SalaryTableReportQuery query, 
			Stack<DepartmentData> depStack, DepartmentData empDep){
		while (!depStack.isEmpty()) {
			String empDepPath = empDep.getDepPath();
			DepartmentData topInStack = depStack.pop();
			// Cumulate printed Denomination into Stack
			if (!empDepPath.startsWith(topInStack.getDepPath())) {
				// print Last Accumulated DepartmentData
				this.printAccByHierarchy(query, topInStack, cells, rowIndex);
			}
			// if(topInStack.getDepPath().equals(empDepPath)){
			// //this.calculateDenomination(prevDepFromStack, topInStack);
			// this.printAccumulated(cells, rowIndex, topInStack);
			// }
			else {
				// Calculate Accumulation
				this.calculateDenomination(empDep, topInStack);
				this.printAccByHierarchy(query, topInStack, cells, rowIndex);
			}
			empDep = topInStack;
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
	private EmployeeData printEmpSameDep(Cells cells, MutableInt rowIndex, SalaryTableReportQuery query,
			EmployeeData currentEmp, Map<Denomination, Long> tempDenomination, MutableDouble tempAccumulate,
			MutableInt members, MutableBoolean isGreen) {
		// Breaking Page Code
		int breakCode = query.getSelectedBreakPageCode();

		if (breakCode == EMPLOYEE_BREAK_CODE) {
			this.breakPageByOption(cells, rowIndex);
			this.printDepInfo(currentEmp, cells, rowIndex, query);
		}
		// Cumulate To Temp Variables
		this.cumulateToTemp(tempDenomination, tempAccumulate, members, currentEmp);

		// print currentEmp
		this.printEmployee(currentEmp, cells, rowIndex, isGreen, query);
		return currentEmp;
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
	private Map<Denomination, Long> pushToStackAndReset(Cells cells, MutableInt rowIndex, 
			SalaryTableReportQuery query, EmployeeData prevEmp, Map<Denomination, Long> tempDenomination, 
			MutableDouble tempAccumulate, MutableInt members, Stack<DepartmentData> depStack){
		
		// Create border line for last employee in a department
		this.createEndRowRange(cells, rowIndex.intValue());
		
		// Print Total In Department if choosen
		this.printTotalOfDep(cells, rowIndex, tempDenomination, tempAccumulate.getValue(), 
				members.intValue(), query);
		
		// Push DepartmentData to Stack
		this.pushDepData(tempDenomination, tempAccumulate.getValue(), depStack, prevEmp);
		
		// Reset Temp Variable and continue Cumulate
		tempDenomination = this.resetTempVars(tempAccumulate, tempDenomination, members);
		
		return tempDenomination;
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
	private EmployeeData printEmpDifferentDep(Cells cells, MutableInt rowIndex, 
			SalaryTableReportQuery query, EmployeeData currentEmp, MutableBoolean isGreen){
		// Breaking Page Code
		int breakCode = query.getSelectedBreakPageCode();
		
		// Check for Breaking page By Employee
		if((breakCode == EMPLOYEE_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageByOption(cells, rowIndex);
		}
		// Print current Department Info
		this.printDepInfo(currentEmp, cells, rowIndex, query);
		
		// Print current Employee
		isGreen.setValue(false);
		this.printEmployee(currentEmp, cells, rowIndex, isGreen, query);
		
		return currentEmp;
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
	private void fillAccSubDep(Cells cells, MutableInt rowIndex, SalaryTableReportQuery query,
			EmployeeData currentEmp, EmployeeData prevEmp, Stack<DepartmentData> depStack){
		
		// Department Path of Current Employee and Previous employee
		int currentDepLevel = currentEmp.getDepartmentData().getDepLevel();
		String currentDepPath = currentEmp.getDepartmentData().getDepPath();
		String prevDepPath = prevEmp.getDepartmentData().getDepPath();
		
		// Check if Current Department is a sub Department of Previous Department
		boolean isSubDep = currentDepPath.startsWith(prevDepPath);
		if (!isSubDep) {
			// Print Sub Accumulated...
			this.printSubAccumulated(cells, rowIndex, query, depStack, currentDepLevel);
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
	private void printAccAtTheEnd(SalaryTableReportQuery query, Cells cells, MutableInt rowIndex,
			EmployeeData prevEmp, Map<Denomination, Long> tempDenomination, 
			MutableDouble tempAccumulate, Stack<DepartmentData> depStack) {
		// Print Accumulated Hierarchy of Department
		if (query.getIsPrintDepHierarchy()) {
			DepartmentData empDep = prevEmp.getDepartmentData();
			empDep.setDenomination(tempDenomination);
			empDep.setAccumulatedPayment(tempAccumulate.getValue());
			this.printAccByHierarchy(query, empDep, cells, rowIndex);
			this.lastCumulatedDeps(cells, rowIndex, query, depStack, empDep);
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
	private void cumulateToTemp(Map<Denomination, Long> tempDenomination, 
			MutableDouble tempAccumulate, MutableInt members, EmployeeData currentEmp){
		// Cumulate to temple variables
		this.cumulateDenomination(tempDenomination, currentEmp.getDenomination());
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
	private EmployeeData printFirstEmp(Cells cells, MutableInt rowIndex, SalaryTableReportQuery query, 
			EmployeeData currentEmp, MutableBoolean isGreen,
			Map<Denomination, Long> tempDenomination, MutableDouble tempAccumulate, MutableInt members){
		// Print Title Row and Department Info
		this.createTitle(cells, rowIndex);
		this.printDepInfo(currentEmp, cells, rowIndex, query);
		 // Cumulate To Temp Variables
		this.cumulateToTemp(tempDenomination, tempAccumulate, members, currentEmp);
		this.printEmployee(currentEmp, cells, rowIndex, isGreen, query);
		return currentEmp;
	}
	
	/**
	 * Reset temp vars.
	 *
	 * @param tempAccumulate the temp accumulate
	 * @param tempDenomination the temp denomination
	 * @param members the members
	 * @return the map
	 */
	private Map<Denomination, Long> resetTempVars(MutableDouble tempAccumulate, 
			Map<Denomination, Long> tempDenomination, MutableInt members){
		// Reset Temple variables
		tempAccumulate.setValue(0);
		members.setValue(0);
		tempDenomination = new HashMap<>();
		return tempDenomination;
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
	private void noneBreakPage(Cells cells, MutableInt currentRow) {
		if (currentRow.intValue() % ROWS_PER_PAGE == 0) {
			this.createEndRowRange(cells, currentRow.intValue());
			currentRow.addAndGet(BLANK_ROWS);
			this.createTitle(cells, currentRow);
		}
	}
	
	/**
	 * Break page border line.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void breakPageBorderLine(Cells cells, MutableInt currentRow) {
		// Create Double border for last row 
		if (currentRow.intValue() % ROWS_PER_PAGE == 0) {
			this.createEndRowRange(cells, currentRow.intValue());
		}
		// Create Double border for first row in next page
		if(currentRow.intValue() % ROWS_PER_PAGE == 1){
			this.createStartRowRange(cells, currentRow.intValue());
		}
	}
	
	/**
	 * Break page by option.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void breakPageByOption(Cells cells, MutableInt currentRow){
		if(currentRow.intValue() % ROWS_PER_PAGE == 0){
			this.createTitle(cells, currentRow);
		} else {
			// Create End Row Range
			this.createEndRowRange(cells, currentRow.intValue());		
			int nextPage = currentRow.intValue() / ROWS_PER_PAGE +1;
			int blankRowsCurrentPage = nextPage * ROWS_PER_PAGE +1 - currentRow.intValue();
			currentRow.addAndGet(blankRowsCurrentPage + BLANK_ROWS);
			// Create Title for Next Page
			this.createTitle(cells, currentRow);
		}
	}

	/**
	 * Creates the end row range.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void createEndRowRange(Cells cells, int currentRow){
		Range endRange = cells.createRange(currentRow - 1, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
		endRange.setOutlineBorder(BorderType.BOTTOM_BORDER, CellBorderType.DOUBLE, Color.getBlack());
	}
	
	/**
	 * Creates the start row range.
	 *
	 * @param cells the cells
	 * @param currentRow the current row
	 */
	private void createStartRowRange(Cells cells, int currentRow){
		Range startRange = cells.createRange(currentRow - 1, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
		startRange.setOutlineBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
	}
	
	/**
	 * Creates the row range.
	 *
	 * @param cells the cells
	 * @param firstRow the first row
	 */
	private void createLeftRightBorder(Cells cells, int firstRow) {
		// Create Left and Right Border to Cell
		Range range = cells.createRange(firstRow, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
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
	private void printSubAccumulated(Cells cells, MutableInt rowIndex, SalaryTableReportQuery query, 
			Stack<DepartmentData> depStack, int currentDepLevel){
		// Peek Department Data from Stack
		DepartmentData depToPrint = depStack.peek();
		String depToPrintPath = depToPrint.getDepPath();
		int depToPrintLevel = depToPrint.getDepLevel();
		
		do { // Print Accumulated payment Of Department
			depToPrint = depStack.pop();
			this.printAccByHierarchy(query, depToPrint, cells, rowIndex);
			if (!depStack.isEmpty()) {
				DepartmentData topInStack = depStack.pop();
				if (depToPrintPath.startsWith(topInStack.getDepPath())) {
					this.calculateDenomination(depToPrint, topInStack);
					depStack.push(topInStack);
				}
				// Assign 
				depToPrintPath = topInStack.getDepPath();
				depToPrintLevel = topInStack.getDepLevel();
			}
		} while (!depStack.isEmpty() && !(currentDepLevel > depToPrintLevel));
	}

	/**
	 * Push dep data.
	 *
	 * @param tempDenomination the temp denomination
	 * @param accumulatePayment the accumulate payment
	 * @param depStack the dep stack
	 * @param prevEmp the prev emp
	 */
	private void pushDepData(Map<Denomination, Long> tempDenomination, double accumulatePayment,
			Stack<DepartmentData> depStack, EmployeeData prevEmp){
		
		// Set Denomination for Temp DepartmentData
		DepartmentData tempDepData = DepartmentData.builder().build();
		tempDepData.setDenomination(tempDenomination);
		tempDepData.setAccumulatedPayment(accumulatePayment);
		tempDepData.setDepLevel(prevEmp.getDepartmentData().getDepLevel());
		tempDepData.setDepCode(prevEmp.getDepartmentData().getDepCode());
		tempDepData.setDepName(prevEmp.getDepartmentData().getDepName());
		tempDepData.setDepPath(prevEmp.getDepartmentData().getDepPath());
		
		// Push DepartmentData to Stack
		depStack.push(tempDepData);
	}
	
	/**
	 * Prints the dep info.
	 *
	 * @param empdata the empdata
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 */
	private void printDepInfo(EmployeeData empdata, Cells cells, MutableInt rowIndex, SalaryTableReportQuery query) {
		DepartmentData depData = empdata.getDepartmentData();
		Cell cell = cells.get(rowIndex.intValue(), 0);
		cell.setValue("部門  : " + SPACES + depData.getDepPath() + SPACES + depData.getDepName());

		// Set Style for cells
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell1 = cells.get(rowIndex.intValue(), i);
			StyleModel stylemodel = new StyleModel();
			stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
			stylemodel.borderType = CellsBorderType.TopBottomOnly;
			this.setCellStyle1(cell1, stylemodel);
			createLeftRightBorder(cells, rowIndex.intValue());	
		}
		rowIndex.increment();
		// Breaking Page
		int breakCode = query.getSelectedBreakPageCode();
		if (breakCode == NONE_BREAK_CODE) {
			this.noneBreakPage(cells, rowIndex);
		}
		if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageBorderLine(cells, rowIndex);
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
	private void printTotalOfDep(Cells cells, MutableInt rowIndex, Map<Denomination, Long> denomination, double amount,
			int members, SalaryTableReportQuery query) {
		if (query.getIsPrintTotalOfDepartment()) {
			int breakCode = query.getSelectedBreakPageCode();
			
			// Fill Data to cells
			cells.get(rowIndex.intValue(), COLUMN_INDEX[0]).setValue("部門計" + SPACES1 + members + "人");
			for (Denomination d : Denomination.values()) {
				int columnIndex = d.value + 1;
				cells.get(rowIndex.intValue(), columnIndex).setValue(denomination.get(d) + "枚");
			}
			cells.get(rowIndex.intValue(), COLUMN_INDEX[11]).setValue(amount);
			
			// Style for cells
			for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
				Cell cell = cells.get(rowIndex.intValue(), i);
				StyleModel stylemodel = new StyleModel();
				stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
				stylemodel.borderType = CellsBorderType.DoubleTopBorder;
				this.setCellStyle1(cell, stylemodel);
				createLeftRightBorder(cells, rowIndex.intValue());
			}
			rowIndex.increment();
			
			// Breaking Page
			if (breakCode == NONE_BREAK_CODE) {
				this.noneBreakPage(cells, rowIndex);
			}
			if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)) {
				this.breakPageBorderLine(cells, rowIndex);
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
	private void printAccByHierarchy(SalaryTableReportQuery query, DepartmentData depToPrint, Cells cells,
			MutableInt rowIndex) {
		// Breaking Page Code
		int breakCode = query.getSelectedBreakPageCode();
		// Hierarchy Breaking Page code
		int hierarchyBreakCode = query.getSelectedBreakPageHierarchyCode();
		List<Integer> selectedLevels = query.getSelectedLevels();
		int depToPrintLevel = depToPrint.getDepLevel();
		for (int i : selectedLevels) {
			if (depToPrintLevel == i) {
				this.printAccumulated(cells, rowIndex, depToPrint, query);
				// Check for Breaking page by Hierarchy
				if ((breakCode == HIERARCHY_BREAK_CODE) && (hierarchyBreakCode == depToPrintLevel)) {
					this.breakPageByOption(cells, rowIndex);
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
	private void printGrossTotal(SalaryTableReportQuery query, Cells cells, MutableInt rowIndex, int totalmembers, 
			Map<Denomination, Long> denomination, double amount) {
		if (query.getIsCalculateTotal()) {
			// Fill Data to cells
			cells.get(rowIndex.intValue(), COLUMN_INDEX[0]).setValue("総合計" + SPACES1 + totalmembers + "人");
			for (Denomination d : Denomination.values()) {
				int columnIndex = d.value + 1;
				cells.get(rowIndex.intValue(), columnIndex).setValue(denomination.get(d) + "枚");
			}
			cells.get(rowIndex.intValue(), COLUMN_INDEX[11]).setValue(amount);
			// Set Style for Cells

			// Style for cells
			for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
				Cell cell = cells.get(rowIndex.intValue(), i);
				StyleModel stylemodel = new StyleModel();
				stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
				stylemodel.borderType = CellsBorderType.DoubleTopBorder;
				this.setCellStyle1(cell, stylemodel);
				createLeftRightBorder(cells, rowIndex.intValue());
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
	private void printAccumulated(Cells cells, MutableInt rowIndex, 
			DepartmentData depData, SalaryTableReportQuery query) {
		int breakCode = query.getSelectedBreakPageCode();
		cells.get(rowIndex.intValue(), COLUMN_INDEX[0]).setValue(depData.getDepName() + SPACES + depData.getDepPath());
		for (Denomination d : Denomination.values()) {
			int columnIndex = d.value + 1;
			cells.get(rowIndex.intValue(), columnIndex).setValue(depData.getDenomination().get(d) + "枚");
		}
		cells.get(rowIndex.intValue(), COLUMN_INDEX[11]).setValue(depData.getAccumulatedPayment());
		// Style for cells
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell = cells.get(rowIndex.intValue(), i);
			StyleModel stylemodel = new StyleModel();
			stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
			stylemodel.borderType = CellsBorderType.DoubleTopBorder;
			this.setCellStyle1(cell, stylemodel);
			createLeftRightBorder(cells, rowIndex.intValue());
		}
		rowIndex.increment();
		// Breaking Page
		if (breakCode == NONE_BREAK_CODE) {
			this.noneBreakPage(cells, rowIndex);
		}	
		if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageBorderLine(cells, rowIndex);
		}
	}

	/**
	 * Cumulate denomination.
	 *
	 * @param denomination the denomination
	 * @param currentEmpDeno the current emp deno
	 */
	private void cumulateDenomination(Map<Denomination, Long> denomination, Map<Denomination, Long> currentEmpDeno) {		
		for (Denomination d : Denomination.values()) {
			if(denomination.get(d) == null){
				denomination.put(d, 0l);
			}
			long quantity = denomination.get(d) + currentEmpDeno.get(d);
			denomination.replace(d, quantity);
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
	private EmployeeData printEmployee(EmployeeData empdata, Cells cells, MutableInt rowIndex, 
			MutableBoolean isGreen, SalaryTableReportQuery query) {
		// Print Employee 
		boolean isPrintEmp = query.getIsPrintDetailItem();
		if (isPrintEmp) {
			cells.get(rowIndex.intValue(), COLUMN_INDEX[0])
					.setValue(empdata.getEmpCode() + SPACES + empdata.getEmpName());
			Map<Denomination, Long> deno = empdata.getDenomination();
			for (Denomination d : Denomination.values()) {
				int columnIndex = d.value + 1;
				cells.get(rowIndex.intValue(), columnIndex).setValue(deno.get(d) + "枚");
			}
			cells.get(rowIndex.intValue(), COLUMN_INDEX[11]).setValue(empdata.getPaymentAmount());

			// Style for cells
			for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
				Cell cell = cells.get(rowIndex.intValue(), i);
				// this.setEmpStyle(cell);
				StyleModel stylemodel = new StyleModel();
				stylemodel.borderType = CellsBorderType.CommonBorder;
				stylemodel.backgroundColor = isGreen.getValue() ? LIGHT_GREEN_COLOR : null;
				this.setCellStyle1(cell, stylemodel);
				createLeftRightBorder(cells, rowIndex.intValue());
			}
			isGreen.setValue(switchColor(isGreen.getValue()));
			rowIndex.increment();
		}

		// Breaking Page
		int breakCode = query.getSelectedBreakPageCode();
		if (breakCode == NONE_BREAK_CODE) {
			this.noneBreakPage(cells, rowIndex);
		}
		if ((breakCode == HIERARCHY_BREAK_CODE) || (breakCode == DEPARTMENT_BREAK_CODE)){
			this.breakPageBorderLine(cells, rowIndex);
		}
		
		return empdata;
	}

	/**
	 * Creates the title.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 */
	private void createTitle(Cells cells, MutableInt rowIndex) {
		
		// Set Header for Employee column
		Cell empCell = cells.get(rowIndex.intValue(), COLUMN_INDEX[0]);
		empCell.setValue("社員");
		Style style = empCell.getStyle();
		style.setHorizontalAlignment(TextAlignmentType.LEFT);
		empCell.setStyle(style);

		for (Denomination deno : Denomination.values()) {
			int columnIndex = deno.value + 1;
			Cell denoCell = cells.get(rowIndex.intValue(), columnIndex);
			denoCell.setValue(deno.description);
		}
		// Set Header for Amount Column
		Cell amountCell = cells.get(rowIndex.intValue(), COLUMN_INDEX[11]);
		amountCell.setValue("金額");

		// Set Style for Header Row
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell = cells.get(rowIndex.intValue(), i);
			this.setCellStyle1(cell, StyleModel.titleStyle());
		}
		rowIndex.increment();
		
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

}
