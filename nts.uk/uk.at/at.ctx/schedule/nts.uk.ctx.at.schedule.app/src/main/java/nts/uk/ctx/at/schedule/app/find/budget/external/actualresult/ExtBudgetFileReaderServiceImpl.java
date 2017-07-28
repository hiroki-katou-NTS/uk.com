package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetFileReaderService;

public class ExtBudgetFileReaderServiceImpl implements ExtBudgetFileReaderService {
    
    public static final String DATA_PREVIEW = "DATA_PREVIEW";
    public static final String TOTAL_RECORD = "TOTAL_RECORD";
    
    private InputStream inputStream;
    
    public ExtBudgetFileReaderServiceImpl(InputStream inputStream) {
        this.inputStream = inputStream;
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
        // check file existed
        this.checkFileExisted();
    }
    
    private void checkFileExisted() {
        if (this.inputStream == null) {
            throw new BusinessException("Msg_158");
        }
    }
    
    private void validateFormatFile() {
        
    }
    
    private void checkFileExtension() {
    }
}
