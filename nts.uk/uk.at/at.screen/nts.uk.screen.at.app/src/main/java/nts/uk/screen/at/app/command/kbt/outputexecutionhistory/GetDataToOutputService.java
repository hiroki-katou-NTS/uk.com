package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.employee.dto.EmployeeBasicInfoExportDto;
import nts.uk.ctx.at.function.app.find.employee.dto.EmployeeInfoExport;
import nts.uk.ctx.at.function.dom.adapter.EmployeeHistWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.EmployeeBasicInfoFnImport;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.EmployeeInfoImport;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionName;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.storage.ResultState;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.shared.dom.person.PersonAdaptor;
import nts.uk.ctx.at.shared.dom.person.PersonImport;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.query.app.exi.ExacErrorLogQueryDto;
import nts.uk.query.app.exi.ExacErrorLogQueryFinder;
import nts.uk.query.app.exo.ExternalOutLogQueryDto;
import nts.uk.query.app.exo.ExternalOutLogQueryFinder;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.csv.CSVReportGenerator;
import nts.uk.shr.infra.file.csv.CsvReportWriter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Stateless
public class GetDataToOutputService extends ExportService<Object> {

    private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV1 = Arrays.asList("KBT002_289", "KBT002_7",
            "KBT002_8", "KBT002_202", "KBT002_203", "KBT002_204",
            "KBT002_205", "KBT002_206", "KBT002_207", "KBT002_220");

    private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV2 = Arrays.asList("KBT002_289", "KBT002_7",
            "KBT002_8", "KBT002_201", "KBT002_202", "KBT002_203",
            "KBT002_204", "KBT002_205", "KBT002_206", "KBT002_334", "KBT002_207");

    private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV3 = Arrays.asList("KBT002_289", "KBT002_7",
            "KBT002_8", "KBT002_201", "KBT002_184", "KBT002_292", "KBT002_186", "KBT002_187");

    private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV4 = Arrays.asList("KBT002_289", "KBT002_7",
            "KBT002_8", "KBT002_316", "KBT002_317", "KBT002_318",
            "KBT002_319", "KBT002_320");

    private static final List<String> LST_NAME_ID_HEADER_TABLE_CSV5 = Arrays.asList("KBT002_289", "KBT002_7",
            "KBT002_8", "KBT002_321", "KBT002_184", "KBT002_292",
            "KBT002_186", "KBT002_322", "KBT002_323", "KBT002_187");

    private static final String CSV_EXTENSION = ".csv";
    private static final String ZIP_EXTENSION = ".zip";
    private static final String FILE_NAME_ZIP = "KBT002_更新処理自動実行の実行履歴_";
    private static final String FILE_NAME_CSV1 = "KBT002_更新処理自動実行の実行状況";
    private static final String FILE_NAME_CSV2 = "KBT002_実行項目の詳細状況";
    private static final String FILE_NAME_CSV3 = "KBT002_実行項目のエラー詳細";
    private static final String FILE_NAME_CSV4 = "KBT002_実行項目のエラー詳細（外部受入）";
    private static final String FILE_NAME_CSV5 = "KBT002_実行項目のエラー詳細（外部出力";


    @Inject
    private ProcessExecutionLogHistRepository updateProAutoExeRepo;

    @Inject
    private ProcessExecutionRepository procExecRepo;

    @Inject
    private ScheduleErrorLogRepository scheduleErrorLogRepo;

    @Inject
    private ErrMessageInfoRepository errMessageInfoRepo;

    @Inject
    private AggrPeriodInforRepository errorInfoRepo;

    @Inject
    private AppDataInfoDailyRepository appDataInfoDailyRepo;

    @Inject
    private AppDataInfoMonthlyRepository appDataInfoMonthlyRepo;

    @Inject
    private SyEmployeeFnAdapter syEmployeeFnAdapter;

    @Inject
    private EmployeeHistWorkRecordAdapter employeeHistWorkRecordAdapter;

    @Inject
    private PersonAdaptor personAdaptor;

    @Inject
    private CSVReportGenerator generator;

    @Inject
    private ApplicationTemporaryFileFactory applicationTemporaryFileFactory;

    @Inject
    private ExternalOutLogQueryFinder externalOutLogQueryFinder;

    @Inject
    private ExacErrorLogQueryFinder exacErrorLogQueryFinder;

    @Override
    protected void handle(ExportServiceContext<Object> context) {
        GetDataToOutputCommand command = (GetDataToOutputCommand) context.getQuery();
        // 出力するデータを取得する
        UpdateProcessAutoRunDataDto updateProAutoRunData = this.getDataOutput(command);
        // Step 取得した「更新処理自動実行データ」からcsvファイルを作成する
        FileGeneratorContext generatorContext = context.getGeneratorContext();
        // create file csv
        ResultState resultState = this.saveAllFileCSV(generatorContext, updateProAutoRunData);
        // Step 作成したcsvファイルをまとめて圧縮する
        // Step 圧縮したファイルをダウンロードする
        if (resultState.equals(ResultState.NORMAL_END)) {
            this.zipAllFileCSV(generatorContext);
        }
    }

