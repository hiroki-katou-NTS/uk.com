package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Entity: アラームリスト実行メール設定
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KFNMT_ALST_EXE_MAILSET")
public class KfnmtAlstExeMailSetting extends ContractUkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private KfnmtAlstExeMailSettingPK pk;

    /** 件名 */
    @Column(name = "SUBJECT")
    public String subject;

    /** 本文 */
    @Column(name = "TEXT")
    public String text;

    /** BBCメールアドレス */
    @Column(name = "BCC")
    public String bcc;

    /** CCメールアドレス */
    @Column(name = "CC")
    public String cc;

    /** 返信用メールアドレス */
    @Column(name = "MAIL_REPLY")
    public String mailReply;

    /** 送信元アドレス */
    @Column(name = "SENDER_ADDRESS")
    public String senderAddress;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KfnmtAlstExeMailSetting of(AlarmListExecutionMailSetting domain) {
        Optional<MailSettings> content = domain.getContentMailSettings();
        return new KfnmtAlstExeMailSetting(
                new KfnmtAlstExeMailSettingPK(
                        AppContexts.user().companyId(),
                        domain.getNormalAutoClassify().value,
                        domain.getPersonalManagerClassify().value
                ),
                content.isPresent() && content.get().getSubject().isPresent() ? content.get().getSubject().get().v() : null,
                content.isPresent() && content.get().getText().isPresent() ? content.get().getText().get().v() : null,
                content.isPresent() ? content.get().getMailAddressBCC().get(0) : null,
                content.isPresent() ? content.get().getMailAddressCC().get(0) : null,
                content.isPresent() && content.get().getMailRely().isPresent() ? content.get().getMailRely().get().v() : null,
                domain.getSenderAddress().v()
        );
    }

    public AlarmListExecutionMailSetting toDomain() {
        List<String> mailBccList = new ArrayList<>();
        mailBccList.add(bcc);
        List<String> mailCcList = new ArrayList<>();
        mailCcList.add(cc);
        return new AlarmListExecutionMailSetting(
                this.pk.companyID,
                IndividualWkpClassification.of(this.pk.normalAutoAtr), //TODO
                NormalAutoClassification.of(this.pk.normalAutoAtr),
                PersonalManagerClassification.of(this.pk.personalManagerAtr),
                Optional.of(new MailSettings(
                        subject,
                        text,
                        mailBccList,
                        mailCcList,
                        mailReply
                )),
                new MailAddress(senderAddress),
                true //TODO
        );
    }
}
