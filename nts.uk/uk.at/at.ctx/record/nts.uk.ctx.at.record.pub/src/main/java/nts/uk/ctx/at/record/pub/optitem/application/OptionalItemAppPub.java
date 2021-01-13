package nts.uk.ctx.at.record.pub.optitem.application;

import java.util.List;

public interface OptionalItemAppPub {

    /**
     * Gets the optional items for application.
     *
     * @param companyId       the company id
     * @param optionalItemNos the optional item nos
     * @return the optional items
     */
    List<OptionalItemAppExport> getOptionalItems(String companyId, List<Integer> optionalItemNos);
}
