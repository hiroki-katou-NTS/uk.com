package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
* 賃金テーブル内容
*/
public interface WageTableContentRepository
{

    List<WageTableContent> getAllWageTableContent();

    Optional<WageTableContent> getWageTableContentById();

    void add(WageTableContent domain);

    void update(WageTableContent domain);

    void remove();

}
