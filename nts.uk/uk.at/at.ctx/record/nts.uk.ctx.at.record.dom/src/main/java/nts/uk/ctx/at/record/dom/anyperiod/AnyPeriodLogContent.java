package nts.uk.ctx.at.record.dom.anyperiod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;

import java.util.List;

/**
 * 任意実績のログ登録内容
 */
@AllArgsConstructor
@Getter
public class AnyPeriodLogContent {
    /** 会社ID */
    private String companyId;

    /** 社員ID */
    private String employeeId;

    /** 任意集計枠コード */
    private String anyPeriodFrameCode;

    /** 手修正リスト */
    private List<ItemInfo> correctedList;

    /** 計算後リスト */
    private List<ItemInfo> calculatedList;
}
