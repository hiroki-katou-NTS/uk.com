package nts.uk.ctx.at.function.dom.adapter.mailserver;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MailServerImportDto {
    // 会社ID
    private String companyId;

    // メール送信認証
    private Integer useAuthentication;

    // 暗号化方式
    private Integer encryptionMethod;

    // 認証方式
    private Integer authenticationMethod;

    // 認証用メールアドレス
    private String emailAuthentication;

    // パスワード
    private String password;

    // SMTP情報
    private SmtpInfoImportDto smtpInfo;

    // POP情報
    private PopInfoImportDto popInfo;

    // IMAP情報
    private ImapInfoImportDto imapInfo;
}
