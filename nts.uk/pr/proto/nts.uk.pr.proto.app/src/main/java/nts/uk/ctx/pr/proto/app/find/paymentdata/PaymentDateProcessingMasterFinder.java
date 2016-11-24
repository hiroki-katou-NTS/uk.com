package nts.uk.ctx.pr.proto.app.find.paymentdata;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateProcessingMasterRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

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
	public Optional<PaymentDateProcessingMasterDto> find(int paymentBonusAtribute,
			int processingNo) {
		LoginUserContext login = AppContexts.user();
		return this.repository.find(login.companyCode(), paymentBonusAtribute, processingNo)
				.map(d -> PaymentDateProcessingMasterDto.fromDomain(d));
	}

	/**
	 * 
	 * @param companyCode
	 * @param paymentBonusAtribute
	 * @return PaymentDateProcessingMasterDto
	 */
	public List<PaymentDateProcessingMasterDto> findAll(int paymentBonusAtribute) {
		LoginUserContext login = AppContexts.user();
		return this.repository.findAll(login.companyCode(), paymentBonusAtribute).stream()
				.map(d -> { return PaymentDateProcessingMasterDto.fromDomain(d); })
				.collect(Collectors.toList());
	}
}
