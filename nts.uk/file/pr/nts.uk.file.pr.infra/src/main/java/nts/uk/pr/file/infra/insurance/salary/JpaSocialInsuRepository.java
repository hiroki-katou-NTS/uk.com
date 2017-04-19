/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.pr.file.infra.insurance.salary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.infra.entity.organization.employment.CmnmtEmp;
import nts.uk.ctx.basic.infra.entity.report.PbsmtPersonBase;
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuCare;
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuHealB;
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuPensB;
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuSocial;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.QismtSocialInsuOffice;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthrate.QismtHealthInsuRate;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.core.infra.entity.personalinfo.employmentcontract.PclmtPersonEmpContract;
import nts.uk.file.pr.app.export.insurance.data.DataRowItem;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.MLayoutInsuOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.MLayoutRowItem;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuMLayoutReportData;
import nts.uk.file.pr.app.export.insurance.data.SocialInsuReportData;
import nts.uk.file.pr.app.export.insurance.salary.SocialInsuQuery;
import nts.uk.file.pr.app.export.insurance.salary.SocialInsuRepository;

/**
 * The Class JpaSalarySocialInsuranceRepository.
 *
 */

@Stateless
public class JpaSocialInsuRepository extends JpaRepository implements SocialInsuRepository {
    
    /** The Constant FIND_PERSON_OFFICES. */
    private static final String FIND_PERSON_OFFICES = "SELECT so, ps "
            + "FROM QismtSocialInsuOffice so, "
            + "QpdptPayday pp, "
            + "QpdmtPayday mp, "
            + "PismtPersonInsuSocial ps "
            + "WHERE so.qismtSocialInsuOfficePK.ccd = :companyCode "
            + "AND so.qismtSocialInsuOfficePK.siOfficeCd IN :officeCodes "
            + "AND pp.qpdptPaydayPK.ccd = so.qismtSocialInsuOfficePK.ccd "
            + "AND pp.qpdptPaydayPK.pid = :loginPid "
            + "AND mp.qpdmtPaydayPK.ccd = pp.qpdptPaydayPK.ccd "
            + "AND mp.qpdmtPaydayPK.processingNo = pp.processingNo "
            + "AND mp.qpdmtPaydayPK.payBonusAtr = 0 "
            + "AND mp.qpdmtPaydayPK.processingYm = :yearMonth "
            + "AND mp.qpdmtPaydayPK.sparePayAtr = 0 "
            + "AND ps.pismtPersonInsuSocialPK.ccd = mp.qpdmtPaydayPK.ccd "
            + "AND ps.strD <= :baseDate "
            + "AND ps.endD >= :baseDate "
            + "AND ps.siOfficeCd = so.qismtSocialInsuOfficePK.siOfficeCd ";
    
    /** The Constant FIND_PERSON_NORMAL. */
    private static final String FIND_PERSON_NORMAL = "SELECT pb, pc, pec, ce, pic, phb, ppb "
            + "FROM PbsmtPersonBase pb, "
            + "PcpmtPersonCom pc, "
            + "PclmtPersonEmpContract pec, "
            + "CmnmtEmp ce, "
            + "QpdmtPayday mp, "
            + "PismtPersonInsuCare pic, "
            + "PismtPersonInsuHealB phb,"
            + "PismtPersonInsuPensB ppb "
            + "WHERE pb.pid IN :personIds "
            + "AND pc.pcpmtPersonComPK.pid = pb.pid "
            + "AND pc.pcpmtPersonComPK.ccd = :companyCode "
            + "AND pec.pclmtPersonEmpContractPK.ccd = pc.pcpmtPersonComPK.ccd "
            + "AND pec.pclmtPersonEmpContractPK.pId = pb.pid "
            + "AND pec.pclmtPersonEmpContractPK.strD <= :baseDate "
            + "AND pec.endD >= :baseDate "
            + "AND ce.cmnmtEmpPk.companyCode = pc.pcpmtPersonComPK.ccd "
            + "AND ce.cmnmtEmpPk.employmentCode = pec.empCd "
            + "AND mp.qpdmtPaydayPK.ccd = pc.pcpmtPersonComPK.ccd "
            + "AND mp.qpdmtPaydayPK.processingNo = ce.processingNo "
            + "AND mp.qpdmtPaydayPK.payBonusAtr = 0 "
            + "AND mp.qpdmtPaydayPK.processingYm = :yearMonth "
            + "AND mp.qpdmtPaydayPK.sparePayAtr = 0 "
            + "AND pic.pismtPersonInsuCarePK.ccd = pc.pcpmtPersonComPK.ccd "
            + "AND pic.pismtPersonInsuCarePK.pid = pb.pid "
            + "AND pic.strD <= mp.socialInsStdDate "
            + "AND pic.endD >= mp.socialInsStdDate "
            + "AND phb.pismtPersonInsuHealBPK.ccd = pc.pcpmtPersonComPK.ccd "
            + "AND phb.pismtPersonInsuHealBPK.pid = pb.pid "
            + "AND phb.strYm <= :yearMonth "
            + "AND phb.endYm >= :yearMonth "
            + "AND ppb.pismtPersonInsuPensBPK.ccd = pc.pcpmtPersonComPK.ccd "
            + "AND ppb.pismtPersonInsuPensBPK.pid = pb.pid "
            + "AND ppb.strYm <= :yearMonth "
            + "AND ppb.endYm >= :yearMonth ";
    
