/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK;

@Generated(value="EclipseLink-2.6.4.v20160829-rNA", date="2017-11-30T14:05:37")
@StaticMetamodel(KshmtPredTimeSet.class)
public class KshmtPredTimeSet_ { 

    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> workAddMorning;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> startDateClock;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> afternoonStartTime;
    public static volatile SingularAttribute<KshmtPredTimeSet, KshmtPredTimeSetPK> kshmtPredTimeSetPK;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> rangeTimeDay;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predAfternoon;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> morningEndTime;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> workAddOneDay;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> workAddAfternoon;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predMorning;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> nightShiftAtr;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predOneDay;
    public static volatile SingularAttribute<KshmtPredTimeSet, Integer> predetermineAtr;

}