/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance.salary;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.file.pr.app.export.insurance.data.ChecklistPrintSettingDto;
import nts.uk.file.pr.app.export.insurance.data.HeaderReportData;
import nts.uk.file.pr.app.export.insurance.data.MLayoutInsuOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.MLayoutRowItem;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuMLayoutReportData;
import nts.uk.file.pr.app.export.insurance.salary.SocialInsuMergeLayoutGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeSalarySocialInsuranceReportGenerator.
 *
 */

@Stateless
public class AsposeInsuMergeLayoutReportGenerator extends AsposeCellsReportGenerator
        implements SocialInsuMergeLayoutGenerator {

    /** The Constant TEMPLATE_FILE. */
    private static final String TEMPLATE_FILE = "report/SocialInsuMergeLayoutTemplate.xlsx";

    /** The Constant REPORT_FILE_NAME. */
    private static final String REPORT_FILE_NAME = "給与社会保険料チェックリスト_表示しない_";
    
    /** The Constant REPORT_FILE_NAME. */
    private static final String EXTENSION = ".pdf";

    /** The Constant NUMBER_ZERO. */
    private static final int NUMBER_ZERO = 0;

    /** The Constant NUMBER_ONE. */
    private static final int NUMBER_ONE = 1;

    /** The Constant NUMBER_SECOND. */
    private static final int NUMBER_SECOND = 2;

    /** The Constant INDEX_ROW_CONTENT_AREA. */
    private static final int INDEX_ROW_CONTENT_AREA = 16;

    /** The Constant RADIX. */
    private static final int RADIX = 16;

    /** The Constant PRINT_AREA. */
    private static final String PRINT_AREA = "A1:O";

    /** The Constant COLOR_EMPLOYEE_HEX. */
    private static final String COLOR_EMPLOYEE_HEX = "C8F295";

    /** The Constant HEADER. */
    private static final String HEADER = "HEADER";
    
    /** The Constant SHEET_NAME. */
    private static final String SHEET_NAME = "My Sheet";
    
    /** The Constant RANGE_OFFICE. */
    private static final String RANGE_OFFICE = "RangeOffice";

    /** The Constant RANGE_EMPLOYEE. */
    private static final String RANGE_EMPLOYEE = "RangeEmployee";

    /** The Constant RANGE_FOOTER_EACH_OFFICE. */
    private static final String RANGE_FOOTER_EACH_OFFICE = "RangeFooterEachOffice";
    
    /** The Constant RANGE_DELIVERY_NOTICE_AMOUNT. */
    private static final String RANGE_DELIVERY_NOTICE_AMOUNT = "RangeDeliveryNoticeAmount";

    /** The Constant RANGE_CHILD_RAISING. */
    private static final String RANGE_CHILD_RAISING = "RangeChildRaising";

    /** The Constant NUMBER_COLUMN. */
    private static final int NUMBER_COLUMN = 15;

    /** The Constant INDEX_COLUMN_DELIVERY. */
    private static final int INDEX_COLUMN_DELIVERY = 2;

    /** The Constant INDEX_COLUMN_INSURED. */
    private static final int INDEX_COLUMN_INSURED = 7;

    /** The Constant INDEX_COLUMN_CHILD_RAISING. */
    private static final int INDEX_COLUMN_CHILD_RAISING = 12;

    /** The Constant ALPHABET_A. */
    private static final String ALPHABET_A = "A";

    /** The Constant ALPHABET_Q. */
    private static final String ALPHABET_Q = "Q";
    
    /** The Constant OPERATOR_SUB. */
    private static final String OPERATOR_SUB = "-";

    /** The Constant OFFICE_JP. */
    private static final String OFFICE_JP = "事業所 : ";
    
    /** The Constant DATE_TIME_FORMAT. */
    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    /** The Constant NUMBER_LINE_OF_PAGE. */
    private static final int NUMBER_LINE_OF_PAGE = 61;

    /** The Constant COLUMN_WIDTH. */
    private static final double COLUMN_WIDTH = 11.5;
    
    /** The Constant COLUMN_WIDTH_OFFICE_CODE. */
    private static final double COLUMN_WIDTH_OFFICE_CODE = 14;

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceGenerator
     * #generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
     * nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData)
     */
    @Override
    public void generate(FileGeneratorContext fileContext, SocialInsuMLayoutReportData reportData) {
        try (val reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            createNewSheet(worksheets, SHEET_NAME, reportData);
            reportContext.getDesigner().setWorkbook(workbook);
            workbook.calculateFormula(true);
            reportContext.getDesigner().setDataSource(HEADER, Arrays.asList(reportData.getHeaderData()));
            reportContext.processDesigner();
            DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = new Date();
            String fileName = REPORT_FILE_NAME + dateFormat.format(date).toString() + EXTENSION;
            reportContext.saveAsPdf(this.createNewFile(fileContext, fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the new sheet.
     *
     * @param worksheets the worksheets
     * @param sheetName the sheet name
     * @param reportData the report data
     * @throws Exception the exception
     */
    private void createNewSheet(WorksheetCollection worksheets, String sheetName,
            SocialInsuMLayoutReportData reportData) throws Exception {
        Worksheet worksheet = worksheets.get(NUMBER_ZERO);
        worksheet.setName(sheetName);
        worksheet.autoFitColumns();
        int numberOfColumn = NUMBER_COLUMN;
        
        PrintProcess printProcess = new PrintProcess();
        printProcess.worksheet = worksheet;
        
        HashMap<String, Range> mapRange = new HashMap<String, Range>();
        // get range from template then remove it.
        Range rangeOffice = worksheets.getRangeByName(RANGE_OFFICE);
        mapRange.put(RANGE_OFFICE, rangeOffice);
        Range rangeEmployee = worksheets.getRangeByName(RANGE_EMPLOYEE);
        mapRange.put(RANGE_EMPLOYEE, rangeEmployee);
        Range rangeFooterEachOffice = worksheets.getRangeByName(RANGE_FOOTER_EACH_OFFICE);
        mapRange.put(RANGE_FOOTER_EACH_OFFICE, rangeFooterEachOffice);
        Range rangeDeliveryNoticeAmount = worksheets.getRangeByName(RANGE_DELIVERY_NOTICE_AMOUNT);
        Range rangeChildRaising = worksheets.getRangeByName(RANGE_CHILD_RAISING);

        ChecklistPrintSettingDto configOutput = reportData.getConfigureOutput();
        printProcess.configOutput = configOutput;

        setColumnWidth(worksheet, numberOfColumn, COLUMN_WIDTH);

        // Begin write report
        writeContentArea(printProcess, reportData, mapRange);
        if (configOutput.getShowDeliveryNoticeAmount()) {
            Range rangeDelivery = rangeDeliveryNoticeAmount;
            Range rangeChild = rangeChildRaising;
            writeFooterPage(printProcess, reportData, rangeDelivery, rangeChild);
        }
        
        removeRowTemplate(printProcess, worksheets.getNamedRanges().length, mapRange);
        int totalRow = printProcess.indexRow;
        String printArea = PRINT_AREA.concat(String.valueOf(totalRow));
        settingPage(worksheet, printArea, reportData.getHeaderData());
        drawBorderLinePageBreak(worksheet, totalRow, numberOfColumn);
        
    }

    /**
     * Sets the column width.
     *
     * @param worksheet the worksheet
     * @param numberColumn the number column
     * @param columnWidth the column width
     */
    private void setColumnWidth(Worksheet worksheet, int numberColumn, double columnWidth) {
        worksheet.getCells().setColumnWidth(NUMBER_ZERO, COLUMN_WIDTH_OFFICE_CODE);
        for (int i = NUMBER_ONE; i < numberColumn - NUMBER_ONE; i++) {
            worksheet.getCells().setColumnWidth(i, columnWidth);
        }
    }

    /**
     * Draw border line page break.
     *
     * @param worksheet the worksheet
     * @param totalRow the total row
     * @param numberOfColumn the number of column
     */
    private void drawBorderLinePageBreak(Worksheet worksheet, int totalRow, int numberOfColumn) {
        for (int i = NUMBER_SECOND; i < totalRow; i++) {
            int indexOfArray = i - NUMBER_ONE;
            if (i % NUMBER_LINE_OF_PAGE == NUMBER_ZERO) {
                drawBorderLine(worksheet, indexOfArray, numberOfColumn, BorderType.BOTTOM_BORDER);
            } else if (i % NUMBER_LINE_OF_PAGE == NUMBER_ONE) {
                drawBorderLine(worksheet, indexOfArray, numberOfColumn, BorderType.TOP_BORDER);
            }
        }
    }

    /**
     * Setting page.
     *
     * @param worksheet the worksheet
     * @param printArea the print area
     * @param header the header
     */
    private void settingPage(Worksheet worksheet, String printArea, HeaderReportData header) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPrintArea(printArea);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setCenterVertically(false);
        
        // set header
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        pageSetup.setHeader(2,"&\"IPAPGothic\"&11 " + dateFormat.format(new Date()) + "\n&P ページ");
    }

    /**
     * Write content area.
     *
     * @param printProcess the print process
     * @param reportData the report data
     * @param mapRange the map range
     * @throws Exception the exception
     */
    private void writeContentArea(PrintProcess printProcess, SocialInsuMLayoutReportData reportData,
            HashMap<String, Range> mapRange) throws Exception {
        List<MLayoutInsuOfficeDto> offices = reportData.getOfficeItems();
        printProcess.indexRow = INDEX_ROW_CONTENT_AREA;
        for (MLayoutInsuOfficeDto office : offices) {
            writeHeaderOffice(printProcess, office, mapRange.get(RANGE_OFFICE));
            int indexRowContentOffice = printProcess.indexRow;
            if (printProcess.configOutput.getShowDetail()) {
                writeContentOffice(printProcess, office, mapRange.get(RANGE_EMPLOYEE));
            }
            // setting show total each of office.
            if (printProcess.configOutput.getShowOffice()) {
                writeFooterEachOffice(printProcess, office, indexRowContentOffice,
                        mapRange.get(RANGE_FOOTER_EACH_OFFICE));
            }
        }
        // setting show total all office.
        if (printProcess.configOutput.getShowTotal()) {
            writeSummaryOffice(printProcess, reportData.getTotalAllOffice(), mapRange.get(RANGE_FOOTER_EACH_OFFICE));
        }
    }

    /**
     * Write header office.
     *
     * @param printProcess the print process
     * @param office the office
     * @param rangeOffice the range office
     * @throws Exception the exception
     */
    private void writeHeaderOffice(PrintProcess printProcess, MLayoutInsuOfficeDto office,
            Range rangeOffice) throws Exception {
        Range newRange = createRangeFromOtherRange(printProcess, rangeOffice);
        String officeCode = OFFICE_JP.concat(office.getCode());
        setDataRangeFirstRow(printProcess.worksheet, newRange, NUMBER_ZERO, officeCode, office.getName());
        printProcess.indexRow++;
    }

    /**
     * Write content office.
     *
     * @param printProcess the print process
     * @param office the office
     * @param rangeEmployee the range employee
     * @throws Exception the exception
     */
    private void writeContentOffice(PrintProcess printProcess, MLayoutInsuOfficeDto office, Range rangeEmployee)
            throws Exception {
        for (int i = 0; i < office.getEmployees().size(); i++) {
            boolean isForegroundColor = false;
            if (i % NUMBER_SECOND != 0) {
                isForegroundColor = true;
            }
            MLayoutRowItem employee = office.getEmployees().get(i);
            Range newRange = createRangeFromOtherRange(printProcess, rangeEmployee);
            writeDataEmployee(printProcess, employee, newRange, isForegroundColor);
            printProcess.indexRow++;
        }
    }

    /**
     * Write footer each office.
     *
     * @param printProcess the print process
     * @param office the office
     * @param indexRowBeginEmployee the index row begin employee
     * @param rangeFooterEachOffice the range footer each office
     * @throws Exception the exception
     */
    private void writeFooterEachOffice(PrintProcess printProcess, MLayoutInsuOfficeDto office, int indexRowBeginEmployee,
            Range rangeFooterEachOffice) throws Exception {
        int numberEmployeeOffice = office.getEmployees().size();
        String totalEmployeeOffice = String.valueOf(numberEmployeeOffice).concat(" 人");
        Range newRange = createRangeFromOtherRange(printProcess, rangeFooterEachOffice);
        setDataRangeFirstRow(printProcess.worksheet, newRange, NUMBER_ZERO, "事業所　計", totalEmployeeOffice);
        setDataTotal(printProcess.worksheet, newRange, office.getTotalEachOffice());
        printProcess.indexRow++;
    }

    /**
     * Write summary office.
     *
     * @param printProcess the print process
     * @param totalAllOffice the total all office
     * @param rangeFooterOffice the range footer office
     * @throws Exception the exception
     */
    private void writeSummaryOffice(PrintProcess printProcess, MLayoutRowItem totalAllOffice,
            Range rangeFooterOffice) throws Exception {
        Range newRange = createRangeFromOtherRange(printProcess, rangeFooterOffice);
        setDataRangeFirstRow(printProcess.worksheet, newRange, NUMBER_ZERO, "総合計");
        setDataTotal(printProcess.worksheet, newRange, totalAllOffice);
        printProcess.indexRow++;
    }

    /**
     * Write footer page.
     *
     * @param printProcess the print process
     * @param reportData the report data
     * @param rangeDeliveryNoticeAmount the range delivery notice amount
     * @param rangeChildRaising the range child raising
     * @throws Exception the exception
     */
    private void writeFooterPage(PrintProcess printProcess, SocialInsuMLayoutReportData reportData,
            Range rangeDeliveryNoticeAmount, Range rangeChildRaising)
                   throws Exception {
        printProcess.indexRow++;
        Range newRangeDeliveryNoticeAmount = createRangeFromOtherRange(printProcess, rangeDeliveryNoticeAmount);
        printProcess.indexRow++;
        Range newRangeChildRaising = createRangeFromOtherRange(printProcess, rangeChildRaising);
        setDataRangeFirstRow(printProcess.worksheet, newRangeDeliveryNoticeAmount, INDEX_COLUMN_DELIVERY, 
                reportData.getDeliveryNoticeAmount());
        setDataRangeFirstRow(printProcess.worksheet, newRangeDeliveryNoticeAmount, INDEX_COLUMN_INSURED, 
                reportData.getInsuredCollectAmount());
        setDataRangeRowCalculateSub(printProcess.worksheet, newRangeDeliveryNoticeAmount, newRangeChildRaising,
                reportData.getTotalAllOffice().getChildRaising());
    }

    /**
     * Write data employee.
     *
     * @param printProcess the print process
     * @param rowData the row data
     * @param range the range
     * @param isForegroundColor the is foreground color
     */
    @SuppressWarnings("rawtypes")
    private void writeDataEmployee(PrintProcess printProcess, MLayoutRowItem rowData, Range range, boolean isForegroundColor) {
        Cells cells = printProcess.worksheet.getCells();
        int indexRowCurrent = range.getFirstRow();
        List valueOfAttributes = convertObjectToList(rowData);
        int numberColumn = valueOfAttributes.size();
        for (int i = 0; i < numberColumn; i++) {
            Object item = valueOfAttributes.get(i);
            cells.get(indexRowCurrent, i).setValue(item);
            if (isForegroundColor) {
                setForegroundColorByRow(printProcess.worksheet, indexRowCurrent, i);
            }
        }
    }

    /**
     * Sets the data range first row.
     *
     * @param worksheet the worksheet
     * @param range the range
     * @param indexColumnBegin the index column begin
     * @param data the data
     */
    private void setDataRangeFirstRow(Worksheet worksheet, Range range, int indexColumnBegin, Object... data) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = range.getFirstRow();
        int index = NUMBER_ZERO;
        while (index < data.length) {
            int indexColumn = indexColumnBegin + index;
            cells.get(indexRowCurrent, indexColumn).setValue(data[index]);
            index++;
        }
    }

    /**
     * Sets the data total.
     *
     * @param worksheet the worksheet
     * @param range the range
     * @param total the total
     */
    @SuppressWarnings("rawtypes")
    private void setDataTotal(Worksheet worksheet, Range range, MLayoutRowItem total) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = range.getFirstRow();
        List valueOfAttributes = convertObjectToList(total);
        int numberColumnNeedFill = valueOfAttributes.size();
        for (int i = NUMBER_ZERO; i < numberColumnNeedFill; i++) {
            int indexColumn = i + NUMBER_SECOND;
            cells.get(indexRowCurrent, indexColumn).setValue(valueOfAttributes.get(i));
        }
    }

    /**
     * Sets the data range row calculate sub.
     *
     * @param worksheet the worksheet
     * @param newRangeDeliveryNoticeAmount the new range delivery notice amount
     * @param newRangeChildRaising the new range child raising
     * @param totalChildRaisingBusiness the total child raising business
     */
    private void setDataRangeRowCalculateSub(Worksheet worksheet, Range newRangeDeliveryNoticeAmount,
            Range newRangeChildRaising, double totalChildRaisingBusiness) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = newRangeDeliveryNoticeAmount.getFirstRow();
        String cellStart = cells.get(indexRowCurrent, INDEX_COLUMN_DELIVERY).getName();
        String cellEnd = cells.get(indexRowCurrent, INDEX_COLUMN_INSURED).getName();
        String formulaSubtract = cellStart.concat(OPERATOR_SUB).concat(cellEnd);
        Cell cellBurden = cells.get(indexRowCurrent, INDEX_COLUMN_CHILD_RAISING);
        cellBurden.setFormula(formulaSubtract);
        String formSubRaising = cellBurden.getName().concat(OPERATOR_SUB) + totalChildRaisingBusiness;
        cells.get(newRangeChildRaising.getFirstRow(), INDEX_COLUMN_CHILD_RAISING).setFormula(formSubRaising);
    }

    /**
     * Creates the range from other range.
     *
     * @param printProcess the print process
     * @param range the range
     * @return the range
     * @throws Exception the exception
     */
    private Range createRangeFromOtherRange(PrintProcess printProcess, Range range) throws Exception {
        Cells cells = printProcess.worksheet.getCells();
        String cellStart = ALPHABET_A.concat(String.valueOf(printProcess.indexRow));
        String cellEnd = ALPHABET_Q.concat(String.valueOf(printProcess.indexRow));
        Range newRange = cells.createRange(cellStart, cellEnd);
        newRange.copy(range);
        return newRange;
    }

    /**
     * Removes the row template.
     *
     * @param printProcess the print process
     * @param totalRange the total range
     * @param mapRange the map range
     */
    private void removeRowTemplate(PrintProcess printProcess, int totalRange, HashMap<String, Range> mapRange) {
        int count = NUMBER_ZERO;
        while (count <= totalRange) {
            printProcess.worksheet.getCells().deleteRow(mapRange.get(RANGE_OFFICE).getFirstRow());
            count++;
        }
        printProcess.indexRow -= count;
    }

    /**
     * Draw border line.
     *
     * @param worksheet the worksheet
     * @param indexRow the index row
     * @param numberColumn the number column
     * @param offset the offset
     */
    private void drawBorderLine(Worksheet worksheet, int indexRow, int numberColumn, int offset) {
        Cells cells = worksheet.getCells();
        for (int i = 0; i < numberColumn; i++) {
            Style style = cells.get(indexRow, i).getStyle();
            style.setPattern(BackgroundType.SOLID);
            style.setBorder(offset, CellBorderType.THIN, Color.getBlack());
            cells.get(indexRow, i).setStyle(style);
        }
    }

    /**
     * Sets the foreground color by row.
     *
     * @param worksheet the worksheet
     * @param indexRow the index row
     * @param indexColumn the index column
     */
    private void setForegroundColorByRow(Worksheet worksheet, int indexRow, int indexColumn) {
        Cells cells = worksheet.getCells();
        Style style = cells.get(indexRow, indexColumn).getStyle();
        style.setForegroundArgbColor(Integer.parseInt(COLOR_EMPLOYEE_HEX, RADIX));
        cells.get(indexRow, indexColumn).setStyle(style);
    }

    /**
     * Convert object to list.
     *
     * @param obj
     *            the obj
     * @return the list
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private List convertObjectToList(Object obj) {
        List valueOfAttributes = new ArrayList<>();
        if (obj == null) {
            return valueOfAttributes;
        }
        if (obj instanceof MLayoutRowItem) {
            obj = (MLayoutRowItem) obj;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object val = ReflectionUtil.getFieldValue(field, obj);
            Object item = null;
            if (val == null) {
                // item = "";
                continue;
            }
            if (val instanceof String) {
                item = (String) val;
            } else if (val instanceof Double) {
                item = (Double) val;
            }
            valueOfAttributes.add(item);
        }
        return valueOfAttributes;
    }
}

class PrintProcess {
    
    /** The worksheet. */
    Worksheet worksheet;
    
    /** The index row. */
    int indexRow;
    
    /** The configure output. */
    ChecklistPrintSettingDto configOutput;
}
