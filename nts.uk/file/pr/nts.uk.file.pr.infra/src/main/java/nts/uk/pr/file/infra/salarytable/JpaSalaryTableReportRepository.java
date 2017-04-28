/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.salarytable;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
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
	private static final String CHECK_AT_PRINTING_QUERY = "SELECT h, ba, pd "
			+ "FROM QstdtPaymentHeader h "
			+ "LEFT JOIN PbamtPersonBankAccount ba "
			+ "ON h.qstdtPaymentHeaderPK.companyCode = :CCD "
			+ "AND h.qstdtPaymentHeaderPK.personId in :PIDs "
			+ "AND h.qstdtPaymentHeaderPK.processingYM = :ProcessingYM "
			+ "AND h.qstdtPaymentHeaderPK.payBonusAtr = :PAY_BONUS_ATR "//0
			+ "AND ba.pbamtPersonBankAccountPK.companyCode = h.qstdtPaymentHeaderPK.companyCode "
			+ "AND ba.pbamtPersonBankAccountPK.personId = h.qstdtPaymentHeaderPK.personId "
			+ "LEFT JOIN QstdtPaymentDetail pd "
			+ "ON pd.qstdtPaymentDetailPK.companyCode = h.qstdtPaymentHeaderPK.companyCode "
			+ "AND pd.qstdtPaymentDetailPK.personId = h.qstdtPaymentHeaderPK.personId "
			+ "AND pd.qstdtPaymentDetailPK.processingYM = h.qstdtPaymentHeaderPK.processingYM "
			+ "AND pd.qstdtPaymentDetailPK.payBonusAttribute = :PAY_BONUS_ATR "//0
			+ "AND pd.qstdtPaymentDetailPK.categoryATR = :CTR_ATR "//4
			+ "AND pd.value != :VALUE ";//0
			
	private static final String QUERY_STRING = "SELECT pb, pc, pdr, d, ba, pd "
			+ "FROM PbsmtPersonBase pb "
			+ "LEFT JOIN PcpmtPersonCom pc "
			+ "ON pb.pid in :PIDs "
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
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeData> getItems(String companyCode, SalaryTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(CHECK_AT_PRINTING_QUERY);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("CTR_ATR", CTR_ATR_CHECK);
		sqlQuery.setParameter("VALUE", VALUE_0);
		List<Object[]> masterResultList;
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		// Check if Result List is Empty
//		if (CollectionUtil.isEmpty(resultList)) {
//			throw new BusinessException("ER010");
//		}
//		else {
//			// Get Master Result List
//			masterResultList = this.getMasterResultList(companyCode, query);
//			if (CollectionUtil.isEmpty(masterResultList)) {
//				throw new BusinessException("ER010");
//			}
//		}
		
		return null;
	}
	
	
	private List<Object[]> getMasterResultList(String companyCode, SalaryTableReportQuery query) {
		EntityManager em = this.getEntityManager();
		Query sqlQuery = em.createQuery(QUERY_STRING);
		sqlQuery.setParameter("CCD", companyCode);
		sqlQuery.setParameter("PAY_BONUS_ATR", PAY_BONUS_ATR);
		sqlQuery.setParameter("ProcessingYM", query.getTargetYear());
		sqlQuery.setParameter("CTR_ATR", CTR_ATR_PRINT);
		sqlQuery.setParameter("SPARE_PAY_ATR", SPARE_PAY_ATR);
		// Get Result List
		List<Object[]> resultList = new ArrayList<>();
		CollectionUtil.split(query.getPIdList(), ONE_THOUSAND,
				subList -> resultList.addAll(sqlQuery.setParameter("PIDs", subList).getResultList()));
		
		return resultList;
	}
	
//	private List<Object[]> filterAtChecking(List<Object[]> itemList) {
//		
//	}
	
}
