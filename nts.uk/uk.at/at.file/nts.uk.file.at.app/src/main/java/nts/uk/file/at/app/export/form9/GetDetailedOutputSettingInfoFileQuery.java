package nts.uk.file.at.app.export.form9;

import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSetting;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 詳細出力設定情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.App.詳細出力設定情報を取得する.詳細出力設定情報を取得する
 */
@Stateless
public class GetDetailedOutputSettingInfoFileQuery {
    @Inject
    private Form9DetailOutputSettingRepository detailOutputSettingRepo;

    public Form9DetailOutputSetting get(String cid) {
        return this.detailOutputSettingRepo.get(cid);
    }
}
