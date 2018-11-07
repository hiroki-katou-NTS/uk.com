package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* かんたん計算式設定
*/
@Getter
public class BasicFormulaSetting extends AggregateRoot {
    
    /**
    * 使用マスタ
    */
    private Optional<MasterUse> masterUse;
    
    /**
    * マスタ分岐利用
    */
    private MasterBranchUse masterBranchUse;
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    public BasicFormulaSetting(Integer masterUse, int masterBranchUse, String historyID) {
        this.masterUse = masterUse == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(masterUse, MasterUse.class));
        this.masterBranchUse = EnumAdaptor.valueOf(masterBranchUse, MasterBranchUse.class);
        this.historyID = historyID;
    }
}
