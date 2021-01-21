package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author HieuLt
 *
 */
public class PersonInforDisplayControlHelper {

	public static PersonInforDisplayControl Dummy = new PersonInforDisplayControl(
			EnumAdaptor.valueOf(0, ConditionATRWorkSchedule.class),
			EnumAdaptor.valueOf(1, NotUseAtr.class));
}
