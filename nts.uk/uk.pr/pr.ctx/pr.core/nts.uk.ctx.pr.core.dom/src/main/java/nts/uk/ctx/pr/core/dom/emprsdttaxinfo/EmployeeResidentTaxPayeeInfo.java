package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 社員住民税納付先情報
 */
@Getter
public class EmployeeResidentTaxPayeeInfo extends AggregateRoot {

    /**
     * 社員ID
     */
    private String sid;

    /**
     * 期間
     */
    private GenericHistYMPeriod historyItem;

    public EmployeeResidentTaxPayeeInfo(String sid, String histId, int startDate, int endDate) {
        this.sid = sid;
        this.historyItem = new GenericHistYMPeriod(histId, startDate, endDate);
    }

}
