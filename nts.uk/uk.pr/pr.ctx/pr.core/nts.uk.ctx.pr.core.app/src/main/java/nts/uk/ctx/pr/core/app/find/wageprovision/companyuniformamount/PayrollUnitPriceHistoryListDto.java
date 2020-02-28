package nts.uk.ctx.pr.core.app.find.wageprovision.companyuniformamount;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class PayrollUnitPriceHistoryListDto {

    private String code;

    private String hisId;

    private String name;


    List<PayrollUnitPriceHistoryDto> payrollUnitPriceHistoryDto;
}
