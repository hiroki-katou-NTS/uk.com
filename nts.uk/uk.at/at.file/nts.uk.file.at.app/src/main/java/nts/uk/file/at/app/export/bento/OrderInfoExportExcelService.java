package nts.uk.file.at.app.export.bento;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservationSearchConditionDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class OrderInfoExportExcelService extends ExportService<CreateOrderInfoDataSource> {

    @Inject
    OrderInfoExcelGenerator generator;

    @Override
    protected void handle(ExportServiceContext<CreateOrderInfoDataSource> exportServiceContext) {
        CreateOrderInfoDataSource dataSource = exportServiceContext.getQuery();
        OrderInfoDto dataGenerator = getGeneratorData(dataSource);
//        OrderInfoDto dataGenerator = new OrderInfoDto();
        generator.generate(exportServiceContext.getGeneratorContext(),dataGenerator);
    }

    public OrderInfoDto getGeneratorData(CreateOrderInfoDataSource dataSource){
        CreateOrderInfoFileQuery fileQuery = new CreateOrderInfoFileQuery();
        Optional<BentoReservationSearchConditionDto> totalExtractCondition = dataSource.getTotalExtractCondition() > -1
                ?  Optional.of(EnumAdaptor.valueOf(dataSource.getTotalExtractCondition(), BentoReservationSearchConditionDto.class)) : Optional.empty();
        Optional<BentoReservationSearchConditionDto> itemExtractCondition = dataSource.getItemExtractCondition() > -1
                ?  Optional.of(EnumAdaptor.valueOf(dataSource.getTotalExtractCondition(), BentoReservationSearchConditionDto.class)) : Optional.empty();
        Optional<Integer> frameNo = dataSource.getFrameNo() > 0 ? Optional.of(dataSource.getFrameNo()) : Optional.empty();
        Optional<String> totalTitle = dataSource.getTotalTitle() == null ? Optional.empty() : Optional.of(dataSource.getTotalTitle());
        Optional<String> detailTitle = dataSource.getDetailTitle() == null ? Optional.empty() : Optional.of(dataSource.getDetailTitle());
        ReservationClosingTimeFrame closingTimeFrame = EnumAdaptor.valueOf(dataSource.getReservationClosingTimeFrame(), ReservationClosingTimeFrame.class);
        OrderInfoDto result = fileQuery.createOrderInfoFileQuery(dataSource.getPeriod(),dataSource.getWorkplaceIds(), dataSource.getWorkplaceCodes(),
                totalExtractCondition, itemExtractCondition, frameNo, totalTitle,
                detailTitle, closingTimeFrame);
        return result;
    }
}
