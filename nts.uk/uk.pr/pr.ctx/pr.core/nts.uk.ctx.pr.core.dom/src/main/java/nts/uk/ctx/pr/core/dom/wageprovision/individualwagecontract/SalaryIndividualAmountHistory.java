package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SalaryIndividualAmountHistory {

    String empId;

    String historyId;

    int salBonusCate;

    int cateIndicator;

    String perValCode;

    public int periodStartYm;

    public int periodEndYm;

    public long amountOfMoney;

}
