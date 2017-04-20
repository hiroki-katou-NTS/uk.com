package nts.uk.ctx.pr.report.app.payment.comparing.confirm.find;

import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;

public class DetailDifferentialFinder {

	@Inject
	private ComfirmDifferentRepository comfirmDiffRepository;

	public List<DetailDifferential> getDetailDifferential(String companyCode, int processingYMEarlier, int processingYMLater) {
		return this.comfirmDiffRepository.getDetailDifferential(companyCode, processingYMEarlier, processingYMLater);
	}

}
