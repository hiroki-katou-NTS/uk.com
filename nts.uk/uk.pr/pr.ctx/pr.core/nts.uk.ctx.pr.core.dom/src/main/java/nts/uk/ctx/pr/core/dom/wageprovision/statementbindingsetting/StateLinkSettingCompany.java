package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 明細書紐付け設定（会社）
*/
@Getter
public class StateLinkSettingCompany extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 給与明細書
    */
    private Optional<StatementCode> salaryCode;
    
    /**
    * 賞与明細書
    */
    private Optional<StatementCode> bonusCode;
    
    public StateLinkSettingCompany(String hisId, StatementCode salary, StatementCode bonus) {
        this.historyID = hisId;
        this.salaryCode = Optional.ofNullable(salary);
        this.bonusCode = Optional.ofNullable(bonus);
    }
    
}
