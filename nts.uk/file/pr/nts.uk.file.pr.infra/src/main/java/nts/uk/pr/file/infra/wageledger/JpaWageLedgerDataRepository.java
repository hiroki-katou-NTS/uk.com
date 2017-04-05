/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;

/**
 * The Class JpaWageLedgerDataRepository.
 */
@Stateless
public class JpaWageLedgerDataRepository extends JpaRepository implements WageLedgerDataRepository {

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository
	 * #findReportDatas(java.lang.String, nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
	 */
	@Override
	public List<WLOldLayoutReportData> findReportDatas(String companyCode, WageLedgerReportQuery query) {
		EntityManager em = this.getEntityManager();
		
		// Create Query String In Case .
		String queryString = "SELECT ? "
				+ "FROM ReportPbsmtPersonBase p, ReportQcamtItem m, "
				+ "ReportQstdtPaymentDetail d "
				+ "WHERE p.";
		
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository
	 * #checkHasReportData(java.lang.String, nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
	 */
	@Override
	public boolean hasReportData(String companyCode, WageLedgerReportQuery query) {
		EntityManager em = this.getEntityManager();
		
		// Create Query String.
		String queryString = "SELECT COUNT(h.qstdtPaymentHeaderPK.personId) FROM ReportQstdtPaymentHeader h"
			+ "WHERE h.qstdtPaymentHeaderPK.companyCode = :companyCode "
			+ "AND h.qstdtPaymentHeaderPK.personId IN :personIds "
			+ "AND h.qstdtPaymentHeaderPK.sparePayAtr = :sparePayAtr "
			+ "AND h.qstdtPaymentHeaderPk.processingYM <= :startProcessingYM "
			+ "AND h.qstdtPaymentHeaderPk.processingYM >= :endProcessingYM ";
		
		// Create Year Month.
		YearMonth startYearMonth = YearMonth.of(query.targetYear, 1);
		YearMonth endYearMonth = YearMonth.of(query.targetYear, 12);
		TypedQuery<Long> typedQuery = em.createQuery(queryString, Long.class)
				.setParameter("companyCode", companyCode)
				.setParameter("sparePayAtr", query.isAggreatePreliminaryMonth ? 1 : 0)
				.setParameter("startProcessingYM", startYearMonth.v())
				.setParameter("endProcessingYM", endYearMonth.v());
		
		List<List<String>> subPersonIdsList = this.splitInParamList(query.employeeIds);
		long amountData = 0;
		for (List<String> personIds : subPersonIdsList) {
			amountData += typedQuery.setParameter("personIds", personIds).getFirstResult();
		}
		return amountData > 0;
	}

	private <T> List<List<T>> splitInParamList(List<T> inputList) {
		List<List<T>> resultList = new ArrayList<>();
		int fromIndex = 0;
		// NOTE: DURING TO LIMITATION OF NUMBER PARAMETER
		// WE MUST LIMIT EMPLOYEE SIZE WHEN QUERY.
		int maxParamSize = 1000;
		int nextIndex = fromIndex;

		// Split into sub user id h.
		do {
			// Cal next index of sublist.
			nextIndex = fromIndex + maxParamSize;
			if (nextIndex > inputList.size()) {
				nextIndex = inputList.size();
			}

			// Extract sub user id list.
			List<T> subUserIdH = inputList.subList(fromIndex, nextIndex);
			resultList.add(subUserIdH);
			fromIndex = nextIndex;
		} while (nextIndex < inputList.size());

		// Ret.
		return resultList;
	}
}
