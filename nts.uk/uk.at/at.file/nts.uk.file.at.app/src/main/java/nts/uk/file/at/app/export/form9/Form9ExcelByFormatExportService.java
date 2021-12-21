package nts.uk.file.at.app.export.form9;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Excel形式で出力
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU008_様式９.Ａ：様式９.Ａ：メニュー別OCD.Excel形式で印刷
 */
@Stateless
public class Form9ExcelByFormatExportService extends ExportService<Form9ExcelByFormatQuery> {

    @Inject
    private CreateForm9FileQuery createForm9Query;

    @Inject
    private Form9ExcelByFormatExportGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<Form9ExcelByFormatQuery> exportServiceContext) {
        long startTime = System.nanoTime();
        Form9ExcelByFormatQuery query = exportServiceContext.getQuery();

         // 1.1. 出力する(対象期間, 年月, int, 様式９のコード)
        Form9ExcelByFormatDataSource dataSource = this.createForm9Query.get(query);

        // 1.2. create report Form9
        this.exportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource, query);
        System.out.println("Thoi gian export: " + (System.nanoTime() - startTime) / 1000000000 + " seconds");
    }
}
