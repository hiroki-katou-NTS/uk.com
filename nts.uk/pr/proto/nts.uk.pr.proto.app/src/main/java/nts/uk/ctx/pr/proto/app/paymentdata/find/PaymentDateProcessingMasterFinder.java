package nts.uk.ctx.pr.proto.app.paymentdata.find;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateProcessingMasterRepository;

@RequestScoped
public class PaymentDateProcessingMasterFinder {
	/**
	 * PaymentDateProcessingMasterRepository
	 */
	@Inject
	private PaymentDateProcessingMasterRepository repository;

	/**
	 * 
	 * @param companyCode
	 * @param paymentBonusAtribute
	 * @param processingNo
	 * @return PaymentDateProcessingMaster
	 */
	public Optional<PaymentDateProcessingMasterDto> find(String companyCode, int paymentBonusAtribute,
			int processingNo) {
		return this.repository.find(companyCode, paymentBonusAtribute, processingNo)
				.map(d -> PaymentDateProcessingMasterDto.fromDomain(d));
	}

	/**
	 * 
	 * @param companyCode
	 * @param paymentBonusAtribute
	 * @return PaymentDateProcessingMasterDto
	 */
	public Optional<PaymentDateProcessingMasterDto> find(String companyCode, int paymentBonusAtribute) {
		return this.repository.find(companyCode, paymentBonusAtribute)
				.map(d -> PaymentDateProcessingMasterDto.fromDomain(d));
	}
}
