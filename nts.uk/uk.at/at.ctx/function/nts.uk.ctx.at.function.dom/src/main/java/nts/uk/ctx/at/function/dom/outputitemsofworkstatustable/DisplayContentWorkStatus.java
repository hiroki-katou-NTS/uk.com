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


    DisplayContenteEmployeeInfor infor;

    // 出力項目１行
    private List<OutputItemOneLine>  outputItemOneLines;
}
