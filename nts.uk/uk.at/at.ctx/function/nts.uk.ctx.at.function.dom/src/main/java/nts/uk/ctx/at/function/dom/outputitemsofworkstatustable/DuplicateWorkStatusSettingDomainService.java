package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;

/**
 * DomainService: 勤務状況の設定を複製する
 *
 * @author chinh.hm
 */
@Stateless
public class DuplicateWorkStatusSettingDomainService {
    public static AtomTask duplicate(Require require, SettingClassificationCommon settingCategory, String settingId,
                                     OutputItemSettingCode settingCode, OutputItemSettingName settingName) {
        val cid = AppContexts.user().companyId();
        val employeeId = AppContexts.user().employeeId();
        // 1.出力設定の詳細を取得する(会社ID, GUID)
        val workStatusSetting = require.getWorkStatusOutputSettings(cid,settingId);
        if (workStatusSetting == null) {
            // 2. [1.isEmpty]
            throw new BusinessException("Msg_1903");
        }
            // 1
        Boolean isCheck;
        if(settingCategory == SettingClassificationCommon.STANDARD_SELECTION){
            // 3.1定型選択の重複をチェックする(出力項目設定コード, 会社ID)
            isCheck = require.checkTheStandard(settingCode.v(), cid);
        } else{
            // 3.2自由設定の重複をチェックする(出力項目設定コード, 会社ID, 社員ID)
            isCheck = require.checkFreedom(settingCode.v(), cid, employeeId);
        }
        // 4. [1] true
        if (isCheck ) {
            throw new BusinessException("Msg_1753");
        }
        // 5.設定IDを生成する
        val id = IdentifierUtil.randomUniqueId();
        // 6.勤務状況設定の複製
        return AtomTask.of(() ->
                // 7.設定を複製する(会社ID, GUID, GUID, 勤務状況の設定表示コード, 勤務状況の設定名称)
                require.duplicateConfigurationDetails(cid, settingId, id, settingCode, settingName)
        );
    }

    public interface Require extends WorkStatusOutputSettings.Require {
        //  [1]	出力設定の詳細を取得する
        WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId);

        //  [4] 設定の詳細を複製する
        void duplicateConfigurationDetails(String cid, String replicationSourceSettingId,
                                           String replicationDestinationSettingId,
                                           OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName);
    }
}
