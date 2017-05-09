/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.denominationtable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.file.pr.app.export.denominationtable.DenoTableRepository;
import nts.uk.file.pr.app.export.denominationtable.data.DepartmentData;
import nts.uk.file.pr.app.export.denominationtable.data.EmployeeData;
import nts.uk.file.pr.app.export.denominationtable.query.DenoTableReportQuery;



/**
 * The Class JpaSalaryChartReportRepository.
 */
@Stateless
public class JpaDenoTableReportRepository extends JpaRepository implements DenoTableRepository {
	private static final int PAY_BONUS_ATR = 0;
	private static final int CTR_ATR_3 = 3;
	private static final int ONE_THOUSAND = 1000;
	private static final int ONE = 1;
	private static final int VALUE_0 = 0;
//	private static final int SPARE_PAY_ATR = 0;
	private static final String ITEM_CD_F304 = "F304";
	private static final String ITEM_CD_F305 = "F305";
	private static final String ITEM_CD_F306 = "F306";
	private static final String ITEM_CD_F307 = "F307";
	private static final String ITEM_CD_F308 = "F308";
//	private static final int PAYMENT_DETAIL_TBL_INDEX = 5;
//	private static final int PERSON_BANK_ACC_TBL_INDEX = 4;
//	private static final int DEP_TBL_INDEX = 3;
//	private static final int DEP_REGL_TBL_INDEX = 2;
//	private static final int PERSON_COM_TBL_INDEX = 1;
//	private static final int PERSON_BASE_TBL_INDEX = 0;
	
	
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
	private static final String BANK_ACC_JOIN_PAYMENT_DETAIL_QUERY = "SELECT ba, pd "
			+ "FROM PbamtPersonBankAccount ba, "
			+ "QstdtPaymentDetail pd "
			+ "WHERE ba.pbamtPersonBankAccountPK.companyCode = :CCD "
			+ "AND ba.pbamtPersonBankAccountPK.personId IN :PIDs "
			+ "AND ((ba.useSet1 = :ONE AND ba.paymentMethod1 = :ONE) "
			+ "OR (ba.useSet2 = :ONE AND ba.paymentMethod2 = :ONE) "
			+ "OR (ba.useSet3 = :ONE AND ba.paymentMethod3 = :ONE) "
			+ "OR (ba.useSet4 = :ONE AND ba.paymentMethod4 = :ONE) "
			+ "OR (ba.useSet5 = :ONE AND ba.paymentMethod5 = :ONE)) "
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
	
//	private static final String QUERY_STRING = "SELECT pb, pc, pdr, d, ba, pd "
//			+ "FROM PbsmtPersonBase pb "
//			+ "LEFT JOIN PcpmtPersonCom pc "
//			+ "ON pb.pid IN :PIDs "
//			+ "AND pc.pcpmtPersonComPK.ccd = :CCD "
//			+ "AND pc.pcpmtPersonComPK.pid = pb.pid "
//			+ "LEFT JOIN PogmtPersonDepRgl pdr "
//			+ "ON pdr.pogmtPersonDepRglPK.ccd = :CCD "
//			+ "AND pdr.pogmtPersonDepRglPK.pid = pb.pid "
//			+ "AND pdr.strD >= :BASE_YMD "
//			+ "AND pdr.endD <= :BASE_YMD "
//			+ "LEFT JOIN CmnmtDep d "
//			+ "ON d.cmnmtDepPK.companyCode = pc.pcpmtPersonComPK.ccd "
//			+ "AND d.startDate >= :BASE_YMD "
//			+ "AND d.endDate <= :BASE_YMD "
//			+ "AND d.cmnmtDepPK.departmentCode = pdr.depcd "
//			+ "LEFT JOIN PbamtPersonBankAccount ba "
//			+ "ON ba.pbamtPersonBankAccountPK.companyCode = d.cmnmtDepPK.companyCode "
//			+ "AND ba.pbamtPersonBankAccountPK.personId = pb.pid "
//			+ "AND ba.startYearMonth >= :BASE_YM "
//			+ "AND ba.endYearMonth <= :BASE_YM "
//			+ "LEFT JOIN QstdtPaymentDetail pd "
//			+ "ON pd.qstdtPaymentDetailPK.companyCode = pc.pcpmtPersonComPK.ccd "
//			+ "AND pd.qstdtPaymentDetailPK.personId = pc.pcpmtPersonComPK.pid "
//			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "
//			+ "AND pd.qstdtPaymentDetailPK.processingYM = :ProcessingYM "
//			+ "AND pd.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR "//0
//			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR "//3 (NOT 4)
//			+ "";
	
