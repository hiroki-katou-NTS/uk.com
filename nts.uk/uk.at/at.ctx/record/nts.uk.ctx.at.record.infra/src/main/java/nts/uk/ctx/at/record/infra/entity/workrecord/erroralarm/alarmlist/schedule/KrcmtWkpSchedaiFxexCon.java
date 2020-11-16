package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.schedule;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.schedule.FixedExtractionScheduleCon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity: アラームリスト（職場別）スケジュール／日次の固定抽出条件
 *
 * @author Thanh.LNP
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_WKP_SCHEDAI_FXEXCON")
public class KrcmtWkpSchedaiFxexCon extends UkJpaEntity {
    @EmbeddedId
    public KrcmtWkpSchedaiFxexConPK pk;

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

    public static KrcmtWkpSchedaiFxexCon fromDomain(FixedExtractionScheduleCon domain) {
        KrcmtWkpSchedaiFxexCon entity = new KrcmtWkpSchedaiFxexCon();

        entity.pk = KrcmtWkpSchedaiFxexConPK.fromDomain(domain);
        entity.useAtr = domain.isUseAtr();
        entity.messageDisp = domain.getMessageDisp().v();

        return entity;
    }

    public FixedExtractionScheduleCon toDomain() {
        return FixedExtractionScheduleCon.create(
                this.pk.errorAlarmWorkplaceId,
                this.pk.fixedCheckDayItemName,
                this.useAtr,
                this.messageDisp
        );
    }
}
