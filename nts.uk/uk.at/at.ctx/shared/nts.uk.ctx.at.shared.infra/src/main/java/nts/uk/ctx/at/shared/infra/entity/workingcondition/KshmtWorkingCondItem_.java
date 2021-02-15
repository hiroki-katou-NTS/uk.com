package nts.uk.ctx.at.shared.infra.entity.workingcondition;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPersonalDayOfWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtScheduleMethod;

@Generated(value = "EclipseLink-2.5.2.v20140319-rNA", date = "2017-12-11T13:52:52")
@StaticMetamodel(KshmtWorkingCondItem.class)
public class KshmtWorkingCondItem_ {

	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> vacationAddTimeAtr;
	public static volatile SingularAttribute<KshmtWorkingCondItem, KshmtScheduleMethod> kshmtScheduleMethod;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> exclusVer;
	public static volatile SingularAttribute<KshmtWorkingCondItem, String> sid;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> hdAddTimeMorning;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> hourlyPayAtr;
	public static volatile ListAttribute<KshmtWorkingCondItem, KshmtPersonalDayOfWeek> kshmtPersonalDayOfWeeks;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> autoIntervalSetAtr;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> autoStampSetAtr;
	public static volatile SingularAttribute<KshmtWorkingCondItem, String> historyId;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> contractTime;
	public static volatile ListAttribute<KshmtWorkingCondItem, KshmtPerWorkCat> kshmtPerWorkCats;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> hdAddTimeOneDay;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> hdAddTimeAfternoon;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> laborSys;
	public static volatile SingularAttribute<KshmtWorkingCondItem, Integer> scheManagementAtr;
	public static volatile SingularAttribute<KshmtWorkingCondItem, KshmtWorkingCond> kshmtWorkingCond;
	public static volatile SingularAttribute<KshmtWorkingCondItem, String> timeApply;
	public static volatile SingularAttribute<KshmtWorkingCondItem, String> monthlyPattern;
}