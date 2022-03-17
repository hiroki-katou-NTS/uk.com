package nts.uk.ctx.at.function.dom.alarm.sendemail;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.function.dom.adapter.alarm.MailExportRolesDto;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerImport;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;

import java.util.*;
import java.util.stream.Collectors;

/**
 * アラームチェックをしてメールを上司に送るとトップページに表示する可能対象者を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム.メール設定.App.アラームチェックをしてメールを上司に送るとトップページに表示する可能対象者を取得する.アラームチェックをしてメールを上司に送るとトップページに表示する可能対象者を取得する
 */
public class ManagerOfWorkplaceService {
    /**
     * @param require
     * @param cid            会社ID
     * @param workplaceIds   List＜職場ID＞
     * @param classification 個人職場区分
     * @return Map＜職場ID、List＜管理者ID＞
     */
    public static Map<String, List<String>> get(Require require,
                                                String cid,
                                                List<String> workplaceIds,
                                                IndividualWkpClassification classification) {
        GeneralDate systemDate = GeneralDate.today();

        // 「アラームメール送信ロール」を取得する (会社ID　＝　Input．会社ID, 個人職場区分　＝　個人別)
        Optional<AlarmMailSendingRole> alarmMailSendRole = require.findAlarmMailSendRole(cid, classification.value);

        // ロールを取得する
        List<MailExportRolesDto> roleList = require.findRoleByCID(cid);

        // Map＜職場ID、List＜管理者ID＞を作成
        Map<String, List<String>> managerOfWorkplaceMap = new HashMap<>();

        // ①Input．List＜職場ID＞をループする
        for (String workplaceId : workplaceIds) {
            // 職場IDと基準日から上位職場を取得する: No.571
            List<String> workplaceIdUppers = require.getWorkplaceIdAndUpper(cid, systemDate, workplaceId);

            //List＜管理者＞を作成
            List<String> managerIds = new ArrayList<>();
            // ②取得したList＜職場ID＞をループする
            for (String workplaceIdUpper : workplaceIdUppers) {

                // 職場から社員IDを取得する: #122420: Update 2022.03.16
                List<AffWorkplaceHistoryItemImport> workplaceHistoryItems = require.getWorkHisItemfromWkpIdAndBaseDate(workplaceIdUpper, systemDate);
                List<String> employeeOfWkpList = workplaceHistoryItems.stream().map(AffWorkplaceHistoryItemImport::getEmployeeId).collect(Collectors.toList());

                // 社員IDListから就業ロールIDを取得 : Map <EmployeeID, RoleID>
                Map<String, String> administratorRoleMap = GetRoleWorkByEmployeeService.get(require, employeeOfWkpList, systemDate);

                // 就業担当者のロール、参照範囲自分のみを持つ管理者を消す
                Iterator<Map.Entry<String, String>> itr = administratorRoleMap.entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, String> item = itr.next();

                    val roleOpt = roleList.stream().filter(x -> x.getRoleId().equals(item.getValue())).findFirst();
                    boolean isRemoved = false;
                    if (roleOpt.isPresent()) {  //RoleAtr.INCHARGE = 0; EmployeeReferenceRange.ONLY_MYSELF = 3; EmployeeReferenceRange.DEPARTMENT_ONLY = 2
                        if (roleOpt.get().getAssignAtr() == 0 || roleOpt.get().getEmployeeReferenceRange() == 3 ||
                                (!workplaceId.equals(workplaceIdUpper) && roleOpt.get().getEmployeeReferenceRange() == 2)) {
                            itr.remove();
                            isRemoved = true;
                        }
                    }

                    // 取得した「アラームメール送信ロール」をチェック
                    if (alarmMailSendRole.isPresent() && alarmMailSendRole.get().isRoleSetting()) {
                        // 設定ないロールを消す: ロールID　NOT IN　アラームメール送信ロール．ロールID
                        if (roleOpt.isPresent() && !alarmMailSendRole.get().getRoleIds().contains(roleOpt.get().getRoleId()) && !isRemoved) {
                            itr.remove();
                        }
                    }
                }

                // List＜管理者＞に社員IDを追加
                if (!administratorRoleMap.isEmpty()) {
                    val adminIds = administratorRoleMap.entrySet().stream().map(Map.Entry::getKey).distinct().collect(Collectors.toList());
                    managerIds.addAll(adminIds);
                }

                // #122420: Update 2022.03.16
                // ①のループ中の職場ID　==②のループ中
                if (workplaceId.equals(workplaceIdUpper)) {
                    // [No.XXX]職場から職場管理者社員を取得する
                    List<WkpManagerImport> wkpManagerList = require.findByPeriodAndBaseDate(workplaceIdUpper, systemDate);
                    List<String> administratorOfWorkplaces = wkpManagerList.stream().map(WkpManagerImport::getEmployeeId).collect(Collectors.toList());
                    if (!administratorOfWorkplaces.isEmpty()) {
                        managerIds.addAll(administratorOfWorkplaces);
                    }
                }
            }

            if (!managerIds.isEmpty()) {
                managerOfWorkplaceMap.put(workplaceId, managerIds.stream().distinct().collect(Collectors.toList()));
            }
        }

        return managerOfWorkplaceMap;
    }

    public interface Require extends GetRoleWorkByEmployeeService.Require {

        Optional<AlarmMailSendingRole> findAlarmMailSendRole(String cid, int individualWkpClassify);

        List<MailExportRolesDto> findRoleByCID(String companyId);

        List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId);

        List<WkpManagerImport> findByPeriodAndBaseDate(String wkpId, GeneralDate baseDate);

        List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdAndBaseDate(String workPlaceId, GeneralDate baseDate);
    }

}
