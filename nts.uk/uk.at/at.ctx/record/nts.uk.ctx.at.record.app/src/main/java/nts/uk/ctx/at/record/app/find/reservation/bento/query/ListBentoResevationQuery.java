package nts.uk.ctx.at.record.app.find.reservation.bento.query;

import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  一覧弁当予約を取得する
 * @author Hoang Anh Tuan
 */
@Stateless
public class ListBentoResevationQuery {

    @Inject
    BentoReservationRepository bentoReservationRepository;

    private final boolean ORDERED = true;
    private final boolean UN_ORDERED = false;

    public List<BentoReservation> getListBentoResevationQuery(BentoReservationSearchCondition searchCondition,
                                  DatePeriod period, List<ReservationRegisterInfo> reservationRegisterInfos,
                                  List<WorkLocationCode> workLocationCodes, ReservationClosingTimeFrame reservationClosingTimeFrame){
        /** 検索条件==１商品２件以上 */
        if(searchCondition == BentoReservationSearchCondition.MORE_THAN_1_PRODUCT)
            return bentoReservationRepository.acquireReservationDetails(reservationRegisterInfos,period,reservationClosingTimeFrame,workLocationCodes);


        /** 検索条件 == 注文済み */
        if(searchCondition == BentoReservationSearchCondition.ORDERED)
            return getOrderedBentoReservationsDetail(reservationRegisterInfos, period, reservationClosingTimeFrame, workLocationCodes);

        /** 検索条件 == 未注文 */
        if(searchCondition == BentoReservationSearchCondition.UN_ORDERED)
            return getUnOrderedBentoReservationsDetail(reservationRegisterInfos, period, reservationClosingTimeFrame, workLocationCodes);

        /** 検索条件 == 新規注文 */
        //if(searchCondition == BentoReservationSearchConditionDto.NEW_ORDER)
            //return getNewOrderDetail(period,reservationRegisterInfos,reservationClosingTimeFrame);

        /** 検索条件 ==　全部 */
        if(searchCondition == BentoReservationSearchCondition.ALL)
            return bentoReservationRepository.getAllReservationDetail(reservationRegisterInfos,period,reservationClosingTimeFrame,workLocationCodes);

        return Collections.EMPTY_LIST;
    }

    public List<BentoReservation> getOrderedBentoReservationsDetail(List<ReservationRegisterInfo> reservationRegisterInfos, DatePeriod period,
                                                             ReservationClosingTimeFrame reservationClosingTimeFrame, List<WorkLocationCode> workLocationCodes){
        return handleData(bentoReservationRepository.getReservationDetailFromOrder(reservationRegisterInfos, period, reservationClosingTimeFrame, ORDERED, workLocationCodes), reservationClosingTimeFrame, workLocationCodes);
    }
    public List<BentoReservation> getUnOrderedBentoReservationsDetail(List<ReservationRegisterInfo> reservationRegisterInfos, DatePeriod period,
                                                                    ReservationClosingTimeFrame reservationClosingTimeFrame, List<WorkLocationCode> workLocationCodes){
        return handleData(bentoReservationRepository.getReservationDetailFromOrder(reservationRegisterInfos, period, reservationClosingTimeFrame, UN_ORDERED, workLocationCodes), reservationClosingTimeFrame, workLocationCodes);
    }


    public List<BentoReservation> handleData(List<BentoReservation> bentoReservations,
                                             ReservationClosingTimeFrame reservationClosingTimeFrame, List<WorkLocationCode> workLocationCodes){
        if(CollectionUtil.isEmpty(workLocationCodes))
            return bentoReservations.stream()
                    .filter(item -> !item.getWorkLocationCode().isPresent())
                    .filter(item -> reservationClosingTimeFrame == item.getReservationDate().getClosingTimeFrame())
                    .collect(Collectors.toList());

        return bentoReservations.stream()
                                .filter(item -> workLocationCodes.contains(item.getWorkLocationCode().orElse(null)))
                                .filter(item -> reservationClosingTimeFrame == item.getReservationDate().getClosingTimeFrame())
                                .collect(Collectors.toList());
    }

    /**
     * 検索条件 == 新規注文
     */
    public List<ReservationRegisterInfo> getNewOrderDetail(DatePeriod period, List<ReservationRegisterInfo> reservationRegisterInfos, ReservationClosingTimeFrame reservationClosingTimeFrame){
        Set<ReservationRegisterInfo> results = new HashSet<>();
        period.stream().forEach( date ->{
            results.addAll(bentoReservationRepository.getEmployeeNotOrder(reservationRegisterInfos,
                    new ReservationDate(date, reservationClosingTimeFrame )));
                }
        );
        return new ArrayList<>(results);
    }

}
