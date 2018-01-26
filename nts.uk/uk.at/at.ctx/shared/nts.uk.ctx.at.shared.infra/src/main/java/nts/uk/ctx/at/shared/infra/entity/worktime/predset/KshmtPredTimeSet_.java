package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-23T16:56:41")
@StaticMetamodel(KshmtPredTimeSet.class)
public class KshmtPredTimeSet_ { 

    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> workAddMorning;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> startDateClock;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> afternoonStartTime;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> isIncludeOt;
    public static volatile SingularAttribute<KshmtPredTimeSet, KshmtPredTimeSetPK> kshmtPredTimeSetPK;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> rangeTimeDay;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predAfternoon;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> exclusVer;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> morningEndTime;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> workAddOneDay;
    public static volatile ListAttribute<KshmtPredTimeSet, KshmtWorkTimeSheetSet> kshmtWorkTimeSheetSets;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> workAddAfternoon;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predMorning;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> nightShiftAtr;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predOneDay;

}