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
import java.util.Arrays;
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

import javax.ejb.Stateless;
import javax.inject.Inject;

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
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaySalaryInsuGenerator;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryReportService;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.HeaderReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.SalaryPrintSettingDto;
import nts.uk.shr.com.time.japanese.JapaneseErasProvider;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposePaySalaryReportGenerator extends AsposeCellsReportGenerator
        implements PaySalaryInsuGenerator {

    @Inject
    private JapaneseErasProvider japaneseProvider;

//    private static final String TEMPLATE_FILE = "/Users/mrken57/Work/UniversalK/project/export/qpp007/PaymentSalaryTemplate.xlsx";
    private static final String PATH = "/Users/mrken57/Work/UniversalK/project/export/qpp007/";
    private static final String TEMPLATE_FILE = "report/QPP007.xlsx";
    private static final String REPORT_FILE_NAME = "明細一覧表.pdf";
    private static final String EXTENSION_PDF = ".pdf";
    private static final String EXTENSION_EXCEL = ".xlsx";
    private static final String SHEET_NAME = "My sheet";

    // public static void main(String[] args) {
    // new AsposePaymentSalaryReportGenerator().testGeneratorReport();
    // }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.file.pr.app.export.detailpaymentsalary.
     * PaymentSalaryInsuranceGenerator#generate(nts.arc.layer.infra.file.export.
     * FileGeneratorContext, nts.uk.file.pr.app.export.detailpaymentsalary.data.
     * PaymentSalaryReportData)
     */
    @Override
    public void generate(FileGeneratorContext fileContext, PaymentSalaryReportData reportData) {
        try (val reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            workbook.calculateFormula(true);
            createNewSheet(worksheets, reportData);
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.getDesigner().setDataSource(PaymentConstant.HEADER, Arrays.asList(reportData.getHeaderData()));
            reportContext.processDesigner();
//             workbook.save(PATH + this.getReportName(REPORT_FILE_NAME).concat(EXTENSION_EXCEL));
            reportContext.saveAsPdf(this.createNewFile(fileContext, this.getReportName(REPORT_FILE_NAME)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void testGeneratorReport() {
        try {
            FileInputStream fstream = new FileInputStream(TEMPLATE_FILE);
            Workbook workbook = new Workbook(fstream);
            WorksheetCollection worksheets = workbook.getWorksheets();
            PaymentSalaryReportService service = new PaymentSalaryReportService();
            PaymentSalaryReportData reportData = service.initData();
            createNewSheet(worksheets, reportData);
            workbook.calculateFormula(true);
            String fileName = this.getReportName(REPORT_FILE_NAME);
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
        Worksheet worksheet = worksheets.get(PaymentConstant.NUMBER_ZERO);
        worksheet.setName(SHEET_NAME);

        PrintProcess printProcess = new PrintProcess();
        printProcess.worksheet = worksheet;
        printProcess.configure = reportData.getConfigure();

        writeContent(printProcess, reportData);

        // ====== SET PRINT PAGE ======
        setupPage(printProcess, reportData.getHeaderData());
    }

    private void setupPage(PrintProcess printProcess, HeaderReportData header) {
        // ======== PAGE SETUP =========
        PageSetup pageSetup = printProcess.worksheet.getPageSetup();
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
        pageSetup.setCenterVertically(false);
        pageSetup.setCenterHorizontally(false);

        int totalColumns = printProcess.totalColumn;

        // ===== SET HEADER =======
         pageSetup.setHeader(0,"&\"IPAPGothic\"&11 " +
         header.getNameCompany());
         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
         pageSetup.setHeader(2,"&\"IPAPGothic\"&11 " + dateFormat.format(new
         Date()) + "\n&P ページ");

        // merge row title report
        printProcess.worksheet.getCells().merge(0, 0, 1, PaymentConstant.NUMBER_COLUMN_PAGE);

        // ======== SET PRINT AREA ========
        int lastColumn = totalColumns - PaymentConstant.NUMBER_ONE;
        Cell cellEnd = printProcess.worksheet.getCells().get(printProcess.indexRow, lastColumn);
        String endArea = cellEnd.getName();
        String printArea = PaymentConstant.START_AREA + endArea;
        pageSetup.setPrintArea(printArea);
    }

    private void writeContent(PrintProcess printProcess, PaymentSalaryReportData reportData) {
        printProcess.indexRow = PaymentConstant.INDEX_ROW_CONTENT;
        printProcess.selectedLevels = reportData.getConfigure().getSelectedLevels();
        printProcess.employees = reportData.getEmployees().stream()
                .filter(distinctByKey(p -> p.getCode()))
                .collect(Collectors.toList());

        Map<EmployeeKey, Double> mapAmount = reportData.getMapEmployeeAmount();

        List<Integer> yearMonths = mapAmount.entrySet().stream()
                .sorted((p1, p2) -> p1.getKey().getYearMonth().compareTo(p2.getKey().getYearMonth()))
                .filter(distinctByKey(p -> p.getKey().getYearMonth())).map(p -> p.getKey().getYearMonth())
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
        int indexRow = PaymentConstant.INDEX_ROW_TITLE;
        cells.setRowHeight(indexRow, PaymentConstant.ROW_HEIGHT_TITLE);
        Map<Integer, String> mapTitle = printProcess.mapTitle;
        StyleModel styleModel = new StyleModel(PaymentConstant.LIGHT_BLUE_COLOR);
        
        for (int indexColumn : mapTitle.keySet()) {
            String item = mapTitle.get(indexColumn);
            Cell cell = cells.get(indexRow, indexColumn);
            cell.setValue(item);
            styleModel.drawBorderCell(cell);
        }
    }

    private Map<EmployeeKey, Double> findMapCategory(Map<EmployeeKey, Double> mapAmount, SalaryCategory category) {
        return mapAmount.entrySet().stream()
                .sorted((p1, p2) -> p1.getKey().getYearMonth().compareTo(p2.getKey().getYearMonth()))
                .filter(item -> item.getKey().getSalaryCategory() == category)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    private void writeCategoryItem(PrintProcess printProcess) {
        Map<EmployeeKey, Double> mapAmount = printProcess.mapAmount;
        // write content of a category.
        List<String> itemNames = mapAmount.entrySet().stream()
                .map(p -> p.getKey().getItemName())
                .distinct()
                .collect(Collectors.toList());
        printProcess.indexRowHeaderCategory = printProcess.indexRow;
        printProcess.indexRow++;
        int indexArr = 0;
        for (String itemName : itemNames) {
            printProcess.isForebackground = false;
            if (indexArr % 2 != 0) {
                printProcess.isForebackground = true;
            }
            calAmountByItemName(printProcess, itemName);

            // ===== UPDATE VARIABLE ====
            printProcess.totalColumn = printProcess.indexColumn;
            printProcess.indexColumn = PaymentConstant.NUMBER_ZERO;
            printProcess.indexRow++;
            printProcess.isHasTitleRow = true;
            printProcess.mapAmountDepMonths = null;
            indexArr++;
        }
        // write header category. Need to calculate index row of header
        // category?
        writeHeaderCategory(printProcess);
    }

    private void writeHeaderCategory(PrintProcess printProcess) {
        String header;
        // ====== FIND HEADER BY CATEGORY ======
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
        Cell cell = cells.get(printProcess.indexRowHeaderCategory, PaymentConstant.NUMBER_ZERO);
        cell.setValue(header);

        int numberColumn = printProcess.totalColumn;
        StyleModel styleModel = new StyleModel();
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.drawHorizontalEdgePage(cells, numberColumn, printProcess.indexRowHeaderCategory);
        styleModel.drawBorderLineRow(cells, numberColumn, printProcess.indexRowHeaderCategory);
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
        while (iteratorEmp.hasNext()) {
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
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.setBorderType(CellsBorderType.VerticalBorder);

        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(PaymentConstant.LIGHT_GREEN_COLOR);
        }
        styleModel.drawBorderCell(cell);
        if (printProcess.mapTitle == null) {
            printProcess.mapTitle = new HashMap<>();
            if (printProcess.configure.getOutputFormatType() == PaymentConstant.OUTPUT_FORMAT_TYPE_CUMULATIVE) {
                printProcess.mapTitle.put(printProcess.indexColumn, "");
            } else {
                printProcess.mapTitle.put(printProcess.indexColumn, "社員");
            }
        }
        printProcess.indexColumn++;
    }

    private void writeEmployee(PrintProcess printProcess, Map<EmployeeKey, Double> mapAmonutItemName, EmployeeDto emp) {
        // map amount of a employee. It can a month or multiple month.
        Map<EmployeeKey, Double> amountEmp = mapAmonutItemName.entrySet().stream()
                .filter(p -> p.getKey().getEmployeeCode().equals(emp.getCode()))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        SalaryPrintSettingDto configure = printProcess.configure;
        
        // ======= SET STYLE ======
        StyleModel styleModel = new StyleModel();
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.setBorderType(CellsBorderType.VerticalBorder);
        styleModel.setFormatNumber(PaymentConstant.FORMAT_NUMBER);

        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(PaymentConstant.LIGHT_GREEN_COLOR);
        }
        Cells cells = printProcess.worksheet.getCells();
        // write amount of a employee.
        double totalEmployee = PaymentConstant.NUMBER_ZERO;
        Map<Integer, String> mapTitle = printProcess.mapTitle;
        
        // ====== WRITE EMPLOYEE MONTHLY ======
        for (Integer yearMonth : printProcess.yearMonths) {
            List<Double> amountMonth = amountEmp.entrySet().stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth)).map(p -> p.getValue())
                    .collect(Collectors.toList());
            for (Double amount : amountMonth) {
                if (configure.getOutputFormatType().equals(PaymentConstant.OUTPUT_FORMAT_TYPE_DETAIL)
                        && configure.getShowPayment()) {
                    Cell cell = cells.get(indexRow, indexColumn);
                    cell.setValue(amount);
                    styleModel.drawBorderCell(cell);
                    styleModel.drawBorderLastColPageIfNeed(cell);
                    // write title row
                    if (!printProcess.isHasTitleRow) {
                        String titleRow = convertYearMonthJP(yearMonth) + "\n" + emp.getCode() + "\n" + emp.getName();
                        mapTitle.put(indexColumn, titleRow);
                    }
                    indexColumn++;
                }
            }
            // if multiple month, will write amount total of employee monthly.
            double totalAmountMonthly = amountMonth.stream()
                    .mapToDouble(p -> p.doubleValue())
                    .sum();
            totalEmployee += totalAmountMonthly;
            if (configure.getOutputFormatType().equals(PaymentConstant.OUTPUT_FORMAT_TYPE_DETAIL)
                    && configure.getSumMonthPersonSet()) {
                Cell cellTotalMonthly = cells.get(indexRow, indexColumn);
                cellTotalMonthly.setValue(totalAmountMonthly);
                styleModel.drawBorderCell(cellTotalMonthly);
                styleModel.drawBorderLastColPageIfNeed(cellTotalMonthly);
                // write title row
                if (!printProcess.isHasTitleRow) {
                    String titleRow = convertYearMonthJP(yearMonth) + "\nTotal Monthly\n" + emp.getCode() + "    "
                            + emp.getName();
                    mapTitle.put(indexColumn, titleRow);
                }
                indexColumn++;
            }
        }
        
        // ====== WRITE EMPLOYEE TOTAL ======
        if (configure.getOutputFormatType().equals(PaymentConstant.OUTPUT_FORMAT_TYPE_DETAIL)
                && configure.getSumPersonSet()) {
            Cell cellTotal = cells.get(indexRow, indexColumn);
            cellTotal.setValue(totalEmployee);
            styleModel.drawBorderCell(cellTotal);
            styleModel.drawBorderLastColPageIfNeed(cellTotal);
            // write title row
            if (!printProcess.isHasTitleRow) {
                String titleRow = "個人計\n" + emp.getCode() + "    " + emp.getName();
                mapTitle.put(indexColumn, titleRow);
            }
            if (PaymentConstant.PAGE_BREAK_EMPLOYEE.equals(printProcess.configure.getPageBreakSetting())) {
                styleModel.drawBorderRight(cellTotal);
                indexColumn = breakPage(printProcess, indexColumn);
            } else {
                indexColumn++;
            }
        }
        printProcess.mapTitle = mapTitle;
        printProcess.indexColumn = indexColumn;
    }

    private void writeTotalDep(PrintProcess printProcess, Map<EmployeeKey, Double> mapAmonutItemName,
            List<String> codeEmps, DepartmentDto prevDep) {
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        
        // ====== SET STYLE ======
        StyleModel styleModel = new StyleModel();
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.setBorderType(CellsBorderType.DoubleVerticalBorder);
        styleModel.setFormatNumber(PaymentConstant.FORMAT_NUMBER);
        // =======================
        
        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(PaymentConstant.LIGHT_GREEN_COLOR);
        }
        double totalDep = PaymentConstant.NUMBER_ZERO;
        Cells cells = printProcess.worksheet.getCells();
        Map<Integer, String> mapTitle = printProcess.mapTitle;
        if (printProcess.mapFooter == null) {
            printProcess.mapFooter = new HashMap<>();
        }
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        if (mapAmountDepMonths == null) {
            mapAmountDepMonths = new HashMap<>();
        }
        SalaryPrintSettingDto configure = printProcess.configure;
        int totalEmp = 0;
        
        // ====== WRITE AMOUNT A DEPARTMENT MONTHLY ======
        for (Integer yearMonth : printProcess.yearMonths) {
            double amountMonth = mapAmonutItemName.entrySet().stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth) 
                            && codeEmps.contains(p.getKey().getEmployeeCode()))
                    .mapToDouble(p -> p.getValue())
                    .sum();
            totalDep += amountMonth;
            DepartmentDto dep = DepartmentDto.newDepartment(prevDep, yearMonth);
            mapAmountDepMonths.put(dep, amountMonth);
            if (configure.getSumMonthDeprtSet()) {
                Cell cell = cells.get(indexRow, indexColumn);
                cell.setValue(amountMonth);
                styleModel.drawBorderCell(cell);

                // write title row
                if (!printProcess.isHasTitleRow) {
                    String titleRow = convertYearMonthJP(yearMonth) + "\n部門月計\n" + prevDep.getCode() + "    "
                            + prevDep.getName();
                    mapTitle.put(indexColumn, titleRow);
                }
                totalEmp += codeEmps.size();
                // count number employee
                String numberEmp = codeEmps.size() + "人";
                printProcess.mapFooter.put(indexColumn, numberEmp);

                indexColumn++;
            }
        }
        
        // ====== WRITE AMOUNT A DEPARTMENT TOTAL =======
        if (configure.getSumEachDeprtSet()) {
            // write total amount a department in many months.
            Cell cellTotal = cells.get(indexRow, indexColumn);
            cellTotal.setValue(totalDep);
            styleModel.drawBorderCell(cellTotal);

            // write title row
            if (!printProcess.isHasTitleRow) {
                String titleRow = "\n部門計\n" + prevDep.getCode() + "    " + prevDep.getName();
                mapTitle.put(indexColumn, titleRow);
            }
            String numberEmp = totalEmp + "人";
            printProcess.mapFooter.put(indexColumn, numberEmp);

            if (PaymentConstant.PAGE_BREAK_DEPARTMENT.equals(printProcess.configure.getPageBreakSetting())) {
                styleModel.drawBorderRight(cellTotal);
                indexColumn = breakPage(printProcess, indexColumn);
            } else {
                indexColumn++;
            }
        }
        printProcess.indexColumn = indexColumn;
        printProcess.mapTitle = mapTitle;
        printProcess.mapAmountDepMonths = mapAmountDepMonths;
    }

    private Stack<DepartmentDto> findSameLevelDep(PrintProcess printProcess, Stack<DepartmentDto> stackDep,
            DepartmentDto currentDep) {
        Stack<DepartmentDto> newStack = new Stack<>();
        newStack.addAll(stackDep);
        
        List<DepartmentDto> tmpDeps = new ArrayList<>();
        boolean isEqualDepLevel = false;
        while (!newStack.isEmpty()) {
            DepartmentDto prevDep = newStack.pop();
            tmpDeps.add(prevDep);
            if (currentDep.getDepLevel() == prevDep.getDepLevel()) {
                calCumulateDepMonthly(printProcess, tmpDeps, prevDep);
                // newStack.push(currentDep);
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
        Map<Integer, String> mapTitle = printProcess.mapTitle;
        calCumulateDepMonthly(printProcess, tmpDeps, prevDep);
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        double totalCumulate = 0;
        
        // ====== SET STYLE ======
        StyleModel styleModel = new StyleModel();
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.setBorderType(CellsBorderType.DoubleVerticalBorder);
        styleModel.setFormatNumber(PaymentConstant.FORMAT_NUMBER);

        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(PaymentConstant.LIGHT_GREEN_COLOR);
        }

        // ====== WRITE CUMULATIVE MONTH DEPARTMENT ======
        for (Integer yearMonth : printProcess.yearMonths) {
            prevDep.setYearMonth(yearMonth);
            double amount = mapAmountDepMonths.get(prevDep);
            totalCumulate += amount;
            if (printProcess.configure.getSumMonthDepHrchySet()) {
                Cell cell = cells.get(indexRow, indexColumn);
                cell.setValue(amount);
                styleModel.drawBorderCell(cell);

                if (!printProcess.isHasTitleRow) {
                    String titleRow = convertYearMonthJP(yearMonth) + "\n部門階層月計\n" + prevDep.getCode() + "        "
                            + prevDep.getName();
                    mapTitle.put(indexColumn, titleRow);
                }
                indexColumn++;
            }
        }
        
        // ====== WRITE CUMULATIVE DEPARTMENT TOTAL ======
        if (printProcess.configure.getSumDepHrchyIndexSet()) {
            Cell cellTotal = cells.get(indexRow, indexColumn);
            cellTotal.setValue(totalCumulate);
            styleModel.drawBorderCell(cellTotal);

            if (!printProcess.isHasTitleRow) {
                String titleRow = "\n部門階層累計\n" + prevDep.getCode() + "        " + prevDep.getName();
                mapTitle.put(indexColumn, titleRow);
            }

            if (PaymentConstant.PAGE_BREAK_HIERARCHY_DEPARTMENT.equals(printProcess.configure.getPageBreakSetting())
                    && printProcess.configure.getHierarchy().equals(prevDep.getDepLevel().toString())) {
                styleModel.drawBorderRight(cellTotal);
                indexColumn = breakPage(printProcess, indexColumn);
            } else {
                indexColumn++;
            }
        }

        printProcess.indexColumn = indexColumn;
        printProcess.mapTitle = mapTitle;
    }

    private void writeTotalAllDep(PrintProcess printProcess) {
        Cells cells = printProcess.worksheet.getCells();
        int indexRow = printProcess.indexRow;
        int indexColumn = printProcess.indexColumn;
        Map<Integer, String> mapTitle = printProcess.mapTitle;
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        double totalCumulate = 0;
        
        // ====== SET STYLE =======
        StyleModel styleModel = new StyleModel();
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.setBorderType(CellsBorderType.DoubleVerticalBorder);
        styleModel.setFormatNumber(PaymentConstant.FORMAT_NUMBER);

        if (printProcess.isForebackground) {
            styleModel.setForegroundColor(PaymentConstant.LIGHT_GREEN_COLOR);
        }

        // ====== WIRITE AMOUNT MONTH TOTAL ======
        for (Integer yearMonth : printProcess.yearMonths) {
            double amount = mapAmountDepMonths.entrySet().stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth))
                    .mapToDouble(p -> p.getValue())
                    .sum();
            totalCumulate += amount;
            if (printProcess.configure.getMonthTotalSet()) {
                Cell cell = cells.get(indexRow, indexColumn);
                cell.setValue(amount);
                styleModel.drawBorderCell(cell);

                if (!printProcess.isHasTitleRow) {
                    String titleRow = convertYearMonthJP(yearMonth) + "\n総合 月計\n";
                    mapTitle.put(indexColumn, titleRow);
                }
                indexColumn++;
            }
        }
        // ====== WRITE AMOUNT TOTAL ======
        if (printProcess.configure.getTotalSet()) {
            Cell cellTotal = cells.get(indexRow, indexColumn);
            cellTotal.setValue(totalCumulate);
            styleModel.drawBorderCell(cellTotal);

            if (!printProcess.isHasTitleRow) {
                String titleRow = "総合計\n";
                mapTitle.put(indexColumn, titleRow);
            }
            indexColumn++;
        }

        printProcess.indexColumn = indexColumn;
        printProcess.mapTitle = mapTitle;
    }

    private void writeNumberEmployee(PrintProcess printProcess) {
        // write number employee footer report.
        Cells cells = printProcess.worksheet.getCells();

        Cell cellName = cells.get(printProcess.indexRow, PaymentConstant.NUMBER_ZERO);
        cellName.setValue("件数");

        for (int indexColum : printProcess.mapFooter.keySet()) {
            String numberEmp = printProcess.mapFooter.get(indexColum);
            Cell cell = cells.get(printProcess.indexRow, indexColum);
            cell.setValue(numberEmp);
        }
        int numberColumn = printProcess.totalColumn;
        
        // ====== SET STYLE FOR ROW NUMBER EMPLOYEE ======
        StyleModel styleModel = new StyleModel(PaymentConstant.LIGHT_BLUE_COLOR);
        styleModel.setHorizontalBorder(printProcess.configure.getIsHorizontalLine());
        styleModel.setVerticalBorder(printProcess.configure.getIsVerticalLine());
        styleModel.drawHorizontalEdgePage(cells, numberColumn, printProcess.indexRow);
        styleModel.drawBorderLineRow(cells, numberColumn, printProcess.indexRow);

        printProcess.indexRow++;
    }

    private void calCumulateDepMonthly(PrintProcess printProcess, List<DepartmentDto> tmpDeps, DepartmentDto dep) {
        Map<DepartmentDto, Double> mapAmountDepMonths = printProcess.mapAmountDepMonths;
        List<String> depCodes = tmpDeps.stream()
                .map(p -> p.getCode())
                .collect(Collectors.toList());
        
        // ====== UPDATE AMOUNT MONTHLY OF DEPARTMENT ======
        for (Integer yearMonth : printProcess.yearMonths) {
            DepartmentDto newDep = DepartmentDto.newDepartment(dep, yearMonth);
            double amount = mapAmountDepMonths.entrySet().stream()
                    .filter(p -> p.getKey().getYearMonth().equals(yearMonth)
                            && depCodes.contains(p.getKey().getCode()))
                    .mapToDouble(p -> p.getValue())
                    .sum();
            if (amount != 0) {
                mapAmountDepMonths.remove(newDep);
                mapAmountDepMonths.put(newDep, amount);
            }
        }
        printProcess.mapAmountDepMonths = mapAmountDepMonths;
    }

    private int breakPage(PrintProcess printProcess, int indexColumn) {
        int numColumnExist = indexColumn % PaymentConstant.NUMBER_COLUMN_PAGE;
        int numColumnBlank = PaymentConstant.NUMBER_COLUMN_PAGE - numColumnExist;

        StyleModel styleModel = new StyleModel();
        Cells cells = printProcess.worksheet.getCells();
        
        // ====== DRAW BORDER FIRST AND LAST COLUMN OF ROW HEADER CATEGORY ======
        Cell cellStartPage = cells.get(printProcess.indexRowHeaderCategory, indexColumn - numColumnExist);
        styleModel.drawBorderLeft(cellStartPage);

        Cell cellEndPage = cells.get(printProcess.indexRowHeaderCategory, indexColumn);
        styleModel.drawBorderRight(cellEndPage);

        return indexColumn + numColumnBlank;
    }

    private Map<EmployeeKey, Double> subMapAmount(Map<EmployeeKey, Double> mapAmount, String itemName) {
        return mapAmount.entrySet().stream()
                .filter(p -> p.getKey().getItemName().equals(itemName))
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private String convertYearMonthJP(Integer yearMonth) {
        String firstDay = "01";
        String tmpDate = yearMonth.toString().concat(firstDay);
        String dateFormat = "yyyyMMdd";
        GeneralDate generalDate = GeneralDate.fromString(tmpDate, dateFormat);
        return this.japaneseProvider.toJapaneseDate(generalDate).toString();
    }

    @Setter
    @Getter
    class StyleModel {

        private Color foregroundColor;
        private String formatNumber;
        private CellsBorderType borderType;
        private boolean isVerticalBorder;
        private boolean isHorizontalBorder;

        public StyleModel() {
            this.foregroundColor = Color.getWhite();
            this.borderType = CellsBorderType.CommonBorder;
            this.isVerticalBorder = true;
            this.isHorizontalBorder = true;
        }

        public StyleModel(Color background) {
            this.foregroundColor = background;
            this.borderType = CellsBorderType.CommonBorder;
            this.isVerticalBorder = true;
            this.isHorizontalBorder = true;
        }

        public void drawTitleReport(Cell cell) {
            Style style = this.findStyleCell(cell, this.borderType);
            style.setHorizontalAlignment(TextAlignmentType.CENTER);
            cell.setStyle(style);
        }

        public void drawHorizontalEdgePage(Cells cells, int numberColumn, int indexRow) {
            Cell firstCellRow = cells.get(indexRow, 0);
            this.drawBorderLeft(firstCellRow);

            int indexLastColumn = numberColumn - PaymentConstant.NUMBER_ONE;
            Cell lastCellRow = cells.get(indexRow, indexLastColumn);
            this.drawBorderRight(lastCellRow);
        }

        public void drawBorderCell(Cell cell) {
            Style style = this.findStyleCell(cell, this.borderType);
            if (this.formatNumber != null) {
                style.setCustom(this.formatNumber);
                style.setHorizontalAlignment(TextAlignmentType.RIGHT);
            }
            cell.setStyle(style);
        }

        public void drawDoubleBorder(Cell cell, CellsBorderType borderType) {
            Style style = this.findStyleCell(cell, borderType);
            cell.setStyle(style);
        }

        public void drawBorderLineRow(Cells cells, int numberColumn, int indexRow) {
            for (int i = 0; i < numberColumn; i++) {
                Cell cellAbove = cells.get(indexRow - 1, i);
                Cell currentCell = cells.get(indexRow, i);
                Cell cellBelow = cells.get(indexRow + 1, i);

                Style style = this.findStyleCell(currentCell, CellsBorderType.HorizontalBorder);
                currentCell.setStyle(style);

                // ====== DRAW BORDER LAST AND FIRST COLUMN IN A PAGE ======
                this.drawBorderLastColPageIfNeed(currentCell);
                
                // ====== CLEAR BORDER COLUMN HAS VALUE EMPTY ======
                if (cellAbove.getValue() == null && currentCell.getValue() == null && cellBelow.getValue() == null) {
                    this.clearBorderColumnEmpty(cells, indexRow, i);
                }

            }
        }

        public void drawBorderLastColPageIfNeed(Cell cell) {
            int indexColumn = cell.getColumn();
            if (indexColumn > PaymentConstant.NUMBER_ONE) {
                Style newStyle = null;
                int indexReal = indexColumn + PaymentConstant.NUMBER_ONE;
                if (indexReal % PaymentConstant.NUMBER_COLUMN_PAGE == PaymentConstant.NUMBER_ZERO) {
                    newStyle = this.findStyleCell(cell, CellsBorderType.RightBorder);
                } else if (indexReal % PaymentConstant.NUMBER_COLUMN_PAGE == PaymentConstant.NUMBER_ONE) {
                    newStyle = this.findStyleCell(cell, CellsBorderType.LeftBorder);
                }
                if (newStyle != null) {
                    cell.setStyle(newStyle);
                }
            }
        }

        public void clearBorderColumnEmpty(Cells cells, int indexRow, int indexColumn) {
            // ====== FIND CELL AROUND ======
            Cell prevCellAbove = cells.get(indexRow - 1, (indexColumn - 1) >= 0 ? (indexColumn - 1) : 0);
            Cell nextCellAbove = cells.get(indexRow - 1, indexColumn + 1);
            Cell currentCell = cells.get(indexRow, indexColumn);
            Cell prevCellBelow = cells.get(indexRow + 1, (indexColumn - 1) >= 0 ? (indexColumn - 1) : 0);
            Cell nextCellBelow = cells.get(indexRow + 1, indexColumn + 1);

            Style style = this.findStyleCell(currentCell, CellsBorderType.NoBorder);
            style.setForegroundColor(Color.getWhite());
            currentCell.setStyle(style);
            
            // ====== SET RIGHT BORDER - LAST COLUMN OF A PAGE ======
            if (prevCellAbove.getValue() != null || prevCellBelow.getValue() != null) {
                Cell prevCell = cells.get(indexRow, (indexColumn - 1) >= 0 ? (indexColumn - 1) : 0);
                Style styleLeft = this.findStyleCell(prevCell, CellsBorderType.RightBorder);
                prevCell.setStyle(styleLeft);
            }
            
            // ====== SET LEFT BORDER - FIRST COLUMN OF A PAGE
            if (nextCellAbove.getValue() != null || nextCellBelow.getValue() != null) {
                Cell nextCell = cells.get(indexRow, indexColumn + 1);
                Style styleLeft = this.findStyleCell(nextCell, CellsBorderType.LeftBorder);
                nextCell.setStyle(styleLeft);
            }
        }

        public void drawBorderLeft(Cell cell) {
            Style style = this.findStyleCell(cell, CellsBorderType.LeftBorder);
            cell.setStyle(style);
        }

        public void drawBorderRight(Cell cell) {
            Style style = this.findStyleCell(cell, CellsBorderType.RightBorder);
            cell.setStyle(style);
        }

        private Style findStyleCell(Cell cell, CellsBorderType borderType) {
            Style style = cell.getStyle();
            style.setForegroundColor(this.foregroundColor);
            style.setPattern(BackgroundType.SOLID);
            style.setTextWrapped(true);

            switch (borderType) {
                case NoBorder :
                    style.setBorder(BorderType.LEFT_BORDER, CellBorderType.NONE, Color.getWhite());
                    style.setBorder(BorderType.TOP_BORDER, CellBorderType.NONE, Color.getWhite());
                    style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.NONE, Color.getWhite());
                    style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.NONE, Color.getWhite());
                    break;
                case CommonBorder :
                    if (this.isVerticalBorder) {
                        style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
                        style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
                    }
                    if (this.isHorizontalBorder) {
                        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
                        style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
                    }
                    break;
                case VerticalBorder :
                    if (this.isVerticalBorder) {
                        style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
                        style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
                    }
                    break;
                case HorizontalBorder :
                    if (this.isHorizontalBorder) {
                        style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
                        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
                    }
                    break;
                case LeftBorder :
                    if (this.isVerticalBorder) {
                        style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
                    }
                    break;
                case RightBorder :
                    if (this.isVerticalBorder) {
                        style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
                    }
                    break;
                case DoubleVerticalBorder :
                    if (this.isVerticalBorder) {
                        style.setBorder(BorderType.LEFT_BORDER, CellBorderType.DOUBLE, Color.getBlack());
                        style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.DOUBLE, Color.getBlack());
                    }
                    break;
                default :
                    break;
            }
            return style;
        }
    }

    enum CellsBorderType {
        NoBorder, CommonBorder, VerticalBorder, HorizontalBorder, LeftBorder, RightBorder, DoubleVerticalBorder
    }

    class PrintProcess {
        Worksheet worksheet;
        int indexRow = 0;
        int indexColumn = 0;
        int indexRowHeaderCategory = 0;
        int totalColumn = 0;
        boolean isForebackground = false;
        boolean isHasTitleRow = false;
        // List<String> titleRows;
        Map<Integer, String> mapTitle;
        Map<Integer, String> mapFooter;
        SalaryCategory category;
        List<EmployeeDto> employees;
        List<DepartmentDto> departments;
        List<Integer> selectedLevels;
        Map<EmployeeKey, Double> mapAmount;
        List<Integer> yearMonths;
        Map<DepartmentDto, Double> mapAmountDepMonths;
        SalaryPrintSettingDto configure;
    }
}
