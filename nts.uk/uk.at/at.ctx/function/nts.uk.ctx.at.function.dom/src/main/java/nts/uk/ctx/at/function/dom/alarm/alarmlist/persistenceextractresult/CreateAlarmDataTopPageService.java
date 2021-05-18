package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.alarm.AffAtWorkplaceExport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DomainService: 上長のトップページアラームデータを作成する
 *
 * @author viet.tx
 */
@Stateless
public class CreateAlarmDataTopPageService {
    public static AtomTask create(Require require, Optional<DeleteInfoAlarmImport> deleteInfo,
                                  List<TopPageAlarmImport> alarmListInfo) {
        String companyId = AppContexts.user().companyId();

        //$パターンコード　＝　アラームリストの情報.get(0).パターンコード　//アラームリストの情報がない場合、削除の情報.get().パターンコード
        Optional<String> patternCode = !alarmListInfo.isEmpty() ? alarmListInfo.get(0).getPatternCode()
                : deleteInfo.isPresent() ? deleteInfo.get().getPatternCode() : Optional.empty();

        //$エラーがある社員IDList　＝　アラームリストの情報　：　map　$.社員ID
        List<String> empIdErrors = alarmListInfo.stream().map(TopPageAlarmImport::getDisplaySId).collect(Collectors.toList());
        //$エラーがない社員IDList(Optional)　＝　削除の情報　：　map　$.社員IDList
        List<String> empIdWithNoErrors = deleteInfo.isPresent() ? deleteInfo.get().getSids() : Collections.emptyList();

        //$社員IDリスト　＝　$エラーがある社員IDList　+　$エラーがない社員IDList(Optional)
        List<String> employeeIds = Stream.of(empIdErrors, empIdWithNoErrors)
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());

        //$所属職場履歴情報リスト　=　require.社員ID（List）と基準日から所属職場IDを取得($社員IDリスト, 年月日#今())
        List<AffAtWorkplaceExport> wkplHistoryInfos = require.getWorkplaceId(employeeIds, GeneralDate.today());
        //$社員IDMap　＝　$所属職場履歴情報リスト　：　map  key：$.社員ID  value：$.職場ID
        Map<String, String> empIdMap = wkplHistoryInfos.stream()
                .collect(Collectors.toMap(AffAtWorkplaceExport::getEmployeeId, AffAtWorkplaceExport::getWorkplaceId));

        List<WorkplaceHistoryTopAlarmParamMerged> mergedList = new ArrayList<>();
        wkplHistoryInfos.forEach(w -> alarmListInfo.forEach(alarm -> {
            if (w.getEmployeeId().equals(alarm.getDisplaySId())) {
                mergedList.add(new WorkplaceHistoryTopAlarmParamMerged(
                        w.getEmployeeId(),
                        w.getWorkplaceId(),
                        w.getHistoryID(),
                        w.getNormalWorkplaceID(),
                        alarm.getAlarmClassification(),
                        alarm.getOccurrenceDateTime(),
                        alarm.getDisplaySId(),
                        alarm.getDisplayAtr(),
                        alarm.getPatternCode(),
                        alarm.getPatternName(),
                        alarm.getLinkUrl(),
                        alarm.getDisplayMessage()
                ));
            }
        }));
        //$職場Map　： map groupingBy $$所属職場履歴情報リスト.職場ID　//※補足1を参照
        Map<String, List<WorkplaceHistoryTopAlarmParamMerged>> workplaceMap = mergedList.stream()
                .collect(Collectors.groupingBy(WorkplaceHistoryTopAlarmParamMerged::getWorkplaceId));

        //$チェックの職場IDList　＝　$所属職場履歴情報リスト　：　map　$.職場ID
        List<String> checkWkplId = wkplHistoryInfos.stream().map(AffAtWorkplaceExport::getWorkplaceId).distinct().collect(Collectors.toList());

        //$エラーがある職場IDList　＝　$エラーがある社員IDList　：　map　$社員IDMap.get($)
        List<String> wkplIdListErrors = empIdErrors.stream().map(empIdMap::get).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        //$エラーがなくなった職場IDList　＝　$チェックの職場IDList　：　except　$エラーがある職場IDList
        List<String> wkplIdListNotErrors = checkWkplId.stream()
                .filter(x -> !wkplIdListErrors.contains(x))
                .collect(Collectors.toList());

