package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import java.sql.Date;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ExecutionTime.
 * 実行日時
 */
@Getter
public class ExecutionTime extends DomainObject {
    
    /** The start date. */
    //開始日時
    private Date startDate;
    
    /** The end date. */
    // 終了日時
    private Date endDate;
}
