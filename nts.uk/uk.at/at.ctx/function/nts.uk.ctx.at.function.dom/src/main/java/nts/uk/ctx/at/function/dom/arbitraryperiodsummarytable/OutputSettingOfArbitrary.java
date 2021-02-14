package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import java.util.List;
import java.util.Optional;

/**
 * AggregateRoot: 任意期間集計表の出力設定
 *
 * @author : chinh.hm
 */

@AllArgsConstructor
@Getter
public class OutputSettingOfArbitrary extends AggregateRoot {
    // 設定ID: 	GUID
    private final String settingId;

    // 設定名称->	出力項目設定名称
    private OutputItemSettingCode code;

    // 設定名称 -> 出力項目設定名称
    private OutputItemSettingName name;

    // 社員ID: 社員ID
    private Optional<String> employeeId;

    // 定型自由区分: 帳票共通の設定区分
    private SettingClassificationCommon standardFreeClassification;

    // 	選択勤怠項目リスト  -> 	印刷する勤怠項目
    private List<AttendanceItemToPrint> outputItemList;

    /**
     * [Static-1]　定型選択の重複をチェックする
     *
     * @param require @Require
     * @param code    コード
     */
    public static boolean checkDuplicateBoilerplateSelection(Require require, OutputItemSettingCode code) {
        return require.checkTheFixedForm(code);
    }

    /**
     * [Static-2]　自由設定の重複をチェックする
     *
     * @param require    @Require
     * @param code       コード
     * @param employeeId 社員ID
     */
    public static boolean checkDuplicateFreeSettings(Require require, OutputItemSettingCode code, String employeeId) {
        return require.freeDomCheck(code, employeeId);
    }

    public interface Require {
        /**
         * [R-1]　定型をチェックする
         * 任意期間集計表の出力項目Repository. exist(コード、ログイ)
         */
        boolean checkTheFixedForm(OutputItemSettingCode code);

        /**
         * [R-2]  自由をチェックする
         * 任意期間集計表の出力項目Repository. exist(コード、ログイン会社ID、ログイン社員ID)
         */
        boolean freeDomCheck(OutputItemSettingCode code, String employeeId);
    }
}
