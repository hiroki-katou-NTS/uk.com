package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 明細書紐付け設定（マスタ）
*/
@Getter
public class StateLinkSettingMaster extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタコード
    */
    private MasterCode masterCode;
    
    /**
    * 給与明細書
    */
    private Optional<StatementCode> salaryCode;
    
    /**
    * 賞与明細書
    */
    private Optional<StatementCode> bonusCode;
    
    public StateLinkSettingMaster(String hisId, MasterCode masterCode, StatementCode salary, StatementCode bonus) {
        this.salaryCode = Optional.ofNullable(salary);
        this.bonusCode = Optional.ofNullable(bonus);
        this.historyID = hisId;
        this.masterCode = masterCode;
    }
    
}
