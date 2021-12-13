package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;

/**
 * 弁当予約設定を登録する
 * delete #120643
 *
 * @author Quang.nh1
 */
public class RegisterReservationLunchService {

    /**
     * 登録する
     */
    public static AtomTask register(
            Require require, OperationDistinction operationDistinction, Achievements arAchievements,
            CorrectionContent correctionContent, BentoReservationClosingTime bentoReservationClosingTime) {

        String companyId = AppContexts.user().companyId();
        GeneralDate date = GeneralDate.max();

        // 1: get(会社ID)
        ReservationSetting bentoReservationSetting = require.getReservationSettings(companyId);

        // 3: get(会社ID,’9999/12/31’)
        BentoMenuHistory bentoMenu = require.getBentoMenu(companyId, date);
        String historyID = bentoMenu != null ? bentoMenu.getHistoryID() : null;

        return AtomTask.of(() -> {
            require.registerBentoMenu(historyID, bentoReservationClosingTime,operationDistinction);
//            ReservationSetting newSetting = new ReservationSetting(companyId, operationDistinction, correctionContent, arAchievements);
//
//            if (bentoReservationSetting == null) {
//                require.inSert(newSetting);
//            } else {
//                require.update(newSetting);
//            }
        });
    }

    public static interface Require {

        /**
         * 弁当予約設定を取得する
         */
        ReservationSetting getReservationSettings(String cid);

        /**
         * 弁当メニュを取得する
         */
        BentoMenuHistory getBentoMenu(String cid, GeneralDate date);

        /**
         * 弁当メニューを登録する
         */
        void registerBentoMenu(String historyID, BentoReservationClosingTime bentoReservationClosingTime,OperationDistinction OperationDistinction);

        /**
         * Insert（弁当予約設定）
         */
        void inSert(ReservationSetting bentoReservationSetting);

        /**
         * Update（弁当予約設定）
         */
        void update(ReservationSetting bentoReservationSetting);

    }

}
