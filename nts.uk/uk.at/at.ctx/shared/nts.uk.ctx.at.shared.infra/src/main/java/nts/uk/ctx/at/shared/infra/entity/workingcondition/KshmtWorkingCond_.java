package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-11T13:52:52")
@StaticMetamodel(KshmtWorkingCond.class)
public class KshmtWorkingCond_ { 

    public static volatile SingularAttribute<KshmtWorkingCond, KshmtWorkingCondPK> kshmtWorkingCondPK;
    public static volatile SingularAttribute<KshmtWorkingCond, GeneralDate> strD;
    public static volatile SingularAttribute<KshmtWorkingCond, GeneralDate> endD;
    public static volatile SingularAttribute<KshmtWorkingCond, KshmtWorkingCondItem> kshmtWorkingCondItem;
    public static volatile SingularAttribute<KshmtWorkingCond, Integer> exclusVer;
    public static volatile SingularAttribute<KshmtWorkingCond, String> cid;

}