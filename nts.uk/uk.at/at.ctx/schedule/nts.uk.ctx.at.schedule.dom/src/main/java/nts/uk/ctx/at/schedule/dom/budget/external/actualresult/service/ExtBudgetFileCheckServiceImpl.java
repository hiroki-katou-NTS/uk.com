/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service;

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
    private static final List<String> FILE_EXTENSION_ARR = Arrays.asList("txt", "csv");
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgetFileCheckService#validFileFormat(java.lang.String)
     */
    @Override
    public void validFileFormat(String fileId) {
        this.validFileExisted(fileId);
        this.validFileExtension(fileId);
        // TODO: validate encoding if has error, show message Msg_164: Do u want to continue import?
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
            this.fileStreamService.takeOutFromFileId(fileId);
        } catch (BusinessException businessException) {
            throw new BusinessException("Msg_158");
        }
    }
    
    /**
     * Valid file extension.
     *
     * @param fileId the file id
     */
    private void validFileExtension(String fileId) {
        Optional<StoredFileInfo> optional = this.fileInfoRepository.find(fileId);
        if(!optional.isPresent()){
            new RuntimeException("stored file info is not found.");
        }
        StoredFileInfo storagedFileInfor = optional.get();
        // check file extension
        String extensionFile = storagedFileInfor.getFileType().toLowerCase();
        for (String item : FILE_EXTENSION_ARR) {
            if (!extensionFile.contains(item)) {
                throw new BusinessException("Msg_159");
            }
        }
    }
}
