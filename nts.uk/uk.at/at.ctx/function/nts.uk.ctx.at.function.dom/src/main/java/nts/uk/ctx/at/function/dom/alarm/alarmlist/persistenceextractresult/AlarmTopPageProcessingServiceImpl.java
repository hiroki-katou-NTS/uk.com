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
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        } else {
            if(optPersisAlarmExtractResult.isPresent() && CollectionUtil.isEmpty(alarmResult.getAlarmListExtractResults())){
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

                for (PeriodByAlarmCategory p : lstCategoryPeriod) {//永続化のアラームリスト抽出結果を絞り込む
                    List<AlarmExtractionCondition> extractConds = alarmExtractConds.stream().filter(e -> e.getAlarmCategory().value == p.getCategory()).collect(Collectors.toList());
                    if (extractConds.isEmpty()) {
                        continue;
                    }

                    List<AlarmEmployeeList> lstExtractResultDB = new ArrayList<>();
                    for (AlarmExtractionCondition c : extractConds) {
                        val temp = persisAlarmExtract.getAlarmListExtractResults().stream()
                                .filter(x -> x.getAlarmExtractInfoResults().stream().anyMatch(y -> c.getAlarmCategory().value == y.getAlarmCategory().value
                                                && c.getAlarmCheckConditionCode().v().equals(y.getAlarmCheckConditionCode().v())
                                                && c.getAlarmListCheckType().value == y.getAlarmListCheckType().value
                                                && c.getAlarmCheckConditionNo().equals(y.getAlarmCheckConditionNo())
                                                && y.getExtractionResultDetails().stream().anyMatch(z -> {
                                            if (z.getPeriodDate() == null) {
                                                return true;
                                            } else if (!z.getPeriodDate().getEndDate().isPresent()) {
                                                return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getStartDate().get().beforeOrEquals(p.getEndDate());
                                            } else {
                                                return z.getPeriodDate().getStartDate().get().afterOrEquals(p.getStartDate()) && z.getPeriodDate().getEndDate().get().beforeOrEquals(p.getEndDate());
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
                    if (lstExtractResultDB.isEmpty()) {
                        lstExResultInsert.addAll(lstExtractResultInput);
                    } else {
                        List<AlarmEmployeeList> lstDelete = new ArrayList<>();
                        List<AlarmEmployeeList> lstInput = new ArrayList<>();

                        dataProcessingInputOutput(lstExtractResultInput, lstExtractResultDB, lstInput, lstDelete);
                        lstExResultInsert.addAll(lstInput);
                        lstExResultDelete.addAll(lstDelete);
                    }
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
    }

    private void dataProcessingInputOutput(List<AlarmEmployeeList> lstInput, List<AlarmEmployeeList> lstDB, List<AlarmEmployeeList> lstInsert, List<AlarmEmployeeList> lstDelete) {
        // Chuyển thành map lồng map
        Map<String, Map<AlarmExtractInfoKey, List<ExtractResultDetail>>> mapInput = lstInput.stream()
                .collect(Collectors.toMap(AlarmEmployeeList::getEmployeeID, x -> {
                    return x.getAlarmExtractInfoResults().stream()
                            .collect(Collectors.toMap(y ->
                                    new AlarmExtractInfoKey(y.getAlarmCheckConditionNo(), y.getAlarmCheckConditionCode(), y.getAlarmCategory(), y.getAlarmListCheckType()), y -> y.getExtractionResultDetails()));
                }));
        // Chuyển thành map lồng map
        Map<String, Map<AlarmExtractInfoKey, List<ExtractResultDetail>>> mapDb = lstDB.stream()
                .collect(Collectors.toMap(AlarmEmployeeList::getEmployeeID, x -> {
                    return x.getAlarmExtractInfoResults().stream()
                            .collect(Collectors.toMap(y ->
                                    new AlarmExtractInfoKey(y.getAlarmCheckConditionNo(), y.getAlarmCheckConditionCode(), y.getAlarmCategory(), y.getAlarmListCheckType()), y -> y.getExtractionResultDetails()));
                }));

//        mapInput.forEach((key, value) -> mapDb.entrySet().removeIf(d -> !d.getKey().contains(key)));
        Set<String> setKeyMapDb = new HashSet<>();
        for (val entryDb : mapDb.entrySet()) {
            Map<AlarmExtractInfoKey, List<ExtractResultDetail>> db = mapDb.get(entryDb.getKey());
            List<AlarmExtractInfoResult> lstInfoResult = new ArrayList<>();
            for (Map.Entry<AlarmExtractInfoKey, List<ExtractResultDetail>> detailDb : db.entrySet()) {
                boolean exist = mapInput.containsKey(entryDb.getKey());
                if (exist) { // Nếu trùng SID -> check tiếp điều kiện khác
                    val input = mapInput.get(entryDb.getKey());
                    val dbKey = detailDb.getKey();
                    if (input.containsKey(dbKey)) { // Nếu trùng SID, No, code, type, cate -> check tiếp điều kiện khác
                        List<ExtractResultDetail> highestInput = mapInput.get(entryDb.getKey()).get(detailDb.getKey());
                        val dateInputs = highestInput.stream().map(i -> i.getPeriodDate().getStartDate().get().toString()).collect(Collectors.toList());

                        // Nếu trùng SID, No, code, type, cate nhưng KHÔNG TRÙNG startDate với trong lstInput => dữ liệu DB đã cũ => add vào lstDelete để xoá đi
                        List<ExtractResultDetail> lstDiffInput = detailDb.getValue().stream().filter(h -> !dateInputs.contains(h.getPeriodDate().getStartDate().get().toString())).collect(Collectors.toList());
                        if (!lstDiffInput.isEmpty()) {
                            lstInfoResult.add(new AlarmExtractInfoResult(
                                    detailDb.getKey().getAlarmCheckConditionNo(),
                                    new AlarmCheckConditionCode(detailDb.getKey().getAlarmCheckConditionCode().v()),
                                    AlarmCategory.of(detailDb.getKey().getAlarmCategory().value),
                                    AlarmListCheckType.of(detailDb.getKey().getAlarmListCheckType().value),
                                    lstDiffInput
                            ));
                        }
                    } else { // Nếu trùng SID nhưng không trùng code, type, cate, No => dữ liệu Db cũ => add to lstDelete
                        lstInfoResult.add(new AlarmExtractInfoResult(
                                detailDb.getKey().getAlarmCheckConditionNo(),
                                new AlarmCheckConditionCode(detailDb.getKey().getAlarmCheckConditionCode().v()),
                                AlarmCategory.of(detailDb.getKey().getAlarmCategory().value),
                                AlarmListCheckType.of(detailDb.getKey().getAlarmListCheckType().value),
                                detailDb.getValue()
                        ));
                    }

                } else { // Nếu không trùng SID => DB có nhưng Input ko có => dữ liệu Db cũ => add vào lstDelete
                    val dataDeletes = lstDB.stream().filter(x -> x.getEmployeeID().equals(entryDb.getKey())).collect(Collectors.toList());
                    // Add vào lstDelete để xoá khỏi DB
                    if (!dataDeletes.isEmpty()) {
                        lstDelete.addAll(dataDeletes);
                    }
                    setKeyMapDb.add(entryDb.getKey());
                }
            }
            if (!lstInfoResult.isEmpty()) {
                val emps = lstDelete.stream().filter(x -> x.getEmployeeID().equals(entryDb.getKey())).collect(Collectors.toList());
                if (emps.isEmpty()) {
                    lstDelete.add(new AlarmEmployeeList(lstInfoResult, entryDb.getKey()));
                } else {
                    lstDelete.forEach(x -> {
                        if (x.getEmployeeID().equals(entryDb.getKey())) {
                            x.getAlarmExtractInfoResults().addAll(lstInfoResult);
                        }
                    });
                }
            }
        }

        // Check SID: có ở input & có ở db --> không cần insert vào nữa (xoá khỏi list input)
        mapDb.forEach((key, value) -> mapInput.entrySet().removeIf(i -> i.getKey().contains(key)));

        for (Map.Entry<String, Map<AlarmExtractInfoKey, List<ExtractResultDetail>>> entryIp : mapInput.entrySet()) {
            Map<AlarmExtractInfoKey, List<ExtractResultDetail>> ip = mapInput.get(entryIp.getKey());
            List<AlarmExtractInfoResult> lstInfoResult = new ArrayList<>();
            for (Map.Entry<AlarmExtractInfoKey, List<ExtractResultDetail>> detailIp : ip.entrySet()) {
                boolean exist = mapDb.containsKey(entryIp.getKey());
                if (exist) { // Nếu trùng SID -> check tiếp điều kiện khác
                    if (mapDb.get(entryIp.getKey()).containsKey(detailIp.getKey())) { // Nếu trùng SID, No, code, type, cate
                        List<ExtractResultDetail> highestDb = mapDb.get(entryIp.getKey()).get(detailIp.getKey());
                        val dateDbs = highestDb.stream().map(i -> i.getPeriodDate().getStartDate().get().toString()).collect(Collectors.toList());
//                        val dateInputs = ip.get(detailIp.getKey()).stream().map(i -> i.getPeriodDate().getStartDate().get().toString()).collect(Collectors.toList());

//                        // Nếu trùng SID, No, code, type, cate nhưng KHÔNG TRÙNG startDate với trong lstInput => dữ liệu DB đã cũ => add vào lstDelete để xoá đi
//                        List<ExtractResultDetail> lstDiffDb = highestDb.stream().filter(h -> !dateInputs.contains(h.getPeriodDate().getStartDate().get())).collect(Collectors.toList());
//                        if (!lstDiffDb.isEmpty()) {
//                            lstInfoResult.add(new AlarmExtractInfoResult(
//                                    detailIp.getKey().getAlarmCheckConditionNo(),
//                                    new AlarmCheckConditionCode(detailIp.getKey().getAlarmCheckConditionCode().v()),
//                                    AlarmCategory.of(detailIp.getKey().getAlarmCategory().value),
//                                    AlarmListCheckType.of(detailIp.getKey().getAlarmListCheckType().value),
//                                    lstDiffDb
//                            ));
//                        }

                        // Nếu trùng SID, No, code, type, cate nhưng KHÔNG TRÙNG startDate với trong lstDB => DB chưa có => add vào lstInsert để insert
                        List<ExtractResultDetail> lstDiffInput = detailIp.getValue().stream().filter(h -> !dateDbs.contains(h.getPeriodDate().getStartDate().get().toString())).collect(Collectors.toList());
                        if (!lstDiffInput.isEmpty()) {
                            lstInfoResult.add(new AlarmExtractInfoResult(
                                    detailIp.getKey().getAlarmCheckConditionNo(),
                                    new AlarmCheckConditionCode(detailIp.getKey().getAlarmCheckConditionCode().v()),
                                    AlarmCategory.of(detailIp.getKey().getAlarmCategory().value),
                                    AlarmListCheckType.of(detailIp.getKey().getAlarmListCheckType().value),
                                    lstDiffInput
                            ));
                        }

                    } else { // Nếu trùng SID nhưng không trùng code, type, cate, No => Db chưa có => add to lstInsert
                        lstInfoResult.add(new AlarmExtractInfoResult(
                                detailIp.getKey().getAlarmCheckConditionNo(),
                                new AlarmCheckConditionCode(detailIp.getKey().getAlarmCheckConditionCode().v()),
                                AlarmCategory.of(detailIp.getKey().getAlarmCategory().value),
                                AlarmListCheckType.of(detailIp.getKey().getAlarmListCheckType().value),
                                detailIp.getValue()
                        ));
                    }
                } else { // Nếu không trùng SID => có ở Input nhưng ko có ở DB => add vào lstInsert
                    val dataInputs = lstInput.stream().filter(x -> x.getEmployeeID().equals(entryIp.getKey())).collect(Collectors.toList());
                    if (!dataInputs.isEmpty()) {
                        lstInsert.addAll(dataInputs);
                    }
                }
            }
            if (!lstInfoResult.isEmpty()) {
                val emps = lstInsert.stream().filter(x -> x.getEmployeeID().equals(entryIp.getKey())).collect(Collectors.toList());
                if (emps.isEmpty()) {
                    lstInsert.add(new AlarmEmployeeList(lstInfoResult, entryIp.getKey()));
                } else {
                    lstInsert.forEach(x -> {
                        if (x.getEmployeeID().equals(entryIp.getKey())) {
                            x.getAlarmExtractInfoResults().addAll(lstInfoResult);
                        }
                    });
                }
            }
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
            return employeeAlarmListAdapter.getListEmployeeId(workplaceId, referenceDate).stream().distinct().collect(Collectors.toList());
        }

        @Override
        public void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt) {
            topPageAlarmAdapter.create(companyId, alarmInfos, delInfoOpt);
        }
    }
}
