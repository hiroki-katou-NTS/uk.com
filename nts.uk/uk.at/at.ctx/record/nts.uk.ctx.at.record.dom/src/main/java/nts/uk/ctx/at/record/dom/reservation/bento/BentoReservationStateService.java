package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * 弁当予約が強制修正できる状態を取得する
 * @author Le Huu Dat
 */
public class BentoReservationStateService {

    /**
     * 取得する
     * @param employeeId 社員ID
     * @param date 予約日
     * @return 状態
     */
    public static boolean check(BentoReservationStateService.Require require, String employeeId, GeneralDate date){
        Optional<GeneralDate> closureStartOpt = require.getClosureStart(employeeId);
        if (closureStartOpt.isPresent()){
            // 修正できる状態
            return closureStartOpt.get().beforeOrEquals(date);
        }
        return false;
    }

    public static interface Require {
        /**
         *  社員に対応する締め開始日を取得する
         */
        Optional<GeneralDate> getClosureStart(String employeeId);
    }
}
