package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class ForceDeleteBentoReserveCommand {

    /**
     * 予約登録情報
     */
    List<ReservationInfo> reservationInfos;

    /**
     * 予約対象日
     */
    GeneralDate date;

    /**
     * 締め時刻枠
     */
    int closingTimeFrame;

    @Value
    public class ReservationInfo{
        /**
         * 予約登録情報
         */
        String reservationCardNo;

        /**
         * 社員ID
         */
        String empployeeId;
    }
}

