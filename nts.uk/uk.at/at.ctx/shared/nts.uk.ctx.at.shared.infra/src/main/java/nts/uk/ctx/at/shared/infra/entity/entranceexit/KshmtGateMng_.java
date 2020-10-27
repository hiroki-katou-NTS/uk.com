package nts.uk.ctx.at.shared.infra.entity.entranceexit;

import java.math.BigDecimal;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author hoangdd
 *
 */
@StaticMetamodel(KshmtGateMng.class)
public class KshmtGateMng_ {
	public static volatile SingularAttribute<KshmtGateMng, String> cid;
    public static volatile SingularAttribute<KshmtGateMng, BigDecimal> useCls;
}

