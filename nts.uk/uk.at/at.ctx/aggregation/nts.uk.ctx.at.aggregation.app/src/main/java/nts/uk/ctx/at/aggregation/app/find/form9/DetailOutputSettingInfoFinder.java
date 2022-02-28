package nts.uk.ctx.at.aggregation.app.find.form9;

import lombok.val;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 詳細出力設定情報を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.App.詳細出力設定情報を取得する.詳細出力設定情報を取得する
 */
@Stateless
public class DetailOutputSettingInfoFinder {
    @Inject
    private Form9DetailOutputSettingRepository detailOutputRepo;

    public Form9DetailOutputSettingDto get() {
        val detail = this.detailOutputRepo.get(AppContexts.user().companyId());
        if (detail == null) return null;

        return new Form9DetailOutputSettingDto(
                detail.getTimeRoundingSetting().getRoundingUnit().value,
                detail.getTimeRoundingSetting().getRoundingMethod().value,
                detail.isAllZeroIsAttributeBlank()
        );
    }
}
