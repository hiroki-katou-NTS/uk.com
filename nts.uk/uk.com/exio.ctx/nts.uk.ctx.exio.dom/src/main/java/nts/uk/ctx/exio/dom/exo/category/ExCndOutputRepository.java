package nts.uk.ctx.exio.dom.exo.category;

import java.util.Optional;
import java.util.List;

/**
* 外部出力リンクテーブル
*/
public interface ExCndOutputRepository
{

    List<ExCndOutput> getAllExCndOutput();

    Optional<ExCndOutput> getExCndOutputById(String categoryId);

    void add(ExCndOutput domain);

    void update(ExCndOutput domain);

    void remove(String categoryId);

}
