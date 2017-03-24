/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.salarychart;

import java.io.Console;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import javax.ejb.Stateless;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.core.tools.Generate;
import org.eclipse.persistence.internal.jpa.metadata.structures.ArrayAccessor;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.screen.app.report.salarychart.SalaryChartReportGenerator;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.Denomination;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.DepartmentData;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.EmployeeData;
import nts.uk.ctx.pr.screen.app.report.salarychart.data.SalaryChartDataSource;
import nts.uk.ctx.pr.screen.app.report.salarychart.query.SalaryChartReportQuery;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeSalaryChartReportGenerator.
 */
@Stateless
public class AsposeSalaryChartReportGenerator extends AsposeCellsReportGenerator implements SalaryChartReportGenerator {

	/** The Constant REPORT_FILE_NAME. */
	private static final String REPORT_FILE_NAME = "SalaryChartReport.pdf";

	/** The Constant TEMPLATE_FILE. */
	private static final String TEMPLATE_FILE = "report/SalaryChartReport.xlsx";

	/** The Constant FIRST_COLUMN_INDEX. */
	private static final int FIRST_COLUMN_INDEX = 0;

	/** The Constant LAST_COLUMN_INDEX. */
	private static final int LAST_COLUMN_INDEX = 12;

	/** The Constant DEP_HIERARCHY. */
	private static final int DEP_HIERARCHY = 9;

	/** The Constant START_COLUMN. */
	private static final int START_COLUMN = 1;

	/** The Constant COLUMN_INDEX. */
	private static final int[] COLUMN_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	/** The Constant ROW_HEIGHT. */
	private static final int ROW_HEIGHT = 28;

	/** The Constant FIRST_ROW_INDEX. */
	private static final int FIRST_ROW_INDEX = 9;

	/** The Constant SPACES. */
	private static final String SPACES = "    ";

	/** The Constant SPACES1. */
	private static final String SPACES1 = "                        ";
	
