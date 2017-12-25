/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorkTimeSetPK;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-11-30T14:05:37")
@StaticMetamodel(KshmtWorkTimeSet.class)
public class KshmtWorkTimeSet_ { 

    public static volatile SingularAttribute<KshmtWorkTimeSet, String> symbol;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> note;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Short> worktimeSetMethod;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> color;
    public static volatile SingularAttribute<KshmtWorkTimeSet, KshmtWorkTimeSetPK> kshmtWorkTimeSetPK;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> name;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> memo;
    public static volatile SingularAttribute<KshmtWorkTimeSet, String> abName;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Short> dailyWorkAtr;
    public static volatile SingularAttribute<KshmtWorkTimeSet, Short> abolitionAtr;

}