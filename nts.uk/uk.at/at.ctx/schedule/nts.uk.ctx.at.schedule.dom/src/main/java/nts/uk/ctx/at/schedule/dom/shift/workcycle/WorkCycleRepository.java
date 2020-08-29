package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import java.util.List;
import java.util.Optional;

public interface WorkCycleRepository {

    List<WorkCycle> getByCid(String cid);

    void add(WorkCycle item);

    void update(WorkCycle item);

    Optional<WorkCycle> getByCidAndCode(String cid, String code);

    void delete(String cid, String code);

    boolean exists(String cid, String code);

}
