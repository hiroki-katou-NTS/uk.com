package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.at.schedule.dom.budget.external.BudgetAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.Encoding;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetFileReaderService;

public class ExtBudgetFileReaderServiceImpl implements ExtBudgetFileReaderService {
    
    public static final String DATA_PREVIEW = "DATA_PREVIEW";
    public static final String TOTAL_RECORD = "TOTAL_RECORD";
    
    private final ExtBudgetExtractCondition extractCondition;
    private final ExternalBudget externalBudget;
    
    private final StoredFileInfoRepository fileInfoRepository;
    private final StoredFileStreamService fileStreamService;
    
    private InputStream inputStream;
    
    public ExtBudgetFileReaderServiceImpl(ExtBudgetExtractCondition extractCondition, ExternalBudget externalBudget,
            StoredFileInfoRepository fileInfoRepository, StoredFileStreamService fileStreamService) {
        this.extractCondition = extractCondition;
        this.externalBudget = externalBudget;
        
        this.fileInfoRepository = fileInfoRepository;
        this.fileStreamService = fileStreamService;
    }

    @Override
    public Map<String, Object> findDataPreview() {
        this.validate();
        this.processInput();
        return null;
    }

    @Override
    public void execute(String externalBudgetCode, File file) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close(boolean isClosed) {
        try {
            if (!isClosed) {
                return;
            }
            if (this.inputStream != null) {
                this.inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validate() {
        this.validFileExisted();
        this.validFileExtension();
        this.validEncoding();
    }
    
    private void processInput() {
        try {
            Charset charset = this.getCharset(this.extractCondition.getEncoding());
            String newLineCode = this.getNewLineCode();
            NtsCsvReader csvReader = NtsCsvReader.newReader().withChartSet(charset)
                    .withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode));
            Iterator<NtsCsvRecord> csvRecordIterator = csvReader.parse(this.inputStream).iterator();
            while(csvRecordIterator.hasNext()) {
                this.splitLine(csvRecordIterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Map<String, Object> splitLine(NtsCsvRecord record) {
        Map<String, Object> resultMap = new HashMap<>();
        // TODO: parse record
        return resultMap;
    }
    
    private void validFileExisted() {
        try {
            this.inputStream = this.fileStreamService.takeOutFromFileId(this.extractCondition.getFileId());
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
//        System.out.println(new File(ServerSystemProperties.fileStoragePath()).toPath()
//                .resolve(this.extractCondition.getFileId()).toString());
        Encoding targetEncode = Encoding.valueOf(this.extractCondition.getEncoding());
        // TODO: get encoding of input file, then compare to encoding setting on the screen.
        Encoding inputEncode = Encoding.Shift_JIS;
        if (targetEncode != inputEncode) {
            throw new BusinessException("Msg_161"); 
        }
    }
    
    private Charset getCharset(Integer value) {
        Encoding encoding = Encoding.valueOf(value);
        switch (encoding) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }
    
    private String getNewLineCode() {
        return "\r\n"; // CR+LF
    }
}
