package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
* 明細書紐付け設定（マスタ基準日）
*/
@Getter
public class StateLinkSettingDate extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタ基準日
    */
    private GeneralDate date;
    
    public StateLinkSettingDate(String hisId, GeneralDate date) {
        this.historyID = hisId;
        this.date = date;
    }
    
}
