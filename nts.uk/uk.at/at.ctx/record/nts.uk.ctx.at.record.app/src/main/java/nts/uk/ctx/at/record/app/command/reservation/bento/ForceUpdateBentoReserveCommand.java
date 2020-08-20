package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
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
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public class BentoReserveInfoCommand{
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
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public class BentoReserveDetailCommand {
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

