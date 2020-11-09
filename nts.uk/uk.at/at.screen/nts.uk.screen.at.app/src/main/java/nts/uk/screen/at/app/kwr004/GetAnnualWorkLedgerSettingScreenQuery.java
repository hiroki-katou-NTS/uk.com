package nts.uk.screen.at.app.kwr004;

import lombok.val;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSetting;
import nts.uk.ctx.at.function.dom.outputitemsofannualworkledger.AnnualWorkLedgerOutputSettingRepository;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 年間勤務台帳の出力設定リストを取得する
 */

@Stateless
public class GetAnnualWorkLedgerSettingScreenQuery {
    @Inject
    AnnualWorkLedgerOutputSettingRepository settingsRepository;

    public List<AnnualWorkLedgerSettingDto> getSettings(GetAnnualWorkLedgerSettingRequestParams requestPrams) {

        val cid = AppContexts.user().companyId();
        val empId = AppContexts.user().employeeId();
        List<AnnualWorkLedgerOutputSetting> listSetting = new ArrayList<AnnualWorkLedgerOutputSetting>();

        // 設定区分 == 定型選択 => Call 定型選択の出力設定一覧を取得する(会社ID, 帳票共通の設定区分)
        if (requestPrams.getSettingClassification() == SettingClassificationCommon.STANDARD_SELECTION.value) {
            listSetting = settingsRepository.getAListOfOutputSettings(cid, SettingClassificationCommon.STANDARD_SELECTION);
        }

        // 設定区分 == 自由設定 => Call 自由設定の出力項設定一覧を取得する(会社ID, 社員コード, 帳票共通の設定区分)
        else if (requestPrams.getSettingClassification() == SettingClassificationCommon.FREE_SETTING.value) {
            listSetting = settingsRepository.getTheFreeSettingOutputItemList(cid, SettingClassificationCommon.FREE_SETTING, empId);
        }

        return listSetting.stream().map(AnnualWorkLedgerSettingDto::fromDomain).collect(Collectors.toList());
    }
}