    private ResultState saveAllFileCSV(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRunData) {
        try {
            this.generalFileCSV1(generatorContext, updateProAutoRunData);
            this.generalFileCSV2(generatorContext, updateProAutoRunData);
            this.generalFileCSV3(generatorContext, updateProAutoRunData);
            this.generalFileCSV4(generatorContext, updateProAutoRunData);
            this.generalFileCSV5(generatorContext, updateProAutoRunData);
            return ResultState.NORMAL_END;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultState.ABNORMAL_END;
        }
    }


    /**
     * Gets the data output.
     * ドメインモデル「更新処理自動実行ログ履歴」を取得する
     *
     * @param command the command
     * @return the data output
     */
    private UpdateProcessAutoRunDataDto getDataOutput(GetDataToOutputCommand command) {
        // Step ドメインモデル「更新処理自動実行ログ履歴」を取得する
        List<ProcessExecutionLogHistory> listProcessExeHistory = this.updateProcessAutomaticExecutionLogHistory(command);

        //「更新処理自動実行ログ履歴」取得できたか
        // 取得できない
        if (listProcessExeHistory.isEmpty()) {
            // Step エラーメッセージ（Msg_37）を表示する
            throw new BusinessException("Msg_37");
        }

        // 取得できた
        // Step ドメインモデル「更新処理自動実行」を取得する
        String companyId = AppContexts.user().companyId();
        List<ProcessExecution> lisProcessExecution = this.acquireTheDomainModel(companyId, listProcessExeHistory);

        // Step OUTPUT「更新処理自動実行データ」を作成する
        UpdateProcessAutoRunDataDto updateProAutoRunData = new UpdateProcessAutoRunDataDto(
                lisProcessExecution,
                Collections.emptyList(),
                Collections.emptyList(),
                command.isExportEmployeeName());

        List<ExecutionLogDetailDto> lstLogDetail = new ArrayList<>();
//        // sort ProcessExecutionLogHistory in  lastExecDateTime by ASC
//        listProcessExeHistory.sort((o1, o2) -> o1.getLastExecDateTime().compareTo(o2.getLastEndExecDateTime()));

        // 取得した「更新処理自動実行ログ履歴」をループする - Loop the acquired 「更新処理自動実行ログ履歴」
        for (ProcessExecutionLogHistory logHistory : listProcessExeHistory) {
            // 処理中の「更新処理自動実行ログ履歴．全体の業務エラー状態」を確認する
            // 全体の業務エラー状態 = true
            if (logHistory.getErrorBusiness().isPresent()) {
                if (logHistory.getErrorBusiness().get()) {
//                  // Sort list ExecutionTaskLog by enum
//                  logHistory.getTaskLogList().sort(Comparator.comparingInt(taskLog -> taskLog.getProcExecTask().value));

                    // Step 処理中の「更新処理自動実行ログ履歴．各処理の終了状態．業務エラー状態」 = true の項目を確認する
                    if (!logHistory.getTaskLogList().isEmpty()) {

                        for (ExecutionTaskLog taskLog : logHistory.getTaskLogList()) {
                        	if(taskLog.getErrorBusiness().isPresent()) {
                        		if (taskLog.getErrorBusiness().get()) {

                                    String execIdByLogHistory = logHistory.getExecId();
                                    ExecutionLogDetailDto execLogDetail = new ExecutionLogDetailDto();
                                    execLogDetail.setProcessExecLogHistory(logHistory);

                                    switch (taskLog.getProcExecTask()) {
                                        // Case 1 ドメインモデル「スケジュール作成エラーログ」を取得する
                                        case SCH_CREATION:
//                                            execLogDetail.setScheduleErrorLog(this.scheduleErrorLogRepo.findByExecutionId(execIdByLogHistory));
                                            break;
                                        // Case 2 ドメインモデル「エラーメッセージ情報」を取得する
                                        case DAILY_CREATION:
                                        case MONTHLY_AGGR:
                                        case RFL_APR_RESULT:
                                        case DAILY_CALCULATION:
                                            execLogDetail.setErrMessageInfo(this.errMessageInfoRepo.getAllErrMessageInfoByEmpID(execIdByLogHistory));
                                            break;
                                        // Case 3 ドメインモデル「任意期間集計エラーメッセージ情報」を取得する
                                        case AGGREGATION_OF_ARBITRARY_PERIOD:
                                            execLogDetail.setAggrPeriodInfor(this.errorInfoRepo.findAll(execIdByLogHistory));
                                            break;
                                        // Case 4 ドメインモデル「承認中間データエラーメッセージ情報（日別実績）」を取得する
                                        case APP_ROUTE_U_DAI:
                                            execLogDetail.setAppDataInfoDailies(this.appDataInfoDailyRepo.getAppDataInfoDailyByExeID(execIdByLogHistory));
                                            break;
                                        // Case 5 ドメインモデル「承認中間データエラーメッセージ情報（月別実績）」を取得する
                                        case APP_ROUTE_U_MON:
                                            execLogDetail.setAppDataInfoMonthlies(this.appDataInfoMonthlyRepo.getAppDataInfoMonthlyByExeID(execIdByLogHistory));
                                            break;
                                        // Case 6 ドメインモデル「外部受入エラーログ」のデータを取得する
                                        case EXTERNAL_ACCEPTANCE:
                                            execLogDetail.setExacErrorLogImports(this.exacErrorLogQueryFinder.getExacErrorLogByProcessId(execIdByLogHistory));
                                            break;
                                        // Case 7 ドメインモデル「外部出力結果ログ」のデータを取得する
                                        case EXTERNAL_OUTPUT:
                                           execLogDetail.setExternalOutLogImports(this.externalOutLogQueryFinder.getExternalOutLogById(companyId,execIdByLogHistory,0));
                                            break;
                                        default:
                                            break;
                                    }
                                    lstLogDetail.add(execLogDetail);
                                }
                        	}
                        	continue;
                        }
                    }
                }
            }
            continue;
        }

        List<String> empIds = new ArrayList<>();
        // get employeeId by logDetail
        for(ExecutionLogDetailDto logDetail : lstLogDetail){
            if(logDetail.getErrMessageInfo() != null){
                empIds.addAll(logDetail.getErrMessageInfo().stream().map(err -> err.getEmployeeID()).collect(Collectors.toList()));
            }
            if(logDetail.getScheduleErrorLog() != null){
                empIds.addAll(logDetail.getErrMessageInfo().stream().map(err -> err.getEmployeeID()).collect(Collectors.toList()));
            }
            if(logDetail.getAggrPeriodInfor() != null){
                empIds.addAll(logDetail.getAggrPeriodInfor().stream().map(agg -> agg.getMemberId()).collect(Collectors.toList()));
            }
            if(logDetail.getAppDataInfoMonthlies() != null){
                empIds.addAll(logDetail.getAppDataInfoMonthlies().stream().map(appM -> appM.getEmployeeId()).collect(Collectors.toList()));
            }
            if(logDetail.getAppDataInfoDailies() != null ){
                empIds.addAll(logDetail.getAppDataInfoDailies().stream().map(appD -> appD.getEmployeeId()).collect(Collectors.toList()));
            }
        }
        // remove duplicate employeeId by empIds
        List<String> deDupEmpIds = empIds.stream().distinct().collect(Collectors.toList());
        // Step 社員ID(List)から個人社員基本情報を取得
        List<EmployeeBasicInfoExportDto> empBasicIf = this.obtainBasicPersonalEmployeeInformation(deDupEmpIds);

        // Step OUTPUT「更新処理自動実行データ」を更新して返す
        // Convert lst EmployeeBasicInfoExportDto ->  lst EmployeeSearchDto
        List<EmployeeInfoImport> empInfoResult = empBasicIf.stream()
                .map(empBs -> new EmployeeInfoImport(empBs.getEmployeeId(), empBs.getEmployeeCode(), empBs.getBusinessName()))
                .collect(Collectors.toList());

        updateProAutoRunData.setLstEmployeeSearch(empInfoResult);
        // Step 「実行ログ詳細」を作成してOUTPUT「更新処理自動実行データ」に追加する
        updateProAutoRunData.setLstExecLogDetail(lstLogDetail);

        return updateProAutoRunData;
    }

