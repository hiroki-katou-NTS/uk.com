/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.pr.file.infra.detailpaymentsalary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryItemType;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItem;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemRepository;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryCategorySetting;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputItem;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.outputsetting.SalaryOutputSettingRepository;
import nts.uk.file.pr.app.export.detailpaymentsalary.PaySalaryReportRepository;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery;

/**
 * The Class JpaPaymentSalaryReportRepository.
 */
@Stateless
public class JpaPaySalaryReportRepository extends JpaRepository implements PaySalaryReportRepository {
    
    private static final String QUERY_CHECK_DATA = "SELECT header, detail "
            + "FROM QstdtPaymentHeader header, "
            + "QstdtPaymentDetail detail "
            + "WHERE header.qstdtPaymentHeaderPK.companyCode = :companyCode "
            + "AND header.qstdtPaymentHeaderPK.personId IN :personIds "
            + "AND header.qstdtPaymentHeaderPK.payBonusAtr = 0 "
            + "AND header.qstdtPaymentHeaderPK.processingYM >= :startYM "
            + "AND header.qstdtPaymentHeaderPK.processingYM <= :endYM "
            + "AND detail.qstdtPaymentDetailPK.companyCode = header.qstdtPaymentHeaderPK.companyCode "
            + "AND detail.qstdtPaymentDetailPK.personId = header.qstdtPaymentHeaderPK.personId "
            + "AND detail.qstdtPaymentDetailPK.payBonusAttribute = 0 "
            + "AND detail.qstdtPaymentDetailPK.processingYM >= :startYM "
            + "AND detail.qstdtPaymentDetailPK.processingYM <= :endYM ";
    
    private static final String QUERY_PAYMENT_DETAIL = "SELECT pCom.scd, detail.qstdtPaymentDetailPK.categoryATR,"
            + "detail.qstdtPaymentDetailPK.itemCode, item.itemName,"
            + "detail.qstdtPaymentDetailPK.processingYM, detail.value "
            + "FROM PbsmtPersonBase pBase "
            + "LEFT JOIN PcpmtPersonCom pCom ON "
            + "pCom.pcpmtPersonComPK.pid = pBase.pid "
            + "LEFT JOIN QstdtPaymentDetail detail ON "
            + "detail.qstdtPaymentDetailPK.personId = pCom.pcpmtPersonComPK.pid "
            + "LEFT JOIN QcamtItem item ON "
            + "item.qcamtItemPK.ccd = detail.qstdtPaymentDetailPK.companyCode "
            + "AND item.qcamtItemPK.itemCd = detail.qstdtPaymentDetailPK.itemCode "
            + "AND item.qcamtItemPK.ctgAtr = detail.qstdtPaymentDetailPK.categoryATR "
            + "WHERE pBase.pid IN :personIds "
            + "AND pCom.pcpmtPersonComPK.ccd = :companyCode "
            + "AND detail.qstdtPaymentDetailPK.companyCode = pCom.pcpmtPersonComPK.ccd "
            + "AND detail.qstdtPaymentDetailPK.payBonusAttribute = 0 "
            + "AND detail.qstdtPaymentDetailPK.processingYM >= :startYM "
            + "AND detail.qstdtPaymentDetailPK.processingYM <= :endYM "
            + "AND detail.qstdtPaymentDetailPK.sparePayAttribute = 0 "
            + "AND detail.qstdtPaymentDetailPK.itemCode IN :itemCodes";
    
    private static final int QUERY_NUMER_EMPLOYEE_LIMIT = 1000;
    
    @Inject
    private SalaryOutputSettingRepository outputSettingRepo;
    
    @Inject
    private ItemMasterRepository masterItemRepo;
    
