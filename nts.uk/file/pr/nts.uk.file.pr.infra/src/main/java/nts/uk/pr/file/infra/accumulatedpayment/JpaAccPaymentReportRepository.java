/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.accumulatedpayment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.organization.employment.CmnmtEmp;
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.basic.infra.entity.report.QyedtYearendDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.personalinfo.employmentcontract.PclmtPersonEmpContract;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.processing.yearmonth.QpdmtPayday;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.file.pr.app.export.accumulatedpayment.AccPaymentRepository;
import nts.uk.file.pr.app.export.accumulatedpayment.data.AccPaymentItemData;
import nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery;



/**
 * The Class JpaAccPaymentReportRepository.
 */
@Stateless
public class JpaAccPaymentReportRepository extends JpaRepository implements AccPaymentRepository{

/** The Constant DATE_FORMAT. */
//	private static final String EMP_DESIGNATION = "社員";
	private static final String DATE_FORMAT = "yyyyMMdd";
	
	/** The Constant START_DATE. */
	private static final String START_DATE = "0101";
	
	/** The Constant END_DATE. */
	private static final String END_DATE = "1231";
	
	/** The Constant PAY_BONUS_ATR. */
	private static final int PAY_BONUS_ATR = 1;
	
	/** The Constant SPARE_PAY_ATR. */
	private static final int SPARE_PAY_ATR = 0;
	
	/** The Constant CTG_ATR. */
	private static final int CTG_ATR = 0;
	
	/** The Constant ITEM_CD_F001. */
	private static final String ITEM_CD_F001 = "F001";
	
	private static final Short REGULAR_COM = 0;
	
	/** The Constant QUERY_STRING. */
	private static final String QUERY_STRING = "SELECT ec.empCd, e.employmentName, pdt.qstdtPaymentDetailPK.categoryATR, "
			+ "pdt.qstdtPaymentDetailPK.itemCode, yd.qyedtYearendDetailPK.adjItemNo, pc.regularCom, pc.endD, a.endD, pdt.value,  yd.valNumber " 
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
			+ "AND pdt.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//1
			+ "AND pdt.qstdtPaymentDetailPK.processingYM = pd.qpdmtPaydayPK.processingYm "
			+ "AND pdt.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR "// 0
			+ "AND yd.qyedtYearendDetailPK.ccd = pdt.qstdtPaymentDetailPK.companyCode "
			+ "AND yd.qyedtYearendDetailPK.pid = p.pid "
			+ "AND yd.qyedtYearendDetailPK.yearK = :YEAR_k ";
	
	/** The Constant CHECK_AT_PRINTING_QUERY. */
	private static final String CHECK_AT_PRINTING_QUERY = "SELECT ec.pclmtPersonEmpContractPK.pId, sum(pdt.value)"
			+ "FROM PclmtPersonEmpContract ec, "
			+ "CmnmtEmp e, "
			+ "QpdmtPayday pd, "
			+ "QstdtPaymentDetail pdt "
			+ "WHERE ec.pclmtPersonEmpContractPK.pId in :PIDs "
			+ "AND ec.pclmtPersonEmpContractPK.ccd = :CCD "
			+ "AND ec.pclmtPersonEmpContractPK.strD <= :BASE_YMD "
			+ "AND ec.endD >= :BASE_YMD "
			+ "AND e.cmnmtEmpPk.companyCode = ec.pclmtPersonEmpContractPK.ccd "
			+ "AND e.cmnmtEmpPk.employmentCode = ec.empCd "
			+ "AND pd.qpdmtPaydayPK.ccd = e.cmnmtEmpPk.companyCode "
			+ "AND pd.qpdmtPaydayPK.payBonusAtr = :PAY_BONUS_ATR " //1
			+ "AND pd.qpdmtPaydayPK.processingNo = e.processingNo "
			+ "AND pd.payDate >= :STR_YMD "
			+ "AND pd.payDate <= :END_YMD "
			+ "AND pdt.qstdtPaymentDetailPK.companyCode = pd.qpdmtPaydayPK.ccd "
			+ "AND pdt.qstdtPaymentDetailPK.personId = p.pid "
			+ "AND pdt.qstdtPaymentDetailPK.processingNo = pd.qpdmtPaydayPK.processingNo "
			+ "AND pdt.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//1
			+ "AND pdt.qstdtPaymentDetailPK.processingYM = pd.qpdmtPaydayPK.processingYm "
			+ "AND pdt.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR "// 0
			+ "AND pdt.qstdtPaymentDetailPK.categoryATR = :CTG_ATR "//0
			+ "AND pdt.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F001 "//"F001"
			+ "GROUP BY ec.pclmtPersonEmpContractPK.pId "
			+ "HAVING SUM(pdt.value) >= :LOWER_LIMIT_VALUE"
			+ "AND SUM(pdt.value) <= :UPPER_LIMIT_VALUE";

