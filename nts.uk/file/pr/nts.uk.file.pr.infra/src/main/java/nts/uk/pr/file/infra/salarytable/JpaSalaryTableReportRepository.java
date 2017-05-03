/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.salarytable;

import java.util.ArrayList;
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
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.basic.infra.entity.system.bank.personaccount.PbamtPersonBankAccount;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.file.pr.app.export.salarytable.SalaryTableRepository;
import nts.uk.file.pr.app.export.salarytable.data.EmployeeData;
import nts.uk.file.pr.app.export.salarytable.query.SalaryTableReportQuery;



/**
 * The Class JpaSalaryChartReportRepository.
 */
@Stateless
public class JpaSalaryTableReportRepository extends JpaRepository implements SalaryTableRepository {
	private static final int PAY_BONUS_ATR = 0;
	private static final int CTR_ATR_CHECK = 4;
	private static final int CTR_ATR_PRINT = 3;
	private static final int ONE_THOUSAND = 1000;
	private static final int VALUE_0 = 0;
	private static final int SPARE_PAY_ATR = 0;
	private static final String ITEM_CD_F304 = "F304";
	private static final String ITEM_CD_F305 = "F305";
	private static final String ITEM_CD_F306 = "F306";
	private static final String ITEM_CD_F307 = "F307";
	private static final String ITEM_CD_F308 = "F308";
	private static final int PAYMENT_DETAIL_TBL_INDEX = 5;
	private static final int PERSON_BANK_ACC_TBL_INDEX = 4;
	private static final int DEP_TBL_INDEX = 3;
	private static final int DEP_REGL_TBL_INDEX = 2;
	private static final int PERSON_COM_TBL_INDEX = 1;
	private static final int PERSON_BASE_TBL_INDEX = 0;
	private static final int ONE = 1;
	
//	private static final String CHECK_AT_PRINTING_QUERY = "SELECT h, ba, pd "
//			+ "FROM QstdtPaymentHeader h "
//			+ "LEFT JOIN PbamtPersonBankAccount ba "
//			+ "ON h.qstdtPaymentHeaderPK.companyCode = :CCD "
//			+ "AND h.qstdtPaymentHeaderPK.personId IN :PIDs "
//			+ "AND h.qstdtPaymentHeaderPK.processingYM = :ProcessingYM "
//			+ "AND h.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR "//0
//			+ "AND ba.pbamtPersonBankAccountPK.companyCode = h.qstdtPaymentHeaderPK.companyCode "
//			+ "AND ba.pbamtPersonBankAccountPK.personId = h.qstdtPaymentHeaderPK.personId "
//			+ "LEFT JOIN QstdtPaymentDetail pd "
//			+ "ON pd.qstdtPaymentDetailPK.companyCode = h.qstdtPaymentHeaderPK.companyCode "
//			+ "AND pd.qstdtPaymentDetailPK.personId = h.qstdtPaymentHeaderPK.personId "
//			+ "AND pd.qstdtPaymentDetailPK.processingYM = h.qstdtPaymentHeaderPK.processingYM "
//			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//0
//			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR "//4
//			+ "AND pd.value != :VALUE ";//0
			
