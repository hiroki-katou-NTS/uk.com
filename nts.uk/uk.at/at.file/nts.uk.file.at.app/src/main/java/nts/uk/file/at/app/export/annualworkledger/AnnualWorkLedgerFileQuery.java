package nts.uk.file.at.app.export.annualworkledger;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDateDto;

import java.util.List;

@Data
public class AnnualWorkLedgerFileQuery {
    // 期間入力 - 開始月
    private GeneralDate startMonth;

    // 期間入力 - 終了月
    private GeneralDate endMonth;

    // 社員リスト
    private List<String> lstEmpIds;

    // 定型自由区分
    private int settingClassification;

    // 設定ID
    private String settingId;

    // ゼロ表示区分選択肢
    private boolean isZeroDisplay;

    // 検索結果．締めID
    private int closureId;

    // 締め日
    private ClosureDateDto closureDate;
}
