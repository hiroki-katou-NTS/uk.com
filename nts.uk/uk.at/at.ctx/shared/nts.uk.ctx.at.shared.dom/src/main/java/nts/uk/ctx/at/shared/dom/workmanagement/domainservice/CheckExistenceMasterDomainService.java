package nts.uk.ctx.at.shared.dom.workmanagement.domainservice;


import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CheckExistenceMasterDomainService {

    public static void checkExistenceWorkMaster(Require require,TaskFrameNo taskFrameNo, List<TaskCode> childWorkList) {

    }

    public interface Require {
        // R-1] 作業を取得する
        Work getWork(String cid, TaskFrameNo taskFrameNo);
    }
}