	private static final String PAYMENT_HEADER_QUERY = "SELECT h "
			+ "FROM QstdtPaymentHeader h "
			+ "WHERE h.qstdtPaymentHeaderPK.companyCode = :CCD "
			+ "AND h.qstdtPaymentHeaderPK.personId in :PIDs "
			+ "AND h.qstdtPaymentHeaderPK.processingYM = :ProcessingYM "
			+ "AND h.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR ";//0
//	private static final String PERSON_BANK_ACC_QUERY = "SELECT ba "
//			+ "FROM PbamtPersonBankAccount ba "
//			+ "WHERE ba.pbamtPersonBankAccountPK.companyCode = :CCD "
//			+ "AND ba.pbamtPersonBankAccountPK.personId IN :PIDs "
//			+ "AND ((ba.useSet1 = :ONE "
//			+ "AND ba.paymentMethod1 = :ONE) "
//			+ "OR (ba.useSet2 = :ONE "
//			+ "AND ba.paymentMethod2 = :ONE) "
//			+ "OR (ba.useSet3 = :ONE "
//			+ "AND ba.paymentMethod3 = :ONE) "
//			+ "OR (ba.useSet4 = :ONE "
//			+ "AND ba.paymentMethod4 = :ONE) "
//			+ "OR (ba.useSet5 = :ONE "
//			+ "AND ba.paymentMethod5 = :ONE) ";
//	private static final String PAYMENT_DETAIL_QUERY = "SELECT pd "
//			+ "FROM QstdtPaymentDetail pd "
//			+ "WHERE pd.qstdtPaymentDetailPK.companyCode = :CCD "
//			+ "AND pd.qstdtPaymentDetailPK.personId in :PIDs "
//			+ "AND pd.qstdtPaymentDetailPK.processingYM = :ProcessingYM "
//			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//0
//			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR "//4
//			+ "AND pd.value != :VALUE "
//			+ "";//0
	private static final String BANK_ACC_JOIN_PAYMENT_DETAIL = "SELECT ba, pd "
			+ "FROM PbamtPersonBankAccount ba "
			+ "JOIN  QstdtPaymentDetail pd "
			+ "ON ba.pbamtPersonBankAccountPK.companyCode = :CCD "
			+ "AND ba.pbamtPersonBankAccountPK.personId IN :PIDs "
			+ "AND ((ba.useSet1 = :ONE "
			+ "AND ba.paymentMethod1 = :ONE) "
			+ "OR (ba.useSet2 = :ONE "
			+ "AND ba.paymentMethod2 = :ONE) "
			+ "OR (ba.useSet3 = :ONE "
			+ "AND ba.paymentMethod3 = :ONE) "
			+ "OR (ba.useSet4 = :ONE "
			+ "AND ba.paymentMethod4 = :ONE) "
			+ "OR (ba.useSet5 = :ONE "
			+ "AND ba.paymentMethod5 = :ONE)) "
			+ "AND pd.qstdtPaymentDetailPK.companyCode = ba.pbamtPersonBankAccountPK.companyCode "
			+ "AND pd.qstdtPaymentDetailPK.personId = ba.pbamtPersonBankAccountPK.personId "
			+ "AND pd.qstdtPaymentDetailPK.processingYM = :ProcessingYM "
			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//0
			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR "//4
			+ "AND pd.value != :VALUE "
			+ "AND ((ba.useSet1 = :ONE AND ba.partialPaySet1 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F304) "
			+ "OR (ba.useSet2 = :ONE AND ba.partialPaySet2 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F305) "
			+ "OR (ba.useSet3 = :ONE AND ba.partialPaySet3 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F306) "
			+ "OR (ba.useSet4 = :ONE AND ba.partialPaySet4 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F307) "
			+ "OR (ba.useSet5 = :ONE AND ba.partialPaySet5 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F308)) "
			+ "";
	
