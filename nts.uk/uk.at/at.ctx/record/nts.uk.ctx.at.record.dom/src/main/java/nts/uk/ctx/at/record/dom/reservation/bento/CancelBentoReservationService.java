package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 自分の弁当予約を取り消す
 */
public class CancelBentoReservationService {
    public static AtomTask reserve(Require require, ReservationRegisterInfo registerInfor,
                                   ReservationDate reservationDate, GeneralDateTime reservationDatetime, Optional<WorkLocationCode> workLocationCode)
    {
        // $メニュー = require.弁当メニューを取得する(対象日.年月日,@勤務場所コード)
        val bentomenu = require.getBentoMenu(reservationDate,workLocationCode);
       // $メニュー.予約受付チェック(登録情報, 対象日)
        bentomenu.receptionCheck(reservationDatetime,reservationDate);
        // $弁当予約 = require.弁当予約を取得する(登録情報, 対象日, 枠番)
        val bentoReservation = require.getBentoReservation(registerInfor,reservationDate,
                bentomenu.getMenu().stream().map(i->i.getFrameNo()).collect(Collectors.toList()));
        // $弁当予約.取消可能かチェックする()
        bentoReservation.checkCancelPossible();
        return AtomTask.of(()->{
            // require.弁当予約を取り消す(弁当予約)
            require.cancelOrder(bentoReservation);
        });
    }

    public static interface Require {
        // [R-1] 弁当予約を取得する
        BentoReservation getBentoReservation(ReservationRegisterInfo registerInfor,
                                                   ReservationDate reservationDate, List<Integer> frameNo);
        // [R-2] 弁当予約を取り消す
        void cancelOrder(BentoReservation bentoReservation);

        // [R-3] 弁当メニューを取得する
        BentoMenu getBentoMenu(ReservationDate reservationDate, Optional<WorkLocationCode> workLocationCode);
    }
}
