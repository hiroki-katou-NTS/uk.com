package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.auth.dom.employmentrole.GetListOfWorkplacesService;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.mailserver.MailServerAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.AlarmMailSettingsAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerImport;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.CreateErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.OutputErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSetting;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRoleRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.Content;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.NormalAutoClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.PersonalManagerClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.Subject;
import nts.uk.ctx.at.function.dom.alarm.sendemail.MailSettingsParamDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.SendEmailService;
import nts.uk.ctx.at.function.dom.alarmworkplace.sendemail.WorkplaceSendEmailService;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.persistenceextractresult.PersisAlarmListExtractResultRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

/**
 * メール送信をする
 */
@Stateless
public class SendEmailAlarmListWorkPlaceCommandHandler extends CommandHandlerWithResult<SendEmailAlarmListWorkPlaceCommand, String> {

    @Inject
    private MailSettingNormalRepository mailSettingNormalRepository;

    @Inject
    private MailServerAdapter mailServerAdapter;

    @Inject
    private WkpManagerAdapter wkpManagerAdapter;

    @Inject
    private SendEmailService sendEmailService;

    @Inject
    private WorkplaceSendEmailService workplaceSendEmailService;

    @Inject
    private AlarmListExecutionMailSettingRepository executionMailSettingRepository;

    @Inject
    private AlarmMailSendingRoleRepository sendingRoleRepository;

    @Inject
    private AlarmMailSettingsAdapter alarmMailSettingsAdapter;

    @Inject
    private SyWorkplaceAdapter syWorkplaceAdapter;

    @Inject
    private GetListOfWorkplacesService.Require require;

    @Inject
    private RoleAdaptor roleAdaptor;

    @Inject
    private PersisAlarmListExtractResultRepository extractResultRepository;

    @Inject
    private EmployeeManageAdapter employeeManageAdapter;

    @Inject
    private CreateErrorInfo createErrorInfo;


    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    protected String handle(CommandHandlerContext<SendEmailAlarmListWorkPlaceCommand> context) {
        SendEmailAlarmListWorkPlaceCommand command = context.getCommand();
        GeneralDate executeDate = GeneralDate.today();
        String companyId = AppContexts.user().companyId();
        val exMailList = executionMailSettingRepository.getByCompanyId(companyId,
                PersonalManagerClassification.EMAIL_SETTING_FOR_ADMIN.value,
                IndividualWkpClassification.WORKPLACE.value
        );
        //エラーメッセージ(#Msg_719)を表示する
        if (command.getWorkplaceIds().size() == 0) {
            throw new BusinessException("Msg_719");
        }

        //ドメインモデル「アラームリスト通常用メール設定」を取得する
        val exMailListNOrmal = exMailList.
                stream()
                .filter(x -> x.getNormalAutoClassify().value == NormalAutoClassification.NORMAL.value)
                .findFirst();
        /*
        * 取得した「アラームリスト実行メール設定」　＝＝　Empty OR
         取得した「アラームリスト実行メール設定」．内容メール設定　＝＝　Empty
       */
        if (isNotHaveMailSetting(exMailListNOrmal)) {
            throw new BusinessException("Msg_1169");
        }

        //管理者を取得する

        val employeeIdMap = angAnAdministrator(command.getWorkplaceIds());


        //ドメインモデル「アラームメール送信ロール」を取得する
        val sendingRole = sendingRoleRepository.find(companyId, IndividualWkpClassification.WORKPLACE.value);

        //ドメインモデル「ロール」を取得
        val roleListByCompany = alarmMailSettingsAdapter.findByCompanyId(companyId);

        List<String> empIdList = new ArrayList<>();
        //[ロール設定=true]
        if (sendingRole.isPresent() && sendingRole.get().isRoleSetting()) {
            //取得したMap＜職場ID、List＜管理者ID＞＞をループする
            Map<String, String> mapFiler = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : employeeIdMap.entrySet()) {
                Map<String, String> roleMap = roleIdWorkDomService(entry.getValue());
                //取得したMap＜社員ID、ロールID＞をループする
                for (Map.Entry<String, String> role : roleMap.entrySet()) {
                    //管理者のロールはアラームメール送信ロールに設定するかチェック
                    if (isRoleExistInAlarmMail(sendingRole, role.getValue())) {
                        OptionalInt range = roleAdaptor.findEmpRangeByRoleID(role.getValue());
                        if (range.isPresent() && range.getAsInt() != EmployeeReferenceRange.ONLY_MYSELF.value) {
                            mapFiler.put(entry.getKey(), role.getKey());
                            empIdList.add(role.getKey());
                        }
                    }
                }
            }
            //取得したアラームメール送信ロール．マスタチェック結果を就業担当へ送信をチェックする
            if (sendingRole.isPresent() && sendingRole.get().isSendResult()) {
                //アラームリスト抽出結果にカテゴリ「マスタチェック（基本）のデータがあるかチェック
                boolean isExtractData = command.listValueExtractAlarmDto.stream().anyMatch(x -> x.getCategory() == AlarmCategory.MASTER_CHECK.value);

                if (isExtractData) {
                    //担当者を取得する。
                    empIdList = employeeManageAdapter.getListEmpID(companyId, GeneralDate.today());
                }
            }
        }

        //ドメインモデル「メールサーバ」を取得する
        boolean useAuthentication = mailServerAdapter.findBy(companyId);
        if (!useAuthentication) {
            throw new BusinessException("Msg_2205");
        }

