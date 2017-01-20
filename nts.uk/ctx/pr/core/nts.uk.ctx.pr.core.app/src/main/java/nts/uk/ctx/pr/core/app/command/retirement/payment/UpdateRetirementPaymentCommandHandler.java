package nts.uk.ctx.pr.core.app.command.retirement.payment;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.retirement.payment.PaymentMoney;
import nts.uk.ctx.pr.core.dom.retirement.payment.PaymentYear;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPayOption;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPayment;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPaymentRepository;
import nts.uk.ctx.pr.core.dom.retirement.payment.TaxCalculationMethod;
import nts.uk.ctx.pr.core.dom.retirement.payment.TrialPeriodSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;
import nts.uk.shr.com.primitive.PersonId;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class UpdateRetirementPaymentCommandHandler extends CommandHandler<UpdateRetirementPaymentCommand>{
	@Inject
	private RetirementPaymentRepository retirementPaymentRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateRetirementPaymentCommand> context) {
		// TODO Auto-generated method stub
		UpdateRetirementPaymentCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		RetirementPayment retirementPayment = new RetirementPayment(
				new PaymentMoney(command.getActualRecieveMoney()), 
				new PaymentYear(command.getAdditionalBoardYears()), 
				new PaymentYear(command.getBoardYears()), 
				new PaymentMoney(command.getCityTaxMoney()), 
				new CompanyCode(companyCode), 
				new PaymentMoney(command.getDeduction1Money()), 
				new PaymentMoney(command.getDeduction2Money()), 
				new PaymentMoney(command.getDeduction3Money()),  
				new PaymentYear(command.getExclusionYears()), 
				new PaymentMoney(command.getIncomeTaxMoney()), 
				new Memo(command.getMemo()), 
				new PersonId(command.getPersonId()), 
				new PaymentMoney(command.getPrefectureTaxMoney()), 
				EnumAdaptor.valueOf(command.getRetirementPayOption(), RetirementPayOption.class), 
				EnumAdaptor.valueOf(command.getTaxCalculationMethod(), TaxCalculationMethod.class), 
				new PaymentMoney(command.getTotalDeclarationMoney()), 
				new PaymentMoney(command.getTotalPaymentMoney()), 
				EnumAdaptor.valueOf(command.getTrialPeriodSet(), TrialPeriodSet.class));
		
		retirementPaymentRepository.update(retirementPayment);
	}
}
