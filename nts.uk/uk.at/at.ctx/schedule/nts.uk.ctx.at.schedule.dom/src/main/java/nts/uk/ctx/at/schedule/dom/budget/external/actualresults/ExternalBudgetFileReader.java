/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import java.io.File;
import java.util.Map;

/**
 * The Interface ExternalBudgetFileReader.
 */
public interface ExternalBudgetFileReader {
    
    /**
     * Find data preview.
     *
     * @param file the file
     * @return the map
     */
    Map<String, Object> findDataPreview(File file);
    
    /**
     * Read.
     *
     * @param externalBudgetCode the external budget code
     * @param file the file
     */
    void read(String externalBudgetCode, File file);
    
    /**
     * Close.
     *
     * @param isClosed the is closed
     */
    void close(boolean isClosed);
}
