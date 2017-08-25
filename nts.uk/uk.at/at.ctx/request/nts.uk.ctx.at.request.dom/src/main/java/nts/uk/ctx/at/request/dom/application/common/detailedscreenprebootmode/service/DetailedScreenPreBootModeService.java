package nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.CanBeApprovedOutput;
import nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode.DetailedScreenPreBootModeOutput;

public interface DetailedScreenPreBootModeService {
	
	public DetailedScreenPreBootModeOutput getDetailedScreenPreBootMode(Application applicationData, GeneralDate baseDate);
	
	public boolean checkAsApprover(Application applicationData);
	
	public CanBeApprovedOutput canBeApproved(Application applicationData, ReflectPlanPerState status);
	
	
	
}