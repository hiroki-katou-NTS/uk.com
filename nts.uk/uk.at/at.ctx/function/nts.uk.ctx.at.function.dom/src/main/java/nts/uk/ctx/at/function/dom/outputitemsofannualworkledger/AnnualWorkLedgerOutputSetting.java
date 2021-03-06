package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemWorkLedger;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;

/**
 * AggregateRoot: 年間勤務台帳の出力設定
 *
 * @author chinh.hm
 */
@AllArgsConstructor
@Getter
public class AnnualWorkLedgerOutputSetting extends AggregateRoot {
    //ID -> GUID
    private final String iD;

    //コード
    private OutputItemSettingCode code;

    // 名称
    private OutputItemSettingName name;

    // 定型自由区分
    private SettingClassificationCommon standardFreeDivision;

    // 日次出力項目リスト->DAILY
    private List<DailyOutputItemsAnnualWorkLedger> dailyOutputItemList;

    //社員ID
    private String employeeId;

    // 	月次出力項目リスト-MONTHLY
    private List<OutputItemWorkLedger> monthlyOutputItemList;


    // 	[C-0] 年間勤務台帳の出力設定を作成する

    // [1]　定型選択の重複をチェックする
    public static boolean  checkDuplicateStandardSelection(Require require, OutputItemSettingCode code) {
        return require.checkTheStandard(code);
    }

    // [2]　自由設定の重複をチェックする
    public static boolean checkDuplicateFreeSettings(Require require, OutputItemSettingCode code, String employeeId) {
        return require.checkFreedom(code,employeeId);
    }

    public interface Require {
        // 	[R-1]　定型をチェックする
        // 	年間勤務台帳の出力項目Repository. exist(コード、ログイン会社ID)
        boolean checkTheStandard(OutputItemSettingCode code);

        //	[R-2]  自由をチェックする
        //  年間勤務台帳の出力項目Repository. exist(コード、ログイン会社ID、ログイン社員ID)
        boolean checkFreedom(OutputItemSettingCode code, String employeeId);
    }
}