    private List<ProcessExecutionLogHistory> updateProcessAutomaticExecutionLogHistory(GetDataToOutputCommand commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        GeneralDateTime startDate = commandHandlerContext.getStartDate();
        GeneralDateTime endDate = commandHandlerContext.getEndDate();
        List<ProcessExecutionLogHistory> result = this.updateProAutoExeRepo.getByCompanyIdAndDateAndEmployeeName(companyId, startDate, endDate);
        return result;
    }

    private List<ProcessExecution> acquireTheDomainModel(String companyId, List<ProcessExecutionLogHistory> processExeHistory) {
        List<ExecutionCode> execItemCd = processExeHistory.stream().map(c -> c.getExecItemCd()).collect(Collectors.toList());
        List<ProcessExecution> listProcessExecution = new ArrayList<>();
        List<ProcessExecution> processExecution = this.procExecRepo.getProcessExecutionByCompanyId(companyId);
        processExecution.forEach(exe -> {
            execItemCd.forEach(code -> {
                if (exe.getExecItemCd().v().equals(code.v())) {
                    listProcessExecution.add(exe);
                }
            });
        });
        return listProcessExecution;
    }

    private List<EmployeeBasicInfoExportDto> obtainBasicPersonalEmployeeInformation(List<String> empIds) {
        List<EmployeeBasicInfoExportDto> result = new ArrayList<>();
        // Step ドメインモデル「社員データ管理情報」を取得する
        List<EmployeeBasicInfoFnImport> employeeInfoImports = this.syEmployeeFnAdapter.findBySIds(empIds);

        // Step ドメインモデル「社員データ管理情報」が取得できたかどうかチェックする
        // 取得できなかった場合（データ件数＝０件）
        if (employeeInfoImports.isEmpty()) {
            // TODO
            throw new BusinessException("list EmployeeBasicInfoFnImport empty !");
        }
        // 取得できた場合（データ件数≠０件）
        else {
            // Step ドメインモデル「所属会社履歴（社員別）」を取得する
            //get list employeeId
            List<String> lstEmpId = employeeInfoImports.stream().map(em -> em.getEmployeeId()).collect(Collectors.toList());

//            List<AffCompanyHistImport> affCompanyHistImports =
//                    this.employeeHistWorkRecordAdapter.getWplByListSidAndPeriod(lstEmpId, new DatePeriod(GeneralDate.min(), GeneralDate.max()));

            // get list personId
            List<String> lstPersonId = employeeInfoImports.stream().map(em -> em.getPId()).collect(Collectors.toList());
            // Step アルゴリズム「個人IDから個人情報を取得」実行する
            List<EmployeeInfoExport> lstEmpSearch = this.getPersonalInformation(lstPersonId);
            // 取得できなかった場合（データ件数＝０件）
            if (lstEmpSearch.isEmpty()) {
                // TODO
                throw new BusinessException("list EmployeeInfoExport empty !");
            }
            // 取得できた場合（データ件数≠０件)
            lstEmpSearch.forEach(person -> {
                Optional<EmployeeBasicInfoFnImport> fnImport = employeeInfoImports.stream().
                        filter(em -> person.getPersonId().equals(em.getPId())).findAny();

                EmployeeBasicInfoExportDto empBasicInfo = new EmployeeBasicInfoExportDto();
                empBasicInfo.setPersonId(person.getPersonId());
                empBasicInfo.setGender(person.getGender());
                empBasicInfo.setBirthDay(person.getBirthDay());
                empBasicInfo.setBusinessName(person.getBusinessName());
                empBasicInfo.setEmployeeCode(fnImport
                        .map(item -> item.getEmployeeCode())
                        .orElse(null));
                empBasicInfo.setEmployeeId(fnImport
                        .map(item -> item.getEmployeeId())
                        .orElse(null));
                empBasicInfo.setEntryDate(fnImport
                        .map(item -> item.getEntryDate())
                        .orElse(null));
                empBasicInfo.setRetiredDate(fnImport
                        .map(item -> item.getRetiredDate())
                        .orElse(null));
                result.add(empBasicInfo);
            });
        }
        return result;
    }

