package nts.uk.ctx.pr.screen.app.query.paymentdata.processor;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PersonalPaymentSettingRepository;
import nts.uk.ctx.pr.screen.app.query.paymentdata.result.PaymentDataResult;

/**
 * GetPaymentDataQueryProcessor
 * 
 * @author vunv
 *
 */
@RequestScoped
public class GetPaymentDataQueryProcessor {

	/** PersonalPaymentSettingRepository */
	@Inject
	private PersonalPaymentSettingRepository personalPSRepository;

	/**
	 * Find a company by code.
	 * 
	 * @param companyCode
	 *            code
	 * @return company
	 */
	public Optional<PaymentDataResult> find(String companyCode, Integer personId) {
		// return this.repository.find(companyCode, personId).map(d ->
		// PersonalPaymentSettingDto.fromDomain(d));
		return null;
	}
}
