package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;


import java.util.List;

/**
 * 勤務状況表の帳票表示内容
 * @author chih.hm
 */
public class DisplayContentWorkStatus {
    // 出力項目１行
    private List<OutputItemOneLine>  outputItemOneLines;

    // 社員コード
    private int employeeCode;

    // 社員名
    private String employeeName;

    // 職場コード
    private int workPlaceCode;

    // 職場名
    private String workPlaceName;
}
