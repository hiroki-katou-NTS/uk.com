package nts.uk.ctx.at.function.app.command.alarm.mailsettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.app.find.alarm.mailsettings.MailSettingsDto;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.*;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class AlarmExecutionMailSettingCommand {
    /** 個人職場区分 */
    private int individualWkpClassify;
    /** 通常自動区分 */
    private int normalAutoClassify;
    /** 本人管理区分 */
    private int personalManagerClassify;
    /** 内容メール設定 */
    private MailSettingsDto contentMailSetting;
    /** 送信元アドレス */
    private String senderAddress;
    /** マスタチェック結果を就業担当へ送信 */
    private boolean sendResult;

    public AlarmListExecutionMailSetting toDomain() {
        return new AlarmListExecutionMailSetting(
                AppContexts.user().companyId(),
                IndividualWkpClassification.of(this.individualWkpClassify),
                NormalAutoClassification.of(this.normalAutoClassify),
                PersonalManagerClassification.of(this.personalManagerClassify),
                Optional.of(new MailSettings(
                        contentMailSetting.getSubject(),
                        contentMailSetting.getText(),
                        contentMailSetting.getMailAddressBCC().stream().map(MailAddress::new).collect(Collectors.toList()),
                        contentMailSetting.getMailAddressCC().stream().map(MailAddress::new).collect(Collectors.toList()),
                        contentMailSetting.getMailRely()
                )),
                new MailAddress(senderAddress),
                true                 //TODO
        );
    }
}
