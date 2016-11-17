package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentHeaderPK;

@RequestScoped
public class JpaPaymentDataRepository extends JpaRepository implements PaymentDataRepository {	

	@Override
	public Optional<Payment> find(String companyCode, String personId, int processingNo, int payBonusAttribute,
			int processingYM, int sparePayAttribute) {
		
		return this.queryProxy().find(new QstdtPaymentHeaderPK(companyCode, personId, processingNo, payBonusAttribute, 
									processingYM, sparePayAttribute), QstdtPaymentHeader.class)
				.map(c -> toDomain(c));
	
	}
	
	private static Payment toDomain(QstdtPaymentHeader entity){
		val domain = Payment.createFromJavaType(
												entity.qstdtPaymentHeaderPK.companyCode, 
												entity.qstdtPaymentHeaderPK.personId, 
												entity.qstdtPaymentHeaderPK.processingNo, 
												entity.qstdtPaymentHeaderPK.payBonusAtr, 
												entity.qstdtPaymentHeaderPK.processingYM, 
												entity.qstdtPaymentHeaderPK.sparePayAtr, 
												entity.standardDate, 
												entity.specificationCode, 
												entity.residenceCode, 
												entity.residenceName, 
												entity.healthInsuranceGrade, 
												entity.healthInsuranceAverageEarn, 
												entity.ageContinuationInsureAtr, 
												entity.tenureAtr, 
												entity.taxAtr, 
												entity.pensionInsuranceGrade, 
												entity.pensionAverageEarn, 
												entity.employementInsuranceAtr, 
												entity.dependentNumber, 
												entity.workInsuranceCalculateAtr, 
												entity.insuredAtr, 
												entity.bonusTaxRate, 
												entity.calcFlag, 
												entity.makeMethodFlag);
		entity.toDomain(domain);
		return domain;
	}
	
	@Override
	public void add(Payment payment) {
		// TODO Auto-generated method stub
		
	}
}