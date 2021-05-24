package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Optional;

/**
 * 	集計項目詳細
 */
@AllArgsConstructor
@Getter
public class SummaryItemDetail extends ValueObject {
    /** コード */
    private String code;
    /** 表示情報 */
    private DisplayInformation displayInfo;
    /** 子階層リスト */
    private SummaryItemDetail childHierarchyList;
    /** 縦計リスト : 	List<日々縦計値> */
    // TODO: ĐANG QA
    /** 期間合計 */
    private Optional<Integer> totalPeriod;
}
