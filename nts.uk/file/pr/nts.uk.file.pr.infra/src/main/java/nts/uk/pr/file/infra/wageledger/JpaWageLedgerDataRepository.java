/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
import nts.uk.ctx.basic.infra.entity.report.PclmtPersonTitleRgl;
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
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.DeductionData;
import nts.uk.file.pr.app.export.wageledger.data.oldlayout.PaymentData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.MonthlyData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;

/**
 * The Class JpaWageLedgerDataRepository.
 */
@Stateless
public class JpaWageLedgerDataRepository extends JpaRepository implements WageLedgerDataRepository {

	/** The Constant CHECK_HAS_DATA_QUERY_STRING. */
	private static final String CHECK_HAS_DATA_QUERY_STRING = "SELECT COUNT(h.qstdtPaymentHeaderPK.personId) "
			+ "FROM QstdtPaymentHeader h "
			+ "WHERE h.qstdtPaymentHeaderPK.companyCode = :companyCode "
			+ "AND h.qstdtPaymentHeaderPK.personId IN :personIds "
			+ "AND h.qstdtPaymentHeaderPK.sparePayAtr = :sparePayAtr "
			+ "AND h.qstdtPaymentHeaderPK.processingYM >= :startProcessingYM "
			+ "AND h.qstdtPaymentHeaderPK.processingYM <= :endProcessingYM ";

	/** The Constant HEADER_QUERY_STRING. */
	private static final String HEADER_QUERY_STRING = "SELECT p, pc, pd, cd, pt "
			+ "FROM PbsmtPersonBase p, "
			+ "PcpmtPersonCom pc, "
			+ "PogmtPersonDepRgl pd, "
			+ "CmnmtDep cd, "
			+ "PclmtPersonTitleRgl pt "
			+ "WHERE pc.pcpmtPersonComPK.pid = p.pid "
			+ "AND pd.pogmtPersonDepRglPK.pid = p.pid "
			+ "AND cd.cmnmtDepPK.departmentCode = pd.depcd "
			+ "AND pt.pclmtPersonTitleRglPK.pid = p.pid "
			+ "AND p.pid IN :personIds "
			+ "AND pc.pcpmtPersonComPK.ccd = :companyCode "
			+ "AND pd.strD <= :baseDate "
			+ "AND pd.endD >= :baseDate ";
	
	/** The Constant DETAIL_DATA_QUERY_STRING. */
	private static final String DETAIL_DATA_QUERY_STRING = "SELECT d, m "
			+ "FROM QcamtItem m, "
			+ "QstdtPaymentDetail d "
			+ "WHERE d.qstdtPaymentDetailPK.itemCode = m.qcamtItemPK.itemCd "
			+ "AND d.qstdtPaymentDetailPK.companyCode = m.qcamtItemPK.ccd "
			+ "AND m.qcamtItemPK.ccd = :companyCode "
			+ "AND d.qstdtPaymentDetailPK.personId in :personIds "
			+ "AND d.qstdtPaymentDetailPK.processingYM >= :startProcessingYM "
			+ "AND d.qstdtPaymentDetailPK.processingYM <= :endProcessingYM "
			+ "AND d.qstdtPaymentDetailPK.sparePayAttribute = :sparePayAtr ";
	
	/** The Constant MASTER_ITEM_QUERY_STRING. */
	private static final String MASTER_ITEM_QUERY_STRING = "SELECT mi FROM QcamtItem mi "
			+ "WHERE mi.qcamtItemPK.ccd = :companyCode";
	
	/** The Constant POSITION_QUERY_STRING. */
	private static final String POSITION_QUERY_STRING = "SELECT jt "
			+ "FROM CmnmtJobHist jh, "
			+ "CmnmtJobTitle jt "
			+ "WHERE jt.cmnmtJobTitlePK.companyCode = :companyCode "
			+ "AND jt.cmnmtJobTitlePK.jobCode IN :jobCode "
			+ "AND jt.cmnmtJobTitlePK.historyId = jh.cmnmtJobHistPK.historyId "
			+ "AND jh.startDate <= :baseDate "
			+ "AND jh.endDate >= :baseDate";

	/** The Constant TOTAL_TAX_ITEM_CODE. */
	private static final String TOTAL_TAX_ITEM_CODE = "F001";

	/** The Constant TOTAL_TAX_EXEMPTION_ITEM_CODE. */
	private static final String TOTAL_TAX_EXEMPTION_ITEM_CODE = "F002";

