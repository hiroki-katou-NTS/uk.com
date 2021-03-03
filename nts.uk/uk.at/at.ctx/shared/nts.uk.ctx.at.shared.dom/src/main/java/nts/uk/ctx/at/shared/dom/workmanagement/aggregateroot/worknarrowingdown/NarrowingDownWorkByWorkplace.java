package nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.worknarrowingdown;


import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.workmanagement.domainservice.CheckExistenceMasterDomainService;

import java.util.List;

/**
 * AggregateRoot :職場別作業の絞込
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業絞込.職場別作業の絞込.職場別作業の絞込
 */
@Getter
@AllArgsConstructor
public class NarrowingDownWorkByWorkplace extends AggregateRoot {
    // 	職場ID
    private final String workPlaceId;
    // 作業枠NO
    private final TaskFrameNo taskFrameNo;
    // 作業一覧
    private List<TaskCode> taskCodeList;
    // 	[C-1] 絞込を作成する

    public static NarrowingDownWorkByWorkplace create(Require require, String workPlaceId, TaskFrameNo taskFrameNo, List<TaskCode> taskCodeList) {
        CheckExistenceMasterDomainService.checkExistenceWorkMaster(require,taskFrameNo, taskCodeList);
        return new NarrowingDownWorkByWorkplace(
                workPlaceId,
                taskFrameNo,
                taskCodeList
        );

    }

    public void changeCodeList(Require require, List<TaskCode> taskCodeList) {
        CheckExistenceMasterDomainService.checkExistenceWorkMaster(require,taskFrameNo, taskCodeList);
        this.taskCodeList = taskCodeList;

    }

    public interface Require extends CheckExistenceMasterDomainService.Require {
    }
}
