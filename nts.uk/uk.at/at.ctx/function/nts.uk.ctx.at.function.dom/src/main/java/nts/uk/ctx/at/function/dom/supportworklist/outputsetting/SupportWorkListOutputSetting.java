package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.SupportWorkOutputData;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.応援勤務一覧表.出力設定
 * 応援勤務一覧表の出力設定
 */
@AllArgsConstructor
@Getter
public class SupportWorkListOutputSetting extends AggregateRoot {
    /**
     * コード
     */
    private SupportWorkOutputCode code;

    /**
     * 名称
     */
    private SupportWorkOutputName name;

    /**
     * レイアウト詳細設定
     */
    private DetailLayoutSetting detailLayoutSetting;

    /**
     * [1] 職場別の応援勤務出力データを取得する
     * @param require
     * @param companyId
     * @param period
     * @param baseDate
     * @param workplaceIds
     * @return
     */
    public SupportWorkOutputData getOutputDataByWorkplace(SupportWorkOutputDataRequire require, String companyId, DatePeriod period, GeneralDate baseDate, List<String> workplaceIds) {
        return detailLayoutSetting.getOutputDataByWorkplace(require, companyId, period, baseDate, workplaceIds);
    }

    /**
     * [2] 場所別の応援勤務出力データを取得する
     * @param require
     * @param companyId
     * @param period
     * @param workLocationCodes
     * @return
     */
    public SupportWorkOutputData getOutputDataByWorkLocation(SupportWorkOutputDataRequire require, String companyId, DatePeriod period, List<String> workLocationCodes) {
        return detailLayoutSetting.getOutputDataByWorkLocation(require, companyId, period, workLocationCodes);
    }
}
