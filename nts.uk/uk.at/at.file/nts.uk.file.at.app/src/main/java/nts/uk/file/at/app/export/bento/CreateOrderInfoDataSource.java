package nts.uk.file.at.app.export.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservationSearchConditionDto;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

import java.util.List;
import java.util.Optional;

/**
 * 予約確認一覧起動情報
 * @author tuan.ha1
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateOrderInfoDataSource {
    private List<String> workplaceIds;
    private List<String> workLocationCodes;
    private DatePeriodDto period;
    private int totalExtractCondition;
    private int itemExtractCondition;
    private int frameNo;
    private String totalTitle;
    private String detailTitle;
    private int reservationClosingTimeFrame;
    private boolean isBreakPage;
    private String reservationTimeZone;

    public OrderInfoDto getGeneratorData(CreateOrderInfoFileQuery createOrderInfoFileQuery){
        Optional<BentoReservationSearchConditionDto> totalExtractCondition = this.getTotalExtractCondition() > -1
                ?  Optional.of(EnumAdaptor.valueOf(this.getTotalExtractCondition(), BentoReservationSearchConditionDto.class)) : Optional.empty();
        Optional<BentoReservationSearchConditionDto> itemExtractCondition = this.getItemExtractCondition() > -1
                ?  Optional.of(EnumAdaptor.valueOf(this.getTotalExtractCondition(), BentoReservationSearchConditionDto.class)) : Optional.empty();
        Optional<Integer> frameNo = this.getFrameNo() > -1 ? Optional.of(this.getFrameNo()) : Optional.empty();
        Optional<String> totalTitle = this.getTotalTitle() == null ? Optional.empty() : Optional.of(this.getTotalTitle());
        Optional<String> detailTitle = this.getDetailTitle() == null ? Optional.empty() : Optional.of(this.getDetailTitle());
        ReservationClosingTimeFrame closingTimeFrame = EnumAdaptor.valueOf(this.getReservationClosingTimeFrame(), ReservationClosingTimeFrame.class);

        OrderInfoDto result = createOrderInfoFileQuery.createOrderInfoFileQuery(this.getPeriod().convertToDate("yyyy/MM/dd"),this.getWorkplaceIds(), this.getWorkLocationCodes(),
                totalExtractCondition, itemExtractCondition, frameNo, totalTitle,
                detailTitle, closingTimeFrame);
        return result;
    }
}
