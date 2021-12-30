package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;


/**
 * 公休当月未来状況
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FutureSituationOfTheMonthPublicHoliday {
    //年月　　　←INPUT．集計年月
    private YearMonth ym;
    // 公休繰越数←[*1]．公休消化情報．繰越数
    private Double numberOfCarryforwards;
    // 公休付与数←[*1]．公休消化情報．公休日数
    private Double numberOfGrants;
    // 公休使用数←[*1]．公休消化情報．取得数
    private Double numberOfUse;
    // 公休残数　←[*1]．公休繰越情報．翌月繰越数
    private Double  numberOfRemaining;
}
