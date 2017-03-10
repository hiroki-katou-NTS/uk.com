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

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.pr.app.export.insurance.SocialInsuranceGenerator;
import nts.uk.file.pr.app.export.insurance.data.EmployeeDto;
import nts.uk.file.pr.app.export.insurance.data.OfficeDto;
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

    @Override
    public void generate(FileGeneratorContext fileContext, SocialInsuranceReportData reportData) {
        try (val reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            createNewSheet(worksheets, "My Sheet", reportData);
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.processDesigner();
            workbook.save(FILE_EXCEL);
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
//        worksheet.getCells().hideColumn(16);
//        numberOfColumn = 16;
        setHeaderPage();
        setContentArea(worksheet, reportData.getOfficeItems(), numberOfColumn);
        setFooterPage(worksheet, reportData.getOfficeItems());
    }

    private void settingPage(Worksheet worksheet) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setFitToPagesTall(NUMBER_ONE);
        pageSetup.setFitToPagesWide(NUMBER_ONE);
        pageSetup.setCenterHorizontally(true);
    }

    private void setHeaderPage() {
        // TODO: set header
    }

    private void setContentArea(Worksheet worksheet, List<OfficeDto> offices, int numberOfColumn) {
        int indexRowHeaderOffice = INDEX_ROW_CONTENT_AREA;
        for (OfficeDto office : offices) {
            setHeaderOffice(worksheet, office, numberOfColumn, indexRowHeaderOffice);
            int indexRowContentOffice = indexRowHeaderOffice + NUMBER_ONE;
            setContentOffice(worksheet, office, numberOfColumn, indexRowContentOffice);
            int indexRowFooterOffice = indexRowContentOffice + office.getEmployeeDtos().size();
            setFooterOffice(worksheet, office, numberOfColumn, indexRowContentOffice, indexRowFooterOffice);
            indexRowHeaderOffice = indexRowFooterOffice + NUMBER_ONE;
        }
    }

    private void setHeaderOffice(Worksheet worksheet, OfficeDto office, int numberOfColumn, int indexRowHeader) {
        drawBorderColumnEmployee(worksheet, true, indexRowHeader, numberOfColumn, office.getOfficeCode(),
                office.getOfficeName());
    }

    private void setContentOffice(Worksheet worksheet, OfficeDto office, int numberOfColumn, int indexRow) {
        for (int i = 0; i < office.getEmployeeDtos().size(); i++) {
            EmployeeDto employee = office.getEmployeeDtos().get(i);
            Color colorRow = Color.getWhite();
            if (i % NUMBER_SECOND != 0) {
                colorRow = Color.getGreenYellow();
            }
            setRowData(worksheet, employee, indexRow, colorRow);
            int indexColumnLast = numberOfColumn - NUMBER_ONE;
            drawBorderCell(worksheet, indexRow, indexColumnLast, BorderType.RIGHT_BORDER);
            indexRow++;
        }
    }

    private void setFooterOffice(Worksheet worksheet, OfficeDto office, int numberOfColumn, int indexRowBeginEmployee, int indexRow) {
        int numberEmployeeOffice = office.getEmployeeDtos().size();
        String totalEmployeeOffice = String.valueOf(numberEmployeeOffice).concat("人");
        drawBorderColumnEmployee(worksheet, true, indexRow, numberOfColumn, "事業所　計", totalEmployeeOffice);
        drawRowCalculation(worksheet, true, indexRow, numberOfColumn, indexRowBeginEmployee);
    }

    private void setFooterPage(Worksheet worksheet, List<OfficeDto> offices) {
    }

    @SuppressWarnings("rawtypes")
    private void setRowData(Worksheet worksheet, EmployeeDto rowData, int indexRow, Color colorRow) {
        Cells cells = worksheet.getCells();
        List valueOfAttributes = convertObjectToList(rowData);
        int numOfColumn = valueOfAttributes.size();
        for (int j = 0; j < numOfColumn; j++) {
            Style style = cells.get(indexRow, j).getStyle();
            style.setForegroundColor(colorRow);
            cells.get(indexRow, j).setStyle(style);
            if (j < NUMBER_SECOND) {
                String employeeCode = String.valueOf(valueOfAttributes.get(NUMBER_ZERO));
                String employeeName = String.valueOf(valueOfAttributes.get(NUMBER_ONE));
                drawBorderColumnEmployee(worksheet, false, indexRow, numOfColumn, employeeCode, employeeName);
                j = NUMBER_ONE;
                cells.get(indexRow, NUMBER_ONE).setStyle(style);
            } else {
                drawBorderCell(worksheet, indexRow, j, BorderType.RIGHT_BORDER);
                cells.get(indexRow, j).setValue(valueOfAttributes.get(j));
            }
        }
    }

    private void drawRowCalculation(Worksheet worksheet, boolean isOffice, int indexRow, int numberOfColumn,
            int indexRowBeginEmployee) {
        Cells cells = worksheet.getCells();
        for (int i = NUMBER_SECOND; i < numberOfColumn; i++) {
            String cellStart = cells.get(indexRowBeginEmployee, i).getName();;
            int indexPrevious = indexRow - NUMBER_ONE;
            String cellEnd = cells.get(indexPrevious, i).getName();
            String formulaCalculateSum = "SUM(" + cellStart + ":" + cellEnd + ")";
            cells.get(indexRow, i).setFormula(formulaCalculateSum);
        }
    }
    
    private void drawBorderColumnEmployee(Worksheet worksheet, boolean isOffice, int indexRow, int numberOfColumn,
            String... data) {
        Cells cells = worksheet.getCells();
        cells.get(indexRow, NUMBER_ZERO).setValue(data[NUMBER_ZERO]);
        cells.get(indexRow, NUMBER_ONE).setValue(data[NUMBER_ONE]);
        if (isOffice) {
            drawBorderLine(worksheet, indexRow, numberOfColumn, BorderType.TOP_BORDER);
            drawBorderLine(worksheet, indexRow, numberOfColumn, BorderType.BOTTOM_BORDER);
        }
        drawBorderCell(worksheet, indexRow, NUMBER_ZERO, BorderType.LEFT_BORDER);
        drawBorderCell(worksheet, indexRow, NUMBER_ONE, BorderType.RIGHT_BORDER);
        int indexColumnLast = numberOfColumn - NUMBER_ONE;
        drawBorderCell(worksheet, indexRow, indexColumnLast, BorderType.RIGHT_BORDER);
    }

    private void drawBorderLine(Worksheet worksheet, int indexRow, int number, int offset) {
        Cells cells = worksheet.getCells();
        for (int i = 0; i < number; i++) {
            Style style = cells.get(indexRow, i).getStyle();
            style.setPattern(BackgroundType.SOLID);
            style.setBackgroundColor(Color.getGreen());
            style.setBorder(offset, CellBorderType.THIN, Color.getBlack());

            cells.get(indexRow, i).setStyle(style);
        }
    }

    private void drawBorderCell(Worksheet worksheet, int indexRow, int indexColumn, int offset) {
        Cells cells = worksheet.getCells();
        Style style = cells.get(indexRow, indexColumn).getStyle();
        style.setPattern(BackgroundType.SOLID);
        style.setBackgroundColor(Color.getGreen());
        style.setBorder(offset, CellBorderType.THIN, Color.getBlack());

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
        ObjectMapper oMapper = new ObjectMapper();
        HashMap<String, Object> mapObject = oMapper.convertValue(obj, HashMap.class);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object attr = field.getName();
            Object val = mapObject.get(attr);
            Object item = null;
            if (val == null) {
                item = "";
            }
            if (val instanceof String) {
                item = (String) val;
            }
            if (val instanceof Double) {
                item = (Double) val;
            }
            valueOfAttributes.add(item);
        }
        return valueOfAttributes;
    }
}
