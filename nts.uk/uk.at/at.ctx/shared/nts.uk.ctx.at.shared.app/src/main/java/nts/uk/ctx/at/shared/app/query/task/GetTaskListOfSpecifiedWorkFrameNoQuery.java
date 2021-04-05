package nts.uk.ctx.at.shared.app.query.task;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Query:指定された作業枠NOの作業一覧を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.App.指定された作業枠NOの作業一覧を取得する.指定された作業枠NOの作業一覧を取得する
 *
 * @author chinh.hm
 */
@Stateless
public class GetTaskListOfSpecifiedWorkFrameNoQuery {
    @Inject
    private TaskingRepository taskingRepository;
    /**
     * 取得する
     * @param cid
     * @param taskNo
     * @return
     */
    public List<Task> getListTask(String cid, Integer taskNo){
        val taskFrameNo = new TaskFrameNo(taskNo);
        return taskingRepository.getListTask(cid,taskFrameNo);
    }
}
