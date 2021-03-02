package nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.ExternalCooperationInfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskDisplayInfo;
import nts.uk.ctx.at.shared.dom.workmanagement.domainservice.CheckExistenceMasterDomainService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * AggregateRoot :作業
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.作業
 *
 * @author chinh.hm
 */
@Getter
public class Work extends AggregateRoot {
    // コード
    private final TaskCode code;

    // 作業枠NO
    private final TaskFrameNo taskFrameNo;

    // 外部連携情報
    private ExternalCooperationInfo cooperationInfo;

    // 子作業一覧
    private List<TaskCode> childWorkList;

    // 有効期限
    private DatePeriod expirationDate;

    // 表示情報 : 作業表示情報
    private TaskDisplayInfo displayInfo;

    public Work(Require require,
                TaskFrameNo taskFrameNo,
                TaskCode code,
                DatePeriod expirationDate,
                ExternalCooperationInfo cooperationInfo,
                TaskDisplayInfo displayInfo,
                List<TaskCode> childWorkList) {
        //	[inv-2]	case 「@作業枠NO」== 5 の場合、「@子作業一覧」はisEmpty
        if (taskFrameNo.v() == 5) {
            childWorkList = Collections.emptyList();
        }
        CheckExistenceMasterDomainService.checkExistenceWorkMaster(require, new TaskFrameNo(taskFrameNo.v() + 1), childWorkList);
        // 	[inv-1]	case 「@作業枠NO」 <> 1 の場合、「@表示情報．作業色」はisEmpty
        if (taskFrameNo.v() != 1) {
            displayInfo = new TaskDisplayInfo(
                    displayInfo.getTaskName(),
                    displayInfo.getTaskAbName(),
                    Optional.empty(),
                    displayInfo.getTaskNote());
        }
        this.code = code;
        this.taskFrameNo = taskFrameNo;
        this.expirationDate = expirationDate;
        this.cooperationInfo = cooperationInfo;
        this.displayInfo = displayInfo;
        this.childWorkList = childWorkList;

    }

    // 	[1] 有効期限内か
    boolean checkExpirationDate(GeneralDate targetDate) {
        return this.expirationDate.contains(targetDate);
    }

    // 	[2] 子作業設定を変更する
    void changeChildWorkList(Require require, List<TaskCode> childWorkList) {
        if (this.taskFrameNo.v() == 5) {
            throw new BusinessException("Msg_2066");
        }
        CheckExistenceMasterDomainService.checkExistenceWorkMaster(require, new TaskFrameNo(taskFrameNo.v() + 1), childWorkList);
        this.childWorkList = childWorkList;

    }

    // 	[3] 子作業設定を削除する
    void deleteChildWorkList() {
        this.childWorkList = Collections.emptyList();
    }

    public interface Require extends CheckExistenceMasterDomainService.Require {
    }
}
