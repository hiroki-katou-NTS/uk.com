package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class ForceUpdateBentoReserveCommand {

    /**
     * 予約登録情報
     */
    List<BentoReserveInfoCommand> reservationInfos;

    /**
     * 予約対象日
     */
    GeneralDate date;

    /**
     * 新規Flg
     */
    boolean isNew;

    /**
     * 締め時刻枠
     */
    int closingTimeFrame;

    /**
     * 予約登録情報
     */
    @Value
    private class BentoReserveInfoCommand{
        /**
         * カード番号
         */
        String reservationCardNo;

        /**
         * 注文済み
         */
        boolean ordered;

        /**
         * 予約明細
         */
        List<BentoReserveDetailCommand> details;
    }

    /**
     * 予約明細
     */
    @Value
    private class BentoReserveDetailCommand {
        /**
         * 枠番
         */
        int frameNo;

        /**
         * 個数
         */
        int bentoCount;
    }
}