    private List<EmployeeInfoExport> getPersonalInformation(List<String> personId) {
        //Step ドメンモデル「個人」を取得
        List<EmployeeInfoExport> result = new ArrayList<>();

        List<PersonImport> lstPersonImport = this.personAdaptor.findByPids(personId);
        if (lstPersonImport.isEmpty()) {
            // Step 終了状態：個人情報取得失敗
            // TODO
            throw new BusinessException("list PersonImport empty !");
        } else {
            // Step 終了状態：成功
            lstPersonImport.forEach(ps -> {
                EmployeeInfoExport empSearchDto = new EmployeeInfoExport();
                empSearchDto.setPersonId(ps.getPersonId());
                empSearchDto.setGender(ps.getGender());
                empSearchDto.setBirthDay(ps.getBirthDate());
                empSearchDto.setBusinessName(ps.getPersonNameGroup().getBusinessName());
                result.add(empSearchDto);
            });
        }
        return result;
    }

    private void generalFileCSV1(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
        try {
            List<String> headerCSV1 = this.getTextHeaderCsv1();
            CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV1 + CSV_EXTENSION, headerCSV1, "UTF-8");
            for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                ExecutionLogDetailDto logDetail = updateProAutoRuns.getLstExecLogDetail().get(i);
                ProcessExecution exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                ProcessExecutionLogHistory proHis = logDetail.getProcessExecLogHistory();

                Map<String, Object> rowCSV1 = new HashMap<>();
                rowCSV1.putAll(this.saveHeaderCSV(rowCSV1, proHis.getExecId(), exeCode.getExecItemCd(), proHis.getExecItemCd(), exeCode.getExecItemName()));

                String lastExecDateTime = proHis.getLastExecDateTime().map(item -> item.toString("yyyy-MM-dd HH:mm:ss")).orElse(null);
                String lastEndExecDateTime = proHis.getLastEndExecDateTime().map(item -> item.toString("yyyy-MM-dd HH:mm:ss")).orElse(null);
                rowCSV1.put(headerCSV1.get(3), proHis.getLastExecDateTime() != null ? lastExecDateTime : null);
                rowCSV1.put(headerCSV1.get(4), proHis.getLastEndExecDateTime() != null ? lastEndExecDateTime : null);

                rowCSV1.put(headerCSV1.get(5), this.getHourByGetLastExecDateTimeAndGetLastEndExecDateTime(proHis.getLastExecDateTime().orElse(null), proHis.getLastEndExecDateTime().orElse(null)));
                rowCSV1.put(headerCSV1.get(6), proHis.getOverallStatus().map(item -> item.value)
                        .orElse(null));
                rowCSV1.put(headerCSV1.get(7), proHis.getErrorSystem().isPresent()
                        ? null
                        : (proHis.getErrorSystem().get() ? TextResource.localize("KBT002_290") : TextResource.localize("KBT002_291")));
                rowCSV1.put(headerCSV1.get(8), proHis.getErrorBusiness().isPresent()
                        ? null
                        : (proHis.getErrorBusiness().get() ? TextResource.localize("KBT002_290") : TextResource.localize("KBT002_291")));
                rowCSV1.put(headerCSV1.get(9), proHis.getOverallError()
                        .map(item -> item.name)
                        .orElse(null));
                csv.writeALine(rowCSV1);
                continue;
            }

