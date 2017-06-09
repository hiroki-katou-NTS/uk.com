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
@StaticMetamodel(KmfmtNursingWorkTypePK.class)
public class KmfmtNursingWorkTypePK_ {
    
    /** The cid. */
    public static volatile SingularAttribute<KmfmtNursingWorkTypePK, String> cid;
    
    /** The nursingCtr. */
    public static volatile SingularAttribute<KmfmtNursingWorkTypePK, Integer> nursingCtr;
    
    /** The order number. */
    public static volatile SingularAttribute<KmfmtNursingWorkTypePK, Integer> orderNumber;
}
