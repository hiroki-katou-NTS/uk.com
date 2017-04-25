/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.detailpaymentsalary;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageOrientationType;
import com.aspose.cells.PageSetup;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryReportService;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;

/**
 * @author duongnd
 *
 */
//@Stateless
//public class AsposePaymentSalaryReportGenerator extends AsposeCellsReportGenerator
//      implements PaymentSalaryInsuranceGenerator {

public class AsposePaymentSalaryReportGenerator {

  private static final String TEMPLATE_FILE = "/Users/mrken57/Work/UniversalK/project/export/qpp007/QPP007.xlsx";
  private static final String PATH = "/Users/mrken57/Work/UniversalK/project/export/qpp007/";
//  private static final String TEMPLATE_FILE = "report/QPP007.xlsx";
  private static final String REPORT_FILE_NAME = "QPP007_";
  private static final String EXTENSION_PDF = ".pdf";
  private static final String EXTENSION_EXCEL = ".xlsx";
  private static final String SHEET_NAME = "My sheet";
  
  public static void main(String[] args) {
      new AsposePaymentSalaryReportGenerator().testGeneratorReport();
  }
  
  
  /*
   * (non-Javadoc)
   * 
   * @see nts.uk.file.pr.app.export.detailpaymentsalary.
   * PaymentSalaryInsuranceGenerator#generate(nts.arc.layer.infra.file.export.
   * FileGeneratorContext, nts.uk.file.pr.app.export.detailpaymentsalary.data.
   * PaymentSalaryReportData)
   */
//  @Override
//  public void generate(FileGeneratorContext fileContext, PaymentSalaryReportData reportData) {
//      try (val reportContext = this.createContext(TEMPLATE_FILE)) {
//          Workbook workbook = reportContext.getWorkbook();
//          WorksheetCollection worksheets = workbook.getWorksheets();
//          createNewSheet(worksheets, reportData);
//          reportContext.getDesigner().setWorkbook(workbook);
//          workbook.calculateFormula(true);
//          reportContext.processDesigner();
//          DateFormat dateFormat = new SimpleDateFormat(ReportConstant.DATE_TIME_FORMAT);
//          Date date = new Date();
//          String fileName = REPORT_FILE_NAME.concat(dateFormat.format(date).toString()).concat(EXTENSION_PDF);
//          workbook.save(PATH + REPORT_FILE_NAME.concat(dateFormat.format(date).toString()).concat(EXTENSION_EXCEL));
//          reportContext.saveAsPdf(this.createNewFile(fileContext, fileName));
//      } catch (Exception e) {
//          throw new RuntimeException(e);
//      }
//  }
  
