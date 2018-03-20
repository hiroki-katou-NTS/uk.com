package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KshstComRegLaborTime.class)
public class KshstComRegLaborTime_ { 

    public static volatile SingularAttribute<KshstComRegLaborTime, Integer> dailyTime;
    public static volatile SingularAttribute<KshstComRegLaborTime, Short> weekStr;
    public static volatile SingularAttribute<KshstComRegLaborTime, Integer> exclusVer;
    public static volatile SingularAttribute<KshstComRegLaborTime, String> cid;
    public static volatile SingularAttribute<KshstComRegLaborTime, Integer> weeklyTime;

}