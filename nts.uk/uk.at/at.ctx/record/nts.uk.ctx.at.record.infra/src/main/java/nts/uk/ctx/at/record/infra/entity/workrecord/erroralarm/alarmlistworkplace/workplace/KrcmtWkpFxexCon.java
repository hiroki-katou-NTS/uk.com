package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "KRCMT_WKP_FXEX_CON")
public class KrcmtWkpFxexCon extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrcmtWkpFxexConPk pk;

    @Column(name = "NO")
    public Integer no;

    @Column(name = "MESSAGE_DISPLAY")
    public String message;

    @Column(name = "USE_ATR")
    public boolean useAtr;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public AlarmFixedExtractionCondition toDomain() {
        return new AlarmFixedExtractionCondition(
                this.pk.id,
                this.no,
                this.useAtr,
                this.message
        );
    }

    public static KrcmtWkpFxexCon toEntity(AlarmFixedExtractionCondition domain) {
        return new KrcmtWkpFxexCon(
                new KrcmtWkpFxexConPk(domain.getId()),
                domain.getNo().value,
                domain.getDisplayMessage().v(),
                domain.isUseAtr()
        );
    }
}
