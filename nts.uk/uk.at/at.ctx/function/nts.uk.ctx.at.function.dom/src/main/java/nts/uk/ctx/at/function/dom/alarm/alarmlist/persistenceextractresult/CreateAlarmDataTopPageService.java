package nts.uk.ctx.at.function.dom.alarm.alarmlist.persistenceextractresult;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.adapter.alarm.AffAtWorkplaceExport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.DeleteInfoAlarmImport;
import nts.uk.ctx.at.function.dom.adapter.toppagealarmpub.TopPageAlarmImport;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;
import nts.uk.ctx.at.function.dom.alarm.sendemail.GetManagerOfEmpSendAlarmMailIndividual;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ManagerOfWorkplaceService;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ManagerTagetDto;

import javax.ejb.Stateless;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DomainService: 上長のトップページアラームデータを作成する
 *
 * @author viet.tx
 */
@Stateless
public class CreateAlarmDataTopPageService {
    public static AtomTask create(Require require, String companyId, Optional<DeleteInfoAlarmImport> deleteInfo,
                                  List<TopPageAlarmImport> alarmListInfo) {
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

        //$職場Map　： map groupingBy $$所属職場履歴情報リスト.職場ID　//※補足1を参照
        Map<String, List<TopPageAlarmImport>> workplaceMap = wkplHistoryInfos.stream()
                .collect(Collectors.groupingBy(
                        AffAtWorkplaceExport::getWorkplaceId,
                        Collectors.mapping(
                                i -> alarmListInfo.stream()
                                        .filter(a -> a.getDisplaySId().equals(i.getEmployeeId()))
                                        .findAny()
                                        .orElse(null),
                                Collectors.toList()
                        )
                ));

        //$チェックの職場IDList　＝　$所属職場履歴情報リスト　：　map　$.職場ID
        List<String> checkWkplIds = wkplHistoryInfos.stream().map(AffAtWorkplaceExport::getWorkplaceId).distinct().collect(Collectors.toList());

        //$エラーがある職場IDList　＝　$エラーがある社員IDList　：　map　$社員IDMap.get($)
        List<String> wkplIdListErrors = empIdErrors.stream().map(empIdMap::get).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        //$エラーがなくなった職場IDList　＝　$チェックの職場IDList　：　except　$エラーがある職場IDList
        List<String> wkplIdListNotErrors = checkWkplIds.stream()
                .filter(x -> !wkplIdListErrors.contains(x))
                .collect(Collectors.toList());

        // $削除の情報　＝　Empty
        Optional<DeleteInfoAlarmImport> delInfo = Optional.empty();

        // if　$エラーがなくなった職場IDList.isPresent()　//削除
        if (!CollectionUtil.isEmpty(wkplIdListNotErrors)) {
            // $全てエラーが解除済み社員　＝　アラームチェックをしてメールを上司に送るとトップページに表示する可能対象者を取得する(社ID,$エラーがなくなった職場IDList、個人職場区分.職場別）
            // .map　：$.value
            Map<String, List<String>> adminReceiveAlarmMailMap = ManagerOfWorkplaceService.get(require, companyId, wkplIdListNotErrors, IndividualWkpClassification.INDIVIDUAL);
            List<String> allEmpErrorsRemoved = adminReceiveAlarmMailMap.isEmpty() ? Collections.emptyList():
                    adminReceiveAlarmMailMap.values().stream()
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());

            //$削除の情報　＝　削除の情報Param#作成する(アラームリスト、 $全てエラーが解除済み社員、 上長、 $パターンコード )
            delInfo = Optional.of(DeleteInfoAlarmImport.builder()
                    .alarmClassification(deleteInfo.map(DeleteInfoAlarmImport::getAlarmClassification).orElse(0))
                    .sids(allEmpErrorsRemoved)
                    .displayAtr(1)
                    .patternCode(patternCode)
                    .subEmpNoErrs(empIdWithNoErrors)
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
        for (String wkpl : wkplIdListErrors) {//職場、基準日からアラーム通知先の社員を取得する
            // $エラー社員List　＝　$所属職場履歴情報リスト：　
            // filter：$　＝　$1.職場ID
            // map：		管理者に送信する社員#管理者に送信する社員($、$.社員ID)
            List<ManagerTagetDto> managerTargetList = wkplHistoryInfos.stream().filter(wkp -> wkp.getWorkplaceId().equals(wkpl))
                    .map(wkp -> new ManagerTagetDto(wkp.getEmployeeId(), wkp.getWorkplaceId())).collect(Collectors.toList());

            // $エラー社員の管理者ID　＝　管理者とアラームリスの対象者を取得する(会社ID、$エラー社員List、List.Empty、true)
            Map<String, List<String>> adminOfEmployeeErrorMap = GetManagerOfEmpSendAlarmMailIndividual.get(require, companyId, managerTargetList, Collections.emptyList(), Optional.of(true));

            // $上長の社員IDList　 =　$エラー社員の管理者ID　：　map：　$.管理者ID
            val superiorEmpIDList = adminOfEmployeeErrorMap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());

            //$発生日時　＝　$職場Map.get($)　：　sort $.発生日時 DESC first $.発生日時
            val topAlarmParamList = workplaceMap.getOrDefault(wkpl, new ArrayList<>());
            val optWkplTopAlarm = topAlarmParamList.stream().filter(Objects::nonNull).map(TopPageAlarmImport::getOccurrenceDateTime).max(GeneralDateTime::compareTo);
            GeneralDateTime occurrenceDateTime = optWkplTopAlarm.orElseGet(GeneralDateTime::now);

            //$部下社員IDList　＝　[prv-1]部下のエラーがある社員IDを判断する（Loopしてる職場ID、$職場Map)
            List<String> subEmpIdList = getEmployeeIdsWithChildWkpError(wkpl, workplaceMap);

            //$上長１ = List.Empty
            List<TopPageAlarmImport> superiors = new ArrayList<>();
            //$上長の社員IDList　：for　
            for (String sid : superiorEmpIDList) {
                val superiorEmpIDListSub = adminOfEmployeeErrorMap.get(sid);
                if (superiorEmpIDListSub.size() > 0){
                    //$上長１.Add（トップアラームParam#作成する(アラームリスト、$発生日時、$、上長、$部下社員IDListSub, $パターンコード、Empty、Empty、$パターン名称））
                    superiors.add(new TopPageAlarmImport(
                            deleteInfo.map(DeleteInfoAlarmImport::getAlarmClassification).orElse(0),
                            occurrenceDateTime,
                            sid,
                            1,
                            patternCode,
                            patternName,
                            Optional.empty(),
                            Optional.empty(),
                            superiorEmpIDListSub,
                            empIdWithNoErrors
                    ));
                }
            }
            topAlarmList.addAll(superiors);
        }

        Optional<DeleteInfoAlarmImport> finalDelInfo = delInfo;
        return AtomTask.of(() -> require.create(companyId, topAlarmList, finalDelInfo));
    }

