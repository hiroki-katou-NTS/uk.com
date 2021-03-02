package nts.uk.ctx.at.shared.infra.repository.workmanagement;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.ctx.at.shared.dom.workmanagement.repo.workmaster.WorkingRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaWorkingRepository extends JpaRepository implements WorkingRepository {
    @Override
    public void insert(Work work) {

    }

    @Override
    public void update(Work work) {

    }

    @Override
    public void delete(String cid, TaskFrameNo taskFrameNo, TaskCode code) {

    }

    @Override
    public List<Work> getListWork(String cid) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo) {
        return null;
    }

    @Override
    public Optional<Work> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        return Optional.empty();
    }

    @Override
    public List<Work> getListWork(String cid, List<TaskFrameNo> taskFrameNos) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate, List<TaskFrameNo> taskFrameNos) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, GeneralDate referenceDate, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        return null;
    }

    @Override
    public List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
        return null;
    }

    @Override
    public boolean checkExit(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
        return false;
    }
}
