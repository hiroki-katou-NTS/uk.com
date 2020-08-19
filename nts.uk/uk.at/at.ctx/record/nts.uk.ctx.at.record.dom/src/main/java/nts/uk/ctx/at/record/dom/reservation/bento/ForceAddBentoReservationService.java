package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 弁当予約を強制予約する
 *
 * @author Le Huu Dat
 */
public class ForceAddBentoReservationService {

    /**
     * 強制予約する
     *
     * @param require               require
     * @param bentoReservationInfos List<弁当予約情報>
     * @param reservationDate       予約対象日
     * @param dateTime              予約登録日時
     * @param workLocationCode      Optional<勤務場所コード>
     * @return AtomTask
     */
    public static AtomTask forceAdd(ForceAddBentoReservationService.Require require,
                                    List<BentoReservationInfoTemp> bentoReservationInfos,
                                    ReservationDate reservationDate,
                                    GeneralDateTime dateTime,
                                    Optional<WorkLocationCode> workLocationCode) {
        return AtomTask.of(() -> {
            for (BentoReservationInfoTemp item : bentoReservationInfos) {

                // 1: 弁当予約情報を作る(弁当予約情報): 弁当予約
                List<BentoReservationDetail> details = new ArrayList<>();
                for (Map.Entry<Integer, BentoReservationCount> mapItem : item.getBentoDetails().entrySet()) {
                    // 1.1: 作る(枠番, 個数): 弁当予約明細
                    BentoReservationDetail detail = BentoReservationDetail.createNew(mapItem.getKey(), mapItem.getValue(), dateTime);
                    details.add(detail);
                }

                // 2: 強制弁当予約する(予約登録情報, 予約対象日, 明細, Optional<勤務場所コード>): 弁当予約
                BentoReservation bentoReservation = new BentoReservation(item.getRegisterInfo(), reservationDate, item.isOrdered(), workLocationCode, details);

                // 3
                require.add(bentoReservation);
            }

        });
    }

    public static interface Require {
        void add(BentoReservation bentoReservation);
    }
}
