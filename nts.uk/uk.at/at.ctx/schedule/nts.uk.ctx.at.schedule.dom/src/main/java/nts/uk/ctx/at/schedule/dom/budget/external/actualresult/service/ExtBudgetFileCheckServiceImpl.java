/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.collection.CollectionUtil;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.gul.text.charset.CharsetDetector;
import nts.gul.text.charset.NtsCharset;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetCharset;

/**
 * The Class ExtBudgetFileCheckServiceImpl.
 */
@Stateless
public class ExtBudgetFileCheckServiceImpl implements ExtBudgetFileCheckService {

    /** The file info repository. */
    @Inject
    private StoredFileInfoRepository fileInfoRepository;

    /** The file stream service. */
    @Inject
    private StoredFileStreamService fileStreamService;

    /** The file extension arr. */
    private final List<String> FILE_EXTENSION_ARR = Arrays.asList("txt", "csv");

    /** The max record. */
    private final int MAX_RECORD = 999;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgetFileCheckService#validFileFormat(java.lang.String)
     */
    @Override
    public void validFileFormat(String fileId, Integer encoding, Integer startLine) {
        InputStream inputStream = this.findContentFile(fileId);

        this.validFileExtension(fileId);
        this.validLimitRecord(inputStream, encoding, startLine);
        this.validCharset(inputStream);

        // close input stream
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.
     * ExtBudgetFileCheckService#validFileIgnoreCharset(java.lang.String,
     * java.lang.Integer, java.lang.Integer)
     */
    @Override
    public void validFileIgnoreCharset(String fileId, Integer encoding, Integer startLine) {
        InputStream inputStream = this.findContentFile(fileId);

        this.validFileExtension(fileId);
        this.validLimitRecord(inputStream, encoding, startLine);

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
     * @param startLine
     *            the start line
     */
    private void validLimitRecord(InputStream inputStream, Integer encoding, Integer startLine) {
        NtsCsvReader csvReader = FileUltil.newCsvReader(encoding);
        try {
            List<NtsCsvRecord> csvRecords = csvReader.parse(inputStream);
            if (CollectionUtil.isEmpty(csvRecords)) {
                return;
            }
            if (csvRecords.size() > MAX_RECORD) {
                throw new BusinessException("Msg_168");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Valid charset.
     *
     * @param inputStream
     *            the input stream
     */
    private void validCharset(InputStream inputStream) {
        NtsCharset charset = CharsetDetector.detect(inputStream);
        if (charset.value != ExtBudgetCharset.Shift_JIS.value) {
            throw new BusinessException("Msg_161");
        }
    }
}
