package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import nts.arc.enums.EnumAdaptor;
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
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionRepository;
import nts.uk.ctx.at.function.dom.processexecution.storage.ResultState;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDailyRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.shared.dom.person.PersonAdaptor;
import nts.uk.ctx.at.shared.dom.person.PersonImport;
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
import java.util.function.Predicate;
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
        List<UpdateProcessAutoExecutionDto> lisProcessExecution = this.acquireTheDomainModel(companyId, listProcessExeHistory);

        // Step OUTPUT「更新処理自動実行データ」を作成する
        UpdateProcessAutoRunDataDto updateProAutoRunData = new UpdateProcessAutoRunDataDto(
                lisProcessExecution,
                Collections.emptyList(),
                Collections.emptyList(),
                command.isExportEmployeeName());

        List<ExecutionLogDetailDto> lstLogDetail = new ArrayList<>();
        List<String> empIds = new ArrayList<>();
        // 取得した「更新処理自動実行ログ履歴」をループする - Loop the acquired 「更新処理自動実行ログ履歴」
        for (ProcessExecutionLogHistory logHistory : listProcessExeHistory) {
            // 処理中の「更新処理自動実行ログ履歴．全体の業務エラー状態」を確認する
            // 全体の業務エラー状態 = true
            if (logHistory.getErrorBusiness().isPresent() && logHistory.getErrorBusiness().get()) {
                // Step 処理中の「更新処理自動実行ログ履歴．各処理の終了状態．業務エラー状態」 = true の項目を確認する
                if (!logHistory.getTaskLogList().isEmpty()) {
                    for (ExecutionTaskLog taskLog : logHistory.getTaskLogList()) {
                        if(taskLog.getErrorBusiness().isPresent() && taskLog.getErrorBusiness().get()) {
                            String execIdByLogHistory = logHistory.getExecId();

                            //create empty ExecutionLogDetailDto
                            ExecutionLogDetailDto execLogDetail = ExecutionLogDetailDto.builder().build();

                            //set ProcessExecutionLogHistoryDto to ExecutionLogDetailDto
                            ProcessExecutionLogHistoryDto processExecutionLogHistoryDto = ProcessExecutionLogHistoryDto.builder().build();
                            logHistory.setMemento(processExecutionLogHistoryDto);
                            execLogDetail.setProcessExecLogHistory(processExecutionLogHistoryDto);

                            switch (taskLog.getProcExecTask()) {
                                // Case 1 ドメインモデル「スケジューScheduleErrorLogル作成エラーログ」を取得する
                                case SCH_CREATION:
                                    List<ScheduleErrorLogDto> scheduleErrorLogDtos = this.scheduleErrorLogRepo.findByExecutionId(execIdByLogHistory).stream()
                                        .map(item -> {
                                            ScheduleErrorLogDto dto = ScheduleErrorLogDto.builder().build();
                                            item.saveToMemento(dto);
                                            return dto;
                                        })
                                        .collect(Collectors.toList());
                                    // 更新処理自動実行データ．実行ログ詳細．スケジュール作成エラー．社員ID
                                    empIds.addAll(scheduleErrorLogDtos.stream().map(ScheduleErrorLogDto::getEmployeeId).collect(Collectors.toList()));
                                    execLogDetail.setScheduleErrorLog(scheduleErrorLogDtos);
                                    break;
                                // Case 2 ドメインモデル「エラーメッセージ情報」を取得する
                                case DAILY_CREATION:
                                case DAILY_CALCULATION:
                                case MONTHLY_AGGR:
                                case RFL_APR_RESULT:
                                    List<ErrMessageInfoDto> errMessageInfoDtos = this.errMessageInfoRepo.getAllErrMessageInfoByEmpID(execIdByLogHistory).stream()
                                        .map(item -> ErrMessageInfoDto.builder()
                                            .employeeID(item.getEmployeeID())
                                            .empCalAndSumExecLogID(item.getEmpCalAndSumExecLogID())
                                            .resourceID(item.getResourceID().v())
                                            .executionContent(item.getExecutionContent().value)
                                            .disposalDay(item.getDisposalDay())
                                            .messageError(item.getMessageError().v())
                                            .build())
                                        .collect(Collectors.toList());
                                    //更新処理自動実行データ．実行ログ詳細．日次・月次処理エラー．社員ID
                                    empIds.addAll(errMessageInfoDtos.stream().map(ErrMessageInfoDto::getEmployeeID).collect(Collectors.toList()));
                                    execLogDetail.setErrMessageInfo(errMessageInfoDtos);
                                    break;
                                // Case 3 ドメインモデル「任意期間集計エラーメッセージ情報」を取得する
                                case AGGREGATION_OF_ARBITRARY_PERIOD:
                                    List<AggrPeriodInforDto> aggrPeriodInforDtos = this.errorInfoRepo.findAll(execIdByLogHistory).stream()
                                        .map(item -> AggrPeriodInforDto.builder()
                                            .memberId(item.getMemberId())
                                            .periodArrgLogId(item.getPeriodArrgLogId())
                                            .resourceId(item.getResourceId())
                                            .processDay(item.getProcessDay())
                                            .errorMess(item.getErrorMess().v())
                                            .build())
                                        .collect(Collectors.toList());
                                    //更新処理自動実行データ．実行ログ詳細．任意期間集計エラー．社員ID
                                    empIds.addAll(aggrPeriodInforDtos.stream().map(AggrPeriodInforDto::getMemberId).collect(Collectors.toList()));
                                    execLogDetail.setAggrPeriodInfor(aggrPeriodInforDtos);
                                    break;
                                // Case 4 ドメインモデル「承認中間データエラーメッセージ情報（日別実績）」を取得する
                                case APP_ROUTE_U_DAI:
                                    List<AppDataInfoDailyDto> appDataInfoDailyDtos = this.appDataInfoDailyRepo.getAppDataInfoDailyByExeID(execIdByLogHistory).stream()
                                        .map(item -> AppDataInfoDailyDto.builder()
                                            .employeeId(item.getEmployeeId())
                                            .executionId(item.getExecutionId())
                                            .errorMessage(item.getErrorMessage().v())
                                            .build())
                                        .collect(Collectors.toList());
                                    //更新処理自動実行データ．実行ログ詳細．承認ルート更新（日次）エラー．社員ID
                                    empIds.addAll(appDataInfoDailyDtos.stream().map(AppDataInfoDailyDto::getEmployeeId).collect(Collectors.toList()));
                                    execLogDetail.setAppDataInfoDailies(appDataInfoDailyDtos);
                                    break;
                                // Case 5 ドメインモデル「承認中間データエラーメッセージ情報（月別実績）」を取得する
                                case APP_ROUTE_U_MON:
                                    List<AppDataInfoMonthlyDto> appDataInfoMonthlyDtos = this.appDataInfoMonthlyRepo.getAppDataInfoMonthlyByExeID(execIdByLogHistory).stream()
                                        .map(item -> AppDataInfoMonthlyDto.builder()
                                            .employeeId(item.getEmployeeId())
                                            .executionId(item.getExecutionId())
                                            .errorMessage(item.getErrorMessage().v())
                                            .build())
                                        .collect(Collectors.toList());
                                    //更新処理自動実行データ．実行ログ詳細．承認ルート更新（月次）エラー．社員ID
                                    empIds.addAll(appDataInfoMonthlyDtos.stream().map(AppDataInfoMonthlyDto::getEmployeeId).collect(Collectors.toList()));
                                    execLogDetail.setAppDataInfoMonthlies(appDataInfoMonthlyDtos);
                                    break;
                                // Case 6 ドメインモデル「外部受入エラーログ」のデータを取得する
                                case EXTERNAL_ACCEPTANCE:
                                    execLogDetail.setExacErrorLogImports(this.exacErrorLogQueryFinder.getExacErrorLogByProcessId(execIdByLogHistory));
                                    break;
                                // Case 7 ドメインモデル「外部出力結果ログ」のデータを取得する
                                case EXTERNAL_OUTPUT:
                                   execLogDetail.setExternalOutLogImports(this.externalOutLogQueryFinder.getExternalOutLogById(companyId,execIdByLogHistory,0)); //ProcessingClassification.ERROR = 0
                                    break;
                                default:
                                    break;
                            }
                            lstLogDetail.add(execLogDetail);
                        }
                    }
                }
            }
        }
        // remove duplicate employeeId by empIds
        List<String> deDupEmpIds = empIds.stream().distinct().collect(Collectors.toList());
        // Step 社員ID(List)から個人社員基本情報を取得
        List<EmployeeBasicInfoExportDto> empBasicIf = this.obtainBasicPersonalEmployeeInformation(deDupEmpIds);

        // Convert lst EmployeeBasicInfoExportDto ->  lst EmployeeSearchDto
        List<EmployeeInfoImport> empInfoResult = empBasicIf.stream()
                .map(empBs -> new EmployeeInfoImport(empBs.getEmployeeId(), empBs.getEmployeeCode(), empBs.getBusinessName()))
                .collect(Collectors.toList());
        updateProAutoRunData.setLstEmployeeSearch(empInfoResult);

        //「更新処理自動実行データ」を更新後「実行ログ詳細」をソートする ::::「更新処理自動実行データ．実行ログ詳細．更新処理自動実行ログ履歴．前回実行日時」の昇順
        lstLogDetail.sort(Comparator.comparing(item -> item.getProcessExecLogHistory().getLastExecDateTime()));
        //「更新処理自動実行データ」を更新後「実行ログ詳細」をソートする ::::「更新処理自動実行データ．実行ログ詳細．更新処理自動実行ログ履歴．各処理の終了状態．更新処理」の昇順
        lstLogDetail.forEach(item -> {
            item.getProcessExecLogHistory().getTaskLogList().sort(Comparator.comparing(logTask -> logTask.getProcExecTask().value));
        });
        // Step 「実行ログ詳細」を作成してOUTPUT「更新処理自動実行データ」に追加する
        updateProAutoRunData.setLstExecLogDetail(lstLogDetail);
        // Step OUTPUT「更新処理自動実行データ」を更新して返す
        return updateProAutoRunData;
    }

    private List<ProcessExecutionLogHistory> updateProcessAutomaticExecutionLogHistory(GetDataToOutputCommand commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        GeneralDateTime startDate = commandHandlerContext.getStartDate();
        GeneralDateTime endDate = commandHandlerContext.getEndDate();
        return this.updateProAutoExeRepo.getByCompanyIdAndDateAndEmployeeName(companyId, startDate, endDate);
    }

    private List<UpdateProcessAutoExecutionDto> acquireTheDomainModel(String companyId, List<ProcessExecutionLogHistory> processExeHistory) {
        List<String> execItemCd = processExeHistory.stream().map(item -> item.getExecItemCd().v()).collect(Collectors.toList());
        return this.procExecRepo.getProcessExecutionByCompanyId(companyId).stream()
                .map(UpdateProcessAutoExecutionDto::createFromDomain)
                .filter(item -> execItemCd.stream().anyMatch(Predicate.isEqual(item.getExecItemCode())))
                .collect(Collectors.toList());
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
            List<String> lstEmpId = employeeInfoImports.stream().map(EmployeeBasicInfoFnImport::getEmployeeId).collect(Collectors.toList());

//            List<AffCompanyHistImport> affCompanyHistImports =
//                    this.employeeHistWorkRecordAdapter.getWplByListSidAndPeriod(lstEmpId, new DatePeriod(GeneralDate.min(), GeneralDate.max()));

            // get list personId
            List<String> lstPersonId = employeeInfoImports.stream().map(EmployeeBasicInfoFnImport::getPId).collect(Collectors.toList());
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
                        .map(EmployeeBasicInfoFnImport::getEmployeeCode)
                        .orElse(null));
                empBasicInfo.setEmployeeId(fnImport
                        .map(EmployeeBasicInfoFnImport::getEmployeeId)
                        .orElse(null));
                empBasicInfo.setEntryDate(fnImport
                        .map(EmployeeBasicInfoFnImport::getEntryDate)
                        .orElse(null));
                empBasicInfo.setRetiredDate(fnImport
                        .map(EmployeeBasicInfoFnImport::getRetiredDate)
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

    private String getTextResource(boolean check) {
    	return check ? TextResource.localize("KBT002_290") : TextResource.localize("KBT002_291");
    }
    
    private void generalFileCSV1(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
            List<String> headerCSV1 = this.getTextHeaderCsv1();
            CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV1 + CSV_EXTENSION, headerCSV1, "UTF-8");
            for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                ExecutionLogDetailDto logDetail = updateProAutoRuns.getLstExecLogDetail().get(i);
                UpdateProcessAutoExecutionDto exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                ProcessExecutionLogHistoryDto proHis = logDetail.getProcessExecLogHistory();

                Map<String, Object> rowCSV1 = new HashMap<>(this.saveHeaderCSV(proHis.getExecId(), exeCode.getExecItemCode(), proHis.getExecItemCd(), exeCode.getExecItemName()));

                String lastExecDateTime = proHis.getLastExecDateTime() == null ? null : proHis.getLastExecDateTime().toString("yyyy-MM-dd HH:mm:ss");
                String lastEndExecDateTime = proHis.getLastEndExecDateTime() == null ? null : proHis.getLastEndExecDateTime().toString("yyyy-MM-dd HH:mm:ss");
                rowCSV1.put(headerCSV1.get(3), lastExecDateTime);
                rowCSV1.put(headerCSV1.get(4), lastEndExecDateTime);

                rowCSV1.put(headerCSV1.get(5), this.getHourByGetLastExecDateTimeAndGetLastEndExecDateTime(proHis.getLastExecDateTime(), proHis.getLastEndExecDateTime()));
                rowCSV1.put(headerCSV1.get(6), proHis.getOverallStatus());
                rowCSV1.put(headerCSV1.get(7), proHis.getErrorSystem() == null ? null : getTextResource(proHis.getErrorSystem()));
                rowCSV1.put(headerCSV1.get(8), proHis.getErrorBusiness() == null ? null : getTextResource(proHis.getErrorBusiness()));
                rowCSV1.put(headerCSV1.get(9), proHis.getOverallError());
                csv.writeALine(rowCSV1);
            }
            csv.destroy();
    }

    private void generalFileCSV2(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
            List<String> headerCSV2 = this.getTextHeaderCsv2();
            CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV2 + CSV_EXTENSION, headerCSV2, "UTF-8");
            for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                ExecutionLogDetailDto logDetail = updateProAutoRuns.getLstExecLogDetail().get(i);
                UpdateProcessAutoExecutionDto exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                ProcessExecutionLogHistoryDto proHis = logDetail.getProcessExecLogHistory();

                if(!proHis.getTaskLogList().isEmpty()){

                    for(ExecutionTaskLog taskLog : proHis.getTaskLogList()){
                        Map<String, Object> rowCSV2 = new HashMap<>(this.saveHeaderCSV(proHis.getExecId(), exeCode.getExecItemCode(), proHis.getExecItemCd(), exeCode.getExecItemName()));
                        rowCSV2.put(headerCSV2.get(3), taskLog.getProcExecTask() != null ? taskLog.getProcExecTask().name : null);
                        String lastExecDateTime = taskLog.getLastExecDateTime().map(item -> item.toString("yyyy-MM-dd HH:mm:ss")).orElse(null);
                        String lastEndExecDateTime = taskLog.getLastEndExecDateTime().map(item -> item.toString("yyyy-MM-dd HH:mm:ss")).orElse(null);
                        rowCSV2.put(headerCSV2.get(4), taskLog.getLastExecDateTime().isPresent() ? lastExecDateTime : null);
                        rowCSV2.put(headerCSV2.get(5), taskLog.getLastEndExecDateTime().isPresent() ? lastEndExecDateTime : null);
                        rowCSV2.put(headerCSV2.get(6), this.getHourByGetLastExecDateTimeAndGetLastEndExecDateTime(proHis.getLastExecDateTime(), proHis.getLastEndExecDateTime()));
                        rowCSV2.put(headerCSV2.get(7), proHis.getOverallStatus() == null ? null : EnumAdaptor.valueOf(proHis.getOverallStatus(),EndStatus.class).name);
                        rowCSV2.put(headerCSV2.get(8), proHis.getErrorSystem() == null ? null : getTextResource(proHis.getErrorSystem()));
                        rowCSV2.put(headerCSV2.get(9), taskLog.getSystemErrorDetails());
                        rowCSV2.put(headerCSV2.get(10), proHis.getErrorBusiness() == null ? null : getTextResource(proHis.getErrorBusiness()));
                        csv.writeALine(rowCSV2);
                    }
                }
            }
            csv.destroy();
    }

    private void generalFileCSV3(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {

        boolean checkEmptyTaskLogList = false;
        for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
            if (!updateProAutoRuns.getLstExecLogDetail().get(i).getProcessExecLogHistory().getTaskLogList().isEmpty()) {
                checkEmptyTaskLogList = true;
            }
        }

        if (checkEmptyTaskLogList) {
            List<String> headerCSV3 = this.getTextHeaderCsv3(updateProAutoRuns.isExportEmpName());
            CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV3 + CSV_EXTENSION, headerCSV3, "UTF-8");
            for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                ExecutionLogDetailDto logDetail = updateProAutoRuns.getLstExecLogDetail().get(i);
                UpdateProcessAutoExecutionDto exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                EmployeeInfoImport empImport = updateProAutoRuns.getLstEmployeeSearch().get(i);
                ProcessExecutionLogHistoryDto proHis = logDetail.getProcessExecLogHistory();

                if (!proHis.getTaskLogList().isEmpty()) {
                    for (ExecutionTaskLog taskLog : proHis.getTaskLogList()) {
                        int j = 0;
                        if (taskLog.getErrorBusiness().isPresent() && taskLog.getErrorBusiness().get()) {
                            // sort list scheduleErrorLog in date by asc
                            ScheduleErrorLogDto schLog = ScheduleErrorLogDto.builder().build();
                            ErrMessageInfoDto errLog = ErrMessageInfoDto.builder().build();
                            AggrPeriodInforDto aggPeInfo = AggrPeriodInforDto.builder().build();
                            AppDataInfoDailyDto appDaily = AppDataInfoDailyDto.builder().build();
                            AppDataInfoMonthlyDto appMonthly = AppDataInfoMonthlyDto.builder().build();
                            if (logDetail.getScheduleErrorLog() != null) {
                                logDetail.getScheduleErrorLog().sort(Comparator.comparing(ScheduleErrorLogDto::getDate));
                                schLog = logDetail.getScheduleErrorLog().get(j);
                            }
                            if (logDetail.getAggrPeriodInfor() != null) {
                                logDetail.getAggrPeriodInfor().sort(Comparator.comparing(AggrPeriodInforDto::getProcessDay));
                                aggPeInfo = logDetail.getAggrPeriodInfor().get(j);
                            }
                            if (logDetail.getErrMessageInfo() != null) {
                                logDetail.getErrMessageInfo().sort(Comparator.comparing(ErrMessageInfoDto::getDisposalDay));
                                errLog = logDetail.getErrMessageInfo().get(j);
                            }

                            if (logDetail.getAppDataInfoDailies() != null) {
                                appDaily = logDetail.getAppDataInfoDailies().get(j);
                            }

                            if (logDetail.getAppDataInfoMonthlies() != null) {
                                appMonthly = logDetail.getAppDataInfoMonthlies().get(j);
                            }

                            Map<String, Object> rowCSV3 = new HashMap<>(this.saveHeaderCSV(proHis.getExecId(), exeCode.getExecItemCode(), proHis.getExecItemCd(), exeCode.getExecItemName()));
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
                                    rowCSV3.put(headerCSV3.get(7), errLog.getMessageError() != null ? errLog.getMessageError() : null);
                                    break;
                                case AGGREGATION_OF_ARBITRARY_PERIOD:
                                    rowCSV3.put(headerCSV3.get(6), aggPeInfo.getProcessDay() != null ? aggPeInfo.getProcessDay().toString() : null);
                                    rowCSV3.put(headerCSV3.get(7), aggPeInfo.getErrorMess() != null ? aggPeInfo.getErrorMess() : null);
                                    break;
                                case APP_ROUTE_U_DAI:
                                    rowCSV3.put(headerCSV3.get(7), appDaily.getErrorMessage() != null ? appDaily.getErrorMessage() : null);
                                    break;
                                case APP_ROUTE_U_MON:
                                    rowCSV3.put(headerCSV3.get(7), appMonthly.getErrorMessage() != null ? appMonthly.getErrorMessage() : null);
                                    break;
                                default:
                                    break;
                            }
                            csv.writeALine(rowCSV3);
                        }
                        j++;
                    }
                }
            }
            csv.destroy();
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
                List<String> headerCSV4 = this.getTextHeaderCsv4();
                CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV4 + CSV_EXTENSION, headerCSV4, "UTF-8");
                for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                	List<ExacErrorLogQueryDto> lstExacErrorLog = updateProAutoRuns.getLstExecLogDetail().get(i).getExacErrorLogImports();
                    if(lstExacErrorLog != null){
                    	UpdateProcessAutoExecutionDto exeCode = updateProAutoRuns.getLstProcessExecution().get(i);
                        ProcessExecutionLogHistoryDto proHis = updateProAutoRuns.getLstExecLogDetail().get(i).getProcessExecLogHistory();
                    	// sort list exacErrorLogImport of record number oder by asc
                        lstExacErrorLog.sort(Comparator.comparingInt(ExacErrorLogQueryDto::getRecordNumber));

                        for(ExacErrorLogQueryDto exacErrLog : lstExacErrorLog){
                            Map<String, Object> rowCSV4 = new HashMap<>(this.saveHeaderCSV(proHis.getExecId(), exeCode.getExecItemCode(), proHis.getExecItemCd(), exeCode.getExecItemName()));
                            rowCSV4.put(headerCSV4.get(3),exacErrLog.getRecordNumber());
                            rowCSV4.put(headerCSV4.get(4),exacErrLog.getCsvErrorItemName().orElse(null));
                            rowCSV4.put(headerCSV4.get(5),exacErrLog.getItemName().orElse(null));
                            rowCSV4.put(headerCSV4.get(6),exacErrLog.getCsvAcceptedValue().orElse(null));
                            rowCSV4.put(headerCSV4.get(7),exacErrLog.getErrorContents().orElse(null));
                            csv.writeALine(rowCSV4);
                        }
                    }
                }
                csv.destroy();
    	}
    }

    private void generalFileCSV5(FileGeneratorContext generatorContext, UpdateProcessAutoRunDataDto updateProAutoRuns) {
        boolean checkExternalErrLogEmpty = false;
        for(int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++){
            List<ExternalOutLogQueryDto> lstExternalOutLog = updateProAutoRuns.getLstExecLogDetail().get(i).getExternalOutLogImports();
            if (lstExternalOutLog != null) {
                checkExternalErrLogEmpty = true;
                break;
            }
        }
        if(checkExternalErrLogEmpty){
                List<String> headerCSV5 = this.getTextHeaderCsv5();
                CsvReportWriter csv = this.generator.generate(generatorContext, FILE_NAME_CSV5 + CSV_EXTENSION, headerCSV5, "UTF-8");
                for (int i = 0; i < updateProAutoRuns.getLstExecLogDetail().size(); i++) {
                    List<ExternalOutLogQueryDto> lstExternalOutLog = updateProAutoRuns.getLstExecLogDetail().get(i).getExternalOutLogImports();
                    if(lstExternalOutLog != null){
                        ProcessExecutionLogHistoryDto proHis = updateProAutoRuns.getLstExecLogDetail().get(i).getProcessExecLogHistory();
                        EmployeeInfoImport empImport = new EmployeeInfoImport(null, null, null);
                        UpdateProcessAutoExecutionDto exeCode = updateProAutoRuns.getLstProcessExecution().get(i);

                        if(updateProAutoRuns.getLstEmployeeSearch() != null){
                            empImport = updateProAutoRuns.getLstEmployeeSearch().get(i);
                        }
                        // sort list ExternalOutLogImport of process count and ascending order by asc
                        lstExternalOutLog.sort(Comparator.comparing(ExternalOutLogQueryDto::getProcessCount));

                        for(ExternalOutLogQueryDto externalOutLogImport : lstExternalOutLog) {
                            Map<String, Object> rowCSV5 = new HashMap<>(this.saveHeaderCSV(proHis.getExecId(), exeCode.getExecItemCode(), proHis.getExecItemCd(), exeCode.getExecItemName()));
                            rowCSV5.put(headerCSV5.get(3), externalOutLogImport.getProcessCount());
                            rowCSV5.put(headerCSV5.get(4), empImport.getScd());
                            rowCSV5.put(headerCSV5.get(5), empImport.getBussinessName());
                            rowCSV5.put(headerCSV5.get(6), externalOutLogImport.getErrorDate().map(item -> item.toString()).orElse(null));
                            rowCSV5.put(headerCSV5.get(7), externalOutLogImport.getErrorItem().orElse(null));
                            rowCSV5.put(headerCSV5.get(8), externalOutLogImport.getErrorTargetValue().orElse(null));
                            rowCSV5.put(headerCSV5.get(9), externalOutLogImport.getErrorContent().orElse(null));
                            csv.writeALine(rowCSV5);
                        }
                    }
                }
                csv.destroy();
        }
    }

    // saves duplicate fields of CSVs
    private Map<String, Object> saveHeaderCSV(String execId, String execCode, String execCodeHistory, String execName) {
        Map<String, Object> rowCsv = new HashMap<>();
        rowCsv.put(TextResource.localize("KBT002_289"), execId);
        rowCsv.put(TextResource.localize("KBT002_7"), execCode);
        rowCsv.put(TextResource.localize("KBT002_8"), execCode.equals(execCodeHistory) ? execName : TextResource.localize("KBT002_193"));
        return rowCsv;
    }

    // check equalId
    private boolean checkEqualId(String id1, String id2) {
        return id1.equals(id2);
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
