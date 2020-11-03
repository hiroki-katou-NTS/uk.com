package nts.uk.ctx.at.function.app.query.outputworkstatustable;


import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Query:勤務状況表の出力設定リストを取得する
 * @author chinh.hm
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetOutputItemSettingQuery {
    @Inject
    WorkStatusOutputSettingsRepository settingsRepository;
    public List<WorkStatusOutputDto>getListWorkStatus (SettingClassificationCommon settingClassification){
        val cid = AppContexts.user().companyId();
        val empId = AppContexts.user().employeeId();
        val listWorkStatus = new ArrayList<WorkStatusOutputSettings>();
        // 自由設定の出力項設定一覧を取得する(会社ID, 社員コード, 勤務状況の設定区分)
        //設定区分 == 定型選択
        if(settingClassification.equals(SettingClassificationCommon.STANDARD_SELECTION)){
            listWorkStatus.addAll(settingsRepository.getListWorkStatusOutputSettings(cid,settingClassification));
        }
        // 自由設定の出力項設定一覧を取得する(会社ID, 社員コード, 勤務状況の設定区分)
        // 設定区分 == 自由設定
        else  if(settingClassification.equals(SettingClassificationCommon.FREE_SETTING)){
            listWorkStatus.addAll(settingsRepository.getListOfFreelySetOutputItems(cid,settingClassification,empId));
        }
        if(listWorkStatus.isEmpty()){
            return Collections.emptyList();
        }
        return listWorkStatus.stream().map(e->new WorkStatusOutputDto(
                e.getSettingId(),
                e.getSettingDisplayCode().v(),
                e.getSettingName().v()
        )).collect(Collectors.toList());
    }
}
