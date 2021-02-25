package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 弁当予約を強制修正する
 *
 * @author Le Huu Dat
 */
public class ForceUpdateBentoReservationService {

    /**
     * 強制修正する
     *
     * @param require               require
     * @param reservationDate       予約対象日
     * @param bentoReservationInfos List<弁当予約情報>
     * @return AtomTask
     */
    public static AtomTask forceUpdate(ForceUpdateBentoReservationService.Require require,
                                       ReservationDate reservationDate,
                                       List<BentoReservationInfoTemp> bentoReservationInfos) {
        if (CollectionUtil.isEmpty(bentoReservationInfos)) {
            return AtomTask.of(() -> {
            });
        }

        return AtomTask.of(() -> {
            if (bentoReservationInfos == null || reservationDate == null) return;
            GeneralDateTime datetime = GeneralDateTime.now();
            for (BentoReservationInfoTemp item : bentoReservationInfos) {
                // 1: get(予約登録情報, 予約対象日)
                BentoReservation bentoReservation = require.get(item.getRegisterInfo(), reservationDate);
                List<BentoReservationDetail> bentoReservationDetails = new ArrayList<>();
                if (bentoReservation == null) continue;

                Optional< BentoReservationDetail> firstDetail =  bentoReservation.getBentoReservationDetails().stream().findFirst();
                if(firstDetail.isPresent()){
                    datetime = firstDetail.get().getDateTime();
                }

                // 2: 作る(弁当予約情報):弁当予約明細
                for (Map.Entry<Integer, BentoReservationCount> mapItem : item.getBentoDetails().entrySet()) {
                    // 2.1: 作る(枠番, 個数): 弁当予約明細
                    BentoReservationDetail detail = BentoReservationDetail.createNew(mapItem.getKey(), mapItem.getValue(), datetime);
                    bentoReservationDetails.add(detail);
                }

                // 3: 強制弁当予約修正する(注文済み, 更後の明細): 弁当予約
                bentoReservation.setOrdered(item.ordered);

                BentoReservation bentoReservationAfter = new BentoReservation(bentoReservation.getRegisterInfor(),
                        bentoReservation.getReservationDate(), bentoReservation.isOrdered(),
                        bentoReservation.getWorkLocationCode(), bentoReservationDetails);

                // 4
                require.update(bentoReservationAfter);
            }
        });
    }

    public static interface Require {

        /**
         * get
         *
         * @param registerInfo    予約登録情報
         * @param reservationDate 予約対象日
         * @return 弁当予約
         */
        BentoReservation get(ReservationRegisterInfo registerInfo, ReservationDate reservationDate);

        void update(BentoReservation bentoReservation);
    }
}
