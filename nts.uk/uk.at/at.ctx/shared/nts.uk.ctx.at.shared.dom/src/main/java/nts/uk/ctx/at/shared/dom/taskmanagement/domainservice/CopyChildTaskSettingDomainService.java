package nts.uk.ctx.at.shared.dom.taskmanagement.domainservice;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskmaster.Tasks;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 子作業設定を複写する
 */
@Stateless
public class CopyChildTaskSettingDomainService {
    /**
     * 説明:紐づける子作業の設定を別の作業に複写する
     *
     * @param require
     * @param taskFrameNo
     * @param copySource
     * @param copyDestinationList
     * @return
     */
    public static AtomTask doCopy(Require require, TaskFrameNo taskFrameNo,
                                  TaskCode copySource, List<TaskCode> copyDestinationList) {
        if (copyDestinationList.isEmpty()) {
            throw new BusinessException("Msg_365");
        }
        val cid = AppContexts.user().companyId();
        val copySourceSettingOpt = require.getOptionalTask(cid, taskFrameNo, copySource);
        if (!copySourceSettingOpt.isPresent()) {
            throw new BusinessException("Msg_1185");
        }
        val copySourceSetting = copySourceSettingOpt.get();
        val copyDestinationSetting = require.getListTask(cid, taskFrameNo, copyDestinationList);

        return AtomTask.of(() -> {
            copyDestinationSetting.forEach(e -> {
                e.changeChildTaskList(require, copySourceSetting.getChildTaskList());
                require.update(e);
            });
        });
    }

    public interface Require extends Tasks.Require {
        /**
         * [R-1] 複写元の作業を取得する
         * 作業Repository.Get(会社ID,作業枠NO,コード)
         *
         * @param cid
         * @param taskFrameNo
         * @param code
         * @return
         */
        Optional<Tasks> getOptionalTask(String cid, TaskFrameNo taskFrameNo, TaskCode code);

        /**
         * [R-2] 複写先設定を取得する
         * 作業Repository.Get(会社ID,作業枠NO,作業コードリスト)
         *
         * @param cid
         * @param taskFrameNo
         * @param codes
         * @return
         */
        List<Tasks> getListTask(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes);

        /**
         * [R-3] 更新する
         * 作業Repository.Update(作業)
         *
         * @param task
         */
        void update(Tasks task);
    }
}
