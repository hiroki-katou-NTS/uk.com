package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

import java.util.List;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;

/**
* カテゴリ別設定
*/
@AllArgsConstructor
@Getter
public class SettingByCtg extends DomainObject
{
    
    /**
    * カテゴリ区分
    */
    private CategoryAtr ctgAtr;

    /**
    * 行別設定
    */
    private List<LineByLineSetting> listLineByLineSet;
    
    public SettingByCtg(int ctgAtr, List<LineByLineSetting> listLineByLineSet) {
        this.ctgAtr = EnumAdaptor.valueOf(ctgAtr, CategoryAtr.class);
        this.listLineByLineSet = listLineByLineSet;
    }
    
}
