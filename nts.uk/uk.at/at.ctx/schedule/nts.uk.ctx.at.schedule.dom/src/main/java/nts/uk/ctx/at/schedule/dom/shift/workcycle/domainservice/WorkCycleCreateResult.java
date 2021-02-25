package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.*;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * 勤務サイクルの登録結果
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkCycleCreateResult {

    //	エラーあるか
    private boolean hasError;

    // 	エラー状態リスト
    private List<ErrorStatusWorkInfo> errorStatusList;

    // 	atomTask
    private Optional<AtomTask> atomTask;

    //	[C-1] 作る
    public WorkCycleCreateResult(Optional<AtomTask> atomTask) {
        this.hasError = false;
        this.errorStatusList = Collections.emptyList();
        this.atomTask = atomTask;
    }

    // 	[C-2] エラーありで作る
    public WorkCycleCreateResult(List<ErrorStatusWorkInfo> errorList) {
        this.hasError = true;
        this.errorStatusList = errorList;
        this.atomTask = Optional.empty();
    }
}
