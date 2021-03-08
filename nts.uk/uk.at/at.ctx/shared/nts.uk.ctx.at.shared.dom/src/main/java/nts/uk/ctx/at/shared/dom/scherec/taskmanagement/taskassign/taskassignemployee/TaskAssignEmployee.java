package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

/**
 * 社員別作業の絞込
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).作業管理.作業絞込.社員別作業の絞込
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAssignEmployee extends AggregateRoot {
    // 社員ID
    private String employeeId;

    // 作業枠NO
    private TaskFrameNo taskFrameNo;

    // 作業コード
    private TaskCode taskCode;

    public TaskAssignEmployee(String employeeId, int taskFrameNo, String taskCode) {
        this.employeeId = employeeId;
        this.taskFrameNo = new TaskFrameNo(taskFrameNo);
        this.taskCode = new TaskCode(taskCode);
    }
}
