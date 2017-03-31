package nts.uk.ctx.pr.report.app.payment.comparing.setting.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ComparingPrintSetFinder {

	@Inject
	private ComparingPrintSetRepository printSetRepository;

	public Optional<ComparingPrintSet> getComparingPrintSet() {
		return this.printSetRepository.getComparingPrintSet(AppContexts.user().companyCode());
	}

}
