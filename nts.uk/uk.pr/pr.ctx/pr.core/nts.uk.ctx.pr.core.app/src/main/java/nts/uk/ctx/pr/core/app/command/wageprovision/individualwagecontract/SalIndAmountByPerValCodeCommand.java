package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.Value;

import java.util.List;

@Value
public class SalIndAmountByPerValCodeCommand {
    private String perValCode;

    private int cateIndicator;

    private int salBonusCate;

    private List<String> employeeIds;
}


