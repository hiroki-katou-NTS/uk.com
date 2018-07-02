package nts.uk.ctx.exio.dom.exo.category;

import java.util.List;
import java.util.Optional;

/**
* 外部出力カテゴリ
*/
public interface ExOutCtgRepository
{

    List<ExOutCtg> getAllExOutCtg();

    Optional<ExOutCtg> getExOutCtgById(String categoryId);

    void add(ExOutCtg domain);

    void update(ExOutCtg domain);

    void remove(String categoryId);

}
