package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalculationClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.RandomStringUtils;

import javax.ejb.Stateless;
import java.util.List;

/**
 * DomainService : 勤務状況の設定を作成する
 *
 * @author chinh.hm
 */
@Stateless
public class CreateWorkStatusSettingDomainService {
    public static AtomTask updateSetting(Require require, OutputItemSettingCode code,
                                         OutputItemSettingName name, SettingClassificationCommon settingCategory,
                                         List<OutputItem> outputItemList

    ) {
        Boolean checkDuplicate = false;
        val cid = AppContexts.user().companyId();
        val empId = AppContexts.user().employeeId();
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {

            checkDuplicate = new WorkStatusOutputSettings().checkDuplicateStandardSelections(require, code.v(), cid);
        }
        if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            checkDuplicate = new WorkStatusOutputSettings().checkDuplicateFreeSettings(require, code.v(), cid, empId);
        }

        if (checkDuplicate) {
            throw new BusinessException("Msg_1753");
        }
        WorkStatusOutputSettings outputSettings;
        val settingId = IdentifierUtil.randomUniqueId();
        if (settingCategory == SettingClassificationCommon.STANDARD_SELECTION) {
            outputSettings = new WorkStatusOutputSettings(
                    settingId,
                    code,
                    name,
                    null,
                    settingCategory,
                    null
            );

        }
        if (settingCategory == SettingClassificationCommon.FREE_SETTING) {
            outputSettings = new WorkStatusOutputSettings(
                    settingId,
                    code,
                    name,
                    empId,
                    settingCategory,
                    null
            );
        }

        return AtomTask.of(() -> {

        });
    }

    public interface Require extends WorkStatusOutputSettings.Require {
        //  [1]	出力設定の詳細を取得する
        WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId);

        //  [4] 設定の詳細を複製する
        void duplicateConfigurationDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId,
                                           OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName);
    }
}
