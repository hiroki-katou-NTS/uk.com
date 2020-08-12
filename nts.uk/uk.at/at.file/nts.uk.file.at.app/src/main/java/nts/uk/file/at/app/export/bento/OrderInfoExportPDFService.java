package nts.uk.file.at.app.export.bento;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class OrderInfoExportPDFService extends ExportService<OrderInfoDto> {

    @Inject
    CreateOrderInfoPDFGenerator generator;
    @Override
    protected void handle(ExportServiceContext<OrderInfoDto> context) {
        OrderInfoDto dataSource = context.getQuery();
        generator.generate(context.getGeneratorContext(),dataSource);
    }
}
