/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.export.executionlog;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

/**
 * The Interface ExtBudgetErrorGemerator.
 */
public interface ExeErrorLogGenerator {
    
    /**
     * Generate.
     *
     * @param fileContext the file context
     * @param exportData the export data
     */
    void generate(FileGeneratorContext fileContext, ExportData exportData);
}
