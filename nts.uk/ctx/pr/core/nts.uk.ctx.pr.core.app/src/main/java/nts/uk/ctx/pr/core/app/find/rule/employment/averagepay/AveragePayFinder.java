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
	
	public AveragePayDto findByCompanyCode() {
		String companyCode = AppContexts.user().companyCode();
		Optional<AveragePay> data= this.averagePayRepository.findByCompanyCode(companyCode);
		if (!data.isPresent()) {
			return null;
		}
		AveragePay x = data.get();
		return new AveragePayDto(
				x.getCompanyCode().v(),
				x.getAttendDayGettingSet().value, 
				x.getExceptionPayRate().v(), 
				x.getRoundTimingSet().value, 
				x.getRoundDigitSet().value);
	}
}
