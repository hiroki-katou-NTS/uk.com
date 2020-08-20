package nts.uk.ctx.at.record.dom.reservation.bento;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        Bento[] bentoList = new Bento[bentoMenu.getMenu().size()];
        bentoMenu.getMenu().toArray(bentoList);
        Optional<Bento> optionalBento = Arrays.stream(bentoList)
                .filter(x -> x.getFrameNo() == bento.getFrameNo())
                .findFirst();
        if(optionalBento.isPresent()){
            int i = Arrays.asList(bentoList).indexOf(optionalBento.get());
            bentoList[i] = bento;
        }
        BentoMenu result = new BentoMenu(bentoMenu.getHistoryID(),Arrays.asList(bentoList),bentoMenu.getClosingTime());
        return AtomTask.of(() -> {
            require.register(result);
        });
    }

    public static interface Require {
        // 弁当メニューを取得する
        BentoMenu getBentoMenu(String cid, GeneralDate date);

        // 弁当を追加する
        void register(BentoMenu bentoMenu);
    }

}
