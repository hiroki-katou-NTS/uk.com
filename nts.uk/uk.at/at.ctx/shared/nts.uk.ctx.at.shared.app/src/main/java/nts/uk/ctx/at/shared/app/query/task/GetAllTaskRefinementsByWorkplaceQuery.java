package nts.uk.ctx.at.shared.app.query.task;


import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * 職場別作業の絞込をすべて取得する
 *
 * @author chinh.hm
 */
@Stateless
public class GetAllTaskRefinementsByWorkplaceQuery {
    @Inject
    NarrowingByWorkplaceRepository narrowingByWorkplaceRepository;

    public List<NarrowingDownTaskByWorkplace> getListWorkByCid(String cid) {
        return narrowingByWorkplaceRepository.getListWorkByCid(cid);
    }
}
