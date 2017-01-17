package nts.uk.ctx.pr.core.app.find.rule.employment.avepay;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.rule.employment.avepay.dto.AvePayDto;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePay;
import nts.uk.ctx.pr.core.dom.rule.employment.avepay.AvePayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chinhbv
 *
 */
@RequestScoped
public class AvePayFinder {

	@Inject
	private AvePayRepository avePayRepository;
	
	/**
	 * 
	 * @return
	 */
	public AvePayDto find() {
		String companyCode = AppContexts.user().companyCode();
		
		Optional<AvePay> domain = avePayRepository.find(companyCode);
		
		if (!domain.isPresent()) {
			return null;
		}
		
		AvePay avePay = domain.get();
		
		return new AvePayDto(
				avePay.getAttendDayGettingSet().value, 
				avePay.getExceptionPayRate().v(), 
				avePay.getRoundDigitSet().value, 
				avePay.getRoundTimingSet().value); 
	}
	
}
