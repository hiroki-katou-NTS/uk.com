package nts.uk.ctx.at.shared.infra.entity.scherec.totaltimes;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KshstTotalSubjectsPK_.
 */
@StaticMetamodel(KshstTotalSubjectsPK.class)
public class KshstTotalSubjectsPK_ {

	 /** The cid. */
    public static volatile SingularAttribute<KshstTotalSubjectsPK, String> cid;
    
    /** The totalTimesNo. */
    public static volatile SingularAttribute<KshstTotalSubjectsPK, Integer> totalTimesNo;
    
    /** The work type atr. */
    public static volatile SingularAttribute<KshstTotalSubjectsPK, Integer> workTypeAtr;
    
    /** The work type atr. */
    public static volatile SingularAttribute<KshstTotalSubjectsPK, String> workTypeCd;
	
}
