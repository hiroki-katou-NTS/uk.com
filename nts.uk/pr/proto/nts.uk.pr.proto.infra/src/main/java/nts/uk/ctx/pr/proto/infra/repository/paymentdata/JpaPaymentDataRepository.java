package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailDeductionItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentHeaderPK;

@RequestScoped
public class JpaPaymentDataRepository extends JpaRepository implements PaymentDataRepository {
	private final String SELECT_HEADER = " SELECT c FROM QstdtPaymentHeader c " +
										 " WHERE c.qstdtPaymentHeaderPK.companyCode = :CCD" + 
										 		" AND c.qstdtPaymentHeaderPK.personId = :PID" + 
										 		" AND c.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR" + 
										 		" AND c.qstdtPaymentHeaderPK.processingYm = :PROCESSING_YM";

	@Override
	public Optional<Payment> find(String companyCode, String personId, int processingNo, int payBonusAttribute,
			int processingYM, int sparePayAttribute) {
		return this.queryProxy().find(new QstdtPaymentHeaderPK(companyCode, personId, processingNo, payBonusAttribute,
				processingYM, sparePayAttribute), QstdtPaymentHeader.class).map(c -> toDomain(c));

	}

	@Override
	public List<Payment> findPaymentHeader(String companyCode, String personId, int payBonusAtr,
			int processingYm) {
		return this.queryProxy()
					.query(SELECT_HEADER, QstdtPaymentHeader.class)
						.setParameter("CCD", companyCode)
						.setParameter("PID", personId)
						.setParameter("PAY_BONUS_ATR", payBonusAtr)
						.setParameter("PROCESSING_YM", processingYm)
					.getList(c -> toDomain(c));
	}

	@Override
	public boolean isExistHeader(String companyCode, String personId, int payBonusAttribute,
			int processingYM) {
		List<QstdtPaymentHeader> pHeader  = this.queryProxy().query(SELECT_HEADER, QstdtPaymentHeader.class).getList();
		
		return !pHeader.isEmpty();
	}
	
	@Override
	public void update(Payment payment) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void importHeader(Payment payment) {
		// TODO Auto-generated method stub
		
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
	public void insertHeader(Payment payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDeductionDetails(int categoryAtr, List<DetailDeductionItem> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertDetails(int categoryAtr, List<DetailItem> items) {
		// TODO Auto-generated method stub
		
	}

	public void updateDetails(int categoryAtr, List<DetailItem> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDeductionDetails(int categoryAtr, List<DetailDeductionItem> items) {
		
	}

	@Override
	public void importPayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

}