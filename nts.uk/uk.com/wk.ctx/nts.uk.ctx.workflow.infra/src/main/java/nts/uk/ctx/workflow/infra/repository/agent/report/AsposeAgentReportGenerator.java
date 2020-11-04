package nts.uk.ctx.workflow.infra.repository.agent.report;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.workflow.dom.agent.report.AgentReportDataSource;
import nts.uk.ctx.workflow.dom.agent.report.AgentReportGenerator;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

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

        HEADER_STYLE_BLUE = new Style();
        HEADER_STYLE_BLUE.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        HEADER_STYLE_BLUE.setPattern(BackgroundType.SOLID);
        HEADER_STYLE_BLUE.setForegroundColor(Color.fromArgb(197, 241, 247));
        HEADER_STYLE_BLUE.setVerticalAlignment(TextAlignmentType.CENTER);
        HEADER_STYLE_BLUE.setHorizontalAlignment(TextAlignmentType.CENTER);

        CELL_STYLE = new Style();
        CELL_STYLE.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
        CELL_STYLE.setVerticalAlignment(TextAlignmentType.CENTER);
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

        try {
            sheet.autoFitColumns(START_COLUMN, COLUMN_SIZE - START_COLUMN + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        reportContext.saveAsExcel(this.createNewFile(generatorContext, getReportName(dataSource.getFileName())));
    }

    private void setupPage(Worksheet sheet) {
        sheet.setName(TextResource.localize("CMM044_33"));

        sheet.getPageSetup().setPaperSize(PaperSizeType.PAPER_A_4);
        sheet.getPageSetup().setOrientation(PageOrientationType.LANDSCAPE);
        sheet.getPageSetup().setLeftMargin(1);
        sheet.getPageSetup().setRightMargin(1);
        sheet.getPageSetup().setBottomMargin(1);
        sheet.getPageSetup().setFitToPagesWide(1);
        sheet.getPageSetup().setFitToPagesTall(0);

        companyAdapter.getCurrentCompany().ifPresent(info -> {
            sheet.getPageSetup().setHeader(0, "&\"ＭＳ ゴシック\"&10 " + info.getCompanyName());
        });
        sheet.getPageSetup().setHeader(1, "&\"ＭＳ ゴシック\"&16 " + TextResource.localize("CMM044_33"));
        DateTimeFormatter fullDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.JAPAN);
        String currentFormattedDate = LocalDateTime.now().format(fullDateTimeFormatter);
        sheet.getPageSetup().setHeader(2, "&\"ＭＳ ゴシック\"&10 " + currentFormattedDate+"\npage &P");
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
    }

    private void createTableBody (Worksheet sheet, List<LinkedHashMap<String, String>> data) {
        for(int row = 0; row < data.size(); row++){
            for (int col = 0; col < COLUMN_SIZE; col++) {
                String value;
                switch (col) {
                    case 0:
                        value = data.get(row).get("employeeCode");
                        break;
                    case 1:
                        value = data.get(row).get("employeeName");
                        break;
                    case 2:
                        value = data.get(row).get("workPlaceCode");
                        break;
                    case 3:
                        value = data.get(row).get("workPlaceName");
                        break;
                    case 4:
                        value = data.get(row).get("position");
                        break;
                    case 5:
                        value = data.get(row).get("startDate");
                        break;
                    case 6:
                        value = data.get(row).get("endDate");
                        break;
                    case 7:
                        value = data.get(row).get("agentCode");
                        break;
                    case 8:
                        value = data.get(row).get("agentName");
                        break;
                    case 9:
                        value = data.get(row).get("agentWorkPlaceCode");
                        break;
                    case 10:
                        value = data.get(row).get("agentWorkPlaceName");
                        break;
                    case 11:
                        value = data.get(row).get("agentPosition");
                        break;
                    default:
                        value = "";
                        break;
                }
                Cell dataCell = sheet.getCells().get(DATA_START_ROW + row, START_COLUMN + col);
                dataCell.setValue(value);
                dataCell.setStyle(CELL_STYLE);
            }
        }
    }
}