	private static final String QUERY_STRING = "SELECT pb, pc, pdr, d, ba, pd "
			+ "FROM PbsmtPersonBase pb "
			+ "LEFT JOIN PcpmtPersonCom pc "
			+ "ON pb.pid IN :PIDs "
			+ "AND pc.pcpmtPersonComPK.ccd = :CCD "
			+ "AND pc.pcpmtPersonComPK.pid = pb.pid "
			+ "LEFT JOIN PogmtPersonDepRgl pdr "
			+ "ON pdr.pogmtPersonDepRglPK.ccd = :CCD "
			+ "AND pdr.pogmtPersonDepRglPK.pid = pb.pid "
			+ "AND pdr.strD >= :BASE_YMD "
			+ "AND pdr.endD <= :BASE_YMD "
			+ "LEFT JOIN CmnmtDep d "
			+ "ON d.cmnmtDepPK.companyCode = pc.pcpmtPersonComPK.ccd "
			+ "AND d.startDate >= :BASE_YMD "
			+ "AND d.endDate <= :BASE_YMD "
			+ "AND d.cmnmtDepPK.departmentCode = pdr.depcd "
			+ "LEFT JOIN PbamtPersonBankAccount ba "
			+ "ON ba.pbamtPersonBankAccountPK.companyCode = d.cmnmtDepPK.companyCode "
			+ "AND ba.pbamtPersonBankAccountPK.personId = pb.pid "
			+ "AND ba.startYearMonth >= :BASE_YM "
			+ "AND ba.endYearMonth <= :BASE_YM "
			+ "LEFT JOIN QstdtPaymentDetail pd "
			+ "ON pd.qstdtPaymentDetailPK.companyCode = pc.pcpmtPersonComPK.ccd "
			+ "AND pd.qstdtPaymentDetailPK.personId = pc.pcpmtPersonComPK.pid "
			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "
			+ "AND pd.qstdtPaymentDetailPK.processingYM = :ProcessingYM "
			+ "AND pd.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR "//0
			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR ";//3 (NOT 4)
			
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qpp009.SalarychartRepository#getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qpp009.query.SalaryChartReportQuery)
	 */
	@Override
	public List<EmployeeData> getItems(String companyCode, SalaryTableReportQuery query) {
//		EntityManager em = this.getEntityManager();
//		Query sqlQuery = em.createQuery(CHECK_AT_PRINTING_QUERY);
//		sqlQuery.setParameter("CCD", companyCode);
//		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
//		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
//		sqlQuery.setParameter("CTR_ATR", CTR_ATR_CHECK);
//		sqlQuery.setParameter("VALUE", VALUE_0);
//		List<Object[]> masterResultList;
//		// Get Result List
//		List<Object[]> resultList = new ArrayList<>();
//		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
//				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
//		
		List<Object[]> resultList = this.getPaymentHeaderResult(companyCode, query);
		List<Object[]> masterResultList;
		// Check if Result List is Empty
		if (CollectionUtil.isEmpty(resultList)) {
			throw new BusinessException("ER010");
		}
		else {
			// Get Checking at Printing Result List
			List<Object[]> checkingResultList = this.getCheckingAtPrintRss(companyCode, query);
			if (CollectionUtil.isEmpty(checkingResultList)) {
				throw new BusinessException("ER010");
			}
			else {
				// Get Master Result List
				masterResultList = this.getMasterResultList(companyCode, query);
				if (CollectionUtil.isEmpty(checkingResultList)) {
					throw new BusinessException("ER010");
				}
				else {
					
				}
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getPaymentHeaderResult(String companyCode, SalaryTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(PAYMENT_HEADER_QUERY);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getCheckingAtPrintRss(String companyCode, SalaryTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(BANK_ACC_JOIN_PAYMENT_DETAIL);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
		sqlQuery.setParameter("ONE", ONE);
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("CTR_ATR", CTR_ATR_CHECK);
		sqlQuery.setParameter("VALUE", VALUE_0);
		sqlQuery.setParameter("ITEM_CD_F304", ITEM_CD_F304);
		sqlQuery.setParameter("ITEM_CD_F305", ITEM_CD_F305);
		sqlQuery.setParameter("ITEM_CD_F306", ITEM_CD_F306);
		sqlQuery.setParameter("ITEM_CD_F307", ITEM_CD_F307);
		sqlQuery.setParameter("ITEM_CD_F308", ITEM_CD_F308);
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		return resultList;
	}

	
	private Double sumValues(List<Object[]> detailData) {
		// Sum values of detailed data item of item code F304
		Double paymentDetailF304Val = detailData.stream().filter(data -> {
			PbamtPersonBankAccount pa = (PbamtPersonBankAccount) data[PERSON_BANK_ACC_TBL_INDEX];
			return pa.useSet1 == ONE && pa.paymentMethod1 == ONE;
		}).filter(data -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) data[PAYMENT_DETAIL_TBL_INDEX];
			return pdt.qstdtPaymentDetailPK.itemCode.equals(ITEM_CD_F304);
		}).mapToDouble(result -> {
			if (result[PAYMENT_DETAIL_TBL_INDEX] instanceof QstdtPaymentDetail) {
				QstdtPaymentDetail pdt = (QstdtPaymentDetail) result[PAYMENT_DETAIL_TBL_INDEX];
				return pdt.value.doubleValue();
			}
			return 0;
		}).sum();
		
		// Sum values of detailed data item of item code F305
		Double paymentDetailF305Val = detailData.stream().filter(data -> {
			PbamtPersonBankAccount pa = (PbamtPersonBankAccount) data[PERSON_BANK_ACC_TBL_INDEX];
			return pa.useSet2 == ONE && pa.paymentMethod2 == ONE;
		}).filter(data -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) data[PAYMENT_DETAIL_TBL_INDEX];
			return pdt.qstdtPaymentDetailPK.itemCode.equals(ITEM_CD_F305);
		}).mapToDouble(result -> {
			if (result[PAYMENT_DETAIL_TBL_INDEX] instanceof QstdtPaymentDetail) {
				QstdtPaymentDetail pdt = (QstdtPaymentDetail) result[PAYMENT_DETAIL_TBL_INDEX];
				return pdt.value.doubleValue();
			}
			return 0;
		}).sum();
		
		// Sum values of detailed data item of item code F306
		Double paymentDetailF306Val = detailData.stream().filter(data -> {
			PbamtPersonBankAccount pa = (PbamtPersonBankAccount) data[PERSON_BANK_ACC_TBL_INDEX];
			return pa.useSet3 == ONE && pa.paymentMethod3 == ONE;
		}).filter(data -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) data[PAYMENT_DETAIL_TBL_INDEX];
			return pdt.qstdtPaymentDetailPK.itemCode.equals(ITEM_CD_F306);
		}).mapToDouble(result -> {
			if (result[PAYMENT_DETAIL_TBL_INDEX] instanceof QstdtPaymentDetail) {
				QstdtPaymentDetail pdt = (QstdtPaymentDetail) result[PAYMENT_DETAIL_TBL_INDEX];
				return pdt.value.doubleValue();
			}
			return 0;
		}).sum();
		
		// Sum values of detailed data item of item code F307
		Double paymentDetailF307Val = detailData.stream().filter(data -> {
			PbamtPersonBankAccount pa = (PbamtPersonBankAccount) data[PERSON_BANK_ACC_TBL_INDEX];
			return pa.useSet4 == ONE && pa.paymentMethod4 == ONE;
		}).filter(data -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) data[PAYMENT_DETAIL_TBL_INDEX];
			return pdt.qstdtPaymentDetailPK.itemCode.equals(ITEM_CD_F307);
		}).mapToDouble(result -> {
			if (result[PAYMENT_DETAIL_TBL_INDEX] instanceof QstdtPaymentDetail) {
				QstdtPaymentDetail pdt = (QstdtPaymentDetail) result[PAYMENT_DETAIL_TBL_INDEX];
				return pdt.value.doubleValue();
			}
			return 0;
		}).sum();
		
		// Sum values of detailed data item of item code F308
		Double paymentDetailF308Val = detailData.stream().filter(data -> {
			PbamtPersonBankAccount pa = (PbamtPersonBankAccount) data[PERSON_BANK_ACC_TBL_INDEX];
			return pa.useSet5 == ONE && pa.paymentMethod5 == ONE;
		}).filter(data -> {
			QstdtPaymentDetail pdt = (QstdtPaymentDetail) data[PAYMENT_DETAIL_TBL_INDEX];
			return pdt.qstdtPaymentDetailPK.itemCode.equals(ITEM_CD_F308);
		}).mapToDouble(result -> {
			if (result[PAYMENT_DETAIL_TBL_INDEX] instanceof QstdtPaymentDetail) {
				QstdtPaymentDetail pdt = (QstdtPaymentDetail) result[PAYMENT_DETAIL_TBL_INDEX];
				return pdt.value.doubleValue();
			}
			return 0;
		}).sum();
		return paymentDetailF304Val + paymentDetailF305Val 
				+ paymentDetailF306Val + paymentDetailF307Val + paymentDetailF308Val;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getMasterResultList(String companyCode, SalaryTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(QUERY_STRING);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
		sqlQuery.setParameter("CTR_ATR", CTR_ATR_PRINT);
		sqlQuery.setParameter("SPARE_PAY_ATR", SPARE_PAY_ATR);
		sqlQuery.setParameter("BASE_YMD", GeneralDate.today());
		sqlQuery.setParameter("BASE_YM", query.getTargetYear());
		
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		
		return resultList;
	}
	
	private List<Object[]> filterData(List<Object[]> itemList) {
		// Group by EMP.
		Map<String, List<Object[]>> userMap = itemList.stream()
				.collect(Collectors.groupingBy(item -> 
				((PbsmtPersonBase) item[0]).getPid()));
		for (Map.Entry<String, List<Object[]>> entry : userMap.entrySet()) {
			List<Object[]> detailData = entry.getValue();
			
		}
		
		return null;
	} 
	
}
