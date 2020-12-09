package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.time.GeneralDate;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-11T13:52:52")
@StaticMetamodel(KshmtWorkcondHist.class)
public class KshmtWorkingCond_ { 

    public static volatile SingularAttribute<KshmtWorkcondHist, KshmtWorkingCondPK> kshmtWorkingCondPK;
    public static volatile SingularAttribute<KshmtWorkcondHist, GeneralDate> strD;
    public static volatile SingularAttribute<KshmtWorkcondHist, GeneralDate> endD;
    public static volatile SingularAttribute<KshmtWorkcondHist, KshmtWorkcondHistItem> kshmtWorkingCondItem;
    public static volatile SingularAttribute<KshmtWorkcondHist, Integer> exclusVer;
    public static volatile SingularAttribute<KshmtWorkcondHist, String> cid;

}