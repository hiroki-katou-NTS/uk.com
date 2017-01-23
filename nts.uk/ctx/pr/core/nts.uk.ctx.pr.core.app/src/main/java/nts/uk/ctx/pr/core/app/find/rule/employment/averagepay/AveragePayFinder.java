package nts.uk.ctx.pr.core.app.find.rule.employment.averagepay;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.rule.employment.averagepay.dto.AveragePayDto;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePay;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chinhbv
 *
 */
@RequestScoped
public class AveragePayFinder {

	@Inject
	private AveragePayRepository averagePayRepository;
	
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
	
	public List<AveragePayDto> findAll() {
		return this.averagePayRepository.findAll()
									.stream()
									.map(x -> new AveragePayDto(
											x.getCompanyCode().v(),
											x.getAttendDayGettingSet().value, 
											x.getExceptionPayRate().v(), 
											x.getRoundTimingSet().value, 
											x.getRoundDigitSet().value))
									.collect(Collectors.toList());
		
	}
		
}
