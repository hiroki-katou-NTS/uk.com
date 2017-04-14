/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.infra.entity.organization.department.CmnmtDep;
import nts.uk.ctx.basic.infra.entity.organization.position.CmnmtJobTitle;
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.basic.infra.entity.report.PcpmtPersonCom;
import nts.uk.ctx.basic.infra.entity.report.PogmtPersonDepRgl;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItem;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.infra.util.JpaUtil;
import nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery;
import nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery.OutputType;
import nts.uk.file.pr.app.export.wageledger.data.WLNewLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.WLOldLayoutReportData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.MonthlyData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;

/**
 * The Class JpaWageLedgerDataRepository.
 */
@Stateless
public class JpaWageLedgerDataRepository extends JpaRepository implements WageLedgerDataRepository {
	
	private static final String CHECK_HAS_DATA_QUERY_STRING = "SELECT COUNT(h.qstdtPaymentHeaderPK.personId) "
			+ "FROM QstdtPaymentHeader h "
			+ "WHERE h.qstdtPaymentHeaderPK.companyCode = :companyCode "
			+ "AND h.qstdtPaymentHeaderPK.personId IN :personIds "
			+ "AND h.qstdtPaymentHeaderPK.sparePayAtr = :sparePayAtr "
			+ "AND h.qstdtPaymentHeaderPK.processingYM <= :startProcessingYM "
			+ "AND h.qstdtPaymentHeaderPK.processingYM >= :endProcessingYM ";
	
	private static final String HEADER_QUERY_STRING = "SELECT p, pc, pd, cd, cj "
			+ "FROM PbsmtPersonBase p,"
			+ "PcpmtPersonCom pc, "
			+ "PogmtPersonDepRgl pd, "
			+ "CmnmtDep cd, "
			+ "PclmtPersonTitleRgl pt, "
			+ "CmnmtJobHist jh, "
			+ "CmnmtJobTitle cj "
			+ "WHERE pc.pcpmtPersonComPK.pid = p.pid "
			+ "AND pd.pogmtPersonDepRglPK.pid = p.pid "
			+ "AND cd.cmnmtDepPK.departmentCode = pd.depcd "
			+ "AND pt.pclmtPersonTitleRglPK.pid = p.pid"
			+ "AND jh.cmnmtJobHistPK.historyId = pt.pclmtPersonTitleRglPK.histId "
			+ "AND cj.cmnmtJobTitlePK.companyCode = m.qcamtItemPK.ccd "
			+ "AND cj.cmnmtJobTitlePK.jobCode = pt.jobcd "
			+ "AND cj.cmnmtJobTitlePK.historyId = jh.cmnmtJobHistPK.historyId "
			+ "AND p.pid in :personIds "
			+ "AND pc.pcpmtPersonComPK.ccd = :companyCode "
			+ "AND pd.strD <= :baseDate "
			+ "AND pd.endD >= :baseDate ";
	
	private static final String TOTAL_TAX_ITEM_CODE = "F001";
	
	private static final String TOTAL_TAX_EXEMPTION_ITEM_CODE = "F002";
	
	private static final String TOTAL_SALARY_PAYMENT_ITEM_CODE = "F003";
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
			return (List<T>) this.convertToOldLayoutDataList(itemResultList, query, companyCode);
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
		
		// Create Year Month.
		YearMonth startYearMonth = YearMonth.of(query.targetYear, 1);
		YearMonth endYearMonth = YearMonth.of(query.targetYear, 12);
		TypedQuery<Long> typedQuery = em.createQuery(CHECK_HAS_DATA_QUERY_STRING, Long.class)
				.setParameter("companyCode", companyCode)
				.setParameter("sparePayAtr", JpaUtil.boolean2Short(query.isAggreatePreliminaryMonth))
				.setParameter("startProcessingYM", startYearMonth.v())
				.setParameter("endProcessingYM", endYearMonth.v());
		
