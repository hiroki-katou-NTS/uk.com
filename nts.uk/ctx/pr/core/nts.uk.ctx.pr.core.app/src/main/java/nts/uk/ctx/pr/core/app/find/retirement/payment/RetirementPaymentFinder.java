package nts.uk.ctx.pr.core.app.find.retirement.payment;

import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.find.retirement.payment.dto.RetirementPaymentDto;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPayment;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPaymentRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class RetirementPaymentFinder {
	
	@Inject
	private RetirementPaymentRepository	retirementPaymentRepository;
	
	public RetirementPaymentDto findByCompanyCode(String personId, String dateTime) {
		String companyCode = AppContexts.user().companyCode();
		GeneralDate date = GeneralDate.fromString(dateTime, "yyyy/MM/dd");
		Optional<RetirementPayment> retirementPayment = this.retirementPaymentRepository.findByCompanyCode(companyCode, personId, date);
		if(!retirementPayment.isPresent()) { 
			return null; 
		}
		RetirementPayment x = retirementPayment.get();
		return new RetirementPaymentDto(
						x.getCompanyCode().v(), 
						x.getPersonId().v(),
						x.getPayDate(),
						x.getTrialPeriodSet().value,
						x.getExclusionYears().v(), 
						x.getAdditionalBoardYears().v(), 
						x.getBoardYears().v(), 
						x.getTotalPaymentMoney().v(), 
						x.getDeduction1Money().v(), 
						x.getDeduction2Money().v(), 
						x.getDeduction3Money().v(), 
						x.getRetirementPayOption().value, 
						x.getTaxCalculationMethod().value, 
						x.getIncomeTaxMoney().v(), 
						x.getCityTaxMoney().v(), 
						x.getPrefectureTaxMoney().v(), 
						x.getTotalDeclarationMoney().v(), 
						x.getActualRecieveMoney().v(), 
						x.getMemo().v());
	}
}
