package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService : 紐づける子作業を変更する
 *
 * @author chinh.hm
 */
@Stateless
public class ChangeChildTaskLinkedDomainService {
    /**
     * 説明:紐づける子作業を追加、または変更する
     *
     * @param require
     * @param taskFrameNo
     * @param parentWorkCode
     * @param childWorkList
     * @return
     */
    public static AtomTask change(Require require, TaskFrameNo taskFrameNo, TaskCode parentWorkCode, List<TaskCode> childWorkList) {
        val cid = AppContexts.user().companyId();
        TaskFrameUsageSetting taskFrameUsageSetting = require.getWorkFrameUsageSetting(cid);
        if (taskFrameUsageSetting == null) {
            throw new BusinessException("Msg_1959");
        }
        if (taskFrameNo.v() == 5) {
            throw new BusinessException("Msg_2066");

        }
        val taskFrameOpt = taskFrameUsageSetting.getFrameSettingList()
                .stream().filter(x -> x.getTaskFrameNo().v().equals(taskFrameNo.v() + 1)).findFirst();
        if (taskFrameOpt.isPresent()) {
            val taskFrame = taskFrameOpt.get();
            if (taskFrame.getUseAtr().value == UseAtr.NOTUSE.value)
                throw new BusinessException("Msg_1957", taskFrame.getTaskFrameName().v());
        }
        val taskOptional = require.getOptionalWork(cid, taskFrameNo, parentWorkCode);
        if (!taskOptional.isPresent()) {
            throw new BusinessException("Msg_2065");
        }
        val taskOld = taskOptional.get();
        taskOld.changeChildTaskList(require, childWorkList);

        return AtomTask.of(() -> {
            require.update(taskOld);
        });
    }


    public interface Require extends Task.Require {

        /**
         * [R-1] 作業枠利用設定を取得する
         * 作業枠利用設定Repository.Get(会社ID)
         *
         * @param cid
         * @return
         */
        TaskFrameUsageSetting getWorkFrameUsageSetting(String cid);

        /**
         * [R-2] 作業を取得する
         * 作業Repository.Get(会社ID,作業枠NO,コード)
         *
         * @param cid
         * @param taskFrameNo
         * @param code
         * @return
         */
        Optional<Task> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code);

        /**
         * [R-3] 作業を更新する
         * 作業Repository.Update(作業)
         *
         * @param task
         */
         void update(Task task);
            

    }
}