		List<Long> resultList = new ArrayList<>();
		CollectionUtil.split(query.employeeIds, 1000, (subList) -> {
			resultList.add((long) typedQuery.setParameter("personIds", subList).getFirstResult());
		});
		return resultList.stream().mapToLong(Long::longValue).sum() > 0;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> queryMasterDataList(String companyCode, WageLedgerReportQuery query) {
		EntityManager em = this.getEntityManager();
		// Create Year Month.
		YearMonth startYearMonth = YearMonth.of(query.targetYear, 1);
		YearMonth endYearMonth = YearMonth.of(query.targetYear, 12);
		
		// Create Query String.
		String queryString = "SELECT d, m "
				+ "FROM QcamtItem m, "
				+ "QstdtPaymentDetail d "
				+ "WHERE d.qstdtPaymentDetailPK.itemCode = m.qcamtItemPK.itemCd "
				+ "AND d.qstdtPaymentDetailPK.ccd = m.qcamtItemPK.ccd "
				+ "AND m.qcamtItemPK.ccd = :companyCode "
				+ "AND d.qstdtPaymentDetailPK.personId in :personIds "
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
		
		// Query data.
		List<Object[]> masterResultList = new ArrayList<>();
		CollectionUtil.split(query.employeeIds, 1000, (subList) -> {
			masterResultList.addAll(typeQuery.setParameter("personIds", subList).getResultList());
		});
		return masterResultList;
	}
	
	private List<Object[]> queryByOutputSetting(String companyCode, WageLedgerReportQuery query) {
		return null;
	}
	
	private List<WLNewLayoutReportData> covertToNewLayoutDataList(List<Object[]> itemList) {
		return null;
	}
	private List<WLOldLayoutReportData> convertToOldLayoutDataList(List<Object[]> itemList, WageLedgerReportQuery query, 
			String companyCode) {
		List<WLOldLayoutReportData> dataList = new ArrayList<>();
		// Query Header data.
		Map<String, HeaderReportData> headerDataMap = this.findHeaderDatas(companyCode, query);
		
		// Group by user.
		Map<String, List<Object[]>> userMap = itemList.stream()
				.collect(Collectors.groupingBy(item -> ((QstdtPaymentDetail) item[0]).qstdtPaymentDetailPK.personId));
		for (String personId : userMap.keySet()) {
			List<Object[]> detailData = userMap.get(personId);
			
			// =============== Salary payment data ===========================
			// Filter result list by payment type and category.
			List<Object[]> salaryPaymentResultList = detailData.stream()
					.filter(res -> {
						QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
						return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Payment.value
								&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Salary.value;
					}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> salaryItemsMap = this.convertMasterResultDatasToItemMap(salaryPaymentResultList);
			PaymentData salaryPaymentData = this.convertToPaymentData(salaryItemsMap);
			
			// =============== Bonus payment data ===========================
			// Filter result list by payment type and category.
			List<Object[]> bonusPaymentResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Payment.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Salary.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> bonusItemsMap = this
					.convertMasterResultDatasToItemMap(bonusPaymentResultList);
			PaymentData bonusPaymentData = this.convertToPaymentData(bonusItemsMap);
			
			WLOldLayoutReportData data = WLOldLayoutReportData.builder()
					.headerData(headerDataMap.get(personId))
					.salaryPaymentData(salaryPaymentData)
					.bonusPaymentData(bonusPaymentData)
					.build();
			
			// Add to report data list.
			dataList.add(data);
		}
		return dataList;
	}
	
	private PaymentData convertToPaymentData(Map<QcamtItem, ReportItemDto> resultsMap) {
		List<ReportItemDto> items = new ArrayList<>(resultsMap.values());
		// Create total tax item.
		QcamtItem totalTaxResultItem = resultsMap.keySet().stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_TAX_ITEM_CODE))
				.findFirst().get();
		ReportItemDto totalTaxReportItem = resultsMap.get(totalTaxResultItem);
		items.remove(totalTaxReportItem);
		
