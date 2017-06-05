package nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.RefundLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RefundLayoutFinder {

	@Inject
	private RefundLayoutRepository refundLayoutRepository;

	public RefundLayoutDto getRefundLayout(int printType) {
		return refundLayoutRepository.getRefundLayout(AppContexts.user().companyCode(), printType)
				.map(s -> RefundLayoutDto.fromDomain(s)).orElse(null);
	}

}