	/** The Constant TOTAL_PAYMENT_ITEM_CODE. */
	private static final String TOTAL_PAYMENT_ITEM_CODE = "F003";

	/** The Constant TOTAL_DEDUCTION_ITEM_CODE. */
	private static final String TOTAL_DEDUCTION_ITEM_CODE = "F114";

	/** The Constant TOTAL_REAL_ITEM_CODE. */
	private static final String TOTAL_REAL_ITEM_CODE = "F309";

	/** The Constant ARTICLE_CATEGORY. */
	private static final int ARTICLE_CATEGORY = 3;

	/*
	 * (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository
	 * #findReportDatas(java.lang.String,
	 * nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findReportDatas(String companyCode, WageLedgerReportQuery query, Class<T> returnType) {
		List<Object[]> itemResultList;
		// Case: Query master item only.
		if (query.outputType == OutputType.MasterItems) {
			itemResultList = this.queryMasterDataList(companyCode, query);
		} else {
			// Case: Query by output setting.
			itemResultList = this.queryByOutputSetting(companyCode, query);
		}

		// Covert to Data model.
		if (returnType.getName().equals(WLOldLayoutReportData.class.getName())) {
			return (List<T>) this.convertToOldLayoutDataList(itemResultList, query, companyCode);
		}
		if (returnType.getName().equals(WLNewLayoutReportData.class)) {
			return (List<T>) this.covertToNewLayoutDataList(itemResultList);
		}

		// Not support return type.
		throw new RuntimeException("Cannot return object instance of " + returnType.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.file.pr.app.export.wageledger.WageLedgerDataRepository
	 * #checkHasReportData(java.lang.String,
	 * nts.uk.file.pr.app.export.wageledger.WageLedgerReportQuery)
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
			resultList.add((long) typedQuery.setParameter("personIds", subList).getSingleResult());
		});
		return resultList.stream().mapToLong(Long::longValue).sum() > 0;
	}

	/**
	 * Query master data list.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> queryMasterDataList(String companyCode, WageLedgerReportQuery query) {
		EntityManager em = this.getEntityManager();
		// Create Year Month.
		YearMonth startYearMonth = YearMonth.of(query.targetYear, 1);
		YearMonth endYearMonth = YearMonth.of(query.targetYear, 12);

		// Create Query.
		Query typeQuery = em.createQuery(DETAIL_DATA_QUERY_STRING)
				.setParameter("companyCode", companyCode)
				.setParameter("sparePayAtr", query.isAggreatePreliminaryMonth ? 1 : 0)
				.setParameter("startProcessingYM", startYearMonth.v())
				.setParameter("endProcessingYM", endYearMonth.v());

		// Query data.
		List<Object[]> masterResultList = new ArrayList<>();
		CollectionUtil.split(query.employeeIds, 1000, (subList) -> {
			masterResultList.addAll(typeQuery.setParameter("personIds", subList).getResultList());
		});
		return masterResultList;
	}

	/**
	 * Query by output setting.
	 *
	 * @param companyCode the company code
	 * @param query the query
	 * @return the list
	 */
	private List<Object[]> queryByOutputSetting(String companyCode, WageLedgerReportQuery query) {
		return null;
	}

	/**
	 * Covert to new layout data list.
	 *
	 * @param itemList the item list
	 * @return the list
	 */
	private List<WLNewLayoutReportData> covertToNewLayoutDataList(List<Object[]> itemList) {
		return null;
	}