    /**
     * [prv-1]部下のエラーがある社員IDを判断する
     * @param workplaceId
     * @param workplaceMap
     * @return 部下のエラーがある社員ID
     */
    private static List<String> getEmployeeIdsWithChildWkpError(String workplaceId, Map<String, List<TopPageAlarmImport>> workplaceMap) {
        List<TopPageAlarmImport> topAlarmParamList = workplaceMap.getOrDefault(workplaceId, new ArrayList<>());
        return topAlarmParamList.stream().filter(Objects::nonNull).map(TopPageAlarmImport::getDisplaySId).collect(Collectors.toList());
    }

    public interface Require extends GetManagerOfEmpSendAlarmMailIndividual.Require, ManagerOfWorkplaceService.Require {
        /**
         * [R-1] 社員ID（List）と基準日から所属職場IDを取得 : using EmployeeWorkplaceAdapter
         *
         * @param sIds
         * @param baseDate
         * @return
         */
        List<AffAtWorkplaceExport> getWorkplaceId(List<String> sIds, GeneralDate baseDate);

        /**
         * [R-3] トップページアラームデータを作成する : using TopPageAlarmAdapter
         *
         * @param companyId  会社ID
         * @param alarmInfos List トップアラームパラメータ
         * @param delInfoOpt 削除の情報
         */
        void create(String companyId, List<TopPageAlarmImport> alarmInfos, Optional<DeleteInfoAlarmImport> delInfoOpt);

        /**
         * 	[R-4]職場IDから、アラームメールの受信を許可されている管理者を取得する
         */
        Map<String, List<String>> getAdminReceiveAlarmMailByWorkplaceIds(List<String> workplaceIds);
    }
}
