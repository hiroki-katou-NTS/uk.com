/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service;

/**
 * The Interface ExtBudgetFileCheckService.
 */
public interface ExtBudgetFileCheckService {

    /**
     * Valid file format.
     *
     * @param fileId the file id
     * @param encoding the encoding
     * @param startLine the start line
     */
    void validFileFormat(String fileId, Integer encoding, Integer startLine);
    
    /**
     * Valid file ignore charset.
     *
     * @param fileId the file id
     * @param encoding the encoding
     * @param startLine the start line
     */
    void validFileIgnoreCharset(String fileId, Integer encoding, Integer startLine);
}
