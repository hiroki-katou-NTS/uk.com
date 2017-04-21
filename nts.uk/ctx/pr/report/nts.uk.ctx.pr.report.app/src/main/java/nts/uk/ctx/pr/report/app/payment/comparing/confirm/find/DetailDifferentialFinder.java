package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DetailDifferentialFinder {

	@Inject
	private ComfirmDifferentRepository comfirmDiffRepository;

	public List<DetailDifferential> getDetailDifferential(int processingYMEarlier, int processingYMLater) {
		return this.comfirmDiffRepository.getDetailDifferential(AppContexts.user().companyCode(), processingYMEarlier, processingYMLater);
	}
}
