package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
* 賃金テーブル
*/
public interface WageTableRepository
{

    List<WageTable> getAllWageTable();

    Optional<WageTable> getWageTableById(String cid, String wageTableCode);

    void add(WageTable domain);

    void update(WageTable domain);

    void remove(String cid, String wageTableCode);

}
