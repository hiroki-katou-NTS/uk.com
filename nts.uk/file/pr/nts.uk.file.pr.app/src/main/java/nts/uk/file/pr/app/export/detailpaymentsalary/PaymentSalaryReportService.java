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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryCategory;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.CategoryItem;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DataItem;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.DepartmentDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.EmployeeKey;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryPrintSettingDto;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.PaymentSalaryReportData;
import nts.uk.file.pr.app.export.detailpaymentsalary.data.RowItemDto;

/**
 * @author duongnd
 *
 */
@Stateless
public class PaymentSalaryReportService extends ExportService<PaymentSalaryQuery> {
//@NoArgsConstructor
//public class PaymentSalaryReportService {

    @Inject
    private PaymentSalaryInsuranceGenerator generator;
    
    private static final String SPACES = "     ";
    
//    public static void main(String[] args) {
//        new PaymentSalaryReportService().testConvertData();
//    }
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file
     * .export.ExportServiceContext)
     */
    @Override
    protected void handle(ExportServiceContext<PaymentSalaryQuery> context) {
         PaymentSalaryQuery query = context.getQuery();

        List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
        PaymentSalaryReportData rawData = fakeReportData();
        
        PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
        configure.setSelectedLevels(selectedLevels);
        rawData.setConfigure(configure);
        
        PaymentSalaryReportData reportData = processData(rawData);

        this.generator.generate(context.getGeneratorContext(), reportData);
    }
    
    private void testConvertData() {
        List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
        PaymentSalaryReportData rawData = fakeReportData();
        
        PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
        configure.setSelectedLevels(selectedLevels);
        rawData.setConfigure(configure);
        
        PaymentSalaryReportData reportData = processData(rawData);
        
        System.out.println(reportData);
    }
    
    public PaymentSalaryReportData initData() {
        List<Integer> selectedLevels = Arrays.asList(1, 3, 6);
        PaymentSalaryReportData rawData = fakeReportData();
        PaymentSalaryPrintSettingDto configure = new PaymentSalaryPrintSettingDto();
        configure.setSelectedLevels(selectedLevels);
        rawData.setConfigure(configure);
        PaymentSalaryReportData reportData = processData(rawData);
        return reportData;
    }
    
    public PaymentSalaryReportData processData(PaymentSalaryReportData reportData) {
        List<CategoryItem> categoryItems = convertCategories(reportData.getMapEmployeeAmount(),
                reportData.getEmployees(), reportData.getConfigure().getSelectedLevels());
        reportData.setCategoryItems(categoryItems);
        
        List<String> reportTitle = findReportTitleItem(reportData.getEmployees(),
                categoryItems.get(0).getRowItems().get(0));
        reportData.setReportTitleItems(reportTitle);
        
        return reportData;
    }

    private List<String> findReportTitleItem(List<EmployeeDto> employees, RowItemDto rowItem) {
        List<String> titles = new ArrayList<>();
        titles.add("社員");
        
        // map information of department
        Map<String, String> mapDepartment = employees.stream()
                .filter(distinctByKey(p -> p.getDepartment().getCode()))
                .map(p -> p.getDepartment())
                .collect(Collectors.toMap(p1 -> p1.getCode(), p2 -> p2.getName()));
        
        // find title of employees
        List<EmployeeDto> emps = employees.stream()
                .sorted((p1, p2) -> p1.getYearMonth().compareTo(p2.getYearMonth()))
                .map(p -> {
                    EmployeeDto emp = new EmployeeDto();
                    emp.setYearMonth(p.getYearMonth());
                    emp.setCode(p.getCode());
                    emp.setName(p.getName());
                    return emp;
                })
                .collect(Collectors.toList());
        List<String> titleEmps = emps.stream()
                .map(p -> {
                    String title = p.getYearMonth() + "\n" + p.getCode() + SPACES + p.getName();
                    return title;
                })
                .collect(Collectors.toList());
        titles.addAll(titleEmps);
        
        String titleTotalEmps = "Total Of Employee";
        titles.add(titleTotalEmps);
        
        List<DepartmentDto> deparmentMonths = employees.stream()
//                .filter(distinctByKey(p -> p.getDepartment().getYearMonth()))
                .sorted((p1, p2) -> p1.getYearMonth().compareTo(p2.getYearMonth()))
                .map(p -> p.getDepartment())
                .collect(Collectors.toList());
        List<String> titleDeps = deparmentMonths.stream()
                .map(p -> {
                    String title = p.getYearMonth() + "\n" + p.getCode() + SPACES + p.getName();
                    return title;
                })
                .collect(Collectors.toList());
        titles.addAll(titleDeps);
        
        String titleTotalDeps = "Total Of Department";
        titles.add(titleTotalDeps);
        
        List<String> hierarchyDeps = new ArrayList<>();
        List<DataItem> amountHierarchyDeps = rowItem.getAmountHierarchyDepartments();
        for(DataItem item : amountHierarchyDeps) {
            String title = item.getYearMonth() + "\n" + item.getCode() + SPACES + mapDepartment.get(item.getCode()) + SPACES + "Level "
                    + item.getLevel();
            hierarchyDeps.add(title);
        }
        titles.addAll(hierarchyDeps);
        String titleTotalhierarchyDeps = "Total Of Hierarchy Department";
        titles.add(titleTotalhierarchyDeps);
        
        return titles;
    }
    
    private List<CategoryItem> convertCategories(Map<EmployeeKey, Double> mapAmount, List<EmployeeDto> employees,
            List<Integer> selectedLevels) {
        List<CategoryItem> categoryItems = new ArrayList<>();
        
        List<DepartmentDto> departments = employees.stream()
                .filter(distinctByKey(p -> p.getDepartment().getCode()))
                .sorted((p1, p2) -> p1.getDepartment().getYearMonth().compareTo(p2.getDepartment().getYearMonth()))
                .map(p -> p.getDepartment())
                .collect(Collectors.toList());
        List<HierarchyDepartmentModel> depModels = findHierarchyDepartmentModel(departments);
        
        Map<EmployeeKey, Double> mapPayment = findMapCategory(mapAmount, SalaryCategory.Payment);
        if (!mapPayment.isEmpty()) {
            CategoryItem category = convertToCategoryItem(mapPayment, employees, SalaryCategory.Payment,
                    depModels, selectedLevels);
            categoryItems.add(category);
        }
        Map<EmployeeKey, Double> mapDeduction = findMapCategory(mapAmount, SalaryCategory.Deduction);
        if (!mapDeduction.isEmpty()) {
            CategoryItem category = convertToCategoryItem(mapDeduction, employees, SalaryCategory.Deduction,
                    depModels, selectedLevels);
            categoryItems.add(category);
        }
        Map<EmployeeKey, Double> mapAttendance = findMapCategory(mapAmount, SalaryCategory.Attendance);
        if (!mapAttendance.isEmpty()) {
            CategoryItem category = convertToCategoryItem(mapAttendance, employees, SalaryCategory.Attendance,
                    depModels, selectedLevels);
            categoryItems.add(category);
        }
        Map<EmployeeKey, Double> mapArticleOthers = findMapCategory(mapAmount, SalaryCategory.ArticleOthers);
        if (!mapArticleOthers.isEmpty()) {
            CategoryItem category = convertToCategoryItem(mapArticleOthers, employees, SalaryCategory.ArticleOthers,
                    depModels, selectedLevels);
            categoryItems.add(category);
        }
        return categoryItems;
    }

    private Map<EmployeeKey, Double> findMapCategory(Map<EmployeeKey, Double> mapAmount, SalaryCategory category) {
        return mapAmount.entrySet()
                .stream()
                .filter(item -> item.getKey().getSalaryCategory() == category)
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    private CategoryItem convertToCategoryItem(Map<EmployeeKey, Double> mapCategory, List<EmployeeDto> employees, SalaryCategory category,
            List<HierarchyDepartmentModel> depModels, List<Integer> selectedLevels) {
        CategoryItem categoryItem = new CategoryItem();
        categoryItem.setSalaryCategory(category);
        
        // get list department follow year month
        List<DepartmentDto> departmentByMonths = employees.stream()
                .map(p -> p.getDepartment())
                .collect(Collectors.toList());
        
        List<RowItemDto> rowItems = new ArrayList<>();
        // get list item name of category item.
        List<String> itemNames = mapCategory.entrySet()
                .stream()
                .map(p -> p.getKey().getItemName())
                .distinct()
                .collect(Collectors.toList());
        for (String itemName : itemNames) {
            RowItemDto rowItem = new RowItemDto();
            rowItem.setItemName(itemName);
//            for (String yearMonth : yearMonths) {
                List<DataItem> amountEmmployees = mapCategory.entrySet()
                        .stream()
                        .filter(p -> p.getKey().getItemName().equals(itemName))
                        .sorted((p1, p2) -> p1.getKey().getYearMonth().compareTo(p2.getKey().getYearMonth()))
                        .map(p -> {
                            DataItem item = new DataItem();
                            item.setYearMonth(p.getKey().getYearMonth());
                            item.setCode(p.getKey().getEmployeeCode());
                            item.setAmount(p.getValue());
                            return item;
                        }).collect(Collectors.toList());
                rowItem.setAmountEmployees(amountEmmployees);
    
                // calculate total department monthly.
                List<DataItem> amountDepartments = calculateDepartmentMonth(amountEmmployees, employees, departmentByMonths);
                rowItem.setAmountDepartments(amountDepartments);
                
                // TODO: total hierarchy department monthly
                List<DataItem> amountHierarchyDepartments = calculateHierarchyDepartment(amountDepartments,
                        depModels, selectedLevels);
                rowItem.setAmountHierarchyDepartments(amountHierarchyDepartments);
    
                rowItems.add(rowItem);
//            }
        }
        categoryItem.setRowItems(rowItems);
        return categoryItem;
    }

    private List<DataItem> calculateDepartmentMonth(List<DataItem> amountEmmployees, List<EmployeeDto> emps,
            List<DepartmentDto> departments) {
        List<DataItem> amountDepartments = new ArrayList<>();
        for (DepartmentDto dep : departments) {
            String yearMonth = dep.getYearMonth();
            DataItem deparmentItem = new DataItem();
            deparmentItem.setYearMonth(yearMonth);
            deparmentItem.setCode(dep.getCode());
            List<String> codeEmpOfDeps = emps.stream()
                    .filter(p -> p.getYearMonth().equals(yearMonth)
                            && p.getDepartment().getCode().equals(dep.getCode()))
                    .map(p -> p.getCode())
                    .collect(Collectors.toList());
            double totalDepartmentMonth = amountEmmployees.stream()
                    .filter(p -> p.getYearMonth().equals(yearMonth) && codeEmpOfDeps.contains(p.getCode()))
                    .mapToDouble(p -> p.getAmount())
                    .sum();
            deparmentItem.setAmount(totalDepartmentMonth);
            amountDepartments.add(deparmentItem);
        }
        return amountDepartments;
    }
    
    private List<DataItem> calculateHierarchyDepartment(List<DataItem> amountDepartments,
            List<HierarchyDepartmentModel> depModels, List<Integer> selectedLevels) {
        List<DataItem> dataItems = new ArrayList<>();
        List<String> yearMonths = amountDepartments.stream()
                .map(p ->p.getYearMonth())
                .distinct()
                .collect(Collectors.toList());
        // calculate amount hierarchy department by level.
        for (String yearMonth : yearMonths) {
            for (Integer level : selectedLevels) {
            List<List<String>> depCodes = findDepartmentByLevel(depModels, level);
                for (List<String> codes : depCodes) {
                    DataItem item = new DataItem();
                    item.setYearMonth(yearMonth);
                    item.setLevel(level);
                    item.setCode(codes.get(0));
                    double amount = amountDepartments.stream()
                            .filter(p -> p.getYearMonth().equals(yearMonth) && codes.contains(p.getCode()))
                            .mapToDouble(p -> p.getAmount())
                            .sum();
                    item.setAmount(amount);
                    
                    dataItems.add(item);
                }
            }
        }
        return dataItems;
    }
    
    private List<List<String>> findDepartmentByLevel(List<HierarchyDepartmentModel> depModels, int level) {
        List<HierarchyDepartmentModel> subDepModels = depModels.stream()
                .filter(p -> p.getDepCodes().size() >= level)
                .collect(Collectors.toList());
        List<List<String>> codeDepRaws = new ArrayList<>();
        for (HierarchyDepartmentModel depModel : subDepModels) {
            int size = depModel.getDepCodes().size();
            List<String> depCodes = depModel.getDepCodes().subList(level - 1, size);
            if (!depCodes.isEmpty()) {
                codeDepRaws.add(depCodes);
            }
        }
        List<List<String>> codeDeps = mergeElementHierarchyDep(codeDepRaws);
        return codeDeps;
    }
    
    private List<List<String>> mergeElementHierarchyDep(List<List<String>> codeDepRaws) {
        List<List<String>> codeDeps = new ArrayList<>(codeDepRaws);
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < codeDeps.size(); i++) {
            List<String> codeDepFirst = codeDeps.get(i);
            String firstElement = codeDepFirst.get(0);
            String firstValue = String.join("", codeDepFirst);
            List<String> tmpResult = new ArrayList<>(codeDepFirst);
            for (int j = 0; j < codeDeps.size(); j++) {
                List<String> codeDepSecond = new ArrayList<>(codeDeps.get(j));
                String secondValue = String.join("", codeDepSecond);
                if (!firstValue.equals(secondValue)) {
                    if (secondValue.startsWith(firstElement)) {
                        codeDepSecond.remove(0);
                        tmpResult.addAll(codeDepSecond);
                        codeDeps.remove(j);
                        j--;
                    }
                }
            }
            result.add(tmpResult);
        }
        return result;
    }
    
    private List<HierarchyDepartmentModel> findHierarchyDepartmentModel(List<DepartmentDto> departments) {
        List<HierarchyDepartmentModel> depModels = new ArrayList<>();
        Iterator<DepartmentDto> iteratorDep = departments.iterator();
        Stack<DepartmentDto> stackDep = new Stack<>();
        DepartmentDto prevDep = null;
        while(iteratorDep.hasNext()) {
            DepartmentDto currentDep = iteratorDep.next();
            if (prevDep == null) {
                stackDep.push(currentDep);
                prevDep = currentDep;
                continue;
            }
            if (currentDep.getDepPath().startsWith(prevDep.getDepPath())) {
                stackDep.push(currentDep);
                prevDep = currentDep;
                continue;
            }
            HierarchyDepartmentModel model = convertToModel(stackDep);
            depModels.add(model);
            if (currentDep.getDepLevel() == prevDep.getDepLevel()) {
                stackDep.pop();
                stackDep.push(currentDep);
                prevDep = currentDep;
            } else {
                while (true) {
                    DepartmentDto dep = stackDep.pop();
                    if (dep.getDepLevel() == currentDep.getDepLevel()) {
                        stackDep.push(currentDep);
                        prevDep = currentDep;
                        break;
                    }
                }
            }
            
        }
        if (!stackDep.isEmpty()) {
            HierarchyDepartmentModel model = convertToModel(stackDep);
            depModels.add(model);
        }
        return depModels;
    }
    
    private HierarchyDepartmentModel convertToModel(Stack<DepartmentDto> stackDep) {
        HierarchyDepartmentModel depModel = new HierarchyDepartmentModel();
        List<DepartmentDto> departments = stackDep.stream()
                .collect(Collectors.toList());
        List<String> depCodes = departments.stream()
                .map(p -> p.getCode())
                .collect(Collectors.toList());
        depModel.setCodeDepParent(departments.get(0).getCode());
        depModel.setDepCodes(depCodes);
        return depModel;
    }
    
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
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

@Setter
@Getter
class HierarchyDepartmentModel {
    private String codeDepParent;
    private List<String> depCodes;
}
