package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.DailyData;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.MonthlyData;

import java.util.List;

/**
 * 勤務台帳の帳票表示内容
 *
 * @author khai.dh
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkLedgerDisplayContent {
    // 月次出力１行
    private List<MonthlyOutputLine> monthlyDataList;

    // 社員コード
    private String employeeCode;

    // 社員名
    private String employeeName;

    // 職場コード
    private String workplaceCode;

    // 職場名称
    private String workplaceName;
}
