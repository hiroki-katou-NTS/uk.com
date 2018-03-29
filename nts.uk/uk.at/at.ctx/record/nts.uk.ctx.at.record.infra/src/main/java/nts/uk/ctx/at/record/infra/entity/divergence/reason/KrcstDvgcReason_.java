package nts.uk.ctx.at.record.infra.entity.divergence.reason;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReasonPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-27T23:12:35")
@StaticMetamodel(KrcstDvgcReason.class)
public class KrcstDvgcReason_ { 

    public static volatile SingularAttribute<KrcstDvgcReason, String> reason;
    public static volatile SingularAttribute<KrcstDvgcReason, KrcstDvgcReasonPK> id;
    public static volatile SingularAttribute<KrcstDvgcReason, BigDecimal> reasonRequired;

}