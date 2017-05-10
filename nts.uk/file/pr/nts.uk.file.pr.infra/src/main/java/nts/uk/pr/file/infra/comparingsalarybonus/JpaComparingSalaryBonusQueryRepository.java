package nts.uk.pr.file.infra.comparingsalarybonus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.entity.QlsptPaycompFormDetail;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusQueryRepository;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusHeaderReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.PaymentDetail;

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
			+ " WHERE d.qstdtPaymentDetailPK.companyCode = :ccd " + " AND d.qstdtPaymentDetailPK.personId IN :personId"
			+ " AND  d.qstdtPaymentDetailPK.payBonusAttribute = :payBonusAttribute"
			+ " AND  d.qstdtPaymentDetailPK.processingYM =:processingYM";
	private final String SELECT_CALLED_DETAIL = "SELECT c.emp, d.depName, d.hierarchyId, c.jobtitle, a.cName FROM  CmnmtCalled c"
			+ " INNER JOIN  CmnmtDep  d " + " ON c.ccd = d.cmnmtDepPK.companyCode "
			+ " INNER JOIN  CmnmtCompany a"
			+ " ON c.ccd = a.cmnmtCompanyPk.companyCd"
			+ " WHERE c.ccd =:ccd ";

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

	/**
	 * convert To Domain PaymentDetail from entity
	 * 
	 * @param entity
	 * @return PaymentDetail
	 */
	private static PaymentDetail toDomain(QstdtPaymentDetail entity) {
		String companyCode = entity.qstdtPaymentDetailPK.companyCode;
		String personId = entity.qstdtPaymentDetailPK.personId;
		int processingNo = entity.qstdtPaymentDetailPK.processingNo;
		int payBonusAtr = entity.qstdtPaymentDetailPK.payBonusAttribute;
		int processingYm = entity.qstdtPaymentDetailPK.processingYM;
		int sparePayAtr = entity.qstdtPaymentDetailPK.sparePayAttribute;
		int ctgAtr = entity.qstdtPaymentDetailPK.categoryATR;
		String itemCode = entity.qstdtPaymentDetailPK.itemCode;
		BigDecimal val = entity.value;
		int correctFlg = entity.correctFlag;
		int taxAtr = entity.taxATR;
		int limitMny = entity.limitAmount;
		int socialInsAtr = entity.socialInsurranceAttribute;
		int laborInsAtr = entity.laborSubjectAttribute;
		int fixPayAtr = entity.fixPayATR;
		int deductAtr = entity.deductAttribute;
		int itemAtr = entity.itemAtr;
		BigDecimal commuAllowTaxImpose = entity.commuteAllowTaxImpose;
		BigDecimal commuAllowMonth = entity.commuteAllowMonth;
		BigDecimal commuAllowFraction = entity.commuteAllowFraction;
		int printLinePos = entity.printLinePosition;
		int itemPosColumn = entity.columnPosition;

		return new PaymentDetail(companyCode, personId, processingNo, payBonusAtr, processingYm, sparePayAtr, ctgAtr,
				itemCode, val, correctFlg, taxAtr, limitMny, socialInsAtr, laborInsAtr, fixPayAtr, deductAtr, itemAtr,
				commuAllowTaxImpose, commuAllowMonth, commuAllowFraction, printLinePos, itemPosColumn);
	}

	/**
	 * convert To Domain DetailDifferential from entity
	 * 
	 * @param entity
	 * @return DetailDifferential
	 */
	private static ComparingSalaryBonusHeaderReportData convertToDomainComparingSalaryBonusHeaderReportData(
			Object[] headerReport) {
		String nameCompany = (String) headerReport[0];
		String titleReport = "";
		String nameDeparment = (String) headerReport[1];
		String typeDeparment = (String) headerReport[2];
		String postion = (String) headerReport[3];
		String targetYearMonth = "2016/02";
		String itemName = null;
		String month1 = null;
		String month2 = null;
		String differentSalary= null;
		String registrationStatus1 = null;
		String registrationStatus2= null;
		String reason= null;
		String confirmed = null;

		return new ComparingSalaryBonusHeaderReportData(nameCompany, titleReport, nameDeparment, typeDeparment, postion,
				targetYearMonth,itemName,month1,month2,differentSalary, registrationStatus1,registrationStatus2,reason,confirmed);
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
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("personId", personIDs).setParameter("categoryATR", categoryATR)
				.setParameter("payBonusAttribute", payBonusAttribute).setParameter("itemCode", itemCode)
				.setParameter("processingYM", processingEarlyYM).getList(s -> {
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
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("personId", personIDs).setParameter("categoryATR", categoryATR)
				.setParameter("payBonusAttribute", payBonusAttribute).setParameter("itemCode", itemCode)
				.setParameter("processingYM", processingLaterYM).getList(s -> {
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
	public List<PaymentDetail> getPaymentDetail(String companyCode, List<String> personIds, int payBonusAttr,
			int processingYm) {
		return this.queryProxy().query(SELECT_PAYMENT_DETAIL, QstdtPaymentDetail.class).setParameter("ccd", companyCode)
				.setParameter("personId", personIds).setParameter("payBonusAttribute", payBonusAttr)
				.setParameter("processingYM", processingYm).getList(x -> toDomain(x));
	}

	@Override
	public List<ComparingSalaryBonusHeaderReportData> getReportHeader(String companyCode) {
		return this.queryProxy().query(SELECT_CALLED_DETAIL, Object[].class).setParameter("ccd", companyCode)
				.getList(c -> convertToDomainComparingSalaryBonusHeaderReportData(c));
	}

}
