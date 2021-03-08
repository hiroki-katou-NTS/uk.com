package nts.uk.ctx.at.shared.dom.workmanagement.domainservice;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.worknarrowingdown.NarrowingDownWorkByWorkplace;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 絞込設定を複写する
 * Responsibility: 職場別作業の絞込を別の職場に複写する
 *
 * @author chinh.hm
 */
@Stateless
public class CopyRefinementSettingDomainService {
    public static AtomTask doCopy(Require require,
                                  String copySourceWplId, String copyDestinationWplId) {
        val copySourceSettings = require.getListWorkByWpl(copySourceWplId);
        if (copySourceSettings.isEmpty()) {
            throw new BusinessException("Msg_1185");
        }
        val registeredList = require.getListWorkByWpl(copyDestinationWplId);
        return AtomTask.of(() -> {
            registeredList.forEach(e -> {
                require.delete(copyDestinationWplId, e.getTaskFrameNo());
            });
            copySourceSettings.forEach(e -> {
                val newSetting = NarrowingDownWorkByWorkplace.create(require, copyDestinationWplId,
                        e.getTaskFrameNo(), e.getTaskCodeList());
                require.insert(newSetting);

            });
        });
    }

    public interface Require extends NarrowingDownWorkByWorkplace.Require {
        /**
         * [R-1] 絞込設定を取得する
         * 職場別作業の絞込Repository.get*(職場ID)
         *
         * @param workPlaceId
         * @return
         */
        List<NarrowingDownWorkByWorkplace> getListWorkByWpl(String workPlaceId);

        /**
         * [R-2] 追加する
         * 職場別作業の絞込Repository.Insert(職場別作業の絞込)
         *
         * @param narrowing
         */
        void insert(NarrowingDownWorkByWorkplace narrowing);

        /**
         * [R-3] 削除する
         * 職場別作業の絞込Repository.Delete (職場ID, 作業枠NO)
         *
         * @param workPlaceId
         * @param taskFrameNo
         */
        void delete(String workPlaceId, TaskFrameNo taskFrameNo);


    }
}
