package nts.uk.ctx.at.shared.dom.workmanagement.domainservice;


import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  DomainService: 作業マスタの存在チェックする
 *  @author chinh.hm
 */
@Stateless
public class CheckExistenceMasterDomainService {
    /**
     * 	説明:指定されている作業コードの中にマスタが削除されている作業があるか確認する
     * @param require
     * @param taskFrameNo
     * @param childWorkList
     */
    public static void checkExistenceWorkMaster(Require require,TaskFrameNo taskFrameNo, List<TaskCode> childWorkList) {
        val cid = AppContexts.user().companyId();
        val workList = require.getWork(cid,taskFrameNo);
        Map<TaskFrameNo,Work> frameNoWorkMap = workList.stream().collect(Collectors.toMap(Work::getTaskFrameNo,e->e));

    }

    public interface Require {
        // R-1] 作業を取得する
        List<Work> getWork(String cid, TaskFrameNo taskFrameNo);
    }
}
