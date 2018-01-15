/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.export.budget.external.actualresult;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * The Interface ExtBudgetErrorGemerator.
 */
public interface ExtBudgetErrorGenerator {
    
    /**
     * Generate.
     *
     * @param fileContext the file context
     * @param exportData the export data
     */
    void generate(FileGeneratorContext fileContext, ExportData exportData);
}
