package nts.uk.ctx.pr.core.app.find.rule.employment.avepay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	/*public AvePayDto find() {
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
	}*/
	
	public List<AvePayDto> findAll() {
		
		return this.avePayRepository.findAll()
									.stream()
									.map(x -> new AvePayDto(
											x.getAttendDayGettingSet().value, 
											x.getExceptionPayRate().v(), 
											x.getRoundTimingSet().value, 
											x.getRoundDigitSet().value))
									.collect(Collectors.toList());
		
	}
		
}