            csv.destroy();
        } catch (Exception e) {

        }
    }

    private void generalFileCSV2(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
        try {
            List<String> headerCSV2 = this.getTextHeaderCsv2();
            CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV2 + CSV_EXTENSION, headerCSV2, "UTF-8");
            for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                ExecutionLogDetailDto logDetail = updateProAutoRuns.getLstExecLogDetail().get(i);
                ProcessExecution exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                ProcessExecutionLogHistory proHis = logDetail.getProcessExecLogHistory();

                if(!proHis.getTaskLogList().isEmpty()){
                    for(ExecutionTaskLog taskLog : proHis.getTaskLogList()){
                        Map<String, Object> rowCSV2 = new HashMap<>();
                        rowCSV2.putAll(this.saveHeaderCSV(rowCSV2, proHis.getExecId(), exeCode.getExecItemCd(), proHis.getExecItemCd(), exeCode.getExecItemName()));
                        rowCSV2.put(headerCSV2.get(3), taskLog.getProcExecTask() != null ? taskLog.getProcExecTask().name : null);

                        String lastExecDateTime = taskLog.getLastExecDateTime().map(item -> item.toString("yyyy-MM-dd HH:mm:ss")).orElse(null);
                        String lastEndExecDateTime = taskLog.getLastEndExecDateTime().map(item -> item.toString("yyyy-MM-dd HH:mm:ss")).orElse(null);
                        rowCSV2.put(headerCSV2.get(4), taskLog.getLastExecDateTime().isPresent() ? lastExecDateTime : null);
                        rowCSV2.put(headerCSV2.get(5), taskLog.getLastEndExecDateTime().isPresent() ? lastEndExecDateTime : null);
                        rowCSV2.put(headerCSV2.get(6), this.getHourByGetLastExecDateTimeAndGetLastEndExecDateTime(proHis.getLastExecDateTime().orElse(null), proHis.getLastEndExecDateTime().orElse(null)));
                        rowCSV2.put(headerCSV2.get(7), proHis.getOverallStatus()
                                .map(item -> item.name)
                                .orElse(null));
                        rowCSV2.put(headerCSV2.get(8), proHis.getErrorSystem().isPresent()
                                ? null
                                : proHis.getErrorSystem().get() ? TextResource.localize("KBT002_290") : TextResource.localize("KBT002_291"));
                        rowCSV2.put(headerCSV2.get(9), taskLog.getSystemErrorDetails());
                        rowCSV2.put(headerCSV2.get(10), proHis.getErrorBusiness().isPresent()
                                ? null
                                : proHis.getErrorBusiness().get() ? TextResource.localize("KBT002_290") : TextResource.localize("KBT002_291"));
                        csv.writeALine(rowCSV2);
                    }
                }
                continue;
            }
            csv.destroy();
        } catch (Exception e) {

        }
    }

    private void generalFileCSV3(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
    	
    	boolean checkEmptyTaskLogList = false;
    	for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
    		if(!updateProAutoRuns.getLstExecLogDetail().get(i).getProcessExecLogHistory().getTaskLogList().isEmpty()) {
    			checkEmptyTaskLogList = true;
        	}
    	}
    	
    	if(checkEmptyTaskLogList) {
    		try {
                List<String> headerCSV3 = this.getTextHeaderCsv3(updateProAutoRuns.isExportEmpName());
                CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV3 + CSV_EXTENSION, headerCSV3, "UTF-8");
                for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                    ExecutionLogDetailDto logDetail = updateProAutoRuns.getLstExecLogDetail().get(i);
                    ProcessExecution exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                    EmployeeInfoImport empImport = updateProAutoRuns.getLstEmployeeSearch().get(i);
                    ProcessExecutionLogHistory proHis = logDetail.getProcessExecLogHistory();

                    if(!proHis.getTaskLogList().isEmpty()){
                        
                        for(ExecutionTaskLog taskLog : proHis.getTaskLogList()){
                        	int j = 0;
                            if(taskLog.getErrorBusiness().isPresent()){
                                if (taskLog.getErrorBusiness().get()) {
                                    // sort list scheduleErrorLog in date by asc
                                	ScheduleErrorLog schLog = new ScheduleErrorLog(null,null,null,null);
                                	ErrMessageInfo errLog = new ErrMessageInfo(null,null, null, null, null, null);
                                	AggrPeriodInfor aggPeInfo = new AggrPeriodInfor(null, null, null,null,null);
                                	AppDataInfoDaily appDaily = new AppDataInfoDaily(null, null, null);
                                	AppDataInfoMonthly appMonthly = new AppDataInfoMonthly(null,null,null);
                                    if(logDetail.getScheduleErrorLog() != null){
                                        logDetail.getScheduleErrorLog().sort(Comparator.comparing(ScheduleErrorLog::getDate));
                                        schLog = logDetail.getScheduleErrorLog().get(j);
                                    }
                                    if(logDetail.getAggrPeriodInfor() != null){
                                        logDetail.getAggrPeriodInfor().sort(Comparator.comparing(AggrPeriodInfor::getProcessDay));
                                        aggPeInfo = logDetail.getAggrPeriodInfor().get(j);
                                    }
                                    if(logDetail.getErrMessageInfo() != null){
                                        logDetail.getErrMessageInfo().sort(Comparator.comparing(ErrMessageInfo::getDisposalDay));
                                        errLog = logDetail.getErrMessageInfo().get(j);
                                    }

                                    if(logDetail.getAppDataInfoDailies() != null) {
                                        appDaily = logDetail.getAppDataInfoDailies().get(j);
                                    }
                                    
                                    if(logDetail.getAppDataInfoMonthlies() != null) {
                                    	appMonthly = logDetail.getAppDataInfoMonthlies().get(j);
                                    }
                                    
                                    Map<String, Object> rowCSV3 = new HashMap<>();

                                    rowCSV3.putAll(this.saveHeaderCSV(rowCSV3, proHis.getExecId(), exeCode.getExecItemCd(), proHis.getExecItemCd(), exeCode.getExecItemName()));
                                    rowCSV3.put(headerCSV3.get(3), taskLog.getProcExecTask() != null ? taskLog.getProcExecTask().name : null);

                                    if (checkEqualId(empImport.getSid(), schLog.getEmployeeId())
                                            || checkEqualId(empImport.getSid(), aggPeInfo.getMemberId())
                                            || checkEqualId(empImport.getSid(), appDaily.getEmployeeId())
                                            || checkEqualId(empImport.getSid(), appMonthly.getEmployeeId())) {

                                        rowCSV3.put(headerCSV3.get(4), empImport.getScd());
                                        // check valid whether to output the employee name
                                        if (updateProAutoRuns.isExportEmpName()) {
                                            rowCSV3.put(headerCSV3.get(5), empImport.getBussinessName());
                                        }

                                    }
                                    switch (taskLog.getProcExecTask()) {
                                        case SCH_CREATION:
                                            rowCSV3.put(headerCSV3.get(6), schLog.getDate() != null ? schLog.getDate().toString() : null);
                                            rowCSV3.put(headerCSV3.get(7), schLog.getErrorContent() != null ? schLog.getErrorContent() : null);
                                            break;
                                        case DAILY_CREATION:
                                        case DAILY_CALCULATION:
                                        case RFL_APR_RESULT:
                                        case MONTHLY_AGGR:
                                            rowCSV3.put(headerCSV3.get(6), errLog.getDisposalDay() != null ? errLog.getDisposalDay().toString() : null);
                                            rowCSV3.put(headerCSV3.get(7), errLog.getMessageError() != null ? errLog.getMessageError().v() : null );
                                            break;
                                        case AGGREGATION_OF_ARBITRARY_PERIOD:
                                            rowCSV3.put(headerCSV3.get(6), aggPeInfo.getProcessDay() != null ? aggPeInfo.getProcessDay().toString() : null);
                                            rowCSV3.put(headerCSV3.get(7), aggPeInfo.getErrorMess() != null ? aggPeInfo.getErrorMess().v() : null);
                                            break;
                                        case APP_ROUTE_U_DAI:
                                            rowCSV3.put(headerCSV3.get(7), appDaily.getErrorMessage() != null ? appDaily.getErrorMessage().v() : null);
                                            break;
                                        case APP_ROUTE_U_MON:
                                            rowCSV3.put(headerCSV3.get(7), appMonthly.getErrorMessage() != null ? appMonthly.getErrorMessage().v() : null);
                                            break;
                                        default:
                                            break;
                                    }
                                    csv.writeALine(rowCSV3);
                                }
                            }
                            j++;
                        }
                    }
                    continue;
                }
                csv.destroy();
            } catch (Exception e) {

            }
    	}
    	
    }

    private void generalFileCSV4(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
    	
    	boolean checkListExacErrorLogEmpty = false;
    	for(int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
    		List<ExacErrorLogQueryDto> exacErrorLogQueryDtos = updateProAutoRuns.getLstExecLogDetail().get(i).getExacErrorLogImports();
    		if(exacErrorLogQueryDtos != null) {
    			checkListExacErrorLogEmpty = true;
    		}
    	}
    	if(checkListExacErrorLogEmpty) {
    		try {
                List<String> headerCSV4 = this.getTextHeaderCsv4();
                CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV4 + CSV_EXTENSION, headerCSV4, "UTF-8");
                for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                	List<ExacErrorLogQueryDto> lstExacErrorLog = updateProAutoRuns.getLstExecLogDetail().get(i).getExacErrorLogImports();
                    if(lstExacErrorLog != null){
                    	ProcessExecution exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                        ProcessExecutionLogHistory proHis = updateProAutoRuns.getLstExecLogDetail().get(i).getProcessExecLogHistory();
                    	// sort list exacErrorLogImport of record number oder by asc
                        lstExacErrorLog.sort(Comparator.comparingInt(ExacErrorLogQueryDto::getRecordNumber));

                        for(ExacErrorLogQueryDto exacErrLog : lstExacErrorLog){
                            Map<String, Object> rowCSV4 = new HashMap<>();
                            rowCSV4.putAll(this.saveHeaderCSV(rowCSV4,proHis.getExecId(),exeCode.getExecItemCd(),proHis.getExecItemCd(),exeCode.getExecItemName()));
                            rowCSV4.put(headerCSV4.get(3),exacErrLog.getRecordNumber());
                            rowCSV4.put(headerCSV4.get(4),exacErrLog.getCsvErrorItemName().isPresent() ? exacErrLog.getCsvErrorItemName().get() : null);
                            rowCSV4.put(headerCSV4.get(5),exacErrLog.getItemName().isPresent() ? exacErrLog.getItemName().get() : null);
                            rowCSV4.put(headerCSV4.get(6),exacErrLog.getCsvAcceptedValue().isPresent() ? exacErrLog.getCsvAcceptedValue().get() : null);
                            rowCSV4.put(headerCSV4.get(7),exacErrLog.getErrorContents().isPresent() ? exacErrLog.getErrorContents().get() : null);
                            csv.writeALine(rowCSV4);
                        }
                    }
                    continue;
                }
                csv.destroy();
            } catch (Exception e) {
            	 
            }
    	}
    }

    private void generalFileCSV5(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {

        boolean checkExternalErrLogEmpty = false;
        for(int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++){
            List<ExternalOutLogQueryDto> lstExternalOutLog = updateProAutoRuns.getLstExecLogDetail().get(i).getExternalOutLogImports();
            if(lstExternalOutLog != null){
                checkExternalErrLogEmpty = true;
            }
        }
        if(checkExternalErrLogEmpty){
            try {
                List<String> headerCSV5 = this.getTextHeaderCsv5();
                CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV5 + CSV_EXTENSION, headerCSV5, "UTF-8");
                for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                    List<ExternalOutLogQueryDto> lstExternalOutLog = updateProAutoRuns.getLstExecLogDetail().get(i).getExternalOutLogImports();
                    if(lstExternalOutLog != null){
                        ProcessExecutionLogHistory proHis = updateProAutoRuns.getLstExecLogDetail().get(i).getProcessExecLogHistory();
                        EmployeeInfoImport empImport = new EmployeeInfoImport(null, null, null);
                        ProcessExecution exeCode = updateProAutoRuns.getLstProcessExecution().get(i);

                        if(updateProAutoRuns.getLstEmployeeSearch() != null){
                            empImport = updateProAutoRuns.getLstEmployeeSearch().get(i);
                        }
                        // sort list ExternalOutLogImport of process count and ascending order by asc
                        lstExternalOutLog.sort(Comparator.comparing(ExternalOutLogQueryDto::getProcessCount));

                        for(ExternalOutLogQueryDto externalOutLogImport : lstExternalOutLog) {
                            Map<String, Object> rowCSV5 = new HashMap<>();
                            rowCSV5.putAll(this.saveHeaderCSV(rowCSV5, proHis.getExecId(), exeCode.getExecItemCd(), proHis.getExecItemCd(), exeCode.getExecItemName()));
                            rowCSV5.put(headerCSV5.get(3), externalOutLogImport.getProcessCount());
                            rowCSV5.put(headerCSV5.get(4), empImport.getScd());
                            rowCSV5.put(headerCSV5.get(5), empImport.getBussinessName());
                            rowCSV5.put(headerCSV5.get(6), externalOutLogImport.getErrorDate().map(item -> item.toString()).orElse(null));
                            rowCSV5.put(headerCSV5.get(7), externalOutLogImport.getErrorItem().isPresent() ? externalOutLogImport.getErrorItem().get() : null);
                            rowCSV5.put(headerCSV5.get(8), externalOutLogImport.getErrorTargetValue().isPresent() ? externalOutLogImport.getErrorTargetValue().get() : null);
                            rowCSV5.put(headerCSV5.get(9), externalOutLogImport.getErrorContent().isPresent() ? externalOutLogImport.getErrorContent().get() : null);
                            csv.writeALine(rowCSV5);
                        }
                    }
                    continue;
                }
                csv.destroy();

            } catch (Exception e) {

            }
        }
    }

    // saves duplicate fields of CSVs
    private Map<String, Object> saveHeaderCSV(Map<String, Object> rowCsv, String execId, ExecutionCode execCode, ExecutionCode execCodeHistory, ExecutionName execName) {
        rowCsv.put(TextResource.localize("KBT002_289"), execId);
        rowCsv.put(TextResource.localize("KBT002_7"), execCode.v());
        rowCsv.put(TextResource.localize("KBT002_8"), execCode.equals(execCodeHistory) ? execName.v() : TextResource.localize("KBT002_193"));
        return rowCsv;
    }

    // check equalId
    private boolean checkEqualId(String id1, String id2) {
        if (id1.equals(id2)) {
            return true;
        }
        return false;
    }

    // convert GeneralDateTime to LocalDateTime and get Hour lastExecTime - lasExecEndTime to String
    private String getHourByGetLastExecDateTimeAndGetLastEndExecDateTime(GeneralDateTime lastExecTime, GeneralDateTime lastExecEndTime) {
        if(lastExecTime == null || lastExecEndTime == null) {
        	return null;
        }
    	LocalDateTime convertLastTime = lastExecTime.localDateTime();
        LocalDateTime convertLastEndTime = lastExecEndTime.localDateTime();
        // get duration between convert last time and end time
        Duration dur = Duration.between(convertLastEndTime, convertLastTime);
        long millis = dur.toMillis();
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private void zipAllFileCSV(FileGeneratorContext generatorContext) {
        // アルゴリズム「結果ファイルの圧縮」を実行
        try {
            ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
                    .createContainer();
            String fileName = FILE_NAME_ZIP + GeneralDateTime.now().toString("yyyyMMddHHmmss") + ZIP_EXTENSION;
            applicationTemporaryFilesContainer.zipWithName(generatorContext, fileName, false);
            applicationTemporaryFilesContainer.removeContainer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getTextHeaderCsv1() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV1) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

    private List<String> getTextHeaderCsv2() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV2) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

    private List<String> getTextHeaderCsv3(boolean isExportEmpName) {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV3) {

            // check valid export employee name true or false
            if (isExportEmpName) {
                if (!nameId.equals("KBT002_292")) {
                    lstHeader.add(TextResource.localize(nameId));
                }
            } else {
                lstHeader.add(TextResource.localize(nameId));
            }
        }
        return lstHeader;
    }

    private List<String> getTextHeaderCsv4() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV4) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }

    private List<String> getTextHeaderCsv5() {
        List<String> lstHeader = new ArrayList<>();
        for (String nameId : LST_NAME_ID_HEADER_TABLE_CSV5) {
            lstHeader.add(TextResource.localize(nameId));
        }
        return lstHeader;
    }
}
