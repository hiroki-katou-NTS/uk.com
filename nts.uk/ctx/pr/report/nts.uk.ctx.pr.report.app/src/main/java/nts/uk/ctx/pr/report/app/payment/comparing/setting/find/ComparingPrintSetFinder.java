package nts.uk.ctx.pr.report.app.payment.comparing.setting.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComparingPrintSetFinder {

	@Inject
	private ComparingPrintSetRepository printSetRepository;

	public ComparingPrintSetDto getComparingPrintSet() {
		return this.printSetRepository.getComparingPrintSet(AppContexts.user().companyCode())
				.map(c -> ComparingPrintSetDto.fromDomain(c)).orElse(null);
	}

}
