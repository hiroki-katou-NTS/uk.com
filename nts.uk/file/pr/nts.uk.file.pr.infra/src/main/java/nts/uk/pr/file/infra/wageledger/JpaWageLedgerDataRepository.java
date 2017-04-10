/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery.OutputType;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.pr.file.infra.entity.ReportCmnmtDep;
import nts.uk.pr.file.infra.entity.ReportPbsmtPersonBase;
import nts.uk.pr.file.infra.entity.ReportPcpmtPersonCom;
import nts.uk.pr.file.infra.entity.ReportPogmtPersonDepRgl;

/**
 * The Class JpaWageLedgerDataRepository.
 */
@Stateless
public class JpaWageLedgerDataRepository extends JpaRepository implements WageLedgerDataRepository {

	/* (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository
	 * #findReportDatas(java.lang.String, nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findReportDatas(String companyCode, WageLedgerReportQuery query, Class<T> returnType) {
		List<Object[]> itemResultList;
		// Case: Query master item only.
		if (query.outputType == OutputType.MasterItems) {
			itemResultList =  this.queryMasterDataList(companyCode, query);
		} else {
			// Case: Query by output setting.
			itemResultList = this.queryByOutputSetting(companyCode, query);
		}
		
		// Covert to Data model.
		if (returnType.isInstance(WLOldLayoutReportData.class)) {
			return (List<T>) this.convertToOldLayoutDataList(itemResultList);
		}
		if (returnType.isInstance(WLNewLayoutReportData.class)) {
			return (List<T>) this.covertToNewLayoutDataList(itemResultList);
		}
		
		// Not support return type.
		throw new RuntimeException("Cannot return object instance of " + returnType.toString());
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
	
	@SuppressWarnings("unchecked")
	private List<Object[]> queryMasterDataList(String companyCode, WageLedgerReportQuery query) {
		EntityManager em = this.getEntityManager();
		// Create Year Month.
		YearMonth startYearMonth = YearMonth.of(query.targetYear, 1);
		YearMonth endYearMonth = YearMonth.of(query.targetYear, 12);
		
		// Create Query String.
		String queryString = "SELECT p, m, d, pc, pd, cd "
				+ "FROM ReportPbsmtPersonBase p, ReportQcamtItem m, "
				+ "ReportQstdtPaymentDetail d, "
				+ "ReportPcpmtPersonCom pc, "
				+ "ReportPogmtPersonDepRgl pd, "
				+ "ReportCmnmtDep cd "
				+ "WHERE p.pid = d.qstdtPaymentDetailPK.personId "
				+ "AND d.qstdtPaymentDetailPK.itemCode = m.qcamtItemPK.itemCd "
				+ "AND d.qstdtPaymentDetailPK.ccd = m.qcamtItemPK.ccd "
				+ "AND pc.pcpmtPersonComPK.ccd = m.qcamtItemPK.ccd "
				+ "AND pc.pcpmtPersonComPK.pid = p.pid "
				+ "AND pd.pogmtPersonDepRglPK.ccd = m.qcamtItemPK.ccd "
				+ "AND pd.pogmtPersonDepRglPK.pid = p.pid "
				+ "AND cd.cmnmtDepPK.companyCode = m.qcamtItemPK.ccd "
				+ "AND cd.cmnmtDepPK.departmentCode = pd.depcd"
				+ "AND p.pid in :personIds "
				+ "AND m.qcamtItemPK.ccd = :companyCode "
				+ "AND d.qstdtPaymentDetailPK.processingYM <= :startProcessingYM "
				+ "AND d.qstdtPaymentDetailPK.processingYM >= :endProcessingYM "
				+ "AND d.qstdtPaymentDetailPK.sparePayAttribute = :sparePayAtr "
				+ "AND pd.strD <= :baseDate "
				+ "AND pd.endD >= :baseDate ";

		Query typeQuery = em.createQuery(queryString).setParameter("companyCode", companyCode)
				.setParameter("sparePayAtr", query.isAggreatePreliminaryMonth ? 1 : 0)
				.setParameter("startProcessingYM", startYearMonth.v())
				.setParameter("endProcessingYM", endYearMonth.v())
				.setParameter("baseDate", query.baseDate);
		List<List<String>> subPersonIdsList = this.splitInParamList(query.employeeIds);
		List<Object[]> masterResultList = new ArrayList<>();
		for (List<String> personIds : subPersonIdsList) {
			masterResultList.addAll(typeQuery.setParameter("personIds", personIds).getResultList());
		}
		return masterResultList;
	}
	
	private List<Object[]> queryByOutputSetting(String companyCode, WageLedgerReportQuery query) {
		return null;
	}
	
	private List<WLNewLayoutReportData> covertToNewLayoutDataList(List<Object[]> itemList) {
		return null;
	}
	private List<WLOldLayoutReportData> convertToOldLayoutDataList(List<Object[]> itemList) {
		List<WLOldLayoutReportData> dataList = new ArrayList<>();
		// Group by user.
		Map<Object, List<Object[]>> userMap = itemList.stream().collect(Collectors.groupingBy(item -> item[0])); 
		for (Object user : userMap.keySet()) {
			ReportPbsmtPersonBase personBase = (ReportPbsmtPersonBase) user;
			List<Object[]> detail = userMap.get(user);
			ReportPcpmtPersonCom personCompany = (ReportPcpmtPersonCom) detail.get(0)[3];
			ReportPogmtPersonDepRgl personDepartment = (ReportPogmtPersonDepRgl) detail.get(0)[4];
			ReportCmnmtDep departmentMaster = (ReportCmnmtDep) detail.get(0)[5];
			
			// Convert to header data.
			// TODO: Wait QA #82348
			HeaderReportData headerReportData = HeaderReportData.builder()
					.employeeCode(personCompany.getScd())
					.employeeName(personBase.getNameOfficial())
					.sex(personBase.getGendar() == 0 ? "男性" : "女性")
					.departmentCode(personDepartment.getDepcd())
					.departmentName(departmentMaster.getName())
					.build();
			
			// Convert To data model.
			WLOldLayoutReportData data = WLOldLayoutReportData.builder()
					.headerData(headerReportData)
					.build();
			
			// Add to report data list.
			dataList.add(data);
		}
		return dataList;
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
