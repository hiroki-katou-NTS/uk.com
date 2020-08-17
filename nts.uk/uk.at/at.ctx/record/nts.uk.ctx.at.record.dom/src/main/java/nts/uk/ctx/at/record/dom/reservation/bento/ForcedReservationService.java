package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 弁当予約を強制予約する
 */
public class ForcedReservationService {
    public static AtomTask reserve(Require require, List<ReservationRegisterInfo> registerInfor,
                                   ReservationDate reservationDate, GeneralDateTime reservationDateTime, Optional<WorkLocationCode> workLocationCode)
    {
        //if @List<弁当予約情報>.size < 0
        //return AtomTask
        if (registerInfor.size() <0){
            return  AtomTask.of(()->{

            });
        }

        return AtomTask.of(()->{
            val
        });
    }

    public static interface Require {
        // 	require.弁当予約を追加する($.予約登録情報,@予約対象日
        //,$弁当予約明細,Optional<勤務場所コード>)
    }
}
