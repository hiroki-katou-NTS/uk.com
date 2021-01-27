/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.ejb.Stateful;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.primitive.PrimitiveValueUtil;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetDailyDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetErrorDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.budget.external.BudgetAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.dailyunit.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.dailyunit.ExternalBudgetDailyRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error.ExternalBudgetErrorRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.ExtBudgetFileCheckService;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.FileUtil;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class ExecutionProcessCommandHandler.
 */
@Stateful
public class ExecutionProcessCommandHandler extends AsyncCommandHandler<ExecutionProcessCommand> {
    
    /** The file check service. */
    @Inject
    private ExtBudgetFileCheckService fileCheckService;
    
    /** The external budget repo. */
    @Inject
    private ExternalBudgetRepository externalBudgetRepo;
    
    /** The ext budget daily repo. */
    @Inject
    private ExternalBudgetDailyRepository extBudgetDailyRepo;
    
    /** The ext budget log repo. */
    @Inject
    private ExternalBudgetLogRepository extBudgetLogRepo;
    
    /** The ext budget error repo. */
    @Inject
    private ExternalBudgetErrorRepository extBudgetErrorRepo;
    
    /** The file stream service. */
    @Inject
    private StoredFileStreamService fileStreamService;
    
    /** The workplace adapter. */
    @Inject
    private ScWorkplaceAdapter workplaceAdapter;
    
    /** The format dates. */
    private final List<String> FORMAT_DATES = Arrays.asList("yyyyMMdd", "yyyy/M/d", "yyyy/MM/dd");
    
    /** The default value. */
    private final Integer DEFAULT_VALUE = 0;
    
    /** The index column code. */
    private final Integer INDEX_COLUMN_CODE = 0;
    
    /** The index column date. */
    private final Integer INDEX_COLUMN_DATE = 1;
    
    /** The index begin col value. */
    private final Integer INDEX_BEGIN_COL_VALUE = 2;
    
    /** The total record. */
    private final String TOTAL_RECORD = "TOTAL_RECORD";
    
    /** The success cnt. */
    private final String SUCCESS_CNT = "SUCCESS_CNT";
    
    /** The fail cnt. */
    private final String FAIL_CNT = "FAIL_CNT";
    
    /** The blank value. */
    private final String BLANK_VALUE = "0";
    
    /** The blank value time. */
    private final String BLANK_VALUE_TIME = "00:00";
    
    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandlerWithResult#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<ExecutionProcessCommand> context) {
        val asyncTask = context.asAsync();
        TaskDataSetter setter = asyncTask.getDataSetter();

        ExecutionProcessCommand command = context.getCommand();
        String executeId = command.getExecuteId();

        // get setting
        ExternalBudget externalBudget = this.findSetting(command.getExternalBudgetCode());
        
        // get standard column
        Integer standardColumn = FileUtil.getStandardColumn(externalBudget.getUnitAtr());
        
        // valid file format
        this.fileCheckService.validFileFormat(command.getFileId(), command.getEncoding(), standardColumn);

        // get input stream by file id
        InputStream inputStream = this.fileStreamService.takeOutFromFileId(command.getFileId());

        setter.setData(TOTAL_RECORD, DEFAULT_VALUE);
        setter.setData(SUCCESS_CNT, DEFAULT_VALUE);
        setter.setData(FAIL_CNT, DEFAULT_VALUE);

        // register table LOG with status: IN_COMPLETE
        String employeeId = AppContexts.user().employeeId();
        GeneralDateTime dateTimeCurrent = GeneralDateTime.now();
        ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.builder()
                .executionId(executeId)
                .employeeId(employeeId)
                .startDateTime(dateTimeCurrent)
                .endDateTime(dateTimeCurrent)
                .extBudgetCode(command.getExternalBudgetCode())
                .extBudgetFileName(command.getFileName())
                .completionState(CompletionState.INCOMPLETE)
                .build();
        this.extBudgetLogRepo.add(extBudgetLogDto.toDomain());

        // initial import process
        ImportProcess importProcess = new ImportProcess();
        importProcess.executeId = executeId;
        importProcess.inputStream = inputStream;
        importProcess.externalBudget = externalBudget;
        importProcess.extractCondition = command;

