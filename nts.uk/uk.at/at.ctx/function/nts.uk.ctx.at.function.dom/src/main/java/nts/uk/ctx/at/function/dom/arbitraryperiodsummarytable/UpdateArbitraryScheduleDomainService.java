package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * DomainService: 任意期間集計表の設定を更新する
 */

@Stateless
public class UpdateArbitraryScheduleDomainService {
    public static AtomTask updateSchedule(
            Require require,
            String settingId,
            OutputItemSettingCode code,
            OutputItemSettingName name,
            SettingClassificationCommon classificationCommon,
            List<AttendanceItemToPrint> itemToPrintList
    ) {

        val outputSettingOld = require.getOutputSetting(settingId);
        if (!outputSettingOld.isPresent()) {
            throw new BusinessException("Msg_1914");
        }
        val employeeId = AppContexts.user().employeeId();
        OutputSettingOfArbitrary outputSettingOfArbitrary = new OutputSettingOfArbitrary(
                settingId,
                code,
                name,
                classificationCommon == SettingClassificationCommon.FREE_SETTING ? Optional.of(employeeId) : Optional.empty(),
                classificationCommon,
                itemToPrintList

        );
        return AtomTask.of(() -> {
            require.updateSchedule(settingId, outputSettingOfArbitrary);
        });
    }

    public interface Require  {
        /**
         * [1] 出力設定の詳細を取得する
         * 任意期間集計表の出力設定Repository.出力設定の詳細を取得する(ログイン会社ID,設定ID)
         */
        Optional<OutputSettingOfArbitrary> getOutputSetting(String settingId);

        /**
         * [2] 任意期間集計表の出力設定を更新する
         * 任意期間集計表の出力設定Repository.更新する(会社ID,設定ID,任意期間集計表の出力設定)
         */
        void updateSchedule(String settingId, OutputSettingOfArbitrary outputSetting);

    }
}
