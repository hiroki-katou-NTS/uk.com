package nts.uk.ctx.sys.assist.dom.categoryfieldmtfordelete;

import java.util.List;
import java.util.Optional;

/**
* カテゴリ項目マスタ
*/
public interface CategoryFieldMtForDelRepository
{

    List<CategoryFieldMtForDelete> getAllCategoryFieldMt();

    Optional<CategoryFieldMtForDelete> getCategoryFieldMtById();

    void add(CategoryFieldMtForDelete domain);

    void update(CategoryFieldMtForDelete domain);

    void remove();

    List<CategoryFieldMtForDelete> getCategoryFieldMtByListId(List<String> categoryIds);   
    
    List<CategoryFieldMtForDelete> findByCategoryIdAndSystemType(String categoryId, int systemType);
}
