package nts.uk.ctx.at.function.app.find.supportworklist.outputsetting;

import lombok.Value;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSetting;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class SupportWorkOutputSettingDto {
    // コード
    private String code;

    // 名称
    private String name;

    // レイアウト詳細設定.社員抽出条件
    private int extractCondition;

    // レイアウト詳細設定.明細表示設定.明細を表示する
    private int displayDetailAtr;

    // レイアウト詳細設定.職場合計の表示設定.1日の合計を表示する
    private int displaySumOneDayAtr;

    // レイアウト詳細設定.職場合計の表示設定.職場・応援計を表示する
    private int displaySumSupportWorkplaceAtr;

    // レイアウト詳細設定.職場合計の表示設定.応援内訳を表示する
    private int displaySumSupportDetailAtr;

    // レイアウト詳細設定.職場合計の表示設定.職場計を表示する
    private int displaySumWorkplaceAtr;

    // レイアウト詳細設定.総合計の表示設定.総合計を表示する
    private int displayTotalSumAtr;

    // レイアウト詳細設定.総合計の表示設定.職場・応援総合計を表示する
    private int displayTotalSumSupportWorkplaceAtr;

    // 改ページする
    private int breakPageAtr;

    private List<Integer> itemIds;

    public SupportWorkOutputSettingDto(SupportWorkListOutputSetting domain) {
        code = domain.getCode().v();
        name = domain.getName().v();
        extractCondition = domain.getDetailLayoutSetting().getExtractCondition().value;
        displayDetailAtr = domain.getDetailLayoutSetting().getDetailDisplaySetting().getDisplayDetail().value;
        itemIds = domain.getDetailLayoutSetting().getDetailDisplaySetting().getOutputItems().stream().map(i -> i.getAttendanceItemId()).collect(Collectors.toList());
        displaySumOneDayAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayOneDayTotal().value;
        displaySumSupportWorkplaceAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayWorkplaceSupportMeter().value;
        displaySumSupportDetailAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplaySupportDetail().value;
        displaySumWorkplaceAtr = domain.getDetailLayoutSetting().getWorkplaceTotalDisplaySetting().getDisplayWorkplaceTotal().value;
        displayTotalSumAtr = domain.getDetailLayoutSetting().getGrandTotalDisplaySetting().getDisplayGrandTotal().value;
        displayTotalSumSupportWorkplaceAtr = domain.getDetailLayoutSetting().getGrandTotalDisplaySetting().getDisplayWorkplaceSupportMeter().value;
        breakPageAtr = domain.getDetailLayoutSetting().getPageBreak().value;
    }
}
