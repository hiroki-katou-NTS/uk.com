package nts.uk.ctx.at.record.app.find.reservation.bento.query;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.BentoReservationSearchConditionDto;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 一覧弁当予約を取得する
 * @author Hoang Anh Tuan
 */
public class ListBentoResevationQuery {

    @Inject
    BentoReservationRepository bentoReservationRepository;

    private final boolean ORDERED = true;
    private final boolean UN_ORDERED = false;

    public List<BentoReservation> getListBentoResevationQuery(BentoReservationSearchConditionDto searchCondition,
                                  DatePeriod period, List<ReservationRegisterInfo> reservationRegisterInfos,
                                  List<WorkLocationCode> workLocationCodes, ReservationClosingTimeFrame reservationClosingTimeFrame){
        /** 検索条件==１商品２件以上 */
        if(searchCondition == BentoReservationSearchConditionDto.MORE_THAN_1_PRODUCT) {
            List<BentoReservation> result = new ArrayList<>();
            result.addAll(getOrderedBentoReservationsDetail(reservationRegisterInfos, period, reservationClosingTimeFrame, workLocationCodes));
            result.addAll(getUnOrderedBentoReservationsDetail(reservationRegisterInfos, period, reservationClosingTimeFrame, workLocationCodes));
            return result;
        }

        /** 検索条件 == 注文済み */
        if(searchCondition == BentoReservationSearchConditionDto.ORDERED)
            return getOrderedBentoReservationsDetail(reservationRegisterInfos, period, reservationClosingTimeFrame, workLocationCodes);

        /** 検索条件 == 未注文 */
        if(searchCondition == BentoReservationSearchConditionDto.UN_ORDERED)
            return getUnOrderedBentoReservationsDetail(reservationRegisterInfos, period, reservationClosingTimeFrame, workLocationCodes);


        return Collections.EMPTY_LIST;
    }

    public List<BentoReservation> getOrderedBentoReservationsDetail(List<ReservationRegisterInfo> reservationRegisterInfos, DatePeriod period,
                                                             ReservationClosingTimeFrame reservationClosingTimeFrame, List<WorkLocationCode> workLocationCodes){
        return handleData(bentoReservationRepository.findByOrderedPeriodEmpLst(reservationRegisterInfos, period, reservationClosingTimeFrame, ORDERED, workLocationCodes), reservationClosingTimeFrame, workLocationCodes);
    }
    public List<BentoReservation> getUnOrderedBentoReservationsDetail(List<ReservationRegisterInfo> reservationRegisterInfos, DatePeriod period,
                                                                    ReservationClosingTimeFrame reservationClosingTimeFrame, List<WorkLocationCode> workLocationCodes){
        return handleData(bentoReservationRepository.findByOrderedPeriodEmpLst(reservationRegisterInfos, period, reservationClosingTimeFrame, UN_ORDERED, workLocationCodes), reservationClosingTimeFrame, workLocationCodes);
    }


    public List<BentoReservation> handleData(List<BentoReservation> bentoReservations,
                                             ReservationClosingTimeFrame reservationClosingTimeFrame, List<WorkLocationCode> workLocationCodes){
        return bentoReservations.stream()
                                .filter(item -> workLocationCodes.contains(item.getWorkLocationCode().get()))
                                .filter(item -> reservationClosingTimeFrame == item.getReservationDate().getClosingTimeFrame())
                                .collect(Collectors.toList());
    }

}
