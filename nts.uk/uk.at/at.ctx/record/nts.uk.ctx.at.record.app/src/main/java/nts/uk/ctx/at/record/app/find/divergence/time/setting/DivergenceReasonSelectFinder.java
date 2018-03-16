package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceReasonSelectFinder {

	public List<DivergenceReasonSelectDto> getAllReason(int divTimeId){
		// Get company id
		String companyId = AppContexts.user().companyId();
		
		// Get list divergence time
//		List<DivergenceReasonSelect> reasonlist =
		
		return null;
	}
}
