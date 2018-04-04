package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;
import nts.uk.shr.com.context.AppContexts;

public class JpaDivergenceReasonInputMethodServiceImp implements DivergenceReasonInputMethodService {
	
	@Inject
	DivergenceReasonInputMethodRepository divergenceReasonInputMethodRepo;

	@Override
	public boolean DetermineLeakageReason(String employeeId, GeneralDate processDate, Integer divergenceTimeNo,
			DivergenceReasonCode divergenceReasonCode, DivergenceReason divergenceReason, boolean justmentResult) {
		
		if(!justmentResult)
		{
			//In case of error or alarm
			String companyId = AppContexts.user().companyId();
			Optional<DivergenceReasonInputMethod> optionalDivReasonInputMethod = divergenceReasonInputMethodRepo.getDivTimeInfo(companyId, divergenceTimeNo);
			if(optionalDivReasonInputMethod.isPresent()){
				DivergenceReasonInputMethod divReasonInputMethod = optionalDivReasonInputMethod.get();
				
				boolean reasonInput = divReasonInputMethod.isDivergenceReasonInputed();
				boolean reasonSelect = divReasonInputMethod.isDivergenceReasonSelected();
				
				if(reasonInput && reasonSelect){
					
				}
			}
			
		}
		else
		{
			return true;
		}
		return false;
	}

}
