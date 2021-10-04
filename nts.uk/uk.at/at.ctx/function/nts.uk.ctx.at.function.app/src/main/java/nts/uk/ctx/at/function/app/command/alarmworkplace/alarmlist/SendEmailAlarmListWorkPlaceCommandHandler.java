package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeAlarmListAdapter;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.function.dom.adapter.mailserver.MailServerAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleExportRpAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportAdapter;
import nts.uk.ctx.at.function.dom.adapter.user.UserEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerImport;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.CreateErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.OutputErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSetting;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRoleRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.NormalAutoClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.PersonalManagerClassification;
import nts.uk.ctx.at.function.dom.alarm.sendemail.SendEmailService;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.sendemail.WorkplaceSendEmailService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
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
    private RoleAdaptor roleAdaptor;

    @Inject
    private EmployeeManageAdapter employeeManageAdapter;

    @Inject
    private CreateErrorInfo createErrorInfo;

    @Inject
    private RoleSetExportAdapter roleAdapter;

    @Inject
    private UserEmployeeAdapter userEmployeeAdapter;

    @Inject
    private EmployeeAlarmListAdapter employeeAlarmListAdapter;

    @Inject
    private RoleExportRpAdapter roleExportRpAdapter;


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

        //取得したMap＜職場ID、List＜管理者ID＞＞をループする
        Map<String, List<String>> mapFiler = new HashMap<>();
        boolean mailSendFlag = true;
        List<String> empIdList = new ArrayList<>();
        //[ロール設定=true]
        if (sendingRole.isPresent() && sendingRole.get().isRoleSetting()) {
            for (Map.Entry<String, List<String>> entry : employeeIdMap.entrySet()) {
                if (!mailSendFlag) {
                    break;
                }
                Map<String, String> roleMap = roleIdWorkDomService(entry.getValue());
                List<String> filterManager = new ArrayList<>();
                //取得したMap＜社員ID、ロールID＞をループする
                if (roleMap.size() <= 0) {
                    mailSendFlag = false;
                }
                for (Map.Entry<String, String> role : roleMap.entrySet()) {
                    OptionalInt range = roleAdaptor.findEmpRangeByRoleID(role.getValue());
                    if (!range.isPresent() || range.getAsInt() == EmployeeReferenceRange.ONLY_MYSELF.value) {
                        mailSendFlag = false;
                        break;
                    }

                }
                mapFiler.put(entry.getKey(), filterManager);
            }
            //取得したアラームメール送信ロール．マスタチェック結果を就業担当へ送信をチェックする
            if (sendingRole.get().isSendResult()) {
                //アラームリスト抽出結果にカテゴリ「マスタチェック（基本）のデータがあるかチェック
                boolean isExtractData = command.listValueExtractAlarmDto
                        .stream()
                        .anyMatch(x -> x.getCategory() == WorkplaceCategory.MASTER_CHECK_BASIC.value);

                if (isExtractData) {
                    for (String wolPlaceId : command.getWorkplaceIds()) {
                        val empList = employeeAlarmListAdapter.getListEmployeeId(wolPlaceId, GeneralDate.today());
                        if (!empList.isEmpty())
                            empIdList.addAll(empList);
                    }
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
        val exportWkrPl = command.listValueExtractAlarmDto.stream().filter(x -> {
            return isCategoryMatch(command.getWorkplaceIds(), x.getWorkplaceID());
        }).collect(Collectors.toList());
        if (employeeIdMap.isEmpty() && empIdList.isEmpty()) {
            throw new BusinessException("Msg_2295");
        }
        if (roleExportRpAdapter.getCurrentLoginerRole().isEmployeeCharge()) {
            if (mailSendFlag && !employeeIdMap.isEmpty()) {            //アルゴリズム「メール送信処理」を実行する。
                managerErrorList = workplaceSendEmailService.alarmWorkplacesendEmail(
                        employeeIdMap,
                        exportWkrPl,
                        exMailListNOrmal.get(),
                        command.getCurrentAlarmCode(),
                        useAuthentication
                );
            }
            //取得したList<メール送信社員ID>　！＝　Empty
            if (mailSendFlag && !empIdList.isEmpty()) {
                personError = workplaceSendEmailService.alarmWorkplacesendEmail(empIdList,
                        exportWkrPl,
                        exMailListNOrmal.get(),
                        command.getCurrentAlarmCode(),
                        useAuthentication
                );
            }
        }

        if (!personError.isEmpty() || !managerErrorList.isEmpty()) {
            return TextResource.localize("Msg_965");
        }

        //管理者未設定職場リスト：取得した管理者未設定職場リスト
        List<String> unsetList = new ArrayList<>();
        for (Map.Entry<String, List<String>> target : employeeIdMap.entrySet()) {
            val extractAlarmDto = command.listValueExtractAlarmDto.stream()
                    .filter(x -> x.getWorkplaceID() == target.getKey())
                    .findFirst();
            if (extractAlarmDto.isPresent()) {
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

    private boolean isCategoryMatch(List<String> workplaceIds, String wkpId) {
        return workplaceIds.stream().anyMatch(y -> y.equals(wkpId));
    }

    private boolean isRoleExistInAlarmMail(Optional<AlarmMailSendingRole> alarmMailSendingRole, String role) {
        if (!alarmMailSendingRole.isPresent()) {
            return false;
        }
        return alarmMailSendingRole.get().getRoleIds().stream().anyMatch(x -> x == role);
    }

    //社員IDListから就業ロールIDを取得
    private Map<String, String> roleIdWorkDomService(List<String> empIdList) {
        Map<String, String> map = new HashMap<>();
        for (String empId : empIdList) {
            //社員IDからユーザIDを取得する
            val userId = userEmployeeAdapter.getUserIDByEmpID(empId);
            if (userId.isPresent()) {
                //ユーザIDからロールセットを取得する
                val role = roleAdapter.getRoleSetFromUserId(userId.get(), GeneralDate.today());
                if (role.isPresent() && StringUtils.isNotEmpty(role.get().getEmploymentRoleId())) {
                    map.put(empId, role.get().getEmploymentRoleId());
                }
            }
        }
        return map;
    }

    private boolean isNotHaveMailSetting(Optional<AlarmListExecutionMailSetting> mailSetting) {
        return !mailSetting.isPresent() || !(mailSetting.get().getContentMailSettings().isPresent());
    }

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
}