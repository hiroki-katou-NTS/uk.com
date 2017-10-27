/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Class ExternalBudgetLog.
 * 外部予算実績項目受入ログ
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = {"executionId"})
public class ExternalBudgetLog extends AggregateRoot {
    
    /** The ext budget file name. */
    // 外部予算実績受入ファイル名
    private ExtBudgetFileName extBudgetFileName;
    
    /** The ext budget code. */
    //外部予算実績項目コード
    private ExternalBudgetCd extBudgetCode;
    
    /** The number fail. */
    // 失敗件数
    private int numberFail;
    
    /** The completion state. */
    // 完了状態
    private CompletionState completionState;
    
    /** The execution id. */
    // 実行ID
    private String executionId;
    
    /** The execute time. */
    // 実行日時
    private ExecutionTime executeTime;
    
    /** The number success. */
    // 成功件数
    private int numberSuccess;
    
    /** The employee id. */
    // 社員ID
    private String employeeId;
    
    /**
     * Instantiates a new external budget log.
     *
     * @param memento the memento
     */
    public ExternalBudgetLog(ExternalBudgetLogGetMemento memento) {
        super();
        this.extBudgetFileName = memento.getExternalBudgetFileName();
        this.extBudgetCode = memento.getExternalBudgetCode();
        this.numberFail = memento.getNumberFail();
        this.completionState = memento.getCompletionState();
        this.executionId = memento.getExecutionId();
        this.executeTime = memento.getExecuteTime();
        this.numberSuccess = memento.getNumberSuccess();
        this.employeeId = memento.getEmployeeId();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExternalBudgetLogSetMemento memento) {
        memento.setExternalBudgetFileName(this.extBudgetFileName);
        memento.setExternalBudgetCode(this.extBudgetCode);
        memento.setNumberFail(this.numberFail);
        memento.setCompletionState(this.completionState);
        memento.setExecutionId(this.executionId);
        memento.setExecuteTime(this.executeTime);
        memento.setNumberSuccess(this.numberSuccess);
        memento.setEmployeeId(this.employeeId);
    }
}
