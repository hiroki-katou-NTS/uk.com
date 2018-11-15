package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import nts.uk.ctx.pr.transfer.dom.adapter.employee.EmployeeInformationAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.employee.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.service.RsdtTaxPayAmountDto;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.service.RsdtTaxPayAmountService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RsdtTaxPayAmountFinder {

    @Inject
    private EmployeeInformationAdapter employeeInformationAdapter;
    @Inject
    private RsdtTaxPayAmountService rsdtTaxPayAmountService;

    /**
     * 部門取得処理
     */
    public List<EmpInfoDeptDto> getEmpInfoDept(RsdtTaxPayAmountParam param) {
        // ドメインモデル「所属部門」をすべて取得する
        return employeeInformationAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(param.getListSId(), param.getBaseDate(),
                        false,
                        true,
                        false,
                        false,
                        false,
                        false)).stream().map(x -> {
                    EmpInfoDeptDto dto = new EmpInfoDeptDto();
                    dto.setSid(x.getEmployeeId());
                    if(x.getDepartment() != null){
                        dto.setDepartmentName(x.getDepartment().getDepartmentName());
                    }else{
                        dto.setDepartmentName("");
                    }
                    return dto;
                }).collect(Collectors.toList());
    }

    /**
     * 対象データ取得処理
     */
    public List<RsdtTaxPayAmountDto> getRsdtTaxPayAmount(RsdtTaxPayAmountParam param) {
        //アルゴリズム「対象データ取得処理」を実施する
        return rsdtTaxPayAmountService.getRsdtTaxPayAmount(param.getListSId(), param.getYear());
    }
}
