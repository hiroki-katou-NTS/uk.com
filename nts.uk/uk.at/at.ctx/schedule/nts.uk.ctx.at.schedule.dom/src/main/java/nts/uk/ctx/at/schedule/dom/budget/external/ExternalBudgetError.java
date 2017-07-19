package nts.uk.ctx.at.schedule.dom.budget.external;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ExternalBudgetError.
 * 外部予算実績項目受入エラー
 */
@Getter
public class ExternalBudgetError extends AggregateRoot {
    
    /** The error content. */
    // エラー内容
    private ExtBudgetErrorContent errorContent;
    
    /** The number column. */
    // 列番号
    private int numberColumn;
    
    /** The actual value. */
    // 受入値
    private ExtBudgetActualValue actualValue;
    
    /** The accepted date. */
    // 受入値年月日
    private ExtBudgetAccDate acceptedDate;
    
    /** The workplace code. */
    // 受入値職場コート
    private ExtBudgetWorkplaceCode workplaceCode;
    
    /** The excution id. */
    // 実行ID
    private String excutionId;
    
    /** The number line. */
    // 行番号
    private int numberLine;
    
    /**
     * Instantiates a new external budget error.
     *
     * @param memento the memento
     */
    public ExternalBudgetError(ExternalBudgetErrorGetMemento memento) {
        super();
        this.errorContent = memento.getErrorContent();
        this.numberColumn = memento.getNumberColumn();
        this.actualValue = memento.getActualValue();
        this.acceptedDate = memento.getAcceptedDate();
        this.workplaceCode = memento.getWorkPlaceCode();
        this.excutionId = memento.getExcutionId();
        this.numberLine = memento.getNumberLine();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExternalBudgetErrorSetMemento memento) {
        memento.setErrorContent(this.errorContent);
        memento.setNumberColumn(this.numberColumn);
        memento.setActualValue(this.actualValue);
        memento.setAcceptedDate(this.acceptedDate);
        memento.setWorkPlaceCode(this.workplaceCode);
        memento.setExcutionId(this.excutionId);
        memento.setNumberLine(this.numberLine);
    }
}
