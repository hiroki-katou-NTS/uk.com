package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 弁当を更新する
 */
@Stateless
public class BentoUpdateService {

    /**
     * 更新する
     */
    public static AtomTask update(Require require, List<Bento> bentoList) {
        val workLocationList = bentoList.stream().filter(i->i.getWorkLocationCode().isPresent())
                .map(w-> w.getWorkLocationCode().get()).collect(Collectors.toList());
        val listFarme = bentoList.stream().map(w-> w.getFrameNo()).collect(Collectors.toList());

        List<BentoMenu> bentoMenuList = require.getBentoMenu(workLocationList,listFarme);

        return AtomTask.of(() -> {
            require.register(bentoMenuList);
        });
    }

    public static interface Require {
        // 弁当メニューを取得する
        List<BentoMenu> getBentoMenu(List<WorkLocationCode> workLocationCodeList, List<Integer> frameNos);

        // 弁当を追加する
        void register(List<BentoMenu> bentoMenus);
    }

}
