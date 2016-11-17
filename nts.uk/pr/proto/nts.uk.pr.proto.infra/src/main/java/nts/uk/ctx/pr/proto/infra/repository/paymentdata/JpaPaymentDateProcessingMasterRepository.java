package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateProcessingMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateProcessingMasterRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.paymentdatemaster.QpdmtPaydayProcessing;

@RequestScoped
public class JpaPaymentDateProcessingMasterRepository extends JpaRepository
		implements PaymentDateProcessingMasterRepository {

	private final String SELECT_WITH_PROCESSINGNO = "SELECT c FROM QPDMT_PAYDAY_PROCESSING c WHERE c.CCD = :CCD and c.PAY_BONUS_ATR = :payBonusAtr and c.PROCESSING_NO = :processingNo";
	private final String SELECT_NOT_WITH_PROCESSINGNO = "SELECT c FROM QPDMT_PAYDAY_PROCESSING c WHERE c.CCD = :CCD and c.PAY_BONUS_ATR = :payBonusAtr";

	@Override
	public Optional<PaymentDateProcessingMaster> find(String companyCode, int paymentBonusAtribute,
			int processingNo) {
		return this.queryProxy()
				.query(SELECT_WITH_PROCESSINGNO, QpdmtPaydayProcessing.class).setParameter("CCD", companyCode)
				.setParameter("payBonusAtr", paymentBonusAtribute).setParameter("processingNo", processingNo)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public Optional<PaymentDateProcessingMaster> find(String companyCode, int paymentBonusAtribute) {
		return this.queryProxy()
				.query(SELECT_NOT_WITH_PROCESSINGNO, QpdmtPaydayProcessing.class).setParameter("CCD", companyCode)
				.setParameter("payBonusAtr", paymentBonusAtribute)
				.getSingle(c -> toDomain(c));
	}

	private static PaymentDateProcessingMaster toDomain(QpdmtPaydayProcessing entity) {
		PaymentDateProcessingMaster domain = PaymentDateProcessingMaster.createFromJavaType(
				entity.qpdmtPaydayProcessingPK.payBonusAtr, entity.qpdmtPaydayProcessingPK.processingNo,
				entity.processingName, entity.currentProcessingYm, entity.dispAtr);
		entity.toDomain(domain);
		return domain;
	}

}
