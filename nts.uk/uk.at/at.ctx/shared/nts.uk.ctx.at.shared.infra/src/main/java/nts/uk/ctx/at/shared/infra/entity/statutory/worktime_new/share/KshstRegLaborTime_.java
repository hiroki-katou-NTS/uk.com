package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KshstRegLaborTime.class)
public class KshstRegLaborTime_ { 

    public static volatile SingularAttribute<KshstRegLaborTime, Integer> dailyTime;
    public static volatile SingularAttribute<KshstRegLaborTime, Short> weekStr;
    public static volatile SingularAttribute<KshstRegLaborTime, Integer> exclusVer;
    public static volatile SingularAttribute<KshstRegLaborTime, Integer> weeklyTime;

}