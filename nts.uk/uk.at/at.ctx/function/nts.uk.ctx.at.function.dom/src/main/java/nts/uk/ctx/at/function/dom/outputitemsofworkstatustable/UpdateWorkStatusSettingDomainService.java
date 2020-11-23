package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DomainService: 勤務状況の設定を更新する
 *
 * @author chinh.hm
 */
@Stateless
public class UpdateWorkStatusSettingDomainService {
    public static AtomTask updateSetting(Require require, String settingId, OutputItemSettingCode code,
                                         OutputItemSettingName name, SettingClassificationCommon settingCategory,
                                         List<OutputItem> outputItemList
    ) {
        // 1. get: ⓪ -勤務状況の出力設定
        val oldItem = require.getWorkStatusOutputSettings(settingId);
        // 2. ⓪．isEmpty
        if (oldItem == null) {
            throw new BusinessException("Msg_1903");
        }
        val empId = AppContexts.user().employeeId();
        // 3.1設定区分 == 定型選択: ① create():勤務状況の出力設定
        WorkStatusOutputSettings finalOutputSettings = new WorkStatusOutputSettings(
                settingId,
                code,
                name,
                settingCategory == SettingClassificationCommon.STANDARD_SELECTION ? null : empId,
                settingCategory,
                outputItemList
        );
        return AtomTask.of(() -> {
            // 7.1 設定区分 == 指定選択: 定型選択を更新する(会社ID, int, 勤務状況の出力設定, 勤務状況の出力項目, 勤務状況の出力項目詳細)
            require.update(finalOutputSettings);

        });
    }
    public interface Require extends WorkStatusOutputSettings.Require {

        // [1]	出力設定の詳細を取得する
        WorkStatusOutputSettings getWorkStatusOutputSettings(String settingId);

        // [2]	定型選択を更新する
        void update(WorkStatusOutputSettings outputSettings);

    }
}
