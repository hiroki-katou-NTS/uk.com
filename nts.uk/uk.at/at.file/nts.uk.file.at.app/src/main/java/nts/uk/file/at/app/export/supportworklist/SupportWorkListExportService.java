package nts.uk.file.at.app.export.supportworklist;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class SupportWorkListExportService extends ExportService<SupportWorkListQuery> {
    @Inject
    private CreateSupportWorkListFileQuery fileQuery;

    @Inject
    private SupportWorkListGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<SupportWorkListQuery> exportServiceContext) {
        long startTime = System.nanoTime();

        FileGeneratorContext generatorContext = exportServiceContext.getGeneratorContext();
        SupportWorkListQuery query = exportServiceContext.getQuery();
        SupportWorkListDataSource dataSource = fileQuery.create(query);

        this.exportGenerator.generate(generatorContext, dataSource, query.isExportCsv());

        System.out.println("Export file time: " + (System.nanoTime() - startTime) / 1000000000 + " seconds");
    }
}
