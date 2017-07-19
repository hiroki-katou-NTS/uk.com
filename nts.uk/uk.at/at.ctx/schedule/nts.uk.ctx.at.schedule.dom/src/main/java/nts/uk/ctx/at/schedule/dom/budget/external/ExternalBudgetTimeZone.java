package nts.uk.ctx.at.schedule.dom.budget.external;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class ExternalBudgetTimeZone.
 *
 * @param <T> the generic type
 * 外外部予算実績時間帯
 */
@Getter
public class ExternalBudgetTimeZone<T> extends AggregateRoot {
    
    /** The company id. */
    // 会社ID
    private String companyId;
    
    /** The actual value. */
    // 外部予算実績時間帯値
    private List<ExternalBudgetTimeZoneVal<T>> actualValues;
    
    /** The ext budget code. */
    // 外部予算実績項目コード
    private ExternalBudgetCd extBudgetCode;
    
    /** The process date. */
    // 年月日
    private Date processDate;
    
    /** The workplace id. */
    // 職場ID
    private String workplaceId;
    
    /**
     * Instantiates a new external budget time table.
     *
     * @param memento the memento
     */
    public ExternalBudgetTimeZone(ExtBudgTimeZoneGetMemento<T> memento) {
        super();
        this.companyId = memento.getCompanyId();
        this.actualValues = memento.getActualValues();
        this.extBudgetCode = memento.getExtBudgetCode();
        this.processDate = memento.getProcessDate();
        this.workplaceId = memento.getWorkplaceId();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExtBudgTimeZoneSetMemento<T> memento) {
        memento.setCompanyId(this.companyId);
        memento.setActualValues(this.actualValues);
        memento.setExtBudgetCode(this.extBudgetCode);
        memento.setProcessDate(this.processDate);
        memento.setWorkplaceId(this.workplaceId);
    }
}
