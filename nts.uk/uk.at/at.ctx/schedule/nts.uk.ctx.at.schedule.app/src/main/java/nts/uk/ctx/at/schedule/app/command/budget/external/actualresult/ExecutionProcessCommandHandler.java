/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult;

import java.io.InputStream;
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

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.primitive.PrimitiveValueUtil;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetDailyDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetErrorDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto.ExternalBudgetTimeDto;
import nts.uk.ctx.at.schedule.dom.budget.external.BudgetAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDailyRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZone;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZoneRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.WorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error.ExternalBudgetErrorRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log.ExternalBudgetLogRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.ExtBudgetFileCheckService;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service.FileUltil;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ExecutionProcessCommandHandler.
 */
@Stateful
public class ExecutionProcessCommandHandler extends AsyncCommandHandler<ExecutionProcessCommand> {
    
    /** The internationalization. */
    @Inject
    private IInternationalization internationalization;
    
    /** The file check service. */
    @Inject
    private ExtBudgetFileCheckService fileCheckService;
    
    /** The external budget repo. */
    @Inject
    private ExternalBudgetRepository externalBudgetRepo;
    
    /** The ext budget daily repo. */
    @Inject
    private ExternalBudgetDailyRepository extBudgetDailyRepo;
    
    /** The ext budget time repo. */
    @Inject
    private ExternalBudgetTimeZoneRepository extBudgetTimeRepo;
    
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
    private WorkplaceAdapter workplaceAdapter;
    
    /** The format dates. */
    private final List<String> FORMAT_DATES = Arrays.asList("yyyyMMdd", "yyyy/M/d", "yyyy/MM/dd");
    
    /** The default value. */
    private final Integer DEFAULT_VALUE = 0;
    
    /** The max column daily. */
    private final Integer MAX_COLUMN_DAILY = 3;
    
    /** The max column time zone. */
    private final Integer MAX_COLUMN_TIME_ZONE = 50;
    
    /** The index column code. */
    private final Integer INDEX_COLUMN_CODE = 0;
    
    /** The index column date. */
    private final Integer INDEX_COLUMN_DATE = 1;
    
    /** The index begin col value. */
    private final Integer INDEX_BEGIN_COL_VALUE = 2;
    
    /** The max colmn. */
    private final Integer MAX_COLMN = 51;
    
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

        // find all message JP before import
        Map<String, String> mapStringJP = findAllStringJP();
        // valid file format
        this.fileCheckService.validFileIgnoreCharset(command.getFileId(), command.getEncoding(),
                command.getStartLine().v());

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

        String companyId = AppContexts.user().companyId();
        Optional<ExternalBudget> extBudgetOptional = this.externalBudgetRepo.find(companyId,
                command.getExternalBudgetCode());
        if (!extBudgetOptional.isPresent()) {
            throw new RuntimeException("Not external budget setting.");
        }

        // initial import process
        ImportProcess importProcess = new ImportProcess();
        importProcess.executeId = executeId;
        importProcess.inputStream = inputStream;
        importProcess.externalBudget = extBudgetOptional.get();
        importProcess.extractCondition = command;
        importProcess.mapStringJP = mapStringJP;

