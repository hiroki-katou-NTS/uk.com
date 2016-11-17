package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QpdmtPayday;
@RequestScoped
public class JpaPaymentDateMasterRepository extends JpaRepository implements PaymentDateMasterRepository {

	private final String SELECT_SINGLE = "SELECT c FROM QPDMT_PAYDAY c WHERE c.CCD = :CCD and c.PAY_BONUS_ATR = :payBonusAtr and c.PROCESSING_YM = :processingYm and c.SPARE_PAY_ATR = :sparePayAtr and c.PROCESSING_NO = :processingNo";

	@Override
	public Optional<PaymentDateMaster> find(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr, int processingNo) {
		Optional<PaymentDateMaster> paymentDateMaster = this.queryProxy()
				.query(SELECT_SINGLE, QpdmtPayday.class).setParameter("CCD", companyCode)
				.setParameter("payBonusAtr", payBonusAtr).setParameter("processingYm", processingYm)
				.setParameter("sparePayAtr", sparePayAtr).setParameter("processingNo", processingNo).getSingle(c -> toDomain(c));
		return paymentDateMaster;
	}

	private static PaymentDateMaster toDomain(QpdmtPayday entity) {
		PaymentDateMaster domain = PaymentDateMaster.createFromJavaType(entity.neededWorkDay,
				entity.qpdmtPaydayPK.processingNo, entity.qpdmtPaydayPK.processingYM, entity.qpdmtPaydayPK.sparePayAtr,
				entity.qpdmtPaydayPK.payBonusAtr);
		entity.toDomain(domain);
		return domain;
	}

}
