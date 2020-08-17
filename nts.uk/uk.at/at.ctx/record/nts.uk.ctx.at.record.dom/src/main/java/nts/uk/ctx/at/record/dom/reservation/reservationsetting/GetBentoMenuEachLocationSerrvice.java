package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.val;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * 場所ごとのメニューを取得する
 */
@Stateless
public class GetBentoMenuEachLocationSerrvice {
    public  static List<Bento> getMenu(Require require, String cid, String histId,
                                       OperationDistinction operationDistinction, Optional<WorkLocationCode> workLocationCode){

        val bentoReservationSet = require.getBentoReservationSet(cid);

        if (bentoReservationSet.getOperationDistinction().value
                == OperationDistinction.BY_LOCATION.value){
            return require.getBentoMenu(cid,histId,workLocationCode).getMenu();
        }
        return require.getBentoMenu(cid,histId,Optional.empty()).getMenu();
    }

    public static interface Require {
        // $弁当予約設定= require.予約の運用区分を取得する(@会社ID)
        BentoReservationSetting getBentoReservationSet(String cid);
        // $弁当メニュー= require.メニューを取得する(@履歴ID,@勤務場所コード)
        BentoMenu getBentoMenu(String cid, String historyId, Optional<WorkLocationCode> workLocationCode);
    }

}
