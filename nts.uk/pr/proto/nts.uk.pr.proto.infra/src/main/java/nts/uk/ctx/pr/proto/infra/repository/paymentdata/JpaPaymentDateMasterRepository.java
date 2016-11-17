package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.paymentdatemaster.PaymentDateMaster;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDateMasterRepository;
import nts.uk.ctx.pr.proto.dom.personalinfo.wage.PersonalWage;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QpdmtPayday;
import nts.uk.ctx.pr.proto.infra.entity.personalinfo.wage.PprmtPersonWage;

@RequestScoped
public class JpaPaymentDateMasterRepository extends JpaRepository implements PaymentDateMasterRepository {

	private final String SELECT_NOT_WITH_PROCESSINGNO = "SELECT c FROM QPDMT_PAYDAY c WHERE c.CCD = :CCD and c.PAY_BONUS_ATR = :payBonusAtr and c.PROCESSING_YM = :processingYm and c.SPARE_PAY_ATR = :sparePayAtr";

	@Override
	public List<PaymentDateMaster> find(String companyCode, int payBonusAtr, int processingYm, int sparePayAtr) {
		List<PaymentDateMaster> lstPaymentDateMaster = this.queryProxy()
				.query(SELECT_NOT_WITH_PROCESSINGNO, QpdmtPayday.class).setParameter("CCD", companyCode)
				.setParameter("payBonusAtr", payBonusAtr).setParameter("processingYm", processingYm)
				.setParameter("sparePayAtr", sparePayAtr).getList(c -> toDomain(c));
		return lstPaymentDateMaster;
	}

	private static PaymentDateMaster toDomain(QpdmtPayday entity) {
		PaymentDateMaster domain = PaymentDateMaster.createFromJavaType(entity.neededWorkDay,
				entity.qpdmtPaydayPK.processingNo, entity.qpdmtPaydayPK.processingYM, entity.qpdmtPaydayPK.sparePayAtr,
				entity.qpdmtPaydayPK.payBonusAtr);
		entity.toDomain(domain);
		return domain;
	}

}
