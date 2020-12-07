package nts.uk.ctx.workflow.infra.repository.agent.report;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.workflow.dom.agent.report.AgentReportDataSource;
import nts.uk.ctx.workflow.dom.agent.report.AgentReportGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Stateless
public class AsposeAgentReportGenerator extends AsposeCellsReportGenerator implements AgentReportGenerator {
    private static final String REPORT_ID = "AGENT_REPORT";

    private static final List<String> HEADER_TEXTS = Arrays.asList(
            TextResource.localize("CMM044_7"),
            "",
            TextResource.localize("CMM044_31"),
            TextResource.localize("CMM044_32"),
            TextResource.localize("Com_Jobtitle"),
            TextResource.localize("CMM044_34"),
            TextResource.localize("CMM044_35"),
            TextResource.localize("CMM044_25"),
            "",
            TextResource.localize("CMM044_31"),
            TextResource.localize("CMM044_32"),
            TextResource.localize("Com_Jobtitle")
    );

    private static final List<Double> COLUMNS_WIDTH = Arrays.asList(
            10.0, 14.0, 17.0, 17.0, 9.0,
            10.1, 10.1,
            10.0, 14.0, 17.0, 17.0, 9.0
    );

    private static final int HEADER_ROW = 0;
    private static final int START_COLUMN = 0;
    private static final int DATA_START_ROW = 1;
    private static final int COLUMN_SIZE = 12;

    private static Style HEADER_STYLE_GREEN;
    private static Style HEADER_STYLE_BLUE;
    private static Style CELL_STYLE;

    static {
        HEADER_STYLE_GREEN = new Style();
        HEADER_STYLE_GREEN.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_GREEN.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_GREEN.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_GREEN.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_GREEN.setPattern(BackgroundType.SOLID);
        HEADER_STYLE_GREEN.setForegroundColor(Color.fromArgb(198, 224, 180));
        HEADER_STYLE_GREEN.setVerticalAlignment(TextAlignmentType.CENTER);
        HEADER_STYLE_GREEN.setHorizontalAlignment(TextAlignmentType.CENTER);
        HEADER_STYLE_GREEN.getFont().setName("ＭＳ ゴシック");
        HEADER_STYLE_GREEN.getFont().setSize(10);

        HEADER_STYLE_BLUE = new Style();
        HEADER_STYLE_BLUE.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setPattern(BackgroundType.SOLID);
        HEADER_STYLE_BLUE.setForegroundColor(Color.fromArgb(197, 241, 247));
        HEADER_STYLE_BLUE.setVerticalAlignment(TextAlignmentType.CENTER);
        HEADER_STYLE_BLUE.setHorizontalAlignment(TextAlignmentType.CENTER);
        HEADER_STYLE_BLUE.getFont().setName("ＭＳ ゴシック");
        HEADER_STYLE_BLUE.getFont().setSize(10);

        CELL_STYLE = new Style();
        CELL_STYLE.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setVerticalAlignment(TextAlignmentType.CENTER);
        CELL_STYLE.getFont().setName("ＭＳ ゴシック");
        CELL_STYLE.getFont().setSize(9);
        CELL_STYLE.setTextWrapped(true);
    }

    @Inject
    private CompanyAdapter companyAdapter;

    @Override
    public void generate(FileGeneratorContext generatorContext, AgentReportDataSource dataSource) {
        val reportContext = this.createEmptyContext(REPORT_ID);
        val workbook = reportContext.getWorkbook();
        val sheet = workbook.getWorksheets().get(0);

        setupPage(sheet);

        createTableHeader(sheet);

        createTableBody(sheet, dataSource.getData());

        reportContext.saveAsExcel(this.createNewFile(generatorContext, getReportName(dataSource.getFileName())));
    }

