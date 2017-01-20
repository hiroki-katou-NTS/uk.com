package nts.uk.ctx.pr.core.infra.repository.retirement.payment;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPayment;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPaymentRepository;
import nts.uk.ctx.pr.core.infra.entity.retirement.payment.QredtRetirementPayment;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class JpaRetirementPaymentRepository extends JpaRepository implements RetirementPaymentRepository {
	
	private final String Select_no_where = "Select a From QredtRetirementPayment a";
	@Override
	public void add(RetirementPayment retirementPayment) {
		// TODO Auto-generated method stub
		this.commandProxy().insert(retirementPayment);
	}
	
	@Override
	public List<RetirementPayment> findAll() {
		// TODO Auto-generated method stub
		return this.queryProxy().query(Select_no_where, QredtRetirementPayment.class).
				getList(x -> RetirementPayment.createFromJavaType(
						x.actualRecieveMoney, 
						x.additionalBoardYears, 
						x.boardYears, 
						x.cityTaxMoney, 
						x.qredtRetirementPaymentPK.companyCode, 
						x.deduction1Money, 
						x.deduction2Money, 
						x.deduction3Money, 
						x.exclusionYears, 
						x.incomeTaxMoney, 
						x.memo, 
						x.qredtRetirementPaymentPK.personId, 
						x.prefectureTaxMoney, 
						x.retirementPayOption, 
						x.taxCalculationMethod, 
						x.totalDeclarationMoney, 
						x.totalPaymentMoney, 
						x.trialPeriodSet));
	}
	
	@Override
	public void update(RetirementPayment retirementPayment) {
		// TODO Auto-generated method stub
		this.commandProxy().update(retirementPayment);
	}
	
	@Override
	public void remove(RetirementPayment retirementPayment) {
		// TODO Auto-generated method stub
		this.commandProxy().remove(retirementPayment);
	}
}
