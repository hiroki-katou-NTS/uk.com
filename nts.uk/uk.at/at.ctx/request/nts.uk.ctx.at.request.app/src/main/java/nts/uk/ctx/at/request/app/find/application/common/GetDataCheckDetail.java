package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.InputGetDetailCheck;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.DetailedScreenPreBootModeOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class GetDataCheckDetail {

	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private ApplicationRepository appRepo;
	
	public OutputDetailCheckDto getDataCheckDetail(InputGetDetailCheck inputGetDetailCheck) {
		String companyID = AppContexts.user().companyId();
		OutputDetailCheckDto ouput = null;
		Optional<Application> app = appRepo.getAppById(companyID, inputGetDetailCheck.getApplicationID());
		GeneralDate generalDate = GeneralDate.fromString(inputGetDetailCheck.getBaseDate(), "yyyy/MM/dd");
		if(app.isPresent()) {
			ouput = OutputDetailCheckDto.fromDomain(beforePreBootMode.getDetailedScreenPreBootMode(app.get(), generalDate))	;
		}
		return ouput;
	}
	
	
}
