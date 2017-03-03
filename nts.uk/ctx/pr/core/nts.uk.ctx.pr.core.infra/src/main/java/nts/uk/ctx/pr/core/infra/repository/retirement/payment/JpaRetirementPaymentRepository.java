package nts.uk.ctx.pr.core.infra.repository.retirement.payment;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.ws.json.serializer.GeneralDateTimeSerializer;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPayment;
import nts.uk.ctx.pr.core.dom.retirement.payment.RetirementPaymentRepository;
import nts.uk.ctx.pr.core.infra.entity.retirement.payment.QredtRetirementPayment;
import nts.uk.ctx.pr.core.infra.entity.retirement.payment.QredtRetirementPaymentPK;
import nts.uk.shr.com.primitive.PersonId;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
@Transactional
public class JpaRetirementPaymentRepository extends JpaRepository implements RetirementPaymentRepository {
	
	private final String FindByCompanyCodeAndPersonId = "Select a From QredtRetirementPayment a "
											+ "where a.qredtRetirementPaymentPK.companyCode = :companyCode "
											+ "and a.qredtRetirementPaymentPK.personId = :personId ";
	
	@Override
	public void add(RetirementPayment retirementPayment) {
		this.commandProxy().insert(convertToEntity(retirementPayment));
	}
	
	@Override
	public List<RetirementPayment> findByCompanyCodeandPersonId(CompanyCode companyCode, PersonId personId) {
		return this.queryProxy().query(FindByCompanyCodeAndPersonId, QredtRetirementPayment.class)
				.setParameter("companyCode", companyCode.v())
				.setParameter("personId", personId.v())
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
	public Optional<RetirementPayment> findRetirementPaymentInfo(CompanyCode companyCode, PersonId personId, GeneralDate dateTime) {
		return this.queryProxy().find(new QredtRetirementPaymentPK(companyCode.v(), personId.v(), dateTime), QredtRetirementPayment.class)
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
		this.commandProxy().update(convertToEntity(retirementPayment));
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
	
	private QredtRetirementPayment convertToEntity(RetirementPayment retirementPayment){
		return new QredtRetirementPayment(
					new QredtRetirementPaymentPK(
							retirementPayment.getCompanyCode().v(), 
							retirementPayment.getPersonId().v(), 
							retirementPayment.getPayDate()),
					retirementPayment.getTrialPeriodSet().value,
					retirementPayment.getExclusionYears().v(),
					retirementPayment.getAdditionalBoardYears().v(),
					retirementPayment.getBoardYears().v(),
					retirementPayment.getTotalPaymentMoney().v(),
					retirementPayment.getDeduction1Money().v(),
					retirementPayment.getDeduction2Money().v(),
					retirementPayment.getDeduction3Money().v(),
					retirementPayment.getRetirementPayOption().value,
					retirementPayment.getTaxCalculationMethod().value,
					retirementPayment.getIncomeTaxMoney().v(),
					retirementPayment.getCityTaxMoney().v(),
					retirementPayment.getPrefectureTaxMoney().v(),
					retirementPayment.getTotalDeclarationMoney().v(),
					retirementPayment.getActualRecieveMoney().v(),
					retirementPayment.getBankTransferOption1().value,
					retirementPayment.getOption1Money().v(),
					retirementPayment.getBankTransferOption2().value,
					retirementPayment.getOption2Money().v(),
					retirementPayment.getBankTransferOption3().value,
					retirementPayment.getOption3Money().v(),
					retirementPayment.getBankTransferOption4().value,
					retirementPayment.getOption4Money().v(),
					retirementPayment.getBankTransferOption5().value,
					retirementPayment.getOption5Money().v(),
					retirementPayment.getWithholdingMeno().v(),
					retirementPayment.getStatementMemo().v()
				);
	}
}
