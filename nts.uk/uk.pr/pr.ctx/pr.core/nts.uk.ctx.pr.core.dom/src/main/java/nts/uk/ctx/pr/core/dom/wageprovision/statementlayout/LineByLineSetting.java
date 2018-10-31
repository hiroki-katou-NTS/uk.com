package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.enums.EnumAdaptor;

/**
* 行別設定
*/
@AllArgsConstructor
@Getter
public class LineByLineSetting extends DomainObject
{
    
    /**
    * 印字設定
    */
    private StatementPrintAtr printSet;
    
    /**
    * 行番号
    */
    private int lineNumber;
    
    /**
    * 項目別設定
    */
    private List<SettingByItem> listSetByItem;
    
    public LineByLineSetting(int printSet, int lineNumber,List<SettingByItem> listSetByItem ) {
        this.printSet = EnumAdaptor.valueOf(printSet, StatementPrintAtr.class);
        this.lineNumber = lineNumber;
        this.listSetByItem = listSetByItem;
    }
    
}
