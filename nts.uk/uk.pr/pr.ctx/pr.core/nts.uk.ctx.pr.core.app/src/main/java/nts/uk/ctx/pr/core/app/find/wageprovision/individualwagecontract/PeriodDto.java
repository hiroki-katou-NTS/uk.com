package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;

@AllArgsConstructor
@Value
class PeriodDto {
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

    public static PeriodDto fromDomain(GenericHistYMPeriod domain) {
        return new PeriodDto(domain.getHistoryID(),domain.getPeriodYearMonth().start().v(),domain.getPeriodYearMonth().end().v());

    }
}