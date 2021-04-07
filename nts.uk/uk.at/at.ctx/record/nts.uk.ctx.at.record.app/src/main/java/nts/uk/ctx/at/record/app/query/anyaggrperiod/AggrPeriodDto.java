package nts.uk.ctx.at.record.app.query.anyaggrperiod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class AggrPeriodDto {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 任意集計枠コード
     */
    private String aggrFrameCode;

    /**
     * 任意集計名称
     */
    private String optionalAggrName;

    /**
     * 対象期間
     */
    private GeneralDate startDate;

    /**
     * 対象期間
     */
    private GeneralDate endDate;
}
