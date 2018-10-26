package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 明細書レイアウト設定
*/
@Getter
public class SpecificationLayoutSet extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;
    
    /**
    * レイアウトパターン
    */
    private SpecificationLayoutPattern layoutPattern;
    
    /**
    * カテゴリ別設定
    */
    private List<SettingByCtg> listSettingByCtg;
    
    public SpecificationLayoutSet(String histId, int layoutPattern, List<SettingByCtg> listSettingByCtg) {
        this.histId = histId;
        this.layoutPattern = EnumAdaptor.valueOf(layoutPattern, SpecificationLayoutPattern.class);
        this.listSettingByCtg = listSettingByCtg;
    }
    
}
