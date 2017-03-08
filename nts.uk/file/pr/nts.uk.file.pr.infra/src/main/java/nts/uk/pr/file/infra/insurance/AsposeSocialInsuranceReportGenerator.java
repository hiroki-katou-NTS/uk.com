/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance;

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
import nts.uk.file.pr.app.export.insurance.data.ColumnInformation;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuranceItemDto;
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
    private static final String REPORT_FILE_NAME = "SocialInsurance.pdf";
    private static final String FILE_EXCEL = "SocialInsurance.xlsx";
    private static final String PRINT_AREA = "A1:H63";
    private static final int NUMBER_ONE = 1;
    private static final int INDEX_TITLE_ROW = 0;
    private static final int COLUMN_WIDTH = 20;

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

    private void createNewSheet(WorksheetCollection worksheets, String sheetName,
            SocialInsuranceReportData dataSource) {
        int sheetLastIndex = worksheets.add();
        Worksheet worksheet = worksheets.get(sheetLastIndex);
        worksheet.setName(sheetName);
        settingPage(worksheet);
        setContentHeader();
        List<ColumnInformation> columns = dataSource.getColumns();
        int numberOfColumn = columns.size();
        setColumnWidth(worksheet, numberOfColumn);
        setTitleRow(worksheet, columns);
        setContentArea(worksheet, dataSource);
    }
    
    private void settingPage(Worksheet worksheet) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setPrintArea(PRINT_AREA);
        pageSetup.setFitToPagesTall(NUMBER_ONE);
        pageSetup.setFitToPagesWide(NUMBER_ONE);
        pageSetup.setCenterHorizontally(true);
    }

    private void setContentHeader() {
        // TODO: set header
    }

    private void setColumnWidth(Worksheet worksheet, int numberOfColumn) {
        Cells cells = worksheet.getCells();
        for (int i = 0; i < numberOfColumn; i++) {
            cells.setColumnWidth(i, COLUMN_WIDTH);
        }
    }

    private void setTitleRow(Worksheet worksheet, List<ColumnInformation> columns) {
        Style style = getStyleCell();
        Cells cells = worksheet.getCells();
        for (int i = 0; i < columns.size(); i++) {
            cells.get(INDEX_TITLE_ROW, i).setStyle(style);
            String columnName = columns.get(i).getColumnName();
            cells.get(INDEX_TITLE_ROW, i).setValue(columnName);
        }
    }

    private void setContentArea(Worksheet worksheet, SocialInsuranceReportData dataSource) {
        Cells cells = worksheet.getCells();
        Style style = getStyleCell();
        List<ColumnInformation> columns = dataSource.getColumns();
        for (int i = 0; i < dataSource.reportItems.size(); i++) {
            if (i % 2 == 0) {
                style.setForegroundColor(Color.getGreen());
            }
            int indexContent = i + NUMBER_ONE;
            SocialInsuranceItemDto item = dataSource.reportItems.get(i);
            List<String> attributes = convertObjectToList(item);
            for (int j = 0; j < columns.size(); j++) {
                cells.get(indexContent, j).setStyle(style);
                cells.get(indexContent, j).setValue(attributes.get(j));
            }
        }
    }

    private Style getStyleCell() {
        Style style = new Style();
        style.setPattern(BackgroundType.SOLID);
        style.setBackgroundColor(Color.getGreen());
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        // set font
        style.getFont().setName("Arial");
        style.getFont().setColor(Color.getBlack());
        return style;
    }
    
    @SuppressWarnings("unchecked")
    private List<String> convertObjectToList(SocialInsuranceItemDto data) {
        List<String> attributes = new ArrayList<>();
        ObjectMapper oMapper = new ObjectMapper();
        HashMap<String, Object> mapObject = oMapper.convertValue(data, HashMap.class);
        for(String key : mapObject.keySet()) {
            String item = (String) mapObject.get(key);
            attributes.add(item);
        }
        return attributes;
    }
}
