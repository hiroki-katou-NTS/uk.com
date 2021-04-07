package nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * DomainService: 任意期間集計表の設定を複製する
 *
 * @author : chinh.hm
 */

@Stateless
public class DuplicateArbitraryScheduleDomainService {
    public static AtomTask duplicateSchedule(Require require,
                                             SettingClassificationCommon settingCategory,
                                             String dupSrcId,
                                             OutputItemSettingCode dupCode,
                                             OutputItemSettingName dupName) {
        val optOutputSetting = require.getOutputSetting(dupSrcId);
        if (!optOutputSetting.isPresent()) {
            throw new BusinessException("Msg_1914");
        }
        val employeeId = AppContexts.user().employeeId();
        boolean isDuplicated = false;
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            isDuplicated = require.standardCheck(dupCode);
        } else if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            isDuplicated = require.freeCheck(dupCode, employeeId);
        }

        if (isDuplicated) {
            throw new BusinessException("Msg_1893");
        }
        String uid = IdentifierUtil.randomUniqueId();

        return AtomTask.of(() -> {
            require.duplicateArbitrarySchedule(dupSrcId, uid, dupCode, dupName);
        });
    }

    public interface Require  {
        /**
         * [1] 出力設定の詳細を取得する
         * 任意期間集計表の出力項目Repository.出力設定の詳細を取得する(会社ID, 設定ID)
         */
        Optional<OutputSettingOfArbitrary> getOutputSetting(String settingId);

        /**
         * [2] 指定のコードが既に定型選択に保存されているか
         * 任意期間集計表の出力項目Repository.exist(設定コード, 会社ID)
         */
        boolean standardCheck(OutputItemSettingCode code);

        /**
         * [3] 指定のコードが既に自由設定に保存されているか
         * 任意期間集計表の出力項目Repository.exist(設定コード, 会社ID, 社員ID)
         */
        boolean freeCheck(OutputItemSettingCode code, String employeeId);

        /**
         * [4] 任意期間集計表の出力設定を複製する
         * 任意期間集計表の出力項目Repository.設定の詳細を複製する(会社ID, 複製元の設定ID, 複製先の設定ID, 複製先のコード, 複製先の名称)
         */
        void duplicateArbitrarySchedule(String dupSrcId,
                                        String dupDestId,
                                        OutputItemSettingCode dupCode,
                                        OutputItemSettingName dupName);

    }
}