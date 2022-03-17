package nts.uk.ctx.at.function.dom.holidaysremaining.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;

/**
 * 公休過去状況
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PublicHolidayPastSituation {
    // 年月
    private YearMonth ym;
    // 付与数 : 月別休暇付与日数
    private Double numberOfGrants;
    // 使用数 : 月別休暇使用日数
    private Double numberOfUse;

    // 残数 :月別休暇残日数
    private Double  numberOfRemaining;
    // 繰越数: 月別休暇残日数
    private Double  numberOfCarryforwards;
}
