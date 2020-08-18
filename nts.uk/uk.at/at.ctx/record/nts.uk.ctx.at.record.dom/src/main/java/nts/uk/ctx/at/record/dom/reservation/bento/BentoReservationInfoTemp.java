package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 弁当予約情報
 * @author Le Huu Dat
 */
@Getter
@Setter
public class BentoReservationInfoTemp {
    /**
     * 予約登録情報
     */
    private ReservationRegisterInfo registerInfo;

    /**
     * 明細
     */
    private Map<Integer, BentoReservationCount> bentoDetails;

    /**
     * 注文済み
     */
    boolean ordered;
}