        // begin process input file
        this.processInput(importProcess, asyncTask);
    }
    
    /**
     * Find setting.
     *
     * @param externalBudgetCode the external budget code
     * @return the external budget
     */
    private ExternalBudget findSetting(String externalBudgetCode) {
        String companyId = AppContexts.user().companyId();
        Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId, externalBudgetCode);
        if (!extBudgetOptional.isPresent()) {
            throw new RuntimeException("Not external budget setting.");
        }
        return extBudgetOptional.get();
    }
    
    /**
     * Process input.
     *
     * @param <C> the generic type
     * @param importProcess the import process
     * @param asyncTask the async task
     */
    private <C> void processInput(ImportProcess importProcess, AsyncCommandHandlerContext<C> asyncTask) {
        TaskDataSetter setter = asyncTask.getDataSetter();
        boolean isInterrupt = false;
        try {
            // get content of file input
            Map<Integer, List<String>> mapRecord = FileUtil.findContentFile(importProcess.inputStream,
                    importProcess.extractCondition.getEncoding(),
                    FileUtil.getStandardColumn(importProcess.externalBudget.getUnitAtr()));
            // calculate total record and check has data
            int calTotal = mapRecord.size() - importProcess.extractCondition.getStartLine().v() + 1;
            if (calTotal > DEFAULT_VALUE) {
                setter.updateData(TOTAL_RECORD, calTotal);
            }
            Iterator<Entry<Integer, List<String>>> recordIterator =  mapRecord.entrySet().iterator();
            while (recordIterator.hasNext()) {
                /**
                 * check has interruption, if is interrupt, update table LOG
                 * status interruption (中断) and end flow (stop process)
                 */
                if (asyncTask.hasBeenRequestedToCancel()) {
                    isInterrupt = true;
                    this.updateLog(importProcess.executeId, CompletionState.INTERRUPTION);
                    asyncTask.finishedAsCancelled();
                    break;
                }
                importProcess.stopLine = false;
                this.processLine(importProcess, recordIterator.next());
                // respond status client
                Optional<ExternalBudgetLog> optional = this.extBudgetLogRepo
                        .findExtBudgetLogByExecuteId(importProcess.executeId);
                if (optional.isPresent()) {
                    ExternalBudgetLog log = optional.get();
                    setter.updateData(SUCCESS_CNT, log.getNumberSuccess());
                    setter.updateData(FAIL_CNT, log.getNumberFail());
                }
            }
            // update status DONE if not interrupt
            if (!isInterrupt) {
                this.updateLog(importProcess.executeId, CompletionState.DONE);
            }
            // close input stream
            importProcess.inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Process line.
     *
     * @param importProcess the import process
     * @param entryRecord the entry record
     */
    private void processLine(ImportProcess importProcess, Entry<Integer, List<String>> entryRecord) {
        importProcess.startLine = entryRecord.getKey();
        // check line start read
        if (importProcess.startLine < importProcess.extractCondition.getStartLine().v()) {
            return;
        }
        // get value of record file input
        List<String> record = entryRecord.getValue();
        
        // get data cell from input csv
        List<String> result = new ArrayList<>();
        for (int i = 0; i < record.size(); i++) {
            String value = record.get(i);
            // only fill blank value when it is value's column.
            if (i >= INDEX_BEGIN_COL_VALUE) {
                result.add(this.fillBlankValueIfNeed(value, importProcess.externalBudget.getBudgetAtr()));
            } else {
                result.add(value);
            }
        }
        // check record has data?
        if (CollectionUtil.isEmpty(result)) {
            // update table log with status DONE(完了)
            this.updateLog(importProcess.executeId, CompletionState.DONE);
            return;
        }
        // validate input file csv
        this.validDataLine(importProcess, result);
        
        // insert data master
        this.insertValue(importProcess, result);
    }
    
    /**
     * Fill blank value if need.
     *
     * @param value the value
     * @param budgetAtr the budget atr
     * @return the string
     */
    private String fillBlankValueIfNeed(String value, BudgetAtr budgetAtr) {
        if (!value.isEmpty()) {
            return value;
        }
        // check type and file blank value: 0 or 00:00
        if (budgetAtr == BudgetAtr.TIME) {
            return BLANK_VALUE_TIME;
        }
        return BLANK_VALUE;
    }
    
    /**
     * Insert value.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void insertValue(ImportProcess importProcess, List<String> result) {
        // check continue process line.
        if (importProcess.stopLine) {
            return;
        }
        switch (importProcess.externalBudget.getUnitAtr()) {
            case DAILY:
                this.addExtBudgetDaily(importProcess, result);
                break;
            case BYTIMEZONE:
                // this.addExtBudgetTime(importProcess, result); // extBudgetTime have been deleted
                break;
            default:
                throw new RuntimeException("Not unit atr suitable.");
        }
        boolean isUpdateCaseFail = false;
        int number;
        if (importProcess.stopLine) {
            isUpdateCaseFail = true;
            number = ++importProcess.failCnt;
        } else {
            number = ++importProcess.successCnt;
        }
        // update log
        this.updateLog(isUpdateCaseFail, importProcess.executeId, number);
    }
    
    /**
     * Adds the ext budget daily.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void addExtBudgetDaily(ImportProcess importProcess, List<String> result) {
        String rawValue = result.get(INDEX_BEGIN_COL_VALUE);
        // parse  value
        Long value = this.parseActualValue(rawValue);
        
        // create daily dto
        ExternalBudgetDailyDto dto = ExternalBudgetDailyDto.builder()
                .budgetAtr(importProcess.externalBudget.getBudgetAtr())
                .workplaceId(importProcess.workplaceId)
                .extBudgetCode(importProcess.extractCondition.getExternalBudgetCode())
                .actualDate(importProcess.actualDate)
                .actualValue(value)
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
    
    /**
     * Save data daily.
     *
     * @param <T> the generic type
     * @param importProcess the import process
     * @param domain the domain
     */
    private <T> void saveDataDaily(ImportProcess importProcess, ExternalBudgetDaily<T> domain) {
        // find entity existed ?
        if (!this.extBudgetDailyRepo.isExisted(domain.getWorkplaceId(), GeneralDate.legacyDate(domain.getActualDate()),
                domain.getExtBudgetCode().v())) {
            this.extBudgetDailyRepo.add(domain);
            return;
        }
        // check has override?
        if (importProcess.extractCondition.getIsOverride()) {
            this.extBudgetDailyRepo.update(domain);
            return;
        }
        // insert table ERROR with message id: Msg_167
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(DEFAULT_VALUE)
                .errorContent(this.getMessageById("Msg_167"))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
        
        // marker finish line.
        importProcess.stopLine = true;
    }
    
    /**
     * Valid data line.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void validDataLine(ImportProcess importProcess, List<String> result) {
        // valid column necessary.
        this.validLimitColumn(importProcess, result.size());
        
        // check column 2 (２列目) is date ?
        this.validDateFormat(importProcess, result);
        
        // Check column 1 (1列目) ~ workplace is existed?
        this.validWorkplaceCode(importProcess, result);
        
        // Check actual value by primitive
        this.validActualVal(importProcess, result);
        
        // finish process of line.
        if (importProcess.stopLine) {
            this.updateLog(true, importProcess.executeId, importProcess.failCnt);
            return;
        }
    }
    
    /**
     * Valid limit column.
     *
     * @param importProcess the import process
     * @param numberColInput the number col input
     */
    private void validLimitColumn(ImportProcess importProcess, int numberColInput) {
        UnitAtr unitAtr = importProcess.externalBudget.getUnitAtr();
        // find standard column
        int numberCol = FileUtil.getStandardColumn(unitAtr);
        
        if (numberColInput == numberCol) {
            return;
        }
        String messageIdError = "Msg_162";
        if (numberColInput < numberCol) {
            messageIdError = "Msg_163";
        }
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(DEFAULT_VALUE)
                .errorContent(this.getMessageById(messageIdError))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
        
        importProcess.failCnt++;
        // marker finish process line
        importProcess.stopLine = true;
    }
    
    /**
     * Valid date format.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void validDateFormat(ImportProcess importProcess, List<String> result) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        String inputDate = result.get(INDEX_COLUMN_DATE);
        Boolean isInValidDateFormat = null;
        for (String formatDate : FORMAT_DATES) {
            try {
                isInValidDateFormat = false;
                DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(formatDate);
                LocalDate localDate = LocalDate.parse(inputDate, dateTimeFormat);
                importProcess.actualDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                break;
            } catch (DateTimeParseException e) {
                isInValidDateFormat = true;
            }
        }
        // if has error, insert information error database
        if (isInValidDateFormat) {
            int idxColReal = INDEX_COLUMN_DATE + 1;
            ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                    .executionId(importProcess.executeId)
                    .lineNo(importProcess.startLine)
                    .columnNo(idxColReal)
                    .acceptedDate(inputDate)
                    .errorContent(this.getMessageById("Msg_977"))
                    .build();
            this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
            
            importProcess.failCnt++;
            // marker finish process line
            importProcess.stopLine = true;
        }
    }
    
    /**
     * Valid workplace code.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void validWorkplaceCode(ImportProcess importProcess, List<String> result) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        String companyId = AppContexts.user().companyId();
        String workplaceCode = result.get(INDEX_COLUMN_CODE);
        
        // find workplace existed?
        List<String> lstWpkId = this.workplaceAdapter.findWpkIdList(companyId, workplaceCode, importProcess.actualDate);
        if (!CollectionUtil.isEmpty(lstWpkId)) {
            importProcess.workplaceId = lstWpkId.get(DEFAULT_VALUE);
            return;
        }
        // insert error
        int idxColReal = INDEX_COLUMN_CODE + 1;
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(idxColReal)
                .workplaceCode(workplaceCode)
                .acceptedDate(result.get(INDEX_COLUMN_DATE))
                .errorContent(this.getMessageById("Msg_164", "Com_Workplace"))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
        
        importProcess.failCnt++;
        importProcess.stopLine = true;
    }
    
    /**
     * Valid actual val.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void validActualVal(ImportProcess importProcess, List<String> result) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        for (int i = INDEX_BEGIN_COL_VALUE; i < result.size(); i++) {
            this.validValByPrimitive(importProcess, i, result.get(i));
        }
    }
    
    /**
     * Valid val by primitive.
     *
     * @param importProcess the import process
     * @param columnNo the column no
     * @param value the value
     */
    private void validValByPrimitive (ImportProcess importProcess, int columnNo, String value) {
        int columnRealNo = columnNo + 1;
        String itemName = this.getItemNameById("KSU006_18");
        String mesageId = StringUtils.EMPTY;
        try {
            switch (importProcess.externalBudget.getBudgetAtr()) {
                case TIME:
                	// set message id
                	mesageId = "Msg_978";
                	
                    // convert HH:mm -> minute
                    Long valueTime = this.convertVal(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetTime(valueTime.intValue()), (ex) ->{
                        this.logError(importProcess, columnRealNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case PEOPLE:
                	// set message id
                	mesageId = "Msg_979";
                	
                    Long valuePeople= Long.parseLong(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetNumberPerson(valuePeople.intValue()), (ex) ->{
                        this.logError(importProcess, columnRealNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case MONEY:
                	// set message id
                	mesageId = "Msg_980";
                	
                    Long valueMoney= Long.parseLong(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetMoney(valueMoney.intValue()), (ex) ->{
                        this.logError(importProcess, columnRealNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case NUMERICAL:
                	// set message id
                	mesageId = "Msg_979";
                	
                    Long valueNumerical= Long.parseLong(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetNumericalVal(valueNumerical.intValue()), (ex) ->{
                        this.logError(importProcess, columnRealNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case PRICE:
                	// set message id
                	mesageId = "Msg_981";
                	
                    Long valuePrice= Long.parseLong(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetUnitPrice(valuePrice.intValue()), (ex) ->{
                        this.logError(importProcess, columnRealNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                default:
                    throw new RuntimeException("Not budget atr suitable.");
            }
        } catch (BusinessException e) {
            this.logError(importProcess, columnRealNo, value, this.getMessageById(mesageId));
        } catch (NumberFormatException numberFormat) {
            this.logError(importProcess, columnRealNo, value, this.getMessageById(mesageId));
        }
    }
    
    /**
     * Parses the actual value.
     *
     * @param rawValue the raw value
     * @return the long
     */
    private Long parseActualValue(String rawValue) {
        try {
            return Long.parseLong(rawValue);
        } catch (NumberFormatException e) {
            // case budget atr: TIME
            return this.convertVal(rawValue);
        }
    }
    
    /**
     * Convert val.
     *
     * @param value the value
     * @return the long
     */
    private Long convertVal(String value) {
        String CHARACTER_COLON = ":";
        int numberFirst = 1;
        
        // not have colon
        if (!value.contains(CHARACTER_COLON)) {
            // it's is number: 0 (mean 00:00 -> 00:59), 1 (mean 01:00 -> 01:59), ...  --> #86500
            return Long.parseLong(value);
        }
        // check number colon character.
        // error when format: hh:mm:ss
        else if (StringUtils.countMatches(value, CHARACTER_COLON) > numberFirst) {
            throw new BusinessException(new RawErrorMessage("Invalid format time of value."));
        }
        
        // format time of value: 99:00 (hh:mm)
        String[] timeComponents = value.split(CHARACTER_COLON);
        
        // error when format: hh:
        if (timeComponents.length <= numberFirst) {
        	throw new BusinessException(new RawErrorMessage("Invalid format time of value."));
        }
        
        Integer HOUR = 60;
        Long numberHour = Long.parseLong(timeComponents[0]);
        Long numberMinute = Long.parseLong(timeComponents[1]);
        return numberHour * HOUR + numberMinute;
    }
    
    /**
     * Log error.
     *
     * @param importProcess the import process
     * @param columnNo the column no
     * @param value the value
     * @param errContent the err content
     */
    private void logError(ImportProcess importProcess, int columnNo, String value, String errContent) {
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(columnNo)
                .actualValue(value)
                .errorContent(errContent)
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
        
        if (!importProcess.stopLine) {
            importProcess.failCnt++;
        }
        
        // marker finish line.
        importProcess.stopLine = true;
    }
    
    /**
     * Update log.
     *
     * @param executeId the execute id
     * @param status the status
     */
    private void updateLog(String executeId, CompletionState status) {
        Optional<ExternalBudgetLog> optional = this.extBudgetLogRepo.findExtBudgetLogByExecuteId(executeId);
        if (!optional.isPresent()) {
            throw new RuntimeException("External budget log isn't existed.");
        }
        ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.copy(optional.get());
        extBudgetLogDto.completionState = status;
        extBudgetLogDto.endDateTime = GeneralDateTime.now();
        this.extBudgetLogRepo.update(extBudgetLogDto.toDomain());
    }
    
    /**
     * Update log.
     *
     * @param isUpdateCaseFail the is update case fail
     * @param executeId the execute id
     * @param number the number
     */
    private void updateLog(boolean isUpdateCaseFail, String executeId, Integer number) {
        Optional<ExternalBudgetLog> optional = this.extBudgetLogRepo.findExtBudgetLogByExecuteId(executeId);
        if (!optional.isPresent()) {
            throw new RuntimeException("External budget log isn't existed.");
        }
        ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.copy(optional.get());
        if (isUpdateCaseFail) {
            extBudgetLogDto.numberFail = number;
        } else {
            extBudgetLogDto.numberSuccess = number;
        }
        this.extBudgetLogRepo.update(extBudgetLogDto.toDomain());
    }
    
    /**
     * Gets the item name by id.
     *
     * @param nameId the name id
     * @return the item name by id
     */
    private String getItemNameById(String nameId) {
        return TextResource.localize(nameId);
    }
    
    /**
     * Gets the message by id.
     *
     * @param messageId the message id
     * @param parameters the parameters
     * @return the message by id
     */
    private String getMessageById(String messageId, String... parameters) {
        // no parameter
        if (parameters.length <= DEFAULT_VALUE) {
        	return TextResource.localize(messageId);
        } 
        
        return TextResource.localize(messageId, parameters);
        
    }
    
    /**
     * The Class ImportProcess.
     */
    class ImportProcess {
        
        /** The execute id. */
        String executeId = "";
        
        /** The start line. */
        int startLine = 0;
        
        /** The success cnt. */
        int successCnt = 0;
        
        /** The fail cnt. */
        int failCnt = 0;
        
        /** The stop line. */
        boolean stopLine;
        
        /** The input stream. */
        InputStream inputStream;
        
        /** The external budget. */
        ExternalBudget externalBudget;
        
        /** The extract condition. */
        ExecutionProcessCommand extractCondition;
        
        /** The workplace id. */
        String workplaceId;
        
        /** The actual date. */
        Date actualDate;
    }
}