	private static final String QUERY_STRING = "SELECT pb.pid, pc.scd, pb.nameB, "
//			+ "d.cmnmtDepPK.departmentCode, d.depName, "
			+ "pdr.depcd, d.depName, "
			+ "d.hierarchyId, SUM(pd.value), COUNT(pdr.pogmtPersonDepRglPK.pid)  "// missing dep info
			+ "FROM PbsmtPersonBase pb "
			+ "LEFT JOIN PcpmtPersonCom pc "
			+ "ON pc.pcpmtPersonComPK.pid = pb.pid "
			+ "LEFT JOIN PogmtPersonDepRgl pdr "
			+ "ON pdr.pogmtPersonDepRglPK.pid = pb.pid "
			+ "LEFT JOIN CmnmtDep d "
			+ "ON d.cmnmtDepPK.departmentCode = pdr.depcd "
			+ "LEFT JOIN PbamtPersonBankAccount ba "
			+ "ON ba.pbamtPersonBankAccountPK.personId = pb.pid "
			+ "LEFT JOIN QstdtPaymentDetail pd "
			+ "ON pd.qstdtPaymentDetailPK.personId = pc.pcpmtPersonComPK.pid "
			+ "WHERE pb.pid IN :PIDs "
			+ "AND pc.pcpmtPersonComPK.ccd = :CCD "
			+ "AND pdr.strD <= :BASE_YMD "
			+ "AND pdr.endD >= :BASE_YMD "
			+ "AND d.startDate <= :BASE_YMD "
			+ "AND d.endDate >= :BASE_YMD "
			+ "AND ba.startYearMonth <= :BASE_YM "
			+ "AND ba.endYearMonth >= :BASE_YM "
			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "
			+ "AND pd.qstdtPaymentDetailPK.processingYM = :ProcessingYM "
			+ "AND pd.qstdtPaymentDetailPK.sparePayAttribute = :SPARE_PAY_ATR "//0
			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR "
			+ "AND ((ba.useSet1 = :ONE AND ba.paymentMethod1 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F304) "
			+ "OR (ba.useSet2 = :ONE AND ba.paymentMethod2 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F305) "
			+ "OR (ba.useSet3 = :ONE AND ba.paymentMethod3 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F306) "
			+ "OR (ba.useSet4 = :ONE AND ba.paymentMethod4 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F307) "
			+ "OR (ba.useSet5 = :ONE AND ba.paymentMethod5 = :ONE AND pd.qstdtPaymentDetailPK.itemCode = :ITEM_CD_F308)) "
			+ "GROUP BY pb.pid, pc.scd, pb.nameB, pdr.depcd, d.depName, d.hierarchyId "
			+ "ORDER BY d.hierarchyId ";
			
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.screen.app.report.qpp009.SalarychartRepository#getItems(java.lang.String, nts.uk.ctx.pr.screen.app.report.qpp009.query.SalaryChartReportQuery)
	 */
	@Override
	public List<EmployeeData> getItems(String companyCode, DenoTableReportQuery query) {
		List<Object[]> paymentHeaderResult = this.getPaymentHeaderResult(companyCode, query);
		List<EmployeeData> masterResultList;
		// Check if Result List is Empty
		if (CollectionUtil.isEmpty(paymentHeaderResult)) {
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		}
		else {
			// Get Checking at Printing Result List
			List<Object[]> checkingResultList = this.getCheckingAtPrintRss(companyCode, query);
			if (CollectionUtil.isEmpty(checkingResultList)) {
				throw new BusinessException(new RawErrorMessage("対象データがありません。"));
			}
			else {
				// Get Master Result List
				masterResultList = this.getMasterResultList(companyCode, query);
				if (CollectionUtil.isEmpty(checkingResultList)) {
					throw new BusinessException(new RawErrorMessage("対象データがありません。"));
				}
			}
		}
		
		return masterResultList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getPaymentHeaderResult(String companyCode, DenoTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(PAYMENT_HEADER_QUERY);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("ProcessingYM", query.getYearMonth());
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getCheckingAtPrintRss(String companyCode, DenoTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(BANK_ACC_JOIN_PAYMENT_DETAIL_QUERY);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("ProcessingYM", query.getYearMonth());
		sqlQuery.setParameter("ONE", ONE);
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("CTR_ATR", CTR_ATR_3);
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

	
	@SuppressWarnings("unchecked")
	private List<EmployeeData> getMasterResultList(String companyCode, DenoTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(QUERY_STRING);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("ProcessingYM", query.getYearMonth());
		sqlQuery.setParameter("CTR_ATR", CTR_ATR_3);
		sqlQuery.setParameter("SPARE_PAY_ATR", query.getSelectedDivision());
		sqlQuery.setParameter("BASE_YMD", GeneralDate.today());
		sqlQuery.setParameter("BASE_YM", query.getYearMonth());
		sqlQuery.setParameter("ITEM_CD_F304", ITEM_CD_F304);
		sqlQuery.setParameter("ITEM_CD_F305", ITEM_CD_F305);
		sqlQuery.setParameter("ITEM_CD_F306", ITEM_CD_F306);
		sqlQuery.setParameter("ITEM_CD_F307", ITEM_CD_F307);
		sqlQuery.setParameter("ITEM_CD_F308", ITEM_CD_F308);
		sqlQuery.setParameter("ONE", ONE);
		
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		
		if (CollectionUtil.isEmpty(resultList)) {
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		}
		// Convert To EmployeeData List
		List<EmployeeData> empList = new ArrayList<>();
		Iterator<Object[]> itemItr = resultList.iterator();
		while (itemItr.hasNext()) {
			Object[] objItr = itemItr.next();
			String empCode = (String) objItr[1];
			String empName = (String) objItr[2];
			BigDecimal sumValues = (BigDecimal) objItr[6];
			Double paymentAmount = sumValues.doubleValue();
			String depCode = (String) objItr[3];
			String depName = (String) objItr[4];
			String depPath = (String) objItr[5];
			int depLevel = depPath.length() / 3;
			Long peopleInDep = (Long) objItr[7];
			DepartmentData depData = DepartmentData.builder()
					.depCode(depCode)
					.depName(depName)
					.depPath(depPath)
					.depLevel(depLevel)
					.numberOfEmp(peopleInDep.intValue())
					.build();
			EmployeeData empData = EmployeeData.builder()
					.empCode(empCode)
					.empName(empName)
					.paymentAmount(paymentAmount)
					.departmentData(depData)
					.build();
			empList.add(empData);
		}
		return empList;
	} 
	
}
