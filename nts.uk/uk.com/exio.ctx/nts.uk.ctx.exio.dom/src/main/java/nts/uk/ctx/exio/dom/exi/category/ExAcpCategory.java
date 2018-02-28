package nts.uk.ctx.exio.dom.exi.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 外部受入カテゴリ
*/
@AllArgsConstructor
@Getter
@Setter
public class ExAcpCategory extends AggregateRoot
{
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * カテゴリ名
    */
    private String categoryName;
    
    public static ExAcpCategory createFromJavaType(Long version, String categoryId, String categoryName)
    {
        ExAcpCategory  exAcpCategory =  new ExAcpCategory(categoryId,  categoryName);
        exAcpCategory.setVersion(version);
        return exAcpCategory;
    }
    
}