	/**
	 * Convert to old layout data list.
	 *
	 * @param itemList the item list
	 * @param query the query
	 * @param companyCode the company code
	 * @return the list
	 */
	private List<WLOldLayoutReportData> convertToOldLayoutDataList(List<Object[]> itemList, WageLedgerReportQuery query,
			String companyCode) {
		List<WLOldLayoutReportData> dataList = new ArrayList<>();
		MonthData monthData = new MonthData();
		// Query Header data.
		Map<String, HeaderReportData> headerDataMap = this.findHeaderDatas(companyCode, query);

		// Group by user.
		Map<String, List<Object[]>> userMap = itemList.stream()
				.collect(Collectors.groupingBy(item -> ((QstdtPaymentDetail) item[0]).qstdtPaymentDetailPK.personId));
		List<QcamtItem> masterItems = this.getEntityManager().createQuery(MASTER_ITEM_QUERY_STRING, QcamtItem.class)
				.setParameter("companyCode", companyCode)
				.getResultList();
		
		// Convert to report data model.
		for (String personId : userMap.keySet()) {
			List<Object[]> detailData = userMap.get(personId);

			// =============== Salary payment data ===========================
			// Filter result list by payment type and category.
			List<Object[]> salaryPaymentResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Payment.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Salary.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> salaryPaymentItemsMap = this
					.convertMasterResultDatasToItemMap(salaryPaymentResultList, monthData);
			PaymentData salaryPaymentData = this.convertToPaymentData(salaryPaymentItemsMap, masterItems);

			// =============== Bonus payment data ===========================
			// Filter result list by payment type and category.
			List<Object[]> bonusPaymentResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Payment.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Bonus.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> bonusPaymentItemsMap = this
					.convertMasterResultDatasToItemMap(bonusPaymentResultList, monthData);
			PaymentData bonusPaymentData = this.convertToPaymentData(bonusPaymentItemsMap, masterItems);

			// =============== Salary Deduction data ===========================
			// Filter result list by payment type and category.
			List<Object[]> salaryDeductionResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Deduction.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Salary.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> salaryDeductionItemsMap = this
					.convertMasterResultDatasToItemMap(salaryDeductionResultList, monthData);
			DeductionData salaryDeductionData = this.convertToDeductionData(salaryDeductionItemsMap, masterItems);

			// =============== Bonus Deduction data ===========================
			// Filter result list by payment type and category.
			List<Object[]> bonusDeductionResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Deduction.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Bonus.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> bonusDeductionItemsMap = this
					.convertMasterResultDatasToItemMap(bonusDeductionResultList, monthData);
			DeductionData bonusDeductionData = this.convertToDeductionData(bonusDeductionItemsMap, masterItems);

			// =============== Net salary item ===========================
			ItemData netSalaryItemData = new ItemData(PaymentType.Salary.value, ARTICLE_CATEGORY, TOTAL_REAL_ITEM_CODE);
			ReportItemDto netSalaryItem = this.findItem(detailData, masterItems, netSalaryItemData, monthData);

			// =============== Total bonus item ===========================
			ItemData totalBonusItemData = new ItemData(PaymentType.Bonus.value, ARTICLE_CATEGORY, TOTAL_REAL_ITEM_CODE);
			ReportItemDto totalBonusItem = this.findItem(detailData, masterItems, totalBonusItemData, monthData);

			// =============== Salary Attendance Data. ===========================
			// Filter result list by payment type and category.
			List<Object[]> salaryAttendanceResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Attendance.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Salary.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> salaryAttendanceItemsMap = this
					.convertMasterResultDatasToItemMap(salaryAttendanceResultList, monthData);

			// =============== Bonus Attendance Data ===========================
			// Filter result list by payment type and category.
			List<Object[]> bonusAttendanceResultList = detailData.stream().filter(res -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
				return paymentDetail.qstdtPaymentDetailPK.categoryATR == WLCategory.Attendance.value
						&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Bonus.value;
			}).collect(Collectors.toList());
			// Convert result list to item map.
			Map<QcamtItem, ReportItemDto> bonusAttendanceItemsMap = this
					.convertMasterResultDatasToItemMap(bonusAttendanceResultList, monthData);

			WLOldLayoutReportData data = WLOldLayoutReportData.builder()
					.headerData(headerDataMap.get(personId))
					.salaryPaymentData(salaryPaymentData)
					.bonusPaymentData(bonusPaymentData)
					.salaryDeductionData(salaryDeductionData)
					.bonusDeductionData(bonusDeductionData)
					.netSalaryData(netSalaryItem)
					.totalBonusData(totalBonusItem)
					.salaryAttendanceDatas(new ArrayList<>(salaryAttendanceItemsMap.values()))
					.bonusAttendanceDatas(new ArrayList<>(bonusAttendanceItemsMap.values()))
					.salaryMonthList(monthData.salaryMonths)
					.bonusMonthList(monthData.bonusMonths)
					.build();

			// Add to report data list.
			dataList.add(data);
		}
		return dataList;
	}

	/**
	 * Find item.
	 *
	 * @param results the results
	 * @param itemData the item data
	 * @param monthData the month data
	 * @return the report item dto
	 */
	private ReportItemDto findItem(List<Object[]> results, List<QcamtItem> masterItems,
			ItemData itemData, MonthData monthData) {
		
		// Find master item
		QcamtItem masterItem = masterItems.stream()
				.filter(item -> {
					return item.qcamtItemPK.itemCd.equals(itemData.itemCode);
				}).findFirst().get();

		// Filter by conditions
		List<Object[]> itemDataResultList = results.stream().filter(res -> {
			QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) res[0];
			return paymentDetail.qstdtPaymentDetailPK.categoryATR == itemData.category
					&& paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == itemData.paymentType
					&& paymentDetail.qstdtPaymentDetailPK.itemCode.equals(itemData.itemCode);
		}).collect(Collectors.toList());

		// Convert data.
		List<MonthlyData> monthlyDatas = itemDataResultList.stream().map(data -> {
			QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) data[0];
			YearMonth processYM = YearMonth.of(paymentDetail.qstdtPaymentDetailPK.processingYM);
			int month = processYM.month();

			// Add month to salary month List.
			if (itemData.paymentType == PaymentType.Salary.value && !monthData.salaryMonths.contains(month)) {
				monthData.salaryMonths.add(month);
			}
			// Add month to bonus month List.
			if (itemData.paymentType == PaymentType.Bonus.value && !monthData.bonusMonths.contains(month)) {
				monthData.bonusMonths.add(month);
			}

			return MonthlyData.builder().month(processYM.month()).amount(paymentDetail.value.longValue()).build();
		}).collect(Collectors.toList());
		
		// Return.
		return ReportItemDto.builder()
				.name(masterItem.itemName)
				.monthlyDatas(monthlyDatas)
				.build();
	}

	/**
	 * Convert to payment data.
	 *
	 * @param resultsMap the results map
	 * @return the payment data
	 */
	private PaymentData convertToPaymentData(Map<QcamtItem, ReportItemDto> resultsMap, List<QcamtItem> masterItems) {
		List<ReportItemDto> items = new ArrayList<>(resultsMap.values());
		
		// Create total tax item.
		QcamtItem totalTaxResultItem = masterItems.stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_TAX_ITEM_CODE))
				.findFirst().get();
		ReportItemDto totalTaxReportItem = resultsMap.get(totalTaxResultItem);
		items.remove(totalTaxReportItem);
		if (totalTaxReportItem == null) {
			totalTaxReportItem = new ReportItemDto(totalTaxResultItem.itemName);
		}

		// Create total tax exemption item.
		QcamtItem totalTaxExemptionResultItem = masterItems.stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_TAX_EXEMPTION_ITEM_CODE))
				.findFirst().get();
		ReportItemDto totalTaxExemptionReportItem = resultsMap.get(totalTaxExemptionResultItem);
		items.remove(totalTaxExemptionReportItem);
		if (totalTaxExemptionReportItem == null) {
			totalTaxExemptionReportItem = new ReportItemDto(totalTaxExemptionResultItem.itemName);
		}

		// Create total salary payment item.
		QcamtItem totalSalaryPaymentResultItem = masterItems.stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_PAYMENT_ITEM_CODE)).findFirst().get();
		ReportItemDto totalSalaryPaymentReportItem = resultsMap.get(totalSalaryPaymentResultItem);
		items.remove(totalSalaryPaymentReportItem);
		if (totalSalaryPaymentReportItem == null) {
			totalSalaryPaymentReportItem = new ReportItemDto(totalSalaryPaymentResultItem.itemName);
		}
		
		return PaymentData.builder()
				.aggregateItemList(items)
				.totalTax(totalTaxReportItem)
				.totalTaxExemption(totalTaxExemptionReportItem)
				.totalSalaryPayment(totalSalaryPaymentReportItem)
				.build();
	}

	/**
	 * Convert to deduction data.
	 *
	 * @param resultsMap the results map
	 * @return the deduction data
	 */
	private DeductionData convertToDeductionData(Map<QcamtItem, ReportItemDto> resultsMap, List<QcamtItem> masterItems) {
		List<ReportItemDto> items = new ArrayList<>(resultsMap.values());

		// Create total deduction item.
		QcamtItem totalDeductionItem = masterItems.stream()
				.filter(item -> item.qcamtItemPK.itemCd.equals(TOTAL_DEDUCTION_ITEM_CODE)).findFirst().get();
		ReportItemDto totalDeductionReportItem = resultsMap.get(totalDeductionItem);
		items.remove(totalDeductionReportItem);
		if (totalDeductionReportItem == null) {
			totalDeductionReportItem = new ReportItemDto(totalDeductionItem.itemName);
		}

		return DeductionData.builder()
				.aggregateItemList(items)
				.totalDeduction(totalDeductionReportItem)
				.build();
	}

	/**
	 * Convert master result datas to item map.
	 *
	 * @param results the results
	 * @param monthData the month data
	 * @return the map
	 */
	private Map<QcamtItem, ReportItemDto> convertMasterResultDatasToItemMap(List<Object[]> results,
			MonthData monthData) {

		Map<QcamtItem, ReportItemDto> items = new HashMap<>();
		// Group result list by master item.
		Map<QcamtItem, List<Object[]>> itemListMap = results.stream()
				.collect(Collectors.groupingBy(res -> (QcamtItem) res[1]));
		itemListMap.forEach((masterItem, datas) -> {
			List<MonthlyData> monthlyDatas = datas.stream().map(data -> {
				QstdtPaymentDetail paymentDetail = (QstdtPaymentDetail) data[0];
				YearMonth processYM = YearMonth.of(paymentDetail.qstdtPaymentDetailPK.processingYM);
				int month = processYM.month();

				// Add month to salary month List.
				if (paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Salary.value
						&& !monthData.salaryMonths.contains(month)) {
					monthData.salaryMonths.add(month);
				}
				// Add month to bonus month List.
				if (paymentDetail.qstdtPaymentDetailPK.payBonusAttribute == PaymentType.Bonus.value
						&& !monthData.bonusMonths.contains(month)) {
					monthData.bonusMonths.add(month);
				}

				return MonthlyData.builder().month(month).amount(paymentDetail.value.longValue()).build();
			}).collect(Collectors.toList());
			items.put(masterItem, ReportItemDto.builder().name(masterItem.itemName).monthlyDatas(monthlyDatas).build());
		});

		return items;
	}

	/**
	 * Find header datas.
	 *
	 * @param companyCode the company code
	 * @param queryData the query data
	 * @return the map
	 */
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
		
		// Query position of employee.
		List<String> jobCodes = headerDatasResult.stream().map(data -> {
			
			PclmtPersonTitleRgl personTitle = (PclmtPersonTitleRgl) data[4];
			return personTitle.getJobcd();
		}).distinct().collect(Collectors.toList());

		List<CmnmtJobTitle> jobList = em.createQuery(POSITION_QUERY_STRING, CmnmtJobTitle.class)
				.setParameter("companyCode", companyCode)
				.setParameter("jobCode", jobCodes)
				.setParameter("baseDate", queryData.baseDate)
				.getResultList();
		Map<String, CmnmtJobTitle> jobMap = jobList.stream()
				.collect(Collectors.toMap(job -> job.cmnmtJobTitlePK.jobCode, Function.identity()));

		// Convert to header data model.
		return headerDatasResult.stream().collect(Collectors.toMap(key -> {
			PbsmtPersonBase personBase = (PbsmtPersonBase) key[0];
			return personBase.getPid();
		}, data -> {
			PbsmtPersonBase personBase = (PbsmtPersonBase) data[0];
			PcpmtPersonCom personCompany = (PcpmtPersonCom) data[1];
			PogmtPersonDepRgl personDepartment = (PogmtPersonDepRgl) data[2];
			CmnmtDep departmentMaster = (CmnmtDep) data[3];
			PclmtPersonTitleRgl personTitle = (PclmtPersonTitleRgl) data[4];
			CmnmtJobTitle jobTitle = jobMap.get(personTitle.getJobcd());
			
			// Build Header Data.
			HeaderReportData headerReportData = HeaderReportData.builder().employeeCode(personCompany.getScd())
					.employeeName(personBase.getNameOfficial()).sex(personBase.getGendar() == 0 ? "男性" : "女性")
					.departmentCode(personDepartment.getDepcd()).departmentName(departmentMaster.getDepName())
					.targetYear(queryData.targetYear).position(jobTitle.jobName).build();
			return headerReportData;
		}));
	}

	/**
	 * The Class MonthData.
	 */
	public class MonthData {
		
		/** The salary months. */
		public List<Integer> salaryMonths;
		
		/** The bonus months. */
		public List<Integer> bonusMonths;

		/**
		 * Instantiates a new month data.
		 */
		public MonthData() {
			this.salaryMonths = new ArrayList<>();
			this.bonusMonths = new ArrayList<>();
		}
	}

	/**
	 * The Class ItemData.
	 */
	public class ItemData {
		
		/** The payment type. */
		public int paymentType;

		/** The category. */
		public int category;

		/** The item code. */
		public String itemCode;

		/**
		 * Instantiates a new item data.
		 *
		 * @param paymentType the payment type
		 * @param category the category
		 * @param itemCode the item code
		 */
		public ItemData(int paymentType, int category, String itemCode) {
			super();
			this.paymentType = paymentType;
			this.category = category;
			this.itemCode = itemCode;
		}
	}
}
