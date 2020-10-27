package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTimePK;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTs;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-23T16:56:41")
@StaticMetamodel(KshmtWtComPredTime.class)
public class KshmtWtComPredTime_ { 

    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> workAddMorning;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> startDateClock;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> afternoonStartTime;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> isIncludeOt;
    public static volatile SingularAttribute<KshmtWtComPredTime, KshmtWtComPredTimePK> kshmtWtComPredTimePK;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> rangeTimeDay;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> predAfternoon;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> exclusVer;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> morningEndTime;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> workAddOneDay;
    public static volatile ListAttribute<KshmtWtComPredTime, KshmtWtComPredTs> kshmtWtComPredTss;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> workAddAfternoon;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> predMorning;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> nightShiftAtr;
    public static volatile SingularAttribute<KshmtWtComPredTime, Integer> predOneDay;

}