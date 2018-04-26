package nts.uk.ctx.sys.assist.dom.category;

import java.util.Optional;
import java.util.List;

/**
* カテゴリ
*/
public interface CategoryRepository
{

    List<Category> getAllCategory();

    Optional<Category> getCategoryById();

    void add(Category domain);

    void update(Category domain);

    void remove();

}
