package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.task.tran.TransactionService;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.alarm.AffAtWorkplaceExport;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeAlarmListAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeWorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmAdapter;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.PeriodByAlarmCategory;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlarmTopPageProcessingService {
    @Inject
    private PersisAlarmListExtractResultRepository alarmExtractResultRepo;
    @Inject
    private TopPageAlarmAdapter topPageAlarmAdapter;
    @Inject
    private EmployeeWorkplaceAdapter employeeWorkplaceAdapter;
    @Inject
    private EmployeeAlarmListAdapter employeeAlarmListAdapter;
    @Inject
    protected TransactionService transaction;

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
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void PersisTopPageProcessing(String runCode, String pattentCd, List<String> lstSid,
                                      List<PeriodByAlarmCategory> lstCategoryPeriod,
                                      PersistenceAlarmListExtractResult alarmResult,
                                      List<AlarmExtractionCondition> alarmExtractConds,
                                      boolean isDisplayByAdmin, boolean isDisplayByPerson) {
        if (runCode.equals("Z")) {
            return;
        }

        // パターン、社員IDからアラーム抽出結果を取得
        val optPersisAlarmExtractResult = alarmExtractResultRepo.getAlarmExtractResult(runCode, pattentCd, lstSid);
        if (!optPersisAlarmExtractResult.isPresent() && CollectionUtil.isEmpty(alarmResult.getAlarmListExtractResults())) {
            return;
        } else if (!optPersisAlarmExtractResult.isPresent()) {
            alarmExtractResultRepo.insert(alarmResult);
        } else {
            List<AlarmEmployeeList> lstExResultInsert = new ArrayList<>();
            List<AlarmEmployeeList> lstExResultDelete = new ArrayList<>();
            PersistenceAlarmListExtractResult persisAlarmExtract = optPersisAlarmExtractResult.get();

            if (lstCategoryPeriod.isEmpty()) {
                return;
            }

            lstCategoryPeriod.forEach(p -> {
                //永続化のアラームリスト抽出結果を絞り込む
                Optional<AlarmExtractionCondition> extractCond = alarmExtractConds.stream().filter(e -> e.getAlarmCategory().value == p.getCategory()).findFirst();
                if (!extractCond.isPresent()) {
                    return;
                }
                List<AlarmEmployeeList> lstExtractResultDB = persisAlarmExtract.getAlarmListExtractResults().stream()
                        .filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> y.getAlarmCategory().value == extractCond.get().getAlarmCategory().value
                                && y.getAlarmCheckConditionCode().v().equals(extractCond.get().getAlarmCheckConditionCode().v())
                                && y.getAlarmListCheckType().value == extractCond.get().getAlarmListCheckType().value
                                && y.getAlarmCheckConditionNo().equals(extractCond.get().getAlarmCheckConditionNo())
                                && y.getExtractionResultDetails().stream().anyMatch(z -> {
                            if (z.getPeriodDate() == null || !z.getPeriodDate().getStartDate().isPresent() || !z.getPeriodDate().getEndDate().isPresent()) {
                                return true;
                            } else if (!z.getPeriodDate().getEndDate().isPresent()) {
                                return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getStartDate().get().beforeOrEquals(p.getEndDate());
                            } else {
                                return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getEndDate().get().beforeOrEquals(p.getEndDate());
                            }
                        }))).collect(Collectors.toList());

                List<AlarmEmployeeList> lstExtractResultInput = alarmResult.getAlarmListExtractResults().stream()
                        .filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> y.getAlarmCategory().value == p.getCategory()))
                        .collect(Collectors.toList());
                if (lstExtractResultDB.isEmpty()) {
                    lstExResultInsert.addAll(lstExtractResultInput);
                } else {
                    List<AlarmEmployeeList> lstDelete = new ArrayList<>();
                    // Filter two list
                    filterData(lstExtractResultDB, lstExtractResultInput, lstDelete);
                    lstExResultDelete.addAll(lstDelete);
                    //Delete: 今回のアラーム結果がないがデータベースに存在している場合データベースを削除
                    PersistenceAlarmListExtractResult persisExtractResultDelete = new PersistenceAlarmListExtractResult(
                            persisAlarmExtract.getAlarmPatternCode(),
                            persisAlarmExtract.getAlarmPatternName(),
                            lstExResultDelete,
                            persisAlarmExtract.getCompanyID(),
                            persisAlarmExtract.getAutoRunCode()
                    );
                    alarmExtractResultRepo.delete(persisExtractResultDelete);

                    //Insert: 今回のアラーム結果がデータベースに存在してない場合データベースのデータを追加
                    List<AlarmEmployeeList> lstInput = new ArrayList<>();
                    // Filter two list
                    filterData(lstExtractResultInput, lstExtractResultDB, lstInput);
                    lstExResultInsert.addAll(lstInput);
                    PersistenceAlarmListExtractResult persisExtractResultInsert = new PersistenceAlarmListExtractResult(
                            alarmResult.getAlarmPatternCode(),
                            alarmResult.getAlarmPatternName(),
                            lstExResultInsert,
                            alarmResult.getCompanyID(),
                            alarmResult.getAutoRunCode()
                    );
                    alarmExtractResultRepo.insert(persisExtractResultInsert);
                }
            });
        }

        //アラームリストからトップページアラームデータに変換する
        RequireImpl require = new RequireImpl(alarmExtractResultRepo, topPageAlarmAdapter, employeeWorkplaceAdapter, employeeAlarmListAdapter);
        List<AtomTask> atomTasks = ConvertAlarmListToTopPageAlarmDataService.convert(require, AppContexts.user().companyId(), lstSid,
                new AlarmPatternCode(pattentCd), new ExecutionCode(runCode), isDisplayByAdmin, isDisplayByPerson);

		if(!atomTasks.isEmpty()){
			transaction.execute(() -> {
				for (AtomTask atomTask : atomTasks) {
						atomTask.run();
				}
			});
		}
    }

    private void filterData(List<AlarmEmployeeList> lstX, List<AlarmEmployeeList> lstY, List<AlarmEmployeeList> lstResult) {
        List<String> lstEmpX = lstX.stream().map(AlarmEmployeeList::getEmployeeID).collect(Collectors.toList());
        List<String> lstEmpY = lstY.stream().map(AlarmEmployeeList::getEmployeeID).collect(Collectors.toList());
        List<String> filterEmps = lstEmpX.stream().filter(x -> !lstEmpY.contains(x)).collect(Collectors.toList());
        List<AlarmEmployeeList> lstNotContainEmp1 = lstX.stream().filter(x -> filterEmps.contains(x.getEmployeeID())).collect(Collectors.toList());

        List<AlarmEmployeeList> lstNotContainEmp = lstX.stream().filter(x -> lstY.stream().anyMatch(y -> !y.getEmployeeID().equals(x.getEmployeeID()))).collect(Collectors.toList());
        if (!lstNotContainEmp.isEmpty()) {
            lstResult.addAll(lstNotContainEmp);
        }

        List<AlarmEmployeeList> lstContainEmp = lstX.stream().filter(x -> lstY.stream().anyMatch(y -> y.getEmployeeID().equals(x.getEmployeeID()))).collect(Collectors.toList());
        if (!lstContainEmp.isEmpty()) {
            lstContainEmp.stream().forEach(x -> {
                lstY.stream().forEach(y -> {
                    List<AlarmExtractInfoResult> exInfos = new ArrayList<>();
                    x.getAlarmExtractInfoResults().stream().forEach(x1 -> {
                        y.getAlarmExtractInfoResults().stream().forEach(y1 -> {
                            if (x1.getAlarmCategory().value != y1.getAlarmCategory().value) {
                                exInfos.add(x1);
                            } else {
                                if (!x1.getAlarmCheckConditionCode().v().equals(y1.getAlarmCheckConditionCode().v())) {
                                    exInfos.add(x1);
                                } else {
                                    if (x1.getAlarmListCheckType().value != y1.getAlarmListCheckType().value) {
                                        exInfos.add(x1);
                                    } else {
                                        if (!x1.getAlarmCheckConditionNo().equals(y1.getAlarmCheckConditionNo())) {
                                            exInfos.add(x1);
                                        } else {
                                            List<ExtractResultDetail> details = new ArrayList<>();
                                            x1.getExtractionResultDetails().stream().forEach(x2 -> {
                                                y1.getExtractionResultDetails().stream().forEach(y2 -> {
                                                    if ((x2.getPeriodDate().getStartDate().isPresent() && y2.getPeriodDate().getStartDate().isPresent()) &&
                                                            x2.getPeriodDate().getStartDate().get() != y2.getPeriodDate().getStartDate().get()) {
                                                        details.add(x2);
                                                    }
                                                });
                                            });
                                            exInfos.add(new AlarmExtractInfoResult(
                                                    x1.getAlarmCheckConditionNo(),
                                                    x1.getAlarmCheckConditionCode(),
                                                    x1.getAlarmCategory(),
                                                    x1.getAlarmListCheckType(),
                                                    details
                                            ));
                                        }
                                    }
                                }
                            }
                        });
                    });
                    lstResult.add(new AlarmEmployeeList(exInfos, x.getEmployeeID()));
                });
            });
        }
    }

    @AllArgsConstructor
    public class RequireImpl implements ConvertAlarmListToTopPageAlarmDataService.Require, CreateAlarmDataTopPageService.Require,
            CreateTopPageAlarmDataOfPersonService.Require{
        private PersisAlarmListExtractResultRepository alarmExtractResultRepo;
        private TopPageAlarmAdapter topPageAlarmAdapter;
        private EmployeeWorkplaceAdapter employeeWorkplaceAdapter;
        private EmployeeAlarmListAdapter employeeAlarmListAdapter;

        @Override
        public Optional<PersistenceAlarmListExtractResult> getAlarmListExtractionResult(String companyId, String patternCode, String autoRunCode) {
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
        public List<String> getListEmployeeId(String workplaceId, GeneralDate referenceDate) {
            return employeeAlarmListAdapter.getListEmployeeId(workplaceId, referenceDate);
        }

        @Override
        public void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt) {
            topPageAlarmAdapter.create(companyId, alarmInfos, delInfoOpt);
        }
    }
}
