package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.LicenseClassification;

public class PersonalConditionHelper {
	
	public static PersonalCondition getData(){
		PersonalCondition data = new PersonalCondition(
				"empID-0000000000000001",
				Optional.ofNullable("TEAMNAME-00000000001"),
				Optional.ofNullable("RANK_SYMBOL-00000001"),
				Optional.ofNullable(EnumAdaptor.valueOf(1 , LicenseClassification.class )));
			return data;	
	}
	

}
