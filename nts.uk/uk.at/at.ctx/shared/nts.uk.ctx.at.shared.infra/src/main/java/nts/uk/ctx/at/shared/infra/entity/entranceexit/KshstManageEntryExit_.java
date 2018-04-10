package nts.uk.ctx.at.shared.infra.entity.entranceexit;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author hoangdd
 *
 */
@StaticMetamodel(KshstManageEntryExit.class)
public class KshstManageEntryExit_ {
	public static volatile SingularAttribute<KshstManageEntryExit, String> cid;
    public static volatile SingularAttribute<KshstManageEntryExit, BigDecimal> useCls;
}

