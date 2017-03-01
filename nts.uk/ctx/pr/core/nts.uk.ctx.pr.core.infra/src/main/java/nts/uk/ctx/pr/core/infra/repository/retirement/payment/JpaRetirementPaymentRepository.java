package nts.uk.ctx.pr.core.infra.repository.retirement.payment;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.ws.json.serializer.GeneralDateTimeSerializer;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPayment;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPaymentRepository;
import nts.uk.ctx.pr.core.infra.entity.retirement.payment.QredtRetirementPayment;
import nts.uk.ctx.pr.core.infra.entity.retirement.payment.QredtRetirementPaymentPK;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class JpaRetirementPaymentRepository extends JpaRepository implements RetirementPaymentRepository {
	
	private final String FindByCompanyCodeAndPersonId = "Select a From QredtRetirementPayment a "
											+ "where a.qredtRetirementPaymentPK.companyCode = :companyCode "
											+ "and a.qredtRetirementPaymentPK.personId = :personId ";
	
	@Override
	public void add(RetirementPayment retirementPayment) {
		this.commandProxy().insert(retirementPayment);
	}
	
	@Override
	public List<RetirementPayment> findByCompanyCodeandPersonId(String companyCode, String personId) {
		return this.queryProxy().query(FindByCompanyCodeAndPersonId, QredtRetirementPayment.class)
				.setParameter("companyCode", companyCode)
				.setParameter("personId", personId)
				.getList(x -> RetirementPayment.createFromJavaType(
						x.qredtRetirementPaymentPK.companyCode, 
						x.qredtRetirementPaymentPK.personId, 
						x.qredtRetirementPaymentPK.payDate, 
						x.trialPeriodSet, 
						x.exclusionYears, 
						x.additionalBoardYears, 
						x.boardYears, 
						x.totalPaymentMoney, 
						x.deduction1Money, 
						x.deduction2Money, 
						x.deduction3Money, 
						x.retirementPayOption, 
						x.taxCalculationMethod, 
						x.incomeTaxMoney, 
						x.cityTaxMoney, 
						x.prefectureTaxMoney, 
						x.totalDeclarationMoney, 
						x.actualRecieveMoney, 
						x.bankTransferOption1,
						x.option1Money,
						x.bankTransferOption2,
						x.option2Money,
						x.bankTransferOption3,
						x.option3Money,
						x.bankTransferOption4,
						x.option4Money,
						x.bankTransferOption5,
						x.option5Money,
						x.withholdingMeno,
						x.statementMemo));
	}
	
	@Override
	public Optional<RetirementPayment> findRetirementPaymentInfo(String companyCode, String personId, GeneralDate dateTime) {
		return this.queryProxy().find(new QredtRetirementPaymentPK(companyCode, personId, dateTime), QredtRetirementPayment.class)
				.map(x -> RetirementPayment.createFromJavaType(
						x.qredtRetirementPaymentPK.companyCode, 
						x.qredtRetirementPaymentPK.personId, 
						x.qredtRetirementPaymentPK.payDate,
						x.trialPeriodSet,
						x.exclusionYears,
						x.additionalBoardYears, 
						x.boardYears,
						x.totalPaymentMoney,
						x.deduction1Money, 
						x.deduction2Money, 
						x.deduction3Money, 
						x.retirementPayOption, 
						x.taxCalculationMethod,
						x.incomeTaxMoney, 
						x.cityTaxMoney, 
						x.prefectureTaxMoney,
						x.totalDeclarationMoney,
						x.actualRecieveMoney,
						x.bankTransferOption1,
						x.option1Money,
						x.bankTransferOption2,
						x.option2Money,
						x.bankTransferOption3,
						x.option3Money,
						x.bankTransferOption4,
						x.option4Money,
						x.bankTransferOption5,
						x.option5Money,
						x.withholdingMeno,
						x.statementMemo));
	}
	
	@Override
	public void update(RetirementPayment retirementPayment) {
		this.commandProxy().update(retirementPayment);
	}
	
	@Override
	public void remove(RetirementPayment retirementPayment) {
		this.commandProxy().remove(
				QredtRetirementPayment.class,
				new QredtRetirementPaymentPK(
						retirementPayment.getCompanyCode().v(), 
						retirementPayment.getPersonId().v(),
						retirementPayment.getPayDate()
				)
		);
	}
}
