package nts.uk.ctx.at.shared.app.query.task;


import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 職場別作業の絞込をすべて取得する=> 作業枠を指定して職場別作業の絞込を取得する
 * Update : # 115628
 *
 * @author chinh.hm
 */
@Stateless
public class GetAllTaskRefinementsByWorkplaceQuery {
    @Inject
    NarrowingByWorkplaceRepository narrowingByWorkplaceRepository;

    public List<NarrowingDownTaskByWorkplace> getListWorkByCid(String cid, List<Integer> listFrameNo) {
        List<NarrowingDownTaskByWorkplace> rs = new ArrayList<>();
        listFrameNo.forEach(e -> {
            val fr = new TaskFrameNo(e);
            rs.addAll(narrowingByWorkplaceRepository.getListWorkByCidAndFrameNo(cid, fr));
        });
        return rs;
    }
}
