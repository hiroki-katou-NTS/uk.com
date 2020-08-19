package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 予約修正内容
 */

@AllArgsConstructor
@Getter
public class CorrectionContent extends DomainObject {

    /**
     * 予約済みの内容変更期限
     */
    // Content change deadline.
    private ContentChangeDeadline contentChangeDeadline;

    /**
     * 予約済みの内容変更期限日数
     */
    // content change deadline days.
    private ContentChangeDeadlineDay contentChangeDeadlineDay;

    /**
     * 注文済みデータ
     */
    // Ordered data.
    private OrderedData orderedData;

    /**
     * 注文済み期限
     */
    // Order deadline.
    private OrderDeadline orderDeadline;

}
