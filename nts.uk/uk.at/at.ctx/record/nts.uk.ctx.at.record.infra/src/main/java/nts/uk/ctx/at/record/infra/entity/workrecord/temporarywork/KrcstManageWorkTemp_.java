/**
 * 
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.temporarywork;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KrcstManageWorkTemp_.
 *
 * @author hoangdd
 */
@StaticMetamodel(KrcstManageWorkTemp.class)
public class KrcstManageWorkTemp_ {
	
	/** The cid. */
	public static volatile SingularAttribute<KrcstManageWorkTemp, String> cid;
    
    /** The max usage. */
    public static volatile SingularAttribute<KrcstManageWorkTemp, BigDecimal> maxUsage;
    
    /** The time treat temp same. */
    public static volatile SingularAttribute<KrcstManageWorkTemp, BigDecimal> timeTreatTempSame;
}

