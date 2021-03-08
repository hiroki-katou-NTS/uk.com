package nts.uk.ctx.at.shared.dom.workmanagement.domainservice;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
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
public class ChangeChildWorkLinkedDomainService {
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
        WorkFrameUsageSetting workFrameUsageSetting = require.getWorkFrameUsageSetting(cid);
        if (workFrameUsageSetting == null) {
            throw new BusinessException("Msg_1959");
        }
        if (taskFrameNo.v() == 5) {
            throw new BusinessException("Msg_2066");

        }
        val workFrameOpt = workFrameUsageSetting.getFrameSettingList()
                .stream().filter(x -> x.getTaskFrameNo().v().equals(taskFrameNo.v() + 1)).findFirst();
        if (workFrameOpt.isPresent()) {
            val workFrame = workFrameOpt.get();
            if (workFrame.getUseAtr().value == UseAtr.NOTUSE.value)
                throw new BusinessException("Msg_1957", workFrame.getWorkFrameName().v());
        }
        val workOptional = require.getOptionalWork(cid, taskFrameNo, parentWorkCode);
        if (!workOptional.isPresent()) {
            throw new BusinessException("Msg_2065");
        }
        val workOld = workOptional.get();
        workOld.changeChildWorkList(require, childWorkList);

        return AtomTask.of(() -> {
            require.update(workOld);
        });
    }


    public interface Require extends Work.Require {

        /**
         * [R-1] 作業枠利用設定を取得する
         * 作業枠利用設定Repository.Get(会社ID)
         *
         * @param cid
         * @return
         */
        WorkFrameUsageSetting getWorkFrameUsageSetting(String cid);

        /**
         * [R-2] 作業を取得する
         * 作業Repository.Get(会社ID,作業枠NO,コード)
         *
         * @param cid
         * @param taskFrameNo
         * @param code
         * @return
         */
        Optional<Work> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code);

        /**
         * [R-3] 作業を更新する
         * 作業Repository.Update(作業)
         *
         * @param work
         */
        void update(Work work);
    }
}
