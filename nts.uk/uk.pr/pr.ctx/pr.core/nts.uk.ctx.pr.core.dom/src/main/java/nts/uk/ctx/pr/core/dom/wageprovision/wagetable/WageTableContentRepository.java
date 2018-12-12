package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
 * 賃金テーブル内容
 */
public interface WageTableContentRepository {

    List<WageTableContent> getAllWageTableContent();

    Optional<WageTableContent> getWageTableContentById(String historyId);

    void addOrUpdate(WageTableContent domain);

    void remove(String historyId);

    List<WageTableQualification> getWageTableQualification(String historyId);

    List<WageTableQualification> getDefaultWageTableQualification();
}
