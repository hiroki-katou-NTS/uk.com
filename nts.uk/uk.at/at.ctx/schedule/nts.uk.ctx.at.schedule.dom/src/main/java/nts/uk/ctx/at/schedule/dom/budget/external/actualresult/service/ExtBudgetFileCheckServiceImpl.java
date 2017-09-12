/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.collection.CollectionUtil;

/**
 * The Class ExtBudgetFileCheckServiceImpl.
 */
@Stateless
public class ExtBudgetFileCheckServiceImpl implements ExtBudgetFileCheckService {

    /** The file stream service. */
    @Inject
    private StoredFileStreamService fileStreamService;

    /** The max record. */
    private final int MAX_RECORD = 999;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgetFileCheckService#validFileFormat(java.lang.String)
     */
    @Override
    public void validFileFormat(String fileId, Integer encoding, Integer standardColumn) {
        InputStream inputStream = this.findContentFile(fileId);

        this.validFileExtension(fileId);
        this.validLimitRecord(inputStream, encoding, standardColumn);

        // close input stream
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find content file.
     *
     * @param fileId
     *            the file id
     * @return the input stream
     */
    private InputStream findContentFile(String fileId) {
        // check file is chose?
        if (StringUtils.isEmpty(fileId)) {
            throw new BusinessException("Msg_157");
        }
        try {
            // get input stream
            return this.fileStreamService.takeOutFromFileId(fileId);
        } catch (BusinessException businessException) {
            throw new BusinessException("Msg_158");
        }
    }

    /**
     * Valid file extension.
     *
     * @param fileId
     *            the file id
     */
    private void validFileExtension(String fileId) {
        // TODO: check extension file, if not support, throw new BusinessException("Msg_159");
    }

    /**
     * Valid limit record.
     *
     * @param inputStream
     *            the input stream
     * @param encoding
     *            the encoding
     * @param standardColumn
     *            the standard column
     */
    private void validLimitRecord(InputStream inputStream, Integer encoding, Integer standardColumn) {
        List<List<String>> resutl = FileUtil.findContentFile(inputStream, encoding, standardColumn);
        if (CollectionUtil.isEmpty(resutl)) {
            return;
        }
        if (resutl.size() > MAX_RECORD) {
            throw new BusinessException("Msg_168");
        }
    }

}
