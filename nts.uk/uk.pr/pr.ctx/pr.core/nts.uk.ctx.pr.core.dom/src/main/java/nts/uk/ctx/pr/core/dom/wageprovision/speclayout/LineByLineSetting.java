package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
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
    private SpecificationPrintAtr  printSet;
    
    /**
    * 行番号
    */
    private int lineNumber;
    
    /**
    * 項目別設定
    */
    private List<SettingByItem> listSetByItem;
    
    public LineByLineSetting(int printSet, int lineNumber,List<SettingByItem> listSetByItem ) {
        this.printSet = EnumAdaptor.valueOf(printSet, SpecificationPrintAtr .class);
        this.lineNumber = lineNumber;
        this.listSetByItem = listSetByItem;
    }
    
}
