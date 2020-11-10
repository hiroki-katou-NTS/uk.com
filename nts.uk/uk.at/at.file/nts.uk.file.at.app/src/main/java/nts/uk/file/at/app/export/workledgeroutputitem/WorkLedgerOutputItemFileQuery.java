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
    //「２」対象年月
    private GeneralDate targetDate;
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
    private ClosureDateDto closureDate;

    private String title;
}
