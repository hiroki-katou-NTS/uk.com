package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 36協定申請理由
 * @author quang.nh1
 */
@StringMaxLength(400)
public class ReasonsForAgreement extends StringPrimitiveValue<ReasonsForAgreement> {

    private static final long serialVersionUID = 1L;

    public ReasonsForAgreement(String rawValue) {
        super(rawValue);
    }
}
