package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.uk.shr.com.enumcommon.NotUseAtr;

public class DisplayControlPersonalConditionHelper {
	
	public static DisplayControlPersonalCondition defaultData() {
		
		List<PersonInforDisplayControl> controlList = Arrays.asList(
				new PersonInforDisplayControl(ConditionATRWorkSchedule.INSURANCE_STATUS, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.NOT_USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.RANK, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.QUALIFICATION, NotUseAtr.NOT_USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.LICENSE_ATR, NotUseAtr.USE) );
		
		return DisplayControlPersonalCondition.get(
				"companyId", 
				controlList, 
				Optional.empty() );
	}
	
	public static List<PersonInforDisplayControl> createControlListWithQualificationAtr (NotUseAtr qualificationNotUseAtr) {
		
		return Arrays.asList(
				new PersonInforDisplayControl(ConditionATRWorkSchedule.INSURANCE_STATUS, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.TEAM, NotUseAtr.NOT_USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.RANK, NotUseAtr.USE),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.QUALIFICATION, qualificationNotUseAtr ),
				new PersonInforDisplayControl(ConditionATRWorkSchedule.LICENSE_ATR, NotUseAtr.USE) );
	}

}
