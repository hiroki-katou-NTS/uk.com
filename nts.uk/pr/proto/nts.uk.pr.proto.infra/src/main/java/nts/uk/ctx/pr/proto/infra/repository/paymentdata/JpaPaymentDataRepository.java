package nts.uk.ctx.pr.proto.infra.repository.paymentdata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.paymentdata.Payment;
import nts.uk.ctx.pr.proto.dom.paymentdata.dataitem.DetailItem;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.PaymentDataRepository;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentDetailPK;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.proto.infra.entity.paymentdata.QstdtPaymentHeaderPK;

@RequestScoped
public class JpaPaymentDataRepository extends JpaRepository implements PaymentDataRepository {
	private final String SELECT_HEADER = " SELECT c FROM QstdtPaymentHeader c "
			+ " WHERE c.qstdtPaymentHeaderPK.companyCode = :CCD" + " AND c.qstdtPaymentHeaderPK.personId = :PID"
			+ " AND c.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR"
			+ " AND c.qstdtPaymentHeaderPK.processingYm = :PROCESSING_YM";

	@Override
	public Optional<Payment> find(String companyCode, String personId, int processingNo, int payBonusAttribute,
			int processingYM, int sparePayAttribute) {
		return this.queryProxy().find(new QstdtPaymentHeaderPK(companyCode, personId, processingNo, payBonusAttribute,
				processingYM, sparePayAttribute), QstdtPaymentHeader.class).map(c -> toDomain(c));

	}

	@Override
	public List<Payment> findPaymentHeader(String companyCode, String personId, int payBonusAtr, int processingYm) {
		return this.queryProxy().query(SELECT_HEADER, QstdtPaymentHeader.class).setParameter("CCD", companyCode)
				.setParameter("PID", personId).setParameter("PAY_BONUS_ATR", payBonusAtr)
				.setParameter("PROCESSING_YM", processingYm).getList(c -> toDomain(c));
	}

	@Override
	public boolean isExistHeader(String companyCode, String personId, int payBonusAttribute, int processingYM) {
		List<QstdtPaymentHeader> pHeader = this.queryProxy().query(SELECT_HEADER, QstdtPaymentHeader.class).getList();

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
				entity.pensionInsuranceGrade, entity.pensionAverageEarn, entity.employmentInsuranceAtr,
				entity.dependentNumber, entity.workInsuranceCalculateAtr, entity.insuredAtr, entity.bonusTaxRate,
				entity.calcFlag, entity.makeMethodFlag, entity.comment);
		entity.toDomain(domain);
		return domain;
	}

	public void updateDetails(int categoryAtr, List<DetailItem> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Payment payment) {
		// TODO Auto-generated method stub

	}

	private static QstdtPaymentHeader toPaymentHeaderEntity(Payment domain) {
		QstdtPaymentHeader entity = new QstdtPaymentHeader();
		entity.fromDomain(domain);
		entity.qstdtPaymentHeaderPK = new QstdtPaymentHeaderPK(domain.getCompanyCode().v(), domain.getPersonId().v(),
				domain.getProcessingNo().v().intValue(), domain.getPayBonusAtr().value,
				domain.getProcessingYM().v().intValue(), domain.getSparePayAtr().value);
		entity.standardDate = domain.getStandardDate().localDate();
		entity.specificationCode = domain.getSpecificationCode().v();
		entity.residenceCode = domain.getResidenceCode().v();
		entity.residenceName = domain.getResidenceName().v();
		entity.healthInsuranceAverageEarn = domain.getHealthInsuranceGrade().v().intValue();
		entity.healthInsuranceAverageEarn = domain.getHealthInsuranceAverageEarn().v().intValue();
		entity.ageContinuationInsureAtr = domain.getAgeContinuationInsureAtr().value;
		entity.tenureAtr = domain.getTenureAtr().value;
		entity.taxAtr = domain.getTaxAtr().value;
		entity.pensionAverageEarn = domain.getPensionAverageEarn().v().intValue();
		entity.employmentInsuranceAtr = domain.getEmploymentInsuranceAtr().value;
		entity.dependentNumber = domain.getDependentNumber().v().intValue();
		entity.workInsuranceCalculateAtr = domain.getWorkInsuranceCalculateAtr().value;
		entity.insuredAtr = domain.getInsuredAtr().value;
		entity.bonusTaxRate = domain.getBonusTaxRate().v().intValue();
		entity.calcFlag = domain.getCalcFlag().value;
		entity.makeMethodFlag = domain.getMakeMethodFlag().value;
		entity.comment = domain.getComment().v();
		return entity;
	}

	private QstdtPaymentDetail toPaymentDetailEntity(Payment domain, DetailItem detail) {
		QstdtPaymentDetail entity = new QstdtPaymentDetail();
		entity.fromDomain(domain);
		entity.qstdtPaymentDetailPK = new QstdtPaymentDetailPK(domain.getCompanyCode().v(), domain.getPersonId().v(),
				domain.getProcessingNo().v().intValue(), domain.getPayBonusAtr().value,
				domain.getProcessingYM().v().intValue(), domain.getSparePayAtr().value,
				detail.getCategoryAttribute().value, detail.getItemCode().v());
		entity.value = BigDecimal.valueOf(detail.getValue());
		entity.correctFlag = detail.getCorrectFlag().value;
		entity.socialInsurranceAttribute = detail.getSocialInsuranceAtr().value;
		entity.laborSubjectAttribute = detail.getLaborInsuranceAtr().value;
		entity.columnPosition = detail.getItemPostion().getColumnPosition().v().intValue();
		entity.linePosition = detail.getItemPostion().getLinePosition().v().intValue();
		return entity;
	}

	@Override
	public void importPayment(Payment payment) {
		QstdtPaymentHeader paymentHeader = toPaymentHeaderEntity(payment);

		this.commandProxy().insert(paymentHeader);

		for (DetailItem item : payment.getDetailPaymentItems()) {
			QstdtPaymentDetail detail = toPaymentDetailEntity(payment, item);
			this.commandProxy().insert(detail);
		}
		for (DetailItem item : payment.getDetailDeductionItems()) {
			QstdtPaymentDetail detail = toPaymentDetailEntity(payment, item);

			this.commandProxy().insert(detail);
		}
		for (DetailItem item : payment.getDetailPersonalTimeItems()) {
			QstdtPaymentDetail detail = toPaymentDetailEntity(payment, item);

			this.commandProxy().insert(detail);
		}
		for (DetailItem item : payment.getDetailArticleItems()) {
			QstdtPaymentDetail detail = toPaymentDetailEntity(payment, item);

			this.commandProxy().insert(detail);
		}
	}

	public boolean isExistDetail(String companyCode, String personId, int baseYM, int categoryAtr, String itemCode) {
		// TODO Auto-generated method stub
		return false;
	}

}