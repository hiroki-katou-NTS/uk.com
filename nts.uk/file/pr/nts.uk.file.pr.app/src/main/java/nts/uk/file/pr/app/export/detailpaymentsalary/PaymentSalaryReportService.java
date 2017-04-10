/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
/**
 * 
 */
package nts.uk.file.pr.app.export.detailpaymentsalary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryPrintSettingDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;

/**
 * @author duongnd
 *
 */
@Stateless
public class PaymentSalaryReportService extends ExportService<PaymentSalaryQuery> {

    @Inject
    private PaymentSalaryInsuranceGenerator generator;
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
     * .export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<PaymentSalaryQuery> context) {
//         PaymentSalaryQuery query = context.getQuery();

        List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
        PaymentSalaryReportData rawData = fakeReportData();
        
        PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
        configure.setSelectedLevels(selectedLevels);
        rawData.setConfigure(configure);
        
        this.generator.generate(context.getGeneratorContext(), rawData);
    }
    
//    private void testConvertData() {
//        List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
//        PaymentSalaryReportData rawData = fakeReportData();
//        
//        PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
//        configure.setSelectedLevels(selectedLevels);
//        rawData.setConfigure(configure);
//        
//        System.out.println(rawData);
//    }
    
    public PaymentSalaryReportData initData() {
        List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
        PaymentSalaryReportData rawData = fakeReportData();
        PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
        configure.setSelectedLevels(selectedLevels);
        rawData.setConfigure(configure);
        return rawData;
    }
    
    
    private PaymentSalaryReportData fakeReportData() {
        PaymentSalaryReportData reportData = new PaymentSalaryReportData();
        int numberOfEmployee = 1;
        List<EmployeeDto> employees = fakeEmployees(numberOfEmployee);
        reportData.setEmployees(employees);

        Map<EmployeeKey, Double> mapEmployeeAmount = fakeMapAmount(numberOfEmployee, employees);
        reportData.setMapEmployeeAmount(mapEmployeeAmount);

        return reportData;
    }

    private List<EmployeeDto> fakeEmployees(int numberOfEmployee) {
        List<EmployeeDto> result = new ArrayList<>();
        List<DepartmentDto> depData = new ArrayList<>();
        List<String> depPath = new ArrayList<>();
        depPath.add("A_");
        depPath.add("B_");
        depPath.add("B_C");
        depPath.add("B_C_D");
        depPath.add("B_C_D_E");
        depPath.add("B_F");
        depPath.add("B_F_G");
        depPath.add("B_H");
        depPath.add("B_H_I");
        depPath.add("K_");

        List<String> depCode = new ArrayList<>();
        depCode.add("A");
        depCode.add("B");
        depCode.add("C");
        depCode.add("D");
        depCode.add("E");
        depCode.add("F");
        depCode.add("G");
        depCode.add("H");
        depCode.add("I");
        depCode.add("K");

        List<Integer> depLevels = Arrays.asList(1, 1, 2, 3, 4, 2, 3, 2, 3, 1);
        List<String> yearMonths = Arrays.asList("2016/01");
        for (String yearMonth : yearMonths) {
            for (int i = 0; i < depCode.size(); i++) {
                DepartmentDto dep = new DepartmentDto();
                dep.setYearMonth(yearMonth);
                dep.setCode(depCode.get(i));
                dep.setName("部門 " + (i + 1));
                dep.setDepPath(depPath.get(i));
                dep.setDepLevel(depLevels.get(i));
                depData.add(dep);
                for (int j = 0; j < numberOfEmployee; j++) {
                    EmployeeDto emp = new EmployeeDto();
                    emp.setYearMonth(yearMonth);
                    emp.setCode(depCode.get(i) + "-E000" + (j + 1));
                    emp.setName("E社員 " + (j + 1));
                    emp.setDepartment(dep);
                    result.add(emp);
                }
            }
        }
        return result;
    }

    private Map<EmployeeKey, Double> fakeMapAmount(int numberOfEmployee, List<EmployeeDto> employees) {
        Map<EmployeeKey, Double> mapEmployeeAmount = new HashMap<>();

        Map<EmployeeKey, Double> mapPayment = fakeCategoryItem(SalaryCategory.Payment, numberOfEmployee, employees);
        mapEmployeeAmount.putAll(mapPayment);

//        Map<EmployeeKey, Double> mapDeduction = fakeCategoryItem(SalaryCategory.Deduction, numberOfEmployee, employees);
//        mapEmployeeAmount.putAll(mapDeduction);
//
//        Map<EmployeeKey, Double> mapArticleOther = fakeCategoryItem(SalaryCategory.ArticleOthers, numberOfEmployee,
//                employees);
//        mapEmployeeAmount.putAll(mapArticleOther);

        return mapEmployeeAmount;
    }

    private Map<EmployeeKey, Double> fakeCategoryItem(SalaryCategory category, int numberOfEmployee, List<EmployeeDto> employees) {
        Map<EmployeeKey, Double> map = new HashMap<>();
        for (int j = 0; j < 10; j++) {
            double value = j + 1;
            for (int i = 0; i < employees.size(); i++) {
                EmployeeKey key = new EmployeeKey();
                key.setSalaryCategory(category);
                key.setYearMonth(employees.get(i).getYearMonth());
                key.setEmployeeCode(employees.get(i).getCode());
                key.setItemName("ItemName " + (j + 1));
                map.put(key, value);
            }
        }
        return map;
    }
    
}
