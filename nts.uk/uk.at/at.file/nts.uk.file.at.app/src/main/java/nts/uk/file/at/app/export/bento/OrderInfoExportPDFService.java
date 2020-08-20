package nts.uk.file.at.app.export.bento;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OrderInfoExportPDFService extends ExportService<CreateOrderInfoDataSource> {

    @Inject
    CreateOrderInfoGenerator generator;

    @Inject
    CreateOrderInfoFileQuery createOrderInfoFileQuery;

    private boolean isWorkLocationExport = true;
    @Override
    protected void handle(ExportServiceContext<CreateOrderInfoDataSource> context) {
        CreateOrderInfoDataSource dataSource = context.getQuery();
        OrderInfoDto dataGenerator = dataSource.getGeneratorData(createOrderInfoFileQuery);
        if(CollectionUtil.isEmpty(dataSource.getWorkLocationCodes()))
            isWorkLocationExport = false;
        generator.generate(context.getGeneratorContext(),new OrderInfoExportData(dataGenerator,
                dataSource.isBreakPage(), isWorkLocationExport, dataSource.getReservationTimeZone(), OutputExtension.EXCEL));
    }
}
