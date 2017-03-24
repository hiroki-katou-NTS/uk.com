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

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.insurance.data.ChecklistPrintSettingDto;
import nts.uk.file.pr.app.export.insurance.data.DataRowItem;
import nts.uk.file.pr.app.export.insurance.data.InsuranceOfficeDto;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceHeaderReportData;
import nts.uk.file.pr.app.export.insurance.data.SalarySocialInsuranceReportData;

/**
 * @author duongnd
 *
 */

@Stateless
public class SalarySocialInsuranceReportService extends ExportService<SalarySocialInsuranceQuery> {

    @Inject
    private SalarySocialInsuranceGenerator generator;
    
    @Inject
    private SalarySocialInsuranceQueryProcessor salarySocialInsuQueryProcessor;
    
    @Override
    protected void handle(ExportServiceContext<SalarySocialInsuranceQuery> context) {
        // get query
        SalarySocialInsuranceQuery query = context.getQuery();
        // get data from repository follow query.
        ChecklistPrintSettingDto configOutput = salarySocialInsuQueryProcessor.findConfigureOutputSetting();
        SalarySocialInsuranceReportData reportData = fakeData();
        reportData.setConfigureOutput(configOutput);
        this.generator.generate(context.getGeneratorContext(), reportData);
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
    
    private SalarySocialInsuranceReportData fakeData() {
        SalarySocialInsuranceReportData reportData = new SalarySocialInsuranceReportData();
        
        SalarySocialInsuranceHeaderReportData headerData = new SalarySocialInsuranceHeaderReportData();
        headerData.setNameCompany("【日通 システム株式会社】");
        headerData.setTitleReport("会社保険料チェックリスト(被保険者)");
        headerData.setInformationOffice("【事業所:本社 ~ 福岡支社(3事業所)】");
        headerData.setTargetYearMonth("【対象年月:平成19年12月 給与】 ");
        headerData.setCondition("【出カ条件:算定月数と対象月控除が全て同じ、不定、超過】 ");
        headerData.setFormalCalculation("【計算方法:全体の保険料より被保険者分を差し引く方法】");
        reportData.setHeaderData(headerData);
        
        List<InsuranceOfficeDto> officeItems = fakeOffice();
        reportData.setOfficeItems(officeItems);
        List<DataRowItem> totalEachOffices = new ArrayList<>();
        for (InsuranceOfficeDto office : officeItems) {
            DataRowItem totalEachOffice = calculateTotal(office.getEmployeeDtos());
            office.setTotalEachOffice(totalEachOffice);
            totalEachOffices.add(totalEachOffice);
        }
        DataRowItem totalAllOffice = calculateTotal(totalEachOffices);
        reportData.setTotalAllOffice(totalAllOffice);
        
        return reportData;
    }
    
    private List<InsuranceOfficeDto> fakeOffice() {
        List<InsuranceOfficeDto> offices = new ArrayList<>();
        for(int i=0; i<20; i++) {
            InsuranceOfficeDto office = setOffice(i + 1);
            offices.add(office);
        }
        return offices;
    }
    private InsuranceOfficeDto setOffice(int index) {
        InsuranceOfficeDto office = new InsuranceOfficeDto();
        
        office.setNumberOfEmployee(5);
        office.setCode("A000" + index);
        office.setName("Office " + index);
        
        List<DataRowItem> employees = new ArrayList<>();
        for (int i=0; i< office.getNumberOfEmployee(); i++) {
            DataRowItem employee = setEmployee("Employee", 1);
            employees.add(employee);
        }
        office.setEmployeeDtos(employees);
        
        return office;
    }
    
    private DataRowItem setEmployee(String name, int indexRaw) {
        double index = indexRaw * 10000000;
        DataRowItem employee = new DataRowItem();
        
        employee.setCode("E000" + index);
        employee.setName(name +" " + index);
        
        employee.setMonthlyHealthInsuranceNormal(index);index++;
        employee.setMonthlyGeneralInsuranceNormal(index);index++;
        employee.setMonthlyLongTermInsuranceNormal(index);index++;
        employee.setMonthlySpecificInsuranceNormal(index);index++;
        employee.setMonthlyBasicInsuranceNormal(index);index++;
        
        employee.setMonthlyHealthInsuranceDeduction(index);index++;
        employee.setMonthlyGeneralInsuranceDeduction(index);index++;
        employee.setMonthlyLongTermInsuranceDeduction(index);index++;
        employee.setMonthlySpecificInsuranceDeduction(index);index++;
        employee.setMonthlyBasicInsuranceDeduction(index);index++;
        
        employee.setWelfarePensionInsuranceNormal(index);index++;
        employee.setWelfarePensionInsuranceDeduction(index);index++;
        employee.setWelfarePensionFundNormal(index);index++;
        employee.setWelfarePensionFundDeduction(index);index++;
        employee.setChildRaisingContributionMoney(index);index++;
        
        return employee;
    }

}
