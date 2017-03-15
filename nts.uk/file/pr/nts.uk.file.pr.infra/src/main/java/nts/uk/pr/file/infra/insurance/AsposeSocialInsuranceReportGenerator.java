/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Range;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.gul.reflection.ReflectionUtil;
import nts.uk.file.pr.app.export.insurance.SocialInsuranceGenerator;
import nts.uk.file.pr.app.export.insurance.data.EmployeeDto;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceReportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * @author duongnd
 *
 */

@Stateless
public class AsposeSocialInsuranceReportGenerator extends AsposeCellsReportGenerator
        implements SocialInsuranceGenerator {

    private static final String TEMPLATE_FILE = "report/SocialInsurance.xlsx";
    private static final String REPORT_FILE_NAME = "/Users/mrken57/Work/UniversalK/project/export/SocialInsurance.pdf";
    private static final String FILE_EXCEL = "/Users/mrken57/Work/UniversalK/project/export/SocialInsurance_Export.xlsx";
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_SECOND = 2;
    private static final int INDEX_ROW_CONTENT_AREA = 10;
    private static final int RADIX = 16;
    private static final String COLOR_EMPLOYEE_HEX = "C8F295";
    private static final String RANGE_OFFICE = "RangeOffice";
    private static final String RANGE_EMPLOYEE = "RangeEmployee";
    private static final String RANGE_FOOTER_EACH_OFFICE = "RangeFooterEachOffice";
    private static final String RANGE_FOOTER_PAGE = "RangeFooterPage";
    private static final String ALPHABET_A = "A";
    private static final String ALPHABET_Q = "Q";
    
    @Override
    public void generate(FileGeneratorContext fileContext, SocialInsuranceReportData reportData) {
        try (val reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            createNewSheet(worksheets, "My Sheet", reportData);
            reportContext.getDesigner().setWorkbook(workbook);
            workbook.calculateFormula(true);
            reportContext.processDesigner();
//            workbook.save(FILE_EXCEL);
            reportContext.saveAsPdf(this.createNewFile(fileContext, REPORT_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewSheet(WorksheetCollection worksheets, String sheetName, SocialInsuranceReportData reportData)
            throws Exception {
        Worksheet worksheet = worksheets.get(0);
        worksheet.setName(sheetName);
        worksheet.autoFitColumns();
        settingPage(worksheet);
        int numberOfColumn = 17;
        
        HashMap<String, Range> mapRange = new HashMap<String, Range>();
        // get range from template then remove it.
        Range rangeOffice = worksheets.getRangeByName(RANGE_OFFICE);
        mapRange.put(RANGE_OFFICE, rangeOffice);
        Range rangeEmployee = worksheets.getRangeByName(RANGE_EMPLOYEE);
        mapRange.put(RANGE_EMPLOYEE, rangeEmployee);
        Range rangeFooterEachOffice = worksheets.getRangeByName(RANGE_FOOTER_EACH_OFFICE);
        mapRange.put(RANGE_FOOTER_EACH_OFFICE, rangeFooterEachOffice);
        Range rangeFooterPage = worksheets.getRangeByName(RANGE_FOOTER_PAGE);
        
        int indexRowCurrent = writeContentArea(worksheet, reportData.getOfficeItems(), numberOfColumn, mapRange);
        setFooterPage(worksheet, reportData.getOfficeItems(), numberOfColumn, indexRowCurrent + NUMBER_ONE, rangeFooterPage);
        removeRowTemplate(worksheets.getNamedRanges().length, worksheet, mapRange);
    }

    private void settingPage(Worksheet worksheet) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setFitToPagesTall(NUMBER_ONE);
        pageSetup.setFitToPagesWide(NUMBER_ONE);
        pageSetup.setCenterHorizontally(true);
        pageSetup.setPaperSize(75);
    }

    private int writeContentArea(Worksheet worksheet, List<InsuranceOfficeDto> offices, int numberOfColumn, HashMap<String, Range> mapRange) throws Exception {
        int indexRowHeaderOffice = INDEX_ROW_CONTENT_AREA + 6;
        for (InsuranceOfficeDto office : offices) {
            setHeaderOffice(worksheet, office, numberOfColumn, indexRowHeaderOffice, mapRange.get(RANGE_OFFICE));
            int indexRowContentOffice = indexRowHeaderOffice + NUMBER_ONE;
            setContentOffice(worksheet, office, numberOfColumn, indexRowContentOffice, mapRange.get(RANGE_EMPLOYEE));
            int indexRowFooterOffice = indexRowContentOffice + office.getEmployeeDtos().size();
            setFooterEachOffice(worksheet, office, numberOfColumn, indexRowContentOffice, indexRowFooterOffice,
                    mapRange.get(RANGE_FOOTER_EACH_OFFICE));
            indexRowHeaderOffice = indexRowFooterOffice + NUMBER_ONE;
        }
        setSummaryOffice(worksheet, numberOfColumn, indexRowHeaderOffice, mapRange.get(RANGE_FOOTER_EACH_OFFICE));
        return indexRowHeaderOffice;
    }

    private void setHeaderOffice(Worksheet worksheet, InsuranceOfficeDto office, int numberOfColumn, int indexRowHeader, Range rangeOffice) throws Exception {
        Range newRange = createRangeFromOtherRange(worksheet, indexRowHeader, rangeOffice);
        setDataRangeFirstRow(worksheet, newRange, NUMBER_ZERO, office.getOfficeCode(), office.getOfficeName());
    }

    private void setContentOffice(Worksheet worksheet, InsuranceOfficeDto office, int numberOfColumn, int indexRow,
            Range rangeEmployee) throws Exception {
        for (int i = 0; i < office.getEmployeeDtos().size(); i++) {
            boolean isForegroundColor = false;
            if (i %  NUMBER_SECOND != 0) {
                isForegroundColor = true;
            }
            EmployeeDto employee = office.getEmployeeDtos().get(i);
            Range newRange = createRangeFromOtherRange(worksheet, indexRow, rangeEmployee);
            setDataRangeFirstRow(worksheet, employee, newRange, isForegroundColor);
            indexRow++;
        }
    }

    private void setFooterEachOffice(Worksheet worksheet, InsuranceOfficeDto office, int numberOfColumn, int indexRowBeginEmployee,
            int indexRow, Range rangeFooterEachOffice) throws Exception {
        int numberEmployeeOffice = office.getEmployeeDtos().size();
        String totalEmployeeOffice = String.valueOf(numberEmployeeOffice).concat(" 人");
        Range newRange = createRangeFromOtherRange(worksheet, indexRow, rangeFooterEachOffice);
        setDataRangeFirstRow(worksheet, newRange, NUMBER_ZERO, "事業所　計", totalEmployeeOffice);
        setDataRangeRowCalculationSum(worksheet, newRange, indexRowBeginEmployee);
    }
    
    private void setSummaryOffice(Worksheet worksheet, int numberOfColumn, int indexRowCurrent, Range rangeFooterOffice) throws Exception {
        Range newRange = createRangeFromOtherRange(worksheet, indexRowCurrent, rangeFooterOffice);
        setDataRangeFirstRow(worksheet, newRange, NUMBER_ZERO, "総合計");
    }

    private void setFooterPage(Worksheet worksheet, List<InsuranceOfficeDto> offices, int numberOfColumn, int indexRow,
            Range rangeFooterPage) throws Exception {
        Range newRange = createRangeFromOtherRange(worksheet, indexRow, rangeFooterPage);
        // TODO: hardcode
        setDataRangeFirstRow(worksheet, newRange, 3, 80);
        setDataRangeFirstRow(worksheet, newRange, 8, 30);
        setDataRangeRowCalculateSub(worksheet, newRange);
    }

    @SuppressWarnings("rawtypes")
    private void setDataRangeFirstRow(Worksheet worksheet, EmployeeDto rowData, Range range, boolean isForegroundColor) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = range.getFirstRow();
        List valueOfAttributes = convertObjectToList(rowData);
        for (int i = 0; i < valueOfAttributes.size(); i++) {
            Object item = valueOfAttributes.get(i);
            cells.get(indexRowCurrent, i).setValue(item);
            if (isForegroundColor) {
                setForegroundColorByRow(worksheet, indexRowCurrent, i);
            }
        }
    }
    
    private void setDataRangeFirstRow(Worksheet worksheet, Range range, int indexColumnBegin, Object...data) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = range.getFirstRow();
        int index = NUMBER_ZERO;
        while(index < data.length) {
            int indexColumn = indexColumnBegin + index;
            cells.get(indexRowCurrent, indexColumn).setValue(data[index]);
            index++;
        }
    }
    
    private void setDataRangeRowCalculationSum(Worksheet worksheet, Range range, int indexRowBeginEmployee) {
        Cells cells = worksheet.getCells();
        int numColumnNeedCalculate = range.getColumnCount();
        int indexRowCurrent = range.getFirstRow();
        for (int i = NUMBER_SECOND; i < numColumnNeedCalculate; i++) {
            String cellStart = cells.get(indexRowBeginEmployee - NUMBER_ONE, i).getName();
            int indexPrevious = indexRowCurrent - NUMBER_ONE;
            String cellEnd = cells.get(indexPrevious, i).getName();
            String formulaCalculateSum = "SUM(" + cellStart + ":" + cellEnd + ")";
            cells.get(indexRowCurrent, i).setFormula(formulaCalculateSum);
        }
    }
    
    private void setDataRangeRowCalculateSub(Worksheet worksheet, Range rangeFooterPage) {
        Cells cells = worksheet.getCells();
        int indexRowCurrent = rangeFooterPage.getFirstRow();
        // TODO: hardcode
        String cellStart = cells.get(indexRowCurrent, 3).getName();
        String cellEnd = cells.get(indexRowCurrent, 8).getName();
        String formulaSubtract = cellStart.concat("-").concat(cellEnd);
        cells.get(indexRowCurrent, 13).setFormula(formulaSubtract);
    }
    
    private Range createRangeFromOtherRange(Worksheet worksheet, int indexRow, Range range) throws Exception {
        Cells cells = worksheet.getCells();
        String cellStart = ALPHABET_A.concat(String.valueOf(indexRow));
        String cellEnd = ALPHABET_Q.concat(String.valueOf(indexRow));
        Range newRange = cells.createRange(cellStart, cellEnd);
        newRange.copy(range);
        return newRange;
    }
    
    private void removeRowTemplate(int totalRange, Worksheet worksheet, HashMap<String, Range> mapRange) {
        int count = NUMBER_ZERO;
        while(count <= totalRange) {
            worksheet.getCells().deleteRow(mapRange.get(RANGE_OFFICE).getFirstRow());
            count++;
        }
    }

    private void setForegroundColorByRow(Worksheet worksheet, int indexRow, int indexColumn) {
        Cells cells = worksheet.getCells();
        Style style = cells.get(indexRow, indexColumn).getStyle();
        style.setForegroundArgbColor(Integer.parseInt(COLOR_EMPLOYEE_HEX, RADIX));
        cells.get(indexRow, indexColumn).setStyle(style);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List convertObjectToList(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof EmployeeDto) {
            obj = (EmployeeDto) obj;
        }
        List valueOfAttributes = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object val = ReflectionUtil.getFieldValue(field, obj);
            Object item = null;
            if (val == null) {
                item = "";
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
