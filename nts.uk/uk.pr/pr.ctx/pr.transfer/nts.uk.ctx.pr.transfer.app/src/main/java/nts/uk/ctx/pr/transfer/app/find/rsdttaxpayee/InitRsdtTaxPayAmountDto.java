package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.transfer.dom.rsdttaxpayee.service.RsdtTaxPayAmountDto;

import java.util.List;

@AllArgsConstructor
@Getter
public class InitRsdtTaxPayAmountDto {
    List<EmpInfoDeptDto> ListEmpInfoDept;
    List<RsdtTaxPayAmountDto> listRsdtTaxPayAmount;
}
