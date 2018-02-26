package nts.uk.ctx.exio.app.find.exi.category;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.category.ExAcpCategory;

/**
* 外部受入カテゴリ
*/
@AllArgsConstructor
@Value
public class ExAcpCategoryDto
{
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * カテゴリ名
    */
    private String categoryName;
    
    
    private Long version;
    public static ExAcpCategoryDto fromDomain(ExAcpCategory domain)
    {
        return new ExAcpCategoryDto(domain.getCategoryId(), domain.getCategoryName(), domain.getVersion());
    }
    
}
