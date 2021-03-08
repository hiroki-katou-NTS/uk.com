package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.tasknarrowingdown;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;

import java.util.List;

/**
 * AggregateRoot :職場別作業の絞込
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業絞込.職場別作業の絞込.職場別作業の絞込
 */
@Getter
@AllArgsConstructor
public class NarrowingDownTaskByWorkplace extends AggregateRoot {
    // 	職場ID
    private final String workPlaceId;
    // 作業枠NO
    private final TaskFrameNo taskFrameNo;
    // 作業一覧
    private List<TaskCode> taskCodeList;
    // 	[C-1] 絞込を作成する

    public static NarrowingDownTaskByWorkplace create(Require require, String workPlaceId, TaskFrameNo taskFrameNo, List<TaskCode> taskCodeList) {
        CheckExistenceMasterDomainService.checkExistenceTaskMaster(require,taskFrameNo, taskCodeList);
        return new NarrowingDownTaskByWorkplace(
                workPlaceId,
                taskFrameNo,
                taskCodeList
        );

    }
    // 	[1] 変更する
    public void changeCodeList(Require require, List<TaskCode> taskCodeList) {
        CheckExistenceMasterDomainService.checkExistenceTaskMaster(require,taskFrameNo, taskCodeList);
        this.taskCodeList = taskCodeList;

    }

    public interface Require extends CheckExistenceMasterDomainService.Require {
    }
}
