package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 計算項目詳細
 */
@AllArgsConstructor
@Getter
public class CalculatedItemDetail {
    /** 社員ID */
    private String employeeId;

    /** 計算項目リスト */
    private List<Integer> calculatedItemIds;
}
