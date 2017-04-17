/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.insurance.salary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.file.pr.app.export.insurance.data.ChecklistPrintSettingDto;
import nts.uk.file.pr.app.export.insurance.data.DataRowItem;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceHeaderReportData;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author duongnd
 *
 */

@Stateless
public class SalarySocialInsuranceReportService extends ExportService<SalarySocialInsuranceQuery> {

    @Inject
    private SalarySocialInsuranceGenerator generator;
    
    @Inject
    private SalarySocialInsuranceQueryProcessor queryProcessor;
    
    @Inject
    private SalarySocialInsuranceRepository repository;
    
    @Override
    protected void handle(ExportServiceContext<SalarySocialInsuranceQuery> context) {
        // get query
        SalarySocialInsuranceQuery query = context.getQuery();
        // get data from repository follow query.
        
        String companyCode = AppContexts.user().companyCode();
        String loginPersonID = "000000000000000000000000000000000001"; //AppContexts.user().personId();
        
        List<String> officeCodes = query.getInsuranceOffices().stream()
                .map(office -> office.getCode())
                .collect(Collectors.toList());
        List<HealthInsuranceAvgearn> listhealInsuAvgearn = queryProcessor.findHealInsuAvgearnByOffice(officeCodes);
        List<PensionAvgearn> listPensionAvgearn = queryProcessor.findPensAvgearnByOffice(officeCodes);
        
        List<SalarySocialInsuranceReportData> dataReports = repository.findReportData(companyCode, loginPersonID, query,
                listhealInsuAvgearn, listPensionAvgearn);
        ChecklistPrintSettingDto configOutput = queryProcessor.findConfigureOutputSetting();
        // dataReports.get(0): report data of personal.
        SalarySocialInsuranceReportData reportPersonal = dataReports.get(0);
        reportPersonal.setIsPrintBusiness(true);
        reportPersonal.setConfigureOutput(configOutput);
        processData(reportPersonal, query);
        
        // dataReports.get(1): report data of office.
        SalarySocialInsuranceReportData reportBusiness = dataReports.get(1);
        reportBusiness.setConfigureOutput(configOutput);
        reportBusiness.setIsPrintBusiness(false);
        processData(reportBusiness, query);
        
        // Fake
        boolean isPrintReportPersonal = true;
        
        // set child raising contribution money of business.
        if (isPrintReportPersonal) {
            reportPersonal.setChildRaisingTotalBusiness(reportBusiness.getTotalAllOffice()
                    .getChildRaisingContributionMoney());
        }
        // print report for personal.
        this.generator.generate(context.getGeneratorContext(), reportPersonal);
    }
    
    private void processData(SalarySocialInsuranceReportData reportData, SalarySocialInsuranceQuery query) {
        SalarySocialInsuranceHeaderReportData headerData = new SalarySocialInsuranceHeaderReportData();
        headerData.setNameCompany("【日通 システム株式会社】");
        headerData.setTitleReport("会社保険料チェックリスト(被保険者)");
        headerData.setInformationOffice("【事業所:本社 ~ 福岡支社(3事業所)】");
        // TODO: convert to year Japan.
        headerData.setTargetYearMonth("【対象年月:平成19年12月 給与】 ");
        String headerCondition = "【出カ条件:算定月額と対象月控除額が";
        if (query.getIsEqual()) {
            headerCondition += "全て同じ、";
        }
        if (query.getIsDeficient()) {
            headerCondition += "不足、";
        }
        if (query.getIsRedundant()) {
            headerCondition += "超過";
        }
        headerCondition += "】 ";
        headerData.setCondition(headerCondition);
        headerData.setFormalCalculation("【計算方法:全体の保険料より被保険者分を差し引く方法】");
        reportData.setHeaderData(headerData);
        
        List<InsuranceOfficeDto> officeItems = reportData.getOfficeItems();
        reportData.setOfficeItems(officeItems);
        List<DataRowItem> totalEachOffices = new ArrayList<>();
        for (InsuranceOfficeDto office : officeItems) {
            DataRowItem totalEachOffice = calculateTotal(office.getEmployeeDtos());
            office.setTotalEachOffice(totalEachOffice);
            totalEachOffices.add(totalEachOffice);
        }
        DataRowItem totalAllOffice = calculateTotal(totalEachOffices);
        reportData.setTotalAllOffice(totalAllOffice);
        
        double insuredCollectAmount = calInsuredCollectAmount(totalAllOffice);
        reportData.setInsuredCollectAmount(insuredCollectAmount);
    }
    
