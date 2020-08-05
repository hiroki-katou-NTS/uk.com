package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.Achievements;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.CorrectionContent;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.shr.com.context.AppContexts;

/**
 * 弁当予約設定を登録する
 */
public class RegisterReservationLunchService {

    /**
     * 登録する
     */
    public static AtomTask register(
            Require require,OperationDistinction operationDistinction,Achievements arAchievements,
            CorrectionContent correctionContent, BentoReservationClosingTime bentoReservationClosingTime,String hisId) {

        if(bentoReservationClosingTime.getClosingTime1().value == null){
            throw new BusinessException("Msg_0001");
        }

        String companyId = AppContexts.user().companyId();

        // 1: get(会社ID)
        BentoReservationSetting bentoReservationSetting = require.getReservationSettings(companyId);

        if(bentoReservationSetting.getAchievements().getReferenceTime() == null){
            throw new BusinessException("Msg_0001");
        }

        // 3: get(会社ID,履歴ID)
        BentoMenu bentoMenu = require.getBentoMenu(companyId, hisId);

        return AtomTask.of(() -> {
            require.register(bentoReservationSetting,bentoMenu);
        });
    }

    public static interface Require {

        BentoReservationSetting getReservationSettings(String cid);
        void registerSetting(Achievements achievements,CorrectionContent correctionContent,OperationDistinction operationDistinction);
        BentoMenu getBentoMenu(String cid,String hisId);
        void registerSetting(BentoReservationClosingTime closingTime);
        void register(BentoReservationSetting bentoReservationSetting,BentoMenu bentoMenu);
    }

}
