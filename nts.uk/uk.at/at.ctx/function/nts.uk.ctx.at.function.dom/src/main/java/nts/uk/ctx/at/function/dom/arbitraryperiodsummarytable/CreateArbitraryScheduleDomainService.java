package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 任意期間集計表の設定を作成する
 *
 * @author : chinh.hm
 */
@Stateless
public class CreateArbitraryScheduleDomainService {

    public static AtomTask createSchedule(Require require,
                                          OutputItemSettingCode code,
                                          OutputItemSettingName name,
                                          SettingClassificationCommon settingCategory,
                                          List<AttendanceItemToPrint> outputItemList) {
        val uid = IdentifierUtil.randomUniqueId();
        val employeeId = AppContexts.user().employeeId();
        boolean checkResult = false;
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            checkResult = require.standardCheck(code);
        } else if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            checkResult = require.freeCheck(code, employeeId);
        }

        if (checkResult) {
            throw new BusinessException("Msg_1893");
        }
        OutputSettingOfArbitrary finalOutputSettings = new OutputSettingOfArbitrary(
                uid,
                code,
                name,
                settingCategory == SettingClassificationCommon.FREE_SETTING ? Optional.of(employeeId) : Optional.empty(),
                settingCategory,
                outputItemList
        );
        return AtomTask.of(() -> {
            require.createSchedule(finalOutputSettings);
        });
    }

    public interface Require {
        /**
         * [1] 指定のコートが既に保存されているか
         * 定型選択の場合	任意期間集計表の出力設定Repository.exist(コード,ログイン会社ID)
         */
        boolean standardCheck(OutputItemSettingCode code);

        /**
         * [1] 指定のコートが既に保存されているか
         * 自由設定の場合	任意期間集計表の出力設定Repository.exist(コード,ログイン会社ID,ログイン社員ID)
         */
        boolean freeCheck(OutputItemSettingCode code, String employeeId);

        /**
         * [2] 任意期間集計表の出力設定を追加する
         * 任意期間集計表の出力設定Repository.新規作成する(会社ID,任意期間集計表の出力設定)
         */
        void createSchedule(OutputSettingOfArbitrary outputSetting);
    }

}
