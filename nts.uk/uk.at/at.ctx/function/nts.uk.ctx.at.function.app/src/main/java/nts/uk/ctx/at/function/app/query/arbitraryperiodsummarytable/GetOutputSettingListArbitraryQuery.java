package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;

import lombok.val;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitrary;
import nts.uk.ctx.at.function.dom.arbitraryperiodsummarytable.OutputSettingOfArbitraryRepo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Query: 任意期間集計表の出力設定リストを取得する
 */
@Stateless
public class GetOutputSettingListArbitraryQuery {

    @Inject
    private OutputSettingOfArbitraryRepo ofArbitraryRepo;

    public List<OutputSettingArbitraryDto> getListOutputSetting(SettingClassificationCommon settingClassification) {
        val cid = AppContexts.user().companyId();
        val empId = AppContexts.user().employeeId();
        val listOutputSetting = new ArrayList<OutputSettingOfArbitrary>();
        // 定型選択の出力設定一覧を取得する(会社ID, 帳票共通の設定区分)
        // 設定区分 == 定型選択
        if (settingClassification.equals(SettingClassificationCommon.STANDARD_SELECTION)) {
            listOutputSetting.addAll(ofArbitraryRepo.getlistForStandard(cid, settingClassification));
        }
        // 自由設定の出力設定一覧を取得する(会社ID, 帳票共通の設定区分, 社員ID)
        // 設定区分 == 自由設定
        else if (settingClassification.equals(SettingClassificationCommon.FREE_SETTING)) {
            listOutputSetting.addAll(ofArbitraryRepo.getListOfFreely(cid, settingClassification, empId));
        }
        if (listOutputSetting.isEmpty()) {
            return Collections.emptyList();
        }
        return listOutputSetting.stream().map(e -> new OutputSettingArbitraryDto(
                e.getSettingId(),
                e.getCode().v(),
                e.getName().v()
        )).collect(Collectors.toList());
    }
}
