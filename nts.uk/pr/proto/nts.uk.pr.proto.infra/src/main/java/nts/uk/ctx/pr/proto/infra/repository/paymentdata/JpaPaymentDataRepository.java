package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.List;
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

	private final String SELECT_HEADER = "SELECT c FROM QstdtPaymentHeader WHERE c.qstdtPaymentHeaderPK.companyCode = :ccd and c.qstdtPaymentHeaderPK.personId = :pid c.qstdtPaymentHeaderPK.payBonusAtr = c:payBonusAtr and c.qstdtPaymentHeaderPK.processingYm = c:processingYm";

	@Override
	public Optional<Payment> find(String companyCode, String personId, int processingNo, int payBonusAttribute,
			int processingYM, int sparePayAttribute) {
		return this.queryProxy().find(new QstdtPaymentHeaderPK(companyCode, personId, processingNo, payBonusAttribute,
				processingYM, sparePayAttribute), QstdtPaymentHeader.class).map(c -> toDomain(c));

	}

	private static Payment toDomain(QstdtPaymentHeader entity) {
		val domain = Payment.createFromJavaType(entity.qstdtPaymentHeaderPK.companyCode,
				entity.qstdtPaymentHeaderPK.personId, entity.qstdtPaymentHeaderPK.processingNo,
				entity.qstdtPaymentHeaderPK.payBonusAtr, entity.qstdtPaymentHeaderPK.processingYM,
				entity.qstdtPaymentHeaderPK.sparePayAtr, entity.standardDate, entity.specificationCode,
				entity.residenceCode, entity.residenceName, entity.healthInsuranceGrade,
				entity.healthInsuranceAverageEarn, entity.ageContinuationInsureAtr, entity.tenureAtr, entity.taxAtr,
				entity.pensionInsuranceGrade, entity.pensionAverageEarn, entity.employementInsuranceAtr,
				entity.dependentNumber, entity.workInsuranceCalculateAtr, entity.insuredAtr, entity.bonusTaxRate,
				entity.calcFlag, entity.makeMethodFlag, entity.comment);
		entity.toDomain(domain);
		return domain;
	}


	@Override
	public void update(Payment payment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Payment payment) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Payment> findPaymentHeader(String companyCode, String personId, int payBonusAttribute,
			int processingYM) {
		return this.queryProxy().query(SELECT_HEADER, QstdtPaymentHeader.class).getList(c -> toDomain(c));
	}

	@Override
	public void importPayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

}