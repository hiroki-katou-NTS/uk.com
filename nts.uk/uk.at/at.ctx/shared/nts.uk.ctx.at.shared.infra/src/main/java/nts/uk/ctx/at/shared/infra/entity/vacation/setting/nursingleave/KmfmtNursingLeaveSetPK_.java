/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KmfmtNursingLeaveSetPK.class)
public class KmfmtNursingLeaveSetPK_ {
    
    /** The cid. */
    public static volatile SingularAttribute<KmfmtNursingLeaveSetPK, String> cid;
    
    /** The nursingCtr. */
    public static volatile SingularAttribute<KmfmtNursingLeaveSetPK, Integer> nursingCtr;
}

