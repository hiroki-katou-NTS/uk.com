/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.accumulatedpayment;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.organization.employment.CmnmtEmp;
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.basic.infra.entity.report.PcpmtPersonCom;
import nts.uk.ctx.basic.infra.entity.report.PcpmtPersonTempAssign;
import nts.uk.ctx.basic.infra.entity.report.QyedtYearendDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.personalinfo.employmentcontract.PclmtPersonEmpContract;
import nts.uk.file.pr.app.export.accumulatedpayment.AccPaymentRepository;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery;

/**
 * The Class JpaAccPaymentReportRepository.
 */
@Stateless
public class JpaAccPaymentReportRepository extends JpaRepository implements AccPaymentRepository{

/** The Constant DATE_FORMAT. */

	private static final String DATE_FORMAT = "yyyyMMdd";
	
	/** The Constant START_DATE_OF_YEAR. */
	private static final String START_DATE_OF_YEAR = "0101";
	
	/** The Constant END_DATE_OF_YEAR. */
	private static final String END_DATE_OF_YEAR = "1231";
	
	/** The Constant PAY_BONUS_ATR. */
	private static final int PAY_BONUS_ATR = 1;
	
	/** The Constant SPARE_PAY_ATR. */
	private static final int SPARE_PAY_ATR = 0;
	
	/** The Constant PAYMENT_CATEGORY. */
	private static final int PAYMENT_CATEGORY = 0;
	
	/** The Constant DEDUCTION_CATEGORY. */
	private static final int DEDUCTION_CATEGORY = 1;
	
	/** The Constant ITEM_CD_F001. */
	private static final String ITEM_CD_F001 = "F001";
	
	/** The Constant ITEM_CD_F005. */
	private static final String ITEM_CD_F005 = "F005";
	
	/** The Constant ITEM_CD_F007. */
	private static final String ITEM_CD_F007 = "F007";
	
	/** The Constant REGULAR_COM. */
	private static final Short REGULAR_COM = 0;// -LEAD
	
	/** The Constant YEAR_ADJUSTMENT_ITEM_046. */
	private static final int YEAR_ADJUSTMENT_ITEM_046 = 46;
	
	/** The Constant YEAR_ADJUSTMENT_ITEM_047. */
	private static final int YEAR_ADJUSTMENT_ITEM_047 = 47;
	
	/** The Constant YEAR_ADJUSTMENT_ITEM_048. */
	private static final int YEAR_ADJUSTMENT_ITEM_048 = 48;
	
	/** The Constant YEAR_ADJUSTMENT_ITEM_049. */
	private static final int YEAR_ADJUSTMENT_ITEM_049 = 49;
	
	/** The Constant YEAR_ADJUSTMENT_ITEM_050. */
	private static final int YEAR_ADJUSTMENT_ITEM_050 = 50;
	
	/** The Constant YEAR_ADJUSTMENT_ITEM_051. */
	private static final int YEAR_ADJUSTMENT_ITEM_051 = 51;
	
	/** The Constant RETIRED. */
	private static final String RETIRED = "退職者";
	
	/** The Constant ENROLMENT. */
	private static final String ENROLMENT = "在籍者";
	
	/** The Constant SECONDMENT. */
	private static final String SECONDMENT = "出向中";// on loan
	
	/** The Constant UNDELIVERED. */
	private static final String UNDELIVERED = "未出向";
	
