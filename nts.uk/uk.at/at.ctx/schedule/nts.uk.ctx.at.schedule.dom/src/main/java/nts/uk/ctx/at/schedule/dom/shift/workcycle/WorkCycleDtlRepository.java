package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import java.util.List;

public interface WorkCycleDtlRepository {

    List<WorkCycleInfo> getByCode(String cid, String code);

    void add(WorkCycle item);

    void update(WorkCycle item);

    void delete(String cid, String code, int dispOrder);

}
