package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;

/**
 * 明細表示設定
 */
@AllArgsConstructor
@Getter
public class DetailDisplaySetting {
    /**
     * 明細を表示する
     */
    private NotUseAtr displayDetail;

    /**
     * 表示項目一覧
     */
    private List<OutputItem> outputItems;
}
