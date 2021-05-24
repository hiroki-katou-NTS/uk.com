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
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.ExtractionAlarmPeriodDate;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
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
    @Override
    public void persisTopPageProcessing(String runCode, String pattentCd, List<String> lstSid,
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
        } else if(optPersisAlarmExtractResult.isPresent() && CollectionUtil.isEmpty(alarmResult.getAlarmListExtractResults())){
            alarmExtractResultRepo.delete(optPersisAlarmExtractResult.get());
        } else if (!optPersisAlarmExtractResult.isPresent() && !CollectionUtil.isEmpty(alarmResult.getAlarmListExtractResults())) {
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
                List<AlarmExtractionCondition> extractConds = alarmExtractConds.stream().filter(e -> e.getAlarmCategory().value == p.getCategory()).collect(Collectors.toList());
                if (extractConds.isEmpty()) {
                    return;
                }

                List<AlarmEmployeeList> lstExtractResultDB = new ArrayList<>();
                extractConds.stream().forEach(c -> {
                    persisAlarmExtract.getAlarmListExtractResults().stream().filter(Objects::nonNull).forEach(x -> {
                        List<AlarmExtractInfoResult> infoResults = new ArrayList<>();
                        x.getAlarmExtractInfoResults().stream().filter(Objects::nonNull).forEach(y -> {
                            List<ExtractResultDetail> details = new ArrayList<>();
                            y.getExtractionResultDetails().stream().filter(Objects::nonNull).forEach(z -> {
                                if (z.getPeriodDate() == null) {
                                    if (c.getAlarmCategory().value == y.getAlarmCategory().value && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                                            && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())) {
                                        details.add(z);
                                    }
                                } else if (!z.getPeriodDate().getEndDate().isPresent()) {
                                    if (c.getAlarmCategory().value == y.getAlarmCategory().value && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                                            && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
                                            && z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getStartDate().get().beforeOrEquals(p.getEndDate())) {
                                        details.add(z);
                                    }
                                } else {
                                    if (c.getAlarmCategory().value == y.getAlarmCategory().value && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                                            && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
                                            && z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getEndDate().get().beforeOrEquals(p.getEndDate())) {
                                        details.add(z);
                                    }
                                }
                            });
                            if (!details.isEmpty()) {
                                infoResults.add(new AlarmExtractInfoResult(
                                        y.getAlarmCheckConditionNo(),
                                        y.getAlarmCheckConditionCode(),
                                        y.getAlarmCategory(),
                                        y.getAlarmListCheckType(),
                                        details));
                            }
                        });
                        if (!infoResults.isEmpty()) {
                            val lstExistEmp = lstExtractResultDB.stream().filter(a -> a.getEmployeeID().equals(x.getEmployeeID())).findFirst();
                            if (!lstExistEmp.isPresent()) {
                                lstExtractResultDB.add(new AlarmEmployeeList(infoResults, x.getEmployeeID()));
                            } else {
                                lstExtractResultDB.stream().filter(a -> a.getEmployeeID().equals(x.getEmployeeID())).forEach(e -> e.getAlarmExtractInfoResults().addAll(infoResults));
                            }
                        }
                    });
                });

