package nts.uk.file.at.infra.outputitemsofworkstatustable;

import com.aspose.cells.*;
import lombok.val;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.file.at.app.export.bento.ReservationBentoLedger;
import nts.uk.file.at.app.export.bento.ReservationEmpLedger;
import nts.uk.file.at.app.export.bento.ReservationMonthDataSource;
import nts.uk.file.at.app.export.bento.ReservationWkpLedger;
import nts.uk.file.at.app.export.outputitemsofworkstatustable.DisplayContentReportData;
import nts.uk.file.at.app.export.outputitemsofworkstatustable.DisplayWorkStatusReportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportContext;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeDisplayWorkStatusReportGenerator extends AsposeCellsReportGenerator implements DisplayWorkStatusReportGenerator {
    private static final String TEMPLATE_FILE_ADD = "report/KWR003.xlsx";
    private static final String REPORT_FILE_NAME = "KWR003.xlsx";

    private static final Integer EMPLOYEE_COLUMN = 1;

    private static final Integer NAME_COLUMN = 2;

    private static final Integer START_DATA_COLUMN = 3;

    private static final Integer QUANTITY_COLUMN = 34;

    private static final Integer AMOUNT1_COLUMN = 35;

    private static final Integer AMOUNT2_COLUMN = 36;

    private static final String DATE_FORMAT = "yyyy/MM/dd";

    private static final String DATE_FORMAT_JP = "yyyy年MM月dd日(E)";

    private static final String DAY_OF_WEEK_FORMAT_JP = "E";

    @Override
    public void generate(FileGeneratorContext generatorContext, DisplayContentReportData dataSource) {
        try {
            AsposeCellsReportContext designer = this.createContext(TEMPLATE_FILE_ADD);
            Workbook workbook = designer.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            Worksheet worksheet = worksheets.get(0);
            printPage(worksheet, dataSource);

            printContent(worksheet, dataSource);
            designer.getDesigner().setWorkbook(workbook);
            designer.processDesigner();
            designer.saveAsExcel(this.createNewFile(generatorContext, this.getReportName(REPORT_FILE_NAME)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printPage(Worksheet worksheet, DisplayContentReportData dataSource) {
        PageSetup pageSetup = worksheet.getPageSetup();
        pageSetup.setFirstPageNumber(1);
        pageSetup.setHeader(0, "&9&\"ＭＳ ゴシック\"" + "対象年月");
        pageSetup.setHeader(1, "&16&\"ＭＳ ゴシック\"" + GeneralDate.today().toString());
    }

    private void printContent(Worksheet worksheet, DisplayContentReportData dataSource) throws Exception {
        Cells cells = worksheet.getCells();
        int maxRow = cells.getMaxRow();
        int maxColumn = cells.getMaxColumn();
        // clear content
        cells.clearContents(CellArea.createCellArea(1, 1, maxRow, maxColumn));
        // I18NText.getText("---ma text---")
//        cells.get(0, 1).setValue(" Value 01" + getDayOfWeekJapan(startDate, DATE_FORMAT_JP) + "〜"
//                + getDayOfWeekJapan(endDate, DATE_FORMAT_JP));
        cells.get(1, 1).setValue("日付");
        cells.get(1, 34).setValue("合計");
        printDayOfWeekHeader(worksheet, dataSource);

//        cells.get(1, 36).setValue(I18NText.getText("KMR005_12"));
//        Integer dataNumberRow = dataSource.getWkpLedgerLst().stream().map(x ->
//                x.getEmpLedgerLst().stream().map(y ->
//                        y.getBentoLedgerLst().size() + 1
//                ).collect(Collectors.summingInt(Integer::intValue))
//        ).map(x -> x + 1).collect(Collectors.summingInt(Integer::intValue));
//        if(dataNumberRow==0) {
//            return;
//        }
//        cells.insertRows(7, dataNumberRow);
//        cells.clearFormats(7, 1, 7 + dataNumberRow, maxColumn);
//        printFormat(cells, dataSource);
//        printContent(cells, dataSource);
//        cells.deleteBlankRows();
    }

    private void printDayOfWeekHeader(Worksheet worksheet, DisplayContentReportData dataSource) {
        Cells cells = worksheet.getCells();
        GeneralDate startDate = dataSource.getPeriod().start();
        GeneralDate endDate = dataSource.getPeriod().end();
        for (int i = 0; i <= getDateRange(startDate, endDate); i++) {
            GeneralDate loopDate = startDate.addDays(i);
            cells.get(1, i + 3).setValue(loopDate.day());
            cells.get(2, i + 3).setValue(getDayOfWeekJapan(loopDate, DAY_OF_WEEK_FORMAT_JP));
            for (int j = 0; j < dataSource.getData().size(); j++) {
                cells.get(3, 4).setValue(dataSource.getData().get(j).getWorkPlaceCode());
                cells.get(4, 4).setValue(dataSource.getData().get(j).getWorkPlaceName());
                val itemInline = dataSource.getData()

            }
        }
        cells.get(3, 1).setValue("場職 :" + dataSource.getData().get(0).getWorkPlaceName());

    }

    private void printFormat(Cells cells, ReservationMonthDataSource dataSource) throws Exception {
        int startDataRow = 6;
        for (ReservationWkpLedger wkpLedger : dataSource.getWkpLedgerLst()) {
            startDataRow = printWkpFormat(cells, wkpLedger, dataSource.getPeriod(), startDataRow);
        }
    }

    private Integer printWkpFormat(Cells cells, ReservationWkpLedger wkpLedger, DatePeriod period, Integer dataRow) throws Exception {
        int startDataRow = dataRow + 1;
        cells.copyRow(cells, 3, startDataRow);
        for (ReservationEmpLedger empLedger : wkpLedger.getEmpLedgerLst()) {
            startDataRow = printEmpFormat(cells, empLedger, period, startDataRow);
        }
        return startDataRow;
    }

    private Integer printEmpFormat(Cells cells, ReservationEmpLedger empLedger, DatePeriod period, Integer dataRow) throws Exception {
        int startDataRow = dataRow + 1;
        List<ReservationBentoLedger> bentoLedgerLst = empLedger.getBentoLedgerLst();
        for (int i = 0; i < bentoLedgerLst.size(); i++) {
            if (i % 2 == 0) {
                cells.copyRow(cells, 4, startDataRow);
            } else {
                cells.copyRow(cells, 5, startDataRow);
            }
            startDataRow += 1;
        }
        cells.copyRow(cells, 6, startDataRow);
        int empRowNo = empLedger.getBentoLedgerLst().size() + 1;
        cells.merge(dataRow + 1, 1, empRowNo, 1);
        return startDataRow;
    }

    private void printContent(Cells cells, ReservationMonthDataSource dataSource) throws Exception {
        int startDataRow = 6;
        for (ReservationWkpLedger wkpLedger : dataSource.getWkpLedgerLst()) {
            startDataRow = printWorkPlace(cells, wkpLedger, dataSource.getPeriod(), startDataRow);
        }
    }

    private Integer printWorkPlace(Cells cells, ReservationWkpLedger wkpLedger, DatePeriod period, Integer dataRow) throws Exception {
        int startDataRow = dataRow + 1;
        cells.get(startDataRow, EMPLOYEE_COLUMN).setValue(wkpLedger.getWkpCD() + " " + wkpLedger.getWkpName());
        for (ReservationEmpLedger empLedger : wkpLedger.getEmpLedgerLst()) {
            startDataRow = printEmployee(cells, empLedger, period, startDataRow);
        }
        return startDataRow;
    }

    private Integer printEmployee(Cells cells, ReservationEmpLedger empLedger, DatePeriod period, Integer dataRow) throws Exception {
        int startDataRow = dataRow + 1;
        cells.get(startDataRow, EMPLOYEE_COLUMN).setValue(empLedger.getEmpCD() + " " + empLedger.getEmpName());
        for (ReservationBentoLedger bentoLedger : empLedger.getBentoLedgerLst()) {
            printBento(cells, bentoLedger, period, startDataRow);
            startDataRow += 1;
        }
        cells.get(startDataRow, QUANTITY_COLUMN).setValue(empLedger.getTotalBentoQuantity());
        Style style1 = cells.get(startDataRow, QUANTITY_COLUMN).getStyle();
        style1.setBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
        cells.get(startDataRow, QUANTITY_COLUMN).setStyle(style1);
        cells.get(startDataRow, AMOUNT1_COLUMN).setValue(empLedger.getTotalBentoAmount1());
        Style style2 = cells.get(startDataRow, AMOUNT1_COLUMN).getStyle();
        style2.setBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
        cells.get(startDataRow, AMOUNT1_COLUMN).setStyle(style2);
        cells.get(startDataRow, AMOUNT2_COLUMN).setValue(empLedger.getTotalBentoAmount2());
        Style style3 = cells.get(startDataRow, AMOUNT2_COLUMN).getStyle();
        style3.setBorder(BorderType.TOP_BORDER, CellBorderType.DOUBLE, Color.getBlack());
        cells.get(startDataRow, AMOUNT2_COLUMN).setStyle(style3);
        return startDataRow;
    }

    private void printBento(Cells cells, ReservationBentoLedger bentoLedger, DatePeriod period, Integer dataRow) {
        cells.get(dataRow, NAME_COLUMN).setValue(bentoLedger.getBentoName());
        bentoLedger.getQuantityDateMap().entrySet().stream().forEach(x -> {
            GeneralDate loopDate = x.getKey();
            Integer quantity = x.getValue();
            int periodRange = getDateRange(period.start(), loopDate);
            cells.get(dataRow, START_DATA_COLUMN + periodRange).setValue(quantity);
        });
        cells.get(dataRow, QUANTITY_COLUMN).setValue(bentoLedger.getTotalQuantity());
        cells.get(dataRow, AMOUNT1_COLUMN).setValue(bentoLedger.getTotalAmount1());
        cells.get(dataRow, AMOUNT2_COLUMN).setValue(bentoLedger.getTotalAmount2());
    }

    private int getDateRange(GeneralDate startDate, GeneralDate endDate) {
        if (endDate.year() - startDate.year() > 0) {
            int startYear = startDate.year();
            int endYear = endDate.year();
            GeneralDate beforeYearEndDate = GeneralDate.fromString(startYear + "/12/31", DATE_FORMAT);
            GeneralDate afterYearStartDate = GeneralDate.fromString(endYear + "/01/01", DATE_FORMAT);
            return endDate.dayOfYear() - afterYearStartDate.dayOfYear() + beforeYearEndDate.dayOfYear() - startDate.dayOfYear() + 1;
        } else {
            return endDate.dayOfYear() - startDate.dayOfYear();
        }
    }

    private String getDayOfWeekJapan(GeneralDate date, String formatDate) {
        SimpleDateFormat jp = new SimpleDateFormat(formatDate, Locale.JAPAN);
        return jp.format(date.date());
    }
}