  private void testGeneratorReport() {
      try {
          FileInputStream fstream = new FileInputStream(TEMPLATE_FILE);
          Workbook workbook = new Workbook(fstream);
          WorksheetCollection worksheets = workbook.getWorksheets();
          PaymentSalaryReportService service = new PaymentSalaryReportService();
          PaymentSalaryReportData reportData = service.initData();
          createNewSheet(worksheets, reportData);
          workbook.calculateFormula(true);
          DateFormat dateFormat = new SimpleDateFormat(ReportConstant.DATE_TIME_FORMAT);
          Date date = new Date();
          String fileName = PATH + REPORT_FILE_NAME.concat(dateFormat.format(date).toString());
          workbook.save(fileName.concat(EXTENSION_EXCEL));
          
          PdfSaveOptions saveOptions = new PdfSaveOptions(SaveFormat.PDF);
          saveOptions.setAllColumnsInOnePagePerSheet(true);
          workbook.save(fileName.concat(EXTENSION_PDF), saveOptions);
          workbook.save(fileName.concat(EXTENSION_PDF));
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
    
    private void createNewSheet(WorksheetCollection worksheets, PaymentSalaryReportData reportData) {
        Worksheet worksheet = worksheets.get(ReportConstant.NUMBER_ZERO);
        worksheet.setName(SHEET_NAME);
        
        PrintProcess printProcess = new PrintProcess();
        printProcess.worksheet = worksheet;
        
        writeContent(printProcess, reportData);
        
        // set print page
        setupPage(printProcess);
    }
    
    private void setupPage(PrintProcess printProcess) {
        PageSetup pageSetup = printProcess.worksheet.getPageSetup();
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setCenterVertically(false);
        pageSetup.setCenterHorizontally(false);
        
        // set print area
        int lastColumn = printProcess.titleRows.size() - ReportConstant.NUMBER_ONE;
        Cell cellEnd = printProcess.worksheet.getCells().get(printProcess.indexRow, lastColumn);
        String endArea = cellEnd.getName();
        String printArea = ReportConstant.START_AREA + endArea;
        pageSetup.setPrintArea(printArea);
    }
    
    private void writeContent(PrintProcess printProcess, PaymentSalaryReportData reportData) {
        printProcess.indexRow = ReportConstant.INDEX_ROW_CONTENT;
        printProcess.selectedLevels = reportData.getConfigure().getSelectedLevels();
        printProcess.employees = reportData.getEmployees();
        
        Map<EmployeeKey, Double> mapAmount = reportData.getMapEmployeeAmount();
        
        List<String> yearMonths = mapAmount.entrySet()
                .stream()
                .sorted((p1, p2) -> p1.getKey().getYearMonth().compareTo(p2.getKey().getYearMonth()))
                .filter(distinctByKey(p -> p.getKey().getYearMonth()))
                .map(p -> p.getKey().getYearMonth())
                .collect(Collectors.toList());
        printProcess.yearMonths = yearMonths;
        
        // ========== CONTENT REPORT ===========
        // payment
        Map<EmployeeKey, Double> mapPayment = findMapCategory(mapAmount, SalaryCategory.Payment);
        if (!mapPayment.isEmpty()) {
            printProcess.mapAmount = mapPayment;
            printProcess.category = SalaryCategory.Payment;
            writeCategoryItem(printProcess);
        }
        // deduction
        Map<EmployeeKey, Double> mapDeduction = findMapCategory(mapAmount, SalaryCategory.Deduction);
        if (!mapDeduction.isEmpty()) {
            printProcess.mapAmount = mapDeduction;
            printProcess.category = SalaryCategory.Deduction;
            writeCategoryItem(printProcess);
        }
        // attendance
        Map<EmployeeKey, Double> mapAttendance = findMapCategory(mapAmount, SalaryCategory.Attendance);
        if (!mapAttendance.isEmpty()) {
            printProcess.mapAmount = mapAttendance;
            printProcess.category = SalaryCategory.Attendance;
            writeCategoryItem(printProcess);
        }
        // article others
        Map<EmployeeKey, Double> mapArticleOthers = findMapCategory(mapAmount, SalaryCategory.ArticleOthers);
        if (!mapArticleOthers.isEmpty()) {
            printProcess.mapAmount = mapArticleOthers;
            printProcess.category = SalaryCategory.ArticleOthers;
            writeCategoryItem(printProcess);
        }
        
        // ========== TITLE REPORT ===========
        writeTitleRow(printProcess);
        
        // ========== WRITE NUMBER EMPLOYEE FOOTER REPORT ===========
        writeNumberEmployee(printProcess);
    }
    
    private void writeTitleRow(PrintProcess printProcess) {
        Cells cells = printProcess.worksheet.getCells();
        int indexRow = ReportConstant.INDEX_ROW_TITLE;
        cells.setRowHeight(indexRow, ReportConstant.ROW_HEIGHT_TITLE);
        List<String> titleRows = printProcess.titleRows;
        StyleModel styleModel = new StyleModel();
        styleModel.setForegroundColor(ReportConstant.LIGHT_BLUE_COLOR);
        for (int i = 0; i < titleRows.size(); i++) {
            String item = titleRows.get(i);
            Cell cell = cells.get(indexRow, i);
            cell.setValue(item);
            styleModel.drawBorderCell(cell);
        }
    }
    
    private Map<EmployeeKey, Double> findMapCategory(Map<EmployeeKey, Double> mapAmount, SalaryCategory category) {
        return mapAmount.entrySet()
                .stream()
                .sorted((p1, p2) -> p1.getKey().getYearMonth().compareTo(p2.getKey().getYearMonth()))
                .filter(item -> item.getKey().getSalaryCategory() == category)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }
    
    private void writeCategoryItem(PrintProcess printProcess) {
        Map<EmployeeKey, Double> mapAmount = printProcess.mapAmount;
        // write content of a category.
        List<String> itemNames = mapAmount.entrySet()
                .stream()
                .map(p -> p.getKey().getItemName())
                .distinct()
                .collect(Collectors.toList());
        int indexRowHead = printProcess.indexRow;
        printProcess.indexRow++;
        int indexArr = 0;
        for (String itemName : itemNames) {
            printProcess.isForebackground = false;
            if (indexArr % 2 != 0) {
                printProcess.isForebackground = true;
            }
            calAmountByItemName(printProcess, itemName);
            printProcess.indexColumn = ReportConstant.NUMBER_ZERO;
            printProcess.indexRow++;
            printProcess.isHasTitleRow = true;
            indexArr++;
        }
        //write header category. Need to calculate index row of header category?
        writeHeaderCategory(printProcess, indexRowHead);
    }
    
    private void writeHeaderCategory(PrintProcess printProcess, int indexRowHead) {
        String header;
        switch (printProcess.category) {
            case Payment :
                header = "【支給項目】";
                break;
            case Deduction :
                header = "【控除項目】";
                break;
            case Attendance :
                header = "";
                break;
            case ArticleOthers :
                header = "【記事項目】";
                break;
            default :
                header = "";
        }
        Cells cells = printProcess.worksheet.getCells();
        Cell cell = cells.get(indexRowHead, ReportConstant.NUMBER_ZERO);
        cell.setValue(header);
        
        StyleModel styleModel = new StyleModel();
        int numberColumn = printProcess.titleRows.size();
        styleModel.drawHeaderCategory(cells, numberColumn, indexRowHead);
    }
    
    private void calAmountByItemName(PrintProcess printProcess, String itemName) {
        // ========== WRITE ITEM NAME ==========
        writeItemName(printProcess, itemName);
        
        // ========== DECLARE VARIABLE =========
        Map<EmployeeKey, Double> mapCategory = printProcess.mapAmount;
        Map<EmployeeKey, Double> mapAmonutItemName = subMapAmount(mapCategory, itemName);
        Stack<DepartmentDto> stackDep = new Stack<>();
        List<EmployeeDto> employees = printProcess.employees;
        Iterator<EmployeeDto> iteratorEmp = employees.iterator();
        EmployeeDto prevEmp = null;
        
        // list code employee in department.
        List<String> codeEmpDeps = new ArrayList<>();
        DepartmentDto lastDep = null;
        
        // ========== FIND AND PRINT EMPLOYEES ===========
        while(iteratorEmp.hasNext()) {
            EmployeeDto currEmp = iteratorEmp.next();
            if (prevEmp == null) {
                prevEmp = currEmp;
                codeEmpDeps.add(currEmp.getCode());
                // print amount of employee monthly and total all month.
                writeEmployee(printProcess, mapAmonutItemName, currEmp);
                continue;
            }
            DepartmentDto prevDep = prevEmp.getDepartment();
            DepartmentDto currDep = currEmp.getDepartment();
            lastDep = currDep;
            // check employees in department ?
            if (currDep.getCode().equals(prevDep.getCode())) {
                // print employee same a department
                writeEmployee(printProcess, mapAmonutItemName, currEmp);
                codeEmpDeps.add(currEmp.getCode());
                continue;
            } else {
                stackDep.push(prevDep);
                // write department monthly and total department all month.
                writeTotalDep(printProcess, mapAmonutItemName, codeEmpDeps, prevDep);
                codeEmpDeps.clear();
                codeEmpDeps.add(currEmp.getCode());
                // check current department
                if (!currDep.getDepPath().startsWith(prevDep.getDepPath())) {
                    // TODO: print calculate cumulative of previous department
                    stackDep = findSameLevelDep(printProcess, stackDep, currDep);
                }
                writeEmployee(printProcess, mapAmonutItemName, currEmp);
                prevEmp = currEmp;
            }
        }
        // write total monthly and total all of last department.
        if (lastDep != null) {
            writeTotalDep(printProcess, mapAmonutItemName, codeEmpDeps, lastDep);
            // write cumulative monthly and total of department.
            writeCumulateDep(printProcess, lastDep, new ArrayList<>());
        }
        
        // write total departments monthly and all department
        writeTotalAllDep(printProcess);
    }
    
    private void writeItemName(PrintProcess printProcess, String itemName) {
        Cell cell = printProcess.worksheet.getCells().get(printProcess.indexRow, printProcess.indexColumn);
        cell.setValue(itemName);
        StyleModel styleModel = new StyleModel();
        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(ReportConstant.LIGHT_GREEN_COLOR);
        }
        styleModel.drawBorderCell(cell);
        printProcess.indexColumn++;
    }
     
    private void writeEmployee(PrintProcess printProcess, Map<EmployeeKey, Double> mapAmonutItemName, EmployeeDto emp) {
        // map amount of a employee. It can a month or multiple month.
        Map<EmployeeKey, Double> amountEmp = mapAmonutItemName.entrySet()
                .stream()
                .filter(p -> p.getKey().getEmployeeCode().equals(emp.getCode()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        StyleModel styleModel = new StyleModel();
        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(ReportConstant.LIGHT_GREEN_COLOR);
        }
        Cells cells = printProcess.worksheet.getCells();
        // write amount of a employee.
        double totalEmployee = ReportConstant.NUMBER_ZERO;
        List<String> titleRows = printProcess.titleRows;
        if (titleRows == null) {
            titleRows = new ArrayList<>();
            titleRows.add("社員");
        }
        for (String yearMonth : printProcess.yearMonths) {
            // amount of employee in a month. It can pay many times in month.
            List<Double> amountMonth = amountEmp.entrySet()
                    .stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth))
                    .map(p -> p.getValue())
                    .collect(Collectors.toList());
            for (Double amount : amountMonth) {
                Cell cell = cells.get(indexRow, indexColumn);
                cell.setValue(amount);
                styleModel.drawBorderCell(cell);
                indexColumn++;
                
                // write title row
                if (!printProcess.isHasTitleRow) {
                    String titleRow = yearMonth + "\n" + emp.getCode() + "    " + emp.getName();
                    titleRows.add(titleRow);
                }
            }
            // if multiple month, will write amount total of employee monthly.
            double totalAmountMonthly = amountMonth.stream()
                    .mapToDouble(p -> p.doubleValue())
                    .sum();
            totalEmployee += totalAmountMonthly;
            Cell cellTotalMonthly = cells.get(indexRow, indexColumn);
            cellTotalMonthly.setValue(totalAmountMonthly);
            styleModel.drawBorderCell(cellTotalMonthly);
            indexColumn++;
            // write title row
            if (!printProcess.isHasTitleRow) {
                String titleRow = yearMonth + "\nTotal Monthly\n" + emp.getCode() + "    " + emp.getName();
                titleRows.add(titleRow);
            }
        }
        // write total a employee of all months.
        Cell cellTotal = cells.get(indexRow, indexColumn);
        cellTotal.setValue(totalEmployee);
        styleModel.drawBorderCell(cellTotal);
        indexColumn++;
        // write title row
        if (!printProcess.isHasTitleRow) {
            String titleRow = "Total all\n" + emp.getCode() + "    " + emp.getName();
            titleRows.add(titleRow);
            
            printProcess.titleRows = titleRows;
        }
        
        printProcess.indexColumn = indexColumn;
    }
    