        Optional<DeleteInfoAlarmImport> delInfo = Optional.empty();
        if (!CollectionUtil.isEmpty(wkplIdListNotErrors)) {
            List<String> allEmpErrorsRemoved = new ArrayList<>();
            wkplIdListNotErrors.forEach(x -> {
                List<String> preEmpIds = require.getListEmployeeId(x, GeneralDate.today());
                allEmpErrorsRemoved.addAll(preEmpIds);
            });

            //$削除の情報　＝　削除の情報Param#作成する(アラームリスト、 $全てエラーが解除済み社員、 上長、 $パターンコード )
            delInfo = Optional.of(DeleteInfoAlarmImport.builder()
                    .alarmClassification(deleteInfo.isPresent() ? deleteInfo.get().getAlarmClassification() : 0)
                    .sids(allEmpErrorsRemoved)
                    .displayAtr(1)
                    .patternCode(patternCode)
                    .build());
        }

        //$上長のアラームリスト　＝　List.Empty
        List<TopPageAlarmImport> topAlarmList = new ArrayList<>();
        if (CollectionUtil.isEmpty(wkplIdListErrors)) {
            Optional<DeleteInfoAlarmImport> finalDelInfo = delInfo;
            return AtomTask.of(() -> require.create(companyId, topAlarmList, finalDelInfo));
        }

        //$パターン名称　＝　アラームリストの情報.get(0).パターン名
        Optional<String> patternName = alarmListInfo.get(0).getPatternName();
        //$エラーがある職場IDList：for
        wkplIdListErrors.forEach(wkpl -> {
            //職場、基準日からアラーム通知先の社員を取得する
            List<String> empIds = require.getListEmployeeId(wkpl, GeneralDate.today());
            //$発生日時　＝　$職場Map.get($)　：　sort $.発生日時 DESC first $.発生日時
            Optional<WorkplaceHistoryTopAlarmParamMerged> optWkplTopAlarm = workplaceMap.get(wkpl).stream()
                    .sorted((e1, e2) -> e2.getOccurrenceDateTime().compareTo(e1.getOccurrenceDateTime()))
                    .findFirst();
            GeneralDateTime occurrenceDateTime = optWkplTopAlarm.isPresent() ? optWkplTopAlarm.get().getOccurrenceDateTime() : null;

            //$上長１　＝　$上長の社員IDList　：　トップアラームParam#作成する(
            empIds.forEach(e -> {
                TopPageAlarmImport topAlarmObj = TopPageAlarmImport.builder()
                        .alarmClassification(deleteInfo.isPresent() ? deleteInfo.get().getAlarmClassification() : 0)
                        .occurrenceDateTime(occurrenceDateTime)
                        .displaySId(e)
                        .displayAtr(1)
                        .patternCode(patternCode)
                        .patternName(patternName)
                        .linkUrl(Optional.empty())
                        .displayMessage(Optional.empty())
                        .build();
                topAlarmList.add(topAlarmObj);
            });
        });
        //$上長のアラームリスト　：distinct　　//※重複の社員IDは追加しない
        List<TopPageAlarmImport> topAlarmListDistinct = topAlarmList.stream()
                .filter(distinctByKey(TopPageAlarmImport::getDisplaySId)).collect(Collectors.toList());

        Optional<DeleteInfoAlarmImport> finalDelInfo = delInfo;
        return AtomTask.of(() -> require.create(companyId, topAlarmListDistinct, finalDelInfo));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public interface Require {
        /**
         * [R-1] 社員ID（List）と基準日から所属職場IDを取得 : using EmployeeWorkplaceAdapter
         *
         * @param sIds
         * @param baseDate
         * @return
         */
        List<AffAtWorkplaceExport> getWorkplaceId(List<String> sIds, GeneralDate baseDate);

        /**
         * [R-2] 職場、基準日からアラーム通知先の社員を取得する : using EmployeeAlarmListAdapter
         *
         * @param workplaceId
         * @param referenceDate
         * @return
         */
        List<String> getListEmployeeId(String workplaceId, GeneralDate referenceDate);

        /**
         * [R-3] トップページアラームデータを作成する : using TopPageAlarmAdapter
         *
         * @param companyId  会社ID
         * @param alarmInfos List トップアラームパラメータ
         * @param delInfoOpt 削除の情報
         */
        void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt);
    }
}
