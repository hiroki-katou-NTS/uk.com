package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.GenericHistYMPeriod;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Data
public class PeriodAndUnitPriceDto {
    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 開始年月
     */
    private int startYearMonth;

    /**
     * 終了年月
     */
    private int endYearMonth;
    /**
     * 個人単価
     */
    private BigDecimal individualUnitPrice;

    public static List<PeriodAndUnitPriceDto> fromDomain(List<GenericHistYMPeriod> domains){
       return domains.stream().map(v->  new PeriodAndUnitPriceDto(v.getHistoryID(),v.getPeriodYearMonth().getStartYearMonth(),v.getPeriodYearMonth().getEndYearMonth(),v.getIndividualUnitPrice().v())).collect(Collectors.toList());
    }

}
