package nts.uk.ctx.at.shared.dom.adapter.workplace.group;

import java.util.List;

public interface SharedAffWorkplaceGroupAdapter {
    /**
     * Find by cid and WkpIds.
     */
    List<AffWorkplaceGroupImport> getByListWkpIds(String cid, List<String> wkpIds);
}
