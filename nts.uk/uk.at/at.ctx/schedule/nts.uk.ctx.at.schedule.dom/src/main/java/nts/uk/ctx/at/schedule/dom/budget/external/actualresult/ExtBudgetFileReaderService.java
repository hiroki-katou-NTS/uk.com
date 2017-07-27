/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.io.File;
import java.util.Map;

/**
 * The Interface ExtBudgetFileReaderService.
 */
public interface ExtBudgetFileReaderService {
    
    /**
     * Find data preview.
     *
     */
    Map<String, Object> findDataPreview();
    
    /**
     * Execute.
     *
     * @param externalBudgetCode the external budget code
     * @param file the file
     */
    void execute(String externalBudgetCode, File file);
    
    /**
     * Close.
     *
     * @param isClosed the is closed
     */
    void close(boolean isClosed);
}
