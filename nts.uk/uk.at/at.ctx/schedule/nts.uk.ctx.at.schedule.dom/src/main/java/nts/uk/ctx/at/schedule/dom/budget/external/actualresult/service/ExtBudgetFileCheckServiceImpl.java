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

    /** The Constant FILE_EXTENSION_ARR. */
    private final List<String> FILE_EXTENSION_ARR = Arrays.asList("txt", "csv");

    /** The Constant MAX_RECORD. */
    private final int MAX_RECORD = 999;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgetFileCheckService#validFileFormat(java.lang.String)
     */
    @Override
    public void validFileFormat(String fileId, Integer encoding, Integer startLine) {
        this.validFileExisted(fileId);
        this.validFileExtension(fileId);
        this.validLimitRecord(fileId, encoding, startLine);
        // TODO: validate encoding if has error, show message Msg_161: Do u want to continue import?
    }

    /**
     * Valid file existed.
     *
     * @param fileId the file id
     */
    private void validFileExisted(String fileId) {
        if (StringUtils.isEmpty(fileId)) {
            throw new BusinessException("Msg_157");
        }
        try {
            this.fileStreamService.takeOutFromFileId(fileId).close();
        } catch (BusinessException businessException) {
            throw new BusinessException("Msg_158");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }

    /**
     * Valid file extension.
     *
     * @param fileId the file id
     */
    private void validFileExtension(String fileId) {
        Optional<StoredFileInfo> optional = this.fileInfoRepository.find(fileId);
        if (!optional.isPresent()) {
            throw new RuntimeException("stored file info is not found.");
        }
        StoredFileInfo storagedFileInfor = optional.get();
        // TODO: check file extension
//        String extensionFile = storagedFileInfor.getMimeType().toLowerCase();
//        for (String item : FILE_EXTENSION_ARR) {
//            if (!extensionFile.contains(item)) {
//                throw new BusinessException("Msg_159");
//            }
//        }
    }

    /**
     * Valid limit record.
     *
     * @param fileId the file id
     * @param encoding the encoding
     * @param startLine the start line
     */
    private void validLimitRecord(String fileId, Integer encoding, Integer startLine) {
        NtsCsvReader csvReader = FileUltil.newCsvReader(encoding);
        try {
            InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);
            List<NtsCsvRecord> csvRecords = csvReader.parse(inputStream);
            if (CollectionUtil.isEmpty(csvRecords)) {
                return;
            }
            if (csvRecords.size() > MAX_RECORD) {
                throw new BusinessException("Msg_168");
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
