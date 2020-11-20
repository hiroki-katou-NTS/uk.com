package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 36協定承認コメント
 * @author quang.nh1
 */
@StringMaxLength(400)
public class AgreementApprovalComments extends StringPrimitiveValue<AgreementApprovalComments> {

    private static final long serialVersionUID = 1L;

    public AgreementApprovalComments(String rawValue) {
        super(rawValue);
    }
}
