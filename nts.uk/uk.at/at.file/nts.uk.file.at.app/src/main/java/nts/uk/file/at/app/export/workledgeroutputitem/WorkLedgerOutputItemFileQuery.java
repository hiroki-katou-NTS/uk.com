package nts.uk.file.at.app.export.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDateDto;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class WorkLedgerOutputItemFileQuery {
    // A2_3 期間入力 - 開始月
    private int startMonth;

    // A2_3 期間入力 - 終了月
    private int endMonth;
    //「３」List<抽出社員>
    private List<String> lstEmpIds;
    // 「４」定型自由区分
    private int standardFreeClassification;

    //「５」項目選択の設定ID
    private String settingId;

    //「６」ゼロ表示区分
    private boolean isZeroDisplay;

    //「７」改ページ指定選択肢
    private boolean pageBreak;

    // 締め日
    private int closureId;

}
