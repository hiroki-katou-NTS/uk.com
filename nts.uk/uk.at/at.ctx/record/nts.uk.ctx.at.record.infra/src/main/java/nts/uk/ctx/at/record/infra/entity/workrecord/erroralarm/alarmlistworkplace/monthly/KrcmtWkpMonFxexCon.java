package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.monthly;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyCon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: アラームリスト（職場）月次の固定抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_MON_FXEX_CON")
public class KrcmtWkpMonFxexCon extends UkJpaEntity {
    @EmbeddedId
    public KrcmtWkpMonFxexConPK pk;

    /* 使用区分 */
    @Column(name = "USE_ATR")
    private boolean useAtr;

    /* 表示するメッセージ */
    @Column(name = "MESSAGE_DISPLAY")
    public String messageDisp;

    @Override
    protected Object getKey() {
        return pk;
    }

    public static KrcmtWkpMonFxexCon fromDomain(FixedExtractionMonthlyCon domain) {
        KrcmtWkpMonFxexCon entity = new KrcmtWkpMonFxexCon();

        entity.pk = KrcmtWkpMonFxexConPK.fromDomain(domain);
        entity.useAtr = domain.isUseAtr();
        entity.messageDisp = domain.getMessageDisp().v();

        return entity;
    }

    public FixedExtractionMonthlyCon toDomain() {
        return FixedExtractionMonthlyCon.create(
                this.pk.errorAlarmWorkplaceId,
                this.pk.fixedCheckMonthlyItemName,
                this.useAtr,
                this.messageDisp
        );
    }
}
