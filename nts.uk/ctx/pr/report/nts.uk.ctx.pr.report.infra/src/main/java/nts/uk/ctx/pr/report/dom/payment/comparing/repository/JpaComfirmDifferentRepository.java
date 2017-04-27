package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsdtPaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsdtPaycompConfirmPK;

@Stateless
public class JpaComfirmDifferentRepository extends JpaRepository implements ComfirmDifferentRepository {

	private final String SELECT_DETAIL_DIFFERENT_YM = "SELECT d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode, "
			+ "i.itemAbName, i.qcamtItemPK.ctgAtr, d.value , h.makeMethodFlag, d.qstdtPaymentDetailPK.companyCode "
			+ "FROM QstdtPaymentDetail d " + "INNER JOIN  QstdtPaymentHeader h "
			+ "ON h.qstdtPaymentHeaderPK.companyCode = d.qstdtPaymentDetailPK.companyCode AND h.qstdtPaymentHeaderPK.personId = d.qstdtPaymentDetailPK.personId "
			+ "AND h.qstdtPaymentHeaderPK.processingNo = d.qstdtPaymentDetailPK.processingNo AND h.qstdtPaymentHeaderPK.payBonusAtr = d.qstdtPaymentDetailPK.payBonusAttribute "
			+ "AND h.qstdtPaymentHeaderPK.sparePayAtr = d.qstdtPaymentDetailPK.sparePayAttribute AND h.qstdtPaymentHeaderPK.processingYM = d.qstdtPaymentDetailPK.processingYM "
			+ "INNER JOIN QcamtItem_v1 i " + "ON h.qstdtPaymentHeaderPK.companyCode = i.qcamtItemPK.ccd "
			+ "AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd "
			+ "WHERE d.qstdtPaymentDetailPK.companyCode = :ccd AND d.qstdtPaymentDetailPK.personId IN :personId AND d.qstdtPaymentDetailPK.processingYM = :processYM "
			+ "AND d.qstdtPaymentDetailPK.categoryATR in (0,1,3) ORDER BY d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode";

	private final String SELECT_PAYCOMP_COMFIRM = "SELECT c FROM QlsdtPaycompConfirm as c WHERE "
			+ "c.paycompConfirmPK.companyCode = :companyCode AND c.paycompConfirmPK.personId IN :personId "
			+ "AND c.paycompConfirmPK.processYMEarly = :processYMEarly AND c.paycompConfirmPK.processYMLater = :processYMLater";


	@Override
	public List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, int processingYMEarlier,
			List<String> personIDs) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("personId", personIDs).setParameter("processYM", processingYMEarlier).getList(s -> {
					String employeeCode = s[0].toString();
					String employeeName = employeeCode.substring(0, 10);
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(Double.valueOf(s[4].toString()));
					BigDecimal comparisonValue2 = BigDecimal.valueOf(0);
					int registrationStatus1 = Integer.valueOf(s[5].toString());
					int registrationStatus2 = 0;
					String companyCd = s[6].toString();
					return convertToDomainDetailDifferential(companyCd, employeeCode, employeeName, itemCode, itemName,
							categoryAtr, comparisonValue1, comparisonValue2, new BigDecimal(0), "", registrationStatus1,
							registrationStatus2, 0);
				});
	}

	@Override
	public List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, int processingYMLater,
			List<String> personIDs) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("personId", personIDs).setParameter("processYM", processingYMLater).getList(s -> {
					String employeeCode = s[0].toString();
					String employeeName = employeeCode.substring(0, 10);
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(0);
					BigDecimal comparisonValue2 = BigDecimal.valueOf(Double.valueOf(s[4].toString()));
					int registrationStatus1 = 0;
					int registrationStatus2 = Integer.valueOf(s[5].toString());
					String companyCd = s[6].toString();
					return convertToDomainDetailDifferential(companyCd, employeeCode, employeeName, itemCode, itemName,
							categoryAtr, comparisonValue1, comparisonValue2, new BigDecimal(0), "", registrationStatus1,
							registrationStatus2, 0);
				});
	}

	@Override
	public List<PaycompConfirm> getPayCompComfirm(String companyCode, List<String> personIDs, int processYMEarly, int processYMLater) {
		return this.queryProxy().query(SELECT_PAYCOMP_COMFIRM, QlsdtPaycompConfirm.class)
				.setParameter("companyCode", companyCode).setParameter("personId", personIDs)
				.setParameter("processYMEarly", processYMEarly).setParameter("processYMLater", processYMLater)
				.getList(s -> convertToDomainPaycompConfirm(s));
	}
	

	@Override
	public void insertComparingPrintSet(PaycompConfirm paycompConfirm) {
		this.commandProxy().insert(convertToEntityPaycompConfirm(paycompConfirm));
	}

	@Override
	public void updateComparingPrintSet(PaycompConfirm paycompConfirm) {
		this.commandProxy().update(convertToEntityPaycompConfirm(paycompConfirm));
	}
	
	private static DetailDifferential convertToDomainDetailDifferential(String companyCode, String employeeCode,
			String employeeName, String itemCode, String itemName, int categoryAtr, BigDecimal comparisonValue1,
			BigDecimal comparisonValue2, BigDecimal valueDifference, String reasonDifference, int registrationStatus1,
			int registrationStatus2, int confirmedStatus) {

		return DetailDifferential.createFromJavaType(companyCode, employeeCode, employeeName, itemCode, itemName,
				categoryAtr, comparisonValue1, comparisonValue2, valueDifference, reasonDifference, registrationStatus1,
				registrationStatus2, confirmedStatus);
	}

	private static PaycompConfirm convertToDomainPaycompConfirm(QlsdtPaycompConfirm entity) {

		return PaycompConfirm.createFromJavaType(entity.paycompConfirmPK.companyCode, entity.paycompConfirmPK.personId,
				entity.paycompConfirmPK.itemCD, entity.paycompConfirmPK.processYMEarly,
				entity.paycompConfirmPK.processYMLater, entity.paycompConfirmPK.categoryAtr, entity.diffAmount,
				entity.diffReason, entity.confirmSTS);
	}

	private static QlsdtPaycompConfirm convertToEntityPaycompConfirm(PaycompConfirm domain) {
		QlsdtPaycompConfirm entity = new QlsdtPaycompConfirm();
		QlsdtPaycompConfirmPK entityPK = new QlsdtPaycompConfirmPK(domain.getCompanyCode(),
				domain.getEmployeeCode().v(), domain.getProcessingYMEarlier().v(), domain.getProcessingYMLater().v(),
				domain.getCategoryAtr().value, domain.getItemCode().v());
		entity.paycompConfirmPK = entityPK;
		entity.confirmSTS = domain.getConfirmedStatus().value;
		entity.diffAmount = domain.getValueDifference().v();
		entity.diffReason = domain.getReasonDifference().v();
		return entity;
	}

}
