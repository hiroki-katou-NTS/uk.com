package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(KshstTransLabTime.class)
public class KshstTransLabTime_ {
	
	public static volatile SingularAttribute<KshstTransLabTime, Integer> dailyTime;
	public static volatile SingularAttribute<KshstTransLabTime, Integer> weekStr;
	public static volatile SingularAttribute<KshstTransLabTime, Integer> exclusVer;
	public static volatile SingularAttribute<KshstTransLabTime, Integer> weeklyTime;

}