package nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;

@Getter
@AllArgsConstructor
public class ApproverRegisterSet {
	/** 会社単位  */
	private DisplayAtr companyUnit;
	/** 職場単位  */
	private DisplayAtr workplaceUnit;
	/** 社員単位  */
	private DisplayAtr employeeUnit;
	
	public static ApproverRegisterSet toDomain(int companyUnit, int workplaceUnit, int employeeUnit){
		return new ApproverRegisterSet(
				EnumAdaptor.valueOf(companyUnit, DisplayAtr.class), 
				EnumAdaptor.valueOf(workplaceUnit, DisplayAtr.class),
				EnumAdaptor.valueOf(employeeUnit, DisplayAtr.class)
				);
	}
}
