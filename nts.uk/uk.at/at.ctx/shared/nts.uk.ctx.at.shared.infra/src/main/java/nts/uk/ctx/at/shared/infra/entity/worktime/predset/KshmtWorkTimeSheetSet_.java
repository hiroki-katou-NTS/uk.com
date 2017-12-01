/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSetPK;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-11-30T14:05:37")
@StaticMetamodel(KshmtWorkTimeSheetSet.class)
public class KshmtWorkTimeSheetSet_ { 

    public static volatile SingularAttribute<KshmtWorkTimeSheetSet, KshmtWorkTimeSheetSetPK> kshmtWorkTimeSheetSetPK;
    public static volatile SingularAttribute<KshmtWorkTimeSheetSet, Short> useAtr;
    public static volatile SingularAttribute<KshmtWorkTimeSheetSet, Short> startTime;
    public static volatile SingularAttribute<KshmtWorkTimeSheetSet, Short> endTime;

}