package nts.uk.ctx.at.schedule.dom.budget.premium;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.auth.dom.employmentrole.DisabledSegment;


/**
 * インセンティブ単価管理
 * @author chinh.hm
 */
@AllArgsConstructor
public class IncentiveUnitPriceManagement extends AggregateRoot {
    // 会社ID
    private String companyId;
    // 計算する: するしない区分
    private DisabledSegment caculate;

}
