package nts.uk.ctx.sys.assist.dom.categoryfieldmt;

import java.util.Optional;
import java.util.List;

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

}