    @Inject
    private SalaryAggregateItemRepository aggrItemRepo;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.file.pr.app.export.detailpaymentsalary.PaySalaryReportRepository#
     * isAvailableData(java.lang.String,
     * nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean isAvailableData(String companyCode, PaymentSalaryQuery paySalaryQuery) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery(QUERY_CHECK_DATA);
        query.setParameter("companyCode", companyCode);
        query.setParameter("startYM", paySalaryQuery.getStartDate());
        query.setParameter("endYM", paySalaryQuery.getEndDate());
        
        List<Object[]> dataResult = new ArrayList<>();
        CollectionUtil.split(paySalaryQuery.getPersonIds(), QUERY_NUMER_EMPLOYEE_LIMIT, (subList) -> {
            dataResult.addAll(query.setParameter("personIds", subList).getResultList());
        });
        if (dataResult.isEmpty()) {
            return false;
        }
        return false;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.file.pr.app.export.detailpaymentsalary.PaySalaryReportRepository#
     * findReportData(java.lang.String,
     * nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery)
     */
    public PaymentSalaryReportData findReportData(String companyCode, PaymentSalaryQuery paySalaryQuery) {
        // ======================== FIND INFORMATION EMPLOYEE ========================
        // TODO: find list department which is sorted department level.
        List<EmployeeDto> emps = new ArrayList<>();
        
        // ======================== FIND MAP AMOUTN EMPLOYEE ========================
        SalaryOutputSetting outputSetting = outputSettingRepo.findByCode(companyCode,
                paySalaryQuery.getOutputSettingCode());
        
        List<ItemMaster> masterItems = masterItemRepo.findAll(companyCode);
        
        List<SalaryAggregateItem> aggrItems = aggrItemRepo.findAll(companyCode);
        
        // ========= FIND MAP ITEM =========
        Map<CategoryItem, List<String>> mapItem = findMapItem(outputSetting, masterItems, aggrItems);
        
        // ========= FIND ITEM CODE =========
        List<String> itemCodes = mapItem.entrySet()
                .stream()
                .flatMap(item -> item.getValue().stream())
                .collect(Collectors.toList());
        
        // ========= FIND PAYMENT DETAIl =========
        List<Object[]> listObject = findPaymentDetail(companyCode, paySalaryQuery, itemCodes);
        Map<String, List<Object[]>> mapEmp = listObject.stream()
                .collect(Collectors.groupingBy(item -> (String)item[0]));
        
        Map<EmployeeKey, Double> mapEmpAmount = new HashMap<>();
        
        // ========= FIND MAP AMOUNT OF EMPLOYEE =========
        for (String codeEmp : mapEmp.keySet()) {
            List<Object[]> listDetail = mapEmp.get(codeEmp);
            Map<Integer, List<Object[]>> mapAmountByYm = listDetail.stream()
                    .collect(Collectors.groupingBy(item -> (int) item[4]));
            
            filterData(codeEmp, mapAmountByYm, mapItem, mapEmpAmount);
        }
        // ========= SET REPORT DATA =========
        PaymentSalaryReportData reportData = new PaymentSalaryReportData();
        reportData.setEmployees(emps);
        reportData.setMapEmployeeAmount(mapEmpAmount);
        return reportData;
    }
    
    private void filterData(String codeEmp, Map<Integer, List<Object[]>> mapAmountByYm, Map<CategoryItem, List<String>> mapItem,
            Map<EmployeeKey, Double> mapAmount) {
        
        mapAmountByYm.forEach((yearMonth, objectList) -> {
            mapItem.forEach((categoryItem, subItemCodes) -> {
                
                EmployeeKey key = new EmployeeKey();
                key.setYearMonth(yearMonth);
                key.setEmployeeCode(codeEmp);
                key.setItemName(categoryItem.itemName);
                key.setSalaryCategory(SalaryCategory.valueOf(categoryItem.category.value));
                
                List<Object[]> objectFiltereds = objectList.stream()
                        .filter(ob -> {
                            return yearMonth == (int) ob[4] && (int) ob[1] == categoryItem.category.value 
                                    && subItemCodes.contains((String) ob[2]);
                        })
                        .collect(Collectors.toList());
                
                double amount = 0;
                
                if (!CollectionUtil.isEmpty(objectFiltereds)) {
                    Object[] detail = objectFiltereds.get(0);
                    // item master
                    if (objectFiltereds.size() == 1) {
                        amount = ((BigDecimal) detail[5]).doubleValue();
                    } else {
                        amount = objectFiltereds.stream()
                                .mapToDouble(ob -> ((BigDecimal) ob[5]).doubleValue())
                                .sum();
                    }
                }
                mapAmount.put(key, amount);
            });
        });
    }
    
    private Map<CategoryItem, List<String>> findMapItem(SalaryOutputSetting outputSetting, List<ItemMaster> masterItems,
            List<SalaryAggregateItem> aggrItems) {
        Map<CategoryItem, List<String>> mapItem = new HashMap<>();
        
        for (SalaryCategorySetting category : outputSetting.getCategorySettings()) {
            for (SalaryOutputItem item : category.getItems()) {
                String itemCode = item.getLinkageCode();
                
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.itemCode = itemCode;
                categoryItem.category = category.getCategory();
                
                // ========= MASTER ITEM =========
                if (item.getType() == SalaryItemType.Master) {
                    categoryItem.itemName = masterItems.stream()
                            .filter(masterItem -> {
                                return masterItem.getCategoryAtr().value == categoryItem.category.value
                                        && masterItem.getItemCode().v().equals(categoryItem.itemCode);
                            })
                            .map(masterItem -> masterItem.getItemName().v())
                            .findFirst()
                            .get();
                    mapItem.put(categoryItem, Arrays.asList(itemCode));
                }
                // ========= AGGREGATE ITEM =========
                else {
                    List<String> subMasterItemCodes = aggrItems.stream()
                            .filter(aggr -> {
                                if (aggr.getHeader().getTaxDivision().value == category.getCategory().value
                                        && aggr.getHeader().getAggregateItemCode().v().equals(itemCode)) {
                                    return true;
                                }
                                return false;
                            })
                            .flatMap(aggr -> {
                                return aggr.getSubItemCodes().stream()
                                        .map(aggrItem -> aggrItem.getSalaryItemCode());
                            })
                            .collect(Collectors.toList());
                    categoryItem.itemName = aggrItems.stream()
                            .filter(aggr -> {
                                if (aggr.getHeader().getTaxDivision().value == category.getCategory().value
                                        && aggr.getHeader().getAggregateItemCode().v().equals(itemCode)) {
                                    return true;
                                }
                                return false;
                            })
                            .map(aggr -> aggr.getName().v())
                            .findFirst()
                            .get();
                    mapItem.put(categoryItem, subMasterItemCodes);
                }
            }
        }
        return mapItem;
    }
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<Object[]> findPaymentDetail(String companyCode, PaymentSalaryQuery payQuery,
            List<String> itemCodes) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery(QUERY_PAYMENT_DETAIL);
        query.setParameter("personIds", payQuery.getPersonIds());
        query.setParameter("companyCode", companyCode);
        query.setParameter("startYM", payQuery.getStartDate());
        query.setParameter("endYM", payQuery.getEndDate());
        query.setParameter("itemCodes",itemCodes );
        List select = query.getResultList();
        if (select.isEmpty()) {
            throw new BusinessException("ER010");
        }
        return select;
    }
    
    class CategoryItem {
        private String itemCode;
        private String itemName;
        private SalaryCategory category;
    }
}
