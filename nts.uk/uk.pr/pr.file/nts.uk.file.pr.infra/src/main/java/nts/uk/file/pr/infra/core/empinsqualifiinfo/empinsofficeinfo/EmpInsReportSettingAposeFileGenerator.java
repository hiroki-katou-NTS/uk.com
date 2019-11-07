package nts.uk.file.pr.infra.core.empinsqualifiinfo.empinsofficeinfo;

import com.aspose.pdf.*;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExFileGenerator;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExportData;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportContext;
import nts.uk.shr.infra.file.report.aspose.pdf.AsposePdfReportGenerator;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsReportSettingAposeFileGenerator extends AsposePdfReportGenerator implements EmpInsReportSettingExFileGenerator{
    private static final String TEMPLATE_FILE = "report/氏名変更届.pdf";


    @Override
    public void generate(FileGeneratorContext fileContext, List<EmpInsReportSettingExportData> data) {
        try (AsposePdfReportContext report = this.createContext(TEMPLATE_FILE)) {

            Document doc = report.getDocument();

            stylePage(doc);

            Page curPage = doc.getPages().add();
            Paragraphs paragraphs = curPage.getParagraphs();

            /********************************************/

            paragraphs.add(createTableWith(Arrays.asList(200, 300),
                    Arrays.asList("Title", "Character"),
                    Arrays.asList("Railgun", "Misaka")));

            /********************************************/

            Table table = createTableWith(Arrays.asList(100, 200),
                    Arrays.asList("Header", "Value"),
                    Arrays.asList("H*****", "xxx"));
            table.setInNewPage(true);
            paragraphs.add(table);

            report.saveAsPdf(this.createNewFile(fileContext, "雇用保険被保険者氏名変更届.pdf"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Table createTableWith(List<Integer> widths, List<String> headers, List<String> defaultVal) {
        Table table = new Table();

        table.setColumnWidths(widths.stream().map(c -> String.valueOf(c)).collect(Collectors.joining(" ")));

        Row header = table.getRows().add();
        header.setFixedRowHeight(20);
        header.setDefaultCellBorder(new BorderInfo(BorderSide.Left | BorderSide.Top| BorderSide.Bottom | BorderSide.Right,
                1.25F, Color.getBlack()));
        header.setVerticalAlignment(VerticalAlignment.Center);

        headers.stream().forEach(h -> {
            Cell c = header.getCells().add();
            c.getParagraphs().add(new TextFragment(h));
            c.setAlignment(HorizontalAlignment.Center);
        });

        for (int i = 1; i <= 10; i++) {
            Row row = table.getRows().add();
            row.setFixedRowHeight(15);
            row.setDefaultCellBorder(new BorderInfo(BorderSide.Left | BorderSide.Top| BorderSide.Bottom | BorderSide.Right,
                    1.25F, Color.getBlack()));
            row.setVerticalAlignment(VerticalAlignment.Center);

            for (int j = 0; j < headers.size(); j++) {
                String text = defaultVal.size() > j ? defaultVal.get(j) : headers.get(j);
                Cell cell = row.getCells().add();
                cell.getParagraphs().add(new TextFragment(text + " - " + i));
                cell.setAlignment(HorizontalAlignment.Center);
            }
        }
        return table;
    }

    private void stylePage(Document doc) {
        PageInfo pageInfo = doc.getPageInfo();
        MarginInfo marginInfo = pageInfo.getMargin();
        marginInfo.setLeft(37);
        marginInfo.setRight(37);
        marginInfo.setTop(37);
        marginInfo.setBottom(37);
        pageInfo.setLandscape(true);
    }
}
