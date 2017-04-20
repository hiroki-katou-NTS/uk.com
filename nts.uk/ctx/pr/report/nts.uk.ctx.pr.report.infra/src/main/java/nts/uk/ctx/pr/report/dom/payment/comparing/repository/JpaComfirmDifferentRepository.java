package nts.uk.ctx.pr.report.dom.payment.comparing.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ConfirmedStatus;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.DetailDifferential;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ValueDifference;

@Stateless
public class JpaComfirmDifferentRepository extends JpaRepository implements ComfirmDifferentRepository {

	private final String SELECT_DETAIL_DIFFERENT_YM = "SELECT d.paymentDetailPK.personId , d.paymentDetailPK.itemCD, i.itemName, i.qcamtItemPK.categoryAtr, "
			+ "d.valueComparing , h.makeMethodFLG, c.diffAmount ,c.diffReason, c.confirmSTS "
			+ "FROM QstdtPaymentDetail as d " + "INNER JOIN  QSTDT_PAYMENT_HEADER as h "
			+ "on h.paymentHeaderPK.companyCode = d.paymentDetailPK.companyCode AND  h.paymentHeaderPK.personId = d.paymentDetailPK.personId "
			+ "AND h.paymentHeaderPK.processNo = d.paymentDetailPK.processNo AND h.paymentHeaderPK.payBonusAtr = d.paymentDetailPK.payBonusAtr "
			+ "AND h.paymentHeaderPK.sparePayAtr =  d.paymentDetailPK.sparePayAtr AND h.paymentHeaderPK.processYM = d.paymentDetailPK.processYM "
			+ "INNER JOIN QCAMT_ITEM as i " + "ON h.paymentHeaderPK.companyCode = i.qcamtItemPK.companyCode "
			+ "AND d.paymentDetailPK.categoryAtr = i.qcamtItemPK.categoryAtr AND d.paymentDetailPK.itemCD = i.qcamtItemPK.itemCD "
			+ "LEFT JOIN QLSDT_PAYCOMP_CONFIRM as c "
			+ "ON c.paycompConfirmPK.companyCode = d.paymentDetailPK.companyCode "
			+ "AND c.paycompConfirmPK.personId = d.paymentDetailPK.personId AND c.paycompConfirmPK.itemCD = d.paymentDetailPK.itemCD "
			+ "AND c.paycompConfirmPK.categoryAtr = d.paymentDetailPK.categoryAtr AND c.paycompConfirmPK.processYM = d.paymentDetailPK.processYM "
			+ "WHERE d.paymentDetailPK.companyCode = :ccd AND d.paymentDetailPK.processYM = :processYM "
			+ "AND d.paymentDetailPK.categoryAtr in (0,1,3) ORDER BY  d.paymentDetailPK.personId, d.paymentDetailPK.itemCD";

	@Override
	public List<DetailDifferential> getDetailDifferential(String companyCode, int processingYMEarlier,
			int processingYMLater) {
		List<DetailDifferential> detailDifferential1 = getDetailDifferentialWithEarlyYM(companyCode,
				processingYMEarlier);
		List<DetailDifferential> detailDifferential2 = getDetailDifferentialWithLaterYM(companyCode, processingYMLater);

		List<DetailDifferential> detailDifferential  = detailDifferential1.stream().map(s -> {
			Optional<DetailDifferential> detalDiff = detailDifferential2.stream()
					.filter(f -> s.getEmployeeCode().equals(f.getEmployeeCode())
							&& s.getItemCode().equals(f.getItemCode()) && s.getCategoryAtr().equals(f.getCategoryAtr()))
					.findFirst();
			detalDiff.ifPresent(d -> {
				s.setComparisonValue2(d.getComparisonValue2());
				s.setRegistrationStatus2(d.getRegistrationStatus2());
				if (s.getConfirmedStatus().value == -1) {
					BigDecimal diff = s.getComparisonValue1().v().subtract(d.getComparisonValue2().v());
					s.setValueDifference(new ValueDifference(diff));
					s.setConfirmedStatus(EnumAdaptor.valueOf(0, ConfirmedStatus.class));
				}
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
					String employeeName = employeeCode;
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(Double.valueOf(s[4].toString()));
					BigDecimal comparisonValue2 = BigDecimal.valueOf(0);
					int registrationStatus1 = Integer.valueOf(s[5].toString());
					int registrationStatus2 = 0;
					String strDiffAmount = s[6].toString();
					BigDecimal valueDifference = BigDecimal.valueOf(0);
					if (!StringUtils.isBlank(strDiffAmount)) {
						valueDifference = BigDecimal.valueOf(Double.valueOf(strDiffAmount));
					}
					String reasonDifference = s[7].toString();
					int confirmedStatus = Integer.valueOf(-1);
					if (!StringUtils.isBlank(s[8].toString())) {
						confirmedStatus = Integer.valueOf(s[8].toString());
					}
					return convertToDomain(companyCode, employeeCode, employeeName, itemCode, itemName, categoryAtr,
							comparisonValue1, comparisonValue2, valueDifference, reasonDifference, registrationStatus1,
							registrationStatus2, confirmedStatus);
				});
	}

	private List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, int processingYMLater) {
		return this.queryProxy().query(SELECT_DETAIL_DIFFERENT_YM, Object[].class).setParameter("ccd", companyCode)
				.setParameter("processYM", processingYMLater).getList(s -> {
					String employeeCode = s[0].toString();
					String employeeName = employeeCode;
					String itemCode = s[1].toString();
					String itemName = s[2].toString();
					int categoryAtr = Integer.valueOf(s[3].toString());
					BigDecimal comparisonValue1 = BigDecimal.valueOf(0);
					BigDecimal comparisonValue2 = BigDecimal.valueOf(Double.valueOf(s[4].toString()));
					int registrationStatus1 = 0;
					int registrationStatus2 = Integer.valueOf(s[5].toString());
					String strDiffAmount = s[6].toString();
					BigDecimal valueDifference = BigDecimal.valueOf(0);
					if (!StringUtils.isBlank(strDiffAmount)) {
						valueDifference = BigDecimal.valueOf(Double.valueOf(strDiffAmount));
					}
					String reasonDifference = s[7].toString();
					int confirmedStatus = Integer.valueOf(-1);
					if (!StringUtils.isBlank(s[8].toString())) {
						confirmedStatus = Integer.valueOf(s[8].toString());
					}
					return convertToDomain(companyCode, employeeCode, employeeName, itemCode, itemName, categoryAtr,
							comparisonValue1, comparisonValue2, valueDifference, reasonDifference, registrationStatus1,
							registrationStatus2, confirmedStatus);
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