        //作成したMap＜職場ID、List＜社員ID＞　！＝　Empty
        Map<String, List<String>> managerErrorList = new HashMap<>();
        List<String> personError = new ArrayList<>();
        if (!employeeIdMap.isEmpty()) {            //アルゴリズム「メール送信処理」を実行する。
            managerErrorList = workplaceSendEmailService.alarmWorkplacesendEmail(
                    employeeIdMap,
                    command.listValueExtractAlarmDto,
                    exMailListNOrmal.get(),
                    command.getCurrentAlarmCode(),
                    useAuthentication
            );
        } else {
            //取得したList<メール送信社員ID>　！＝　Empty
            if (!empIdList.isEmpty()) {
                personError = workplaceSendEmailService.alarmWorkplacesendEmail(empIdList,
                        command.listValueExtractAlarmDto,
                        exMailListNOrmal.get(),
                        command.getCurrentAlarmCode(),
                        useAuthentication);
            }
        }

        if (personError.isEmpty() && managerErrorList.isEmpty()) {
            return TextResource.localize("Msg_965");
        }

        //管理者未設定職場リスト：取得した管理者未設定職場リスト
        List<String> unsetList = new ArrayList<>();
        for (Map.Entry<String, List<String>> target : employeeIdMap.entrySet()) {
            val extractAlarmDto = command.listValueExtractAlarmDto.stream()
                    .filter(x -> x.getWorkplaceID() == target.getKey())
                    .findFirst();
            if (!extractAlarmDto.isPresent()) {
                unsetList.add(extractAlarmDto.get().getWorkplaceID());
            }
        }

        //アルゴリズム「メール送信のエラー情報を作成する」を実行する
        OutputErrorInfo outputErrorInfo = createErrorInfo.getErrorInfo(GeneralDate.today(), personError, managerErrorList, unsetList);
        String errorInfo = "";
        if (!outputErrorInfo.getError().equals("")) {
            errorInfo += outputErrorInfo.getError();
        }
        if (!outputErrorInfo.getErrorWkp().equals("")) {
            errorInfo += outputErrorInfo.getErrorWkp();
        }
        return errorInfo;
    }

    private boolean isRoleExistInAlarmMail(Optional<AlarmMailSendingRole> alarmMailSendingRole, String role) {
        if (!alarmMailSendingRole.isPresent()) {
            return false;
        }
        return alarmMailSendingRole.get().getRoleIds().stream().anyMatch(x -> x == role);
    }

    private Map<String, String> roleIdWorkDomService(List<String> empIdList) {
        Map<String, String> map = new HashMap<>();
        int index = 0;
        for (String empId : empIdList) {
            /*Optional<String> userID = require.getUserID(empId);
            if (userID.isPresent()) {
                roleSetList.add(new RoleSetExport());
            }*/
            //TODO get role id from  https://insight.3si.vn/issues/53190
            map.put(empId, "roleId_" + index);
            index++;
        }
        return map;
    }

    private boolean isNotHaveMailSetting(Optional<AlarmListExecutionMailSetting> mailSetting) {
        return !mailSetting.isPresent() || !(mailSetting.get().getContentMailSettings().isPresent());
    }

    private MailSettingsParamDto buildMailSend(AlarmListExecutionMailSetting mailSetting) {

        val mailSetings = mailSetting.getContentMailSettings();
        val mailSetingAdmins = mailSetting.getContentMailSettings();
        String subject = "", text = "", subjectAdmin = "", textAdmin = "";
        if (mailSetings.isPresent()) {
            subject = mailSetings.get().getSubject().orElseGet(() -> new Subject("")).v();
            text = mailSetings.get().getText().orElseGet(() -> new Content("")).v();
        }
        if (mailSetingAdmins != null) {
            subjectAdmin = mailSetingAdmins.get().getSubject().orElseGet(() -> new Subject("")).v();
            textAdmin = mailSetingAdmins.get().getText().orElseGet(() -> new Content("")).v();
        }
        // setting subject , body mail
        return new MailSettingsParamDto(subject, text, subjectAdmin, textAdmin);
    }

   /* private Map<String, List<String>> getManagerIdMap(List<String> worplaceIdList) {
        Map<String, List<String>> managerMap = new HashMap<>();
        for (String worlPlaceId : worplaceIdList) {
            List<WorkplaceManagerImport> managerList = wkpManagerAdapter.getWkpManagerListByWkpId(worlPlaceId);
            if (!managerList.isEmpty()) {
                managerMap.put(worlPlaceId, managerList.stream().map(x -> x.getWorkplaceManagerId()).collect(Collectors.toList()));
            }
        }
        return managerMap;
    }*/

    private Map<String, List<String>> angAnAdministrator(List<String> worplaceIdList) {
        Map<String, List<String>> managerMap = new HashMap<>();
        for (String worlPlaceId : worplaceIdList) {
            List<WkpManagerImport> managerList = wkpManagerAdapter.findByPeriodAndBaseDate(worlPlaceId, GeneralDate.today());
            if (!managerList.isEmpty()) {
                managerMap.put(worlPlaceId, managerList.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList()));
            }
        }
        return managerMap;
    }

    private Map<String, List<String>> getEmployeeIdMap(List<String> worplaceIdList) {
        Map<String, List<String>> map = new HashMap<>();
        for (String worlPlaceId : worplaceIdList) {
            //TODO get employee list by worlPlaceId
            map.put(worlPlaceId, Collections.emptyList());
        }
        return map;
    }

}