        // begin process input file
        this.processInput(importProcess, asyncTask);
    }
    
    /**
     * Process input.
     *
     * @param importProcess the import process
     * @param setter the setter
     */
    private <C> void processInput(ImportProcess importProcess, AsyncCommandHandlerContext<C> asyncTask) {
        TaskDataSetter setter = asyncTask.getDataSetter();
        boolean isInterrupt = false;
        try {
            NtsCsvReader csvReader = FileUltil.newCsvReader(importProcess.extractCondition.getEncoding());
            List<NtsCsvRecord> csRecords = csvReader.parse(importProcess.inputStream);
            
            // calculate total record and check has data
            int calTotal = csRecords.size() - importProcess.extractCondition.getStartLine().v() + 1;
            if (calTotal > DEFAULT_VALUE) {
                setter.updateData(TOTAL_RECORD, calTotal);
            }
            
            Iterator<NtsCsvRecord> csvRecordIterator = csRecords.iterator();
            while(csvRecordIterator.hasNext()) {
                /** check has interruption, if is interrupt, update table LOG status interruption (中断)
                 * and end flow (stop process)
                 */
                if (asyncTask.hasBeenRequestedToCancel()) {
                    isInterrupt = true;
                    this.updateLog(importProcess.executeId, CompletionState.INTERRUPTION);
                    asyncTask.finishedAsCancelled();
                    break;
                }
                
                importProcess.stopLine = false;
                this.processLine(importProcess, csvRecordIterator.next());
                
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Process line.
     *
     * @param importProcess the import process
     * @param record the record
     */
    private void processLine(ImportProcess importProcess, NtsCsvRecord record) {
        importProcess.startLine++;
        // check line start read
        if (importProcess.startLine < importProcess.extractCondition.getStartLine().v()) {
            return;
        }
        
        // get data cell from input csv
        List<String> result = new ArrayList<>();
        for (int i = 0; i < MAX_COLMN; i++) {
            Object value = record.getColumn(i);
            if (value != null) {
                result.add(this.fillBlankValueIfNeed(value.toString(), importProcess.externalBudget.getBudgetAtr()));
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
     * Fill value if need.
     *
     * @param value the value
     * @param budgetAtr the budget atr
     * @return the string
     */
    private String fillBlankValueIfNeed(String value, BudgetAtr budgetAtr) {
        if (!value.trim().isEmpty()) {
            return value;
        }
        if (budgetAtr == BudgetAtr.TIME) {
            return BLANK_VALUE_TIME;
        } else {
            return BLANK_VALUE;
        }
    }
    
    /**
     * Insert value.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void insertValue(ImportProcess importProcess, List<String> result) {
        if (importProcess.stopLine) {
            return;
        }
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
        boolean isUpdateCaseFail = false;
        int number;
        if (importProcess.stopLine) {
            isUpdateCaseFail = true;
            number = ++importProcess.failCnt;
        } else {
            number = ++importProcess.successCnt;
        }
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
        Long value = this.parseActualValue(rawValue);
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
                .columnNo(DEFAULT_VALUE)
                .errorContent(importProcess.mapStringJP.get("Msg_167"))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
        
        // marker finish line.
        importProcess.stopLine = true;
    }
    
    /**
     * Adds the ext budget time.
     *
     * @param importProcess the import process
     * @param result the result
     */
    private void addExtBudgetTime(ImportProcess importProcess, List<String> result) {
        Map<Integer, Long> mapValue = new HashMap<>();
        List<String> lstValue = result.subList(INDEX_BEGIN_COL_VALUE, result.size());
        for (int i=0; i<lstValue.size(); i++) {
            String rawValue = lstValue.get(i);
            Long value = this.parseActualValue(rawValue);
            mapValue.put(i, value);
        }
        ExternalBudgetTimeDto dto = ExternalBudgetTimeDto.builder()
                .budgetAtr(importProcess.externalBudget.getBudgetAtr())
                .workplaceId(importProcess.workplaceId)
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
    
    /**
     * Save data time.
     *
     * @param <T> the generic type
     * @param importProcess the import process
     * @param domain the domain
     */
    private <T> void saveDataTime(ImportProcess importProcess, ExternalBudgetTimeZone<T> domain) {
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
                .columnNo(DEFAULT_VALUE)
                .errorContent(importProcess.mapStringJP.get("Msg_167"))
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
        this.validUnitAtr(importProcess, result.size());
        
        // check column 2 (２列目) is date ?
        this.validDateFormat(importProcess, result);
        
        // Check column 1 (1列目) is workplace code?
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
     * Valid unit atr.
     *
     * @param importProcess the import process
     * @param numberColInput the number col input
     */
    private void validUnitAtr(ImportProcess importProcess, int numberColInput) {
        UnitAtr unitAtr = importProcess.externalBudget.getUnitAtr();
        int numberCol;
        switch (unitAtr) {
            case DAILY:
                numberCol = MAX_COLUMN_DAILY;
                break;
            case BYTIMEZONE:
                numberCol = MAX_COLUMN_TIME_ZONE;
                break;
            default:
                throw new RuntimeException("Not unit atr suitable.");
        }
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
                .errorContent(importProcess.mapStringJP.get(messageIdError))
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
     * @param inputDate the input date
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
                importProcess.actualDate = new SimpleDateFormat(formatDate).parse(inputDate);
                break;
            } catch (ParseException e) {
                isInValidDateFormat = true;
            }
        }
        if (isInValidDateFormat) {
            int idxColReal = INDEX_COLUMN_DATE + 1;
            ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                    .executionId(importProcess.executeId)
                    .lineNo(importProcess.startLine)
                    .columnNo(idxColReal)
                    .acceptedDate(inputDate)
                    .errorContent("Invalid format date.") // Not has message id
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
     * @param workplaceCode the workplace code
     */
    private void validWorkplaceCode(ImportProcess importProcess, List<String> result) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        String companyId = AppContexts.user().companyId();
        String workplaceCode = result.get(INDEX_COLUMN_CODE);
        List<String> lstWpkId = this.workplaceAdapter.findWpkIdList(companyId, workplaceCode, importProcess.actualDate);
        if (!CollectionUtil.isEmpty(lstWpkId)) {
            importProcess.workplaceId = lstWpkId.get(DEFAULT_VALUE);
            return;
        }
        // insert error
        ExternalBudgetErrorDto extBudgetErrorDto = ExternalBudgetErrorDto.builder()
                .executionId(importProcess.executeId)
                .lineNo(importProcess.startLine)
                .columnNo(INDEX_COLUMN_CODE)
                .workplaceCode(workplaceCode)
                .errorContent(importProcess.mapStringJP.get("Msg_164"))
                .build();
        this.extBudgetErrorRepo.add(extBudgetErrorDto.toDomain());
        
        importProcess.failCnt++;
        importProcess.stopLine = true;
    }
    
    /**
     * Valid actual val.
     *
     * @param importProcess the import process
     * @param lstValue the lst value
     */
    private void validActualVal(ImportProcess importProcess, List<String> lstValue) {
        // finish process of line.
        if (importProcess.stopLine) {
            return;
        }
        for (int i = INDEX_BEGIN_COL_VALUE; i < lstValue.size(); i++) {
            this.validValByPrimitive(importProcess, i + 1, lstValue.get(i));
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
        String itemName = importProcess.mapStringJP.get("KSU006_18");
        try {
            switch (importProcess.externalBudget.getBudgetAtr()) {
                case TIME:
                    // convert HH:mm -> minute
                    Long valueTime = this.convertVal(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetTime(valueTime), (ex) ->{
                        this.logError(importProcess, columnNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case PEOPLE:
                    Integer valuePeople= Integer.parseInt(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetNumberPerson(valuePeople), (ex) ->{
                        this.logError(importProcess, columnNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case MONEY:
                    Integer valueMoney= Integer.parseInt(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetMoney(valueMoney), (ex) ->{
                        this.logError(importProcess, columnNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case NUMERICAL:
                    Integer valueNumerical= Integer.parseInt(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetNumericalVal(valueNumerical), (ex) ->{
                        this.logError(importProcess, columnNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                case PRICE:
                    Integer valuePrice= Integer.parseInt(value);
                    PrimitiveValueUtil.createWithValidate(() -> new ExtBudgetUnitPrice(valuePrice), (ex) ->{
                        this.logError(importProcess, columnNo, value, ex.getErrorMessage(itemName));
                    });
                    break;
                default:
                    throw new RuntimeException("Not budget atr suitable.");
            }
        } catch (BusinessException e) {
            this.logError(importProcess, columnNo, value, e.getMessage());
        } catch (NumberFormatException numberFormat) {
            this.logError(importProcess, columnNo, value, "Invalid format number.");
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
        if (!value.contains(CHARACTER_COLON)) {
            throw new BusinessException(new RawErrorMessage("Invalid format time of value."));
        }
        String[] arr = value.split(CHARACTER_COLON);
        Integer HOUR = 60;
        Long numberHour = Long.parseLong(arr[0]);
        Long numberMinute = Long.parseLong(arr[1]);
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
        if (optional.isPresent()) {
            ExternalBudgetLogDto extBudgetLogDto = ExternalBudgetLogDto.copy(optional.get());
            extBudgetLogDto.completionState = status;
            extBudgetLogDto.endDateTime = GeneralDateTime.now();
            this.extBudgetLogRepo.update(extBudgetLogDto.toDomain());
        }
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
    
    /**
     * Find all string JP.
     *
     * @return the map
     */
    private Map<String, String> findAllStringJP() {
        Map<String, String> mapMessage = new HashMap<>();
        String nameId = "KSU006_18";
//        Optional<String> optional = this.internationalization.getItemName(nameId);
//        mapMessage.put(nameId, optional.isPresent() ? optional.get() : (nameId + " is not found."));
        mapMessage.put(nameId, nameId + " is not found.");
        
        List<String> lstMsgId = Arrays.asList("Msg_162", "Msg_163", "Msg_164", "Msg_167");
        for (String msgId : lstMsgId) {
//            mapMessage.put(msgId, this.getMessageById(msgId));
            mapMessage.put(msgId, msgId + " is not found.");
        }
        return mapMessage;
    }
    
    /**
     * Gets the message by id.
     *
     * @param messageId the message id
     * @return the message by id
     */
    private String getMessageById(String messageId) {
        String errorContent = messageId + " is not found.";
        Optional<String> optional = this.internationalization.getRawMessage(messageId);
        if (optional.isPresent()) {
            errorContent = optional.get();
        }
        return errorContent;
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
        
        /** The map string JP. */
        Map<String, String> mapStringJP;
        
        /** The workplace id. */
        String workplaceId;
        
        /** The actual date. */
        Date actualDate;
    }
}
