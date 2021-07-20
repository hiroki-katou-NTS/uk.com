package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
 * アラームリスト実行メール設定
 */
@AllArgsConstructor
@Getter
public class AlarmListExecutionMailSetting extends AggregateRoot {
    private String companyId;

    /** 個人職場区分 */
    private IndividualWkpClassification individualWkpClassify;

    /** 通常自動区分 */
    private NormalAutoClassification normalAutoClassify;

    /** 本人管理区分 */
    private PersonalManagerClassification personalManagerClassify;

    /** 内容メール設定 */
    private Optional<MailSettings> contentMailSettings;

    /** 送信元アドレス */
    private MailAddress senderAddress;     // TODO : QA      // Optional<メールアドレス>

    /** マスタチェック結果を就業担当へ送信 */
    private boolean sendResult;

    /**
     * [1] 設定済みか（会社ID、個人職場区分、通常自動区分、本人管理区分）
     *
     * @param cid                           会社ID
     * @param individualWkpClassification   個人職場区分
     * @param normalAutoClassification      通常自動区分
     * @param personalManagerClassification 本人管理区分
     * @return 設定済
     */
    public boolean isAlreadyConfigured(String cid, int individualWkpClassification, int normalAutoClassification, int personalManagerClassification) {
        return companyId.equals(cid) &&
                individualWkpClassify.value == individualWkpClassification &&
                normalAutoClassify.value == normalAutoClassification &&
                personalManagerClassify.value == personalManagerClassification &&
                contentMailSettings.isPresent();
    }

}
