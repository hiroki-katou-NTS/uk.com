package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ConfirmedStatus;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ValueDifference;

@Stateless
public class JpaComfirmDifferentRepository extends JpaRepository implements ComfirmDifferentRepository {

	/*
	 * private final String SELECT_DETAIL_DIFFERENT_YM =
	 * "SELECT d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode, "
	 * +
	 * "i.itemAbName, i.qcamtItemPK.ctgAtr, d.value , h.makeMethodFlag, c.diffAmount, c.diffReason, c.confirmSTS, d.qstdtPaymentDetailPK.companyCode "
	 * + "FROM QstdtPaymentDetail d " + "INNER JOIN  QstdtPaymentHeader h " +
	 * "ON h.qstdtPaymentHeaderPK.companyCode = d.qstdtPaymentDetailPK.companyCode AND h.qstdtPaymentHeaderPK.personId = d.qstdtPaymentDetailPK.personId "
	 * +
	 * "AND h.qstdtPaymentHeaderPK.processingNo = d.qstdtPaymentDetailPK.processingNo AND h.qstdtPaymentHeaderPK.payBonusAtr = d.qstdtPaymentDetailPK.payBonusAttribute "
	 * +
	 * "AND h.qstdtPaymentHeaderPK.sparePayAtr = d.qstdtPaymentDetailPK.sparePayAttribute AND h.qstdtPaymentHeaderPK.processingYM = d.qstdtPaymentDetailPK.processingYM "
	 * + "INNER JOIN QcamtItem_v1 i " +
	 * "ON h.qstdtPaymentHeaderPK.companyCode = i.qcamtItemPK.ccd " +
	 * "AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd "
	 * + "LEFT JOIN QlsdtPaycompConfirm c " +
	 * "ON c.paycompConfirmPK.companyCode = d.qstdtPaymentDetailPK.companyCode "
	 * +
	 * "AND c.paycompConfirmPK.personId = d.qstdtPaymentDetailPK.personId AND c.paycompConfirmPK.itemCD = d.qstdtPaymentDetailPK.itemCode "
	 * +
	 * "AND c.paycompConfirmPK.categoryAtr = d.qstdtPaymentDetailPK.categoryATR AND c.paycompConfirmPK.processYM = d.qstdtPaymentDetailPK.processingYM "
	 * +
	 * "WHERE d.qstdtPaymentDetailPK.companyCode = :ccd AND d.qstdtPaymentDetailPK.processingYM = :processYM "
	 * +
	 * "AND d.qstdtPaymentDetailPK.categoryATR in (0,1,3) ORDER BY d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode"
	 * ;
	 */

	private final String SELECT_DETAIL_DIFFERENT_YM = "SELECT d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode, "
			+ "i.itemAbName, i.qcamtItemPK.ctgAtr, d.value , h.makeMethodFlag, d.qstdtPaymentDetailPK.companyCode "
			+ "FROM QstdtPaymentDetail d " + "INNER JOIN  QstdtPaymentHeader h "
			+ "ON h.qstdtPaymentHeaderPK.companyCode = d.qstdtPaymentDetailPK.companyCode AND h.qstdtPaymentHeaderPK.personId = d.qstdtPaymentDetailPK.personId "
			+ "AND h.qstdtPaymentHeaderPK.processingNo = d.qstdtPaymentDetailPK.processingNo AND h.qstdtPaymentHeaderPK.payBonusAtr = d.qstdtPaymentDetailPK.payBonusAttribute "
			+ "AND h.qstdtPaymentHeaderPK.sparePayAtr = d.qstdtPaymentDetailPK.sparePayAttribute AND h.qstdtPaymentHeaderPK.processingYM = d.qstdtPaymentDetailPK.processingYM "
			+ "INNER JOIN QcamtItem_v1 i " + "ON h.qstdtPaymentHeaderPK.companyCode = i.qcamtItemPK.ccd "
			+ "AND d.qstdtPaymentDetailPK.categoryATR = i.qcamtItemPK.ctgAtr AND d.qstdtPaymentDetailPK.itemCode = i.qcamtItemPK.itemCd "
			+ "WHERE d.qstdtPaymentDetailPK.companyCode = :ccd AND d.qstdtPaymentDetailPK.processingYM = :processYM "
			+ "AND d.qstdtPaymentDetailPK.categoryATR in (0,1,3) ORDER BY d.qstdtPaymentDetailPK.personId, d.qstdtPaymentDetailPK.itemCode";

