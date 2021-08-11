package nts.uk.ctx.at.function.app.command.alarmworkplace.alarmlist;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.mailserver.MailServerAdapter;
import nts.uk.ctx.at.function.dom.adapter.role.AlarmMailSettingsAdapter;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSetting;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRoleRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.Content;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormal;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettingNormalRepository;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettings;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.NormalAutoClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.PersonalManagerClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.Subject;
import nts.uk.ctx.at.function.dom.alarm.sendemail.MailSettingsParamDto;
import nts.uk.ctx.at.function.dom.alarm.sendemail.SendEmailService;
import nts.uk.ctx.at.function.dom.alarmworkplace.sendemail.WorkplaceSendEmailService;
import nts.uk.ctx.at.record.dom.adapter.auth.wkpmanager.WorkplaceManagerAdapter;
import nts.uk.ctx.at.record.dom.adapter.auth.wkpmanager.WorkplaceManagerImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.shr.com.context.AppContexts;

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
    private WorkplaceManagerAdapter wkpManagerAdapter;

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

        val managerIdMap = getManagerIdMap(command.getWorkplaceIds());


        //ドメインモデル「アラームメール送信ロール」を取得する
        val sendingRole = sendingRoleRepository.find(companyId, IndividualWkpClassification.WORKPLACE.value);

        //ドメインモデル「ロール」を取得
        val roleListByCompany = alarmMailSettingsAdapter.findByCompanyId(companyId);

        //Map＜職場ID、List＜社員ID＞を作成
        val employeeIdMap = getEmployeeIdMap(command.getWorkplaceIds());
        //[ロール設定=true]
        if (sendingRole.isPresent() && sendingRole.get().isRoleSetting()) {
            //社員IDListから就業ロールIDを取得
            for (Map.Entry<String, List<String>> entry : employeeIdMap.entrySet()) {
                /*
                * 【Input】
　              ・List＜社員ID＞　＝　ループ中のList＜管理社ID＞
　               ・基準日　＝　システム日付
                【Output】
　              ・Map＜社員ID、ロールID＞
                * */
                //  syWorkplaceAdapter.findBySid(entry.getValue(),  GeneralDate.today());
            }
        } else {

        }

        //ドメインモデル「メールサーバ」を取得する
        boolean useAuthentication = mailServerAdapter.findBy(companyId);
        //メール設定(本人宛)：アラームリスト通常用メール設定.本人宛メール設定
        Optional<AlarmListExecutionMailSetting> mailSetting = exMailListNOrmal;
        //メール設定(管理者宛)：アラームリスト通常用メール設定.管理者宛メール設定
        Optional<AlarmListExecutionMailSetting> mailSettingAdmins = exMailListNOrmal;
        MailSettingsParamDto mailSettingsParamDto = buildMailSend(exMailListNOrmal.get());// メール送信設定

        List<String> lstEmployeeId = new ArrayList<>();

        Optional<MailSettings> mailContent = exMailListNOrmal.get().getContentMailSettings();
        MailSettingNormal mailSettingNormal = null;
        if (mailContent.isPresent()) {
            MailSettings mailSettings = new MailSettings(
                    mailContent.get().getSubject().isPresent() ? mailContent.get().getSubject().get().v() : "",
                    mailContent.get().getText().isPresent() ? mailContent.get().getText().get().v() : "",
                    mailContent.get().getMailAddressCC(),
                    mailContent.get().getMailAddressBCC(),
                    mailContent.get().getMailRely().get().v()
            );
            mailSettingNormal = new MailSettingNormal(companyId, mailSettings, mailSettings);
        }
        return workplaceSendEmailService.alarmWorkplacesendEmail(
                command.getWorkplaceIds(),
                command.listValueExtractAlarmDto,
                mailSettingNormal,
                command.getCurrentAlarmCode(),
                useAuthentication
        );
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

    private Map<String, List<String>> getManagerIdMap(List<String> worplaceIdList) {
        Map<String, List<String>> managerMap = new HashMap<>();
        for (String worlPlaceId : worplaceIdList) {
            List<WorkplaceManagerImport> managerList = wkpManagerAdapter.getWkpManagerListByWkpId(worlPlaceId);
            if (!managerList.isEmpty()) {
                managerMap.put(worlPlaceId, managerList.stream().map(x -> x.getWorkplaceManagerId()).collect(Collectors.toList()));
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