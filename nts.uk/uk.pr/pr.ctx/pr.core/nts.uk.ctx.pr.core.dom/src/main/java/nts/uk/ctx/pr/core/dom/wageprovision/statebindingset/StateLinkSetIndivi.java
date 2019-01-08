package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 明細書紐付け設定（個人）
*/
@Getter
public class StateLinkSetIndivi extends AggregateRoot {
    
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

    public StateLinkSetIndivi(String hisId, String salary, String bonus) {
        this.historyID = hisId;
        this.salaryCode = salary == null ? Optional.empty() : Optional.ofNullable(new StatementCode(salary));
        this.bonusCode = bonus == null ? Optional.empty() : Optional.ofNullable(new StatementCode(bonus));
    }
}
