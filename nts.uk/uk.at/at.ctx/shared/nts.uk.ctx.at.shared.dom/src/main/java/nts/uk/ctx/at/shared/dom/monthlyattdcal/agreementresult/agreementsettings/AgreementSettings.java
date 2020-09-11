package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.agreementsettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.Deadline;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.StartingMonthType;

/**
 * ３６協定運用設定
 * @author quang.nh1
 *
 */
@Getter
@AllArgsConstructor
public class AgreementSettings extends AggregateRoot {

    /** The company id. */
    final String companyId;

    /** 起算月 **/
    private StartingMonthType startingMonth;

    /** 締め日 **/
    private Deadline deadline;

    /** 特別条項申請を使用する **/
    private GeneralDate useSpecialClause;

    /** 年間の特別条項申請を使用する **/
    private GeneralDate UseAnnualSpecialClause;

}
