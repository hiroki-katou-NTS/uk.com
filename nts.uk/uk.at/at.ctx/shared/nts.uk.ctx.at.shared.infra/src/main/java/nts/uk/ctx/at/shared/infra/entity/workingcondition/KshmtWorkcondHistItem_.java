package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondCtg;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondScheMeth;

@Generated(value = "EclipseLink-2.5.2.v20140319-rNA", date = "2017-12-11T13:52:52")
@StaticMetamodel(KshmtWorkcondHistItem.class)
public class KshmtWorkcondHistItem_ {

	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> vacationAddTimeAtr;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, KshmtWorkcondScheMeth> kshmtWorkcondScheMeth;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> exclusVer;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, String> sid;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> hdAddTimeMorning;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> hourlyPayAtr;
	public static volatile ListAttribute<KshmtWorkcondHistItem, KshmtWorkcondWeek> kshmtWorkcondWeeks;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> autoIntervalSetAtr;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> autoStampSetAtr;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, String> historyId;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> contractTime;
	public static volatile ListAttribute<KshmtWorkcondHistItem, KshmtWorkcondCtg> kshmtWorkcondCtgs;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> hdAddTimeOneDay;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> hdAddTimeAfternoon;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> laborSys;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, Integer> scheManagementAtr;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, KshmtWorkcondHist> kshmtWorkcondHist;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, String> timeApply;
	public static volatile SingularAttribute<KshmtWorkcondHistItem, String> monthlyPattern;
}