package nts.uk.screen.at.app.ksm003.find;

import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;

import java.util.List;
import java.util.Optional;

public interface WorkCycleQueryRepository {

    List<WorkCycle> getByCid(String cid);

    Optional<WorkCycle> getByCidAndCode(String cid, String code);

}
