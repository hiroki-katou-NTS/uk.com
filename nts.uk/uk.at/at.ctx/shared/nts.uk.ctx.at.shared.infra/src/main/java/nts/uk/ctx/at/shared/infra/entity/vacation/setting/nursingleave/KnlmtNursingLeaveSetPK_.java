/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KnlmtNursingLeaveSetPK.class)
public class KnlmtNursingLeaveSetPK_ {
    
    /** The cid. */
    public static volatile SingularAttribute<KnlmtNursingLeaveSetPK, String> cid;
    
    /** The nursingCtr. */
    public static volatile SingularAttribute<KnlmtNursingLeaveSetPK, Integer> nursingCtr;
}

