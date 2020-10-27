/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWtPK;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-11-30T14:05:37")
@StaticMetamodel(KshmtWt.class)
public class KshmtWt_ { 

    public static volatile SingularAttribute<KshmtWt, String> symbol;
    public static volatile SingularAttribute<KshmtWt, String> note;
    public static volatile SingularAttribute<KshmtWt, Short> worktimeSetMethod;
    public static volatile SingularAttribute<KshmtWt, String> color;
    public static volatile SingularAttribute<KshmtWt, KshmtWtPK> kshmtWtPK;
    public static volatile SingularAttribute<KshmtWt, String> name;
    public static volatile SingularAttribute<KshmtWt, String> memo;
    public static volatile SingularAttribute<KshmtWt, String> abName;
    public static volatile SingularAttribute<KshmtWt, Short> dailyWorkAtr;
    public static volatile SingularAttribute<KshmtWt, Short> abolitionAtr;

}