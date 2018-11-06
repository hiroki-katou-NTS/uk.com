package nts.uk.ctx.pr.core.app.command.emprsdttaxinfo.amountinfo;

import lombok.Data;

import java.util.List;

@Data
public class DeleteEmployeeResidentTaxPayAmountInfoCommand {
    private List<String> listSId;
    private int year;
}
