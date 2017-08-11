/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.export;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.PageSetup;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.dto.ExternalBudgetErrorDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.export.ExportData;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.export.ExtBudgetErrorGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * The Class ExtBudgetErrorExportGenerator.
 */
@Stateless
public class ExtBudgetErrorExportGenerator extends AsposeCellsReportGenerator implements ExtBudgetErrorGenerator {
    
    /** The Constant TEMPLATE_FILE. */
    private static final String TEMPLATE_FILE = "export/KSU006.xlsx";
    
    /** The Constant EXPORT_FILE_NAME. */
    private static final String EXPORT_FILE_NAME = "KSU006.xlsx";
    
    private static final String EXTENSION_FILE = ".xlsx";
    
    /** The Constant SHEET_NAME. */
    private static final String SHEET_NAME = "Sheet 1";
    
    /** The Constant PRINT_AREA. */
    private static final String PRINT_AREA = "A1:F";
    
    /** The Constant DEFAULT_VALUE. */
    private static final int DEFAULT_VALUE = 0;
    
    /** The Constant INDEX_HEADER. */
    private static final int INDEX_HEADER = 0;
    
    /** The Constant INDEX_CONTENT. */
    private static final int INDEX_CONTENT = 1;
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.export.
     * ExtBudgetErrorGenerator#generate(nts.arc.layer.infra.file.export.
     * FileGeneratorContext)
     */
    @Override
    public void generate(FileGeneratorContext fileContext, ExportData exportData) {
        try (val reportContext = this.createContext(TEMPLATE_FILE)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            
            // create new sheet
            createNewSheet(worksheets, exportData);
            
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.processDesigner();
            
            // save excel file
//            workbook.save(this.getFileName(exportData.employeeId));
            reportContext.saveAsExcel(this.createNewFile(fileContext, this.getFileName(exportData.employeeId)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates the new sheet.
     *
     * @param worksheets the worksheets
     * @param exportData the export data
     */
    private void createNewSheet(WorksheetCollection worksheets, ExportData exportData) {
        // get worksheet
        Worksheet worksheet = worksheets.get(DEFAULT_VALUE);
        
        // set sheet name
        worksheet.setName(SHEET_NAME);
        
        // begin write data
        this.writeData(worksheet, exportData);
        
        // setting page
        this.settingPage(worksheet, exportData.lstError.size());
    }
    
    /**
     * Sets the ting page.
     *
     * @param worksheet the new ting page
     */
    private void settingPage(Worksheet worksheet, int totalColumn) {
        PageSetup pageSetup = worksheet.getPageSetup();
        String printArea = PRINT_AREA + totalColumn + 1;
        pageSetup.setPrintArea(printArea);
//        pageSetup.setHeader(0,"&\"IPAPGothic\"&11 " + header.getNameCompany());
    }
    
    /**
     * Write data.
     *
     * @param worksheet the worksheet
     * @param exportData the export data
     */
    private void writeData(Worksheet worksheet, ExportData exportData) {
        // write header
        this.writeHeader(worksheet, exportData.lstHeader);
        
        // write content error
        this.writeContent(worksheet, exportData.lstError);
    }
    
    /**
     * Write header.
     *
     * @param worksheet the worksheet
     * @param lstHeader the lst header
     */
    private void writeHeader(Worksheet worksheet, List<String> lstHeader) {
        Cells cells = worksheet.getCells();
        for (int i = 0; i < lstHeader.size(); i++) {
            Cell cell = cells.get(INDEX_HEADER, i);
            this.setStyleCell(cell);
            cell.setValue(lstHeader.get(i));
        }
    }
    
    /**
     * Write content.
     *
     * @param worksheet the worksheet
     * @param lstError the lst error
     */
    private void writeContent(Worksheet worksheet, List<ExternalBudgetErrorDto> lstError) {
        Cells cells = worksheet.getCells();
        int indexRow = INDEX_CONTENT;
        for (ExternalBudgetErrorDto record : lstError) {
            this.setDataRecord(cells, indexRow, record);
            indexRow++;
        }
    }
    
    /**
     * Sets the data record.
     *
     * @param cells the cells
     * @param indexRow the index row
     * @param data the data
     */
    @SuppressWarnings("rawtypes")
    private void setDataRecord(Cells cells, int indexRow, ExternalBudgetErrorDto data) {
        List lstValueColumn = this.convertObjectToList(data);
        for (int i = 0; i < lstValueColumn.size(); i++) {
            Cell cell = cells.get(indexRow, i);
            this.setStyleCell(cell);
            cell.setValue(lstValueColumn.get(i));
        }
    }
    
    /**
     * Sets the cell data.
     *
     * @param cell the cell
     * @param value the value
     */
    private void setStyleCell(Cell cell) {
        // new style
        Style style = cell.getStyle();
        style.setPattern(BackgroundType.SOLID);
        style.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        style.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        style.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        style.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        
        // set style for cell
        cell.setStyle(style);
    }
    
    /**
     * Gets the file name.
     *
     * @param employeeId the employee id
     * @return the file name
     */
    private String getFileName(String employeeId) {
        String fileName = this.getReportName(EXPORT_FILE_NAME);
        String[] lstComponent = fileName.split(EXTENSION_FILE);
        return String.format("%s_%s.%s", lstComponent[0], employeeId, EXTENSION_FILE);
    }
    
    /**
     * Convert object to list.
     *
     * @param obj the obj
     * @return the list
     */
    @SuppressWarnings({"rawtypes"})
    private List convertObjectToList(ExternalBudgetErrorDto data) {
        return Arrays.asList(data.lineNo, data.columnNo, data.wpkCode, data.acceptedDate, data.actualValue,
                data.errorContent);
    }
}
