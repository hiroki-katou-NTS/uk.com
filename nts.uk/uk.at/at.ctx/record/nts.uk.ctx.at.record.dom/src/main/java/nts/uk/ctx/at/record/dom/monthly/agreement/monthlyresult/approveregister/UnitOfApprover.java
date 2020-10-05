package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;

/**
 * 承認者（36協定）の利用単位
 *
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class UnitOfApprover extends AggregateRoot {

    /**
     * 会社ID
     */
    private final String companyID;

    /**
     * 職場を利用する
     */
    private DoWork useWorkplace;
}
