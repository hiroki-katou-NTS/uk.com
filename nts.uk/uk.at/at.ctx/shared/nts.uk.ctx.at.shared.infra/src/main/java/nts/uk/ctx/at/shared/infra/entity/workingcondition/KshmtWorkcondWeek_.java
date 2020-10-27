package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekTs;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-11T13:52:53")
@StaticMetamodel(KshmtWorkcondWeek.class)
public class KshmtWorkcondWeek_ { 

    public static volatile SingularAttribute<KshmtWorkcondWeek, KshmtWorkcondWeekPK> kshmtWorkcondWeekPK;
    public static volatile SingularAttribute<KshmtWorkcondWeek, String> workTypeCode;
    public static volatile SingularAttribute<KshmtWorkcondWeek, String> workTimeCode;
    public static volatile ListAttribute<KshmtWorkcondWeek, KshmtWorkcondWeekTs> kshmtWorkcondWeekTss;
    public static volatile SingularAttribute<KshmtWorkcondWeek, Integer> exclusVer;

}