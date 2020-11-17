package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionCondition;
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
@Table(name = "KRCMT_WKP_BASIC_FXEXCON")
public class KrcmtWkpBasicFxexCon extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    public KrcmtWkpBasicFxexConPk pk;

    @Column(name = "CONTRACT_CD")
    public String contractCd;

    @Column(name = "MESSAGE_DISPLAY")
    public String message;

    @Column(name = "USE_ATR")
    public boolean useAtr;

    @Override
    protected Object getKey() {
        return this.pk;
    }

    public BasicFixedExtractionCondition toDomain() {
        return new BasicFixedExtractionCondition(
                this.pk.id,
                this.pk.no,
                this.useAtr,
                this.message
        );
    }

}
