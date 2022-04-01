package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.*;
import nts.uk.ctx.at.function.dom.adapter.role.AlarmMailSettingsAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportDto;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.user.UserEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRoleRepository;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class AlarmTopPageProcessingServiceImpl implements AlarmTopPageProcessingService {

    @Inject
    private PersisAlarmListExtractResultRepository alarmExtractResultRepo;

    @Inject
    private TopPageAlarmAdapter topPageAlarmAdapter;

    @Inject
    private EmployeeWorkplaceAdapter employeeWorkplaceAdapter;

    @Inject
    protected TransactionService transaction;

    @Inject
    private AdministratorReceiveAlarmMailAdapter adminReceiveAlarmMailAdapter;

    @Inject
    private AlarmMailSettingsAdapter mailAdapter;

    @Inject
    private RoleSetExportAdapter roleAdapter;

    @Inject
    private UserEmployeeAdapter userEmployeeAdapter;

    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;

    @Inject
    private WkpManagerAdapter workplaceAdapter;

    @Inject
    private AlarmMailSendingRoleRepository alarmMailSendingRoleRepo;

    @Inject
    private AuthWorkPlaceAdapter authWorkPlaceAdapter;


    /**
     * アラーム（トップページ）永続化の処理
     *
     * @param runCode
     * @param pattentCd
     * @param lstSid
     * @param lstCategoryPeriod List＜カテゴリ別期間＞
     * @param alarmResult       永続化のアラームリスト抽出結果
     * @param alarmExtractConds アラーム抽出条件
     * @param isDisplayByAdmin
     * @param isDisplayByPerson
     */
    @Override
    public void persisTopPageProcessing(String runCode, String pattentCd, List<String> lstSid,
                                        List<PeriodByAlarmCategory> lstCategoryPeriod,
                                        PersistenceAlarmListExtractResult alarmResult,
                                        List<AlarmExtractionCondition> alarmExtractConds,
                                        boolean isDisplayByAdmin, boolean isDisplayByPerson) {
        String cid = AppContexts.user().companyId();
        if (runCode.equals("Z")) {
            return;
        }

        // パターン、社員IDからアラーム抽出結果を取得
        val optPersisAlarmExtractResult = alarmExtractResultRepo.getAlarmExtractResult(runCode, pattentCd, lstSid);
        if (!optPersisAlarmExtractResult.isPresent() && CollectionUtil.isEmpty(alarmResult.getAlarmListExtractResults())) {
            return;
        } else {
            if (!optPersisAlarmExtractResult.isPresent() && !CollectionUtil.isEmpty(alarmResult.getAlarmListExtractResults())) {
                alarmExtractResultRepo.insert(alarmResult);
            } else {
                List<AlarmEmployeeList> lstExResultInsert = new ArrayList<>();
                List<AlarmEmployeeList> lstExResultDelete = new ArrayList<>();
                PersistenceAlarmListExtractResult persisAlarmExtract = optPersisAlarmExtractResult.get();

                if (lstCategoryPeriod.isEmpty()) {
                    return;
                }

                List<AlarmEmployeeList> lstExtractResultDB = new ArrayList<>();
                for (PeriodByAlarmCategory p : lstCategoryPeriod) {//永続化のアラームリスト抽出結果を絞り込む
                    List<AlarmExtractionCondition> extractConds = alarmExtractConds.stream()
                            .filter(e -> e.getAlarmCategory().value == p.getCategory()).collect(Collectors.toList());

                    for (AlarmExtractionCondition c : extractConds) {
                        val temp = persisAlarmExtract.getAlarmListExtractResults().stream()
                                .filter(x -> x.getAlarmExtractInfoResults().stream()
                                        .anyMatch(y -> c.getAlarmCategory().value == y.getAlarmCategory().value
                                                && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                                                && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value
                                                && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
                                                && y.getExtractionResultDetails().stream().anyMatch(z -> {
                                            if (c.getAlarmCategory().value == AlarmCategory.MONTHLY.value) {
                                                return z.getPeriodDate().getStartDate().get().compareTo(p.getStartDate()) == 0;
                                            } else {
                                                if (z.getPeriodDate() == null || c.getAlarmCategory().value == AlarmCategory.MASTER_CHECK.value) {
                                                    return true;
                                                } else if (!z.getPeriodDate().getEndDate().isPresent()) {
                                                    return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getStartDate().get().beforeOrEquals(p.getEndDate());
                                                } else {
                                                    return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getEndDate().get().beforeOrEquals(p.getEndDate());
                                                }
                                            }
                                        })
                                )).collect(Collectors.toList());
                        if (!temp.isEmpty()) {
                            lstExtractResultDB.addAll(temp);
                        }
                    }

                    List<AlarmEmployeeList> lstExtractResultInput = alarmResult.getAlarmListExtractResults().stream()
                            .filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> y.getAlarmCategory().value == p.getCategory()))
                            .collect(Collectors.toList());

                    dataProcessingInputOutput(p, lstExtractResultInput, lstExtractResultDB, lstExResultInsert, lstExResultDelete);
                }

                //Delete: 今回のアラーム結果がないがデータベースに存在している場合データベースを削除
                if (!CollectionUtil.isEmpty(lstExResultDelete)) {
                    PersistenceAlarmListExtractResult persisExtractResultDelete = new PersistenceAlarmListExtractResult(
                            persisAlarmExtract.getAlarmPatternCode(),
                            persisAlarmExtract.getAlarmPatternName(),
                            lstExResultDelete,
                            persisAlarmExtract.getCompanyID(),
                            persisAlarmExtract.getAutoRunCode()
                    );
                    alarmExtractResultRepo.delete(persisExtractResultDelete);
                }

                //Insert: 今回のアラーム結果がデータベースに存在してない場合データベースのデータを追加
                if (!CollectionUtil.isEmpty(lstExResultInsert)) {
                    PersistenceAlarmListExtractResult persisExtractResultInsert = new PersistenceAlarmListExtractResult(
                            alarmResult.getAlarmPatternCode(),
                            alarmResult.getAlarmPatternName(),
                            lstExResultInsert,
                            alarmResult.getCompanyID(),
                            alarmResult.getAutoRunCode()
                    );
                    alarmExtractResultRepo.insert(persisExtractResultInsert);
                }
            }
        }

        //アラームリストからトップページアラームデータに変換する
        RequireImpl require = new RequireImpl(alarmExtractResultRepo, topPageAlarmAdapter, employeeWorkplaceAdapter, adminReceiveAlarmMailAdapter,
                mailAdapter, roleAdapter, userEmployeeAdapter, affWorkplaceAdapter, workplaceAdapter, alarmMailSendingRoleRepo, authWorkPlaceAdapter);
        List<AtomTask> atomTasks = ConvertAlarmListToTopPageAlarmDataService.convert(require, cid, lstSid,
                new AlarmPatternCode(pattentCd), new ExecutionCode(runCode), isDisplayByAdmin, isDisplayByPerson);

        if (!atomTasks.isEmpty()) {
            transaction.execute(() -> {
                for (AtomTask atomTask : atomTasks) {
                    atomTask.run();
                }
            });
        }
    }

    private void dataProcessingInputOutput(PeriodByAlarmCategory period, List<AlarmEmployeeList> lstInput, List<AlarmEmployeeList> lstDB, List<AlarmEmployeeList> lstInsert, List<AlarmEmployeeList> lstDelete) {
        for (AlarmEmployeeList alarmEmpInput : lstInput) {
            // tim theo employeeId
            Optional<AlarmEmployeeList> alarmEmpDbOpt = lstDB.stream()
                    .filter(i -> i.getEmployeeID().equals(alarmEmpInput.getEmployeeID())).findAny();
            // neu data theo employee co trong db thi xu ly tiep (ton tai thi them vao listDelete, khong thi them vao listInsert)
            if (alarmEmpDbOpt.isPresent()) {
                AlarmEmployeeList alarmEmpDb = alarmEmpDbOpt.get();
                List<AlarmExtractInfoResult> tempInsert = new ArrayList<>();
                List<AlarmExtractInfoResult> tempDelete = new ArrayList<>();
                alarmEmpInput.getAlarmExtractInfoResults().forEach(inputInfo -> {
                    // tim theo checkNo, checkCode, category, checkType
                    Optional<AlarmExtractInfoResult> dbInfoOpt = alarmEmpDb.getAlarmExtractInfoResults().stream()
                            .filter(i -> i.getAlarmCheckConditionNo().equals(inputInfo.getAlarmCheckConditionNo())
                                    && i.getAlarmCheckConditionCode().v().equals(inputInfo.getAlarmCheckConditionCode().v())
                                    && i.getAlarmCategory() == inputInfo.getAlarmCategory()
                                    && i.getAlarmCategory().value == period.getCategory()
                                    && i.getAlarmListCheckType() == inputInfo.getAlarmListCheckType()).findAny();
                    // neu ton tai trong db thi xu ly tiep (ton tai thi them vao listDelete, khong thi them vao listInsert)
                    if (dbInfoOpt.isPresent()) {
                        AlarmExtractInfoResult dbInfo = dbInfoOpt.get();
                        List<ExtractResultDetail> tempInsertDetails = inputInfo.getExtractionResultDetails().stream()
                                .filter(i -> dbInfo.getExtractionResultDetails().stream().noneMatch(j -> j.getPeriodDate().getStartDate().get().equals(i.getPeriodDate().getStartDate().get())))
                                .collect(Collectors.toList());
                        List<ExtractResultDetail> tempDeleteDetails = dbInfo.getExtractionResultDetails().stream()
                                .filter(i -> inputInfo.getExtractionResultDetails().stream().noneMatch(j -> j.getPeriodDate().getStartDate().get().equals(i.getPeriodDate().getStartDate().get())))
                                .collect(Collectors.toList());
                        if (!tempInsertDetails.isEmpty()) {
                            tempInsert.add(new AlarmExtractInfoResult(
                                    inputInfo.getAlarmCheckConditionNo(),
                                    inputInfo.getAlarmCheckConditionCode(),
                                    inputInfo.getAlarmCategory(),
                                    inputInfo.getAlarmListCheckType(),
                                    tempInsertDetails
                            ));
                        }
                        if (!tempDeleteDetails.isEmpty()) {
                            tempDelete.add(new AlarmExtractInfoResult(
                                    dbInfo.getAlarmCheckConditionNo(),
                                    dbInfo.getAlarmCheckConditionCode(),
                                    dbInfo.getAlarmCategory(),
                                    dbInfo.getAlarmListCheckType(),
                                    tempDeleteDetails
                            ));
                        }
                    } else if (inputInfo.getAlarmCategory().value == period.getCategory()) {
                        tempInsert.add(inputInfo);
                    }
                });
                tempDelete.addAll(alarmEmpDb.getAlarmExtractInfoResults().stream()
                        .filter(j -> j.getAlarmCategory().value == period.getCategory()
                                && alarmEmpInput.getAlarmExtractInfoResults().stream()
                                .noneMatch(i -> i.getAlarmCheckConditionNo().equals(j.getAlarmCheckConditionNo())
                                        && i.getAlarmCheckConditionCode().v().equals(j.getAlarmCheckConditionCode().v())
                                        && i.getAlarmCategory() == j.getAlarmCategory()
                                        && i.getAlarmListCheckType() == j.getAlarmListCheckType())
                        ).collect(Collectors.toList()));
                if (!tempInsert.isEmpty()) {
                    lstInsert.add(new AlarmEmployeeList(tempInsert, alarmEmpInput.getEmployeeID()));
                }
                if (!tempDelete.isEmpty()) {
                    lstDelete.add(new AlarmEmployeeList(tempDelete, alarmEmpDb.getEmployeeID()));
                }
            } else {
                lstInsert.add(new AlarmEmployeeList(
                        alarmEmpInput.getAlarmExtractInfoResults().stream().filter(i -> i.getAlarmCategory().value == period.getCategory()).collect(Collectors.toList()),
                        alarmEmpInput.getEmployeeID()
                ));
            }
        }
        List<AlarmEmployeeList> filteredBySid = lstDB.stream()
                .filter(i -> lstInput.stream().noneMatch(j -> j.getEmployeeID().equals(i.getEmployeeID())))
                .collect(Collectors.toList());
        if (!filteredBySid.isEmpty()) {
            lstDelete.addAll(filteredBySid.stream().map(i -> new AlarmEmployeeList(
                    i.getAlarmExtractInfoResults().stream().filter(j -> j.getAlarmCategory().value == period.getCategory()).collect(Collectors.toList()),
                    i.getEmployeeID()
            )).collect(Collectors.toList()));
        }
    }

    @AllArgsConstructor
    public class RequireImpl implements ConvertAlarmListToTopPageAlarmDataService.Require, CreateAlarmDataTopPageService.Require,
            CreateTopPageAlarmDataOfPersonService.Require {
        private PersisAlarmListExtractResultRepository alarmExtractResultRepo;
        private TopPageAlarmAdapter topPageAlarmAdapter;
        private EmployeeWorkplaceAdapter employeeWorkplaceAdapter;
        private AdministratorReceiveAlarmMailAdapter adminReceiveAlarmMailAdapter;
        private AlarmMailSettingsAdapter mailAdapter;
        private RoleSetExportAdapter roleAdapter;
        private UserEmployeeAdapter userEmployeeAdapter;
        private AffWorkplaceAdapter affWorkplaceAdapter;
        private WkpManagerAdapter workplaceAdapter;
        private AlarmMailSendingRoleRepository alarmMailSendingRoleRepo;
        private AuthWorkPlaceAdapter authWorkPlaceAdapter;

        @Override
        public Optional<PersistenceAlarmListExtractResult> getAlarmListExtractionResult(String companyId, String patternCode, String autoRunCode, List<String> empIds) {
            return alarmExtractResultRepo.getAlarmExtractResult(companyId, patternCode, autoRunCode);
        }

        @Override
        public Optional<UpdateProcessAutoExecution> getUpdateProcessExecutionByCidAndExecCd(String companyId, String execItemCd) {
            return Optional.empty();
        }

        @Override
        public List<AffAtWorkplaceExport> getWorkplaceId(List<String> sIds, GeneralDate baseDate) {
            return employeeWorkplaceAdapter.getWorkplaceId(sIds, baseDate);
        }

        @Override
        public void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt) {
            topPageAlarmAdapter.create(companyId, alarmInfos, delInfoOpt);
        }

        @Override
        public Map<String, List<String>> getAdminReceiveAlarmMailByWorkplaceIds(List<String> workplaceIds) {
            return adminReceiveAlarmMailAdapter.getAdminReceiveAlarmMailByWorkplaceIds(workplaceIds);
        }

        @Override
        public Optional<String> getUserIDByEmpID(String employeeID) {
            return userEmployeeAdapter.getUserIDByEmpID(employeeID);
        }

        @Override
        public Optional<RoleSetExportDto> getRoleSetFromUserId(String userId, GeneralDate baseDate) {
            return roleAdapter.getRoleSetFromUserId(userId, baseDate);
        }

        @Override
        public Optional<AlarmMailSendingRole> findAlarmMailSendRole(String cid, int individualWkpClassify) {
            return alarmMailSendingRoleRepo.find(cid, individualWkpClassify);
        }

        @Override
        public List<MailExportRolesDto> findRoleByCID(String companyId) {
            return mailAdapter.findByCompanyId(companyId);
        }

        @Override
        public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId) {
            return affWorkplaceAdapter.getWorkplaceIdAndUpper(companyId, baseDate, workplaceId);
        }

        @Override
        public List<WkpManagerImport> findByPeriodAndBaseDate(String wkpId, GeneralDate baseDate) {
            return workplaceAdapter.findByPeriodAndBaseDate(wkpId, baseDate);
        }

        @Override
        public List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdAndBaseDate(String workPlaceId, GeneralDate baseDate) {
            return authWorkPlaceAdapter.getWorkHisItemfromWkpIdAndBaseDate(workPlaceId, baseDate);
        }
    }
}
