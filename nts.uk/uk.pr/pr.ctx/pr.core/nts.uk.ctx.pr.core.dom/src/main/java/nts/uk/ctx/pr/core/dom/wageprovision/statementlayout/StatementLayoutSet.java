package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

/**
* 明細書レイアウト設定
*/
@Getter
public class StatementLayoutSet extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String histId;
    
    /**
    * レイアウトパターン
    */
    private StatementLayoutPattern layoutPattern;
    
    /**
    * カテゴリ別設定
    */
    private List<SettingByCtg> listSettingByCtg;
    
    public StatementLayoutSet(String histId, int layoutPattern, List<SettingByCtg> listSettingByCtg) {
        this.histId = histId;
        this.layoutPattern = EnumAdaptor.valueOf(layoutPattern, StatementLayoutPattern.class);
        this.listSettingByCtg = listSettingByCtg;
    }
    
}
