package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternCode;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.AlarmEmployeeList;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.ExtractResultDetail;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersistenceAlarmListExtractResult;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DomainService: アラームリストからトップページアラームデータに変換する
 *
 * @author viet.tx
 */
@Stateless
public class ConvertAlarmListToTopPageAlarmDataService {

    /**
     * @param require
     * @param companyId        会社ID
     * @param employeeIDs      社員IDList
     * @param patternCode      パターンコード
     * @param executionCode    自動実行コード
     * @param isDisplayByAdmin トップページに表示(管理者)
     * @param isDisplayByPerson  トップページに表示(本人)
     * @return AtomTask
     */
    public static List<AtomTask> convert(Require require, String companyId, List<String> employeeIDs, AlarmPatternCode patternCode,
                                         ExecutionCode executionCode, boolean isDisplayByAdmin, boolean isDisplayByPerson) {
        if (!isDisplayByAdmin && !isDisplayByPerson) return Collections.emptyList();

        //永続化のアラームリスト抽出結果を取得する
        Optional<PersistenceAlarmListExtractResult> extractResult = require.getAlarmListExtractionResult(companyId, patternCode.v(), executionCode.v());

        //$アラームリストの集約結果　＝　Empty：
        List<AlarmEmployeeList> alarmListAggregateResults = new ArrayList<>();
        //$パターン名　＝　Empty
        String patternName = null;
        Map<String, GeneralDateTime> alarmListAggreRsMap = null;
        if (extractResult.isPresent()) {
            patternName = extractResult.get().getAlarmPatternName().v();
            //$アラームリストの集約結果Map = [prv-1]  アラームリストを集約する(会社ID, $アラームリストパターンの抽出結果)
            alarmListAggreRsMap = aggregateAlarmList(extractResult.get());

            alarmListAggregateResults = extractResult.get().getAlarmListExtractResults();
        }

        //$エラーがある社員IDList　＝　$アラームリストの集約結果　：　map　＄.社員ID
        List<String> employeeIdListError = alarmListAggregateResults.stream()
                .map(AlarmEmployeeList::getEmployeeID).collect(Collectors.toList());
        //$エラーがなくなった社員IDList　＝　社員IDList　：　except　$エラーがある社員IDList
        List<String> empIdListNoErrors = employeeIDs.stream()
                .filter(x -> !employeeIdListError.contains(x))
                .collect(Collectors.toList());
        //	$削除の情報　＝　Empty
        Optional<DeleteInfoAlarmImport> deleteInfo = Optional.empty();
        if (!CollectionUtil.isEmpty(empIdListNoErrors)) {
            deleteInfo = Optional.of(DeleteInfoAlarmImport.builder()
                    .alarmClassification(0)
                    .sids(empIdListNoErrors)
                    .displayAtr(0)
                    .patternCode(Optional.of(patternCode.v()))
                    .build());
        }

        //	$トップアラーム　＝　$エラーがある社員IDList：　map　トップアラームParam
        Map<String, GeneralDateTime> finalAlarmListAggreRsMap = alarmListAggreRsMap;
        String finalPatternName = patternName;
        List<TopPageAlarmImport> topAlarmList = employeeIdListError.stream().map(x -> new TopPageAlarmImport(
                0,
                finalAlarmListAggreRsMap.get(x),
                x,
                0,
                Optional.of(patternCode.v()),
                Optional.ofNullable(finalPatternName),
                Optional.empty(),
                Optional.empty(),
                new ArrayList<>()
        )).collect(Collectors.toList());

        List<AtomTask> atomTask = new ArrayList<>();
        if (isDisplayByPerson) {
            //$AtomTask = 本人のトップページアラームデータを作成する#作成する（require,会社ID、$トップアラーム、$削除の情報）
            atomTask.add(CreateTopPageAlarmDataOfPersonService.create(require, companyId,
                    topAlarmList, deleteInfo));
        }
        if (isDisplayByAdmin) {
            //	$AtomTask．Add(上長のトップページアラームデータを作成する#作成する（require,会社ID、$トップアラーム、$削除の情報）
            atomTask.add(CreateAlarmDataTopPageService.create(require, deleteInfo, topAlarmList));
        }
        return atomTask;
    }

    /**
     * [prv-1]  アラームリストを集約する
     *
     * @param alarmPatternExtractResult アラームリストパターンの抽出結果
     * @return
     */
    private static Map<String, GeneralDateTime> aggregateAlarmList(PersistenceAlarmListExtractResult alarmPatternExtractResult) {
        // List<チェック条件結果>
        //create $map＜社員ID, List＜抽出結果詳細＞＞
        Map<String, List<AlarmEmployeeList>> extractResultMap = alarmPatternExtractResult.getAlarmListExtractResults().stream()
                .filter(x -> x.getEmployeeID() != null)
                .collect(Collectors.groupingBy(AlarmEmployeeList::getEmployeeID));

        //$社員リスト　＝　$map＜社員ID, List＜抽出結果詳細＞＞　：　map　$.key()
        Map<String, GeneralDateTime> alarmAggregateResultMap = new HashMap<>();
        for (val item : extractResultMap.keySet()) {
            List<AlarmEmployeeList> alarmEmps = extractResultMap.get(item);
            List<ExtractResultDetail> extractResultDetails = alarmEmps.stream()
                    .flatMap(x -> x.getAlarmExtractInfoResults().stream().flatMap(m -> m.getExtractionResultDetails()
                            .stream())).sorted(Comparator.comparing(ExtractResultDetail::getRunTime)).collect(Collectors.toList());

            Optional<GeneralDateTime> date = extractResultDetails.stream().map(ExtractResultDetail::getRunTime).findFirst();

            alarmAggregateResultMap.put(item, date.orElse(null));
        }

        return alarmAggregateResultMap;
    }

    public interface Require extends CreateTopPageAlarmDataOfPersonService.Require, CreateAlarmDataTopPageService.Require {
        /**
         * [R-1] 永続化のアラームリスト抽出結果を取得する
         *
         * @param companyId   会社ID
         * @param patternCode パターンコード
         * @param autoRunCode 自動実行コード
         * @return
         */
        Optional<PersistenceAlarmListExtractResult> getAlarmListExtractionResult(String companyId, String patternCode, String autoRunCode);

        /**
         * [R-2] 更新処理自動実行の設定を取得する
         *
         * @param companyId  会社ID
         * @param execItemCd 更新処理自動実行コード
         */
//        Optional<ExecutionTaskSetting> getUpdateProcessingAutoRunSetting(String companyId, String execItemCd);
        Optional<UpdateProcessAutoExecution> getUpdateProcessExecutionByCidAndExecCd(String companyId, String execItemCd);
    }
}
