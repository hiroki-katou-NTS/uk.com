package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import java.util.Optional;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 明細書紐付け設定（マスタ）
*/
@Getter
public class StateLinkSetMaster extends AggregateRoot {
    
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

    public StateLinkSetMaster(String hisId, String masterCode, String salary, String bonus) {
        this.salaryCode = salary == null ? Optional.empty() : Optional.of(new StatementCode(salary));
        this.bonusCode = bonus == null ? Optional.empty() : Optional.of(new StatementCode(bonus));
        this.historyID = hisId;
        this.masterCode = new MasterCode(masterCode);
    }
    
}
