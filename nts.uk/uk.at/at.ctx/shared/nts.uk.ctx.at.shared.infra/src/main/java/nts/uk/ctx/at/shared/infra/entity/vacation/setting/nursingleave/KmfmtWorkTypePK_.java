/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * The Class KmfmtWorkTypePK_.
 */
@StaticMetamodel(KmfmtWorkTypePK.class)
public class KmfmtWorkTypePK_ {
    
    /** The cid. */
    public static volatile SingularAttribute<KmfmtWorkTypePK, String> cid;
    
    /** The nursingCtr. */
    public static volatile SingularAttribute<KmfmtWorkTypePK, Integer> nursingCtr;
    
    /** The order number. */
    public static volatile SingularAttribute<KmfmtWorkTypePK, Integer> orderNumber;
}
