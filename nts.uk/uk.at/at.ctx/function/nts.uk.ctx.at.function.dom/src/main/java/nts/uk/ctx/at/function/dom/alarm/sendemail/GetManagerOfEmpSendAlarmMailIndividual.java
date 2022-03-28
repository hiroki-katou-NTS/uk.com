package nts.uk.ctx.at.function.dom.alarm.sendemail;

import lombok.val;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ロールにより管理対象者を調整
 * UKDesign.UniversalK.就業.KAL_アラームリスト.KAL001_アラームリスト.C:メール送信者選択ダイアログ.アルゴリズム.メールを送信する.ロールにより管理対象者を調整.ロールにより管理対象者を調整
 */
public class GetManagerOfEmpSendAlarmMailIndividual {

    /**
     * @param cid               会社ID
     * @param managerTargetList Input．List＜管理者に送信する社員＞
     * @param employeeTargetIds Input．List＜本人送信対象＞
     * @param sendPerson        Optional＜本人送るか＞
     * @return Map＜管理者ID、List＜対象者ID＞＞
     */
    public static Map<String, List<String>> get(Require require,
                                                String cid,
                                                List<ManagerTagetDto> managerTargetList,
                                                List<String> employeeTargetIds,
                                                Optional<Boolean> sendPerson) {
        // Map＜管理者ID、List＜対象者ID＞＞を作成 : Map<ManagerId, List<EmployeeIds>>
        Map<String, List<String>> managerOfEmployeeMap = new HashMap<>();

        // Input．List＜管理者に送信する社員＞：List＜職場ID、社員ID＞をチェック
        if (managerTargetList.isEmpty()) return managerOfEmployeeMap;

        // アラームチェックをしてメールを上司に送るとトップページに表示する可能対象者を取得する: Output Map＜職場ID、List＜管理者ID＞
        val workplaceIds = managerTargetList.stream().map(ManagerTagetDto::getWorkplaceID).distinct().collect(Collectors.toList());
        val managerOfWorkplaceMap = ManagerOfWorkplaceService.get(require, cid, workplaceIds, IndividualWkpClassification.INDIVIDUAL);

        // Map＜職場ID、List＜管理者ID＞
        for (Map.Entry<String, List<String>> entry : managerOfWorkplaceMap.entrySet()) {

            // 対象者一覧を絞り込む
            val targetPersonList = managerTargetList.stream().filter(x -> x.getWorkplaceID().equals(entry.getKey()))
                    .map(ManagerTagetDto::getEmployeeID).collect(Collectors.toList());

            for (String managerId : entry.getValue()) {
                // 絞り込んだ対象者一覧を調整
                val condition1 = (sendPerson.isPresent() && sendPerson.get());
                // OR　Input．List＜本人送信対象＞にInput．管理者IDを存在する AND 絞り込んだ対象者一覧にループ中の管理者IDを存在する
                val condition2 = employeeTargetIds.contains(managerId) && targetPersonList.contains(managerId);

                if (condition1 || condition2) {
                    // ↑の条件に従って絞り込んだ対象者一覧に管理者IDを消す
                    targetPersonList.removeIf(emp -> emp.equals(managerId));
                }

                // Map＜管理者ID、List＜対象者ID＞＞に追加
                if (!targetPersonList.isEmpty()) {
                    if (!managerOfEmployeeMap.containsKey(managerId)) {
                        managerOfEmployeeMap.put(managerId, targetPersonList);
                    } else {
                        val combinedList = Stream.of(managerOfEmployeeMap.get(managerId), targetPersonList).flatMap(Collection::stream).distinct().collect(Collectors.toList());
                        managerOfEmployeeMap.put(managerId, combinedList);
                    }
                }
            }
        }

        return managerOfEmployeeMap;
    }

    public interface Require extends ManagerOfWorkplaceService.Require {

    }
}
