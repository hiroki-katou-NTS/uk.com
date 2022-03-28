package nts.uk.ctx.at.function.app.command.supportworklist.outputsetting;

import lombok.Data;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SupportWorkOutputSettingCommand {
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

    private int mode;

    public SupportWorkListOutputSetting toDomain() {
        List<OutputItem> outputItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            outputItems.add(new OutputItem(itemIds.get(i), i));
        }
        return new SupportWorkListOutputSetting(
                new SupportWorkOutputCode(code),
                new SupportWorkOutputName(name),
                new DetailLayoutSetting(
                        EnumAdaptor.valueOf(extractCondition, EmployeeExtractCondition.class),
                        new DetailDisplaySetting(
                                EnumAdaptor.valueOf(displayDetailAtr, NotUseAtr.class),
                                outputItems
                        ),
                        new GrandTotalDisplaySetting(
                                EnumAdaptor.valueOf(displayTotalSumAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(displayTotalSumSupportWorkplaceAtr, NotUseAtr.class)
                        ),
                        new WorkplaceTotalDisplaySetting(
                                EnumAdaptor.valueOf(displaySumOneDayAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(displaySumSupportDetailAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(displaySumSupportWorkplaceAtr, NotUseAtr.class),
                                EnumAdaptor.valueOf(displaySumWorkplaceAtr, NotUseAtr.class)
                        ),
                        EnumAdaptor.valueOf(breakPageAtr, NotUseAtr.class)
                )
        );
    }
}