	/**
	 * Check at printing.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 */
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qet002.AccPaymentRepository
	 * #getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qet002.query.AccPaymentReportQuery)
	 */
	@SuppressWarnings("unchecked")
	private void checkAtPrinting(String companyCode, AccPaymentReportQuery query) {
		EntityManager em = this.getEntityManager();
		
		// Create Year Month.
		String startDate = query.getTargetYear() + START_DATE;
		String endDate = query.getTargetYear() + END_DATE;
		GeneralDate strYMD = GeneralDate.fromString(startDate, DATE_FORMAT);
		GeneralDate endYMD = GeneralDate.fromString(endDate, DATE_FORMAT);
		
		Query typedQuery = em.createQuery(CHECK_AT_PRINTING_QUERY)
				.setParameter("CCD", companyCode)
				.setParameter("BASE_YMD", query.getBaseDate())
				.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR)
				.setParameter("STR_YMD", strYMD)
				.setParameter("END_YMD", endYMD)
				.setParameter("SPARE_PAY_ATR", SPARE_PAY_ATR)
				.setParameter("CTG_ATR", CTG_ATR)
				.setParameter("ITEM_CD_F001", ITEM_CD_F001)
				.setParameter("LOWER_LIMIT_VALUE", query.getLowerLimitValue())
				.setParameter("UPPER_LIMIT_VALUE", query.getUpperLimitValue());
		
		// Query data.
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getEmpIdList(), 1000, (subList) -> {
			resultList.addAll(typedQuery.setParameter("PIDs", subList).getResultList());
		});
		List<String> pIdList = new ArrayList<>();
		resultList.stream().forEach(record -> {
			pIdList.add((String)record[0]);
		});
		if(resultList.isEmpty()){
			// Throw Error message and stop
//			throw new RuntimeException("ER010");
		}else{
			// Check Conditions and Filter 
			this.getMasterResultList(pIdList, query);
		}
	}
	