    private void writeTotalDep(PrintProcess printProcess, Map<EmployeeKey, Double> mapAmonutItemName, 
            List<String> codeEmps, DepartmentDto prevDep) {
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        StyleModel styleModel = new StyleModel();
        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(ReportConstant.LIGHT_GREEN_COLOR);
        }
        double totalDep = ReportConstant.NUMBER_ZERO;
        Cells cells = printProcess.worksheet.getCells();
        List<String> titleRows = printProcess.titleRows;
        if (printProcess.mapFooter == null) {
            printProcess.mapFooter = new HashMap<>();
        }
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        if (mapAmountDepMonths == null) {
            mapAmountDepMonths = new HashMap<>();
        }
        // write amount a department monthly.
        for (String yearMonth : printProcess.yearMonths) {
            double amountMonth = mapAmonutItemName.entrySet()
                    .stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth)
                            && codeEmps.contains(p.getKey().getEmployeeCode()))
                    .mapToDouble(p -> p.getValue())
                    .sum();
            totalDep += amountMonth;
            DepartmentDto dep = DepartmentDto.newDepartment(prevDep, yearMonth);
            mapAmountDepMonths.put(dep, amountMonth);
            
            Cell cell = cells.get(indexRow, indexColumn);
            cell.setValue(amountMonth);
            styleModel.drawBorderCell(cell);
            indexColumn++;
            
            // write title row
            if (!printProcess.isHasTitleRow) {
                String titleRow = yearMonth + "\nTotal Monthly\n" + prevDep.getCode() + "    " + prevDep.getName();
                titleRows.add(titleRow);
            }
            
            // count number employee
            String numberEmp = codeEmps.size() + "人";
            printProcess.mapFooter.put(indexColumn, numberEmp);
        }
        // write total amount a department in many months.
        Cell cellTotal = cells.get(indexRow, indexColumn);
        cellTotal.setValue(totalDep);
        styleModel.drawBorderCell(cellTotal);
        indexColumn++;
        // write title row
        if (!printProcess.isHasTitleRow) {
            String titleRow = "Total Dep\n" + prevDep.getCode() + "    " + prevDep.getName();
            titleRows.add(titleRow);
            
            printProcess.titleRows = titleRows;
        }
        printProcess.indexColumn = indexColumn;
        printProcess.mapAmountDepMonths = mapAmountDepMonths;
    }
    
    private Stack<DepartmentDto> findSameLevelDep(PrintProcess printProcess, Stack<DepartmentDto> stackDep, DepartmentDto currentDep) {
        Stack<DepartmentDto> newStack = new Stack<>();
        newStack.addAll(stackDep);
        List<DepartmentDto> tmpDeps = new ArrayList<>();
        boolean isEqualDepLevel = false;
        while (!newStack.isEmpty()) {
            DepartmentDto prevDep = newStack.pop();
            tmpDeps.add(prevDep);
            if (currentDep.getDepLevel() == prevDep.getDepLevel()) {
                calCumulateDepMonthly(printProcess, tmpDeps, prevDep);
                newStack.push(currentDep);
                isEqualDepLevel = true;
                // remove department code: C, D, E --> remove E (D contains E)
                for (int i = 0; i < tmpDeps.size(); i++) {
                    if (tmpDeps.get(i) == prevDep) {
                        break;
                    } else {
                        tmpDeps.remove(i);
                    }
                }
            }
            // write cumulative department.
            writeCumulateDep(printProcess, prevDep, tmpDeps);
            if (isEqualDepLevel) {
                break;
            }
        }
        
        return newStack;
    }
    
    private void writeCumulateDep(PrintProcess printProcess, DepartmentDto prevDep, List<DepartmentDto> tmpDeps) {
        if (!printProcess.selectedLevels.contains(prevDep.getDepLevel())) {
            return;
        }
        Cells cells = printProcess.worksheet.getCells();
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        List<String> titleRows = printProcess.titleRows;
        calCumulateDepMonthly(printProcess, tmpDeps, prevDep);
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        double totalCumulate = 0;
        StyleModel styleModel = new StyleModel();
        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(ReportConstant.LIGHT_GREEN_COLOR);
        }
        
        // write cumulative previous department.
        for (String yearMonth : printProcess.yearMonths) {
            prevDep.setYearMonth(yearMonth);
            Cell cell = cells.get(indexRow, indexColumn);
            double amount = mapAmountDepMonths.get(prevDep);
            cell.setValue(amount);
            styleModel.drawBorderCell(cell);
            totalCumulate += amount;
            indexColumn++;
            if (!printProcess.isHasTitleRow) {
                String titleRow = yearMonth + "\nCumulative Monthly\n" + prevDep.getCode() + "        " + prevDep.getName();
                titleRows.add(titleRow);
            }
        }
        Cell cellTotal = cells.get(indexRow, indexColumn);
        cellTotal.setValue(totalCumulate);
        styleModel.drawBorderCell(cellTotal);
        indexColumn++;
        if (!printProcess.isHasTitleRow) {
            String titleRow = "Cumulative Total\n" + prevDep.getCode() + "        " + prevDep.getName();
            titleRows.add(titleRow);
        }
        
        printProcess.indexColumn = indexColumn;
        printProcess.titleRows = titleRows;
    }
    
    private void writeTotalAllDep(PrintProcess printProcess) {
        Cells cells = printProcess.worksheet.getCells();
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        List<String> titleRows = printProcess.titleRows;
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        double totalCumulate = 0;
        StyleModel styleModel = new StyleModel();
        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(ReportConstant.LIGHT_GREEN_COLOR);
        }
        
        // write cumulative previous department.
        for (String yearMonth : printProcess.yearMonths) {
            Cell cell = cells.get(indexRow, indexColumn);
            double amount = mapAmountDepMonths.entrySet()
                    .stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth))
                    .mapToDouble(p -> p.getValue())
                    .sum();
            cell.setValue(amount);
            styleModel.drawBorderCell(cell);
            totalCumulate += amount;
            indexColumn++;
            if (!printProcess.isHasTitleRow) {
                String titleRow = yearMonth + "\n総合 月計\n";
                titleRows.add(titleRow);
            }
        }
        Cell cellTotal = cells.get(indexRow, indexColumn);
        cellTotal.setValue(totalCumulate);
        styleModel.drawBorderCell(cellTotal);
        indexColumn++;
        if (!printProcess.isHasTitleRow) {
            String titleRow = "総合計\n";
            titleRows.add(titleRow);
        }
        
        printProcess.indexColumn = indexColumn;
        printProcess.titleRows = titleRows;
    }
    
    private void writeNumberEmployee(PrintProcess printProcess) {
        // TODO: write number employee footer report.
        StyleModel styleModel = new StyleModel();
        styleModel.setForegroundColor(ReportConstant.LIGHT_BLUE_COLOR);
        for (int indexColum : printProcess.mapFooter.keySet()) {
            String numberEmp = printProcess.mapFooter.get(indexColum);
            Cell cell = printProcess.worksheet.getCells().get(printProcess.indexRow, indexColum);
            cell.setValue(numberEmp);
            styleModel.drawBorderCell(cell);
        }
        printProcess.indexRow++;
    }
    
    private void calCumulateDepMonthly(PrintProcess printProcess, List<DepartmentDto> tmpDeps, DepartmentDto prevDep) {
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        for (String yearMonth : printProcess.yearMonths) {
            prevDep.setYearMonth(yearMonth);
            double amount = mapAmountDepMonths.entrySet()
                    .stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth) && tmpDeps.contains(p.getKey()))
                    .mapToDouble(p -> p.getValue())
                    .sum();
            if (amount != 0) {
                mapAmountDepMonths.remove(prevDep);
                mapAmountDepMonths.put(prevDep, amount);
            }
        }
        printProcess.mapAmountDepMonths = mapAmountDepMonths;
    }
    
    private Map<EmployeeKey, Double> subMapAmount(Map<EmployeeKey, Double> mapAmount, String itemName) {
        return mapAmount.entrySet()
                .stream()
                .filter(p -> p.getKey().getItemName().equals(itemName))
                .collect(Collectors.toMap(p-> p.getKey(), p -> p.getValue()));
    }
    
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    @Setter
    @Getter
    class StyleModel {
        
        private Color foregroundColor;
        private String formatNumber;
        private CellsBorderType borderType;
        
        public StyleModel() {
            this.foregroundColor = Color.getWhite();
            this.borderType = CellsBorderType.CommonBorder;
        }
        
        public StyleModel(Color background, CellsBorderType cellBorder) {
            this.foregroundColor = background;
            this.borderType = cellBorder;
        }
        
        public void drawTitleReport(Cell cell) {
            Style style = this.getCellStyle(cell);
            style.setHorizontalAlignment(TextAlignmentType.CENTER);
            cell.setStyle(style);
        }
        
        public void drawHeaderCategory(Cells cells, int numberColumn, int indexRow) {
            this.drawBorderLineRow(cells, numberColumn, indexRow);
            Cell firstCellRow = cells.get(indexRow, 0);
            this.drawBorderLeft(firstCellRow);
            
            int indexLastColumn = numberColumn - ReportConstant.NUMBER_ONE;
            Cell lastCellRow = cells.get(indexRow, indexLastColumn);
            this.drawBorderRight(lastCellRow);
        }
        
        public void drawBorderCell(Cell cell) {
            Style style = this.getCellStyle(cell);
            if (this.formatNumber != null) {
                style.setCustom(this.formatNumber);
                style.setHorizontalAlignment(TextAlignmentType.RIGHT);
            }
            cell.setStyle(style);
        }
        
        public void drawDoubleBorder(Cell cell, CellsBorderType borderType) {
            Style style = this.getCellStyle(cell, borderType);
            cell.setStyle(style);
        }
        
        public void drawBorderLineRow(Cells cells, int numberColumn, int indexRow) {
            for (int i = 0; i < numberColumn; i++) {
                Cell cell = cells.get(indexRow, i);
                Style style = this.getCellStyle(cell, CellsBorderType.CategoryBorder);
                cell.setStyle(style);
                
                // draw border last and first column in page.
                if ( i > ReportConstant.NUMBER_ONE) { 
                    Style newStyle = null;
                    int indexReal = i + ReportConstant.NUMBER_ONE;
                    if (indexReal % ReportConstant.NUMBER_COLUMN_PAGE == ReportConstant.NUMBER_ZERO) {
                        newStyle = this.getCellStyle(cell, CellsBorderType.RightBorder);
                    } else if (indexReal % ReportConstant.NUMBER_COLUMN_PAGE == ReportConstant.NUMBER_ONE) {
                        newStyle = this.getCellStyle(cell, CellsBorderType.LeftBorder);
                    }
                    if (newStyle != null) {
                        cell.setStyle(newStyle);
                    }
                }
            }
        }
        
        public void drawBorderLeft(Cell cell) {
            Style style = this.getCellStyle(cell, CellsBorderType.LeftBorder);
            cell.setStyle(style);
        }
        
        public void drawBorderRight(Cell cell) {
            Style style = this.getCellStyle(cell, CellsBorderType.RightBorder);
            cell.setStyle(style);
        }
        
        private Style getCellStyle(Cell cell, CellsBorderType borderType){
            Style style = cell.getStyle();
            style = findBorder(cell, borderType);
            style.setForegroundColor(this.foregroundColor);
            style.setPattern(BackgroundType.SOLID);
            style.setTextWrapped(true);
            return style;
        }
    
        private Style getCellStyle(Cell cell){
            Style style = cell.getStyle();
            style = findBorder(cell, this.borderType);
            style.setForegroundColor(this.foregroundColor);
            style.setPattern(BackgroundType.SOLID);
            style.setTextWrapped(true);
            return style;
        }
        
        private Style findBorder(Cell cell, CellsBorderType borderType) {
            Style style = cell.getStyle();
            switch (borderType) {
                case CommonBorder:
                    style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
                    style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
                    style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
                    style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
                    break;
                case DoubleLeftBorder:
                    style.setBorder(BorderType.LEFT_BORDER, CellBorderType.DOUBLE, Color.getBlack());
                    break;
                case DoubleRightBorder:
                    style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOUBLE, Color.getBlack());
                    break;
                case CategoryBorder:
                    style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
                    style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
                    break;
                case LeftBorder:
                    style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
                    break;
                case RightBorder:
                    style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
                    break;
                default:
                    break;
            }
            return style;
        }
    }
    
    enum CellsBorderType {
        CommonBorder,
        DoubleLeftBorder,
        DoubleRightBorder,
        CategoryBorder,
        LeftBorder,
        RightBorder
    }
    
    class PrintProcess {
        Worksheet worksheet;
        int indexRow = 0;
        int indexColumn = 0;
        boolean isForebackground = false;
        boolean isHasTitleRow = false;
        List<String> titleRows;
        Map<Integer, String> mapFooter;
        SalaryCategory category;
        List<EmployeeDto> employees;
        List<DepartmentDto> departments;
        List<Integer> selectedLevels;
        Map<EmployeeKey, Double> mapAmount;
        List<String> yearMonths;
        Map<DepartmentDto, Double> mapAmountDepMonths;
    }
}
