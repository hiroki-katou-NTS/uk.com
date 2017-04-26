/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
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

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSetting;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSettingRepository;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.HeaderReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.SalaryPrintSettingDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.query.PaymentSalaryQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class PaymentSalaryReportService extends ExportService<PaymentSalaryQuery> {

    /** The generator. */
    @Inject
    private PaymentSalaryInsuranceGenerator generator;

    /** The repository. */
    @Inject
    private PaymentSalaryReportRepository repository;

    @Inject
    private SalaryPrintSettingRepository salaryPrintRepo;
    
    /** The Constant OUTPUT_FORMAT_TYPE_DETAIL. */
    private static final String OUTPUT_FORMAT_TYPE_DETAIL = "1";
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
     * .export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<PaymentSalaryQuery> context) {
        // get login user info
        LoginUserContext loginUserContext = AppContexts.user();

        // get company code by user login
        String companyCode = loginUserContext.companyCode();

        // get query
        PaymentSalaryQuery query = context.getQuery();

        if (this.repository.checkExport(companyCode, query)) {
            throw new BusinessException("ER010");
        }
        
        PaymentSalaryReportData reportData = this.repository.exportPDFPaymentSalary(companyCode, query);

        SalaryPrintSettingDto configure = findSalarySetting(query);
        reportData.setConfigure(configure);
        
        HeaderReportData header = findReportHeader(query);
        reportData.setHeaderData(header);

        this.generator.generate(context.getGeneratorContext(), reportData);
    }
    
    private SalaryPrintSettingDto findSalarySetting(PaymentSalaryQuery query) {
        SalaryPrintSettingDto dto = new SalaryPrintSettingDto();
        String companyCode = AppContexts.user().companyCode();
        SalaryPrintSetting salary = salaryPrintRepo.find(companyCode);
        
        dto.setShowPayment(salary.getShowPayment());
        dto.setSumPersonSet(salary.getSumPersonSet());
        dto.setSumMonthPersonSet(salary.getSumMonthPersonSet());
        dto.setSumEachDeprtSet(salary.getSumEachDeprtSet());
        dto.setSumMonthDeprtSet(salary.getSumMonthDeprtSet());
        dto.setSumDepHrchyIndexSet(salary.getSumDepHrchyIndexSet());
        dto.setSumMonthDepHrchySet(salary.getSumMonthDepHrchySet());
        List<Integer> listHierarchy = findHierarchy(salary);
        dto.setSelectedLevels(listHierarchy);
        dto.setTotalSet(salary.getTotalSet());
        dto.setMonthTotalSet(salary.getMonthTotalSet());
        
        // ===== SET OPTION REPORT =====
        dto.setOutputFormatType(query.getOutputFormatType());
        dto.setIsVerticalLine(query.getIsVerticalLine());
        dto.setIsHorizontalLine(query.getIsHorizontalLine());
        dto.setPageBreakSetting(query.getPageBreakSetting());
        dto.setHierarchy(query.getHierarchy());
        dto.setOutputLanguage(query.getOutputLanguage());
        
        // TODO: fake value
//        dto.setShowPayment(true);
//        dto.setSumPersonSet(true);
//        dto.setSumMonthPersonSet(true);
//        dto.setSumEachDeprtSet(true);
//        dto.setSumMonthDeprtSet(true);
//        dto.setSumDepHrchyIndexSet(true);
//        dto.setSumMonthDepHrchySet(true);
//        List<Integer> listHierarchy = Arrays.asList(1, 3, 6); //findHierarchy(salary);
//        dto.setSelectedLevels(listHierarchy);
//        dto.setTotalSet(true);
//        dto.setMonthTotalSet(true);
//        
//        dto.setOutputFormatType("1");
//        dto.setIsVerticalLine(false);
//        dto.setIsHorizontalLine(true);
//        dto.setPageBreakSetting("2");
//        dto.setHierarchy("2");
//        dto.setOutputLanguage("1");
        
        return dto;
    }
    
    private List<Integer> findHierarchy(SalaryPrintSetting salarySetting) {
        List<Integer> hierarchies = new ArrayList<>();
        if (salarySetting.getHrchyIndex1()) {
            hierarchies.add(1);
        }
        if (salarySetting.getHrchyIndex2()) {
            hierarchies.add(2);
        }
        if (salarySetting.getHrchyIndex3()) {
            hierarchies.add(3);
        }
        if (salarySetting.getHrchyIndex4()) {
            hierarchies.add(4);
        }
        if (salarySetting.getHrchyIndex5()) {
            hierarchies.add(5);
        }
        if (salarySetting.getHrchyIndex6()) {
            hierarchies.add(6);
        }
        if (salarySetting.getHrchyIndex7()) {
            hierarchies.add(7);
        }
        if (salarySetting.getHrchyIndex8()) {
            hierarchies.add(8);
        }
        if (salarySetting.getHrchyIndex9()) {
            hierarchies.add(9);
        }
        return hierarchies;
    }

    public PaymentSalaryReportData initData() {
        PaymentSalaryReportData rawData = fakeReportData();
        SalaryPrintSettingDto configure = findSalarySetting(null);
        List<Integer> listHierarchy = Arrays.asList(1, 3, 6);
        configure.setSelectedLevels(listHierarchy);
        rawData.setConfigure(configure);

//        HeaderReportData header = findReportHeader();
//        rawData.setHeaderData(header);

        return rawData;
    }

    private HeaderReportData findReportHeader(PaymentSalaryQuery query) {
        HeaderReportData header = new HeaderReportData();
        header.setNameCompany("【日通システム株式会社】");
        String titleReport = "明細累計表（給与）";
        if (query.getOutputFormatType() == OUTPUT_FORMAT_TYPE_DETAIL) {
            titleReport = "明細一覧表（給与）";
        }
        header.setTitleReport(titleReport);
        header.setDepartment("【部門　：役員　販売促進1課　役員～製造部　製造課　製造　(31部門)】");
        header.setCategory("【分類　：正社員～アルバイト　(5分類)】");
        header.setPosition("【職位　：参事～主任　(10職位)】");
        header.setTargetYearMonth("【処理年月：平成12年 1月度】");
        return header;
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
        List<String> yearMonths = Arrays.asList("2016/01", "2016/02");
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

        Map<EmployeeKey, Double> mapDeduction = fakeCategoryItem(SalaryCategory.Deduction, numberOfEmployee, employees);
        mapEmployeeAmount.putAll(mapDeduction);

        Map<EmployeeKey, Double> mapArticleOther = fakeCategoryItem(SalaryCategory.ArticleOthers, numberOfEmployee,
                employees);
        mapEmployeeAmount.putAll(mapArticleOther);

        return mapEmployeeAmount;
    }

    private Map<EmployeeKey, Double> fakeCategoryItem(SalaryCategory category, int numberOfEmployee,
            List<EmployeeDto> employees) {
        Map<EmployeeKey, Double> map = new HashMap<>();
        for (int j = 0; j < 10; j++) {
            double value = j + 1;
            for (int i = 0; i < employees.size(); i++) {
                EmployeeKey key = new EmployeeKey();
                key.setSalaryCategory(category);
                key.setYearMonth(employees.get(i).getYearMonth());
                key.setEmployeeCode(employees.get(i).getCode());
                key.setItemName("ItemName " + value);
                map.put(key, value);
            }
        }
        return map;
    }
}
