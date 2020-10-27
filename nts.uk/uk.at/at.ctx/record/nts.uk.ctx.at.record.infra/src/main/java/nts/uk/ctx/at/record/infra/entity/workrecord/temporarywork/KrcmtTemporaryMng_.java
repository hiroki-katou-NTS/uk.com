/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcmtTemporaryMng_.
 *
 * @author hoangdd
 */
@StaticMetamodel(KrcmtTemporaryMng.class)
public class KrcmtTemporaryMng_ {
	
	/** The cid. */
	public static volatile SingularAttribute<KrcmtTemporaryMng, String> cid;
    
    /** The max usage. */
    public static volatile SingularAttribute<KrcmtTemporaryMng, BigDecimal> maxUsage;
    
    /** The time treat temp same. */
    public static volatile SingularAttribute<KrcmtTemporaryMng, BigDecimal> timeTreatTempSame;
}

