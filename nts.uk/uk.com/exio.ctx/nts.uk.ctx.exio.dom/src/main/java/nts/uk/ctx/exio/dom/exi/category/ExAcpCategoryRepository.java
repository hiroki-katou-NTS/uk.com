package nts.uk.ctx.exio.dom.exi.category;

import java.util.Optional;
import java.util.List;

/**
* 外部受入カテゴリ
*/
public interface ExAcpCategoryRepository
{

    List<ExAcpCategory> getAllExAcpCategory();

    Optional<ExAcpCategory> getExAcpCategoryById(String categoryId);

    void add(ExAcpCategory domain);

    void update(ExAcpCategory domain);

    void remove(String categoryId);

}
