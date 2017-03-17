package nts.uk.ctx.pr.report.app.payment.comparing.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.ComparingFormHeaderRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComparingFormHeaderFinder {

	@Inject
	private ComparingFormHeaderRepository comparingFormRepository;

	public List<ComparingFormHeaderDto> getListComparingFormHeader() {
		String companyCode = AppContexts.user().companyCode();
		return comparingFormRepository.getListComparingFormHeader(companyCode).stream()
				.map(item -> ComparingFormHeaderDto.fromDomain(item)).collect(Collectors.toList());
	}	
}
