package nts.uk.ctx.at.function.infra.entity.alarm.mailsettings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.function.dom.alarm.mailsettings.*;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.*;
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

    public AlarmListExecutionMailSetting toDomain(){
        List<String> mailBccList = new ArrayList<>();
        mailBccList.add(bcc);
        List<String> mailCcList = new ArrayList<>();
        mailCcList.add(cc);
        return new AlarmListExecutionMailSetting(
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
                true,
                "" //TODO
        );
    }
}
