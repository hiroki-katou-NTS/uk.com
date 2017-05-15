package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsdtPaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsdtPaycompConfirmPK;

@Stateless
public class JpaComfirmDifferentRepository extends JpaRepository implements ComfirmDifferentRepository {

	private final String SELECT_DETAIL_DIFFERENT_YM = "SELECT DISTINCT pc.scd, d.qstdtPaymentDetailPK.itemCode, "
			+ "i.itemAbName, i.qcamtItemPK.ctgAtr, d.value , h.makeMethodFlag, d.qstdtPaymentDetailPK.companyCode, p.nameOfficial, p.pid "
			+ "FROM QstdtPaymentDetail d LEFT JOIN  QstdtPaymentHeader h "
			+ "ON h.qstdtPaymentHeaderPK.companyCode = d.qstdtPaymentDetailPK.companyCode AND h.qstdtPaymentHeaderPK.personId = d.qstdtPaymentDetailPK.personId "
			+ "AND h.qstdtPaymentHeaderPK.processingNo = d.qstdtPaymentDetailPK.processingNo AND h.qstdtPaymentHeaderPK.payBonusAtr = d.qstdtPaymentDetailPK.payBonusAttribute "
			+ "AND h.qstdtPaymentHeaderPK.sparePayAtr = d.qstdtPaymentDetailPK.sparePayAttribute AND h.qstdtPaymentHeaderPK.processingYM = d.qstdtPaymentDetailPK.processingYM "
			+ "LEFT JOIN QcamtItem_v1 i ON h.qstdtPaymentHeaderPK.companyCode = i.qcamtItemPK.ccd "
			+ "AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd "
			+ "INNER JOIN PbsmtPersonBase p ON p.pid = d.qstdtPaymentDetailPK.personId "
			+ "INNER JOIN PcpmtPersonCom pc ON pc.pcpmtPersonComPK.pid = p.pid "
			+ "WHERE d.qstdtPaymentDetailPK.companyCode = :ccd AND pc.pid IN :personIDs AND d.qstdtPaymentDetailPK.processingYM = :processYM "
			+ "AND d.qstdtPaymentDetailPK.categoryATR in (0,1,3) ORDER BY p.scd, d.qstdtPaymentDetailPK.itemCode";

	private final String SELECT_PAYCOMP_COMFIRM = "SELECT c FROM QlsdtPaycompConfirm as c WHERE "
			+ "c.paycompConfirmPK.companyCode = :companyCode AND c.paycompConfirmPK.personId IN :personId "
			+ "AND c.paycompConfirmPK.processYMEarly = :processYMEarly AND c.paycompConfirmPK.processYMLater = :processYMLater";

