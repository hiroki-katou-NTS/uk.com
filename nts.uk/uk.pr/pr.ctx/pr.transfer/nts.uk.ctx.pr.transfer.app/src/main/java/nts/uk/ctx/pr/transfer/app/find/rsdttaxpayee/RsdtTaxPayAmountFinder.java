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
     * 初期データ取得処理
     */
    public InitRsdtTaxPayAmountDto initRsdtTaxPayAmount(RsdtTaxPayAmountParam param) {
        // アルゴリズム「部門取得処理」を実行する
        List<EmpInfoDeptDto> ListEmpInfoDept = employeeInformationAdapter
                .getEmployeeInfo(new EmployeeInformationQueryDtoImport(param.getListSId(), param.getBaseDate(),
                        false,
                        true,
                        false,
                        false,
                        false,
                        false)).stream().map(x -> EmpInfoDeptDto.builder()
                        .sid(x.getEmployeeId())
                        .departmentCode(x.getDepartment().getDepartmentCode())
                        .departmentName(x.getDepartment().getDepartmentName())
                        .build()).collect(Collectors.toList());

        //アルゴリズム「対象データ取得処理」を実施する
        List<RsdtTaxPayAmountDto> listRsdtTaxPayAmount = rsdtTaxPayAmountService.getRsdtTaxPayAmount(param.getListSId(),
                param.getYear());

        return new InitRsdtTaxPayAmountDto(ListEmpInfoDept, listRsdtTaxPayAmount);
    }

    /**
     * 対象データ取得処理
     */
    public List<RsdtTaxPayAmountDto> getRsdtTaxPayAmount(RsdtTaxPayAmountParam param) {
        //アルゴリズム「対象データ取得処理」を実施する
        return rsdtTaxPayAmountService.getRsdtTaxPayAmount(param.getListSId(), param.getYear());
    }
}
