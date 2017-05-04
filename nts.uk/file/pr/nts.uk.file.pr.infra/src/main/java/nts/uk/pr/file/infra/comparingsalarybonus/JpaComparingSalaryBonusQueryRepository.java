package nts.uk.pr.file.infra.comparingsalarybonus;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusQueryRepository;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.PaymentDetail;
import nts.uk.pr.file.infra.paymentdata.result.DetailItemDto;

/**
 * The class JpaComparingSalaryBonusQueryRepository
 * 
 * @author lanlt
 *
 */
@Stateless
public class JpaComparingSalaryBonusQueryRepository extends JpaRepository
		implements ComparingSalaryBonusQueryRepository {
	// EA2
	private final String SELECT_DETAIL_DIFFERENT_YM = "SELECT d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode, "
			+ "i.itemAbName, i.qcamtItemPK.ctgAtr, d.value , h.makeMethodFlag, d.qstdtPaymentDetailPK.companyCode "
			+ " FROM QstdtPaymentDetail d " + "INNER JOIN  QstdtPaymentHeader h "
			+ " ON h.qstdtPaymentHeaderPK.companyCode = d.qstdtPaymentDetailPK.companyCode AND h.qstdtPaymentHeaderPK.personId = d.qstdtPaymentDetailPK.personId "
			+ " AND h.qstdtPaymentHeaderPK.processingNo = d.qstdtPaymentDetailPK.processingNo AND h.qstdtPaymentHeaderPK.payBonusAtr = d.qstdtPaymentDetailPK.payBonusAttribute "
			+ " AND h.qstdtPaymentHeaderPK.sparePayAtr = d.qstdtPaymentDetailPK.sparePayAttribute AND h.qstdtPaymentHeaderPK.processingYM = d.qstdtPaymentDetailPK.processingYM "
			+ " INNER JOIN QcamtItem_v1 i " + "ON h.qstdtPaymentHeaderPK.companyCode = i.qcamtItemPK.ccd "
			+ " AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd "
			+ " WHERE d.qstdtPaymentDetailPK.companyCode = :ccd AND d.qstdtPaymentDetailPK.personId IN :personId"
			+ " AND  d.qstdtPaymentDetailPK.categoryATR =:categoryATR "
			+ " AND  d.qstdtPaymentDetailPK.payBonusAttribute = :payBonusAttribute"
			+ " AND  d.qstdtPaymentDetailPK.processingYM =:processingYM"
			+ " AND  d.qstdtPaymentDetailPK.itemCode = :itemCode";

	private final String SELECT_PAYCOMP_FORM_DETAIL = "SELECT c FROM QlsptPaycompFormDetail as c WHERE "
			+ "c.paycompFormDetailPK.companyCode = :companyCode AND c.paycompFormDetailPK.formCode =:formCode";
	
	private final String SELECT_PAYMENT_DETAIL = "SELECT d FROM  QstdtPaymentDetail d"
			+ " WHERE d.qstdtPaymentDetailPK.companyCode = :ccd "
			+ " AND d.qstdtPaymentDetailPK.personId IN :personId"
			+ " AND  d.qstdtPaymentDetailPK.payBonusAttribute = :payBonusAttribute"
			+ " AND  d.qstdtPaymentDetailPK.processingYM =:processingYM";
	

	/**
	 * convert To Domain ComparingFormDetail from entity
	 * 
	 * @param entity
	 * @return ComparingFormDetail
	 */
	private static ComparingFormDetail convertToDomainComparingFormDetail(QlsptPaycompFormDetail entity) {
		return ComparingFormDetail.createFromJavaType(entity.paycompFormDetailPK.companyCode,
				entity.paycompFormDetailPK.formCode, entity.paycompFormDetailPK.categoryATR,
				entity.paycompFormDetailPK.itemCode, entity.dispOrder);
	}

	/**
	 * convert To Domain DetailDifferential from entity
	 * 
	 * @param entity
	 * @return DetailDifferential
	 */
	private static DetailDifferential convertToDomainDetailDifferential(String companyCode, String employeeCode,
			String employeeName, String itemCode, String itemName, int categoryAtr, BigDecimal comparisonValue1,
			BigDecimal comparisonValue2, BigDecimal valueDifference, String reasonDifference, int registrationStatus1,
			int registrationStatus2, int confirmedStatus) {

		return DetailDifferential.createFromJavaType(companyCode, employeeCode, employeeName, itemCode, itemName,
				categoryAtr, comparisonValue1, comparisonValue2, valueDifference, reasonDifference, registrationStatus1,
				registrationStatus2, confirmedStatus);
	}
	
	private PaymentDetail toDomain(Object[] paymentDetail) {
        String companyCode = (String)paymentDetail[0];
		String personId   = (String)paymentDetail[1];
		int processingNo = (int)paymentDetail[2];
		int payBonusAtr = (int)paymentDetail[3];
		int processingYm =(int)paymentDetail[4];
		int sparePayAtr = (int)paymentDetail[5];
		int ctgAtr= (int)paymentDetail[6];
		String itemCode =(String)paymentDetail[7];
		int val = (int)paymentDetail[8];
		int correctFlg = (int)paymentDetail[9];
		int taxAtr = (int)paymentDetail[10];
		int limitMny = (int)paymentDetail[11];
		int socialInsAtr = (int)paymentDetail[12];
		int laborInsAtr = (int)paymentDetail[13];
		int fixPayAtr = (int)paymentDetail[14];
		int deductAtr = (int)paymentDetail[15];
		int itemAtr = (int)paymentDetail[16];
		int commuAllowTaxImpose = (int)paymentDetail[17];
		int commuAllowMonth = (int)paymentDetail[18];
		int commuAllowFraction = (int)paymentDetail[19];
		int printLinePos = (int)paymentDetail[20];
		int itemPosColumn= (int)paymentDetail[21];
		
		return new PaymentDetail(companyCode, personId, processingNo, 
				payBonusAtr, processingYm, sparePayAtr, ctgAtr, 
				itemCode, val, correctFlg, taxAtr, limitMny, 
				socialInsAtr, laborInsAtr, fixPayAtr, deductAtr,
				itemAtr, commuAllowTaxImpose, commuAllowMonth, 
				commuAllowFraction, printLinePos, itemPosColumn);
	}

	/**
	 * SEL_1 from QlsptPaycompFormDetail
	 */
	@Override
	public List<ComparingFormDetail> getPayComDetailByFormCode(String companyCode, String formCode) {
		return this.queryProxy().query(SELECT_PAYCOMP_FORM_DETAIL, QlsptPaycompFormDetail.class)
				.setParameter("companyCode", companyCode).setParameter("formCode", formCode)
				.getList(s -> convertToDomainComparingFormDetail(s));
	}

	@Override
	public List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, List<String> personIDs,
			int processingEarlyYM, int categoryATR, int payBonusAttribute, String itemCode) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class)
				.setParameter("ccd", companyCode)
				.setParameter("personId", personIDs)
				.setParameter("categoryATR", categoryATR)
				.setParameter("payBonusAttribute", payBonusAttribute)
				.setParameter("itemCode", itemCode)
				.setParameter("processingYM", processingEarlyYM)
				.getList(s -> {
					String employeeCode = s[0].toString();
					String employeeName = employeeCode.substring(0, 10);
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
	public List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, List<String> personIDs,
			int processingLaterYM, int categoryATR, int payBonusAttribute, String itemCode) {	
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class)
					.setParameter("ccd", companyCode)
					.setParameter("personId", personIDs)
					.setParameter("categoryATR", categoryATR)
					.setParameter("payBonusAttribute", payBonusAttribute)
					.setParameter("itemCode", itemCode)
					.setParameter("processingYM", processingLaterYM)
					.getList(s -> {
						String employeeCode = s[0].toString();
						String employeeName = employeeCode.substring(0, 10);
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
	public List<PaymentDetail> getPaymentDetail(String companyCode, List<String> personIds, int payBonusAttr, int processingYm) {
		return this.queryProxy().query(SELECT_PAYMENT_DETAIL, Object[].class)
				.setParameter("companyCode", companyCode)
				.setParameter("personId", personIds)
				.setParameter("payBonusAttribute", payBonusAttr)
				.setParameter("processingYM", processingYm)
				.getList(x ->toDomain(x));
	}

}
