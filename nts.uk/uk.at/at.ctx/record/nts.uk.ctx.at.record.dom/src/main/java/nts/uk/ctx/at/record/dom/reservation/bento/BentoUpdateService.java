package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;

/**
 * 弁当を更新する
 */
@Stateless
public class BentoUpdateService {

    /**
     * 更新する
     */
    public static AtomTask update(Require require, Bento bento) {

        String cid = AppContexts.user().companyId();
        GeneralDate date = GeneralDate.max();

        BentoMenu bentoMenu = require.getBentoMenu(cid, date);

        bentoMenu.getMenu().stream()
                .filter(x -> x.getFrameNo() == bento.getFrameNo())
                .forEach( b -> b = bento);
        return AtomTask.of(() -> {
            require.register(bentoMenu);
        });
    }

    public static interface Require {
        // 弁当メニューを取得する
        BentoMenu getBentoMenu(String cid, GeneralDate date);

        // 弁当を追加する
        void register(BentoMenu bentoMenu);
    }

}
