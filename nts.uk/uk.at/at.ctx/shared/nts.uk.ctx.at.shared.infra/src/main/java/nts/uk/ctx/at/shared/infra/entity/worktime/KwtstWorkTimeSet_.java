package nts.uk.ctx.at.shared.infra.entity.worktime;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.worktime.KwtstWorkTimeSetPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-23T17:54:14")
@StaticMetamodel(KwtstWorkTimeSet.class)
public class KwtstWorkTimeSet_ { 

    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> startDateClock;
    public static volatile SingularAttribute<KwtstWorkTimeSet, String> additionSetId;
    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> afternoonStartTime;
    public static volatile SingularAttribute<KwtstWorkTimeSet, KwtstWorkTimeSetPK> kwtstWorkTimeSetPK;
    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> rangeTimeDay;
    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> nightShiftAtr;
    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> exclusVer;
    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> predetermineAtr;
    public static volatile SingularAttribute<KwtstWorkTimeSet, Integer> morningEndTime;

}