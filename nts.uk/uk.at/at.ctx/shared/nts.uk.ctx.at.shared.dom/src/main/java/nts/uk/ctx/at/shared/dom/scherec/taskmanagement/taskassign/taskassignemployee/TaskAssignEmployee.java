package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

import java.util.Arrays;

/**
 * 社員別作業の絞込
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).作業管理.作業絞込.社員別作業の絞込
 */
@Getter
@AllArgsConstructor
public class TaskAssignEmployee extends AggregateRoot {
    // 社員ID
    private final String employeeId;

    // 作業枠NO
    private final TaskFrameNo taskFrameNo;

    // 作業コード
    private TaskCode taskCode;

    public static TaskAssignEmployee create(Require require, String employeeId, int taskFrameNo, String taskCode) {
        TaskFrameNo frameNo = new TaskFrameNo(taskFrameNo);
        TaskCode code = new TaskCode(taskCode);
        CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, frameNo, Arrays.asList(code));
        return new TaskAssignEmployee(employeeId, frameNo, code);
    }

    public interface Require extends CheckExistenceMasterDomainService.Require {

    }
}
