package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 勤務状況表の帳票表示内容
 * @author chih.hm
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DisplayContentWorkStatus {


    // 社員コード
    private String employeeCode;

    // 社員名
    private String employeeName;

    // 職場コード
    private String workPlaceCode;

    // 職場名
    private String workPlaceName;

    // 出力項目１行
    private List<OutputItemOneLine>  outputItemOneLines;
}
