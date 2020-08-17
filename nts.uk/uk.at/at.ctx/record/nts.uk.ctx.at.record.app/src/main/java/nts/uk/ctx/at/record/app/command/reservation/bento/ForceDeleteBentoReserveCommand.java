package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class ForceDeleteBentoReserveCommand {

    /**
     * 予約登録情報
     */
    private List<ReservationInfo> reservationInfos;

    /**
     * 予約対象日
     */
    private GeneralDate date;

    /**
     * 締め時刻枠
     */
    private int closingTimeFrame;

    @Value
    public class ReservationInfo{
        /**
         * 予約登録情報
         */
        private String reservationCardNo;

        /**
         *
         */
        private String empployeeId;
    }
}

