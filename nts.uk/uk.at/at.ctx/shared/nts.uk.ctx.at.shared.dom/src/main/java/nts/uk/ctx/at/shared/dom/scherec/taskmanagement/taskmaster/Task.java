package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CheckExistenceMasterDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 作業
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業
 *
 * @author lan_lt
 */
@Getter
@AllArgsConstructor
public class Task implements DomainAggregate {
    // コード
    private final TaskCode code;

    // 作業枠NO
    private final TaskFrameNo taskFrameNo;

    // 外部連携情報
    private ExternalCooperationInfo cooperationInfo;

    // 子作業一覧
    private List<TaskCode> childTaskList;

    // 有効期限
    private DatePeriod expirationDate;

    // 表示情報 : 作業表示情報
    private TaskDisplayInfo displayInfo;

    public static Task create(Require require,
                              TaskFrameNo taskFrameNo,
                              TaskCode code,
                              DatePeriod expirationDate,
                              ExternalCooperationInfo cooperationInfo,
                              TaskDisplayInfo displayInfo,
                              List<TaskCode> childTaskList

    ) {
        //	[inv-2]	case 「@作業枠NO」== 5 の場合、「@子作業一覧」はisEmpty
        if (taskFrameNo.v() == 5) {
            childTaskList = Collections.emptyList();
        } else {
            CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, new TaskFrameNo(taskFrameNo.v() + 1), childTaskList);

        }
        // 	[inv-1]	case 「@作業枠NO」 <> 1 の場合、「@表示情報．作業色」はisEmpty
        if (taskFrameNo.v() != 1) {
            displayInfo = new TaskDisplayInfo(
                    displayInfo.getTaskName(),
                    displayInfo.getTaskAbName(),
                    Optional.empty(),
                    displayInfo.getTaskNote());
        }
        return new Task(code, taskFrameNo, cooperationInfo, childTaskList, expirationDate, displayInfo);

    }

    // 	[1] 有効期限内か
    public boolean checkExpirationDate(GeneralDate targetDate) {
        return this.expirationDate.contains(targetDate);
    }

    // 	[2] 子作業設定を変更する
    public void changeChildTaskList(Require require, List<TaskCode> childTaskList) {
        if (this.taskFrameNo.v() == 5) {
            throw new BusinessException("Msg_2066");
        }
        CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, new TaskFrameNo(taskFrameNo.v() + 1), childTaskList);
        this.childTaskList = childTaskList;

    }

    // 	[3] 子作業設定を削除する
    public void deleteChildTaskList() {
        this.childTaskList = Collections.emptyList();
    }

    public interface Require extends CheckExistenceMasterDomainService.Require {
    }
}
