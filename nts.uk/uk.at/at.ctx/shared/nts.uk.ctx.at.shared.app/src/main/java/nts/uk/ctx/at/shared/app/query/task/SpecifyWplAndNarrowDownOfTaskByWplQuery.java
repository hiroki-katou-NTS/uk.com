package nts.uk.ctx.at.shared.app.query.task;


import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * 職場を指定して職場別作業の絞込を取得する
 */
@Stateless
public class SpecifyWplAndNarrowDownOfTaskByWplQuery {
    @Inject
    private NarrowingByWorkplaceRepository repository;

    public List<NarrowingDownTaskByWorkplace> getListWorkByWpl(String workPlaceId) {
        return repository.getListWorkByWpl(workPlaceId);
    }
}
