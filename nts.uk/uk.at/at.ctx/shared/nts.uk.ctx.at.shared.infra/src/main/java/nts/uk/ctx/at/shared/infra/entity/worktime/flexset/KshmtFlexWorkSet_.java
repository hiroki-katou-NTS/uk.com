package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.uk.ctx.at.shared.infra.entity.worktime.flexset.KshmtFlexWorkSetPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-23T17:54:16")
@StaticMetamodel(KshmtFlexWorkSet.class)
public class KshmtFlexWorkSet_ { 

    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> deductFromWorkTime;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> coreTimeStr;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> leastWorkTime;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> especialCalc;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> useHalfdayShift;
    public static volatile SingularAttribute<KshmtFlexWorkSet, KshmtFlexWorkSetPK> kshmtFlexWorkSetPK;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> coretimeUseAtr;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> exclusVer;
    public static volatile SingularAttribute<KshmtFlexWorkSet, Integer> coreTimeEnd;

}