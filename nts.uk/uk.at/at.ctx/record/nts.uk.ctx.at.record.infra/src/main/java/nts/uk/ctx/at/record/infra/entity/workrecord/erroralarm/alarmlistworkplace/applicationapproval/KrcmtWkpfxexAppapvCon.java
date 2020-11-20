package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity: アラームリスト（職場）申請承認の固定抽出条件
 *
 * @author Thanh.LNP
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKPFXEX_APPAPV_CON")
public class KrcmtWkpfxexAppapvCon extends UkJpaEntity {

    /* 職場のエラーアラームチェックID */
    @Id
    @Column(name = "WP_ERROR_ALARM_CHKID")
    public String errorAlarmWorkplaceId;

    /* No */
    @Column(name = "NO")
    public int checkItemAppapv;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    /* 会社ID */
    @Column(name = "CID")
    public String cid;

    @Override
    protected Object getKey() {
        return errorAlarmWorkplaceId;
    }

    public static KrcmtWkpfxexAppapvCon fromDomain(FixedExtractionAppapvCon domain) {
        KrcmtWkpfxexAppapvCon entity = new KrcmtWkpfxexAppapvCon();

        entity.errorAlarmWorkplaceId = domain.getErrorAlarmWorkplaceId();
        entity.checkItemAppapv = domain.getCheckItemAppapv().value;
        entity.messageDisp = domain.getMessageDisp().v();
        entity.cid = AppContexts.user().companyId();

        return entity;
    }

    public FixedExtractionAppapvCon toDomain() {
        return FixedExtractionAppapvCon.create(
                this.errorAlarmWorkplaceId,
                this.checkItemAppapv,
                this.messageDisp,
                this.useAtr
        );
    }
}
