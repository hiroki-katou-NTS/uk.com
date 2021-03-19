/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance.service;

/**
 * The Interface ExtBudgetFileCheckService.
 */
public interface ExtBudgetFileCheckService {

    /**
     * Valid file format.
     *
     * @param fileId the file id
     * @param encoding the encoding
     * @param standardColumn the standard column
     */
    void validFileFormat(String fileId, Integer encoding, Integer standardColumn);
}