    /** The Constant FIND_PERSON_DEDUCTION. */
    private static final String FIND_PERSON_DEDUCTION = "SELECT ph, pd, hr, pr "
            + "FROM QstdtPaymentHeader ph, "
            + "QstdtPaymentDetail pd, "
            + "QismtHealthInsuRate hr, "
            + "QismtPensionRate pr "
            + "WHERE ph.qstdtPaymentHeaderPK.companyCode = :companyCode "
            + "AND ph.qstdtPaymentHeaderPK.personId IN :personIds "
            + "AND ph.qstdtPaymentHeaderPK.payBonusAtr = 0 "
            + "AND ph.qstdtPaymentHeaderPK.processingYM = :yearMonth "
            + "AND pd.qstdtPaymentDetailPK.personId = ph.qstdtPaymentHeaderPK.personId "
            + "AND pd.qstdtPaymentDetailPK.processingNo = ph.qstdtPaymentHeaderPK.processingNo "
            + "AND pd.qstdtPaymentDetailPK.payBonusAttribute = 0 "
            + "AND pd.qstdtPaymentDetailPK.processingYM = ph.qstdtPaymentHeaderPK.processingYM "
            + "AND pd.qstdtPaymentDetailPK.sparePayAttribute = ph.qstdtPaymentHeaderPK.sparePayAtr "
            + "AND pd.qstdtPaymentDetailPK.categoryATR = 1 "
            + "AND pd.qstdtPaymentDetailPK.itemCode IN :itemCodes "
            + "AND hr.ccd = ph.qstdtPaymentHeaderPK.companyCode "
            + "AND hr.siOfficeCd = :officeCode "
            + "AND hr.strYm <= :yearMonth "
            + "AND hr.endYm >= :yearMonth "
            + "AND pr.ccd = ph.qstdtPaymentHeaderPK.companyCode "
            + "AND pr.siOfficeCd = hr.siOfficeCd "
            + "AND pr.strYm <= :yearMonth "
            + "AND pr.endYm >= :yearMonth ";
    
    /** The Constant DATE_FORMAT. */
    private static final String DATE_FORMAT = "yyyyMMdd";
    
    /** The Constant FIRST_DAY. */
    private static final String FIRST_DAY = "01";
    
