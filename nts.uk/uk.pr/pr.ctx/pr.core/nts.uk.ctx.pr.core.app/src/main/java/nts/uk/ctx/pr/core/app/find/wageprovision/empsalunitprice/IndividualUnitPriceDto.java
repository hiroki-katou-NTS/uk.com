package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.IndEmpSalUnitPriceHistory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class IndividualUnitPriceDto {

    String employeeId;

    String perUnitPriceCode;

    String perUnitPriceName;

    int baseYearMonth;

    int startYearMonth;

    int endYearMonth;

    BigDecimal amountOfMoney;

}