	/** The Constant QUERY_STRING. */
	private static final String QUERY_STRING = "p, a, pc, ec, e, pd, pdt, yd " 
			+ "FROM PbsmtPersonBase p, "
			+ "PcpmtPersonTempAssign a, "//To get the loan information of individuals who meet the conditions
			+ "PcpmtPersonCom pc, "
			+ "PclmtPersonEmpContract ec, "
			+ "CmnmtEmp e, "
			+ "QpdmtPayday pd, "
			+ "QstdtPaymentDetail pdt, "
			+ "QyedtYearendDetail yd "
			+ "WHERE p.pid in :PIDs "
			+ "AND a.pcpmtPersonTempAssignPK.ccd = :CCD "
			+ "AND a.pcpmtPersonTempAssignPK.pid = p.pid "
			+ "AND pc.pcpmtPersonComPK.ccd = :CCD "
			+ "AND pc.pcpmtPersonComPK.pid = p.pid "
			+ "AND pc.regularCom = :REGULAR_COM "//0
			+ "AND ec.pclmtPersonEmpContractPK.ccd = :CCD "
			+ "AND ec.pclmtPersonEmpContractPK.pId = pc.pcpmtPersonComPK.pid "
			+ "AND ec.pclmtPersonEmpContractPK.strD <= :BASE_YMD "
			+ "AND ec.endD >= :BASE_YMD "
			+ "AND e.cmnmtEmpPk.companyCode = ec.pclmtPersonEmpContractPK.ccd "
			+ "AND e.cmnmtEmpPk.employmentCode = ec.empCd "
			+ "AND pd.qpdmtPaydayPK.ccd = :CCD "
			+ "AND pd.qpdmtPaydayPK.payBonusAtr = :PAY_BONUS_ATR " //1
			+ "AND pd.qpdmtPaydayPK.processingNo = e.processingNo "
			+ "AND pd.payDate >= :STR_YMD "
			+ "AND pd.payDate <= :END_YMD "
			+ "AND pdt.qstdtPaymentDetailPK.companyCode = pd.qpdmtPaydayPK.ccd "
			+ "AND pdt.qstdtPaymentDetailPK.personId = p.pid "
			+ "AND pdt.qstdtPaymentDetailPK.processingNo = pd.qpdmtPaydayPK.processingNo "
//			+ "AND pdt.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//1
//			+ "AND pdt.qstdtPaymentDetailPK.processingYM = pd.qpdmtPaydayPK.processingYm "
			+ "AND pdt.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR ";// 0
//			+ "AND yd.qyedtYearendDetailPK.ccd = pdt.qstdtPaymentDetailPK.companyCode "
//			+ "AND yd.qyedtYearendDetailPK.pid = p.pid "
//			+ "AND yd.qyedtYearendDetailPK.yearK = :YEAR_k ";
	

	/** The Constant CHECK_AT_PRINTING_QUERY. */
	private static final String CHECK_AT_PRINTING_QUERY = "SELECT pdt.qstdtPaymentDetailPK.personId, sum(pdt.value) "
				+ "FROM QstdtPaymentDetail pdt,"
				+ "CmnmtEmp e, "
				+ "QpdmtPayday pd, "
				+ "PclmtPersonEmpContract ec "
				+ "WHERE ec.pclmtPersonEmpContractPK.pId in :PIDs "
				+ "AND ec.pclmtPersonEmpContractPK.ccd = :CCD "
				+ "AND ec.pclmtPersonEmpContractPK.strD <= :BASE_YMD "
				+ "AND ec.endD >= :BASE_YMD "
				+ "AND e.cmnmtEmpPk.companyCode = ec.pclmtPersonEmpContractPK.ccd "
				+ "AND e.cmnmtEmpPk.employmentCode = ec.empCd "
				+ "AND pd.qpdmtPaydayPK.ccd = e.cmnmtEmpPk.companyCode "
//				+ "AND pd.qpdmtPaydayPK.payBonusAtr = :PAY_BONUS_ATR " //1
//				+ "AND pd.qpdmtPaydayPK.processingNo = e.processingNo "
				+ "AND pd.payDate >= :STR_YMD "
				+ "AND pd.payDate <= :END_YMD "
				+ "AND pdt.qstdtPaymentDetailPK.companyCode = pd.qpdmtPaydayPK.ccd "
				+ "AND pdt.qstdtPaymentDetailPK.personId = ec.pclmtPersonEmpContractPK.pId "
				+ "AND pdt.qstdtPaymentDetailPK.processingNo = pd.qpdmtPaydayPK.processingNo "
				+ "AND pdt.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//1
				+ "AND pdt.qstdtPaymentDetailPK.processingYM = pd.qpdmtPaydayPK.processingYm "
				+ "AND pdt.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR "// 0
				+ "AND pdt.qstdtPaymentDetailPK.categoryATR = :CTG_ATR_0 "//0
				+ "AND pdt.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F001 "//"F001"
				+ "GROUP BY pdt.qstdtPaymentDetailPK.personId ";
//				+ "HAVING SUM(pdt.value) >= :LOWER_LIMIT_VALUE "
//				+ "AND SUM(pdt.value) <= :UPPER_LIMIT_VALUE ";

