package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.Data;

import java.util.Map;

@Data
public class LunchReservationInfor {

    // 予約登録情報
    private  ReservationRegisterInfo reservationRegisterInfo;

    // Map<枠番,個数>
    private Map<Integer,Integer> details;

    // 注文済み
    private boolean ordered;
}