//                List<AlarmEmployeeList> lstExtractResultDB2 = new ArrayList<>();
//                extractConds.stream().forEach(c -> {
//                    val temp = persisAlarmExtract.getAlarmListExtractResults().stream()
//                            .filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> c.getAlarmCategory().value == y.getAlarmCategory().value
//                                            && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
//                                            && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value
//                                            && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
//                                && y.getExtractionResultDetails().stream().anyMatch(z -> {
//                            if (z.getPeriodDate() == null) {
//                                return true;
//                            } else if (!z.getPeriodDate().getEndDate().isPresent()) {
//                                return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getStartDate().get().beforeOrEquals(p.getEndDate());
//                            } else {
//                                return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getEndDate().get().beforeOrEquals(p.getEndDate());
//                            }})
//                            )).collect(Collectors.toList());
//                    lstExtractResultDB2.addAll(temp);
//                });

                List<AlarmEmployeeList> lstExtractResultInput = alarmResult.getAlarmListExtractResults().stream()
                        .filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> y.getAlarmCategory().value == p.getCategory()))
                        .collect(Collectors.toList());
                if (lstExtractResultDB.isEmpty()) {
                    lstExResultInsert.addAll(lstExtractResultInput);
                } else {
                    List<AlarmEmployeeList> lstDelete = new ArrayList<>();
                    List<AlarmEmployeeList> lstInput = new ArrayList<>();

                    dataProcessingInputOutput(lstExtractResultInput, lstExtractResultDB, lstInput, lstDelete);
                    filterData(lstExtractResultInput, lstExtractResultDB, lstInput, lstDelete);
                    lstExResultInsert.addAll(lstInput);
                    lstExResultDelete.addAll(lstDelete);
                }
            });

            //Delete: 今回のアラーム結果がないがデータベースに存在している場合データベースを削除
            if(!CollectionUtil.isEmpty(lstExResultDelete)){
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
            if(!CollectionUtil.isEmpty(lstExResultInsert)){
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

    private void dataProcessingInputOutput(List<AlarmEmployeeList> lstInput, List<AlarmEmployeeList> lstDB, List<AlarmEmployeeList> lstInsert, List<AlarmEmployeeList> lstDelete) {
        List<AlarmEmployeeList> lstDiffEmp = lstInput.stream().filter(x -> lstDB.stream().anyMatch(y -> !y.getEmployeeID().equals(x.getEmployeeID()))).collect(Collectors.toList());

        if (!lstDiffEmp.isEmpty()) {
            lstInsert.addAll(lstDiffEmp);

            List<AlarmEmployeeList> lstInputRemaining = lstInput.stream().filter(x -> lstDiffEmp.stream().anyMatch(y -> !y.getEmployeeID().equals(x.getEmployeeID()))).collect(Collectors.toList());
            List<AlarmEmployeeList> lstDBRemaining = lstDB.stream().filter(x -> lstDiffEmp.stream().anyMatch(y -> !y.getEmployeeID().equals(x.getEmployeeID()))).collect(Collectors.toList());
            if (lstInputRemaining.isEmpty() && !lstDBRemaining.isEmpty()) {
                lstDelete.addAll(lstDBRemaining);
            }
            if (!lstInputRemaining.isEmpty() && lstDBRemaining.isEmpty()) {
                lstInsert.addAll(lstInputRemaining);
            }
            if (!lstInputRemaining.isEmpty() && !lstDBRemaining.isEmpty()) {
                filterData(lstInputRemaining, lstDBRemaining, lstInsert, lstDelete);
            }
        } else {
            filterData(lstInput, lstDB, lstInsert, lstDelete);

//            lstInput.stream().forEach(b -> {
//                b.getAlarmExtractInfoResults().stream().forEach(c -> {
//                    c.getExtractionResultDetails().forEach(d -> {
//                        List<AlarmEmployeeList> dataTemp = lstDB.stream().filter(x -> x.getAlarmExtractInfoResults().stream().allMatch(y -> c.getAlarmCategory().value == y.getAlarmCategory().value
//                                && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
//                                && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value
//                                && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
//                                && y.getExtractionResultDetails().stream().anyMatch(z -> z.getPeriodDate().getStartDate().get().compareTo(d.getPeriodDate().getStartDate().get()) == 0)
//                        )).collect(Collectors.toList());
//                        if (!dataTemp.isEmpty()) {
//                            lstInsert.addAll(dataTemp);
//                        }
//                    });
//
//                });
//            });
        }
    }

    private void filterData(List<AlarmEmployeeList> lstInput, List<AlarmEmployeeList> lstDB, List<AlarmEmployeeList> lstInsert, List<AlarmEmployeeList> lstDelete) {
        List<AlarmEmployeeList> lstExistDb = new ArrayList<>();
        for (AlarmEmployeeList b : lstInput) {
            List<AlarmEmployeeList> lstExtract = new ArrayList<>();
            boolean isPause = false;
            for (AlarmExtractInfoResult c : b.getAlarmExtractInfoResults()) {
                if (isPause) break;
                for (ExtractResultDetail d : c.getExtractionResultDetails()) {
                    val dataTemp = lstDB.stream().filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> c.getAlarmCategory().value == y.getAlarmCategory().value
                            && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                            && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value
                            && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
                            && y.getExtractionResultDetails().stream().anyMatch(z -> z.getPeriodDate().getStartDate().get().compareTo(d.getPeriodDate().getStartDate().get()) == 0)
                    )).findFirst();
                    if (dataTemp.isPresent()) {
                        lstExtract.add(dataTemp.get());
                        isPause = true;
                        break;
                    }
                }
            }
            lstExistDb.addAll(lstExtract);
        }

        // Remove existing items from lstInput
        List<AlarmEmployeeList> lstInputTemp = new ArrayList<>(lstInput);
        lstExistDb.forEach(a -> {
            a.getAlarmExtractInfoResults().forEach(b -> {
                b.getExtractionResultDetails().forEach(c -> {
                    lstInputTemp.stream().filter(e -> e.getEmployeeID().equals(a.getEmployeeID()))
                            .forEach(x -> x.getAlarmExtractInfoResults().removeIf(y -> b.getAlarmCategory().value == y.getAlarmCategory().value
                                    && b.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                                    && b.getAlarmListCheckType().value == y.getAlarmListCheckType().value
                                    && b.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
                                    && y.getExtractionResultDetails().stream().anyMatch(z -> z.getPeriodDate().getStartDate().get().compareTo(c.getPeriodDate().getStartDate().get()) == 0)));
                });
            });
        });
        if (!lstInputTemp.isEmpty()) {
            lstInsert.addAll(lstInputTemp);
        }

//        // Remove the database if there is no current alarm result but exists in the database
//        List<AlarmEmployeeList> lstDbTemp = new ArrayList<>(lstDB);
//        lstInput.forEach(a -> {
//            a.getAlarmExtractInfoResults().forEach(b -> {
//                b.getExtractionResultDetails().forEach(c -> {
//                    lstDbTemp.stream().filter(e -> e.getEmployeeID().equals(a.getEmployeeID()))
//                            .forEach(x -> x.getAlarmExtractInfoResults().removeIf(y -> b.getAlarmCategory().value == y.getAlarmCategory().value
//                                    && b.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
//                                    && b.getAlarmListCheckType().value == y.getAlarmListCheckType().value
//                                    && b.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
//                                    && y.getExtractionResultDetails().stream().anyMatch(z -> z.getPeriodDate().getStartDate().get().compareTo(c.getPeriodDate().getStartDate().get()) == 0)));
//                });
//            });
//        });
//        if (!lstDbTemp.isEmpty()) {
//            lstDelete.addAll(lstDbTemp);
//        }
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
            return employeeAlarmListAdapter.getListEmployeeId(workplaceId, referenceDate).stream().distinct().collect(Collectors.toList());
        }

        @Override
        public void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt) {
            topPageAlarmAdapter.create(companyId, alarmInfos, delInfoOpt);
        }
    }
}
