package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;

import java.util.List;
import java.util.Optional;

public class DeleteWorkCycleService {

    public static void delete(Require require, String cid, String code) {
        Optional<WorkCycle> workingCycle = require.getWorkingCycle(cid, code);
        if (workingCycle.isPresent()) {
            require.deleteWorkCycle(cid, code);
            List<WorkCycleInfo> infos = require.getWorkingCycleInfos(cid, code);
            infos.forEach(i -> {
                require.deleteWorkCycleInfo(cid, code, i.getDispOrder().v());
            });
        }
    }

    public static interface Require {

        Optional<WorkCycle> getWorkingCycle(String cid, String code);

        List<WorkCycleInfo> getWorkingCycleInfos(String cid, String code);

        void deleteWorkCycle(String cid, String code);

        void deleteWorkCycleInfo(String cid, String code, int dispOrder);

    }

}
