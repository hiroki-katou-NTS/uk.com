package nts.uk.file.at.app.export.bento;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoMakeOrderCommand;
import nts.uk.ctx.at.record.app.command.reservation.bento.BentoMakeOrderCommandHandler;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class OrderInfoExportPDFService extends ExportService<CreateOrderInfoDataSource> {

    @Inject
    CreateOrderInfoGenerator generator;

    @Inject
    CreateOrderInfoFileQuery createOrderInfoFileQuery;

    @Inject
    BentoMakeOrderCommandHandler commandHandler;

    private boolean isWorkLocationExport = true;
    @Override
    protected void handle(ExportServiceContext<CreateOrderInfoDataSource> context) {
        CreateOrderInfoDataSource dataSource = context.getQuery();
        OrderInfoDto dataGenerator = dataSource.getGeneratorData(createOrderInfoFileQuery, commandHandler);
        if(CollectionUtil.isEmpty(dataSource.getWorkLocationCodes()))
            isWorkLocationExport = false;
        ReservationClosingTimeFrame closingTimeFrame = EnumAdaptor.valueOf(dataSource.getReservationClosingTimeFrame(),
                ReservationClosingTimeFrame.class);
        generator.generate(context.getGeneratorContext(),new OrderInfoExportData(dataGenerator,
                dataSource.isBreakPage(), isWorkLocationExport, closingTimeFrame.name, OutputExtension.PDF));
    }
}
