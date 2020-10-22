package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 年間勤務台帳の帳票表示内容
 */

@Getter
@Setter
@AllArgsConstructor
public class AnnualWorkLedgerContent {

    // 日次データ
    private List<DailyData> lstDailyData;

    // 月次データ１行
    private List<MonthlyData> lstMonthlyData;

    // 社員コード
    private String employeeCode;

    // 社員名
    private String employeeName;

    // 締め日
    private String closureDate;

    // 職場コード
    private String workplaceCode;

    // 職場名称
    private String workplaceName;

    // 雇用コード
    private String employmentCode;

    // 雇用名称
    private String employmentName;

}
