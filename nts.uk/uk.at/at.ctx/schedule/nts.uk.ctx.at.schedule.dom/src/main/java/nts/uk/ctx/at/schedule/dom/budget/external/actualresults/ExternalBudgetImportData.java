package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import java.io.File;
import java.util.Map;

/**
 * The Interface ExternalBudgetImportData.
 */
public interface ExternalBudgetImportData {
    
    /**
     * Find data preview.
     *
     * @param file the file
     * @return the map
     */
    Map<String, Object> findDataPreview(File file);
    
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
