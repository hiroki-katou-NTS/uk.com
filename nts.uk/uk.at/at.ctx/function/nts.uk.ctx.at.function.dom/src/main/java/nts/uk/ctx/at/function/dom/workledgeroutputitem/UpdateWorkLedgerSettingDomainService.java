package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.commonform.AttendanceItemToPrint;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;

/**
 * 勤務台帳の設定を更新する
 *
 * @author khai.dh
 */
public class UpdateWorkLedgerSettingDomainService {

    /**
     * 設定を更新する
     *
     * @param require         Require
     * @param id              GUID
     * @param name            名称: 出力項目設定名称
     * @param settingCategory 設定区分: 帳票共通の設定区分
     * @param outputItemList  List<AttendanceItemToPrint>
     * @return AtomTask
     */
    public static AtomTask updateSetting(
            Require require,
            String id,
            OutputItemSettingName name,
            SettingClassificationCommon settingCategory,
            List<AttendanceItemToPrint> outputItemList) {

        // 出力設定の詳細を取得する(会社ID, GUID)
        Optional<WorkLedgerOutputItem> outputSetting = require.getOutputSettingDetail(id);
        if (!outputSetting.isPresent()) {
            throw new BusinessException("Msg_1928");
        }
        val employeeId = AppContexts.user().employeeId();
        val code = outputSetting.get().getCode();
        val workLedgerOutputItem = new WorkLedgerOutputItem(
                id,
                code,
                outputItemList,
                name,
                settingCategory,
                settingCategory == SettingClassificationCommon.FREE_SETTING ? employeeId : null
        );
        return AtomTask.of(() -> {
            require.updateWorkLedgerOutputItem(id, workLedgerOutputItem);
        });

    }

    public interface Require {

        /**
         * Call 勤務台帳の出力項目Repository#出力設定の詳細を取得する
         */
        Optional<WorkLedgerOutputItem> getOutputSettingDetail(String id);

        /**
         * Call 勤務台帳の出力項目Repository#出力設定の詳細を取得する
         */
        void updateWorkLedgerOutputItem(String id, WorkLedgerOutputItem outputSetting);
    }
}
