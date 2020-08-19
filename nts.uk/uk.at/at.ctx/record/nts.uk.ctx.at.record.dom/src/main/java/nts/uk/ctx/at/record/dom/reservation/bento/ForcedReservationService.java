package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 弁当予約を強制予約する
 */
@Stateless
public class ForcedReservationService {
    public static AtomTask reserve(Require require, List<LunchReservationInfor> lunchReservationInfors,
                                   ReservationDate reservationDate, GeneralDateTime reservationDateTime, Optional<WorkLocationCode> workLocationCode)
    {
        //if @List<弁当予約情報>.size < 0
        //return AtomTask
        if (lunchReservationInfors.size() <0){
            return  AtomTask.of(()->{

            });
        }

        // 作る(弁当メニュー枠番, 弁当予約個数) 枠番, 個数 out 弁当予約明細
        List<BentoReservation> listRegist = new ArrayList<>();

        lunchReservationInfors.stream().map(i-> {
            val details = i.getDetails().entrySet().stream()
            .map(e -> BentoReservationDetail.createNew(e.getKey(), new BentoReservationCount(e.getValue()),reservationDateTime)).collect(Collectors.toList());
            BentoReservation bentoReservation = BentoReservation.reserve(i.getReservationRegisterInfo(),reservationDate,workLocationCode,details);
            return listRegist.add(bentoReservation);
        });

        // BentoReservationDetail  bentoReservationDetail= new  BentoReservationDetail();
        return AtomTask.of(()->{
            //val
        });
    }

    public static interface Require {
        // 	require.弁当予約を追加する($.予約登録情報,@予約対象日
        //,$弁当予約明細,Optional<勤務場所コード>)
    }
}