	/**
	 * Filter data.
	 *
	 * @param itemList the item list
	 * @param companyCode the company code
	 * @param query the query
	 * @return the list
	 */
	private List<AccPaymentItemData> filterData(List<Object[]> itemList, String companyCode, AccPaymentReportQuery query) {
		List<AccPaymentItemData> resultDataList = new ArrayList<>();
		// Group by EMP.
		Map<String, List<Object[]>> userMap = itemList.stream()
				.collect(Collectors.groupingBy(item -> ((PbsmtPersonBase) item[0]).getPid()));
		for (String pId : userMap.keySet()) {
			List<Object[]> detailData = userMap.get(pId);
			// Category_Attribute = 0 and Item_code = F001
			// Taxable Amount
			Double taxAmount = this.sumValues(detailData, PAYMENT_CATEGORY, ITEM_CD_F001, YEAR_ADJUSTMENT_ITEM_046,
					YEAR_ADJUSTMENT_ITEM_049);

			// Social Insurance Total Amount
			Double socialInsAmount = this.sumValues(detailData, DEDUCTION_CATEGORY, ITEM_CD_F005,
					YEAR_ADJUSTMENT_ITEM_048, YEAR_ADJUSTMENT_ITEM_051);

			// Withholding tax amount
			Double withHoldingTax = this.sumValues(detailData, DEDUCTION_CATEGORY, ITEM_CD_F007,
					YEAR_ADJUSTMENT_ITEM_047, YEAR_ADJUSTMENT_ITEM_050);

			String empCode = ((PclmtPersonEmpContract) detailData.get(0)[3]).empCd;
			String empName = ((CmnmtEmp) detailData.get(0)[4]).employmentName;
			GeneralDate endDatePersonTem = ((PcpmtPersonCom) detailData.get(0)[2]).getEndD();
			String enrollmentStatus = endDatePersonTem.year() < query.getTargetYear() ? RETIRED : ENROLMENT;

			GeneralDate endDatePersonTempAsign = ((PcpmtPersonTempAssign) detailData.get(0)[1]).getEndD();
			String directionalStatus = endDatePersonTempAsign.year() < query.getTargetYear() ? SECONDMENT : UNDELIVERED;
			// AccPaymentItemData
			AccPaymentItemData itemData = AccPaymentItemData.builder()
					.taxAmount(taxAmount)
					.empCode(empCode)
					.empName(empName)
					.taxAmount(taxAmount)
					.socialInsuranceAmount(socialInsAmount)
					.widthHoldingTaxAmount(withHoldingTax)
					.directionalStatus(directionalStatus)
					.enrollmentStatus(enrollmentStatus)
					.build();
			resultDataList.add(itemData);
		}
		return resultDataList;
	}
	
