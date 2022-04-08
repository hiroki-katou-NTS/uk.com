package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeeAlarmListAdapter;
import nts.uk.ctx.at.function.dom.adapter.alarm.MailExportRolesDto;
import nts.uk.ctx.at.function.dom.adapter.mailserver.MailServerAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.AlarmMailSettingsAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.RoleSetExportDto;
import nts.uk.ctx.at.function.dom.adapter.user.UserEmployeeAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerAdapter;
import nts.uk.ctx.at.function.dom.adapter.wkpmanager.WkpManagerImport;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.CreateErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.createerrorinfo.OutputErrorInfo;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.*;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ManagerOfWorkplaceService;
import nts.uk.ctx.at.function.dom.alarm.sendemail.ValueExtractAlarmManualDto;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.sendemail.WorkplaceSendEmailService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.AffWorkplaceAdapter;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * メール送信をする
 */
@Stateless
public class SendEmailAlarmListWorkPlaceCommandHandler extends CommandHandlerWithResult<SendEmailAlarmListWorkPlaceCommand, String> {
    @Inject
    private MailServerAdapter mailServerAdapter;

    @Inject
    private WorkplaceSendEmailService workplaceSendEmailService;

    @Inject
    private AlarmListExecutionMailSettingRepository executionMailSettingRepository;

    @Inject
    private CreateErrorInfo createErrorInfo;

    @Inject
    private RoleSetExportAdapter roleAdapter;

    @Inject
    private UserEmployeeAdapter userEmployeeAdapter;

    @Inject
    private EmployeeAlarmListAdapter employeeAlarmListAdapter;

    @Inject
    private WkpManagerAdapter workplaceAdapter;

    @Inject
    private AlarmMailSettingsAdapter mailAdapter;

    @Inject
    private AlarmMailSendingRoleRepository alarmMailSendingRoleRepo;

    @Inject
    private AffWorkplaceAdapter affWorkplaceAdapter;

    @Inject
    private AuthWorkPlaceAdapter authWorkPlaceAdapter;

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
        val exMailListNormal = exMailList.
                stream()
                .filter(x -> x.getNormalAutoClassify().value == NormalAutoClassification.NORMAL.value)
                .findFirst();
        /*
        * 取得した「アラームリスト実行メール設定」　＝＝　Empty OR
         取得した「アラームリスト実行メール設定」．内容メール設定　＝＝　Empty
       */
        if (isNotHaveMailSetting(exMailListNormal)) {
            throw new BusinessException("Msg_1169");
        }

        Map<String, List<String>> managerOfEmployeeMap = ManagerOfWorkplaceService.get(
                new ManagerOfWorkplaceService.Require() {

                    @Override
                    public Optional<String> getUserIDByEmpID(String employeeID) {
                        return userEmployeeAdapter.getUserIDByEmpID(employeeID);
                    }

                    @Override
                    public Optional<RoleSetExportDto> getRoleSetFromUserId(String userId, GeneralDate baseDate) {
                        return roleAdapter.getRoleSetFromUserId(userId, baseDate);
                    }

                    @Override
                    public Optional<AlarmMailSendingRole> findAlarmMailSendRole(String cid, int individualWkpClassify) {
                        return alarmMailSendingRoleRepo.find(cid, individualWkpClassify);
                    }

                    @Override
                    public List<MailExportRolesDto> findRoleByCID(String companyId) {
                        return mailAdapter.findByCompanyId(companyId);
                    }

                    @Override
                    public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId) {
                        return affWorkplaceAdapter.getWorkplaceIdAndUpper(companyId, baseDate, workplaceId);
                    }

                    @Override
                    public List<WkpManagerImport> findByPeriodAndBaseDate(String wkpId, GeneralDate baseDate) {
                        return workplaceAdapter.findByPeriodAndBaseDate(wkpId, baseDate);
                    }

                    @Override
                    public List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdAndBaseDate(String workPlaceId, GeneralDate baseDate) {
                        return authWorkPlaceAdapter.getWorkHisItemfromWkpIdAndBaseDate(workPlaceId, baseDate);
                    }
                },
                companyId, command.getWorkplaceIds(),
                IndividualWkpClassification.WORKPLACE);

        boolean isMasterCheck = command.listValueExtractAlarmDto
                .stream().anyMatch(x -> x.getCategory() == WorkplaceCategory.MASTER_CHECK_BASIC.value);

        //アラームリスト抽出結果にカテゴリ「マスタチェック（基本）のデータがあるかチェック
        List<String> empIdList = new ArrayList<>();
        if (isMasterCheck) {
            // No.526
            empIdList = employeeAlarmListAdapter.getListEmployeeId(companyId, executeDate);
        }

        //ドメインモデル「メールサーバ」を取得する
        boolean useAuthentication = mailServerAdapter.checkMailServerSet(companyId).isMailServerSet();
        if (!useAuthentication) {
            throw new BusinessException("Msg_2205");
        }

        //作成したMap＜職場ID、List＜社員ID＞　！＝　Empty
        if (managerOfEmployeeMap.isEmpty() && empIdList.isEmpty()) {
            throw new BusinessException("Msg_2295");
        }

        //アルゴリズム「メール送信処理」を実行する。
        List<ValueExtractAlarmManualDto> extractAlarmData = command.listValueExtractAlarmDto.stream()
                .filter(x -> command.getWorkplaceIds().stream().anyMatch(y -> y.equals(x.getWorkplaceID())))
                .collect(Collectors.toList());

        Map<String, List<String>> managerErrorList = new HashMap<>();
        List<String> personError = new ArrayList<>();
        if (!managerOfEmployeeMap.isEmpty()) {
            managerErrorList = workplaceSendEmailService.alarmWorkplacesendEmail(
                    managerOfEmployeeMap,
                    extractAlarmData,
                    exMailListNormal,
                    command.getCurrentAlarmCode(),
                    useAuthentication
            );
        }
        //取得したList<メール送信社員ID>　！＝　Empty
        if (!empIdList.isEmpty()) {
            personError = workplaceSendEmailService.alarmWorkplacesendEmail(empIdList,
                    extractAlarmData,
                    exMailListNormal,
                    command.getCurrentAlarmCode(),
                    useAuthentication
            );
        }

        //管理者未設定職場リスト：取得した管理者未設定職場リスト
        List<String> unsetList = new ArrayList<>();
//        for (Map.Entry<String, List<String>> target : managerOfEmployeeMap.entrySet()) {
//            val extractAlarmDto = command.listValueExtractAlarmDto.stream()
//                    .filter(x -> x.getWorkplaceID().equals(target.getKey()))
//                    .findFirst();
//            extractAlarmDto.ifPresent(valueExtract -> unsetList.add(valueExtract.getWorkplaceID()));
//        }

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

    private boolean isNotHaveMailSetting(Optional<AlarmListExecutionMailSetting> mailSetting) {
        return !mailSetting.isPresent() || !(mailSetting.get().getContentMailSettings().isPresent());
    }
}