package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 賃金テーブル内容
*/
@Getter
public class WageTableContent extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 支払金額
    */
    private List<ElementsCombinationPaymentAmount> payment;
    
    /**
    * 資格グループ設定
    */
    private List<QualificationGroupSettingContent> qualificationGroupSetting;

}