	@Override
	public List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, int processingYMEarlier,
			List<String> personIDs) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("personIDs", personIDs).setParameter("processYM", processingYMEarlier).getList(s -> {
					String employeeCode = s[0].toString();
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					String strComparisonValue1 = s[4].toString();
					BigDecimal comparisonValue1 = new BigDecimal(-1);
					if (!StringUtils.isBlank(strComparisonValue1)) {
						comparisonValue1 = BigDecimal.valueOf(Double.valueOf(strComparisonValue1));
					}
					BigDecimal comparisonValue2 = BigDecimal.valueOf(0);
					String strRegistrationStatus1 = s[5].toString();
					int registrationStatus1 = -1;
					if(!StringUtils.isBlank(strRegistrationStatus1)){
						registrationStatus1 = Integer.valueOf(s[5].toString());
					}
					int registrationStatus2 = 0;
					String companyCd = s[6].toString();
					String employeeName = s[7].toString(); 
					String personId = s[8].toString();
					return convertToDomainDetailDifferential(companyCd, personId, employeeCode, employeeName, itemCode,
							itemName, categoryAtr, comparisonValue1, comparisonValue2, new BigDecimal(0), "",
							registrationStatus1, registrationStatus2, 0);
				});
	}

	@Override
	public List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, int processingYMLater,
			List<String> personIDs) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("personIDs", personIDs).setParameter("processYM", processingYMLater).getList(s -> {
					String employeeCode = s[0].toString();
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(0);
					String strcomparisonValue2 = s[4].toString();
					BigDecimal comparisonValue2 = new BigDecimal(-1);
					if (!StringUtils.isBlank(strcomparisonValue2)) {
						comparisonValue1 = BigDecimal.valueOf(Double.valueOf(strcomparisonValue2));
					}
					int registrationStatus1 = 0;
					int registrationStatus2 = -1;
					String strRegistrationStatus2 = s[5].toString();
					if(!StringUtils.isBlank(strRegistrationStatus2)){
						registrationStatus1 = Integer.valueOf(s[5].toString());
					}
					String companyCd = s[6].toString();
					String employeeName = s[7].toString();
					String personId = s[8].toString();
					return convertToDomainDetailDifferential(companyCd, personId, employeeCode, employeeName, itemCode,
							itemName, categoryAtr, comparisonValue1, comparisonValue2, new BigDecimal(0), "",
							registrationStatus1, registrationStatus2, 0);
				});
	}

	@Override
	public List<PaycompConfirm> getPayCompComfirm(String companyCode, List<String> personIDs, int processYMEarly,
			int processYMLater) {
		return this.queryProxy().query(SELECT_PAYCOMP_COMFIRM, QlsdtPaycompConfirm.class)
				.setParameter("companyCode", companyCode).setParameter("personId", personIDs)
				.setParameter("processYMEarly", processYMEarly).setParameter("processYMLater", processYMLater)
				.getList(s -> convertToDomainPaycompConfirm(s));
	}

	@Override
	public void insertComparingPrintSet(List<PaycompConfirm> paycompConfirmList) {
		List<QlsdtPaycompConfirm> qlsdtPaycompConfirmList = paycompConfirmList.stream()
				.map(pc -> convertToEntityPaycompConfirm(pc)).collect(Collectors.toList());
		this.commandProxy().insertAll(qlsdtPaycompConfirmList);
	}

	@Override
	public void updateComparingPrintSet(List<PaycompConfirm> paycompConfirmList) {
		List<QlsdtPaycompConfirm> qlsdtPaycompConfirmList = paycompConfirmList.stream()
				.map(pc -> convertToEntityPaycompConfirm(pc)).collect(Collectors.toList());
		this.commandProxy().updateAll(qlsdtPaycompConfirmList);
	}

	private static DetailDifferential convertToDomainDetailDifferential(String companyCode, String personID,
			String employeeCode, String employeeName, String itemCode, String itemName, int categoryAtr,
			BigDecimal comparisonValue1, BigDecimal comparisonValue2, BigDecimal valueDifference,
			String reasonDifference, int registrationStatus1, int registrationStatus2, int confirmedStatus) {

		return DetailDifferential.createFromJavaType(companyCode, personID, employeeCode, employeeName, itemCode,
				itemName, categoryAtr, comparisonValue1, comparisonValue2, valueDifference, reasonDifference,
				registrationStatus1, registrationStatus2, confirmedStatus);
	}

	private static PaycompConfirm convertToDomainPaycompConfirm(QlsdtPaycompConfirm entity) {

		return PaycompConfirm.createFromJavaType(entity.paycompConfirmPK.companyCode, entity.paycompConfirmPK.personId,
				entity.paycompConfirmPK.itemCD, entity.paycompConfirmPK.processYMEarly,
				entity.paycompConfirmPK.processYMLater, entity.paycompConfirmPK.categoryAtr, entity.diffAmount,
				entity.diffReason, entity.confirmSTS);
	}

	private static QlsdtPaycompConfirm convertToEntityPaycompConfirm(PaycompConfirm domain) {
		val entity = new QlsdtPaycompConfirm();
		val entityPK = new QlsdtPaycompConfirmPK(domain.getCompanyCode(), domain.getPersonID().v(),
				domain.getProcessingYMEarlier().v(), domain.getProcessingYMLater().v(), domain.getCategoryAtr().value,
				domain.getItemCode().v());
		entity.paycompConfirmPK = entityPK;
		entity.confirmSTS = domain.getConfirmedStatus().value;
		entity.diffAmount = domain.getValueDifference().v();
		entity.diffReason = domain.getReasonDifference().v();
		return entity;
	}

}