	@Override
	public List<DetailDifferential> getDetailDifferential(String companyCode, int processingYMEarlier,
			int processingYMLater) {

		List<DetailDifferential> detailDifferential1 = getDetailDifferentialWithEarlyYM(companyCode,
				processingYMEarlier);
		List<DetailDifferential> detailDifferential2 = getDetailDifferentialWithLaterYM(companyCode, processingYMLater);

		List<DetailDifferential> detailDifferential = detailDifferential1.stream().map(s -> {
			Optional<DetailDifferential> detalDiff = detailDifferential2.stream()
					.filter(f -> s.getEmployeeCode().equals(f.getEmployeeCode())
							&& s.getItemCode().equals(f.getItemCode()) && s.getCategoryAtr().equals(f.getCategoryAtr()))
					.findFirst();
			detalDiff.ifPresent(d -> {
				s.setComparisonValue2(d.getComparisonValue2());
				s.setRegistrationStatus2(d.getRegistrationStatus2());
				/*if (s.getConfirmedStatus().value == -1) {
					BigDecimal diff = s.getComparisonValue1().v().subtract(d.getComparisonValue2().v());
					s.setValueDifference(new ValueDifference(diff));
					s.setConfirmedStatus(EnumAdaptor.valueOf(0, ConfirmedStatus.class));
				}*/
				
			});
			return s;
		}).collect(Collectors.toList());
		return detailDifferential;
	}

	@Override
	public void insertComparingPrintSet(PaycompConfirm paycompConfirm) {

	}

	@Override
	public void updateComparingPrintSet(PaycompConfirm paycompConfirm) {

	}

	private List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, int processingYMEarlier) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("processYM", processingYMEarlier).getList(s -> {
					String employeeCode = s[0].toString();
					String employeeName = employeeCode.substring(0,10);
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(Double.valueOf(s[4].toString()));
					BigDecimal comparisonValue2 = BigDecimal.valueOf(0);
					int registrationStatus1 = Integer.valueOf(s[5].toString());
					int registrationStatus2 = 0;
					String companyCd = s[6].toString();
					return convertToDomain(companyCd, employeeCode, employeeName, itemCode, itemName, categoryAtr,
							comparisonValue1, comparisonValue2, new BigDecimal(0), "", registrationStatus1,
							registrationStatus2, 0);
				});
	}

	private List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, int processingYMLater) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("processYM", processingYMLater).getList(s -> {
					String employeeCode = s[0].toString();
					String employeeName = employeeCode.substring(0,10);
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(0);
					BigDecimal comparisonValue2 = BigDecimal.valueOf(Double.valueOf(s[4].toString()));
					int registrationStatus1 = 0;
					int registrationStatus2 = Integer.valueOf(s[5].toString());
					String companyCd = s[6].toString();
					return convertToDomain(companyCd, employeeCode, employeeName, itemCode, itemName, categoryAtr,
							comparisonValue1, comparisonValue2, new BigDecimal(0), "", registrationStatus1,
							registrationStatus2, 0);
				});
	}

	private static DetailDifferential convertToDomain(String companyCode, String employeeCode, String employeeName,
			String itemCode, String itemName, int categoryAtr, BigDecimal comparisonValue1, BigDecimal comparisonValue2,
			BigDecimal valueDifference, String reasonDifference, int registrationStatus1, int registrationStatus2,
			int confirmedStatus) {

		return DetailDifferential.createFromJavaType(companyCode, employeeCode, employeeName, itemCode, itemName,
				categoryAtr, comparisonValue1, comparisonValue2, valueDifference, reasonDifference, registrationStatus1,
				registrationStatus2, confirmedStatus);
	}

}
