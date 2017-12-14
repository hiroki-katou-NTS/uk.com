package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtDayofweekTimeZone;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeekPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-11T13:52:53")
@StaticMetamodel(KshmtPersonalDayOfWeek.class)
public class KshmtPersonalDayOfWeek_ { 

    public static volatile SingularAttribute<KshmtPersonalDayOfWeek, KshmtPersonalDayOfWeekPK> kshmtPersonalDayOfWeekPK;
    public static volatile SingularAttribute<KshmtPersonalDayOfWeek, String> workTypeCode;
    public static volatile SingularAttribute<KshmtPersonalDayOfWeek, String> workTimeCode;
    public static volatile ListAttribute<KshmtPersonalDayOfWeek, KshmtDayofweekTimeZone> kshmtDayofweekTimeZones;
    public static volatile SingularAttribute<KshmtPersonalDayOfWeek, Integer> exclusVer;

}