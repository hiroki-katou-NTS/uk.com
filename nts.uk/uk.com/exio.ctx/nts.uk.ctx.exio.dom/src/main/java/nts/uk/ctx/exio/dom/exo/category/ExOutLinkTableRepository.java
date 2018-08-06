package nts.uk.ctx.exio.dom.exo.category;

import java.util.Optional;
import java.util.List;

/**
* 外部出力リンクテーブル
*/
public interface ExOutLinkTableRepository
{

    List<ExOutLinkTable> getAllExCndOutput();

    Optional<ExOutLinkTable> getExCndOutputById(Integer categoryId);

    void add(ExOutLinkTable domain);

    void update(ExOutLinkTable domain);

    void remove(int categoryId);

}
