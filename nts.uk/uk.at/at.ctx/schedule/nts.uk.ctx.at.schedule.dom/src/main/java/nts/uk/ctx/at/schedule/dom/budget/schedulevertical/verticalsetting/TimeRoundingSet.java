package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeRoundingSet {
	/* 会社ID */
    private String companyId;
    
    /* コード */
    private String code;
    
    /* 端数処理 */
    private Rounding rounding;
    
    /* 単位 */
    private RoundingTime roundingTime;
}