		// Create total tax exemption item.
		QcamtItem totalTaxExemptionResultItem = resultsMap.keySet().stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_TAX_EXEMPTION_ITEM_CODE))
				.findFirst().get();
		ReportItemDto totalTaxExemptionReportItem = resultsMap.get(totalTaxExemptionResultItem);
		items.remove(totalTaxExemptionReportItem);
		
		// Create total salary payment item.
		QcamtItem totalSalaryPaymentResultItem = resultsMap.keySet().stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_SALARY_PAYMENT_ITEM_CODE))
				.findFirst().get();
		ReportItemDto totalSalaryPaymentReportItem = resultsMap.get(totalSalaryPaymentResultItem);
		items.remove(totalSalaryPaymentReportItem);
		return PaymentData.builder()
				.aggregateItemList(items)
				.totalTax(totalTaxReportItem)
				.totalTaxExemption(totalTaxExemptionReportItem)
				.totalSalaryPayment(totalSalaryPaymentReportItem)
				.build();
	}
	
	private Map<QcamtItem, ReportItemDto> convertMasterResultDatasToItemMap(List<Object[]> results) {
		
		 Map<QcamtItem, ReportItemDto> items = new HashMap<>();
		// Group result list by master item.
		Map<QcamtItem, List<Object[]>> itemListMap = results.stream()
				.collect(Collectors.groupingBy(res -> (QcamtItem) res[1]));
		itemListMap.forEach((masterItem, datas) -> {
			List<MonthlyData> monthlyDatas = datas.stream().map(data -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) data[0];
				YearMonth processYM = YearMonth.of(paymentDetail.qstdtPaymentDetailPK.processingYM);
				return MonthlyData.builder()
						.month(processYM.month())
						.amount(paymentDetail.value.longValue())
						.build();
			}).collect(Collectors.toList());
			items.put(masterItem, ReportItemDto.builder()
					.name(masterItem.itemName)
					.monthlyDatas(monthlyDatas)
					.build());
		});
		
		return items;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, HeaderReportData> findHeaderDatas(String companyCode, WageLedgerReportQuery queryData) {
		EntityManager em = this.getEntityManager();
		
		// Create Query.
		Query query = em.createQuery(HEADER_QUERY_STRING)
				.setParameter("companyCode", companyCode)
				.setParameter("baseDate", queryData.baseDate);
		
		// Query data.
		List<Object[]> headerDatasResult = new ArrayList<>();
		CollectionUtil.split(queryData.employeeIds, 1000, (subList) -> {
			headerDatasResult.addAll(query.setParameter("personIds", subList).getResultList());
		});
		
		// Convert to header data model.
		return headerDatasResult.stream().collect(Collectors.toMap(key -> {
			PbsmtPersonBase personBase = (PbsmtPersonBase) key[0];
			return personBase.getPid();
		}, data -> {
			PbsmtPersonBase personBase = (PbsmtPersonBase) data[0];
			PcpmtPersonCom personCompany = (PcpmtPersonCom) data[1];
			PogmtPersonDepRgl personDepartment = (PogmtPersonDepRgl) data[2];
			CmnmtDep departmentMaster = (CmnmtDep) data[3];
			CmnmtJobTitle jobTitle = (CmnmtJobTitle) data[4];
			
			// Build Header Data.
			HeaderReportData headerReportData = HeaderReportData.builder()
					.employeeCode(personCompany.getScd())
					.employeeName(personBase.getNameOfficial())
					.sex(personBase.getGendar() == 0 ? "男性" : "女性")
					.departmentCode(personDepartment.getDepcd())
					.departmentName(departmentMaster.getDepName())
					.targetYear(queryData.targetYear)
					.position(jobTitle.jobName)
					.build();
			return headerReportData;
		}));
	}
}
