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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

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
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryInsuranceGenerator;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaymentSalaryReportService;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.CategoryItem;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DataItem;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.RowItemDto;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

/**
 * @author duongnd
 *
 */
@Stateless
public class AsposePaymentSalaryReportGenerator extends AsposeCellsReportGenerator
        implements PaymentSalaryInsuranceGenerator {

//public class AsposePaymentSalaryReportGenerator {

    private static final String TEMPLATE_FILE = "report/QPP007.xlsx";
    private static final String REPORT_FILE_NAME = "QPP007_";
    private static final String EXTENSION_PDF = ".pdf";
    private static final String EXTENSION_EXCEL = ".xlsx";
    private static final String SHEET_NAME = "My sheet";
    
//    public static void main(String[] args) {
//        new AsposePaymentSalaryReportGenerator().testGeneratorReport();
//    }
    
    
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
            createNewSheet(worksheets, reportData);
            reportContext.getDesigner().setWorkbook(workbook);
            workbook.calculateFormula(true);
            reportContext.processDesigner();
            DateFormat dateFormat = new SimpleDateFormat(ReportConstant.DATE_TIME_FORMAT);
            Date date = new Date();
            String fileName = REPORT_FILE_NAME.concat(dateFormat.format(date).toString()).concat(EXTENSION_PDF);
            reportContext.saveAsPdf(this.createNewFile(fileContext, fileName));
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
            DateFormat dateFormat = new SimpleDateFormat(ReportConstant.DATE_TIME_FORMAT);
            Date date = new Date();
            String fileName = REPORT_FILE_NAME.concat(dateFormat.format(date).toString());
            workbook.save(fileName.concat(EXTENSION_EXCEL));
            
            PdfSaveOptions saveOptions = new PdfSaveOptions(SaveFormat.PDF);
            saveOptions.setAllColumnsInOnePagePerSheet(true);
            workbook.save(fileName.concat(EXTENSION_PDF), saveOptions);
//            workbook.save(fileName.concat(EXTENSION_PDF));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void createNewSheet(WorksheetCollection worksheets, PaymentSalaryReportData reportData) {
        Worksheet worksheet = worksheets.get(ReportConstant.NUMBER_ZERO);
        worksheet.setName(SHEET_NAME);
        setupPage(worksheet);
        writeContent(worksheet, reportData);
    }
    
    private void setupPage(Worksheet worksheet) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setOrientation(PageOrientationType.LANDSCAPE);
    }
    
    private void writeContent(Worksheet worksheet, PaymentSalaryReportData reportData) {
        Cells cells = worksheet.getCells();
        
        List<CategoryItem> categoryItems = reportData.getCategoryItems();
        List<String> reportTitleItem = reportData.getReportTitleItems();
        
        int numberColumn = reportTitleItem.size();
        int indexRow = ReportConstant.INDEX_ROW_CONTENT;
        writeTitle(cells, reportTitleItem, indexRow);
        indexRow++;
        for (CategoryItem category : categoryItems) {
            indexRow = writeCategory(cells, category, numberColumn, indexRow);
            indexRow++;
        }
        
        // set print area
        PageSetup pageSetup = worksheet.getPageSetup();
        String cellEnd = cells.get(indexRow - ReportConstant.NUMBER_ONE, numberColumn - ReportConstant.NUMBER_ONE).getName();
        String printArea = ReportConstant.START_AREA + cellEnd;
        pageSetup.setPrintArea(printArea);
    }
    
    private void writeTitle(Cells cells, List<String> reportTitleItem, int indexRow) {
        cells.setRowHeight(indexRow, ReportConstant.ROW_HEIGHT_TITLE);
        StyleModel styleModel = new StyleModel();
        styleModel.setForegroundColor(ReportConstant.LIGHT_BLUE_COLOR);
        for (int i = 0; i < reportTitleItem.size(); i++) {
            cells.setColumnWidth(i, ReportConstant.COLUMN_WITH);
            Cell cell = cells.get(indexRow, i);
            styleModel.drawTitleReport(cell);
            cell.setValue(reportTitleItem.get(i));
        }
    }
    
    private int writeCategory(Cells cells, CategoryItem categoryItem, int numberColumn, int indexRow) {
        writeHeaderCategory(cells, categoryItem, numberColumn, indexRow);
        indexRow++;
        writeCategoryItem(cells, categoryItem.getRowItems(), indexRow);
        indexRow += categoryItem.getRowItems().size() - ReportConstant.NUMBER_ONE;
        return indexRow;
    }
    
    private void writeHeaderCategory(Cells cells, CategoryItem categoryItem, int numberColumn, int indexRow) {
        Cell cell = cells.get(indexRow, ReportConstant.INDEX_FIRST_COLUMN);
        cell.setValue(categoryItem.getHeaderCategory());
        
        StyleModel styleModel = new StyleModel();
        styleModel.drawHeaderCategory(cells, numberColumn, indexRow);
    }
    
    private void writeCategoryItem(Cells cells, List<RowItemDto> rowItems, int indexRow) {
        int indexArrItem = 0;
        for (RowItemDto rowItem : rowItems) {
            StyleModel styleModel = new StyleModel();
            if (indexArrItem % 2 != ReportConstant.NUMBER_ZERO) {
                styleModel.setForegroundColor(ReportConstant.LIGHT_GREEN_COLOR);
            }
            writeRowItem(cells, rowItem, indexRow, styleModel);
            indexRow++;
            indexArrItem++;
        }
    }
    
    private void writeRowItem(Cells cells, RowItemDto rowItem, int indexRow, StyleModel styleModel) {
        int indexColumn = ReportConstant.INDEX_FIRST_COLUMN;
        
        Cell cellItemName = cells.get(indexRow, indexColumn);
        cellItemName.setValue(rowItem.getItemName());
        styleModel.drawBorderCell(cellItemName);
        indexColumn++;
        
        styleModel.setFormatNumber(ReportConstant.FORMAT_NUMBER);
        indexColumn = this.writeEmployee(cells, rowItem, styleModel, indexRow, indexColumn);
        indexColumn++;
        indexColumn = this.writeDepartment(cells, rowItem, styleModel, indexRow, indexColumn);
        indexColumn++;
        indexColumn = this.writeHierarchyDeparment(cells, rowItem, styleModel, indexRow, indexColumn);
        indexColumn++;
    }
    
    private int writeEmployee(Cells cells, RowItemDto rowItem, StyleModel styleModel, int indexRow, int indexColumn) {
        return this.writeDataItem(cells, rowItem.getAmountEmployees(), styleModel, indexRow, indexColumn,
                rowItem.calculateEmployeeTotal());
    }
    
    private int writeDepartment(Cells cells, RowItemDto rowItem, StyleModel styleModel, int indexRow,
            int indexColumn) {
        return this.writeDataItem(cells, rowItem.getAmountDepartments(), styleModel, indexRow, indexColumn,
                rowItem.calculateDepartmentTotal());
    }
    
    private int writeHierarchyDeparment(Cells cells, RowItemDto rowItem, StyleModel styleModel, int indexRow,
            int indexColumn) {
        return this.writeDataItem(cells, rowItem.getAmountHierarchyDepartments(), styleModel, indexRow, indexColumn,
                rowItem.calculateHierarchyDepartmentTotal());
    }
    
    private int writeDataItem(Cells cells, List<DataItem> dataItems, StyleModel styleModel, int indexRow,
            int indexColumn, double total) {
        List<Double> amountDeps = dataItems.stream()
                .map(p -> p.getAmount())
                .collect(Collectors.toList());
        int indexArr = ReportConstant.NUMBER_ZERO;
        while (indexArr < amountDeps.size()) {
            Cell cell = cells.get(indexRow, indexColumn);
            cell.setValue(amountDeps.get(indexArr));
            styleModel.drawBorderCell(cell);
            if (indexArr == ReportConstant.NUMBER_ZERO && indexColumn > ReportConstant.NUMBER_ONE) {
                StyleModel newStyleModel = new StyleModel(styleModel.getForegroundColor(),
                        CellsBorderType.DoubleLeftBorder);
                newStyleModel.drawBorderCell(cell);
                
                // draw double border for row title
                StyleModel styleTitleModel = new StyleModel(ReportConstant.LIGHT_BLUE_COLOR,
                        CellsBorderType.DoubleLeftBorder);
                Cell cellTitle = cells.get(ReportConstant.INDEX_ROW_CONTENT, indexColumn);
                styleTitleModel.drawBorderCell(cellTitle);
            }
            indexArr++;
            indexColumn++;
        }
        Cell cellTotal = cells.get(indexRow, indexColumn);
        cellTotal.setValue(total);
        styleModel.drawBorderCell(cellTotal);
        
        StyleModel newStyleModel = new StyleModel(styleModel.getForegroundColor(), CellsBorderType.DoubleLeftBorder);
        newStyleModel.drawBorderCell(cellTotal);
        
        // draw double border for row title
        StyleModel styleTitleModel = new StyleModel(ReportConstant.LIGHT_BLUE_COLOR,
                CellsBorderType.DoubleLeftBorder);
        Cell cellTitle = cells.get(ReportConstant.INDEX_ROW_CONTENT, indexColumn);
        styleTitleModel.drawBorderCell(cellTitle);
        
        return indexColumn;
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
}

enum CellsBorderType {
    CommonBorder,
    DoubleLeftBorder,
    DoubleRightBorder,
    CategoryBorder,
    LeftBorder,
    RightBorder
}