package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.worknarrowingdown.NarrowingDownWorkByWorkplace;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.worknarrowingdown.NarrowingByWorkplaceRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaNarrowingByWorkplaceRepository extends JpaRepository implements NarrowingByWorkplaceRepository {
    @Override
    public void insert(NarrowingDownWorkByWorkplace narrowing) {

    }

    @Override
    public void update(NarrowingDownWorkByWorkplace narrowing) {

    }

    @Override
    public void delete(String workPlaceId, TaskFrameNo taskFrameNo) {

    }

    @Override
    public List<NarrowingDownWorkByWorkplace> getListWork(String cid) {
        return null;
    }

    @Override
    public Optional<NarrowingDownWorkByWorkplace> getOptionalWork(String workPlaceId, TaskFrameNo taskFrameNo) {
        return Optional.empty();
    }

    @Override
    public List<NarrowingDownWorkByWorkplace> getListWork(List<String> workPlaceIds, TaskFrameNo taskFrameNo) {
        return null;
    }

    @Override
    public List<NarrowingDownWorkByWorkplace> getListWork(String workPlaceId, GeneralDate referenceDate) {
        return null;
    }
}
