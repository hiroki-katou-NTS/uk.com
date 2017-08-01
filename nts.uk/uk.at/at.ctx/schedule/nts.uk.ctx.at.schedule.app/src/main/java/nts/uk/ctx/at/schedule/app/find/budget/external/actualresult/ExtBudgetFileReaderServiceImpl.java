package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.Encoding;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetFileReaderService;

public class ExtBudgetFileReaderServiceImpl implements ExtBudgetFileReaderService {
    
    public static final String DATA_PREVIEW = "DATA_PREVIEW";
    public static final String TOTAL_RECORD = "TOTAL_RECORD";
    
    private ExtBudgetExtractCondition extractCondition;
    private InputStream inputStream;
    
    @Inject
    private StoredFileInfoRepository fileInfoRepository;
    
    @Inject 
    StoredFileStreamService fileStreamService;
    
    public ExtBudgetFileReaderServiceImpl(ExtBudgetExtractCondition extractCondition) {
        this.extractCondition = extractCondition;
    }

    @Override
    public Map<String, Object> findDataPreview() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void execute(String externalBudgetCode, File file) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close(boolean isClosed) {
        // TODO Auto-generated method stub
        
    }

    private void validate() {
        this.validFileExisted();
        this.validFileExtension();
    }
    
    private void validFileExisted() {
        try {
            inputStream = this.fileStreamService.takeOutFromFileId(this.extractCondition.getFileId());
        } catch (BusinessException businessException) {
            throw new BusinessException("Msg_158");
        }
    }
    
    private void validFileExtension() {
        Optional<StoredFileInfo> optional = this.fileInfoRepository.find(this.extractCondition.getFileId());
        if(!optional.isPresent()){
            new RuntimeException("stored file info is not found.");
        }
        StoredFileInfo storagedFileInfor = optional.get();
        
        // check file extension
        List<String> FILE_EXTENSION_ARR = Arrays.asList("text, csv");
        if (!FILE_EXTENSION_ARR.contains(storagedFileInfor.getFileType().toLowerCase())) {
            throw new BusinessException("Msg_159"); 
        }
    }
    
    private void validEncoding() {
        System.out.println(new File(ServerSystemProperties.fileStoragePath()).toPath().resolve(this.extractCondition.getFileId()).toString());
        Encoding targetEncode = Encoding.valueOf(this.extractCondition.getEncoding());
        switch (targetEncode) {
        case Shift_JIS:

            break;

        default:
            break;
        }
    }
}