//	private void filterByLimitsValue(List<Object[]> itemList, String companyCode, AccPaymentReportQuery query){
//		// Filter result list by lower limit and upper limit value.?..
//		Map<String, List<Object[]>> resultMap = new HashMap<>();
//		// Group by user.
//		Map<String, List<Object[]>> userMap = itemList.stream()
//				.collect(Collectors.groupingBy(item -> ((QstdtPaymentDetail) item[0]).qstdtPaymentDetailPK.personId));
//		for (String personId : userMap.keySet()) {
//			List<Object[]> detailData = userMap.get(personId);
//			
//			Double sumedValue = detailData.stream()
//					.mapToDouble(result -> {
//				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) result[0];
//				return paymentDetail.value.doubleValue();
//					})
//					.sum();
//			
//			if (sumedValue >= query.getLowerLimitValue().doubleValue() 
//					&& sumedValue <= query.getUpperLimitValue().doubleValue()){
//				resultMap.put(personId, detailData);
//			}
//		}
//		
//		if(resultMap.isEmpty()){
//			// Throw Error message and stop
////			throw new RuntimeException("ER010");
//		}else{
//			// TODO:
//			List<String> pIdList = new ArrayList<String>(resultMap.keySet());
//			this.getMasterResultList(pIdList, query);
//		}
//	}
	
		
//		Map<String, List<Object[]>> collect = userMap.entrySet().stream()
//				.filter(map -> {
//					for (String personId : map.keySet()) {
//						List<Object[]> detailData = userMap.get(personId);
//						
//						Double sumedValue = detailData.stream()
//								.mapToDouble(result -> {
//							QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) result[0];
//							return paymentDetail.value.doubleValue();
//								})
//								.sum();
//						
//						return sumedValue >= query.getLowerLimitValue().doubleValue() 
//								&& sumedValue <= query.getUpperLimitValue().doubleValue();
//					}
//				}).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue() ));
		
	private void filterData(List<Object[]> itemList, String companyCode, AccPaymentReportQuery query){
		// Group by EMP.
		Map<String, List<Object[]>> userMap = itemList.stream()
				.collect(Collectors.groupingBy(item -> ((PclmtPersonEmpContract) item[3]).empCd));
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
		String startDate = query.getTargetYear() + START_DATE;
		String endDate = query.getTargetYear() + END_DATE;
		GeneralDate strYMD = GeneralDate.fromString(startDate, DATE_FORMAT);
		GeneralDate endYMD = GeneralDate.fromString(endDate, DATE_FORMAT);

		Query typedQuery = em.createQuery(QUERY_STRING)
//				.setParameter("PIDs", pIdList)
				.setParameter("BASE_YMD", query.getBaseDate())
				.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR)
				.setParameter("STR_YMD", strYMD)
				.setParameter("END_YMD", endYMD)
				.setParameter("SPARE_PAY_ATR", SPARE_PAY_ATR)
				.setParameter("YEAR_k", query.getTargetYear())
				.setParameter("CTG_ATR", CTG_ATR)
				.setParameter("REGULAR_COM", REGULAR_COM);//REGULAR_COM
		

		CollectionUtil.split(pIdList, 1000, (subList) -> {
			masterResultList.addAll(typedQuery.setParameter("PIDs", subList).getResultList());
		});

		return masterResultList;
	}
	
	
	
//	private List<AccPaymentItemData> convertToMapItem(List<Object[]> objectList){
//		List<AccPaymentItemData> dataList = new ArrayList<>();
//		
////		objectList.stream().filter(obj ->);
//		for(Object obj[]: objectList){
//			PbsmtPersonBase p = (PbsmtPersonBase) obj[0];
//			PclmtPersonEmpContract ec = (PclmtPersonEmpContract) obj[1];
//			CmnmtEmp e = (CmnmtEmp) obj[2];
//			QpdmtPayday pd = (QpdmtPayday) obj[3];
//			QstdtPaymentDetail pdt = (QstdtPaymentDetail) obj[4];
//			QyedtYearendDetail yd = (QyedtYearendDetail) obj[5];
//			
//			
//			
////			AccPaymentItemData itemData = AccPaymentItemData.builder()
////					.empDesignation(EMP_DESIGNATION)
////					.empCode(ec.getEmpCd())
////					.empName(e.getEmploymentName())
////					.build();
////			
////			dataList.add(itemData);
//		}
//		return dataList;
//	}

	/* (non-Javadoc)
 * @see nts.uk.file.pr.app.export.accumulatedpayment.AccPaymentRepository#getItems(java.lang.String, nts.uk.file.pr.app.export.accumulatedpayment.query.AccPaymentReportQuery)
 */
@Override
	public List<AccPaymentItemData> getItems(String companyCode, AccPaymentReportQuery query) {
		// TODO Auto-generated method stub
		return null;
	}
}
