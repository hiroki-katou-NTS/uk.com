package nts.uk.ctx.at.function.app.command.alarm.mailsettings;

import lombok.Data;
import lombok.Value;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmListExecutionMailSetting;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.AlarmMailSendingRole;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.IndividualWkpClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailAddress;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.MailSettings;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.NormalAutoClassification;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.PersonalManagerClassification;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * アラームリスト実行メール設定（職場別）を登録する
 *
 * @author rafiqul.islam
 */
@Data
public class RegisterAlarmExecutionMailSettingsCommand {

    private List<AlarmListExecutionMailCmd> mailSettingList;

    private AlarmMailSendingRoleCmd sendingRole;

    public AlarmListExecutionMailSetting toMailSettingDomain(AlarmListExecutionMailCmd mailSetting) {
        MailSettings contentMailSettings = new MailSettings(
                mailSetting.getContentMailSettings().getSubject(),
                mailSetting.getContentMailSettings().getText(),
                mailSetting.getContentMailSettings().getMailAddressCC().stream().map(x -> {
                    return new MailAddress(x);
                }).collect(Collectors.toList()),
                mailSetting.getContentMailSettings().getMailAddressBCC().stream().map(x -> {
                    return new MailAddress(x);
                }).collect(Collectors.toList()),
                mailSetting.getContentMailSettings().getMailRely()
        );
        return new AlarmListExecutionMailSetting(
                AppContexts.user().companyId(),
                IndividualWkpClassification.of(mailSetting.individualWkpClassify),
                NormalAutoClassification.of(mailSetting.normalAutoClassify),
                PersonalManagerClassification.of(mailSetting.personalManagerClassify),
                Optional.ofNullable(contentMailSettings),
                new MailAddress(mailSetting.senderAddress),
                mailSetting.isSendResult()
        );
    }

    public AlarmMailSendingRole toRoleDomain() {
        AlarmMailSendingRoleCmd mailSetting = this.getSendingRole();
        return new AlarmMailSendingRole(
                IndividualWkpClassification.of(mailSetting.getIndividualWkpClassify()),
                mailSetting.isRoleSetting(),
                mailSetting.isSendResult(),
                mailSetting.getRoleIds()
        );
    }

    @Data
    static class AlarmListExecutionMailCmd {
        /**
         * 個人職場区分
         */
        private int individualWkpClassify;

        /**
         * 通常自動区分
         */
        private int normalAutoClassify;

        /**
         * 本人管理区分
         */
        private int personalManagerClassify;

        /**
         * 内容メール設定
         */
        private MailSettingsCmd contentMailSettings;

        /**
         * 送信元アドレス
         */
        private String senderAddress;

        /**
         * マスタチェック結果を就業担当へ送信
         */
        private boolean sendResult;

    }

    @Data
     static class MailSettingsCmd {

        /**
         * 件名
         */
        private String subject;
        /**
         * 本文
         */
        private String text;
        /**
         * BBCメールアドレス
         */
        private List<String> mailAddressBCC;
        /**
         * CCメールアドレス
         */
        private List<String> mailAddressCC;
        /**
         * 返信用メールアドレス
         */
        private String mailRely;
    }
    @Data
    private static class AlarmMailSendingRoleCmd {
        /**
         * 個人職場区分
         */
        private int individualWkpClassify;

        /**
         * ロール設定
         */
        private boolean roleSetting;

        /**
         * マスタチェック結果を就業担当へ送信
         */
        private boolean sendResult;

        /**
         * ロールID
         */
        private List<String> roleIds;
    }
}
