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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuHealB;
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuPensB;
import nts.uk.ctx.basic.infra.entity.report.PismtPersonInsuSocial;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.QismtSocialInsuOffice;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.healthavgearn.QismtHealthInsuAvgearn;
import nts.uk.ctx.pr.core.infra.entity.insurance.social.pensionavgearn.QismtPensionAvgearn;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentDetail;
import nts.uk.ctx.pr.core.infra.entity.paymentdata.QstdtPaymentHeader;
import nts.uk.ctx.pr.core.infra.entity.personalinfo.employmentcontract.PclmtPersonEmpContract;
import nts.uk.file.pr.app.export.insurance.data.DataRowItem;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceQuery;
import nts.uk.file.pr.app.export.insurance.salary.SalarySocialInsuranceRepository;

/**
 * The Class JpaSalarySocialInsuranceRepository.
 *
 * @author duongnd
 */

@Stateless
public class JpaSalarySocialInsuranceRepository extends JpaRepository implements SalarySocialInsuranceRepository {
    
    private static final String FIND_PERSON_OFFICES = "SELECT so, pp, mp, ps "
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
    
    private static final String FIND_PERSON_DEDUCTION = "SELECT ph, pd, hr, ha, pr, pa "
            + "FROM QstdtPaymentHeader ph, "
            + "QstdtPaymentDetail pd, "
            + "QismtHealthInsuRate hr, "
            + "QismtHealthInsuAvgearn ha, "
            + "QismtPensionRate pr, "
            + "QismtPensionAvgearn pa "
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
            + "AND hr.qismtHealthInsuRatePK.ccd = ph.qstdtPaymentHeaderPK.companyCode "
            + "AND hr.qismtHealthInsuRatePK.siOfficeCd = :officeCode "
            + "AND hr.strYm <= :yearMonth "
            + "AND hr.endYm >= :yearMonth "
            + "AND ha.qismtHealthInsuAvgearnPK.ccd = ph.qstdtPaymentHeaderPK.companyCode "
            + "AND ha.qismtHealthInsuAvgearnPK.siOfficeCd = hr.qismtHealthInsuRatePK.siOfficeCd "
            + "AND ha.qismtHealthInsuAvgearnPK.histId =  hr.qismtHealthInsuRatePK.histId "
            + "AND pr.qismtPensionRatePK.ccd = ph.qstdtPaymentHeaderPK.companyCode "
            + "AND pr.qismtPensionRatePK.siOfficeCd = hr.qismtHealthInsuRatePK.siOfficeCd "
            + "AND pr.strYm <= :yearMonth "
            + "AND pr.endYm >= :yearMonth "
            + "AND pa.qismtPensionAvgearnPK.ccd = ph.qstdtPaymentHeaderPK.companyCode "
            + "AND pa.qismtPensionAvgearnPK.siOfficeCd = hr.qismtHealthInsuRatePK.siOfficeCd "
            + "AND pa.qismtPensionAvgearnPK.histId = pr.qismtPensionRatePK.histId ";
    
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String FIRST_DAY = "01";
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.file.pr.app.export.insurance.salary.
     * SalarySocailInsuranceRepository#fincReportData(nts.uk.file.pr.app.export.
     * insurance.salary.SalarySocialInsuranceQuery)
     */
    @Override
    public List<SalarySocialInsuranceReportData> findReportData(String companyCode, String loginPersonId,
            SalarySocialInsuranceQuery salaryQuery) {
        List<String> officeCodes = salaryQuery.getInsuranceOffices().stream()
                .map(p -> p.getCode())
                .collect(Collectors.toList());
        int yearMonth = salaryQuery.getYearMonth();
        List<Object[]> itemList = findPersonOffices(companyCode, officeCodes, yearMonth, loginPersonId);
        if (itemList.isEmpty()) {
            // TODO: throw message ER010
            throw new BusinessException("ER010");
        }
        // find list offices.
        List<QismtSocialInsuOffice> entityOffices = itemList.stream()
        .map(p -> {
            return (QismtSocialInsuOffice) p[0];
        })
        .filter(distinctByKey(p -> p.getQismtSocialInsuOfficePK().getSiOfficeCd()))
        .collect(Collectors.toList());
        List<InsuranceOfficeDto> listPersonal = new ArrayList<>();
        List<InsuranceOfficeDto> listBusiness = new ArrayList<>();
        ItemDeduction itemDeductionPersonal = initDeductionPersonal();
        ItemDeduction itemDeductionBusiness = initDeductionBusiness();
        for (QismtSocialInsuOffice office : entityOffices) {
            String officeCode = office.getQismtSocialInsuOfficePK().getSiOfficeCd();
            List<String> personIds = itemList.stream()
                    .filter(p -> {
                        QismtSocialInsuOffice entity = (QismtSocialInsuOffice) p[0];
                        return entity.getQismtSocialInsuOfficePK().getSiOfficeCd()
                                .equals(office.getQismtSocialInsuOfficePK().getSiOfficeCd());
                    })
                    .map(p -> {
                        PismtPersonInsuSocial entity = (PismtPersonInsuSocial) p[3];
                        return entity.getPismtPersonInsuSocialPK().getPid();
                    })
                    .distinct()
                    .collect(Collectors.toList());
            List<Object[]> objectNormals = findPersonNormal(companyCode, officeCode, personIds, yearMonth);
            List<Object[]> objectDeductions = findPersonDeduction(companyCode, officeCode, personIds, yearMonth);
            if (objectNormals.isEmpty() || objectDeductions.isEmpty()) {
                // TODO: throw message ER010
                throw new BusinessException("ER010");
            }
            List<DataRowItem> listRowItemPersonal = new ArrayList<>();
            List<DataRowItem> listRowItemBusiness = new ArrayList<>();
            for (String personId : personIds) {
                // set row item for personal.
                DataRowItem rowItemPersonal = convertNormalToDataItem(objectNormals, objectDeductions, personId, false);
                // rowItemPersonal is passed reference parameter.
                convertDeductionToDataItem(rowItemPersonal, objectDeductions, personId, itemDeductionPersonal);
                listRowItemPersonal.add(rowItemPersonal);
                
                // set row item for business.
                DataRowItem rowItemBusiness = convertNormalToDataItem(objectNormals, objectDeductions, personId, true);
                // rowItemBusiness is passed reference parameter.
                convertDeductionToDataItem(rowItemBusiness, objectDeductions, personId, itemDeductionBusiness);
                listRowItemBusiness.add(rowItemBusiness);
            }
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
        }
        List<SalarySocialInsuranceReportData> listReport = new ArrayList<>();
        // set list office for personal.
        SalarySocialInsuranceReportData reportPersonal = new SalarySocialInsuranceReportData();
        reportPersonal.setOfficeItems(listPersonal);
        listReport.add(reportPersonal);
        
        // set list office for business.
        SalarySocialInsuranceReportData reportBusiness = new SalarySocialInsuranceReportData();
        reportBusiness.setOfficeItems(listBusiness);
        listReport.add(reportBusiness);
        
        return listReport;
    }
    
    @SuppressWarnings("unchecked")
    private List<Object[]> findPersonOffices(String companyCode, List<String> officeCodes, Integer yearMonth,
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
    
    @SuppressWarnings("unchecked")
    private List<Object[]> findPersonDeduction(String companyCode, String officeCode, List<String> personIds,
            Integer yearMonth) {
        EntityManager em = this.getEntityManager();
        List<String> itemCodes = Arrays.asList("F101", "F102", "F103", "F109", "F110", "F111", "F112", "F115", "F116",
                "F117", "F118", "F119", "F120", "F121", "F124");
        Query query = em.createQuery(FIND_PERSON_DEDUCTION);
        query.setParameter("personIds", personIds);
        query.setParameter("companyCode", companyCode);
        query.setParameter("yearMonth", yearMonth);
        query.setParameter("itemCodes", itemCodes);
        query.setParameter("officeCode", officeCode);
        return query.getResultList();
    }
    
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    
    private DataRowItem convertNormalToDataItem(List<Object[]> objectNormals, List<Object[]> objectDeductions, String personId, boolean isFindBusiness) {
        Object[] objects = objectNormals.stream()
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
        // TODO: cast object entity
        PismtPersonInsuHealB insuHealBEntity = (PismtPersonInsuHealB) objects[5];
        QismtHealthInsuAvgearn healInsuAvgearn = objectDeductions.stream()
                .map(p -> (QismtHealthInsuAvgearn) p[3])
                .filter(p -> {
                    return insuHealBEntity.getHealthInsuGrade()
                            .compareTo(p.getQismtHealthInsuAvgearnPK().getHealthInsuGrade()) == 0;
                })
                .findFirst()
                .get();
        
        PismtPersonInsuPensB insuPensBEntity = (PismtPersonInsuPensB) objects[6];
        QismtPensionAvgearn pensAvgearn = objectDeductions.stream()
                .map(p -> (QismtPensionAvgearn) p[5])
                .filter(p -> {
                    return insuPensBEntity.getPensionInsuGrade()
                            .compareTo(p.getQismtPensionAvgearnPK().getPensionGrade()) == 0;
                })
                .findFirst()
                .get();
        PbsmtPersonBase personBase = (PbsmtPersonBase) objects[0];
        
        // set data row item for business.
        if (isFindBusiness) {
            // set monthly health insurance normal.
            double healInsuNormal = healInsuAvgearn.getCHealthGeneralMny().doubleValue()
                    + healInsuAvgearn.getCHealthNursingMny().doubleValue();
            item.setMonthlyHealthInsuranceNormal(healInsuNormal);
            // set monthly general insurance normal.
            double generalInsuNormal = healInsuAvgearn.getCHealthGeneralMny().doubleValue();
            item.setMonthlyGeneralInsuranceNormal(generalInsuNormal);
            // set monthly long term insurance normal.
            double longTermInsuNormal = healInsuAvgearn.getCHealthNursingMny().doubleValue();
            item.setMonthlyLongTermInsuranceNormal(longTermInsuNormal);
            // set monthly specific insurance normal.
            double specificInsuNormal = healInsuAvgearn.getCHealthSpecificMny().doubleValue();
            item.setMonthlySpecificInsuranceNormal(specificInsuNormal);
            // set monthly basic insurance normal.
            double basicInsuNormal = healInsuAvgearn.getCHealthBasicMny().doubleValue();
            item.setMonthlyBasicInsuranceNormal(basicInsuNormal);
            if (personBase.getGendar() == 0) {// male
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getCPensionMaleMny().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getCFundMaleMny().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            } else { // Female
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getCPensionFemMny().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getCFundFemMny().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            }
        }
        // set data row item for personal.
        else {
            // set monthly health insurance normal.
            double healInsuNormal = healInsuAvgearn.getPHealthGeneralMny().doubleValue()
                    + healInsuAvgearn.getPHealthNursingMny().doubleValue();
            item.setMonthlyHealthInsuranceNormal(healInsuNormal);
            // set monthly general insurance normal.
            double generalInsuNormal = healInsuAvgearn.getPHealthGeneralMny().doubleValue();
            item.setMonthlyGeneralInsuranceNormal(generalInsuNormal);
            // set monthly long term insurance normal.
            double longTermInsuNormal = healInsuAvgearn.getPHealthNursingMny().doubleValue();
            item.setMonthlyLongTermInsuranceNormal(longTermInsuNormal);
            // set monthly specific insurance normal.
            double specificInsuNormal = healInsuAvgearn.getPHealthSpecificMny().doubleValue();
            item.setMonthlySpecificInsuranceNormal(specificInsuNormal);
            // set monthly basic insurance normal.
            double basicInsuNormal = healInsuAvgearn.getPHealthBasicMny().doubleValue();
            item.setMonthlyBasicInsuranceNormal(basicInsuNormal);
            if (personBase.getGendar() == 0) {// male
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getPPensionMaleMny().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getPFundMaleMny().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            } else {
                // set welfare pension insurance normal.
                double welfarePensInsu = pensAvgearn.getPPensionFemMny().doubleValue();
                item.setWelfarePensionInsuranceNormal(welfarePensInsu);
                // set welfare pension fund normal.
                double welfarePensFund = pensAvgearn.getPFundFemMny().doubleValue();
                item.setWelfarePensionFundNormal(welfarePensFund);
            }
        }
        
        return item;
    }
    
    private void convertDeductionToDataItem(DataRowItem rowItem, List<Object[]> objectDeductions, String personId,
            ItemDeduction itemDeduction) {
        List<Object[]> objectList = objectDeductions.stream()
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

@Setter
@Getter
class ItemDeduction {
    String itemCodeHealthInsu;
    String itemCodeGeneralInsu;
    String itemCodeLongTermInsu;
    String itemCodeSpecificInsu;
    String itemCodeBasicInsu;
    String itemCodeWelfarePensionInsu;
    String itemCodeWelfarePensionFund;
    String itemCodeChildRaising;
}
