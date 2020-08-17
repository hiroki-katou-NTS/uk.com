package nts.uk.file.at.app.export.bento;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OrderInfoExportPDFService extends ExportService<OrderInfoExportData> {

    @Inject
    CreateOrderInfoGenerator generator;
    @Override
    protected void handle(ExportServiceContext<OrderInfoExportData> context) {
        OrderInfoExportData dataSource = context.getQuery();
        generator.generate(context.getGeneratorContext(),dataSource);
    }
}
