package nts.uk.ctx.at.record.infra.entity.workrecord.goout;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author hoangdd
 *
 */
@StaticMetamodel(KrcstOutManage.class)
public class KrcstOutManage_ {
	public static volatile SingularAttribute<KrcstOutManage, String> cid;
    public static volatile SingularAttribute<KrcstOutManage, BigDecimal> initValueReasonGoOut;
    public static volatile SingularAttribute<KrcstOutManage, BigDecimal> maxUsage;
}

