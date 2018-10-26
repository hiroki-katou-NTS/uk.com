package nts.uk.ctx.pr.transfer.app.find.rsdttaxpayee;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class RsdtTaxPayAmountParam {
    private List<String> listSId;
    private GeneralDate baseDate;
    private int year;
}
