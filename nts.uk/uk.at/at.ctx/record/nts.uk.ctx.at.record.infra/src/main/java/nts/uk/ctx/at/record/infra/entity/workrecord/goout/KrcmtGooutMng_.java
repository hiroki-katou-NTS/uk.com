package nts.uk.ctx.at.record.infra.entity.workrecord.goout;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author hoangdd
 *
 */
@StaticMetamodel(KrcmtGooutMng.class)
public class KrcmtGooutMng_ {
	public static volatile SingularAttribute<KrcmtGooutMng, String> cid;
    public static volatile SingularAttribute<KrcmtGooutMng, BigDecimal> initValueReasonGoOut;
    public static volatile SingularAttribute<KrcmtGooutMng, BigDecimal> maxUsage;
}

