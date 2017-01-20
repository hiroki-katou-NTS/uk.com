package nts.uk.ctx.pr.core.app.find.retirement.payment;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.find.retirement.payment.dto.RetirementPaymentDto;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPaymentRepository;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class RetirementPaymentFinder {
	
	@Inject
	private RetirementPaymentRepository	retirementPaymentRepository;
	
	public List<RetirementPaymentDto> findAll() {
		return this.retirementPaymentRepository.findAll()
				.stream()
				.map(x -> new RetirementPaymentDto(
						x.getActualRecieveMoney().v(), 
						x.getAdditionalBoardYears().v(), 
						x.getBoardYears().v(), 
						x.getCityTaxMoney().v(), 
						x.getCompanyCode().v(), 
						x.getDeduction1Money().v(), 
						x.getDeduction2Money().v(), 
						x.getDeduction3Money().v(), 
						x.getExclusionYears().v(), 
						x.getIncomeTaxMoney().v(), 
						x.getMemo().v(), 
						x.getPersonId().v(), 
						x.getPrefectureTaxMoney().v(), 
						x.getRetirementPayOption().value, 
						x.getTaxCalculationMethod().value, 
						x.getTotalDeclarationMoney().v(), 
						x.getTotalPaymentMoney().v(), 
						x.getTrialPeriodSet().value))
				.collect(Collectors.toList());
				
	}
}
