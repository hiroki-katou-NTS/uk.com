package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

    @EmbeddedId
    public KrcmtWkpfxexAppapvConPK pk;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    public boolean useAtr;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpfxexAppapvCon fromDomain(FixedExtractionAppapvCon domain) {
        KrcmtWkpfxexAppapvCon entity = new KrcmtWkpfxexAppapvCon();

        entity.pk = KrcmtWkpfxexAppapvConPK.fromDomain(domain);
        entity.messageDisp = domain.getMessageDisp().v();

        return entity;
    }

    public FixedExtractionAppapvCon toDomain() {
        return FixedExtractionAppapvCon.create(
                this.pk.errorAlarmWorkplaceId,
                this.pk.checkItemAppapv,
                this.messageDisp,
                this.useAtr
        );
    }
}
