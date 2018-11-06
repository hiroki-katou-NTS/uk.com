package nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo;

import lombok.Data;

import java.util.List;

@Data
public class RegisterEmployeeResidentTaxPayAmountInfoCommand {
    private List<EmployeeResidentTaxPayAmountInfoCommand> listEmpPayAmount;
    private int year;
}
