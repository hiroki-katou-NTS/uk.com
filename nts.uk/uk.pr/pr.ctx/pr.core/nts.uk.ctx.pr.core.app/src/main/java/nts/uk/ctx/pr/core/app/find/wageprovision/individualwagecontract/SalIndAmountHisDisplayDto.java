package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;


@AllArgsConstructor
@Value
public class SalIndAmountHisDisplayDto {

    private List<String> perValCodeList;

    private String empId;

    private int cateIndicator;

    private int currentProcessYearMonth;

    private int salBonusCate;
}
