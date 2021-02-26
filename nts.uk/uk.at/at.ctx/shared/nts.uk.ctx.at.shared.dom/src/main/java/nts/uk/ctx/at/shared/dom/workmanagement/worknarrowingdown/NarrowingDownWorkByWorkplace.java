package nts.uk.ctx.at.shared.dom.workmanagement.worknarrowingdown;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

import java.util.List;

/**
 * AggregateRoot :職場別作業の絞込
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業絞込.職場別作業の絞込.職場別作業の絞込
 */
@Getter
public class NarrowingDownWorkByWorkplace extends AggregateRoot {
    // 	職場ID
    private final String workPlaceId;
    // 作業枠NO
    private final TaskFrameNo taskFrameNo;
    // 作業一覧
    private List<TaskCode> taskCodeList;
    // 	[C-1] 絞込を作成する

    public NarrowingDownWorkByWorkplace(Require require, String workPlaceId, TaskFrameNo taskFrameNo, List<TaskCode> taskCodeList) {
        if (require.checkExistenceWorkMaster(taskFrameNo, taskCodeList)) {

        }
        this.workPlaceId = workPlaceId;
        this.taskCodeList = taskCodeList;
        this.taskFrameNo = taskFrameNo;

    }

    public void changeCodeList(Require require, List<TaskCode> taskCodeList){
        if(require.checkExistenceWorkMaster(this.taskFrameNo,taskCodeList)){
            this.taskCodeList = taskCodeList;
        }
    }

    public interface Require {
        //	[1] 変更する
        boolean checkExistenceWorkMaster(TaskFrameNo taskFrameNo, List<TaskCode> childWorkList);
    }

}
