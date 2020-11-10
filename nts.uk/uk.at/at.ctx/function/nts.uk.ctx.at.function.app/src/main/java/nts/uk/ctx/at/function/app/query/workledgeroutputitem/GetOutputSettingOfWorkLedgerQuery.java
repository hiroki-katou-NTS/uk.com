package nts.uk.ctx.at.function.app.query.workledgeroutputitem;


import lombok.val;
import nts.uk.ctx.at.function.app.query.outputworkstatustable.WorkStatusOutputDto;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItem;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerOutputItemRepo;
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
 * Query: 勤務台帳の出力設定リストを取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetOutputSettingOfWorkLedgerQuery {
    @Inject
    WorkLedgerOutputItemRepo workLedgerOutputItemRepo;
    public List<WorkStatusOutputDto> getListWorkStatus (SettingClassificationCommon settingClassification){
        val cid = AppContexts.user().companyId();
        val empId = AppContexts.user().employeeId();
        val listWorkStatus = new ArrayList<WorkLedgerOutputItem>();
        // 自由設定の出力項設定一覧を取得する(会社ID, 社員コード, 勤務状況の設定区分)
        //設定区分 == 定型選択
        if(settingClassification.equals(SettingClassificationCommon.STANDARD_SELECTION)){
            listWorkStatus.addAll(workLedgerOutputItemRepo.getlistForStandard(cid,settingClassification));
        }
        // 自由設定の出力項設定一覧を取得する(会社ID, 社員コード, 勤務状況の設定区分)
        // 設定区分 == 自由設定
        else  if(settingClassification.equals(SettingClassificationCommon.FREE_SETTING)){
            listWorkStatus.addAll(workLedgerOutputItemRepo.getListOfFreely(cid,settingClassification,empId));
        }
        if(listWorkStatus.isEmpty()){
            return Collections.emptyList();
        }
        return listWorkStatus.stream().map(e->new WorkStatusOutputDto(
                e.getId(),
                e.getCode().v(),
                e.getName().v()
        )).collect(Collectors.toList());
    }
}
