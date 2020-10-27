package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshmtTotalSubjectsPK_.
 */
@StaticMetamodel(KshmtTotalSubjectsPK.class)
public class KshmtTotalSubjectsPK_ {

	 /** The cid. */
    public static volatile SingularAttribute<KshmtTotalSubjectsPK, String> cid;
    
    /** The totalTimesNo. */
    public static volatile SingularAttribute<KshmtTotalSubjectsPK, Integer> totalTimesNo;
    
    /** The work type atr. */
    public static volatile SingularAttribute<KshmtTotalSubjectsPK, Integer> workTypeAtr;
    
    /** The work type atr. */
    public static volatile SingularAttribute<KshmtTotalSubjectsPK, String> workTypeCd;
	
}
