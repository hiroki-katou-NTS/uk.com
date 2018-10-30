package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.Optional;
import java.util.List;

/**
* 固定項目区分一覧
*/
public interface FixedItemClassificationListRepository
{

    List<FixedItemClassificationList> getAllFixedItemClassificationList();

    Optional<FixedItemClassificationList> getFixedItemClassificationListById();

    void add(FixedItemClassificationList domain);

    void update(FixedItemClassificationList domain);

    void remove();

}
