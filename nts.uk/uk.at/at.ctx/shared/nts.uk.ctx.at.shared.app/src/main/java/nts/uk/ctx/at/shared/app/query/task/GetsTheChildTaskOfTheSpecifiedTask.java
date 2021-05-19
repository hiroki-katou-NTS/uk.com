package nts.uk.ctx.at.shared.app.query.task;


import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Query: 指定された作業の子作業を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.App.指定された作業の子作業を取得する.指定された作業の子作業を取得する
 *
 * @author chinh.hm
 */

@Stateless
public class GetsTheChildTaskOfTheSpecifiedTask {
    @Inject
    private TaskingRepository taskingRepository;

    /**
     * 子作業をすべて取得する
     *
     * @param cid
     * @param taskNo
     * @param code
     * @return
     */
    public List<Task> getAllChildTask(String cid, Integer taskNo, String code) {

        TaskFrameNo taskFrameNo = new TaskFrameNo(taskNo);
        TaskCode taskCode = new TaskCode(code);
        return taskingRepository.getListChildTask(cid, taskFrameNo, taskCode);
    }

    ;
}
