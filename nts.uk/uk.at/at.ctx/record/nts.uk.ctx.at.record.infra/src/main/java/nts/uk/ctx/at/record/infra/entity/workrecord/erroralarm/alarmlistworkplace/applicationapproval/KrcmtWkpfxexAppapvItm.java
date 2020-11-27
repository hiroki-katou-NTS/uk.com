package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItems;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Entity: アラームリスト（職場）申請承認の固定抽出項目
 *
 * @author Thanh.LNP
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKPFXEX_APPAPV_ITM")
public class KrcmtWkpfxexAppapvItm extends ContractUkJpaEntity implements Serializable {

    /* No */
    @Id
    @Column(name = "NO")
    public int checkItemAppapv;

    /* 申請承認チェック名称 */
    @Column(name = "APPLI_APPRO_CHKNAME")
    public String appapvCheckName;

    /* アラームチェック区分 */
    @Column(name = "ALARM_CHK_ATR")
    public int alarmCheckCls;

    /* 最初表示するメッセージ */
    @Column(name = "FIRST_MESSAGE_DIS")
    public String firstMessageDisp;

    /* メッセージを太字にする */
    @Column(name = "MESSAGE_BOLD")
    public boolean boldAtr;

    /* メッセージの色 */
    @Column(name = "MESSAGE_COLOR")
    public String messageColor;

    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    @Override
    protected Object getKey() {
        return checkItemAppapv;
    }

    public static KrcmtWkpfxexAppapvItm fromDomain(FixedExtractionAppapvItems domain) {
        KrcmtWkpfxexAppapvItm entity = new KrcmtWkpfxexAppapvItm();

        entity.checkItemAppapv = domain.getCheckItemAppapv().value;
        entity.appapvCheckName = domain.getAppapvCheckName();
        entity.alarmCheckCls = domain.getAlarmCheckCls().value;
        entity.firstMessageDisp = domain.getFirstMessageDisp().v();
        entity.boldAtr = domain.isBoldAtr();
        entity.messageColor = domain.getMessageColor().map(i -> i.v()).orElse(null);
        entity.cid = AppContexts.user().companyId();

        return entity;
    }

    public FixedExtractionAppapvItems toDomain() {
        return FixedExtractionAppapvItems.create(
                this.checkItemAppapv,
                this.alarmCheckCls,
                this.appapvCheckName,
                this.boldAtr,
                this.firstMessageDisp,
                this.messageColor
        );
    }
}
