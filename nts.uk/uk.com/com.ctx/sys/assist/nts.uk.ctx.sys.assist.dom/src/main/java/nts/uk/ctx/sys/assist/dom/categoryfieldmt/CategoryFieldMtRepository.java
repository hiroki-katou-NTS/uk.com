package nts.uk.ctx.sys.assist.dom.categoryfieldmt;

import java.util.List;
import java.util.Optional;

/**
* カテゴリ項目マスタ
*/
public interface CategoryFieldMtRepository
{

    List<CategoryFieldMt> getAllCategoryFieldMt();

    Optional<CategoryFieldMt> getCategoryFieldMtById();

    void add(CategoryFieldMt domain);

    void update(CategoryFieldMt domain);

    void remove();

    List<CategoryFieldMt> getCategoryFieldMtByListId(List<String> categoryIds);
    List<CategoryFieldMt> findByCategoryIdAndSystemType(String categoryId, int systemType);
    
}
