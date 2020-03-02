package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;

@AllArgsConstructor
@Value
public class GenericHistYMPeriodDto {

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 期間
     */

    private int periodStartYm;

    private int periodEndYm;


    public static GenericHistYMPeriodDto fromDomain(GenericHistYMPeriod domain) {
        return new GenericHistYMPeriodDto(domain.getHistoryID(), domain.getPeriodYearMonth().start().v(), domain.getPeriodYearMonth().end().v());
    }

}