    private DataRowItem calculateTotal(List<DataRowItem> rowItems) {
        double monthlyHealthInsuranceNormal = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyHealthInsuranceNormal())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlyGeneralInsuranceNormal = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyGeneralInsuranceNormal())
                .mapToDouble(item -> item.doubleValue()).sum();;
        double monthlyLongTermInsuranceNormal = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyLongTermInsuranceNormal())
                .mapToDouble(item -> item.doubleValue()).sum();;
        double monthlySpecificInsuranceNormal = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlySpecificInsuranceNormal())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlyBasicInsuranceNormal = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyBasicInsuranceNormal())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlyHealthInsuranceDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyHealthInsuranceDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlyGeneralInsuranceDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyGeneralInsuranceDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlyLongTermInsuranceDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyLongTermInsuranceDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlySpecificInsuranceDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlySpecificInsuranceDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double monthlyBasicInsuranceDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getMonthlyBasicInsuranceDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double welfarePensionInsuranceNormal = rowItems.stream()
                .map(rowItem -> rowItem.getWelfarePensionInsuranceNormal())
                .mapToDouble(item -> item.doubleValue()).sum();
        double welfarePensionInsuranceDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getWelfarePensionInsuranceDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double welfarePensionFundNormal = rowItems.stream()
                .map(rowItem -> rowItem.getWelfarePensionFundNormal())
                .mapToDouble(item -> item.doubleValue()).sum();
        double welfarePensionFundDeduction = rowItems.stream()
                .map(rowItem -> rowItem.getWelfarePensionFundDeduction())
                .mapToDouble(item -> item.doubleValue()).sum();
        double childRaisingContributionMoney = rowItems.stream()
                .map(rowItem -> rowItem.getChildRaisingContributionMoney())
                .mapToDouble(item -> item.doubleValue()).sum();
        
        DataRowItem rowItem = new DataRowItem();
        rowItem.setMonthlyHealthInsuranceNormal(monthlyHealthInsuranceNormal);
        rowItem.setMonthlyGeneralInsuranceNormal(monthlyGeneralInsuranceNormal);
        rowItem.setMonthlyLongTermInsuranceNormal(monthlyLongTermInsuranceNormal);
        rowItem.setMonthlySpecificInsuranceNormal(monthlySpecificInsuranceNormal);
        rowItem.setMonthlyBasicInsuranceNormal(monthlyBasicInsuranceNormal);
        
        rowItem.setMonthlyHealthInsuranceDeduction(monthlyHealthInsuranceDeduction);
        rowItem.setMonthlyGeneralInsuranceDeduction(monthlyGeneralInsuranceDeduction);
        rowItem.setMonthlyLongTermInsuranceDeduction(monthlyLongTermInsuranceDeduction);
        rowItem.setMonthlySpecificInsuranceDeduction(monthlySpecificInsuranceDeduction);
        rowItem.setMonthlyBasicInsuranceDeduction(monthlyBasicInsuranceDeduction);
        
        rowItem.setWelfarePensionInsuranceNormal(welfarePensionInsuranceNormal);
        rowItem.setWelfarePensionInsuranceDeduction(welfarePensionInsuranceDeduction);
        rowItem.setWelfarePensionFundNormal(welfarePensionFundNormal);
        rowItem.setWelfarePensionFundDeduction(welfarePensionFundDeduction);
        rowItem.setChildRaisingContributionMoney(childRaisingContributionMoney);
        
        return rowItem;
    }
    
    private double calInsuredCollectAmount(DataRowItem totalAllOffice) {
        return (totalAllOffice.getMonthlyHealthInsuranceDeduction()
                + totalAllOffice.getWelfarePensionInsuranceDeduction()
                + totalAllOffice.getWelfarePensionFundDeduction());
    }
    
}