    private void setupPage(Worksheet sheet) {
        sheet.setName(TextResource.localize("CMM044_33"));

        sheet.getPageSetup().setPaperSize(PaperSizeType.PAPER_A_4);
        sheet.getPageSetup().setOrientation(PageOrientationType.LANDSCAPE);
        sheet.getPageSetup().setTopMargin(2.5);
        sheet.getPageSetup().setHeaderMargin(1);
        sheet.getPageSetup().setLeftMargin(1);
        sheet.getPageSetup().setRightMargin(1);
        sheet.getPageSetup().setBottomMargin(1);
        sheet.getPageSetup().setFitToPagesWide(1);
        sheet.getPageSetup().setFitToPagesTall(0);
        sheet.getPageSetup().setCenterHorizontally(true);

        companyAdapter.getCurrentCompany().ifPresent(info -> {
            sheet.getPageSetup().setHeader(0, "&\"ＭＳ ゴシック\"&10 " + info.getCompanyName());
        });
        sheet.getPageSetup().setHeader(1, "&\"ＭＳ ゴシック,Bold\"&16 " + TextResource.localize("CMM044_33"));
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        sheet.getPageSetup().setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage &P");

        sheet.getPageSetup().setPrintTitleRows("$1:$1");

        for (int j = 0; j < COLUMNS_WIDTH.size(); j++) {
            sheet.getCells().setColumnWidth(START_COLUMN + j, COLUMNS_WIDTH.get(j));
        }
    }

    private void createTableHeader (Worksheet sheet) {
        for (int j = 0; j < HEADER_TEXTS.size(); j++) {
            Cell header = sheet.getCells().get(HEADER_ROW, START_COLUMN + j);
            header.setValue(HEADER_TEXTS.get(j));
            if (j < 7)
                header.setStyle(HEADER_STYLE_BLUE);
            else
                header.setStyle(HEADER_STYLE_GREEN);
        }
        sheet.getCells().merge(HEADER_ROW, START_COLUMN, 1, 2);
        sheet.getCells().merge(HEADER_ROW, START_COLUMN + 7, 1, 2);
        sheet.getCells().setRowHeight(HEADER_ROW, 21);
    }

    private void createTableBody (Worksheet sheet, List<Map<String, String>> data) {
        LinkedHashMap<String, List<Map<String, String>>> convertedData = new LinkedHashMap<>();
        String currEmp = null;
        for(Map<String, String> row : data) {
            if (!StringUtils.isEmpty(row.get("employeeCode"))) {
                currEmp = row.get("employeeCode");
                List<Map<String, String>> block = new ArrayList<>();
                block.add(row);
                convertedData.put(currEmp, block);
            } else {
                convertedData.get(currEmp).add(row);
            }
        }
        int rowCount = 0;
        double maxPageHeight = 24 * 22.5;
        double height = 0.0;
        for (List<Map<String, String>> block : convertedData.values()) {
            for (int row = 0; row < block.size(); row++){
                for (int col = 0; col < COLUMN_SIZE; col++) {
                    String value;
                    switch (col) {
                        case 0:
                            value = height == 0.0 ? block.get(0).get("employeeCode") : block.get(row).get("employeeCode");
                            break;
                        case 1:
                            value = height == 0.0 ? block.get(0).get("employeeName") : block.get(row).get("employeeName");
                            break;
                        case 2:
                            value = height == 0.0 ? block.get(0).get("workPlaceCode") : block.get(row).get("workPlaceCode");
                            break;
                        case 3:
                            value = height == 0.0 ? block.get(0).get("workPlaceName") : block.get(row).get("workPlaceName");
                            break;
                        case 4:
                            value = height == 0.0 ? block.get(0).get("position") : block.get(row).get("position");
                            break;
                        case 5:
                            value = block.get(row).get("startDate");
                            break;
                        case 6:
                            value = block.get(row).get("endDate");
                            break;
                        case 7:
                            value = block.get(row).get("agentCode");
                            break;
                        case 8:
                            value = block.get(row).get("agentName");
                            break;
                        case 9:
                            value = block.get(row).get("agentWorkPlaceCode");
                            break;
                        case 10:
                            value = block.get(row).get("agentWorkPlaceName");
                            break;
                        case 11:
                            value = block.get(row).get("agentPosition");
                            break;
                        default:
                            value = "";
                            break;
                    }
                    Cell dataCell = sheet.getCells().get(DATA_START_ROW + rowCount, START_COLUMN + col);
                    dataCell.setValue(value);
                    dataCell.setStyle(CELL_STYLE);
                }
                sheet.getCells().setRowHeight(DATA_START_ROW + rowCount, 22.5);
                if (height + sheet.getCells().getRowHeight(DATA_START_ROW + rowCount) > maxPageHeight) {
                    sheet.getHorizontalPageBreaks().add(DATA_START_ROW + rowCount + 1);
                    height = 0.0;
                } else {
                    height += sheet.getCells().getRowHeight(DATA_START_ROW + rowCount);
                }
                rowCount++;
            }
        }

    }
}