	private static final Color LIGHT_BLUE_COLOR = Color.fromArgb(197, 241, 247);
	private static final Color LIGHT_GREEN_COLOR = Color.fromArgb(199, 243, 145);

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.screen.app.report.salarychart.SalaryChartReportGenerator#
	 * generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
	 * nts.uk.ctx.pr.screen.app.report.salarychart.data.SalaryChartDataSource)
	 */
	@Override
	public void generate(FileGeneratorContext generatorContext, SalaryChartDataSource dataSource,
			SalaryChartReportQuery query) {
		// TODO Auto-generated method stub
		List<EmployeeData> empList = dataSource.getEmployeeList();
		try {
			 AsposeCellsReportContext designer =
			 this.createContext(TEMPLATE_FILE);
			 Workbook workbook = designer.getWorkbook();
//			WorkbookDesigner designer = new WorkbookDesigner();
//			Workbook workbook = new Workbook("D:/SalaryChartReport.xlsx");
//			designer.setWorkbook(workbook);
			// create worksheet and Formatting ...
			WorksheetCollection worksheets = workbook.getWorksheets();
			//
			int sheetIndex = 0;
			Worksheet worksheet = worksheets.get(sheetIndex);
			Cells cells = worksheet.getCells();
			// Fill data
			// Print Date (Can set in Template-> now())
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/mm/dd hh:mm");
			String str = sf.format(date);
			designer.setDataSource("HeaderTime", str);

			// Print Data
			this.PrintData(cells, empList, query);

			//designer.setDataSource("EmpList", empList);
			 designer.getDesigner().setWorkbook(workbook);
			 designer.processDesigner();
			 designer.saveAsPdf(this.createNewFile(generatorContext, REPORT_FILE_NAME));
//			designer.getWorkbook().save("D:/testGen.xlsx");
//			designer.getWorkbook().save("D:/testGen.pdf", FileFormatType.PDF);
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
	 */
	private void PrintData(Cells cells, List<EmployeeData> empList, SalaryChartReportQuery query) {
		Iterator<EmployeeData> itr = empList.iterator();		
		MutableInt firstRow = new MutableInt(FIRST_ROW_INDEX);
		int titleRowIndex = FIRST_ROW_INDEX - 1;
		EmployeeData prevEmp = null;
		Stack<DepartmentData> depStack = new Stack<>();
		Map<Denomination, Long> tempDenomination = new HashMap<>();
		MutableDouble tempAccumulate = new MutableDouble(0);
		MutableInt members = new MutableInt(0);
		while (itr.hasNext()) {
			EmployeeData currentEmp = itr.next();
			// Print Title Row
			createTitle(cells, titleRowIndex);
			if (prevEmp == null) {
				printDepInfo(currentEmp, cells, firstRow);
				// Increment Accumulator
				this.cumulateDenomination(tempDenomination, currentEmp);
				tempAccumulate.add(currentEmp.getPaymentAmount());
				members.increment();
				// Print currentEmp
				this.printEmployee(currentEmp, cells, firstRow);
				prevEmp = currentEmp;
				continue;
			}
			String prevDepCode = prevEmp.getDepartmentData().getDepCode();
			String currentDepCode = currentEmp.getDepartmentData().getDepCode();
			if (prevDepCode == currentDepCode) {
				// print currentEmp
				this.printEmployee(currentEmp, cells, firstRow);
				// Cumulate
				this.cumulateDenomination(tempDenomination, currentEmp);
				tempAccumulate.add(currentEmp.getPaymentAmount());
				members.increment();
				prevEmp = currentEmp;
				continue;
			}
			if (prevDepCode != currentDepCode) {
				// border line for last employee Of Department
//				int lastEmpIndex = firstRow.decrementAndGet();
//				this.drawLastEmpBorderLine(cells, lastEmpIndex);
				// Print Total In Department
				if (query.getIsPrintTotalOfDepartment()) {
					this.printTotalOfDep(cells, firstRow, tempDenomination, tempAccumulate.getValue(), members.intValue());
				}
				// Print Accumulated Payment for Department
				tempDenomination = this.accumulated(cells, firstRow, currentEmp, prevEmp, depStack, 
						tempDenomination, tempAccumulate, members, query);
				// Print current Department Info
				this.printDepInfo(currentEmp, cells, firstRow);
				
				// Print current Employee
				this.printEmployee(currentEmp, cells, firstRow);
				
				prevEmp = currentEmp;				
			}
		}
		// End Of Report
		this.endOfReport(empList, prevEmp, cells, firstRow, query, 
				tempDenomination, tempAccumulate, depStack, members.intValue());
	}
	
	private void drawLastEmpBorderLine(Cells cells, int rowIndex){
		for (int i = 0; i < LAST_COLUMN_INDEX; i++) {
            Style style = cells.get(rowIndex, i).getStyle();
            style.setPattern(BackgroundType.SOLID);            
            style.setBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
            cells.get(rowIndex, i).setStyle(style);
        }
	}

	/**
	 * Accumulated.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param currentEmp the current emp
	 * @param prevEmp the prev emp
	 * @param depStack the dep stack
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param members the members
	 * @param query the query
	 * @return the map
	 */
	private Map<Denomination, Long> accumulated(Cells cells, MutableInt rowIndex, EmployeeData currentEmp, EmployeeData prevEmp, 
			Stack<DepartmentData> depStack, Map<Denomination, Long> tempDenomination, 
			MutableDouble tempAccumulate, MutableInt members, SalaryChartReportQuery query){
		// Department Path of Current Employee and Previous employee
		int currentDepLevel = currentEmp.getDepartmentData().getDepLevel();
		String currentDepPath = currentEmp.getDepartmentData().getDepPath();
		String prevDepPath = prevEmp.getDepartmentData().getDepPath();
		// Push DepartmentData to Stack and reset TempDenomination..
		this.pushDepData(tempDenomination, tempAccumulate.getValue(), depStack, prevEmp);
		tempAccumulate.setValue(0);
		members.setValue(0);
		// Reset Temp Variable and Cumulate
		tempDenomination = new HashMap<>();
		this.cumulateDenomination(tempDenomination, currentEmp);
		tempAccumulate.add(currentEmp.getPaymentAmount());
		members.increment();
		// Check if Current Department is a sub Department of Previous Department
		boolean isSubDep = currentDepPath.startsWith(prevDepPath);
		if (!isSubDep) {
			// Print Sub Accumulated...
			this.printSubAccumulated(cells, rowIndex, query, depStack, currentDepLevel);
		}
		return tempDenomination;
	}
	
	/**
	 * End of report.
	 *
	 * @param empList the emp list
	 * @param employee the employee
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param query the query
	 * @param tempDenomination the temp denomination
	 * @param tempAccumulate the temp accumulate
	 * @param depStack the dep stack
	 * @param members the members
	 */
	private void endOfReport(List<EmployeeData> empList, EmployeeData employee, Cells cells, MutableInt rowIndex, SalaryChartReportQuery query, 
			Map<Denomination, Long> tempDenomination, MutableDouble tempAccumulate, Stack<DepartmentData> depStack, int members) {
		if (query.getIsPrintTotalOfDepartment()) {
			// Print Total Of Previous Department
			this.printTotalOfDep(cells, rowIndex, tempDenomination, tempAccumulate.getValue(), members);
		}
		// Print Accumulated Payment
		if (query.getIsPrintDepHierarchy()) {
			DepartmentData empDep = employee.getDepartmentData();
			empDep.setDenomination(tempDenomination);
			empDep.setAccumulatedPayment(tempAccumulate.getValue());
			this.isPrintedOrNot(query, empDep, cells, rowIndex);
			while (!depStack.isEmpty()) {// Can xem lai. DepData co level roi
											// :))
				String empDepPath = empDep.getDepPath();
				DepartmentData topInStack = depStack.pop();
				// Cumulate printed Denomination into Stack
				if (!empDepPath.startsWith(topInStack.getDepPath())) {
					// print Last Accumulated DepartmentData
					this.isPrintedOrNot(query, topInStack, cells, rowIndex);
				}
				// if(topInStack.getDepPath().equals(empDepPath)){
				// //this.calculateDenomination(prevDepFromStack, topInStack);
				// this.printAccumulated(cells, rowIndex, topInStack);
				// }
				else {
					// Calculate Accumulation
					this.calculateDenomination(empDep, topInStack);
					this.isPrintedOrNot(query, topInStack, cells, rowIndex);
				}
				empDep = topInStack;
			}
		}
		if (query.getIsCalculateTotal()) {
			// Print Gross Total
			this.printGrossTotal(cells, rowIndex, empList);
			rowIndex.increment();
		}
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
	private void printSubAccumulated(Cells cells, MutableInt rowIndex, SalaryChartReportQuery query, 
			Stack<DepartmentData> depStack, int currentDepLevel){
		DepartmentData depToPrint = depStack.peek();
		String depToPrintPath = depToPrint.getDepPath();
		int depToPrintLevel = depToPrint.getDepLevel();
		do {
			// Print Accumulated
			depToPrint = depStack.pop();
			this.isPrintedOrNot(query, depToPrint, cells, rowIndex);
			if(!depStack.isEmpty()){
				DepartmentData topInStack = depStack.pop();
				if (depToPrintPath.startsWith(topInStack.getDepPath())) {
					this.calculateDenomination(depToPrint, topInStack);
					depStack.push(topInStack);
				}
				depToPrintPath = topInStack.getDepPath();
				depToPrintLevel = topInStack.getDepLevel();
			}
			// }while(!depStack.isEmpty() && !currentDepPath.startsWith(depToPrintPath));// Can xem lai dk. DepData co level roi
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
	 */
	private void printDepInfo(EmployeeData empdata, Cells cells, MutableInt rowIndex) {
		DepartmentData depData = empdata.getDepartmentData();
		cells.setRowHeightPixel(rowIndex.intValue(), ROW_HEIGHT);
		Cell cell = cells.get(rowIndex.intValue(), 0);
		cell.setValue("部門  : " + SPACES + depData.getDepPath() + SPACES + depData.getDepName());

		// Set Style for cells
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell1 = cells.get(rowIndex.intValue(), i);
			//this.setDepStyle(cell1);
			StyleModel stylemodel = new StyleModel();
			stylemodel.backgroundColor = LIGHT_BLUE_COLOR;
			stylemodel.borderType = CellsBorderType.TopBottomOnly;
			this.setCellStyle1(cell1, stylemodel);
			
		}
		// Create Range
		//createRange(cells, rowIndex.intValue());
		rowIndex.increment();
	}
	
	/**
	 * Prints the total of dep.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param denomination the denomination
	 * @param amount the amount
	 * @param members the members
	 */
	private void printTotalOfDep(Cells cells, MutableInt rowIndex, Map<Denomination, Long> denomination, double amount,
			int members) {
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
		}
		rowIndex.increment();
	}
	
	/**
	 * Checks if is printed or not.
	 *
	 * @param query the query
	 * @param depToPrint the dep to print
	 * @param cells the cells
	 * @param rowIndex the row index
	 */
	private void isPrintedOrNot(SalaryChartReportQuery query, DepartmentData depToPrint, 
			Cells cells, MutableInt rowIndex){
		List<Integer> selectedLevels = query.getSelectedLevels();
		for(int i : selectedLevels){
			if(depToPrint.getDepLevel() == i){
				this.printAccumulated(cells, rowIndex, depToPrint);
				break;
			}
		}
	}

	/**
	 * Prints the gross total.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param empList the emp list
	 */
	private void printGrossTotal(Cells cells, MutableInt rowIndex, List<EmployeeData> empList) {
		// Cells cells = worksheet.getCells();
		double amount = 0;
		int amountEmp = empList.size();
		Map<Denomination, Long> denomination = new HashMap<>();
		for (Denomination d : Denomination.values()) {
			denomination.put(d, 0l);
		}
		Iterator<EmployeeData> itr = empList.iterator();
		while (itr.hasNext()) {
			EmployeeData emp = itr.next();
			amount += emp.getPaymentAmount();
			for (Denomination d : Denomination.values()) {
				long quantity = denomination.get(d) + emp.getDenomination().get(d);
				denomination.replace(d, quantity);
			}
		}
		// Fill Data to cells
		cells.get(rowIndex.intValue(), COLUMN_INDEX[0]).setValue("総合計" + SPACES1 + amountEmp + "人");
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
		}
	}

	/**
	 * Prints the accumulated.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 * @param depData the dep data
	 */
	private void printAccumulated(Cells cells, MutableInt rowIndex, DepartmentData depData) {
		// Cells cells = worksheet.getCells();
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
		}
		rowIndex.increment();
	}

	/**
	 * Cumulate denomination.
	 *
	 * @param denomination the denomination
	 * @param currentEmp the current emp
	 */
	private void cumulateDenomination(Map<Denomination, Long> denomination, EmployeeData currentEmp) {		
		for (Denomination d : Denomination.values()) {
			if(denomination.get(d) == null){
				denomination.put(d, 0l);
			}
			long quantity = denomination.get(d) + currentEmp.getDenomination().get(d);
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
	 */
	private void printEmployee(EmployeeData empdata, Cells cells, MutableInt rowIndex) {
		// Cells cells = worksheet.getCells();
		// Set Row Height
		cells.setRowHeightPixel(rowIndex.intValue(), ROW_HEIGHT);
		cells.get(rowIndex.intValue(), COLUMN_INDEX[0]).setValue(empdata.getEmpCode() + SPACES + empdata.getEmpName());
		Map<Denomination, Long> deno = empdata.getDenomination();
		for (Denomination d : Denomination.values()) {
			int columnIndex = d.value + 1;
			cells.get(rowIndex.intValue(), columnIndex).setValue(deno.get(d) + "枚");
		}
		cells.get(rowIndex.intValue(), COLUMN_INDEX[11]).setValue(empdata.getPaymentAmount());

		// Style for cells
		for (int i = FIRST_COLUMN_INDEX; i < LAST_COLUMN_INDEX; i++) {
			Cell cell = cells.get(rowIndex.intValue(), i);
			//this.setEmpStyle(cell);
			StyleModel stylemodel = new StyleModel();
			stylemodel.borderType = CellsBorderType.CommonBorder;
			this.setCellStyle1(cell, stylemodel);
		}
		// Create Range
		//createRange(cells, rowIndex.intValue());
		rowIndex.increment();
	}




//	/**
//	 * Creates the range.
//	 *
//	 * @param cells the cells
//	 * @param rowIndex the row index
//	 */
//	private void createRange(Cells cells, int rowIndex) {
//		Range range = cells.createRange(rowIndex, FIRST_COLUMN_INDEX, 1, LAST_COLUMN_INDEX);
//		range.setOutlineBorders(CellBorderType.THIN, Color.getBlack());
//	}

	/**
	 * Creates the title.
	 *
	 * @param cells the cells
	 * @param rowIndex the row index
	 */
	private void createTitle(Cells cells, int rowIndex) {
		// Worksheet worksheet = worksheets.get(0);
		// Cells cells = worksheet.getCells();
		// Set Row height
		cells.setRowHeightPixel(rowIndex, ROW_HEIGHT);

		// Set Header for Employee column
		Cell empCell = cells.get(rowIndex, COLUMN_INDEX[0]);
		empCell.setValue("社員");		

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
	}

	
	private enum CellsBorderType{
		TopBottomOnly,
		DoubleTopBorder,
		MediumBorder,
		CommonBorder,
	}
	
	public static class StyleModel{
		public Color backgroundColor;
		public CellsBorderType borderType;
		
		public static StyleModel titleStyle() {
			StyleModel style = new StyleModel();
			style.backgroundColor = LIGHT_BLUE_COLOR;
			style.borderType = CellsBorderType.MediumBorder;
			return style;
		}
	}
	
	private void setCellStyle1(Cell cell, StyleModel styleModel){
		Style style = cell.getStyle();
		if (styleModel.backgroundColor != null) {
			style.setForegroundColor(styleModel.backgroundColor);
			style.setPattern(BackgroundType.SOLID);
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
