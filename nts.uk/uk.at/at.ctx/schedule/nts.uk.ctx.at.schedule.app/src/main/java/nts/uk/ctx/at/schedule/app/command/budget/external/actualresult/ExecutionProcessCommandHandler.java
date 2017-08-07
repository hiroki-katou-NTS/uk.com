package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang3.StringUtils;

import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.task.AsyncTask;
import nts.arc.task.AsyncTaskService;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetDailyDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetErrorDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetTimeDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.Encoding;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDailyRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetErrorRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZone;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZoneRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.ExtBudgetFileCheckService;
import nts.uk.shr.com.context.AppContexts;

@Stateful
public class ExecutionProcessCommandHandler extends CommandHandlerWithResult<ExecutionProcessCommand, String> {
    
    @Inject
    private AsyncTaskService managedTaskService;
    
    @Inject
    private IInternationalization internationalization;
    
    /** The file check service. */
    @Inject
    private ExtBudgetFileCheckService fileCheckService;
    
    @Inject
    private ExternalBudgetRepository externalBudgetRepo;
    
    @Inject
    private ExternalBudgetDailyRepository extBudgetDailyRepo;
    
    @Inject
    private ExternalBudgetTimeZoneRepository extBudgetTimeRepo;
    
    @Inject
    private ExternalBudgetLogRepository extBudgetLogRepo;
    
    @Inject
    private ExternalBudgetErrorRepository extBudgetErrorRepo;
    
    @Inject
    private StoredFileStreamService fileStreamService;
    
    private TaskDataSetter setter = new TaskDataSetter();
    private static final List<String> FORMAT_DATES = Arrays.asList("yyyyMMdd", "yyyy/M/d", "yyyy/MM/dd");
    private static final Integer OPEN_DIALOG = -990;
    private static final Integer INDEX_COLUMN_CODE = 0;
    private static final Integer INDEX_COLUMN_DATE = 1;
    private static final Integer INDEX_BEGIN_COL_VALUE = 2;
    