    /** The list item code. */
    private static List<String> LIST_ITEM_CODE = Arrays.asList("F101", "F102", "F103", "F109", "F110", "F111", "F112",
            "F115", "F116", "F117", "F118", "F119", "F120", "F121", "F124");
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.file.pr.app.export.insurance.salary.
     * SalarySocailInsuranceRepository#fincReportData(nts.uk.file.pr.app.export.
     * insurance.salary.SalarySocialInsuranceQuery)
     */
    @Override
    public List<SocialInsuReportData> findReportData(String companyCode, String loginPersonId,
            SocialInsuQuery salaryQuery, List<HealthInsuranceAvgearn> healInsuAvgearns,
            List<PensionAvgearn> pensionAvgearns) {
        ReportData reportData = new ReportData();
        reportData.listHealInsuAvgearn = healInsuAvgearns;
        reportData.listPensAvgearn = pensionAvgearns;
        List<String> officeCodes = salaryQuery.getInsuranceOffices().stream()
                .map(p -> p.getCode())
                .collect(Collectors.toList());
        int yearMonth = salaryQuery.getYearMonth();
        List<Object[]> itemList = findOffice(companyCode, officeCodes, yearMonth, loginPersonId);
        if (itemList.isEmpty()) {
            throw new BusinessException("対象データがありません。");
        }
        Map<Object, List<Object[]>> mapOffice = itemList.stream()
                .collect(Collectors.groupingBy(item -> ((QismtSocialInsuOffice) item[0]))
                 );
        List<InsuranceOfficeDto> listPersonal = new ArrayList<>();
        List<InsuranceOfficeDto> listBusiness = new ArrayList<>();
        ItemDeduction itemDeductionPersonal = initDeductionPersonal();
        ItemDeduction itemDeductionBusiness = initDeductionBusiness();
        double deliveryAmount = 0;
        
        // ============== FIND INFROMATION OFFICE ==================
        for (Object object : mapOffice.keySet()) {
            QismtSocialInsuOffice office = (QismtSocialInsuOffice) object;
            List<Object[]> listDetail = mapOffice.get(object);
            
            // find list employee in a office.
            List<String> personIds = listDetail.stream()
                    .map(p -> {
                        PismtPersonInsuSocial entity = (PismtPersonInsuSocial) p[1];
                        return entity.getPismtPersonInsuSocialPK().getPid();
                    })
                    .distinct()
                    .collect(Collectors.toList());
            String officeCode = office.getQismtSocialInsuOfficePK().getSiOfficeCd();
            // find information of employees monthly
            reportData.objectNormals = findPersonNormal(companyCode, officeCode, personIds, yearMonth);
            // find information deduction of employees monthly 
            reportData.objectDeductions = findPersonDeduction(companyCode, officeCode, personIds, yearMonth);
            
            if (reportData.objectNormals.isEmpty() || reportData.objectDeductions.isEmpty()) {
                throw new BusinessException("対象データがありません。");
            }
            List<DataRowItem> listRowItemPersonal = new ArrayList<>();
            List<DataRowItem> listRowItemBusiness = new ArrayList<>();
            // list employee is printed output.
            List<String> tmpPersonIds = new ArrayList<>();
            // ============== FIND INFROMATION EMPLOYEE ==================
            for (String personId : personIds) {
                // set row item for business.
                DataRowItem rowItemBusiness = convertNormalToDataItem(reportData, personId, true);
                // rowItemBusiness is passed reference parameter.
                convertDeductionToDataItem(reportData, rowItemBusiness, personId, itemDeductionBusiness);
                listRowItemBusiness.add(rowItemBusiness);
                
                // set row item for personal.
                DataRowItem rowItemPersonal = convertNormalToDataItem(reportData, personId, false);
                // rowItemPersonal is passed reference parameter.
                convertDeductionToDataItem(reportData, rowItemPersonal, personId, itemDeductionPersonal);
                // valid output condition.
                if (isPrintedPersonal(rowItemPersonal, salaryQuery)) {
                    listRowItemPersonal.add(rowItemPersonal);
                    tmpPersonIds.add(personId);
                }
            }
            personIds.clear();
            // adding employees is printed.
            personIds.addAll(tmpPersonIds);
            
            // set data insurance office for personal.
            InsuranceOfficeDto officePersonal = new InsuranceOfficeDto();
            officePersonal.setCode(officeCode);
            officePersonal.setName(office.getSiOfficeName());
            officePersonal.setEmployeeDtos(listRowItemPersonal);
            officePersonal.setNumberOfEmployee(listRowItemPersonal.size());
            // set information office of personal.
            listPersonal.add(officePersonal);
            
            // set data insurance office for business.
            InsuranceOfficeDto officeBusiness = new InsuranceOfficeDto();
            officeBusiness.setCode(officeCode);
            officeBusiness.setName(office.getSiOfficeName());
            officeBusiness.setEmployeeDtos(listRowItemBusiness);
            officeBusiness.setNumberOfEmployee(listRowItemBusiness.size());
            // set information office of business.
            listBusiness.add(officeBusiness);
            
            // calculate delivery amount.
            deliveryAmount += calDeliveryAmount(reportData, personIds);
        }
        List<SocialInsuReportData> listReport = new ArrayList<>();
        // set list office for personal.
        SocialInsuReportData reportPersonal = new SocialInsuReportData();
        reportPersonal.setOfficeItems(listPersonal);
        reportPersonal.setDeliveryNoticeAmount(deliveryAmount);
        listReport.add(reportPersonal);
        
        // set list office for business.
        SocialInsuReportData reportBusiness = new SocialInsuReportData();
        reportBusiness.setOfficeItems(listBusiness);
        listReport.add(reportBusiness);
        
        return listReport;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.file.pr.app.export.insurance.salary.
     * SalarySocialInsuranceRepository#findReportMLayout(java.lang.String,
     * java.lang.String,
     * nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceQuery,
     * java.util.List, java.util.List)
     */
    @Override
    public SocialInsuMLayoutReportData findReportMLayout(String companyCode, String loginPid,
            SocialInsuQuery salaryQuery, List<HealthInsuranceAvgearn> healInsuAvgearns,
            List<PensionAvgearn> pensionAvgearns) {
        ReportData reportData = new ReportData();
        reportData.listHealInsuAvgearn = healInsuAvgearns;
        reportData.listPensAvgearn = pensionAvgearns;
        
        List<String> officeCodes = salaryQuery.getInsuranceOffices().stream()
                .map(p -> p.getCode())
                .collect(Collectors.toList());
        int yearMonth = salaryQuery.getYearMonth();
        List<Object[]> itemList = findOffice(companyCode, officeCodes, yearMonth, loginPid);
        if (itemList.isEmpty()) {
            throw new BusinessException("対象データがありません。");
        }
        Map<Object, List<Object[]>> mapOffice = itemList.stream()
                .collect(Collectors.groupingBy(item -> ((QismtSocialInsuOffice) item[0]))
                 );
        ItemDeduction itemDePersonal = initDeductionPersonal();
        ItemDeduction itemDeBusiness = initDeductionBusiness();
        double deliveryAmount = 0;
        List<MLayoutInsuOfficeDto> listOffice = new ArrayList<>();
        
        // ============== FIND INFROMATION OFFICE ==================
        for (Object object : mapOffice.keySet()) {
            QismtSocialInsuOffice office = (QismtSocialInsuOffice) object;
            List<Object[]> listDetail = mapOffice.get(object);
            List<String> personIds = listDetail.stream()
                    .map(p -> {
                        PismtPersonInsuSocial entity = (PismtPersonInsuSocial) p[1];
                        return entity.getPismtPersonInsuSocialPK().getPid();
                    })
                    .distinct()
                    .collect(Collectors.toList());
            String officeCode = office.getQismtSocialInsuOfficePK().getSiOfficeCd();
            // find information of employees monthly
            reportData.objectNormals = findPersonNormal(companyCode, officeCode, personIds, yearMonth);
            // find information deduction of employees monthly
            reportData.objectDeductions = findPersonDeduction(companyCode, officeCode, personIds, yearMonth);
            
            if (reportData.objectNormals.isEmpty() || reportData.objectDeductions.isEmpty()) {
                throw new BusinessException("対象データがありません。");
            }
            List<MLayoutRowItem> listRowItem = new ArrayList<>();
            
            // ============== FIND INFROMATION EMPLOYEE ==================
            for (String personId : personIds) {
                MLayoutRowItem emp = convertMLayoutItem(reportData, personId, itemDePersonal, itemDeBusiness);
                // valid print output condition
                if (isPrintedPersonalMLayout(emp, salaryQuery)) {
                    listRowItem.add(emp);
                }
            }
            MLayoutInsuOfficeDto officeMLayout = new MLayoutInsuOfficeDto();
            officeMLayout.setCode(officeCode);
            officeMLayout.setName(office.getSiOfficeName());
            officeMLayout.setNumberOfEmployee(personIds.size());
            officeMLayout.setEmployees(listRowItem);
            
            listOffice.add(officeMLayout);
            // calculate delivery amount.
            deliveryAmount += calDeliveryAmount(reportData, personIds);
        }
        SocialInsuMLayoutReportData report = new SocialInsuMLayoutReportData();
        report.setOfficeItems(listOffice);
        report.setDeliveryNoticeAmount(deliveryAmount);
        
        return report;
    }
    
    /**
     * Find office.
     *
     * @param companyCode the company code
     * @param officeCodes the office codes
     * @param yearMonth the year month
     * @param loginPersonId the login person id
     * @return the list
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> findOffice(String companyCode, List<String> officeCodes, Integer yearMonth,
            String loginPersonId) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery(FIND_PERSON_OFFICES);
        query.setParameter("companyCode", companyCode);
        query.setParameter("officeCodes", officeCodes);
        query.setParameter("loginPid", loginPersonId);
        query.setParameter("yearMonth", yearMonth);
        String tmpDate = yearMonth.toString().concat(FIRST_DAY);
        GeneralDate baseDate = GeneralDate.fromString(tmpDate, DATE_FORMAT);
        query.setParameter("baseDate", baseDate);
        return query.getResultList();
    }
    
    /**
     * Find person normal.
     *
     * @param companyCode the company code
     * @param officeCode the office code
     * @param personIds the person ids
     * @param yearMonth the year month
     * @return the list
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> findPersonNormal(String companyCode, String officeCode, List<String> personIds,
            Integer yearMonth) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery(FIND_PERSON_NORMAL);
        query.setParameter("personIds", personIds);
        query.setParameter("companyCode", companyCode);
        String tmpDate = yearMonth.toString().concat(FIRST_DAY);
        GeneralDate baseDate = GeneralDate.fromString(tmpDate, DATE_FORMAT);
        query.setParameter("baseDate", baseDate);
        query.setParameter("yearMonth", yearMonth);
        return query.getResultList(); 
    }
    
    /**
     * Find person deduction.
     *
     * @param companyCode the company code
     * @param officeCode the office code
     * @param personIds the person ids
     * @param yearMonth the year month
     * @return the list
     */
    @SuppressWarnings("unchecked")
    private List<Object[]> findPersonDeduction(String companyCode, String officeCode, List<String> personIds,
            Integer yearMonth) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery(FIND_PERSON_DEDUCTION);
        query.setParameter("personIds", personIds);
        query.setParameter("companyCode", companyCode);
        query.setParameter("yearMonth", yearMonth);
        query.setParameter("itemCodes", LIST_ITEM_CODE);
        query.setParameter("officeCode", officeCode);
        return query.getResultList();
    }
    
    /**
     * Cal delivery amount.
     *
     * @param reportData the report data
     * @param personIds the person ids
     * @return the double
     */
    private double calDeliveryAmount(ReportData reportData, List<String> personIds) {
        double deliveryAmount = 0;
        for (String personId : personIds) {
            Object[] objects = reportData.objectNormals.stream()
                    .filter(p -> {
                        PbsmtPersonBase baseEntity = (PbsmtPersonBase) p[0];
                        return personId.equals(baseEntity.getPid());
                    })
                    .findFirst()
                    .get();
            Optional<QismtHealthInsuRate> objectOptional = reportData.objectDeductions.stream()
                    .filter(p -> {
                        QstdtPaymentHeader headerEntity = (QstdtPaymentHeader) p[0];
                        return personId.equals(headerEntity.qstdtPaymentHeaderPK.personId);
                    })
                    .map(p -> (QismtHealthInsuRate) p[2])
                    .findFirst();
            if (!objectOptional.isPresent()) {
                continue;
            }
            QismtHealthInsuRate healInsuRate = objectOptional.get();
            PismtPersonInsuCare penInsuCare = (PismtPersonInsuCare) objects[4];
            PismtPersonInsuHealB penInsuHeal = (PismtPersonInsuHealB) objects[5];
            PismtPersonInsuPensB penInsuPens = (PismtPersonInsuPensB) objects[6];
            double avgEearnTarget = 0;
            double avgEearnNonTarget = 0;
            if (penInsuCare.getCareInsuAtr() == 1 || penInsuCare.getCareInsuAtr() == 2) {
                avgEearnTarget = penInsuHeal.getHealthInsuAvgEarn();
            } else {
                avgEearnNonTarget = penInsuPens.getPensionAvgEarn();
            }
            double generalRate = healInsuRate.getPPayGeneralRate().doubleValue() 
                    + healInsuRate.getCPayGeneralRate().doubleValue();
            double nursingRate = healInsuRate.getPPayNursingRate().doubleValue() 
                    + healInsuRate.getCPayNursingRate().doubleValue();
            deliveryAmount = avgEearnTarget * generalRate + avgEearnNonTarget * (generalRate + nursingRate);
        }
        return deliveryAmount;
    }
    
    /**
     * Convert normal to data item.
     *
     * @param reportData the report data
     * @param personId the person id
     * @param isFindBusiness the is find business
     * @return the data row item
     */
    private DataRowItem convertNormalToDataItem(ReportData reportData, String personId, boolean isFindBusiness) {
        Object[] objects = reportData.objectNormals.stream()
                .filter(p -> {
                    PbsmtPersonBase baseEntity = (PbsmtPersonBase) p[0];
                    return baseEntity.getPid().equals(personId);
                })
                .findFirst()
                .get();
        
        PclmtPersonEmpContract empContract = (PclmtPersonEmpContract) objects[2];
        CmnmtEmp cmEmp = (CmnmtEmp) objects[3];
        DataRowItem item = new DataRowItem();
        item.setCode(empContract.empCd);
        item.setName(cmEmp.employmentName);
        
        PismtPersonInsuHealB insuHealBEntity = (PismtPersonInsuHealB) objects[5];
        HealthInsuranceAvgearn healInsuAvgearn = reportData.listHealInsuAvgearn.stream()
                .filter(p -> {
                    return insuHealBEntity.getHealthInsuGrade().intValue() == p.getGrade();
                })
                .findFirst()
                .get();
        
        PismtPersonInsuPensB insuPensBEntity = (PismtPersonInsuPensB) objects[6];
        PensionAvgearn pensAvgearn = reportData.listPensAvgearn.stream()
                .filter(p -> {
                    return insuPensBEntity.getPensionInsuGrade().intValue() == p.getGrade();
                })
                .findFirst()
                .get();
        PbsmtPersonBase personBase = (PbsmtPersonBase) objects[0];
        
        // set data row item for business.
        if (isFindBusiness) {
            // set monthly health insurance normal.
            double healInsuNormal = healInsuAvgearn.getCompanyAvg().getHealthGeneralMny().v().doubleValue()
                    + healInsuAvgearn.getCompanyAvg().getHealthNursingMny().v().doubleValue();
            item.setMonthlyHealthInsuranceNormal(healInsuNormal);
            // set monthly general insurance normal.
            double generalInsuNormal = healInsuAvgearn.getCompanyAvg().getHealthGeneralMny().v().doubleValue();
            item.setMonthlyGeneralInsuranceNormal(generalInsuNormal);
            // set monthly long term insurance normal.
            double longTermInsuNormal = healInsuAvgearn.getCompanyAvg().getHealthNursingMny().v().doubleValue();
            item.setMonthlyLongTermInsuranceNormal(longTermInsuNormal);
            // set monthly specific insurance normal.
            double specificInsuNormal = healInsuAvgearn.getCompanyAvg().getHealthSpecificMny().v().doubleValue();
            item.setMonthlySpecificInsuranceNormal(specificInsuNormal);
            // set monthly basic insurance normal.
            double basicInsuNormal = healInsuAvgearn.getCompanyAvg().getHealthBasicMny().v().doubleValue();
            item.setMonthlyBasicInsuranceNormal(basicInsuNormal);
            if (personBase.getGendar() == 0) {// male
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getCompanyPension().getMaleAmount().v().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getCompanyFund().getFemaleAmount().v().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            } else { // Female
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getCompanyPension().getFemaleAmount().v().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getCompanyFund().getFemaleAmount().v().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            }
        }
        // set data row item for personal.
        else {
            // set monthly health insurance normal.
            double healInsuNormal = healInsuAvgearn.getPersonalAvg().getHealthGeneralMny().v().doubleValue()
                    + healInsuAvgearn.getPersonalAvg().getHealthNursingMny().v().doubleValue();
            item.setMonthlyHealthInsuranceNormal(healInsuNormal);
            // set monthly general insurance normal.
            double generalInsuNormal = healInsuAvgearn.getPersonalAvg().getHealthGeneralMny().v().doubleValue();
            item.setMonthlyGeneralInsuranceNormal(generalInsuNormal);
            // set monthly long term insurance normal.
            double longTermInsuNormal = healInsuAvgearn.getPersonalAvg().getHealthNursingMny().v().doubleValue();
            item.setMonthlyLongTermInsuranceNormal(longTermInsuNormal);
            // set monthly specific insurance normal.
            double specificInsuNormal = healInsuAvgearn.getPersonalAvg().getHealthSpecificMny().v().doubleValue();
            item.setMonthlySpecificInsuranceNormal(specificInsuNormal);
            // set monthly basic insurance normal.
            double basicInsuNormal = healInsuAvgearn.getPersonalAvg().getHealthBasicMny().v().doubleValue();
            item.setMonthlyBasicInsuranceNormal(basicInsuNormal);
            if (personBase.getGendar() == 0) {// male
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getPersonalPension().getMaleAmount().v().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getPersonalFund().getMaleAmount().v().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            } else {
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getPersonalPension().getFemaleAmount().v().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getPersonalFund().getFemaleAmount().v().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            }
        }
        
        return item;
    }
    
    /**
     * Convert deduction to data item.
     *
     * @param reportData the report data
     * @param rowItem the row item
     * @param personId the person id
     * @param itemDeduction the item deduction
     */
    private void convertDeductionToDataItem(ReportData reportData, DataRowItem rowItem, String personId,
            ItemDeduction itemDeduction) {
        List<Object[]> objectList = reportData.objectDeductions.stream()
                .filter(p -> {
                    QstdtPaymentHeader headerEntity = (QstdtPaymentHeader) p[0];
                    return headerEntity.qstdtPaymentHeaderPK.personId.equals(personId);
                })
                .collect(Collectors.toList());
        // set monthly health insurance deduction.
        double healInsuDeduction = findValueDeductionByItemCode(objectList, itemDeduction.getItemCodeHealthInsu());
        rowItem.setMonthlyHealthInsuranceDeduction(healInsuDeduction);
        //set monthly general insurance deduction.
        double generalInsuDeduction = findValueDeductionByItemCode(objectList, itemDeduction.getItemCodeGeneralInsu());
        rowItem.setMonthlyGeneralInsuranceDeduction(generalInsuDeduction);
        // set monthly long term insurance deduction.
        double longTermDeduction = findValueDeductionByItemCode(objectList, itemDeduction.getItemCodeLongTermInsu());
        rowItem.setMonthlyLongTermInsuranceDeduction(longTermDeduction);
        // set monthly specific insurance deduction.
        double specificInsuDeduction = findValueDeductionByItemCode(objectList, itemDeduction.getItemCodeSpecificInsu());
        rowItem.setMonthlySpecificInsuranceDeduction(specificInsuDeduction);
        // set monthly basic insurance deduction.
        double basicInsuDeduction = findValueDeductionByItemCode(objectList, itemDeduction.getItemCodeBasicInsu());
        rowItem.setMonthlyBasicInsuranceDeduction(basicInsuDeduction);
        // set welfare pension insurance deduction.
        double welfarePenInsuDeduction = findValueDeductionByItemCode(objectList,
                itemDeduction.getItemCodeWelfarePensionInsu());
        rowItem.setWelfarePensionInsuranceDeduction(welfarePenInsuDeduction);
        // set welfare pension fund deduction.
        double welfarePenFundDeduction = findValueDeductionByItemCode(objectList,
                itemDeduction.getItemCodeWelfarePensionFund());
        rowItem.setWelfarePensionFundDeduction(welfarePenFundDeduction);
        //set child raising contribution money.
        if (itemDeduction.getItemCodeChildRaising() != null) {
            double childRaisingMoney = findValueDeductionByItemCode(objectList, itemDeduction.getItemCodeChildRaising());
            rowItem.setChildRaisingContributionMoney(childRaisingMoney);
        }
    }
    
    /**
     * Convert M layout item.
     *
     * @param reportData the report data
     * @param personId the person id
     * @param itemDPersonal the item D personal
     * @param itemDBusiness the item D business
     * @return the m layout row item
     */
    private MLayoutRowItem convertMLayoutItem(ReportData reportData, String personId, ItemDeduction itemDPersonal,
            ItemDeduction itemDBusiness) {
        Object[] objectNormals = reportData.objectNormals.stream()
                .filter(p -> {
                    PbsmtPersonBase baseEntity = (PbsmtPersonBase) p[0];
                    return baseEntity.getPid().equals(personId);
                })
                .findFirst()
                .get();
        
        PclmtPersonEmpContract empContract = (PclmtPersonEmpContract) objectNormals[2];
        CmnmtEmp cmEmp = (CmnmtEmp) objectNormals[3];
        MLayoutRowItem item = new MLayoutRowItem();
        item.setCode(empContract.empCd);
        item.setName(cmEmp.employmentName);
        
        PismtPersonInsuHealB insuHealBEntity = (PismtPersonInsuHealB) objectNormals[5];
        HealthInsuranceAvgearn healInsuAvgearn = reportData.listHealInsuAvgearn.stream()
                .filter(p -> {
                    return insuHealBEntity.getHealthInsuGrade().intValue() == p.getGrade();
                })
                .findFirst()
                .get();
        
        PismtPersonInsuPensB insuPensBEntity = (PismtPersonInsuPensB) objectNormals[6];
        PensionAvgearn pensAvgearn = reportData.listPensAvgearn.stream()
                .filter(p -> {
                    return insuPensBEntity.getPensionInsuGrade().intValue() == p.getGrade();
                })
                .findFirst()
                .get();
        PbsmtPersonBase personBase = (PbsmtPersonBase) objectNormals[0];
        
        // set heal insurance fee personal
        double healInsuFeePersonal = healInsuAvgearn.getPersonalAvg().getHealthBasicMny().v().doubleValue() 
                + healInsuAvgearn.getPersonalAvg().getHealthNursingMny().v().doubleValue();
        item.setHealInsuFeePersonal(healInsuFeePersonal);
        // set heal insurance fee business
        double healInsuFeeBusiness = healInsuAvgearn.getCompanyAvg().getHealthBasicMny().v().doubleValue()
                + healInsuAvgearn.getCompanyAvg().getHealthNursingMny().v().doubleValue();
        item.setHealInsuFeeBusiness(healInsuFeeBusiness);
        
        // find list object item code.
        List<Object[]> listObjectItem = reportData.objectDeductions.stream()
                .filter(p -> {
                    QstdtPaymentHeader headerEntity = (QstdtPaymentHeader) p[0];
                    return headerEntity.qstdtPaymentHeaderPK.personId.equals(personId);
                })
                .collect(Collectors.toList());
        
        // set deduction heal insurance personal
        double healInsuDPersonal = findValueDeductionByItemCode(listObjectItem, itemDPersonal.getItemCodeHealthInsu());
        item.setDeductionHealInsuPersonal(healInsuDPersonal);
        // set deduction heal insurance business
        double healInsuDBusiness = findValueDeductionByItemCode(listObjectItem, itemDBusiness.getItemCodeHealthInsu());
        item.setDeductionHealInsuBusiness(healInsuDBusiness);
        if (personBase.getGendar() == 0) {// male
            // set welfare pen insurance personal
            double welfarePensInsuPersonal = pensAvgearn.getPersonalPension().getMaleAmount().v().doubleValue();
            item.setWelfarePenInsuBusiness(welfarePensInsuPersonal);
            // set set welfare pen insurance business
            double welfarePensInsuBusiness = pensAvgearn.getCompanyPension().getMaleAmount().v().doubleValue();
            item.setWelfarePenInsuBusiness(welfarePensInsuBusiness);
            
            // welfare pen fund personal.
            double welfarePensFundDPersonal = findValueDeductionByItemCode(listObjectItem, 
                    itemDPersonal.getItemCodeWelfarePensionFund());
            item.setWelfarePenFundPersonal(welfarePensFundDPersonal);
            // welfare pen fund business.
            double welfarePensFundBusiness = findValueDeductionByItemCode(listObjectItem, 
                    itemDBusiness.getItemCodeWelfarePensionFund());
            item.setWelfarePenFundBusiness(welfarePensFundBusiness);
        } else { // Female
            // set welfare pen insurance personal
            double welfarePensInsuPersonal = pensAvgearn.getPersonalPension().getFemaleAmount().v().doubleValue();
            item.setWelfarePenInsuBusiness(welfarePensInsuPersonal);
            // set set welfare pen insurance business
            double welfarePensInsuBusiness = pensAvgearn.getCompanyPension().getFemaleAmount().v().doubleValue();
            item.setWelfarePenInsuBusiness(welfarePensInsuBusiness);
            
            // deduction welfare pen insurance personal.
            double welfarePensFundDPersonal = findValueDeductionByItemCode(listObjectItem, 
                    itemDPersonal.getItemCodeWelfarePensionFund());
            item.setWelfarePenFundPersonal(welfarePensFundDPersonal);
            // deduction welfare pen insurance business.
            double welfarePensFundBusiness = findValueDeductionByItemCode(listObjectItem, 
                    itemDBusiness.getItemCodeWelfarePensionFund());
            item.setWelfarePenFundBusiness(welfarePensFundBusiness);
        }
        // set deduction welfare pen insurance personal
        double welfarePenInsuDPersonal = findValueDeductionByItemCode(listObjectItem, 
                itemDPersonal.getItemCodeWelfarePensionInsu());
        item.setDeductionWelfarePenInsuPersonal(welfarePenInsuDPersonal);
        // set deduction welfare pen insurance business
        double welfarePenInsuDBusiness = findValueDeductionByItemCode(listObjectItem, 
                itemDBusiness.getItemCodeWelfarePensionInsu());
        item.setDeductionWelfarePenInsuBusiness(welfarePenInsuDBusiness);
        
        // set deduction welfare pen fund personal
        double welfarePenFundPersonal = findValueDeductionByItemCode(listObjectItem, 
                itemDPersonal.getItemCodeWelfarePensionFund());
        item.setDeductionWelfarePenFundPersonal(welfarePenFundPersonal);
        // set deduction welfare pen fund business
        double welfarePenFundBusiness = findValueDeductionByItemCode(listObjectItem, 
                itemDBusiness.getItemCodeWelfarePensionFund());
        item.setDeductionWelfarePenFundPersonal(welfarePenFundBusiness);
        
        // set child raising
        double childRaising = findValueDeductionByItemCode(listObjectItem, itemDBusiness.getItemCodeChildRaising());
        item.setChildRaising(childRaising);
        
        return item;
    }
    
    /**
     * Find value deduction by item code.
     *
     * @param objectList the object list
     * @param itemCode the item code
     * @return the double
     */
    private double findValueDeductionByItemCode(List<Object[]> objectList, String itemCode) {
        return objectList.stream()
                .filter(p -> {
                    QstdtPaymentDetail paymentEntity = (QstdtPaymentDetail) p[1];
                    return paymentEntity.qstdtPaymentDetailPK.itemCode.equals(itemCode);
                })
                .mapToDouble(p -> {
                    QstdtPaymentDetail paymentEntity = (QstdtPaymentDetail) p[1];
                    return paymentEntity.value.doubleValue();
                })
                .sum();
    }
    
    /**
     * Checks if is printed personal.
     *
     * @param item the item
     * @param query the query
     * @return true, if is printed personal
     */
    private boolean isPrintedPersonal(DataRowItem item, SocialInsuQuery query) {
        // equal
        if (query.getIsEqual()) {
            if (item.getMonthlyHealthInsuranceNormal() == item.getMonthlyHealthInsuranceDeduction()
                    && item.getMonthlyGeneralInsuranceNormal() == item.getMonthlyGeneralInsuranceDeduction()
                    && item.getMonthlyLongTermInsuranceNormal() == item.getMonthlyLongTermInsuranceDeduction()
                    && item.getMonthlySpecificInsuranceNormal() == item.getMonthlySpecificInsuranceDeduction()
                    && item.getMonthlyBasicInsuranceNormal() == item.getMonthlyBasicInsuranceDeduction()
                    && item.getWelfarePensionInsuranceNormal() == item.getWelfarePensionInsuranceDeduction()
                    && item.getWelfarePensionFundNormal() == item.getWelfarePensionFundDeduction()) {
                return true;
            }
        }
        // less than
        if (query.getIsDeficient()) {
            if (item.getMonthlyHealthInsuranceNormal() < item.getMonthlyHealthInsuranceDeduction()
                    && item.getMonthlyGeneralInsuranceNormal() < item.getMonthlyGeneralInsuranceDeduction()
                    && item.getMonthlyLongTermInsuranceNormal() < item.getMonthlyLongTermInsuranceDeduction()
                    && item.getMonthlySpecificInsuranceNormal() < item.getMonthlySpecificInsuranceDeduction()
                    && item.getMonthlyBasicInsuranceNormal() < item.getMonthlyBasicInsuranceDeduction()
                    && item.getWelfarePensionInsuranceNormal() < item.getWelfarePensionInsuranceDeduction()
                    && item.getWelfarePensionFundNormal() < item.getWelfarePensionFundDeduction()) {
                return true;
            }
        }
        // greater than
        if(query.getIsRedundant()) {
            if (item.getMonthlyHealthInsuranceNormal() > item.getMonthlyHealthInsuranceDeduction()
                    && item.getMonthlyGeneralInsuranceNormal() > item.getMonthlyGeneralInsuranceDeduction()
                    && item.getMonthlyLongTermInsuranceNormal() > item.getMonthlyLongTermInsuranceDeduction()
                    && item.getMonthlySpecificInsuranceNormal() > item.getMonthlySpecificInsuranceDeduction()
                    && item.getMonthlyBasicInsuranceNormal() > item.getMonthlyBasicInsuranceDeduction()
                    && item.getWelfarePensionInsuranceNormal() < item.getWelfarePensionInsuranceDeduction()
                    && item.getWelfarePensionFundNormal() > item.getWelfarePensionFundDeduction()) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Checks if is printed personal M layout.
     *
     * @param item the item
     * @param query the query
     * @return true, if is printed personal M layout
     */
    private boolean isPrintedPersonalMLayout(MLayoutRowItem item, SocialInsuQuery query) {
        if (query.getIsEqual()) {
            if (item.getHealInsuFeePersonal() == item.getDeductionHealInsuPersonal()
                    && item.getWelfarePenInsuPersonal() == item.getDeductionWelfarePenInsuPersonal()
                    && item.getWelfarePenFundPersonal() == item.getDeductionWelfarePenFundPersonal()) {
                return true;
            }
        }
        if (query.getIsDeficient()) {
            if (item.getHealInsuFeePersonal() < item.getDeductionHealInsuPersonal()
                    && item.getWelfarePenInsuPersonal() < item.getDeductionWelfarePenInsuPersonal()
                    && item.getWelfarePenFundPersonal() < item.getDeductionWelfarePenFundPersonal()) {
                return true;
            }
        }
        if (query.getIsRedundant()) {
            if (item.getHealInsuFeePersonal() == item.getDeductionHealInsuPersonal()
                    && item.getWelfarePenInsuPersonal() > item.getDeductionWelfarePenInsuPersonal()
                    && item.getWelfarePenFundPersonal() > item.getDeductionWelfarePenFundPersonal()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Inits the deduction personal.
     *
     * @return the item deduction
     */
    private ItemDeduction initDeductionPersonal() {
        ItemDeduction item = new ItemDeduction();
        item.setItemCodeHealthInsu("F101");
        item.setItemCodeGeneralInsu("F109");
        item.setItemCodeLongTermInsu("F110");
        item.setItemCodeSpecificInsu("F111");
        item.setItemCodeBasicInsu("F112");
        item.setItemCodeWelfarePensionInsu("F102");
        item.setItemCodeWelfarePensionFund("F103");
        return item;
    }
    
    /**
     * Inits the deduction business.
     *
     * @return the item deduction
     */
    private ItemDeduction initDeductionBusiness() {
        ItemDeduction item = new ItemDeduction();
        item.setItemCodeHealthInsu("F115");
        item.setItemCodeGeneralInsu("F116");
        item.setItemCodeLongTermInsu("F117");
        item.setItemCodeSpecificInsu("F118");
        item.setItemCodeBasicInsu("F119");
        item.setItemCodeWelfarePensionInsu("F120");
        item.setItemCodeWelfarePensionFund("F121");
        item.setItemCodeChildRaising("F124");
        return item;
    }
}

class ReportData {
    
    /** The object normals. */
    List<Object[]> objectNormals;
    
    /** The object deductions. */
    List<Object[]> objectDeductions;
    
    /** The list heal insu avgearn. */
    List<HealthInsuranceAvgearn> listHealInsuAvgearn;
    
    /** The list pens avgearn. */
    List<PensionAvgearn> listPensAvgearn;
}

@Setter
@Getter
class ItemDeduction {
    
    /** The item code health insu. */
    String itemCodeHealthInsu;
    
    /** The item code general insu. */
    String itemCodeGeneralInsu;
    
    /** The item code long term insu. */
    String itemCodeLongTermInsu;
    
    /** The item code specific insu. */
    String itemCodeSpecificInsu;
    
    /** The item code basic insu. */
    String itemCodeBasicInsu;
    
    /** The item code welfare pension insu. */
    String itemCodeWelfarePensionInsu;
    
    /** The item code welfare pension fund. */
    String itemCodeWelfarePensionFund;
    
    /** The item code child raising. */
    String itemCodeChildRaising;
}