	/**
	 * Sum values.
	 *
	 * @param detailData the detail data
	 * @param category the category
	 * @param itemCode the item code
	 * @param yearAdj1 the year adj 1
	 * @param yearAdj2 the year adj 2
	 * @return the double
	 */
	private Double sumValues(List<Object[]> detailData, int category, String itemCode, int yearAdj1, int yearAdj2){
		Double amount = 0.0;
		Double sumOfF001 = detailData.stream().filter(data -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) data[6];
			return pdt.qstdtPaymentDetailPK.categoryATR == category && pdt.qstdtPaymentDetailPK.itemCode == itemCode;
		}).mapToDouble(result -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) result[6];
			return pdt.value.doubleValue();
		}).sum();

		// Value Number of Year Adjustment
		Double valueNoTaxAmount = detailData.stream().filter(data -> {
			QyedtYearendDetail yd = (QyedtYearendDetail) data[7];
			return yd.getQyedtYearendDetailPK().getAdjItemNo() == yearAdj1
					|| yd.getQyedtYearendDetailPK().getAdjItemNo() == yearAdj2;
		}).mapToDouble(result -> {
			QyedtYearendDetail yd = (QyedtYearendDetail) result[7];
			return yd.getValNumber().doubleValue();
		}).sum();
		// sum above values
		amount = sumOfF001 + valueNoTaxAmount;
		return amount;
	}
	
	/**
	 * Gets the master result list.
	 *
	 * @param pIdList the id list
	 * @param query the query
	 * @return the master result list
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getMasterResultList(List<String> pIdList, AccPaymentReportQuery query) {
		EntityManager em = this.getEntityManager();
		List<Object[]> masterResultList = new ArrayList<>();
		
		// Create Year Month.
		String startDate = query.getTargetYear() + START_DATE_OF_YEAR;
		String endDate = query.getTargetYear() + END_DATE_OF_YEAR;
		GeneralDate strYMD = GeneralDate.fromString(startDate, DATE_FORMAT);
		GeneralDate endYMD = GeneralDate.fromString(endDate, DATE_FORMAT);

		Query typedQuery = em.createQuery(QUERY_STRING)
				.setParameter("BASE_YMD", query.getBaseDate())
				.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR)
				.setParameter("STR_YMD", strYMD)
				.setParameter("END_YMD", endYMD)
				.setParameter("SPARE_PAY_ATR", SPARE_PAY_ATR)
				.setParameter("YEAR_k", query.getTargetYear())
				.setParameter("REGULAR_COM", REGULAR_COM);//REGULAR_COM

		CollectionUtil.split(pIdList, 1000, (subList) -> {
			masterResultList.addAll(typedQuery.setParameter("PIDs", subList).getResultList());
		});

		return masterResultList;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.accumulatedpayment.AccPaymentRepository#
	 * getItems(java.lang.String,
	 * nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AccPaymentItemData> getItems(String companyCode, AccPaymentReportQuery query) {
		// Check Having Conditions
		String havingQuery = "";
		List<String> havingCondition = new ArrayList<>();
		if(query.getIsLowerLimit()){
			havingCondition.add("SUM(pdt.value) >= :LOWER_LIMIT_VALUE");
		}
		if (query.getIsUpperLimit()) {
			havingCondition.add("SUM(pdt.value) <= :UPPER_LIMIT_VALUE");
		}
		if (!CollectionUtil.isEmpty(havingCondition)) {
			havingQuery += " Having " + havingCondition.stream().collect(Collectors.joining(" AND "));
		}
		
		List<AccPaymentItemData> filtedData = new ArrayList<>();
		EntityManager em = this.getEntityManager();
		List<Object[]> masterResultList = new ArrayList<>();
		// Create Year Month.
		String startDate = query.getTargetYear() + START_DATE_OF_YEAR;
		String endDate = query.getTargetYear() + END_DATE_OF_YEAR;
		GeneralDate strYMD = GeneralDate.fromString(startDate, DATE_FORMAT);
		GeneralDate endYMD = GeneralDate.fromString(endDate, DATE_FORMAT);
		
		Query typedQuery = em.createQuery(CHECK_AT_PRINTING_QUERY + havingQuery)
				.setParameter("CCD", companyCode)
				.setParameter("BASE_YMD", query.getBaseDate())
				.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR)
				.setParameter("STR_YMD", strYMD)
				.setParameter("END_YMD", endYMD)
				.setParameter("SPARE_PAY_ATR", SPARE_PAY_ATR)
				.setParameter("CTG_ATR_0", PAYMENT_CATEGORY)
				.setParameter("ITEM_CD_F001", ITEM_CD_F001);
		
		if (query.getIsLowerLimit()) {
			typedQuery.setParameter("LOWER_LIMIT_VALUE", query.getLowerLimitValue());
		}
		if (query.getIsUpperLimit()) {
			typedQuery.setParameter("UPPER_LIMIT_VALUE", query.getUpperLimitValue());
		}
		
		// 
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getEmpIdList(), 1000, (subList) -> {
			resultList.addAll(typedQuery.setParameter("PIDs", subList).getResultList());
		});
		List<String> pIdList = new ArrayList<>();
		resultList.stream().forEach(record -> {
			pIdList.add((String) record[0]);
		});
		if (resultList.isEmpty()) {
			// Throw Error message and stop
			throw new BusinessException("ER010");
		} else {
			// Get Master Result List
			masterResultList = this.getMasterResultList(pIdList, query);
			if (masterResultList.isEmpty()) {
				// Throw Error message and stop
				throw new BusinessException("ER010");
			}
		}
		filtedData = this.filterData(masterResultList, companyCode, query);
		return filtedData;
	}
}
