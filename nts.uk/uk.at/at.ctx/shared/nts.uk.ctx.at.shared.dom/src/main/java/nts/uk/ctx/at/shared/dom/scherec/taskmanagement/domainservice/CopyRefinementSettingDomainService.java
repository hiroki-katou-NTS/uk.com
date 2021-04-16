package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;

import javax.ejb.Stateless;
import java.util.List;

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
        List<NarrowingDownTaskByWorkplace> copySourceSettings = require.getListWorkByWpl(copySourceWplId);
        if (copySourceSettings.isEmpty()) {
            throw new BusinessException("Msg_1185");
        }

        return AtomTask.of(() -> {
            require.delete(copyDestinationWplId);
            copySourceSettings.forEach(e -> {
                NarrowingDownTaskByWorkplace newSetting = NarrowingDownTaskByWorkplace.create(require, copyDestinationWplId,
                        e.getTaskFrameNo(), e.getTaskCodeList());
                require.insert(newSetting);
            });
        });
    }

    public interface Require extends NarrowingDownTaskByWorkplace.Require {
        /**
         * [R-1] 絞込設定を取得する
         * 職場別作業の絞込Repository.get*(職場ID)
         *
         * @param workPlaceId
         * @return
         */
        List<NarrowingDownTaskByWorkplace> getListWorkByWpl(String workPlaceId);

        /**
         * [R-2] 追加する
         * 職場別作業の絞込Repository.Insert(職場別作業の絞込)
         *
         * @param narrowing
         */
        void insert(NarrowingDownTaskByWorkplace narrowing);

        /**
         * [R-3] 削除する
         * 職場別作業の絞込Repository.Delete (職場ID, 作業枠NO)
         *
         * @param workPlaceId
         */
        void delete(String workPlaceId);


    }
}
