package nts.uk.ctx.at.shared.dom.workmanagement.domainservice;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 絞込設定を複写する
 *
 * @author chinh.hm
 */
@Stateless
public class CopyRefinementSettingDomainService {
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
        val copySourceSettingOpt = require.getOptionalWork(cid, taskFrameNo, copySource);
        if (!copySourceSettingOpt.isPresent()) {
            throw new BusinessException("Msg_1185");
        }
        val copySourceSetting = copySourceSettingOpt.get();
        val copyDestinationSetting = require.getListWork(cid, taskFrameNo, copyDestinationList);

        return AtomTask.of(() -> {
            copyDestinationSetting.forEach(e -> {
                e.changeChildWorkList(require, copySourceSetting.getChildWorkList());
                require.update(e);
            });
        });
    }

    public interface Require extends Work.Require {
        /**
         * [R-1] 複写元の作業を取得する
         * 作業Repository.Get(会社ID,作業枠NO,コード)
         *
         * @param cid
         * @param taskFrameNo
         * @param code
         * @return
         */
        Optional<Work> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code);

        /**
         * [R-2] 複写先設定を取得する
         * 作業Repository.Get(会社ID,作業枠NO,作業コードリスト)
         *
         * @param cid
         * @param taskFrameNo
         * @param codes
         * @return
         */
        List<Work> getListWork(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes);

        /**
         * [R-3] 更新する
         * 作業Repository.Update(作業)
         *
         * @param work
         */
        void update(Work work);
    }
}
