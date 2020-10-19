package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.CommonAttributesOfForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.IndependentCalculationClassification;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.OperatorsCommonToForms;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

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
                                         OutputItemSettingName name,WorkStatusOutputSettings settingCategory,
                                         List<Integer> listRank,List<Boolean> listPrintTaget,
                                         List<FormOutputItemName> outputItemNameList,List<IndependentCalculationClassification>independentCalculationCatelogyList,
                                         List<CommonAttributesOfForms> attributesOfFormsList,List<OperatorsCommonToForms>commonToFormsList,List<String> listAttendanceItemID

    ) {
        if(settingCategory.getDesignateFreeClassing() == SettingClassificationCommon.FREE_SETTING){
            val cid = AppContexts.user().companyId();
            val checkDuplicateStandard = settingCategory.checkDuplicateStandardSelections(require,code.v(),cid);
        }
//        // 1.定型選択の重複をチェックする(出力項目設定コード, 会社ID)
//        val isCheckDuplicateFixedSelection = require.exist(settingCode, cid);
//        // 3.2自由設定の重複をチェックする(出力項目設定コード, 会社ID, 社員ID)
//        val isCheckDuplicateFreeSetting = require.exist(settingCode, cid, settingCategory.getEmployeeId());
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
