package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.*;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class ForceUpdateBentoReserveCommand {

    /**
     * 予約登録情報
     */
    private List<BentoReserveInfoCommand> reservationInfos;

    /**
     * 予約対象日
     */
    private GeneralDate date;

    /**
     * 新規Flg
     */
    private boolean isNew;

    /**
     * 締め時刻枠
     */
    private int closingTimeFrame;

    /**
     * 予約登録情報
     */
    @Value
    public static class BentoReserveInfoCommand{
        /**
         * カード番号
         */

        private String reservationCardNo;

        /**
         * 注文済み
         */
        private boolean ordered;

        /**
         * 予約明細
         */
        private List<BentoReserveDetailCommand> details;
    }

    /**
     * 予約明細
     */
    @Value
    public static class BentoReserveDetailCommand {
        /**
         * 枠番
         */
        private int frameNo;

        /**
         * 個数
         */
        private int bentoCount;
    }
}