    @Override
    protected String handle(CommandHandlerContext<ExecutionProcessCommand> context) {
        ExecutionProcessCommand command = context.getCommand();
        String employeeId = AppContexts.user().employeeId();
        // GUID
        String executeId = IdentifierUtil.randomUniqueId();
        AsyncTask task = AsyncTask.builder().withContexts().keepsTrack(true).build(() -> {
            this.fileCheckService.validFileFormat(command.getFileId());
            setter.setData("OPEN_DIALOG", OPEN_DIALOG);
            
            // register table LOG with status: IN_COMPLETE 
            GeneralDateTime dateTimeCurrent = GeneralDateTime.now();
            ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.builder()
                    .executionId(executeId)
                    .employeeId(employeeId)
                    .startDateTime(dateTimeCurrent)
                    .endDateTime(dateTimeCurrent) // begin import, do not have end date?
                    .extBudgetCode(command.getExternalBudgetCode())
                    .extBudgetFileName(command.getFileName())
                    .completionState(CompletionState.INCOMPLETE)
                    .build();
            this.extBudgetLogRepo.add(extBudgetLogDto.toDomain());
            
            String companyId = AppContexts.user().companyId();
            Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId,
                    command.getExternalBudgetCode());
            if (!extBudgetOptional.isPresent()) {
                throw new RuntimeException("Not external budget setting.");
            }
            
            // initial import process
            ImportProcess importProcess = new ImportProcess();
            importProcess.executeId = executeId;
            importProcess.inputStream = this.fileStreamService.takeOutFromFileId(command.getFileId());
            importProcess.externalBudget = extBudgetOptional.get();
            importProcess.extractCondition = command;
            
            // begin process input file
            this.processInput(importProcess);
        });
        task.setDataSetter(setter);
        this.managedTaskService.execute(task);
        return task.getId();
    }
    
    private void processInput(ImportProcess importProcess) {
        try {
            Charset charset = this.getCharset(importProcess.extractCondition.getEncoding());
            String newLineCode = this.getNewLineCode();
            NtsCsvReader csvReader = NtsCsvReader.newReader().withChartSet(charset)
                    .withFormat(CSVFormat.EXCEL.withHeader(this.fakeHeader(importProcess.externalBudget.getUnitAtr()))
                            .withRecordSeparator(newLineCode));
            Iterator<NtsCsvRecord> csvRecordIterator = csvReader.parse(importProcess.inputStream).iterator();
            while(csvRecordIterator.hasNext()) {
                /** TODO: check has interruption, if is interrupt, update table LOG status interruption (中断)
                 * and end flow (stop process)
                 * this.updateLog(importProcess.executeId, CompletionState.INTERRUPTION);
                 */
                importProcess.stopLine = false;
                this.processLine(importProcess, csvRecordIterator.next());
                
                // respond status client
                Optional<ExternalBudgetLog> optional = this.extBudgetLogRepo.findExtBudgetLogByExecuteId(importProcess.executeId);
                if (optional.isPresent()) {
                    ExternalBudgetLog log = optional.get();
                    setter.setData("SUCCESS_CNT", log.getNumberSuccess());
                    setter.setData("FAIL_CNT", log.getNumberFail());
                }
            }
            this.updateLog(importProcess.executeId, CompletionState.DONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void processLine(ImportProcess importProcess, NtsCsvRecord record) {
        importProcess.startLine++;
        if (importProcess.startLine < importProcess.extractCondition.getStartLine()) {
            return;
        }
        // TODO: parse record return List<object>
        String[] lstHeader = this.fakeHeader(importProcess.externalBudget.getUnitAtr());
        List<String> result = new ArrayList<>();
        for (String header : lstHeader) {
            result.add(record.getColumnAsString(header));
        }
        // check record has data?
        if (CollectionUtil.isEmpty(result)) {
            // update table log with status DONE(完了)
            importProcess.failCnt++;
            this.updateLog(importProcess.executeId, CompletionState.DONE);
            return;
        }
        this.validDataLine(importProcess, result);
        this.insertValue(importProcess, result);
    }
    
    private void insertValue(ImportProcess importProcess, List<String> result) {
        if (importProcess.stopLine) {
            return;
        }
        boolean isUpdateCaseFail = false;
        try {
            switch (importProcess.externalBudget.getUnitAtr()) {
            case DAILY:
                this.addExtBudgetDaily(importProcess, result);
                break;
            case BYTIMEZONE:
                this.addExtBudgetTime(importProcess, result);
                break;
            default:
                throw new RuntimeException("Not unit atr suitable.");
            }
            importProcess.successCnt++;
            this.updateLog(isUpdateCaseFail, importProcess.executeId, importProcess.successCnt);
        } catch (Exception e) {
            importProcess.failCnt++;
            isUpdateCaseFail = true;
            this.updateLog(isUpdateCaseFail, importProcess.executeId, importProcess.failCnt);
        }
    }
    
    private void addExtBudgetDaily(ImportProcess importProcess, List<String> result) {
        ExternalBudgetDailyDto dto = ExternalBudgetDailyDto.builder()
                .budgetAtr(importProcess.externalBudget.getBudgetAtr())
                .workplaceId(result.get(INDEX_COLUMN_CODE)) // TODO: find workplace id by workplace code
                .extBudgetCode(importProcess.extractCondition.getExternalBudgetCode())
                .actualDate(importProcess.actualDate)
                .actualValue(Long.parseLong(result.get(INDEX_BEGIN_COL_VALUE)))
                .build();
        switch (importProcess.externalBudget.getBudgetAtr()) {
        case TIME:
            ExternalBudgetDaily<ExtBudgetTime> domainTime = dto.toDomain();
            this.saveDataDaily(importProcess, domainTime);
            break;
        case PEOPLE:
            ExternalBudgetDaily<ExtBudgetNumberPerson> domainPeople = dto.toDomain();
            this.saveDataDaily(importProcess, domainPeople);
            break;
        case MONEY:
            ExternalBudgetDaily<ExtBudgetMoney> domainMoney = dto.toDomain();
            this.saveDataDaily(importProcess, domainMoney);
            break;
        case NUMERICAL:
            ExternalBudgetDaily<ExtBudgetNumericalVal> domainNumerical = dto.toDomain();
            this.saveDataDaily(importProcess, domainNumerical);
            break;
        case PRICE:
            ExternalBudgetDaily<ExtBudgetUnitPrice> domainPrice = dto.toDomain();
            this.saveDataDaily(importProcess, domainPrice);
            break;
        default:
            throw new RuntimeException("Not budget atr suitable.");
        }
    }
    
    private <T> void saveDataDaily (ImportProcess importProcess, ExternalBudgetDaily<T> domain) {
        if (!this.extBudgetDailyRepo.isExisted(domain.getWorkplaceId(), GeneralDate.legacyDate(domain.getActualDate()),
                domain.getExtBudgetCode().v())) {
            this.extBudgetDailyRepo.add(domain);
            return;
        }
        if (importProcess.extractCondition.getIsOverride()) {
            this.extBudgetDailyRepo.update(domain);
            return;
        }
        // insert table ERROR with message id: Msg_167
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(0)
                .errorContent(this.getMessageById("Msg_167"))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
    }
    
    private void addExtBudgetTime(ImportProcess importProcess, List<String> result) {
        Map<Integer, Long> mapValue = new HashMap<>();
        List<String> lstValue = result.subList(INDEX_BEGIN_COL_VALUE, result.size());
        for (int i=0; i<lstValue.size(); i++) {
            mapValue.put(i, Long.parseLong(lstValue.get(i)));
        }
        ExternalBudgetTimeDto dto = ExternalBudgetTimeDto.builder()
                .workplaceId("") // TODO: find workplace id by workplace code
                .extBudgetCode(importProcess.extractCondition.getExternalBudgetCode())
                .actualDate(importProcess.actualDate)
                .mapValue(mapValue)
                .build();
        switch (importProcess.externalBudget.getBudgetAtr()) {
        case TIME:
            ExternalBudgetTimeZone<ExtBudgetTime> domainTime = dto.toDomain();
            this.saveDataTime(importProcess, domainTime);
            break;
        case PEOPLE:
            ExternalBudgetTimeZone<ExtBudgetNumberPerson> domainPeople = dto.toDomain();
            this.saveDataTime(importProcess, domainPeople);
            break;
        case MONEY:
            ExternalBudgetTimeZone<ExtBudgetMoney> domainMoney = dto.toDomain();
            this.saveDataTime(importProcess, domainMoney);
            break;
        case NUMERICAL:
            ExternalBudgetTimeZone<ExtBudgetNumericalVal> domainNumerical = dto.toDomain();
            this.saveDataTime(importProcess, domainNumerical);
            break;
        case PRICE:
            ExternalBudgetTimeZone<ExtBudgetUnitPrice> domainPrice = dto.toDomain();
            this.saveDataTime(importProcess, domainPrice);
            break;
        default:
            throw new RuntimeException("Not budget atr suitable.");
        }
    }
    
    private <T> void saveDataTime (ImportProcess importProcess, ExternalBudgetTimeZone<T> domain) {
        if (!this.extBudgetTimeRepo.isExisted(domain.getWorkplaceId(), GeneralDate.legacyDate(domain.getActualDate()),
                domain.getExtBudgetCode().v())) {
            this.extBudgetTimeRepo.add(domain);
            return;
        }
        if (importProcess.extractCondition.getIsOverride()) {
            this.extBudgetTimeRepo.update(domain);
            return;
        }
        // insert table ERROR with message id: Msg_167
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(0)
                .errorContent(this.getMessageById("Msg_167"))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
    }
    
    private void validDataLine(ImportProcess importProcess, List<String> result) {
        this.validUnitAtr(importProcess, result.size());
        
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        // check column 2 (２列目) is date ?
        this.validDateFormat(importProcess, result.get(INDEX_COLUMN_DATE));
        
        // Check colum 1 (1列目) is workplace code?
        this.validWorkplaceCode(importProcess, result.get(INDEX_COLUMN_CODE));
        
        // Check actual value by primitive
        int idxValue = INDEX_BEGIN_COL_VALUE;
//        this.validActualVal(importProcess, result.subList(idxValue, result.size()));
    }
    private void validUnitAtr(ImportProcess importProcess, int numberColInput) {
        UnitAtr unitAtr = importProcess.externalBudget.getUnitAtr();
        int numberCol = 0;
        switch (unitAtr) {
        case DAILY:
            numberCol = 3;
            break;
        case BYTIMEZONE:
            numberCol = 50;
            break;
        default:
            throw new RuntimeException("Not unit atr suitable.");
        }
        if (numberColInput < numberCol) {
            ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                    .executionId(importProcess.executeId)
                    .lineNo(importProcess.startLine)
                    .columnNo(numberColInput)
                    .errorContent(this.getMessageById("Msg_163"))
                    .build();
            this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
            
            importProcess.failCnt++;
            // marker finish process line
            importProcess.stopLine = true;
        } else if (numberColInput > numberCol) {
            ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                    .executionId(importProcess.executeId)
                    .lineNo(importProcess.startLine)
                    .columnNo(++numberCol)
                    .errorContent(this.getMessageById("Msg_162"))
                    .build();
            this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
            
            importProcess.failCnt++;
            // marker finish process line
            importProcess.stopLine = true;
        }
    }
    
    private void validDateFormat(ImportProcess importProcess, String inputDate) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        Boolean isInValidDateFormat = null;
        for (String formatDate : FORMAT_DATES) {
            try {
                isInValidDateFormat = false;
                Date date = new SimpleDateFormat(formatDate).parse(inputDate);
                importProcess.actualDate = date;
                break;
            } catch (ParseException e) {
                isInValidDateFormat = true;
            }
        }
        if (isInValidDateFormat) {
            ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                    .executionId(importProcess.executeId)
                    .lineNo(importProcess.startLine)
                    .columnNo(INDEX_COLUMN_DATE)
                    .errorContent("Invalid format") // Not has message id
                    .build();
            this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
            
            importProcess.failCnt++;
            // marker finish process line
            importProcess.stopLine = true;
        }
    }
    
    private void validWorkplaceCode(ImportProcess importProcess, String workplaceCode) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        /** TODO: pending wait anotation find workplace other context.
         * if has erorr, finish process line 
         * importProcess.failCnt++;
         */
    }
    
    private void validActualVal(ImportProcess importProcess, List<String> lstValue) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        int idxVal = INDEX_BEGIN_COL_VALUE;
        for (int i = 0; i < lstValue.size(); i++) {
            String value = lstValue.get(i);
            String errorContent = this.validValuePrimitive(importProcess, value);
            if (!StringUtils.isEmpty(errorContent)) {
                ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                        .executionId(importProcess.executeId)
                        .lineNo(importProcess.startLine)
                        .columnNo(idxVal + 1)
                        .errorContent(errorContent)
                        .build();
                this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
                importProcess.failCnt++;
            }
        }
    }
    
    private String validValuePrimitive(ImportProcess importProcess, String value) {
        // Check actual value base on primitive.
        //  TODO: pending team Kiban update get message error from primitive.
        try {
            switch (importProcess.externalBudget.getBudgetAtr()) {
                case TIME:
                    // TODO: convert hour, min ==> min
                    break;
                case PEOPLE:
                    break;
                case MONEY:
                    break;
                case NUMERICAL:
                    break;
                case PRICE:
                    break;
                default:
                    throw new RuntimeException("Not budget atr suitable.");
            }
        } catch (Exception e) {
            // TODO: handle exception
//            e.printStackTrace();
        }
        return "";
    }
    
    private Long convertVal(String value) {
        String CHARACTER_COLON = ":";
        if (!value.contains(CHARACTER_COLON)) {
            throw new RuntimeException("Actual valua invalid format.");
        }
        String[] arr = value.split(CHARACTER_COLON);
        Integer HOUR = 60;
        try {
            Long numberHour = Long.parseLong(arr[0]);
            Long numberminute = Long.parseLong(arr[1]);
            return numberHour * HOUR + numberminute;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
    
    private void updateLog(String executeId, CompletionState status) {
        Optional<ExternalBudgetLog> optional = this.extBudgetLogRepo.findExtBudgetLogByExecuteId(executeId);
        if (optional.isPresent()) {
            ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.copy(optional.get());
            extBudgetLogDto.completionState = status;
            extBudgetLogDto.endDateTime = GeneralDateTime.now();
            this.extBudgetLogRepo.update(extBudgetLogDto.toDomain());
        }
    }
    private void updateLog(boolean isUpdateCaseFail, String executeId, Integer number) {
        Optional<ExternalBudgetLog> optional = this.extBudgetLogRepo.findExtBudgetLogByExecuteId(executeId);
        if (optional.isPresent()) {
            ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.copy(optional.get());
            if (isUpdateCaseFail) {
                extBudgetLogDto.numberFail = number;
            } else {
                extBudgetLogDto.numberSuccess = number;
            }
            this.extBudgetLogRepo.update(extBudgetLogDto.toDomain());
        }
    }
    
    private String getNewLineCode() {
        return "\r\n"; // CR+LF
    }
    
    private String[] fakeHeader(UnitAtr unitAtr) {
        switch (unitAtr) {
            case DAILY:
                return new String[] {"職場コード", "年月日", "値"};
            case BYTIMEZONE:
                List<String> lstHeader = new ArrayList<>();
                lstHeader.add("職場コード");
                lstHeader.add("年月日");
                for (int i = 0; i < 48; i++) {
                    lstHeader.add("値" + i);
                }
                return lstHeader.toArray(new String[lstHeader.size()]);
            default:
                throw new RuntimeException("Not unit atr suitable.");
        }
    }
    
    private String getMessageById(String messageId) {
        String errorContent = messageId + " is not found.";
        Optional<String> optional = this.internationalization.getRawMessage(messageId);
        if (optional.isPresent()) {
            errorContent = optional.get();
        }
        return errorContent;
    }
    
    class ImportProcess {
        String executeId = "";
        int startLine;
        int successCnt = 0;
        int failCnt = 0;
        boolean stopLine;
        InputStream inputStream;
        ExternalBudget externalBudget;
        ExecutionProcessCommand extractCondition;
        
        Date actualDate;
    }
}
