/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance.salary;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
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
import nts.uk.file.pr.app.export.insurance.data.DataRowItem;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class AsposeSalarySocialInsuranceReportGenerator.
 *
 * @author duongnd
 */

@Stateless
public class AsposeSalarySocialInsuranceReportGenerator extends AsposeCellsReportGenerator
        implements SalarySocialInsuranceGenerator {

    /** The Constant TEMPLATE_FILE. */
    private static final String TEMPLATE_FILE = "report/SocialInsurance.xlsx";

    /** The Constant REPORT_FILE_NAME. */
    private static final String REPORT_FILE_NAME = "SocialInsurance.pdf";

    /** The Constant NUMBER_ZERO. */
    private static final int NUMBER_ZERO = 0;

    /** The Constant NUMBER_ONE. */
    private static final int NUMBER_ONE = 1;

    /** The Constant NUMBER_SECOND. */
    private static final int NUMBER_SECOND = 2;

    /** The Constant INDEX_ROW_CONTENT_AREA. */
    private static final int INDEX_ROW_CONTENT_AREA = 20;

    /** The Constant RADIX. */
    private static final int RADIX = 16;

    /** The Constant PRINT_AREA. */
    private static final String PRINT_AREA = "A1:Q";

    /** The Constant COLOR_EMPLOYEE_HEX. */
    private static final String COLOR_EMPLOYEE_HEX = "C8F295";

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

    /** The Constant RANGE_DELIVERY_NOTICE_AMOUNT_MANUAL. */
    private static final String RANGE_DELIVERY_NOTICE_AMOUNT_MANUAL = "RangeDeliveryNoticeAmountManual";

    /** The Constant RANGE_CHILD_RAISING_MANUAL. */
    private static final String RANGE_CHILD_RAISING_MANUAL = "RangeChildRaisingManual";

    /** The Constant NUMBER_COLUMN. */
    private static final int NUMBER_COLUMN = 17;

    /** The Constant INDEX_COLUMN_DELIVERY. */
    private static final int INDEX_COLUMN_DELIVERY = 2;

    /** The Constant INDEX_COLUMN_INSURED. */
    private static final int INDEX_COLUMN_INSURED = 7;

    /** The Constant INDEX_COLUMN_EMPLOYEE. */
    private static final int INDEX_COLUMN_EMPLOYEE = 12;

    /** The Constant INDEX_COLUMN_DELIVERY_MANUAL. */
    private static final int INDEX_COLUMN_DELIVERY_MANUAL = 1;

    /** The Constant INDEX_COLUMN_INSURED_MANUAL. */
    private static final int INDEX_COLUMN_INSURED_MANUAL = 12;

    /** The Constant INDEX_COLUMN_EMPLOYEE_MANUAL. */
    private static final int INDEX_COLUMN_EMPLOYEE_MANUAL = 15;

    /** The Constant ALPHABET_A. */
    private static final String ALPHABET_A = "A";

    /** The Constant ALPHABET_Q. */
    private static final String ALPHABET_Q = "Q";

    /** The Constant OFFICE_JP. */
    private static final String OFFICE_JP = "事業所 : ";

    /** The Constant NUMBER_LINE_OF_PAGE. */
    private static final int NUMBER_LINE_OF_PAGE = 61;

    /** The Constant TOTAL_ROW_FOOTER. */
    private static final int TOTAL_ROW_FOOTER = 4;

    /** The Constant COLUMN_WIDTH. */
    private static final double COLUMN_WIDTH = 11.5;
    
    /** The Constant COLUMN_WIDTH_OFFICE_CODE. */
    private static final double COLUMN_WIDTH_OFFICE_CODE = 14;

    /** The Constant indexColumnCategoryInsuItems. */
    private static final List<Integer> indexColumnCategoryInsuItems = new ArrayList<>(
            Arrays.asList(3, 4, 5, 6, 8, 9, 10, 11));

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceGenerator
     * #generate(nts.arc.layer.infra.file.export.FileGeneratorContext,
     * nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData)
     */
    @Override
    public void generate(FileGeneratorContext fileContext, SalarySocialInsuranceReportData reportData) {
        try (val reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            createNewSheet(worksheets, "My Sheet", reportData);
            reportContext.getDesigner().setWorkbook(workbook);
            workbook.calculateFormula(true);
            reportContext.getDesigner().setDataSource("HEADER", Arrays.asList(reportData.getHeaderData()));
            reportContext.processDesigner();
            reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the new sheet.
     *
     * @param worksheets
     *            the worksheets
     * @param sheetName
     *            the sheet name
     * @param reportData
     *            the report data
     * @throws Exception
     *             the exception
     */
    private void createNewSheet(WorksheetCollection worksheets, String sheetName,
            SalarySocialInsuranceReportData reportData) throws Exception {
        Worksheet worksheet = worksheets.get(NUMBER_ZERO);
        worksheet.setName(sheetName);
        worksheet.autoFitColumns();
        int numberOfColumn = NUMBER_COLUMN;

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
        Range rangeDeliveryNoticeAmountManual = worksheets.getRangeByName(RANGE_DELIVERY_NOTICE_AMOUNT_MANUAL);
        Range rangeChildRaisingManual = worksheets.getRangeByName(RANGE_CHILD_RAISING_MANUAL);

        ChecklistPrintSettingDto configOutput = reportData.getConfigureOutput();

        setColumnWidth(worksheet, numberOfColumn, COLUMN_WIDTH);

        // Begin write report
        int indexRowCurrent = writeContentArea(worksheet, reportData, numberOfColumn, mapRange, configOutput);
        int totalRowFooter = TOTAL_ROW_FOOTER;
        if (configOutput.getShowDeliveryNoticeAmount()) {
            int indexColumnDelivery = INDEX_COLUMN_DELIVERY;
            int indexColumnInsured = INDEX_COLUMN_INSURED;
            int indexColumnEmployee = INDEX_COLUMN_EMPLOYEE;
            Range rangeDelivery = rangeDeliveryNoticeAmount;
            Range rangeChild = rangeChildRaising;
            if (!configOutput.getShowCategoryInsuranceItem()) {
                indexColumnDelivery = INDEX_COLUMN_DELIVERY_MANUAL;
                indexColumnInsured = INDEX_COLUMN_INSURED_MANUAL;
                indexColumnEmployee = INDEX_COLUMN_EMPLOYEE_MANUAL;
                rangeDelivery = rangeDeliveryNoticeAmountManual;
                rangeChild = rangeChildRaisingManual;
                double columnWidth = 14;
                setColumnWidth(worksheet, numberOfColumn, columnWidth);
            }
            setFooterPage(worksheet, reportData.getOfficeItems(), numberOfColumn, indexRowCurrent + NUMBER_SECOND,
                    rangeDelivery, rangeChild, indexColumnDelivery, indexColumnInsured, indexColumnEmployee);
        } else {
            totalRowFooter = NUMBER_ZERO;
        }
        showColumnCategoryInsuranceItem(worksheet, configOutput.getShowCategoryInsuranceItem());
        removeRowTemplate(worksheets.getNamedRanges().length, worksheet, mapRange);
        int totalRow = indexRowCurrent + totalRowFooter;
        String printArea = PRINT_AREA.concat(String.valueOf(totalRow));
        settingPage(worksheet, printArea);
        drawBorderLinePageBreak(worksheet, totalRow, numberOfColumn);
    }

    /**
     * Sets the column width.
     *
     * @param worksheet
     *            the worksheet
     * @param numberColumn
     *            the number column
     * @param columnWidth
     *            the column width
     */
    private void setColumnWidth(Worksheet worksheet, int numberColumn, double columnWidth) {
        worksheet.getCells().setColumnWidth(NUMBER_ZERO, COLUMN_WIDTH_OFFICE_CODE);
        for (int i = NUMBER_ONE; i < numberColumn - NUMBER_ONE; i++) {
            worksheet.getCells().setColumnWidth(i, columnWidth);
        }
    }

    /**
     * Show column category insurance item.
     *
     * @param worksheet
     *            the worksheet
     * @param isShowCategoryInsuItem
     *            the is show category insu item
     */
    private void showColumnCategoryInsuranceItem(Worksheet worksheet, boolean isShowCategoryInsuItem) {
        if (isShowCategoryInsuItem) {
            return;
        }
        for (Integer indexColum : indexColumnCategoryInsuItems) {
            worksheet.getCells().hideColumn(indexColum);
        }
    }

    /**
     * Draw border line page break.
     *
     * @param worksheet
     *            the worksheet
     * @param totalRow
     *            the total row
     * @param numberOfColumn
     *            the number of column
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
     * @param worksheet
     *            the worksheet
     * @param printArea
     *            the print area
     */
    private void settingPage(Worksheet worksheet, String printArea) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPrintArea(printArea);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setCenterVertically(false);
    }

    /**
     * Write content area.
     *
     * @param worksheet
     *            the worksheet
     * @param reportData
     *            the report data
     * @param numberOfColumn
     *            the number of column
     * @param mapRange
     *            the map range
     * @param configOutput
     *            the config output
     * @return the int
     * @throws Exception
     *             the exception
     */
    private int writeContentArea(Worksheet worksheet, SalarySocialInsuranceReportData reportData, int numberOfColumn,
            HashMap<String, Range> mapRange, ChecklistPrintSettingDto configOutput) throws Exception {
        List<InsuranceOfficeDto> offices = reportData.getOfficeItems();
        int indexRowHeaderOffice = INDEX_ROW_CONTENT_AREA;
        for (InsuranceOfficeDto office : offices) {
            int indexRowContentOffice = indexRowHeaderOffice;
            setHeaderOffice(worksheet, office, numberOfColumn, indexRowHeaderOffice, mapRange.get(RANGE_OFFICE));
            indexRowContentOffice += NUMBER_ONE;
            int indexRowFooterOffice = indexRowContentOffice;
            if (configOutput.getShowDetail()) {
                setContentOffice(worksheet, office, numberOfColumn, indexRowContentOffice,
                        mapRange.get(RANGE_EMPLOYEE));
                indexRowFooterOffice += office.getEmployeeDtos().size();
            }
            indexRowHeaderOffice = indexRowFooterOffice;
            // setting show total each of office.
            if (configOutput.getShowOffice()) {
                setFooterEachOffice(worksheet, office, numberOfColumn, indexRowContentOffice, indexRowFooterOffice,
                        mapRange.get(RANGE_FOOTER_EACH_OFFICE));
                indexRowHeaderOffice += NUMBER_ONE;
            }
        }
        // setting show total all office.
        if (configOutput.getShowTotal()) {
            setSummaryOffice(worksheet, reportData.getTotalAllOffice(), indexRowHeaderOffice,
                    mapRange.get(RANGE_FOOTER_EACH_OFFICE));
        }
        return indexRowHeaderOffice;
    }

    /**
     * Sets the header office.
     *
     * @param worksheet
     *            the worksheet
     * @param office
     *            the office
     * @param numberOfColumn
     *            the number of column
     * @param indexRowHeader
     *            the index row header
     * @param rangeOffice
     *            the range office
     * @throws Exception
     *             the exception
     */
    private void setHeaderOffice(Worksheet worksheet, InsuranceOfficeDto office, int numberOfColumn, int indexRowHeader,
            Range rangeOffice) throws Exception {
        Range newRange = createRangeFromOtherRange(worksheet, indexRowHeader, rangeOffice);
        String officeCode = OFFICE_JP.concat(office.getCode());
        setDataRangeFirstRow(worksheet, newRange, NUMBER_ZERO, officeCode, office.getName());
    }

    /**
     * Sets the content office.
     *
     * @param worksheet
     *            the worksheet
     * @param office
     *            the office
     * @param numberOfColumn
     *            the number of column
     * @param indexRow
     *            the index row
     * @param rangeEmployee
     *            the range employee
     * @throws Exception
     *             the exception
     */
    private void setContentOffice(Worksheet worksheet, InsuranceOfficeDto office, int numberOfColumn, int indexRow,
            Range rangeEmployee) throws Exception {
        for (int i = 0; i < office.getEmployeeDtos().size(); i++) {
            boolean isForegroundColor = false;
            if (i % NUMBER_SECOND != 0) {
                isForegroundColor = true;
            }
            DataRowItem employee = office.getEmployeeDtos().get(i);
            Range newRange = createRangeFromOtherRange(worksheet, indexRow, rangeEmployee);
            setDataEmployee(worksheet, employee, newRange, isForegroundColor);
            indexRow++;
        }
    }

    /**
     * Sets the footer each office.
     *
     * @param worksheet
     *            the worksheet
     * @param office
     *            the office
     * @param numberOfColumn
     *            the number of column
     * @param indexRowBeginEmployee
     *            the index row begin employee
     * @param indexRow
     *            the index row
     * @param rangeFooterEachOffice
     *            the range footer each office
     * @throws Exception
     *             the exception
     */
    private void setFooterEachOffice(Worksheet worksheet, InsuranceOfficeDto office, int numberOfColumn,
            int indexRowBeginEmployee, int indexRow, Range rangeFooterEachOffice) throws Exception {
        int numberEmployeeOffice = office.getEmployeeDtos().size();
        String totalEmployeeOffice = String.valueOf(numberEmployeeOffice).concat(" 人");
        Range newRange = createRangeFromOtherRange(worksheet, indexRow, rangeFooterEachOffice);
        setDataRangeFirstRow(worksheet, newRange, NUMBER_ZERO, "事業所　計", totalEmployeeOffice);
        setDataTotal(worksheet, newRange, office.getTotalEachOffice());
    }

    /**
     * Sets the summary office.
     *
     * @param worksheet
     *            the worksheet
     * @param totalAllOffice
     *            the total all office
     * @param indexRowCurrent
     *            the index row current
     * @param rangeFooterOffice
     *            the range footer office
     * @throws Exception
     *             the exception
     */
    private void setSummaryOffice(Worksheet worksheet, DataRowItem totalAllOffice, int indexRowCurrent,
            Range rangeFooterOffice) throws Exception {
        Range newRange = createRangeFromOtherRange(worksheet, indexRowCurrent, rangeFooterOffice);
        setDataRangeFirstRow(worksheet, newRange, NUMBER_ZERO, "総合計");
        setDataTotal(worksheet, newRange, totalAllOffice);
    }

    /**
     * Sets the footer page.
     *
     * @param worksheet
     *            the worksheet
     * @param offices
     *            the offices
     * @param numberOfColumn
     *            the number of column
     * @param indexRow
     *            the index row
     * @param rangeDeliveryNoticeAmount
     *            the range delivery notice amount
     * @param rangeChildRaising
     *            the range child raising
     * @param indexColumnDelivery
     *            the index column delivery
     * @param indexColumnInsured
     *            the index column insured
     * @param indexColumnEmployee
     *            the index column employee
     * @throws Exception
     *             the exception
     */
    private void setFooterPage(Worksheet worksheet, List<InsuranceOfficeDto> offices, int numberOfColumn, int indexRow,
            Range rangeDeliveryNoticeAmount, Range rangeChildRaising, int indexColumnDelivery, int indexColumnInsured,
            int indexColumnEmployee) throws Exception {
        Range newRangeDeliveryNoticeAmount = createRangeFromOtherRange(worksheet, indexRow, rangeDeliveryNoticeAmount);
        indexRow++;
        Range newRangeChildRaising = createRangeFromOtherRange(worksheet, indexRow, rangeChildRaising);
        setDataRangeFirstRow(worksheet, newRangeDeliveryNoticeAmount, indexColumnDelivery, 80);
        setDataRangeFirstRow(worksheet, newRangeDeliveryNoticeAmount, indexColumnInsured, 30);
        // setDataRangeFirstRow(worksheet, newRange, 3, "納入告知額", 30);
        setDataRangeRowCalculateSub(worksheet, newRangeDeliveryNoticeAmount, newRangeChildRaising, indexColumnDelivery,
                indexColumnInsured, indexColumnEmployee);
    }

    /**
     * Sets the data employee.
     *
     * @param worksheet
     *            the worksheet
     * @param rowData
     *            the row data
     * @param range
     *            the range
     * @param isForegroundColor
     *            the is foreground color
     */
    @SuppressWarnings("rawtypes")
    private void setDataEmployee(Worksheet worksheet, DataRowItem rowData, Range range, boolean isForegroundColor) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = range.getFirstRow();
        List valueOfAttributes = convertObjectToList(rowData);
        int numberColumn = valueOfAttributes.size();
        for (int i = 0; i < numberColumn; i++) {
            Object item = valueOfAttributes.get(i);
            cells.get(indexRowCurrent, i).setValue(item);
            if (isForegroundColor) {
                setForegroundColorByRow(worksheet, indexRowCurrent, i);
            }
        }
    }

    /**
     * Sets the data range first row.
     *
     * @param worksheet
     *            the worksheet
     * @param range
     *            the range
     * @param indexColumnBegin
     *            the index column begin
     * @param data
     *            the data
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
     * @param worksheet
     *            the worksheet
     * @param range
     *            the range
     * @param total
     *            the total
     */
    @SuppressWarnings("rawtypes")
    private void setDataTotal(Worksheet worksheet, Range range, DataRowItem total) {
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
     * @param worksheet
     *            the worksheet
     * @param newRangeDeliveryNoticeAmount
     *            the new range delivery notice amount
     * @param newRangeChildRaising
     *            the new range child raising
     * @param indexColumnDelivery
     *            the index column delivery
     * @param indexColumnInsured
     *            the index column insured
     * @param indexColumnEmployee
     *            the index column employee
     */
    private void setDataRangeRowCalculateSub(Worksheet worksheet, Range newRangeDeliveryNoticeAmount,
            Range newRangeChildRaising, int indexColumnDelivery, int indexColumnInsured, int indexColumnEmployee) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = newRangeDeliveryNoticeAmount.getFirstRow();
        String cellStart = cells.get(indexRowCurrent, indexColumnDelivery).getName();
        String cellEnd = cells.get(indexRowCurrent, indexColumnInsured).getName();
        String formulaSubtract = cellStart.concat("-").concat(cellEnd);
        cells.get(indexRowCurrent, indexColumnEmployee).setFormula(formulaSubtract);
        cells.get(newRangeChildRaising.getFirstRow(), indexColumnEmployee).setValue(20);
    }

    /**
     * Creates the range from other range.
     *
     * @param worksheet
     *            the worksheet
     * @param indexRow
     *            the index row
     * @param range
     *            the range
     * @return the range
     * @throws Exception
     *             the exception
     */
    private Range createRangeFromOtherRange(Worksheet worksheet, int indexRow, Range range) throws Exception {
        Cells cells = worksheet.getCells();
        String cellStart = ALPHABET_A.concat(String.valueOf(indexRow));
        String cellEnd = ALPHABET_Q.concat(String.valueOf(indexRow));
        Range newRange = cells.createRange(cellStart, cellEnd);
        newRange.copy(range);
        return newRange;
    }

    /**
     * Removes the row template.
     *
     * @param totalRange
     *            the total range
     * @param worksheet
     *            the worksheet
     * @param mapRange
     *            the map range
     */
    private void removeRowTemplate(int totalRange, Worksheet worksheet, HashMap<String, Range> mapRange) {
        int count = NUMBER_ZERO;
        while (count <= totalRange) {
            worksheet.getCells().deleteRow(mapRange.get(RANGE_OFFICE).getFirstRow());
            count++;
        }
    }

    /**
     * Draw border line.
     *
     * @param worksheet
     *            the worksheet
     * @param indexRow
     *            the index row
     * @param numberColumn
     *            the number column
     * @param offset
     *            the offset
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
     * @param worksheet
     *            the worksheet
     * @param indexRow
     *            the index row
     * @param indexColumn
     *            the index column
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
        if (obj == null) {
            return null;
        }
        if (obj instanceof DataRowItem) {
            obj = (DataRowItem) obj;
        }
        List valueOfAttributes = new ArrayList<>();
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
