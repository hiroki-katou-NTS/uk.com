package nts.uk.ctx.at.function.app.command.outputworkanualsetting;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateAnualWorkledgerSettingCommand {

    //ID -> GUID
    private final String iD;

    //コード
    private String code;

    // 名称
    private String name;

    // 定型自由区分
    private int settingCategory;

    // 日次出力項目リスト->DAILY
    private List<DailyOutputItemsCommand> dailyOutputItems;

    //社員ID
    private String employeeId;

    // 	月次出力項目リスト-MONTHLY
    private List<MonthlyOutputItemsCommand> monthlyOutputItems;
}
