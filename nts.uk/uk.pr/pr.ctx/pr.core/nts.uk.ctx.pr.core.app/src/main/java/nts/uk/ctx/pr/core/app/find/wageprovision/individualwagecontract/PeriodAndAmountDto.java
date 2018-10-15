package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Objects;

@AllArgsConstructor
@Value
public class PeriodAndAmountDto  {
    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 期間
     */
    private int periodStartYm;

    /**
     * 期間
     */
    private int periodEndYm;

    /**
     * 金額
     */
    private long amountOfMoney;


    public static PeriodAndAmountDto fromDomain(PeriodDto periodDto,SalIndAmountDto salIndAmountDto){

        if(Objects.isNull(periodDto) ||Objects.isNull(salIndAmountDto))
            return null;

        return new PeriodAndAmountDto(
                periodDto.getHistoryID(),
                periodDto.getPeriodStartYm(),
                periodDto.getPeriodEndYm(),
                salIndAmountDto.getAmountOfMoney()
        );
    }
}
